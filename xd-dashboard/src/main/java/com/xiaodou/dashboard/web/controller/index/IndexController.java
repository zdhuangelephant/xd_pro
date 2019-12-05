package com.xiaodou.dashboard.web.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class IndexController {
  /**
   * 首页
   * @return
   */
  @RequestMapping("/")
  public ModelAndView index(){
		  ModelAndView modelAndView = new ModelAndView("/common/index");
		  return modelAndView;   
  }
}
