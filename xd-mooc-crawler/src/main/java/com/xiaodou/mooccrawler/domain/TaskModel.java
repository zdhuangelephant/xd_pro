package com.xiaodou.mooccrawler.domain;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("mooc_task")
public class TaskModel extends MongoBaseModel {

  @Pk
  private String id;
  private String tid;
  private String baseUrl;
  private String courseName;
  private String courseId;
  private Boolean courseInfoFinish = false;
  private Boolean resourceInfoFinish = false;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public Boolean getCourseInfoFinish() {
    return courseInfoFinish;
  }

  public void setCourseInfoFinish(Boolean courseInfoFinish) {
    this.courseInfoFinish = courseInfoFinish;
  }

  public Boolean getResourceInfoFinish() {
    return resourceInfoFinish;
  }

  public void setResourceInfoFinish(Boolean resourceInfoFinish) {
    this.resourceInfoFinish = resourceInfoFinish;
  }

}
