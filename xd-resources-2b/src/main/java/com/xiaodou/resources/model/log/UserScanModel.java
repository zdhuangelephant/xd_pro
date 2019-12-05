package com.xiaodou.resources.model.log;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class UserScanModel {
  @Column(isMajor = true, canUpdate = false)
  private String id;
  @Column(canUpdate = false)
  private String userId;
  @Column(canUpdate = false)
  private String productId;
  @Column(canUpdate = false)
  private Timestamp scanTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Timestamp getScanTime() {
    return scanTime;
  }

  public void setScanTime(Timestamp scanTime) {
    this.scanTime = scanTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserScanModel.class, "xd_user_scan_log",
        "/Users/zhaodan/xiaodou/server/WorkSpace/xd-resources/src/main/resources/conf/mybatis/log")
        .buildXml();
  }
}
