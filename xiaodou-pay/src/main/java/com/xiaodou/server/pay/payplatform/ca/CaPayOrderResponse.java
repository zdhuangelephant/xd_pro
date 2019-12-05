package com.xiaodou.server.pay.payplatform.ca;

import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.payplatform.IPayOrderResponse;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.server.pay.payplatform.ca.CaPayOrderResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 现金账户支付订单结果
 * @version 1.0
 */
public class CaPayOrderResponse extends ResultInfo implements IPayOrderResponse {

  public CaPayOrderResponse(ResultType type) {
    super(type);
  }

  /** totalAmount 总金额 */
  private Double totalAmount;
  /** payStatus 支付状态 */
  private Short payStatus;
  /** payInfo 支付结果信息 */
  private String payInfo;

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Short getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(Short payStatus) {
    this.payStatus = payStatus;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }

  @Override
  public boolean isPaySuccess() {
    return null != payStatus && payStatus == PayStatus.SUCCESS.getCode();
  }

}
