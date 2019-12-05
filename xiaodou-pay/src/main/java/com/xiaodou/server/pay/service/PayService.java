package com.xiaodou.server.pay.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.constant.PayConstant;
import com.xiaodou.server.pay.enums.BusinessStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.model.PayToken;
import com.xiaodou.server.pay.payplatform.PlatformFilter;
import com.xiaodou.server.pay.payplatform.PlatformHelper;
import com.xiaodou.server.pay.payplatform.ca.CaPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.request.TokenRequest;
import com.xiaodou.server.pay.request.inner.MixPaymentCa;
import com.xiaodou.server.pay.request.inner.MixPaymentCc;
import com.xiaodou.server.pay.request.inner.MixPaymentDc;
import com.xiaodou.server.pay.response.PayResponse;
import com.xiaodou.server.pay.response.TokenResponse;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.server.pay.service.queue.QueueService;
import com.xiaodou.server.pay.util.IDGenerator;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;
import com.xiaodou.server.pay.vo.CheckTokenResult;
import com.xiaodou.server.pay.vo.alarm.TokenAlarm;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;

@Service("payService")
public class PayService {
  @Resource(name = "payServiceFacade")
  IPayServiceFacade payServiceFacade;
  @Resource(name = "queueService")
  QueueService queueService;

