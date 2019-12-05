package com.xiaodou.userCenter.web.controller.user;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

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

  // /**
  // * 提取Controller方法主干
  // *
  // * @param pojo 参数Pojo
  // * @throws Exception
  // */
  // @SuppressWarnings({"rawtypes", "unchecked"})
  // public <T extends BaseRequest> String doMain(T pojo, IController controller) throws Exception {
  // ResultInfo result = null;
  // Errors errors = pojo.validate();
  // if (errors.hasErrors()) {
  // result = new ResultInfo(ResultType.VALFAIL);
  // result.setRetdesc(getErrMsg(errors));
  // return JSON.toJSONString(result);
  // } else {
  // ModuleMapping module = ModuleMapping.getByCode(pojo.getModule());
  // if (null == module) {
  // result = new ResultInfo(ResultType.VALFAIL);
  // result.setRetdesc("unvalid module num, plz check it.");
  // return FastJsonUtil.toJson(result);
  // }
  // ModuleMappingWrapper.getWrapper().setModule(module);
  // return JSON.toJSONString(controller.doService(pojo));
  // }
  // }
  //
  // public static interface IController<T extends BaseRequest> {
  //
  // public abstract ResultInfo doService(T pojo) throws Exception;
  // }

}
