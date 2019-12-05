package com.xiaodou.logmonitor.domain;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("project_model")
public class ProjectModel extends MongoBaseModel {
  /** id 主键ID */
  @Pk
  private String id;
  /** projectName 项目名称 */
  private String projectName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

}
