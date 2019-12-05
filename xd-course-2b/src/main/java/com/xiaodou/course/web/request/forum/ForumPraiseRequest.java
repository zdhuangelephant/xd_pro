package com.xiaodou.course.web.request.forum;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * 点赞request
 * 
 * @author wuyunkuo
 * 
 */
@OverComeField(field = "typeCode", annotiation = AnnotationType.NotEmpty)
public class ForumPraiseRequest extends BaseRequest {
  /**
   * 帖子Id/itemId
   */
  @NotEmpty
  private String resourcesId;
  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;


  public String getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(String resourcesId) {
    this.resourcesId = resourcesId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

}
