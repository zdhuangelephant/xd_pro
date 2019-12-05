package com.xiaodou.ucerCenter.agent.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseRequest;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;
import com.xiaodou.userCenter.enums.LoginPar;
import com.xiaodou.userCenter.module.mapping.ModuleMapping;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.CheckTokenResult;
import com.xiaodou.userCenter.util.FileUtils;

/**
 * 登录filter，安全拦截
 * 
 * @author weichao.zai
 * 
 */
public class CheckUserStatusFilter extends BaseController implements Filter {

  private Class<? extends BaseUserModel> type = BaseUserModel.class;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    // TODO 1、入口 解密所有请求

    LoginMod loginMod = null;
    PrintWriter out = null;
    // 2、获取入参信息
    try {
      // 初始化登录参数
      loginMod = new LoginMod(req);
    } catch (Exception e) {
      LoggerUtil.error("请求入参获取异常", e);
      out = response.getWriter();
      out.print("{\"retCode\":\"-1\",\"retInfo\":\"Get The HTTP request parameter exception..\"}");
      closePrintWriter(out);
      return;
    }

    try {
      // 3、 判断是否登陆 用户信息获取后存储位置：threadlocal
      if (isFilterOpen()) {
        ResultInfo result = null;
        if ((result = checkLogin(loginMod)) != null) {
          out = response.getWriter();
          out.print(JSON.toJSONString(result));
          out.flush();
          return;
        }
      }
    } catch (Exception e) {
      out = response.getWriter();
      LoggerUtil.error("检测用户是否登录异常", e);
      out.print("{\"retCode\":\"-1\",\"retInfo\":\"检测用户是否登录异常.\"}");
      out.flush();
    } finally {
      closePrintWriter(out);
    }

