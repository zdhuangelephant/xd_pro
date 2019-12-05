package com.xiaodou.wallet.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.wallet.enums.WalletNotifyType;
import com.xiaodou.wallet.request.payment.PaymentNotifyPojo;
import com.xiaodou.wallet.service.wallet.WalletService;

@Controller
@RequestMapping("pay")
public class WalletNotifyController {

  private static final String SUCCESS = "\"success\"";
  /**
   * pmt表示,接收到就返回success 另外由于我们有了主动查的机制
   */
  private static final String EXCEPTION = "\"success\"";

  @Resource
  WalletService walletService;

  /**
   * 第一次支付
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "first_pay_notify", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  public String firstPayNotify(@RequestBody PaymentNotifyPojo pojo) {
    if (doFlow(pojo, WalletNotifyType.PayNotify.getCode())) {
      return SUCCESS;
    }
    return EXCEPTION;
  }

  private boolean doFlow(PaymentNotifyPojo pojo, int type) {
    try {
      try {
        // 1 验证参数
        if (pojo.getTradeNo() == null || pojo.getTradeNo().equals("")) {
          throw new IllegalArgumentException("[payment回调][参数不正确]" + "Trade_no：" + pojo.getTradeNo());
        }
        if (pojo.getResultStatus() == null) {
          throw new IllegalArgumentException("[payment回调][参数不正确]" + "result_status："
              + pojo.getResultStatus());
        }
        if (pojo.getResultStatus() != 0 && pojo.getResultStatus() != 1) {
          throw new IllegalArgumentException("[payment回调][参数不正确][不是0/1]" + "result_status："
              + pojo.getResultStatus());
        }
      } catch (Exception e) {
        LoggerUtil.error("[payment回调][createFirePojo异常]", e);
        return false;
      }

      try {
        if (type == WalletNotifyType.PayNotify.getCode())
          walletService.operateDorecharge(pojo);
      } catch (Exception e) {
        LoggerUtil.error("[payment回调触发状态机异常]", e);
        return false;
      }
      return true;
    } catch (Exception e) {
      LoggerUtil.error("[payment回调doFlow异常]", e);
      return false;
    }
  }
}