  public PayResponse pay(PayRequest pojo) {
    PayResponse response = new PayResponse(ResultType.SUCCESS);
    // check token
    CheckTokenResult checkResult =
        checkToken(pojo.getBusinessType(), pojo.getTradeNo(), pojo.getOrderId());
    if (!checkResult.isCheckedOk()) return new PayResponse(checkResult.getResultType());
    PayRecord record = payServiceFacade.queryPayRecordByTradeNo4Update(pojo.getTradeNo());
    if (null != record && PayConstant.FIRST_PAYID.equals(record.getPayType())) {
      fillResponse(response, record);
      return response;
    }
    record = fillPayOrder(pojo);
    PlatformFilter filter = PlatformHelper.buildFilter(pojo);
    try {
      if (null != filter.getCaPlatform()) {
        CaPayOrderResponse caResponse = filter.getCaPlatform().payOrder(pojo);
        if (!caResponse.getRetcode().equals("0")) {
          response.setRetcode(caResponse.getRetcode());
          response.setRetdesc(caResponse.getRetdesc());
          return response;
        }
        record.setCaAmt(caResponse.getTotalAmount());
        record.addRealPayAmt(caResponse.getTotalAmount());
        record.setPayStatus(caResponse.getPayStatus());
        record.setPayResult(caResponse.getPayInfo());
        response.setCaInfo(caResponse);
        payServiceFacade.insertPayRecord(record);
        queueService.callBackBusiness(new CallbackBusinessPojo(record));
      } else if (null != filter.getDcPlatform()) {
        DcPayOrderResponse dcResponse = filter.getDcPlatform().payOrder(pojo);
        record.setDcPayUrl(dcResponse.getPayUrl());
        record.setDcTradeNo(dcResponse.getThirdPlatformTradeNo());
        record.setPayStatus(dcResponse.getPayStatus());
        record.setDcAmt(dcResponse.getTotalAmount());
        record.addRealPayAmt(dcResponse.getTotalAmount());
        record.setPayStatus(dcResponse.getPayStatus());
        record.setPayResult(dcResponse.getPayInfo());
        payServiceFacade.insertPayRecord(record);
        response.setDcInfo(dcResponse);
      } else {
        LoggerUtil.alarmInfo(new TokenAlarm("未找到支付平台." + pojo.getMixpaymentType().toString()));
        response = new PayResponse(ResultType.SYSFAIL);
        response.appendRetdesc("#未找到支付平台");
      }
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new TokenAlarm(e.getMessage()));
      LoggerUtil.error("支付失败", e);
      response = new PayResponse(ResultType.SYSFAIL);
      response.setRetdesc(e.getMessage());
    }
    return response;
  }

  private void fillResponse(PayResponse response, PayRecord record) {
    response.setPayStatus(record.getPayStatus().toString());
    response.setPayInfo(record.getPayResult());
    response.setPayUrl(record.getDcPayUrl());
  }

  private CheckTokenResult checkToken(Integer businessType, String tradeNo, String orderId) {
    CheckTokenResult result = new CheckTokenResult();
    IQueryParam param = new QueryParam();
    param.addInput("tradeNo", tradeNo);
    param.addOutputs(CommUtil.getAllField(PayToken.class));
    Page<PayToken> tokenList = payServiceFacade.queryPayToken(param);
    if (null != tokenList && null != tokenList.getResult() && tokenList.getResult().size() > 0) {
      PayToken token = tokenList.getResult().get(0);
      if (!businessType.equals(token.getBusinessType()))
        result.setResultType(PaymentResType.UnMatchedBusinessType);
      if (StringUtils.isBlank(token.getOutTradeNo())
          || PayConstant.DEFAULT_OUT_TRADE_NO.equals(token.getOutTradeNo())) {
        token.setOutTradeNo(orderId);
        payServiceFacade.updatePayToken(token);
      } else if (!orderId.equals(token.getOutTradeNo())) {
        result.setResultType(PaymentResType.UnMatchedOutTradeNo);
      }
      return result;
    }
    result.setResultType(PaymentResType.TokenUnValable);
    return result;
  }

  public TokenResponse token(TokenRequest pojo) {
    TokenResponse response = new TokenResponse(ResultType.SUCCESS);
    PayToken token = fillAndInsertToken(pojo);
    response.setTradeNo(token.getTradeNo());
    return response;
  }

  private PayToken fillAndInsertToken(TokenRequest pojo) {
    PayToken token = new PayToken();
    token.setId(IDGenerator.getSeqID());
    token.setBusinessType(pojo.getBusinessType());
    if (StringUtils.isNotBlank(pojo.getOutTradeNo())) token.setOutTradeNo(pojo.getOutTradeNo());
    token.setTradeNo(IDGenerator.getSeqTradeNo(pojo.getBusinessType()));
    token.setBusinessStatus(BusinessStatus.INIT_TOKEN.getCode());
    token.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return payServiceFacade.insertPayToken(token);
  }

  private PayRecord fillPayOrder(PayRequest pojo) {
    PayRecord record = new PayRecord();
    record.setId(IDGenerator.getSeqID());
    record.setPayStatus(PayConstant.FIRST_PAYID);
    record.setMerchantId(pojo.getMerchantId());
    record.setBusinessType(pojo.getBusinessType());
    record.setBusinessCallbackUrl(pojo.getNotifyUrl());
    record.setTradeNo(pojo.getTradeNo());
    record.setPayFrom(pojo.getPayFrom());
    record.setPayType(PayConstant.FIRST_PAYID);
    record.setOrderFrom(pojo.getOrderFrom());
    record.setOutTradeNo(pojo.getOrderId());
    record.setOrderAmt(pojo.getTotalAmt());
    record.setOriginalPayAmt(pojo.getTotalAmt());
    record.setCreateTime(new Timestamp(System.currentTimeMillis()));
    record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    if (null != pojo.getCc()) {
      MixPaymentCc cc = pojo.getCc();
      record.setCcAmt(cc.getCcAmt());
      record.setCcCardHolder(cc.getCardHolder());
      record.setCcCurrency(cc.getCcCurrency());
      record.setCcCustomerServiceAmt(cc.getCcCustomerServiceAmt());
      record.setCcExpireDate(cc.getExpireDate());
      record.setCcIdNo(cc.getIdNo());
      record.setCcIdType(cc.getIdType());
      record.setCcNo(cc.getCreditCardNo());
      record.setCcVerifyCode(cc.getVerifyCode());
    }
    if (null != pojo.getCa()) {
      MixPaymentCa ca = pojo.getCa();
      record.setCaAmt(ca.getCaAmt());
      record.setCaProductLine(ca.getProductLine());
      record.setCaCurrency(ca.getCaCurrency());
      record.setCaMemcardNo(ca.getMemberCardNo());
    }
    if (null != pojo.getDc()) {
      MixPaymentDc dc = pojo.getDc();
      record.setDcAmt(dc.getDcAmt());
      record.setDcBody(dc.getBody());
      record.setDcCancelUrl(dc.getCancelUrl());
      record.setDcCurrency(dc.getDcCurrency());
      record.setDcCustomerServiceAmt(dc.getDcCustomerServiceAmt());
      record.setDcExternalbankId(dc.getExternalBankId());
      record.setDcPaymentMethod(dc.getPaymentMethod());
      record.setDcPaymentProvider(dc.getPaymentProviderId());
      record.setDcReturnUrl(dc.getReturnUrl());
      record.setDcSubject(dc.getSubject());
    }
    return record;
  }

}
