package com.xiaodou.server.pay.service.callback;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.enums.ApplePayType;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.prop.VerifyPayProp;
import com.xiaodou.server.pay.request.callback.AppleCallbackRequest;
import com.xiaodou.server.pay.response.PayResponse;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.server.pay.service.PayService;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.server.pay.service.queue.QueueService;
import com.xiaodou.server.pay.util.HttpUtil;
import com.xiaodou.server.pay.vo.ApplePayRes;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;

@Service("appleCallBackService")
public class AppleCallBackService implements ICallbackService<AppleCallbackRequest, PayResponse> {
  @Resource(name = "payServiceFacade")
  IPayServiceFacade payServiceFacade;
  @Resource(name = "queueService")
  QueueService queueService;
  @Resource(name = "payService")
  PayService payService;

  /**
   * 支付结果回调(校验和记录)
   * 
   * @param pojo 参数pojo 说明 考虑到网络异常情况，服务器的验证应该是一个可恢复的队列，如果网络失败了，应该进行重试。
   *        与苹果的验证接口文档在这里(https://developer.apple.com
   *        /library/ios/releasenotes/General/ValidateAppStoreReceipt
   *        /Introduction.html#//apple_ref/doc/uid/TP40008267-CH104-SW3)。
   *        简单来说就是将该购买凭证用Base64编码，然后POST给苹果的验证服务器，苹果将验证结果以JSON形式返回。
   *        苹果AppStore线上的购买凭证验证地址是https://buy.itunes.apple.com/verifyReceipt ，
   *        测试的验证地址是：https://sandbox.itunes.apple.com/verifyReceipt
   */
  public PayResponse callback(AppleCallbackRequest pojo) {
    // LoggerUtil.outInInfo("生成订单入口,pojo:" + pojo.toString());
    PayResponse response = new PayResponse(ResultType.SUCCESS);
    String resultStatus = PayStatus.SUCCESS.getCode().toString();
    // 支付流程
    /* 1、接收ios端发过来的购买凭证 */
    String thirdTositenumero = pojo.getThirdTositenumero();
    // 1.1、将该购买凭证用Base64编码
    // String thirdTositenumero = Base64Utils.encode(_thirdTositenumero.getBytes());
    /* 将该凭证发送到苹果的服务器验证，并将验证结果返回给客户端 */
    String payStatus = verifyThirdTos(thirdTositenumero);
    if (PayConstant.APPLE_PAY_RETURN_STATUS_SUCCESS.equals(payStatus)) {// 返回成功
      PayRecord payRecord = payServiceFacade.queryPayRecordByTradeNo4Update(pojo.getTradeNo());
      if (null == payRecord) {
        // payServiceFacade.insertPayRecord(payRecord);
        return new PayResponse(PaymentResType.FindPayFailed);
      } else {
        payRecord.setPayStatus(PayStatus.SUCCESS.getCode());
        payRecord.setPayResult(PayStatus.SUCCESS.getStatus());
        if (!payServiceFacade.updatePayRecord(payRecord)) {
          return new PayResponse(PaymentResType.FindPayFailed);
        }
        queueService.callBackBusiness(new CallbackBusinessPojo(payRecord));
      }
    }else{
      resultStatus = PayStatus.FAIL.getCode().toString();
    }
    response.setPayStatus(resultStatus);
    return response;
  }

  @SuppressWarnings("unused")
  private Page<PayRecord> queryRecordListByCond(AppleCallbackRequest pojo) {
    /* 查询支付数据,确认订单生成凭证也在支付表中存在 */
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("tradeNo", pojo.getTradeNo());
    Page<PayRecord> payRecordList =
        payServiceFacade.queryPayRecordList(cond, CommUtil.getAllField(PayRecord.class));
    return payRecordList;
  }

  /*
   * 将该凭证发送到苹果的服务器验证，并将验证结果返回给客户端（发消息）
   */
  @SuppressWarnings("unused")
  private String _verifyThirdTos(String thirdTositenumero) {
    // 发消息
    // queueService.verifyThirdTos(thirdTositenumero);
    String res = verifyThirdTos(thirdTositenumero);
    ApplePayRes applePayRes = FastJsonUtil.fromJson(res, ApplePayRes.class);
    Short payStatus = PayStatus.FAIL.getCode();
    if (null != applePayRes) applePayRes.getStatus();
    return payStatus.toString();
  }

  /*
   * 将该凭证发送到苹果的服务器验证，并将验证结果返回给客户端
   */
  public String verifyThirdTos(String thirdTositenumero) {
    ApplePayRes applePayRes =
        HttpUtil.doflowVerifyPayTos(ApplePayType.BUY.getPayType(), "receipt-data",
            thirdTositenumero, ApplePayRes.class);
    if(null == applePayRes){
      return PayStatus.FAIL.getCode().toString();
    }
    if (VerifyPayProp.getParams("verify.pay.applePay.sandbox.verify").equals("ON")) {
      if (PayConstant.PAY_SANDBOX.equals(applePayRes.getStatus())) {
        applePayRes =
            HttpUtil.doflowVerifyPayTos(ApplePayType.SANDBOX.getPayType(), "receipt-data",
                thirdTositenumero, ApplePayRes.class);
        if(null == applePayRes){
          return PayStatus.FAIL.getCode().toString();
        }
      }
    }
    return applePayRes.getStatus();
  }

}
