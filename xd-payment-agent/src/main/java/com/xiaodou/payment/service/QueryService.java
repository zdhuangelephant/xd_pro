package com.xiaodou.payment.service;

import com.xiaodou.payment.entity.Protocol;
import com.xiaodou.payment.util.SignUtil;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.ContinuePayNoticeRequest;
import com.xiaodou.payment.vo.request.GetPayDetailRequest;
import com.xiaodou.payment.vo.request.GetPayStatusRequest;
import com.xiaodou.payment.vo.request.GetPayTypeRequest;
import com.xiaodou.payment.vo.request.GetTokenRequest;
import com.xiaodou.payment.vo.response.PayDetailResponse;
import com.xiaodou.payment.vo.response.PayStatusResponse;
import com.xiaodou.payment.vo.response.PayTypeResponse;
import com.xiaodou.payment.vo.response.ResponseBase;
import com.xiaodou.payment.vo.response.TokenResponse;

/**
 * Date: 2014/7/18 Time: 14:54
 * 
 * @author Tian.Dong
 */
public class QueryService {
  public static TokenResponse getToken(GetTokenRequest request, String key) {
    String sign = SignUtil.sign(request, key);
    request.setSign(sign);
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getTokenUrl());
    return FlowService.doFlow(request, TokenResponse.class, protocol);
  }

  public static PayTypeResponse getPayType(GetPayTypeRequest request) {
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getPayTypeUrl());
    return FlowService.doFlow(request, PayTypeResponse.class, protocol);
  }

  public static PayDetailResponse getPayDetail(GetPayDetailRequest request) {
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getPayDetailUrl());
    return FlowService.doFlow(request, PayDetailResponse.class, protocol);
  }

  public static PayStatusResponse getPayStatus(GetPayStatusRequest request) {
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getPayStatusUrl());
    return FlowService.doFlow(request, PayStatusResponse.class, protocol);
  }

  public static ResponseBase continuePayNotice(ContinuePayNoticeRequest request) {
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(PayMerchant.getContinuePayNoticeUrl());
    return FlowService.doFlow(request, ResponseBase.class, protocol);
  }
}
