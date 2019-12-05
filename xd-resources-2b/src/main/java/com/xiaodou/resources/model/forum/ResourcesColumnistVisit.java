package com.xiaodou.resources.model.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name ResourcesColumnistVisit CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年12月27日
 * @description 资源访问记录
 * @version 1.0
 */
public class ResourcesColumnistVisit {

  /** id 主键ID */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** module 模块号 */
  @Column(canUpdate = false)
  private String module;
  /** columnistId 专栏ID */
  @Column(canUpdate = false)
  private String columnistId;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** lastVisitTime 最近访问时间 */
  @Column(betweenScope = true)
  private Timestamp lastVisitTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getColumnistId() {
    return columnistId;
  }

  public void setColumnistId(String columnistId) {
    this.columnistId = columnistId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getLastVisitTime() {
    return lastVisitTime;
  }

  public void setLastVisitTime(Timestamp lastVisitTime) {
    this.lastVisitTime = lastVisitTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(ResourcesColumnistVisit.class, "xd_resources_columnist_visit",
            "/Users/zhaodan/xiaodou/server/WorkSpace/xd-resources/src/main/resources/conf/mybatis/forum")
        .buildXml();
  }
}
