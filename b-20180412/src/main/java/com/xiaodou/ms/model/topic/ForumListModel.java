package com.xiaodou.ms.model.topic;

import java.sql.Timestamp;

/**
 * @Description: 话题Model
 * @author tao.huang
 * Created by zyp on 15/6/26.
 */
public class ForumListModel {

  private Integer id;
  private Integer status;

  private Integer digest;

  private Integer top;

  private Integer recommend;

  private String title;

  private String content;

  private String outline;

  private String images;

  private Integer categoryId;

  private Integer publisherId;

  private Integer repliesNumber;

  private Integer praiseNumber;

  private Integer assign;

  private Integer tag;

  private String operator;

  private String operatorip;

  private Timestamp createTime;

  private Timestamp updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDigest() {
    return digest;
  }

  public void setDigest(Integer digest) {
    this.digest = digest;
  }

  public Integer getTop() {
    return top;
  }

  public void setTop(Integer top) {
    this.top = top;
  }

  public Integer getRecommend() {
    return recommend;
  }

  public void setRecommend(Integer recommend) {
    this.recommend = recommend;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public Integer getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Integer publisherId) {
    this.publisherId = publisherId;
  }

  public Integer getRepliesNumber() {
    return repliesNumber;
  }

  public void setRepliesNumber(Integer repliesNumber) {
    this.repliesNumber = repliesNumber;
  }

  public Integer getPraiseNumber() {
    return praiseNumber;
  }

  public void setPraiseNumber(Integer praiseNumber) {
    this.praiseNumber = praiseNumber;
  }

  public Integer getAssign() {
    return assign;
  }

  public void setAssign(Integer assign) {
    this.assign = assign;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
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

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }
}
