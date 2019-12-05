package com.xiaodou.server.mapi.web.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.server.mapi.web.controller.BaseController.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月28日
 * @description 基础Controller
 * @version 1.0
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
}
