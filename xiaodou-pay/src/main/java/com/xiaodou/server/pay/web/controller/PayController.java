package com.xiaodou.server.pay.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.request.TokenRequest;
import com.xiaodou.server.pay.request.callback.AliCallbackRequest;
import com.xiaodou.server.pay.request.callback.AppleCallbackRequest;
import com.xiaodou.server.pay.response.PayResponse;
import com.xiaodou.server.pay.service.PayService;
import com.xiaodou.server.pay.service.callback.AliCallBackService;
import com.xiaodou.server.pay.service.callback.AppleCallBackService;
import com.xiaodou.server.pay.service.callback.WxCallBackService;
import com.xiaodou.server.pay.vo.alarm.TokenAlarm;

@Controller("payController")
@RequestMapping("/payment")
public class PayController {
  @Resource
  PayService payService;
  @Resource
  AliCallBackService aliCallBackService;
  @Resource
  AppleCallBackService appleCallBackService;
  @Resource
  WxCallBackService wxCallBackService;

  /**
   * 支付接口
   * 
   * @param pojo
   */
  @RequestMapping(value = "/pay", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String pay(@RequestBody PayRequest pojo) {
    return payService.pay(pojo).toString();
  }

  /**
   * 获取支付凭证接口
   * 
   * @param pojo
   */
  @RequestMapping(value = "/token", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String token(@RequestBody TokenRequest pojo) {
    try {
      return payService.token(pojo).toString();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new TokenAlarm(e.getMessage()));
      throw e;
    }

  }

  /**
   * 苹果回调接口
   * 
   * @param pojo
   */
  @RequestMapping(value = "/apple_call_back")
  @ResponseBody
  public String appleCallBack(AppleCallbackRequest pojo) {
    return appleCallBackService.callback(pojo).toString();
  }

  /**
   * 支付宝回调接口
   * 
   * @param pojo
   */
  @RequestMapping(value = "/ali_call_back")
  @ResponseBody
  public String aliCallBack(AliCallbackRequest pojo) {
    PayResponse response = aliCallBackService.callback(pojo);
    if (PayStatus.SUCCESS.getCode().toString().equals(response.getPayStatus())){
      return "success";
    }
    return "fail";
  }

  /**
   * 微信回调接口
   * 
   * @param pojo
   */
  @RequestMapping(value = "/wx_call_back")
  @ResponseBody
  public String wxCallBack(HttpServletRequest request, HttpServletResponse response) {
    try {
      PayResponse payResponse = wxCallBackService.callback(request, response);
      if (PayStatus.SUCCESS.getCode().toString().equals(payResponse.getPayStatus())) {
        return "success";
      } else {
        return payResponse.getRetdesc().toString();
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "fail";
  }
}
