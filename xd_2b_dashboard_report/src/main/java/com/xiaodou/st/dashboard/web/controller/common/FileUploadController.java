package com.xiaodou.st.dashboard.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("fileUploadController")
@RequestMapping("/upload")
public class FileUploadController {

  @RequestMapping("file")
  public ModelAndView file(String scope,String maxFileSize,String mimeTypes){
    ModelAndView modelAndView = new ModelAndView("/common/upload");
    modelAndView.addObject("scope",scope);//范围
    modelAndView.addObject("maxFileSize",maxFileSize);//大小
    modelAndView.addObject("mimeTypes",mimeTypes);//类型
    return modelAndView;
  }
}
