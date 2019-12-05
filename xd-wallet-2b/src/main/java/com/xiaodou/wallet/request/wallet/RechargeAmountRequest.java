package com.xiaodou.wallet.request.wallet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.wallet.request.BaseRequest;

/**
 * @name @see com.xiaodou.wallet.request.wallet.RechargeAmountRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 充值余额请求pojo
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeAmountRequest extends BaseRequest {

  /** billNo 支付账单编号 */
  @NotEmpty
  private Long billNo;
  /** clientType 设备类型 */
  @NotEmpty
  private String clientType;
  /** paymentProvider 支付类型 */
  @NotEmpty
  private Integer paymentProvider;

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public Long getBillNo() {
    return billNo;
  }

  public void setBillNo(Long billNo) {
    this.billNo = billNo;
  }

  public Integer getPaymentProvider() {
    return paymentProvider;
  }

  public void setPaymentProvider(Integer paymentProvider) {
    this.paymentProvider = paymentProvider;
  }

}
