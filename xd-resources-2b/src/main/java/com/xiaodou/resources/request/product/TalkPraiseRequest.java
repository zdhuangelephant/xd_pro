package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class TalkPraiseRequest extends BaseRequest {

  /**
   * 资源Id（讨论）
   */
  @NotEmpty
  private String resourceId;

  /**
   * 评论Id
   */
  private String commentId;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

}
