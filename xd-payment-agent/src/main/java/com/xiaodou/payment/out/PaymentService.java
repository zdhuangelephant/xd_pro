package com.xiaodou.payment.out;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.payment.exception.PropertiesException;
import com.xiaodou.payment.service.QueryService;
import com.xiaodou.payment.service.RefundService;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.input.ContinuePayNoticePojo;
import com.xiaodou.payment.vo.input.GetPayDetailPojo;
import com.xiaodou.payment.vo.input.GetPayStatusPojo;
import com.xiaodou.payment.vo.input.GetPayTypePojo;
import com.xiaodou.payment.vo.input.RefundPojo;
import com.xiaodou.payment.vo.request.ContinuePayNoticeRequest;
import com.xiaodou.payment.vo.request.GetPayDetailRequest;
import com.xiaodou.payment.vo.request.GetPayStatusRequest;
import com.xiaodou.payment.vo.request.GetPayTypeRequest;
import com.xiaodou.payment.vo.request.GetTokenRequest;
import com.xiaodou.payment.vo.request.RefundRequest;
import com.xiaodou.payment.vo.response.PayDetailResponse;
import com.xiaodou.payment.vo.response.PayStatusResponse;
import com.xiaodou.payment.vo.response.PayTypeResponse;
import com.xiaodou.payment.vo.response.RefundResponse;
import com.xiaodou.payment.vo.response.ResponseBase;
import com.xiaodou.payment.vo.response.TokenResponse;


/**
 * 支付对外提供的接口
 * 
 * @author Jiejun.Gao, tian.dong
 * @date 2014-03-11
 */
public class PaymentService {

  /**
   * 传productLine 获取支付流水号
   * <p/>
   * 20401 未找到productLine 20402 获取token异常 payment返回不为0 20403 获取token为null 20404 网络异常，http状态码不是200
   * 
   * @param productLine 产品线 如05
   * @return TokenResponse
   */
  public static TokenResponse getToken(String productLine) {
    return getToken(productLine, PayMerchant.getBusinessType(productLine),
        PayMerchant.getMerchantId(productLine));
  }

  public static TokenResponse getToken(String productLine, Integer businessType) {
    return getToken(productLine, businessType, PayMerchant.getMerchantId(productLine));
  }

  /**
   * 传productLine 获取支付流水号
   * <p/>
   * 20401 未找到productLine 20402 获取token异常 payment返回不为0 20403 获取token为null 20404 网络异常，http状态码不是200
   * 
   * @param productLine 产品线 如05
   * @return TokenResponse
   */
  public static TokenResponse getToken(String productLine, Integer businessType, String merchantId) {
    GetTokenRequest request = new GetTokenRequest();
    try {
      request.setBusinessType(businessType);
      request.setMerchantId(merchantId);
      String key = PayMerchant.getKey(productLine);
      return QueryService.getToken(request, key);
    } catch (PropertiesException e) {
      TokenResponse response = new TokenResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + productLine + " 配置");
      LoggerUtil.error("未找到productLine " + productLine + " 配置", e);
      return response;
    }

  }

  public static PayTypeResponse getPayType(GetPayTypePojo pojo) {
    GetPayTypeRequest request = new GetPayTypeRequest();
    try {
      request.setBusinessType(PayMerchant.getBusinessType(pojo.getProductLine()));
    } catch (PropertiesException e) {
      PayTypeResponse response = new PayTypeResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + pojo.getProductLine() + " 配置");
      return response;
    }
    request.setOrderId(pojo.getGorderId());
    request.setTradeNo(pojo.getToken());
    return QueryService.getPayType(request);
  }

  public static RefundResponse refund(RefundPojo refundPojo) {
    RefundRequest request = new RefundRequest();
    String key = null;
    try {
      request.setBusinessType(PayMerchant.getBusinessType(refundPojo.getProductLine()));
      request.setMerchantId(PayMerchant.getMerchantId(refundPojo.getProductLine()));
      key = PayMerchant.getKey(refundPojo.getProductLine());
    } catch (PropertiesException e) {
      RefundResponse response = new RefundResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + refundPojo.getProductLine() + " 配置");
      return response;
    }
    request.setSignType(PayMerchant.getSignType());
    request.setOrderId(refundPojo.getGorderId());
    request.setTradeNo(Long.parseLong(refundPojo.getToken()));
    request.setAmt(refundPojo.getAmt());
    request.setNotifyUrl(refundPojo.getNotifyUrl());
    return RefundService.refund(request, key);
  }

  public static PayDetailResponse getPayDetail(GetPayDetailPojo pojo) {
    GetPayDetailRequest request = new GetPayDetailRequest();
    try {
      request.setBusinessType(PayMerchant.getBusinessType(pojo.getProductLine()));
    } catch (PropertiesException e) {
      PayDetailResponse response = new PayDetailResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + pojo.getProductLine() + " 配置");
      return response;
    }
    request.setOrderId(pojo.getGorderId());
    return QueryService.getPayDetail(request);
  }

  public static PayStatusResponse getPayStatus(GetPayStatusPojo pojo) {
    GetPayStatusRequest request = new GetPayStatusRequest();
    try {
      request.setBusinessType(PayMerchant.getBusinessType(pojo.getProductLine()));
    } catch (PropertiesException e) {
      PayStatusResponse response = new PayStatusResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + pojo.getProductLine() + " 配置");
      return response;
    }
    request.setOrderId(pojo.getGorderId());
    request.setTradeNo(pojo.getToken());
    return QueryService.getPayStatus(request);
  }

  public static ResponseBase continuePayNotice(ContinuePayNoticePojo pojo) {
    ContinuePayNoticeRequest request = new ContinuePayNoticeRequest();
    try {
      request.setBusinessType(PayMerchant.getBusinessType(pojo.getProductLine()));
    } catch (PropertiesException e) {
      PayStatusResponse response = new PayStatusResponse();
      response.setRetcode("20000");
      response.setRetdesc("未找到productLine " + pojo.getProductLine() + " 配置");
      return response;
    }
    request.setContinuePay(pojo.getContinuePay().getCode());
    try {
      if (StringUtils.isNotBlank(pojo.getCheckInfo())) {
        request.setCheckInfo(URLEncoder.encode(pojo.getCheckInfo(), "utf-8"));
      } else {
        request.setCheckInfo("");
      }
    } catch (UnsupportedEncodingException e) {
      LoggerUtil.error("不支持的编码", e);
    }
    request.setOrderId(pojo.getGorderId());
    request.setTradeNo(pojo.getToken());
    return QueryService.continuePayNotice(request);
  }
}
