package com.xiaodou.payment.service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.constant.PaymentStatus;
import com.xiaodou.payment.entity.Protocol;
import com.xiaodou.payment.exception.CardException;
import com.xiaodou.payment.util.SignUtil;
import com.xiaodou.payment.vo.PayCreditCardInfo;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.response.CreditCardResponse;
import com.xiaodou.payment.vo.response.PayResponse;

public class PayService {
  private static DecimalFormat dFormat = new DecimalFormat("#0.00");

  /**
   * 不建议使用 建议使用public static PayResponse pay(PayRequest request)
   * 
   * @param payCreditCardInfo
   * @return
   * @throws UnsupportedEncodingException
   */
  @Deprecated
  public static PayResponse pay(PayCreditCardInfo payCreditCardInfo)
      throws UnsupportedEncodingException {
    long start = System.currentTimeMillis();
    if (null == payCreditCardInfo.getPayment_product_id()) {
      payCreditCardInfo.setPayment_product_id(0);
    }
    StringBuilder sBuilder = new StringBuilder();// 用来定义字符串的签名
    sBuilder.append("business_type=").append(payCreditCardInfo.getBusiness_type());// 业务类型
    if (StringUtils.isNotBlank(payCreditCardInfo.getCc().getCard_holder())) {
      sBuilder.append("&card_holder=").append(payCreditCardInfo.getCc().getCard_holder());// 持卡人
    }
    sBuilder.append("&cc_amt=").append(dFormat.format(payCreditCardInfo.getCc().getCc_amt()))
        // 交易金额
        .append("&cc_currency=").append(payCreditCardInfo.getCc().getCc_currency())
        // 交易币种
        .append("&cc_customer_service_amt=")
        .append(dFormat.format(payCreditCardInfo.getCc().getCc_customer_service_amt()))// 手续费
        .append("&credit_card_no=").append(payCreditCardInfo.getCc().getCredit_card_no()); // 信用卡号
    if (StringUtils.isNotBlank(payCreditCardInfo.getCc().getExpire_date())) {
      sBuilder.append("&expire_date=").append(payCreditCardInfo.getCc().getExpire_date()); // 有效期
    }
    if (StringUtils.isNotBlank(payCreditCardInfo.getCc().getId_no())) {
      sBuilder.append("&id_no=").append(payCreditCardInfo.getCc().getId_no()); // 证件号码
    }
    sBuilder.append("&id_type=").append(payCreditCardInfo.getCc().getId_type()) // 证件类型
        .append("&merchant_id=").append(payCreditCardInfo.getMerchant_id()) // 商户
        .append("&mixpayment_type=").append(payCreditCardInfo.getMixpayment_type()) // 混合模式
        .append("&notify_url=").append(payCreditCardInfo.getNotify_url()); // 回调url
    if (StringUtils.isNotBlank(payCreditCardInfo.getOrder_from())) {
      sBuilder.append("&order_from=").append(payCreditCardInfo.getOrder_from()); // 订单来源
    }
    sBuilder.append("&order_id=").append(payCreditCardInfo.getOrder_id()) // 订单号
        .append("&pay_from=").append(payCreditCardInfo.getPay_from()) // 支付来源
        .append("&payment_product_id=").append(payCreditCardInfo.getPayment_product_id()) // 支付产品ID
        .append("&sign_type=").append(payCreditCardInfo.getSign_type()) // 签名方式
        .append("&total_amt=").append(dFormat.format(payCreditCardInfo.getTotal_amt()))// 支付订单金额
        .append("&trade_no=").append(payCreditCardInfo.getTrade_no());// 支付流水号
    if (StringUtils.isNotBlank(payCreditCardInfo.getCc().getVerify_code())) {
      sBuilder.append("&verify_code=").append(payCreditCardInfo.getCc().getVerify_code());// CVV码
    }
    // String signStr=MD5EnCode.MD5Sign(sBuilder.toString(), payMerchant.getKey());//
    String signStr = StringUtils.EMPTY;
    try {
      signStr =
          CommUtil.HEXAndMd5(sBuilder.toString() + "&key=" + payCreditCardInfo.getKey())
              .toUpperCase();
    } catch (Exception e) {
      LoggerUtil.error("请求payment签名失败", e);
    }
    payCreditCardInfo.setSign(signStr);// 签名
    String payInfoJson = FastJsonUtil.toJson(payCreditCardInfo);
    HttpUtil httpUtil = HttpUtil.getInstance();
    HttpMethod postMethod =
        HttpMethodUtil.getPostMethod(PayMerchant.getPayUrl(), "application/json", "utf-8",
            payInfoJson);
    httpUtil.setMethod(postMethod);
    HttpResult result = httpUtil.getHttpResult();
    if (!result.isResultOk()) {
      // TODO 报警
      return null;
    }
    String responseStr = result.getContent();
    if (StringUtils.isBlank(responseStr)) {
      return null;// 调用未成功记录日志
    }
    long end = System.currentTimeMillis();
    InOutEntity entity = new InOutEntity();
    entity.setParams(payInfoJson);
    entity.setResponseInfo(responseStr);
    entity.setTargetUrl(PayMerchant.getPayUrl());
    entity.setUseTime(end - start);
    LoggerUtil.inOutInfo(entity);
    PayResponse payResponse = FastJsonUtil.fromJson(responseStr, PayResponse.class);
    return payResponse;

  }


