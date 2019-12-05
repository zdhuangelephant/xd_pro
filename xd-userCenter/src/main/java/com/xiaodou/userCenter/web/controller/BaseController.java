package com.xiaodou.userCenter.web.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.LoginPar;
import com.xiaodou.userCenter.module.mapping.ModuleMapping;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

/**
 * 
 * controller基础类
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public abstract class BaseController {
  /**
   * 
   * @Title: getParamsValue
   * @Description: 获取req中的值，并且解析成对象
   * @param httpRequest
   * @param paramClassType
   * @return T 返回类型
   */
  public <T> T getParamsValue(HttpServletRequest httpRequest, Class<T> paramClassType) {
    String json = httpRequest.getParameter("req");
    if (StringUtils.isBlank(json)) {
      return null;
    }
    return FastJsonUtil.fromJson(this.getDecodeValue(json), paramClassType);
  }
  /**
   * 从请求中获取BaseReq中的字段
   * 
   * @param httpRequest
   * @param baseReq
   */
  public void getParamsValueFromHeader(HttpServletRequest httpRequest, BaseRequest baseReq) {
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.deviceId.toString())))
      baseReq.setDeviceId(String.valueOf(httpRequest.getHeader(LoginPar.deviceId.toString())));
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.clientIp.toString())))
      baseReq.setClientIp(httpRequest.getHeader(LoginPar.clientIp.toString()));
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.sessionToken.toString())))
      baseReq.setSessionToken(httpRequest.getHeader(LoginPar.sessionToken.toString()));
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.module.toString())))
      baseReq.setModule(httpRequest.getHeader(LoginPar.module.toString()));
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.version.toString())))
      baseReq.setVersion(httpRequest.getHeader(LoginPar.version.toString()));
    if (StringUtils.isNotBlank(httpRequest.getHeader(LoginPar.clientType.toString())))
      baseReq.setClientType(httpRequest.getHeader(LoginPar.clientType.toString()));
  }

  /**
   * 
   * 设置字符串编码
   * 
   * @param paramValue
   * @return
   */
  public String getDecodeValue(String paramValue) {
    try {
      return URLDecoder.decode(paramValue, "UTF-8");
    } catch (Exception e) {
      return paramValue;
    }
  }

  /**
   * 参数异常提示信息
   * 
   * @param errors 错误
   * @return 错误提示
   */
  public String getErrMsg(Errors errors) {
    List<ObjectError> errs = errors.getAllErrors();
    StringBuffer errInfo = new StringBuffer(200);
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage());
    }
    return errInfo.toString();
  }

  /**
   * 提取Controller方法主干
   * 
   * @param pojo 参数Pojo
   * @param serviceHandler 业务方法Handler
   * @return resultInfoVO
   * @throws Exception
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String doMain(HttpServletRequest request, IController controller) {
    ResultInfo result = null;
    try {
      if (StringUtils.isNotBlank(request.getHeader("module"))) {
        ModuleMapping module = ModuleMapping.getByCode(request.getHeader("module"));
        if (null == module) {
          result = new ResultInfo(ResultType.VALFAIL);
          result.setRetdesc("unvalid module num, plz check it.");
          return FastJsonUtil.toJson(result);
        }
        ModuleMappingWrapper.getWrapper().setModule(module);
      } else {
        result = new ResultInfo(ResultType.VALFAIL);
        result.setRetdesc("module can't be empty.");
        return FastJsonUtil.toJson(result);
      }
      BaseRequest pojo = controller.buildRequest(request);
      if (null == pojo) return FastJsonUtil.toJson(new ResultInfo(ResultType.VALFAIL));
      getParamsValueFromHeader(request, pojo);
      Errors errors = pojo.validate();
      if (errors.hasErrors()) {
        result = new ResultInfo(ResultType.VALFAIL);
        result.setRetdesc(getErrMsg(errors));
        return JSON.toJSONString(result);
      } else {
        return JSON.toJSONString(controller.doService(pojo, request));
      }
    } catch (Exception e) {
      LoggerUtil.error(this.getClass().getSimpleName(), e);
      result = new ResultInfo(ResultType.SYSFAIL);
    }
    return JSON.toJSONString(result);
  }

  public static interface IController<T extends BaseRequest> {
    public abstract ResultInfo doService(T pojo, HttpServletRequest request) throws Exception;

    public abstract T buildRequest(HttpServletRequest request) throws Exception;
  }

}
