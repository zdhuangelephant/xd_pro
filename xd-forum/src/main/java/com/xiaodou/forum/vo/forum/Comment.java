package com.xiaodou.forum.vo.forum;


import java.sql.Timestamp;
import java.text.ParseException;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.util.DateUtil;

public class Comment {
  /** id */
  private String id;
  /** 图片列表 */
  private String images;
  /** 回帖人id */
  private String replyId;
  /** 目标评论id */
  private String targeId;
  /** 目标评论内容 */
  private String targeContent;
  /** 目标评论id */
  private String targeCommentId;
  /** id */
  private String tag;
  /** 话题ID */
  private String forumId;
  /** 评论目标ID */
  private String commentId;
  /** 评论人 */
  private String people;
  /** 评论人头像 */
  private String portrait;
  /** 评论内容 */
  private String content;
  /** 评论类型 */
  private String type;
  /** 评论时间 */
  private Timestamp time;
  /** sTime 评论时间(缓存设置用) */
  private String sTime;
  /** 评论赞数量 */
  private String praiseNumber;
  /** 是否点赞 */
  private String isPraise = "0";
  /** 评论的人的昵称 */
  private String nickName;

  public String getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(String praiseNumber) {
    this.praiseNumber = praiseNumber;
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

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getReplyId() {
    return replyId;
  }

  public void setReplyId(String replyId) {
    this.replyId = replyId;
  }

  public String getTargeId() {
    return targeId;
  }

  public void setTargeId(String targeId) {
    this.targeId = targeId;
  }

  public String getTargeContent() {
    return targeContent;
  }

  public void setTargeContent(String targeContent) {
    this.targeContent = targeContent;
  }

  public String getTargeCommentId() {
    return targeCommentId;
  }

  public void setTargeCommentId(String targeCommentId) {
    this.targeCommentId = targeCommentId;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getIsPraise() {
    return isPraise;
  }

  public void setIsPraise(String isPraise) {
    this.isPraise = isPraise;
  }
}
