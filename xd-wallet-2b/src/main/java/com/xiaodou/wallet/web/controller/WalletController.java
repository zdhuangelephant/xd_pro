package com.xiaodou.wallet.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.wallet.request.BusinessOperateRequest;
import com.xiaodou.wallet.request.wallet.AccountBillRequest;
import com.xiaodou.wallet.request.wallet.CreateAccountRequest;
import com.xiaodou.wallet.request.wallet.PayAmountRequest;
import com.xiaodou.wallet.request.wallet.RechargeAmountRequest;
import com.xiaodou.wallet.request.wallet.RechargeOrderRequest;
import com.xiaodou.wallet.service.wallet.WalletService;
import com.xiaodou.wallet.vo.alarm.ConsumeAlarm;
import com.xiaodou.wallet.vo.alarm.CreateAccountAlarm;
import com.xiaodou.wallet.vo.alarm.OrderAlarm;
import com.xiaodou.wallet.vo.alarm.RechargeAlarm;

@Controller
@RequestMapping("wallet")
public class WalletController extends BaseController {

  @Resource
  WalletService walletService;

  /**
   * 0.创建账户
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "create_account", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String createAccount(@RequestBody CreateAccountRequest request) {
    try {
      return walletService.operateCreateAccount(request).toString0();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new CreateAccountAlarm(e.getMessage()));
      throw e;
    }
  }

  /**
   * 2.发起支付
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "pay", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String payAmount(@RequestBody PayAmountRequest request) {
    try {
      return walletService.operatePay(request).toString0();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new ConsumeAlarm(e.getMessage()));
      throw e;
    }
  }

  /**
   * 3.生成充值订单
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "order", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String order(@RequestBody RechargeOrderRequest request) {
    try {
      return walletService.operateOrder(request).toString0();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new OrderAlarm(e.getMessage()));
      throw e;
    }
  }

  /**
   * 4.发起充值
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "recharge", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String rechargeAmount(@RequestBody RechargeAmountRequest request) {
    try {
      return walletService.operateRecharge(request).toString0();
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new RechargeAlarm(e.getMessage()));
      throw e;
    }
  }

  /**
   * 5、查询用户账户信息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "query_account_wallet", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String queryAccountWallet(@RequestBody BusinessOperateRequest request) {
    return walletService.queryAccountWallet(request).toString0();
  }

  /**
   * 6、查询用户账单信息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "query_account_wallet_bill", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String queryAccountBill(@RequestBody AccountBillRequest request) {
    return walletService.queryAccountBill(request).toString0();
  }

}
