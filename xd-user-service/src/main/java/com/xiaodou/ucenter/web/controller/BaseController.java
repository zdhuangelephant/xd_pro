package com.xiaodou.ucenter.web.controller;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.module.ModuleWrapper;
import com.xiaodou.ucenter.request.BaseRequest;

public class BaseController {

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
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public <T extends BaseRequest> String doMain(T pojo, IController controller) {
    ResultInfo result = null;
    try {
      Errors errors = pojo.validate();
      if (errors.hasErrors()) {
        result = new ResultInfo(ResultType.VALFAIL);
        result.setRetdesc(getErrMsg(errors));
        return JSON.toJSONString(result);
      } else {
        if (StringUtils.isBlank(pojo.getModule()) || !StringUtils.isNumeric(pojo.getModule())) {
          result = new ResultInfo(ResultType.VALFAIL);
          result.setRetdesc("unvalid module num, please check it.");
          return FastJsonUtil.toJson(result);
        }
        ModuleWrapper.getWrapper().setModule(pojo.getModule());
        return JSON.toJSONString(controller.doService(pojo));
      }
    } catch (Exception e) {
      LoggerUtil.error(this.getClass().getSimpleName(), e);
      result = new ResultInfo(ResultType.SYSFAIL);
    }
    return JSON.toJSONString(result);
  }

  public static interface IController<T extends BaseRequest> {

    public abstract ResultInfo doService(T pojo) throws Exception;
  }

}
