package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.wallet.request.wallet.RechargeOrderRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月3日
 * @description 充值余额订单请求
 * @version 1.0
 */
public class RechargeOrderRequest extends MapiBaseRequest {

  /** goodId 充值商品ID */
  @NotEmpty
  private Long goodId;

  public Long getGoodId() {
    return goodId;
  }

  public void setGoodId(Long goodId) {
    this.goodId = goodId;
  }

}
