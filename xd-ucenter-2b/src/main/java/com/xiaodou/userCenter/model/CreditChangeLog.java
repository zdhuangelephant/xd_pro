package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class CreditChangeLog {

  @Column(isMajor = true)
  private String id;
  private String customTag;
  private String userId;
  private String operateDesc;
  private String count;
  @Column(betweenScope = true)
  private Timestamp createTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCustomTag() {
    return customTag;
  }

  public void setCustomTag(String customTag) {
    this.customTag = customTag;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOperateDesc() {
    return operateDesc;
  }

  public void setOperateDesc(String operateDesc) {
    this.operateDesc = operateDesc;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CreditChangeLog.class, "xd_user_credit_change_log").buildXml();
  }
}