  /**
   * 获取银行卡信息 便于二次支付
   * 
   * @param token
   * @param url
   * @return
   * @throws CardException
   */
  @Deprecated
  public static CreditCardResponse getCreditCardModel(String productLine, String token, String url)
      throws CardException {
    long start = System.currentTimeMillis();
    HttpUtil httpUtil = HttpUtil.getInstance();
    String getUrl =
        url + "?business_type=" + PayMerchant.getBusinessType(productLine) + "&trade_no=" + token
            + "&merchant_id=" + PayMerchant.getMerchantId(productLine);
    GetMethod method = HttpMethodUtil.getGetMethod(getUrl);
    httpUtil.setMethod(method);
    HttpResult result = httpUtil.getHttpResult();
    if (!result.isResultOk()) {
      // TODO 报警
      return null;
    }
    String creditCardResponse = result.getContent();
    if (StringUtils.isBlank(creditCardResponse)) {
      throw new CardException("获取卡信息异常");
    } else {
      // 运行正确
      CreditCardResponse creditCard =
          (CreditCardResponse) FastJsonUtil.fromJson(creditCardResponse, CreditCardResponse.class);
      StringBuilder sb = new StringBuilder();
      sb.append("获取card 前置 token：" + token);
      if (StringUtils.isBlank(creditCard.getCard_holder())) {
        sb.append("|card holder为空");
      }
      if (StringUtils.isBlank(creditCard.getCredit_card_no())) {
        sb.append("|卡号为空");
      }
      if (StringUtils.isBlank(creditCard.getExpire_date())) {
        sb.append("|有效期为空");
      }
      if (StringUtils.isBlank(creditCard.getId_no())) {
        sb.append("|证件号为空");
      }
      if (null == creditCard.getId_type()) {
        sb.append("|证件类型为空");
      }
      if (StringUtils.isBlank(creditCard.getVerify_code())) {
        sb.append("|验证码为空");
      }
      if (null == creditCard.getPayment_product_id()) {
        sb.append("|支付产品号为空");
      }
      LoggerUtil.inOutInfo(sb.toString());

      long end = System.currentTimeMillis();
      InOutEntity entity = new InOutEntity();
      entity.setParams(token);
      entity.setTargetUrl(url);
      entity.setUseTime(end - start);
      LoggerUtil.inOutInfo(entity);
      if (PaymentStatus.REP_OKCODE.equals(creditCard.getRetcode())) {
        return creditCard;
      } else {
        throw new CardException("获取卡信息异常,状态码：" + creditCard.getRetcode());
      }

    }
  }

  public static PayResponse payWithKey(PayRequest request, String key) {
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);

    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getPayUrl());
    return FlowService.doFlow(request, PayResponse.class, protocol);
  }

  public static PayResponse payWithProductLine(PayRequest request, String productLine) {
    if (null == request.getBusinessType())
      request.setBusinessType(PayMerchant.getBusinessType(productLine));
    if (StringUtils.isBlank(request.getMerchantId()))
      request.setMerchantId(PayMerchant.getMerchantId(productLine));

    String key = PayMerchant.getKey(productLine);
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);

    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getPayUrl());
    return FlowService.doFlow(request, PayResponse.class, protocol);
  }

}
