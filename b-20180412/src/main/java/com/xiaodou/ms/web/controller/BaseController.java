package com.xiaodou.ms.web.controller;

import com.xiaodou.ms.service.admin.AdminUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zyp on 14-9-1.
 */
public class BaseController {

  /**
   * 获取当前登录用户
   */
  protected AdminUser getUser() {
    return (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * 消息提示
   *
   * @param message
   * @param forward
   * @param dialog
   * @return
   */
  protected ModelAndView showMessage(String messageTitle, String message, String forward, Boolean dialog,Integer closeTime) {
    ModelAndView modelAndView = new ModelAndView("/common/message");
    modelAndView.addObject("messageTitle", messageTitle);
    modelAndView.addObject("message", message);
    modelAndView.addObject("forward", forward);
    modelAndView.addObject("dialog", dialog);
    return modelAndView;
  }

  /**
   * 消息提示
   * @param message
   * @param forward
   * @param dialog
   * @return
   */
  protected ModelAndView showMessage(String messageTitle, String message, String forward, Boolean dialog) {
      return this.showMessage(messageTitle,message,forward,dialog,2000);
  }


  /**
   * 参数异常提示信息
   *
   * @param errors 错误
   * @return 错误提示
   */
  public String getErrMsg(Errors errors) {
    java.util.List<ObjectError> errs = errors.getAllErrors();
    StringBuffer errInfo = new StringBuffer();
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage() + ";");
    }
    return errInfo.toString();
  }

}
