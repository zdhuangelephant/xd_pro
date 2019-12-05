package com.xiaodou.server.mapi.request;

import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复评论
 * 
 * @author bing.cheng
 * 
 */
public class ReplyCommentsRequest extends MapiBaseRequest {
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;

  /** chapterId 章ID */
  @NotEmpty
  private String chapterId;

  /** itemId 节ID */
  @NotEmpty
  private String itemId;

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

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

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

  public String getClientIp() {
    return UserTokenWrapper.getWrapper().getHeadParam().getClientIp();
  }

}
