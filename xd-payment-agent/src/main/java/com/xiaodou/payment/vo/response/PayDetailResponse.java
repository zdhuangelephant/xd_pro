package com.xiaodou.payment.vo.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaodou.payment.vo.response.inner.MixPaymentTransOperationInfo;

/**
 * 交易详情
 *
 * Date: 2014/10/22
 * Time: 11:24
 *
 * @author Tian.Dong
 */
public class PayDetailResponse extends ResponseBase {
  @JSONField(name = "transOperationInfoList")
  private List<MixPaymentTransOperationInfo> mixPaymentTransOperationInfoList;

  public List<MixPaymentTransOperationInfo> getMixPaymentTransOperationInfoList() {
    return mixPaymentTransOperationInfoList;
  }

  public void setMixPaymentTransOperationInfoList(List<MixPaymentTransOperationInfo> mixPaymentTransOperationInfoList) {
    this.mixPaymentTransOperationInfoList = mixPaymentTransOperationInfoList;
  }

  @Override
  public String toString() {
    return "PayDetailResponse{" +
            "mixPaymentTransOperationInfoList=" + mixPaymentTransOperationInfoList +
            '}';
  }
}