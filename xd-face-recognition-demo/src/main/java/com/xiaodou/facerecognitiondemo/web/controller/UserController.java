package com.xiaodou.facerecognitiondemo.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.facerecognitiondemo.service.UserService;
import com.xiaodou.facerecognitiondemo.vo.request.UserRequest;
import com.xiaodou.facerecognitiondemo.vo.request.UserUptokenPojo;
import com.xiaodou.summer.web.BaseController;

@Controller("userController")
@RequestMapping("user")
public class UserController extends BaseController {

  @Resource
  UserService userService;

  @RequestMapping("/create")
  @ResponseBody
  public String create(String faceUrl) {
    return userService.addUser(faceUrl).toString0();
  }

  @RequestMapping("/uptoken")
  @ResponseBody
  public String uptoken(UserUptokenPojo pojo) throws Exception {
    return userService.getUptoken(pojo).toString0();
  }

  @RequestMapping("/compare")
  @ResponseBody
  public String compare(UserRequest pojo) {
    return userService.compare(pojo).toString0();
  }

}
