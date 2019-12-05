package com.xiaodou.course.vo.product;


import java.sql.Timestamp;
import java.text.ParseException;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.model.comment.CommentModel;

public class Comment {
  /** id */
  private Long commentId;
  /** 回帖人id */
  private Long replyId;
  /** targetUserId 目标评论人ID */
  private Long targetUserId;
  /** 目标评论id */
  private Long targetCommentId;
  /** 目标评论内容 */
  private String targetContent;
  /** 评论内容 */
  private String content;
  /** 评论时间 */
  private Timestamp time;
  /** sTime 评论时间(缓存设置用) */
  private String sTime;

  public Comment(CommentModel comment) {
    if (null == comment) return;
    this.commentId = comment.getId();
    this.replyId = comment.getReplyId();
    this.content = comment.getContent();
    this.targetUserId = comment.getTargetId();
    this.targetCommentId = comment.getTargetCommentId();
    this.targetContent = comment.getTargetContent();
    this.time = comment.getCreateTime();
  }

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public Long getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTime() throws ParseException {
    if (StringUtils.isNotBlank(sTime)) return sTime;
    return DateUtil.relativeDateFormat(time);
  }

  public void setTime(String time) {
    this.sTime = time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Long getTargetCommentId() {
    return targetCommentId;
  }

  public void setTargetCommentId(Long targetCommentId) {
    this.targetCommentId = targetCommentId;
  }

  public String getTargetContent() {
    return targetContent;
  }

  public void setTargetContent(String targetContent) {
    this.targetContent = targetContent;
  }

}
