package com.xiaodou.st.dashboard.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.util.ExceptionWrapper;

/**
 * 
 * @name ExceptionController CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月29日
 * @description 1.在@ExceptionHandler方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
 *              2.@ExceptionHandler方法的入参中不能传入Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
 *              3.@ExceptionHandler方法标记的异常有优先级的问题. 4.@ControllerAdvice: 如果在当前 Handler
 *              中找不到@ExceptionHandler方法来出来当前方法出现的异常,
 *              则将去@ControllerAdvice标记的类中查找@ExceptionHandler标记的方法来处理异常.
 */
@ControllerAdvice
public class ExceptionController {

  /**
   * 异常统一处理
   * 
   * @param ex
   * @return
   */
  @ExceptionHandler({Exception.class})
  public ModelAndView exception(Exception ex) {
    if (null != ExceptionWrapper.getWrapper().getValue())
      ex = (Exception) ExceptionWrapper.getWrapper().getValue();
    LoggerUtil.error(ex.getMessage(), ex);
    ModelAndView view = new ModelAndView();
    view.setViewName("/common/error");
    view.addObject("errorMsg", ex.getMessage());
    return view;
  }
}
