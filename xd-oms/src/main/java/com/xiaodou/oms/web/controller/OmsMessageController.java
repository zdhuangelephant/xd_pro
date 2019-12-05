package com.xiaodou.oms.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.service.message.OmsMessageService;
import com.xiaodou.oms.vo.input.message.InitQQPojo;
import com.xiaodou.oms.vo.input.message.InitQQUrlPojo;
import com.xiaodou.oms.vo.input.message.SendQQMsgPojo;

/**
 * qq消息、邮件等
 * <p/>
 * Date: 2014/12/23
 * Time: 13:02
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("message")
public class OmsMessageController {
  @Resource
  OmsMessageService omsMessageService;


  @RequestMapping("loginModel")
  public String loginModel(){
    return "message/index";
  }

//  @ResponseBody
//  @RequestMapping("loginCheck")
//  public String loginCheck(LoginCheckPojo pojo){
//    LoginCheckVo loginCheckVo = omsMessageService.loginCheck(pojo);
//    return FastJsonUtil.toJson(loginCheckVo);
//  }
//
//  @ResponseBody
//  @RequestMapping("refreshImage")
//  public String refreshImage(LoginCheckPojo pojo){
//    LoginCheckVo loginCheckVo = omsMessageService.refreshImage(pojo);
//    return FastJsonUtil.toJson(loginCheckVo);
//  }
//
//  @ResponseBody
//  @RequestMapping("login")
//  public String login(LoginPojo pojo){
//    LoginVo loginVo = omsMessageService.login(pojo);
//    return FastJsonUtil.toJson(loginVo);
//  }

  /**
   * 初始化redis
   * cookie等
   */
  @ResponseBody
  @RequestMapping("initQQ")
  public String initQQ(InitQQPojo pojo) {
    return FastJsonUtil.toJson(omsMessageService.initQQ(pojo));
  }
  /**
   * 初始化redis
   * cookie等
   */
  @ResponseBody
  @RequestMapping("initQQUrl")
  public String initQQUrl(InitQQUrlPojo pojo) {
    return FastJsonUtil.toJson(omsMessageService.initQQUrl(pojo));
  }

  @ResponseBody
  @RequestMapping("setVersion")
  public String setVersion(String version) {
    return FastJsonUtil.toJson(omsMessageService.setVersion(version));
  }

  /**
   * 清除所有redis缓存
   */
  @ResponseBody
  @RequestMapping("logout")
  public String logout() {
    return FastJsonUtil.toJson(omsMessageService.logout());
  }

  /**
   * 发群、讨论组消息
   */
  @ResponseBody
  @RequestMapping("send")
  public String sendMsg(SendQQMsgPojo pojo) throws Exception {
    return FastJsonUtil.toJson(omsMessageService.sendMsg(pojo));
  }


  public void sendMail() {

  }

  /**
   * 会开启一个线程
   */
  @ResponseBody
  @RequestMapping("poll")
  public String poll() {
    return FastJsonUtil.toJson(omsMessageService.poll());
  }

  /**
   * 关闭线程
   */
  @ResponseBody
  @RequestMapping("close_poll")
  public String closePoll() {
    return FastJsonUtil.toJson(omsMessageService.closePoll());
  }

  /**
   * 设置init为false
   */
  @ResponseBody
  @RequestMapping("close_init")
  public String closeInit() {
    return FastJsonUtil.toJson(omsMessageService.closeInit());
  }

  @ResponseBody
  @RequestMapping("task/shutdown")
  public String taskShutdown() {
    return FastJsonUtil.toJson(omsMessageService.taskShutdown());
  }

//  @ResponseBody
//  @RequestMapping("task/start")
//  public String taskStart(LoginCheckPojo pojo) {
//    return FastJsonUtil.toJson(omsMessageService.taskStart(pojo));
//  }
}
