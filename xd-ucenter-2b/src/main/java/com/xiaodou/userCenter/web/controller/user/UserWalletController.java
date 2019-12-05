package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.RechargeAmountRequest;
import com.xiaodou.userCenter.request.RechargeOrderRequest;
import com.xiaodou.userCenter.service.UserWalletService;

@Controller("userWalletController")
@RequestMapping("/user")
public class UserWalletController extends BaseController {

  @Resource
  UserWalletService userWalletService;

  @RequestMapping("/good_list")
  public @ResponseBody String goodList(BaseRequest request) throws Exception {
    return userWalletService.goodsList(request).toJsonString();
  }

  @RequestMapping("/order")
  @ResponseBody
  public String order(RechargeOrderRequest request) throws Exception {
    return FastJsonUtil.toJson(userWalletService.order(request));
  }

  @RequestMapping("/recharge")
  @ResponseBody
  public String rechargeAmount(RechargeAmountRequest request) throws Exception {
    return FastJsonUtil.toJson(userWalletService.rechargeAmount(request));
  }

}
