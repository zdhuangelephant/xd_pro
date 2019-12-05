package com.xiaodou.server.mapi.vo.response;

import java.util.Map;



public class Comment {
  /** id */
  private String commentId;
  /** 回帖人id */
  private Long replyId;
  /** targetUserId 目标评论人ID */
  private Long targetUserId;
  /** 目标评论id */
  private Long targetCommentId;
  /** 目标评论内容 */
  private String targetContent;
  /** 评论人 */
  private String people;
  /** 评论人头像 */
  private String portrait;
  /** gender 评论人性别 */
  private String gender;
  /** 评论内容 */
  private String content;
  /** 评论类型 */
  private String type;
  /** 评论时间 */
  private String time;

  public void setReplyPeople(Map<String, Object> userInfo) {
    if (null != userInfo.get("nickName")) this.people = userInfo.get("nickName").toString();
    if (null != userInfo.get("portrait")) this.portrait = userInfo.get("portrait").toString();
    if (null != userInfo.get("gender")) this.gender = userInfo.get("gender").toString();
  }

  public void setTargetPeople(Map<String, Object> userInfo) {
    if (null != userInfo.get("nickName"))
      this.targetContent =
          targetContent.replaceAll(":targetUser:", userInfo.get("nickName").toString());
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPeople() {
    return people;
  }

  public void setPeople(String people) {
    this.people = people;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
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
