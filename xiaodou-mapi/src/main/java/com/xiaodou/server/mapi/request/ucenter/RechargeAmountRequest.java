package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.wallet.request.wallet.RechargeAmountRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 充值余额请求pojo
 * @version 1.0
 */
public class RechargeAmountRequest extends MapiBaseRequest {

  /** billNo 支付账单编号 */
  @NotEmpty
  private Long billNo;
  /** clientType 客户端类型 */
  @NotEmpty
  @LegalValueList({"ios", "android", "web"})
  private String clientType;
  @NotEmpty
  private String payMethod;

  public String getPayMethod() {
    return payMethod;
  }

  public void setPayMethod(String payMethod) {
    this.payMethod = payMethod;
  }

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

}
