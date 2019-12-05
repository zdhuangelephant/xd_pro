package com.xiaodou.ms.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zyp on 15/8/4.
 */
@Controller("fileUploadController")
@RequestMapping("/upload")
public class FileUploadController {

  @RequestMapping("file")
  public ModelAndView file(String scope,String maxFileSize,String mimeTypes){
    ModelAndView modelAndView = new ModelAndView("/common/upload");
    modelAndView.addObject("scope",scope);
    modelAndView.addObject("maxFileSize",maxFileSize);
    modelAndView.addObject("mimeTypes",mimeTypes);
    return modelAndView;
  }
}
