package com.xiaodou.course.web.request.product;

import org.springframework.validation.Errors;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复评论
 * 
 * @author bing.cheng
 * 
 */
public class ReplyCommentsRequest extends BaseRequest {
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

  /** clientIp 操作者IP */
  @NotEmpty
  private String clientIp;

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
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }
  
  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if(commentContent.length() > 300)
      errors.rejectValue("commentContent", null, "commentContent is too long.");
    return errors;
  }

}
