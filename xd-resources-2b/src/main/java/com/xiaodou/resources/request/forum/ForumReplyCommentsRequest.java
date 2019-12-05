package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复评论
 * 
 * @author bing.cheng
 * 
 */
public class ForumReplyCommentsRequest extends BaseRequest {
  /**
   * 话题Id
   */
  @NotEmpty
  private Long resourcesId;
  /**
   * 评论内容
   */
  @NotEmpty
  private String commentContent;

  /**
   * 评论id
   */
  @NotEmpty
  private String commentId;

  /** clientIp 设备IP */
  @NotEmpty
  private String clientIp;

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }


  public Long getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(Long resourcesId) {
    this.resourcesId = resourcesId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

}
