package com.xiaodou.mooccrawler.domain;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("mooc_resource")
public class ResourceInfo extends MongoBaseModel {
  @Pk
  private String id;
  private Integer index;
  private String cid;
  private String courseId;
  private String chapterId;
  private String resourceName;
  private String type;
  private String resourceUrl;
  private String trueUrl;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Integer getIndex() {
    return index;
  }
  public void setIndex(Integer index) {
    this.index = index;
  }
  public String getCid() {
    return cid;
  }
  public void setCid(String cid) {
    this.cid = cid;
  }
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
  public String getResourceName() {
    return resourceName;
  }
  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getResourceUrl() {
    return resourceUrl;
  }
  public void setResourceUrl(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }
public String getTrueUrl() {
	return trueUrl;
}
public void setTrueUrl(String trueUrl) {
	this.trueUrl = trueUrl;
}
  
}
