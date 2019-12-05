package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 点赞request
 * 
 * @author wuyunkuo
 * 
 */
public class ForumPraiseRequest extends BaseRequest {

  /**
   * 帖子Id/itemId
   */
  @NotEmpty
  private String resourcesId;

  /**
   * 评论Id
   */
  private String commentId;

  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

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
