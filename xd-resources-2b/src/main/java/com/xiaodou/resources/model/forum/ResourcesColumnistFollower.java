package com.xiaodou.resources.model.forum;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name ResourcesColumnistFollower CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 资源专栏关注者模型
 * @version 1.0
 */
public class ResourcesColumnistFollower {

  /** id 主键ID */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** module 模块 */
  @Column(canUpdate = false)
  private String module;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** columnistId 专栏ID */
  @Column(canUpdate = false)
  private String columnistId;
  /** followTime 关注时间 */
  @Column(betweenScope = true, canUpdate = false)
  private Timestamp followTime;

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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getColumnistId() {
    return columnistId;
  }

  public void setColumnistId(String columnistId) {
    this.columnistId = columnistId;
  }

  public Timestamp getFollowTime() {
    return followTime;
  }

  public void setFollowTime(Timestamp followTime) {
    this.followTime = followTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(ResourcesColumnist.class, "xd_resources_columnist",
            "D:\\MyWorkSpace\\MyEclipse8.5\\xd-resource\\src\\main\\resources\\conf\\mybatis\\forum")
        .buildXml();
  }
}
