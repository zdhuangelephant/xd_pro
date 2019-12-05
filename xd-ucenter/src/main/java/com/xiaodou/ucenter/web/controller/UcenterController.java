package com.xiaodou.ucenter.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.summer.web.BaseController;
import com.xiaodou.ucenter.domain.request.FindAllUserRequest;
import com.xiaodou.ucenter.domain.request.FindPasswordRequest;
import com.xiaodou.ucenter.domain.request.FindUserRequest;
import com.xiaodou.ucenter.domain.request.LoginRequest;
import com.xiaodou.ucenter.domain.request.ModifyDefaultPwdRequest;
import com.xiaodou.ucenter.domain.request.ModifyPwdRequest;
import com.xiaodou.ucenter.domain.request.RegistRequest;
import com.xiaodou.ucenter.domain.request.UserByTelAndModRequest;
import com.xiaodou.ucenter.service.UcenterService;

@Controller("ucenterController")
@RequestMapping(value = "/user", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
public class UcenterController extends BaseController {

  @Resource
  UcenterService ucenterService;


  @RequestMapping("/register")
  @ResponseBody
  public String registerMember(@RequestBody RegistRequest request) {
    return ucenterService.registerAccount(request).toString0();
  }

  @RequestMapping(value = "/login")
  @ResponseBody
  public String login(@RequestBody LoginRequest pojo) {
    return ucenterService.login(pojo).toString0();
  }

  @RequestMapping("/find_password")
  @ResponseBody
  public String findPassword(@RequestBody FindPasswordRequest pojo) throws Exception {
    return ucenterService.findPassword(pojo).toString0();
  }

  @RequestMapping(value = "/modify_password")
  @ResponseBody
  public String modifyPwd(@RequestBody ModifyPwdRequest pojo) {
    return ucenterService.modifyPassword(pojo).toString0();
  }

  @RequestMapping(value = "/find_user")
  @ResponseBody
  public String findUser(@RequestBody FindUserRequest pojo) {
    return ucenterService.findUser(pojo).toString0();
  }

  @RequestMapping(value = "/modify_default_password")
  @ResponseBody
  public String modifyDefaultPassword(@RequestBody ModifyDefaultPwdRequest pojo) {
    return ucenterService.modifyDefaultPassword(pojo).toString0();
  }

  @RequestMapping(value = "/find_all_user")
  @ResponseBody
  public String findAllUser(@RequestBody FindAllUserRequest pojo) {
    return ucenterService.findAllUser(pojo).toString0();
  }

  @RequestMapping(value = "/get_user_by_tel_and_mod")
  @ResponseBody
  public String getUserByTelAndMod(@RequestBody UserByTelAndModRequest pojo) {
    return ucenterService.getUserByTelAndMod(pojo).toString0();
  }

}
