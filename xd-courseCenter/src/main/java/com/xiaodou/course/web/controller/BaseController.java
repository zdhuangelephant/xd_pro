package com.xiaodou.course.web.controller;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * @author zhaoxu.yang
 * @ClassName: BaseController
 * @Description: 基类controller，封装公共方法
 * @date 2015年4月11日 下午8:48:56
 */
public class BaseController {

  /**
   * 参数异常提示信息
   *
   * @param errors 错误
   * @return 错误提示
   */
  public String getErrMsg(Errors errors) {
    List<ObjectError> errs = errors.getAllErrors();
    StringBuffer errInfo = new StringBuffer();
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage());
    }
    return errInfo.toString();
  }

}
