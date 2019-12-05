package com.xiaodou.st.dashboard.web.controller.pay;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.xiaodou.st.dashboard.service.pay.AliPayService;
import com.xiaodou.st.dashboard.service.pay.WxPayService;

@Controller("payController")
@RequestMapping("/pay")
public class PayController {

  @Resource
  WxPayService wxPayService;
  @Resource
  AliPayService aliPayService;
  
  
  @RequestMapping(value = "/ali_pay_unifiedorder")
  @ResponseBody
  public String aliPay(Long orderNumber) throws AlipayApiException {
    return aliPayService.aliPay(orderNumber);
  }

  /**
   * 支付宝支付回调
   */
  @RequestMapping(value = "/ali_call_back")
  public void aliCallBack(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

  }

  /**
   * 微信扫码支付统一下单
   */
  @RequestMapping(value = "/wx_pay_unifiedorder")
  @ResponseBody
  public String wxPayUnifiedorder(HttpServletRequest request, Long orderNumber) throws Exception {
    return wxPayService.wxPay(request, orderNumber);
  }

  /**
   * 微信扫码支付回调
   */
  @RequestMapping(value = "/wx_pay_notify")
  public void wxCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
    wxPayService.wxNotify(request, response);
  }

  /**
   * 微信订单查询
   */
  @RequestMapping(value = "/query_pay")
  @ResponseBody
  public String queryPay(Long orderNumber) throws Exception {
    return wxPayService.queryPay(orderNumber);
  }
}
