package com.xiaodou.server.mapi.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

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
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.server.mapi.constant.UcenterConstant;
import com.xiaodou.server.mapi.response.ucenter.CheckTokenResult;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.util.CheckTokenExceptionWrapper;
import com.xiaodou.server.mapi.util.FileUtils;
import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseRequest;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;


/**
 * @name @see com.xiaodou.server.mapi.web.filter.CheckUserStatusFilter.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月28日
 * @description TODO
 * @version 1.0
 */
public class CheckUserStatusFilter extends BaseController implements Filter {

  private static final String UnCompleteInfo = "00007";
  private static final String TOURIST_SWITCH_CODE = "027";
  /**游客学习（0为关闭学习，1为开启学习）*/
  public static final String TOURIST_SWITCH_CLOSE = "0";
  public static final String TOURIST_SWITCH_OPENT = "1";

  public static enum LoginPar {
    /** module 模块 */
    module,
    /** sessionToken 登录Token */
    sessionToken,
    /** deviceId 设备号 */
    deviceId,
    /** clientIp 设备IP */
    clientIp,
    /** clientType 设备类型 */
    clientType,
    /** version app版本号 */
    version,
    /** traceId 会话标识符 */
    traceId
  }
  
  public enum CheckTokenEnum {

    READ_TIMED_OUT("00003","请求超时！"),
    SEND_HTTP_FAIL("00004","http异常,访问请求超时！");
    
    /**code 结果码*/
    private String code;
    /**msg 提示信息*/
    private String msg;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    CheckTokenEnum(String code, String msg) {
      this.code= code;
      this.msg=msg;
    }
    
  }

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
      UserTokenWrapper.getWrapper().setHeadParam(loginMod);
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
    if ((errors = loginMod.validate()).hasErrors()) {
      result = new ResultInfo(ResultType.VALFAIL);
      result.appendRetdesc(getErrMsg(errors));
      // 2、 检验登录状态
    } else {
        CheckTokenResult checkTokenResult = this.checkUserStatus(loginMod);
        if (null == checkTokenResult) {
          result = new ResultInfo(ResultType.NOTLOGIN);
          if (null != CheckTokenExceptionWrapper.getWrapper().getAndRemove()) {
            result.setRetcode(CheckTokenEnum.SEND_HTTP_FAIL.getCode());
            result.setRetdesc(CheckTokenEnum.SEND_HTTP_FAIL.getMsg());
          }
        } else {
          if (!ResultType.SUCCESS.getCode().equals(checkTokenResult.getRetcode())) {
            result = checkTokenResult;
          }
        }
    }
    // 判断用户是否需要登陆访问权限
    if (result != null && !ResultType.SUCCESS.getCode().equals(result.getRetcode())
        && isInFilterMap(loginMod.remoteAccessUrl)) {
      result = null;
    }
    // 判断请求是否需要拦截
    if (isInInterceptMap(loginMod.remoteAccessUrl)) {
      result = new ResultInfo(ResultType.VALFAIL);
      result.setRetdesc("不具备访问权限,当前请求被拒绝");
      return result;
    }
    // 判断登录用户 无需必填信息
    if (result != null && UnCompleteInfo.equals(result.getRetcode())
        && isInInterceptAccess(loginMod.remoteAccessUrl)) {
      return null;
    }
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
  private CheckTokenResult checkUserStatus(LoginMod loginMod) {
    CheckTokenResult result = (CheckTokenResult) doflow(this.createContent(loginMod),
        BaseRequest.class, CheckTokenResult.class, null);
    if (null == result) {
      return null;
    }
    if (null != result.getUser()) {
      UserModelResponse user = parseResponse(result.getUser(), UserModelResponse.class);
      // 存储本地变量
      UserTokenWrapper.getWrapper().setUser(user);
    }
    return result;
  }

  /**
   * 构造request请求内容
   * 
   * @param obj
   * @return
   * @throws Exception
   */
  public String createContent(LoginMod loginMod) {
    try {
      if (loginMod == null) return "";
      StringBuilder content = new StringBuilder();
//      content.append(String.format("&%s=%s", LoginPar.module.toString(), loginMod.module));
      content.append(String.format("&%s=%s", LoginPar.deviceId.toString(), loginMod.deviceId));
      content
          .append(String.format("&%s=%s", LoginPar.sessionToken.toString(), loginMod.sessionToken));
      content.append(String.format("&%s=%s", LoginPar.clientIp.toString(), loginMod.clientIp));
      content.append(String.format("&%s=%s", LoginPar.version.toString(), loginMod.version));
      content.append(String.format("&%s=%s", LoginPar.clientType.toString(), loginMod.clientType));
      content.append(String.format("&%s=%s", LoginPar.traceId.toString(), loginMod.traceId));
      return content.toString();
    } catch (Exception e) {
      LoggerUtil.error("[构造request请求内容]", e);
      return null;
    }
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
  public <T> T doflow(String content, Class<?> clazzReq, Class<T> clazzRes,
      Map<String, String> headers) {
    try {
      String result = send(FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.host"),
          Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.port")),
          FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.checktoken.url"),
          Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES()
              .getProperties("user.center.checktoken.retryTime")),
          Integer.parseInt(FileUtils.getUSER_CENTER_PROPERTIES()
              .getProperties("user.center.checktoken.timeout")),
          content, headers);
      return parseResponse(result, clazzRes);
    } catch (Exception e) {
      CheckTokenExceptionWrapper.getWrapper().setErrValue(CheckTokenEnum.SEND_HTTP_FAIL);
      LoggerUtil.error("[检测用户是否登陆，访问用户中心异常]", e);
      return null;
    }

  }

