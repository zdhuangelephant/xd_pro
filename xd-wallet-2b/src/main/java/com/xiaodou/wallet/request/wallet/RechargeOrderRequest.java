package com.xiaodou.wallet.request.wallet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.wallet.request.BusinessOperateRequest;

/**
 * @name @see com.xiaodou.wallet.request.wallet.RechargeOrderRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 充值余额订单请求
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeOrderRequest extends BusinessOperateRequest {
  /** goodName 充值产品名称 */
  @NotEmpty
  private String goodName;
  /** goodId 充值产品价格 */
  @NotEmpty
  private Double goodPrice;
  /** goodCount 充值产品数量 */
  @NotEmpty
  private Double goodCount;

  public String getGoodName() {
    return goodName;
  }

  public void setGoodName(String goodName) {
    this.goodName = goodName;
  }

  public Double getGoodPrice() {
    return goodPrice;
  }

  public void setGoodPrice(Double goodPrice) {
    this.goodPrice = goodPrice;
  }

  public Double getGoodCount() {
    return goodCount;
  }

  public void setGoodCount(Double goodCount) {
    this.goodCount = goodCount;
  }
}