    // 4、filter链接着走
    chain.doFilter(request, response);
  }

  /**
   * 检验登录状态
   * 
   * @param loginMod 登录模型
   * @return 登录结果
   */
  public ResultInfo checkLogin(LoginMod loginMod) {
    Errors errors = null;
    ResultInfo result = null;
    // 1、 检验参数有效性
    if (!isInFilterMap(loginMod.remoteAccessUrl) && (errors = loginMod.validate()).hasErrors()) {
      result = new ResultInfo(ResultType.VALFAIL);
      result.appendRetdesc(getErrMsg(errors));
      // 2、 检验登录状态
    } else {
      ModuleMapping module = getModule(loginMod);
      if (null == module) {
        result = new ResultInfo(ResultType.VALFAIL);
        result.setRetdesc("unvalid module num, plz check it.");
      } else if (!checkUserStatus(loginMod, module)) {
        result = new ResultInfo(ResultType.NOTLOGIN);
      }
    }
    if (isInFilterMap(loginMod.remoteAccessUrl)) return null;
    return result;
  }

  /**
   * 
   * 访问用户中心，检测用户是否登陆
   * 
   * @param result
   * 
   * @param userId
   * @return true-已登陆，false-未登陆
   * @throws Exception
   */
  private boolean checkUserStatus(LoginMod loginMod, ModuleMapping module) {
    UserTokenWrapper.getWrapper().setDeviceId(loginMod.deviceId);
    UserTokenWrapper.getWrapper().setClientIp(loginMod.clientIp);
    UserTokenWrapper.getWrapper().setClientType(loginMod.clientType);
    UserTokenWrapper.getWrapper().setVersion(loginMod.version);
    UserTokenWrapper.getWrapper().setModule(module.getCode());
    Map<String, String> headers = Maps.newHashMap();
    headers.put(LoginPar.sessionToken.toString(), loginMod.sessionToken);
    headers.put(LoginPar.deviceId.toString(), loginMod.deviceId);
    headers.put(LoginPar.clientIp.toString(), loginMod.clientIp);
    headers.put(LoginPar.module.toString(), loginMod.module);
    headers.put(LoginPar.clientType.toString(), loginMod.clientType);
    headers.put(LoginPar.version.toString(), loginMod.version);
    CheckTokenResult result =
        (CheckTokenResult) doflow(null, BaseRequest.class, CheckTokenResult.class, headers);
    type = module.getResponse();
    if (null == result || null == result.getUser()) {
      return false;
    } else {
      BaseUserModel user = parseResponse(result.getUser(), type);
      // 存储本地变量
      UserTokenWrapper.getWrapper().setUserModel(user);
      UserTokenWrapper.getWrapper().setUserToken(loginMod.sessionToken);
      return true;
    }
  }

  /**
   * 
   * 获取用户访问模块
   * 
   * @param loginMod
   * @return
   */
  private ModuleMapping getModule(LoginMod loginMod) {
    return ModuleMapping.getByCode(loginMod.module);
  }

  /**
   * 构造request请求内容
   * 
   * @param obj
   * @param clazzReq
   * @return
   * @throws Exception
   */
  public String createContent(Object obj, Class<?> clazzReq) throws Exception {
    if (obj == null) return "";
    StringBuilder content = new StringBuilder();
    String value = JSON.toJSONString(obj);
    content.append("&").append("req=").append(URLEncoder.encode(value, "utf-8"));

    return content.substring(1);

  }

  public <T> T parseResponse(String result, Class<T> clazzRes) {
    return null == result ? null : JSON.parseObject(result, clazzRes);

  }

  /**
   * 访问用户中心
   * 
   * @param req
   * @param clazzReq
   * @param clazzRes
   * @return
   */
  public <T> T doflow(Object req, Class<?> clazzReq, Class<T> clazzRes, Map<String, String> headers) {
    try {
      String content = this.createContent(req, clazzReq);
      String result =
          send(
              FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.host"),
              Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES().getProperties(
                  "user.center.port")),
              FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.checktoken.url"),
              Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES().getProperties(
                  "user.center.checktoken.retryTime")),
              Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES().getProperties(
                  "user.center.checktoken.timeout")), content, headers);
      return parseResponse(result, clazzRes);
    } catch (Exception e) {
      LoggerUtil.error("[检测用户是否登陆，访问用户中心异常]", e);
      return null;
    }

  }

  public static String send(String host, int port, String path, int retryTime, int timeout,
      String content, Map<String, String> headers) throws Exception {

    HttpUtil http = HttpUtil.getInstance(host, port, "http", timeout, retryTime);
    HttpMethod httpMethod =
        HttpMethodUtil.getPostMethod(path, "application/x-www-form-urlencoded", "utf-8", content);
    for (String headerName : headers.keySet()) {
      httpMethod.setRequestHeader(headerName, headers.get(headerName));
    }
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
    if (!httpResult.isResultOk()) {
      LoggerUtil.error("[通信异常][" + httpResult.toString() + "]", httpResult.getErr() == null
          ? new RuntimeException()
          : httpResult.getErr());
      return null;
    } else {
      return httpResult.getContent();
    }
  }

  /**
   * 
   * 判断是否过滤－判断用户是否登陆
   * 
   * @param remoteAccessUrl
   * @return
   */
  private boolean isInFilterMap(String remoteAccessUrl) {
    boolean notBlank0 =
        StringUtils.isNotBlank(FileUtils.getNO_LOGIN_ACCESS_FILTER_URL().getProperties(
            remoteAccessUrl));
    if (notBlank0) return notBlank0;
    for (String ignorePrefix : FileUtils.getNO_LOGIN_ACCESS_FILTER_URL()
        .getProperties("access.filter.ignore.prefix").split(";")) {
      if (remoteAccessUrl.startsWith(ignorePrefix)) notBlank0 = true;
    }
    return notBlank0;
  }

  /**
   * 关闭写资源
   * 
   * @param out
   */
  public void closePrintWriter(PrintWriter out) {
    if (out != null) out.close();
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    Assert.notNull(StringUtils.isNotBlank(FileUtils.getUSER_CENTER_PROPERTIES().getProperties(
        "user.center.host")));
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES().getPropertiesInt("user.center.port") != -1);
    Assert.notNull(FileUtils.getUSER_CENTER_PROPERTIES()
        .getProperties("user.center.checktoken.url"));
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES().getPropertiesInt(
        "user.center.checktoken.retryTime") != -1);
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES().getPropertiesInt(
        "user.center.checktoken.timeout") != -1);
  }

  private boolean isFilterOpen() {
    String filterStatus =
        FileUtils.getNO_LOGIN_ACCESS_FILTER_URL().getProperties("access.filter.status");
    if (StringUtils.isNotBlank(filterStatus)) {
      return filterStatus.equals("ON");
    }
    return true;
  }

  @Override
  public void destroy() {

  }

  /**
   * @name LoginMod CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月18日
   * @description 登录模型类
   * @version 1.0
   * @tags 如果你想尝试优化这段代码,请你将浪费的时间记录在下面,让以后尝试优化的人作为一个参考.
   * @waste
   */
  @SuppressWarnings("unused")
  private static class LoginMod extends BaseValidatorPojo {
    public LoginMod(HttpServletRequest req) {
      module = req.getHeader(LoginPar.module.toString());
      sessionToken = req.getHeader(LoginPar.sessionToken.toString());
      deviceId = req.getHeader(LoginPar.deviceId.toString());
      clientIp = req.getHeader(LoginPar.clientIp.toString());
      version = req.getHeader(LoginPar.version.toString());
      clientType = req.getHeader(LoginPar.clientType.toString());
      remoteAccessUrl = req.getRequestURI();
      if (StringUtils.isBlank(remoteAccessUrl)) remoteAccessUrl = StringUtils.EMPTY;
    }

    /** module 所属模块 */
    @NotEmpty
    private String module;
    /** sessionToken 登录Token */
    @NotEmpty
    private String sessionToken;
    /** deviceId 设备号 */
    @NotEmpty
    private String deviceId;
    /** clientIp 设备IP */
    @NotEmpty
    private String clientIp;
    /** clientType 系统类型 */
    @NotEmpty
    @LegalValueList({"ios", "android", "web", "other"})
    private String clientType;
    /** version */
    @NotEmpty
    private String version;
    /** remoteAccessUrl 请求地址 */
    @NotEmpty
    private String remoteAccessUrl;

    public String getClientType() {
      return clientType;
    }

    public void setClientType(String clientType) {
      this.clientType = clientType;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public String getModule() {
      return module;
    }

    public void setModule(String module) {
      this.module = module;
    }

    public String getSessionToken() {
      return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
    }

    public String getDeviceId() {
      return deviceId;
    }

    public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
    }

    public String getClientIp() {
      return clientIp;
    }

    public void setClientIp(String clientIp) {
      this.clientIp = clientIp;
    }

    public String getRemoteAccessUrl() {
      return remoteAccessUrl;
    }

    public void setRemoteAccessUrl(String remoteAccessUrl) {
      this.remoteAccessUrl = remoteAccessUrl;
    }
  }
}
