package com.xiaodou.resources.model.forum;

import java.util.Date;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 话题分类model
 * 
 * @author bing.cheng
 * 
 */
public class ForumCategoryModel extends ForumBaseModel {

  /** 数据库自增id */
  private Long id;

  /** 内容 */
  private String content;

  /** 内容 */
  private String images;

  /** shortName 简称 */
  private String shortName;

  /** 题目 */
  private String title;

  /** 概括 */
  private String outline;

  /** 话题总数目 */
  private Integer forumNumber;

  /** 参与人总数 */
  private Integer peopleNumber;

  /** 标签-扩展字段 */
  private Integer tag;

  /** 创建时间 */
  private Date createTime;
  
  private String showOrder;

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline == null ? null : outline.trim();
  }

  public Integer getForumNumber() {
    return forumNumber;
  }

  public void setForumNumber(Integer forumNumber) {
    this.forumNumber = forumNumber;
  }

  public Integer getPeopleNumber() {
    return peopleNumber;
  }

  public void setPeopleNumber(Integer peopleNumber) {
    this.peopleNumber = peopleNumber;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
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

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

public String getShowOrder() {
	return showOrder;
}

public void setShowOrder(String showOrder) {
	this.showOrder = showOrder;
}


}
