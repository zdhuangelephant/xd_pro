package com.xiaodou.server.mapi.request;

import com.xiaodou.server.mapi.util.UserTokenWrapper;
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
public class CreateOrderRequest extends MapiBaseRequest {

  /** productId 课程ID */
  @NotEmpty
  private Long courseId;

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getDeviceId() {
    return UserTokenWrapper.getWrapper().getHeadParam().getDeviceId();
  }

  public String getClientType() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientType();
  }

  public String getClientIp() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientIp();
  }

}
