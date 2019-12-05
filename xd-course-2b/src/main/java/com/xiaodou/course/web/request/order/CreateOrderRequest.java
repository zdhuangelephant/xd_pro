package com.xiaodou.course.web.request.order;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.course.web.request.order.CreateOrderRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月31日
 * @description 下单请求pojo
 * @version 1.0
 */
public class CreateOrderRequest extends BaseRequest {

  /** productId 产品ID */
  @NotEmpty
  private Long productId;
  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;
  @NotEmpty
  private String clientType;
  @NotEmpty
  private String deviceId;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