  public static String send(String host, int port, String path, int retryTime, int timeout,
      String content, Map<String, String> headers) throws Exception {

    HttpUtil http = HttpUtil.getInstance(host, port, "http", timeout, retryTime);
    HttpMethod httpMethod =
        HttpMethodUtil.getPostMethod(path, "application/x-www-form-urlencoded", "utf-8", content);
    if (null != headers) {
      for (String headerName : headers.keySet()) {
        httpMethod.setRequestHeader(headerName, headers.get(headerName));
      }
    }
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
    InOutEntity logEntity = new InOutEntity();
    logEntity.setTargetUrl(String.format("http://%s:%s/%s", host, port, path));
    logEntity.setResult(httpResult);
    logEntity.setResponseInfo(StringUtils.EMPTY);
    LoggerUtil.inOutInfo(logEntity);
    if (!httpResult.isResultOk()) {
      LoggerUtil.error("[通信异常][" + httpResult.toString() + "]",
          httpResult.getErr() == null ? new RuntimeException() : httpResult.getErr());
      CheckTokenExceptionWrapper.getWrapper().setErrValue(CheckTokenEnum.SEND_HTTP_FAIL);
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
    // 1、check ignore url
    if (StringUtils.isNotBlank(
        FileUtils.getNO_LOGIN_ACCESS_FILTER_URL().getProperties(remoteAccessUrl)))
      return true;
    // 2、check ignore prefix
    String ignorePrefixStrand =
        FileUtils.getNO_LOGIN_ACCESS_FILTER_URL().getProperties("access.filter.ignore.prefix");
    if (StringUtils.isNotBlank(ignorePrefixStrand)) {
      for (String ignorePrefix : ignorePrefixStrand.split(";")) {
        if (remoteAccessUrl.startsWith(ignorePrefix)) return true;
      }
    }
    return false;
  }

  /**
   * 
   * 判断是否拦截－判断用户是否具备访问权限
   * 
   * @param remoteAccessUrl
   * @return
   */
  private boolean isInInterceptMap(String remoteAccessUrl) {
    // 1、check ingore platform
    UserModelResponse userInfo = UserTokenWrapper.getWrapper().getUser();
    if (null != userInfo) {
      if (UcenterConstant.PLATFORM_TOURIST.equals(userInfo.getMainAccount())) {
        CommonInfo commonInfo = CommonInfoUtil.getCommonInfoInfoByCode(TOURIST_SWITCH_CODE);
        if (null != commonInfo && StringUtils.isNotBlank(commonInfo.getInfoVersion())
            && TOURIST_SWITCH_OPENT.equals(commonInfo.getInfoVersion())) {
          return false;
        }
        if (StringUtils.isNotBlank(FileUtils.getNO_LOGIN_ACCESS_FILTER_URL()
            .getProperties(String.format("access.filter.ignore.platform.%s.%s",
                userInfo.getMainAccount(), remoteAccessUrl)))) {
          return true;
        }
      }
    }

    return false;
  }


  /**
   * 判断登录用户 无需必填信息
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年6月8日
   * @param remoteAccessUrl
   * @return
   */
  private boolean isInInterceptAccess(String remoteAccessUrl) {
    // 1、check ingore info platform
    if (StringUtils.isNotBlank(FileUtils.getNO_LOGIN_ACCESS_FILTER_URL()
        .getProperties(String.format("access.filter.ignore.info.%s", remoteAccessUrl))))
      return true;
    return false;
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
    Assert.notNull(StringUtils
        .isNotBlank(FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.host")));
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES().getPropertiesInt("user.center.port") != -1);
    Assert
        .notNull(FileUtils.getUSER_CENTER_PROPERTIES().getProperties("user.center.checktoken.url"));
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES()
        .getPropertiesInt("user.center.checktoken.retryTime") != -1);
    Assert.isTrue(FileUtils.getUSER_CENTER_PROPERTIES()
        .getPropertiesInt("user.center.checktoken.timeout") != -1);
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
  public static class LoginMod extends BaseValidatorPojo {
    public LoginMod() {}

    public LoginMod(HttpServletRequest req) {
      sessionToken = req.getHeader(LoginPar.sessionToken.toString());
      deviceId = req.getHeader(LoginPar.deviceId.toString());
      clientIp = req.getHeader(LoginPar.clientIp.toString());
      version = req.getHeader(LoginPar.version.toString()) == null ? "1.0.0" : req.getHeader(LoginPar.version.toString());
      clientType = req.getHeader(LoginPar.clientType.toString());
      traceId = buildTraceId(clientType, req.getHeader(LoginPar.traceId.toString()));
      remoteAccessUrl = req.getRequestURI();
      if (StringUtils.isBlank(remoteAccessUrl)) {
        remoteAccessUrl = StringUtils.EMPTY;
      }
    }

    private String buildTraceId(String clientType, String traceId) {
      if (StringUtils.isBlank(clientType)) clientType = "server";
      if (StringUtils.isBlank(traceId)) traceId = UUID.randomUUID().toString();
      return String.format("%s-%s", clientType, traceId);
    }

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
    /** traceId 会话标识符 */
    private String traceId;
    /** remoteAccessUrl 请求地址 */
    @NotEmpty
    private String remoteAccessUrl;

    public String getClientType() {
      return clientType;
    }

    public String getTraceId() {
      return traceId;
    }

    public void setTraceId(String traceId) {
      this.traceId = traceId;
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

//    public String getModule() {
//      return module;
//    }
//
//    public void setModule(String module) {
//      this.module = module;
//    }

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
