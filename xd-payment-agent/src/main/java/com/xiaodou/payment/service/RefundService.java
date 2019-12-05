package com.xiaodou.payment.service;

import java.text.DecimalFormat;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.constant.PaymentConfig;
import com.xiaodou.payment.entity.Protocol;
import com.xiaodou.payment.util.SignUtil;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.RefundInfo;
import com.xiaodou.payment.vo.request.RefundRequest;
import com.xiaodou.payment.vo.response.RefundResponse;

public class RefundService {

  private static DecimalFormat dFormat = new DecimalFormat("#0.00");

  /**
   * 退款 旧代码 不建议使用 使用 RefundResponse refund(RefundRequest request,String key)
   * 
   * @param productLine
   * @param preToken
   * @param orderId
   * @param token
   * @param order
   * @return
   * @throws Exception 
   * @deprecated
   */
  @Deprecated
  public static RefundResponse refund(String productLine, String preToken, String orderId,
      String token, OrderInfo order) throws Exception {
    long start = System.currentTimeMillis();
    StringBuilder sBuilder = new StringBuilder();
    sBuilder.append("amt=").append(dFormat.format(order.getTotalAmt())).append("&business_type=")
        .append(PayMerchant.getBusinessType(productLine)).append("&merchant_id=")
        .append(PayMerchant.getMerchantId(productLine)).append("&notify_url=")
        .append(order.getNotifyUrl()).append("&operation_id=").append(PaymentConfig.REFUNDID)
        .append("&order_id=").append(orderId).append("&pre_trade_no=").append(preToken)
        .append("&sign_type=").append(PayMerchant.getSignType()).append("&trade_no=").append(token)
        .append("&key=").append(PayMerchant.getKey(productLine));
    String signStr = CommUtil.HEXAndMd5(sBuilder.toString()).toUpperCase();
    RefundInfo refundInfo = new RefundInfo();
    refundInfo.setAmt(order.getTotalAmt());
    refundInfo.setBusiness_type(PayMerchant.getBusinessType(productLine));
    refundInfo.setMerchant_id(PayMerchant.getMerchantId(productLine));
    refundInfo.setNotify_url(order.getNotifyUrl());
    refundInfo.setOperation_id(PaymentConfig.REFUNDID);
    refundInfo.setOrder_id(orderId);
    refundInfo.setPre_trade_no(preToken);
    refundInfo.setSign(signStr);
    refundInfo.setSign_type(PayMerchant.getSignType());
    refundInfo.setTrade_no(token);

    String refundInfoJson = FastJsonUtil.toJson(refundInfo);
    HttpUtil httpUtil = HttpUtil.getInstance();
    HttpMethod postMethod =
        HttpMethodUtil.getPostMethod(PayMerchant.getRefundUrl(), "application/json", "utf-8",
            refundInfoJson);
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    if (!result.isResultOk()) {
      // TODO 报警
      return null;
    }
    String responseStr = result.getContent();
    long end = System.currentTimeMillis();
    InOutEntity entity = new InOutEntity();
    entity.setParams(refundInfoJson);
    entity.setResponseInfo(responseStr);
    entity.setUseTime(end - start);
    LoggerUtil.inOutInfo(entity);
    if (StringUtils.isBlank(responseStr)) {
      return null;
    }
    RefundResponse refundResponse = FastJsonUtil.fromJson(responseStr, RefundResponse.class);
    return refundResponse;
  }

  public static RefundResponse refund(RefundRequest request, String key) {
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);

    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getRefundUrl());
    return FlowService.doFlow(request, RefundResponse.class, protocol);
  }
}
