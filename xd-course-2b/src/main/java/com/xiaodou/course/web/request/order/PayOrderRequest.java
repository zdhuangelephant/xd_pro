package com.xiaodou.course.web.request.order;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.course.web.request.order.PayOrderRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 支付订单请求
 * @version 1.0
 */
public class PayOrderRequest extends BaseRequest {

  /** gorderId 支付订单ID */
  @NotEmpty
  private String gorderId;
  /** clientType 客户端类型 */
  @NotEmpty
  @LegalValueList({"ios", "web", "android"})
  private String clientType;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

}
