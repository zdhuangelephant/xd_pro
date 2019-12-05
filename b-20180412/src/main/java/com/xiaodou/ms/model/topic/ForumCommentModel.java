package com.xiaodou.ms.model.topic;

import java.sql.Timestamp;

/**
 * @Description: 回帖Model
 * @author tao.huang
 * 
 */
public class ForumCommentModel {

  private String id;
  private Integer status;
  private Integer forumId;
  private String nickName;
  private String content;
  private String images;
  private Integer replyId;
  private Integer targeId;
  private String targeContent;
  private Integer targeCommentId;
  private Integer praiseNumber;
  private Integer tag;
  private Timestamp createTime;
  private String operator;
  private String operatorip;
  private Integer type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getForumId() {
    return forumId;
  }

  public void setForumId(Integer forumId) {
    this.forumId = forumId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public Integer getReplyId() {
    return replyId;
  }

  public void setReplyId(Integer replyId) {
    this.replyId = replyId;
  }

  public Integer getTargeId() {
    return targeId;
  }

  public void setTargeId(Integer targeId) {
    this.targeId = targeId;
  }

  public String getTargeContent() {
    return targeContent;
  }

  public void setTargeContent(String targeContent) {
    this.targeContent = targeContent;
  }

  public Integer getTargeCommentId() {
    return targeCommentId;
  }

  public void setTargeCommentId(Integer targeCommentId) {
    this.targeCommentId = targeCommentId;
  }

  public Integer getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(Integer praiseNumber) {
    this.praiseNumber = praiseNumber;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperatorip() {
    return operatorip;
  }

  public void setOperatorip(String operatorip) {
    this.operatorip = operatorip;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


}
