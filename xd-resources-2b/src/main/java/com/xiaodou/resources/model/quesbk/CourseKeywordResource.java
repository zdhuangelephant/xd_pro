package com.xiaodou.resources.model.quesbk;

import java.util.Date;

import com.xiaodou.resources.model.BaseEntity;

public class CourseKeywordResource extends BaseEntity {
  private Long id;

  private Long resourceId;

  private Long keywordId;

  private Short resourceType;

  private Date createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getResourceId() {
    return resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }

  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  public Short getResourceType() {
    return resourceType;
  }

  public void setResourceType(Short resourceType) {
    this.resourceType = resourceType;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
