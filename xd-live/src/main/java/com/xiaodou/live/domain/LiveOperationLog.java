package com.xiaodou.live.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name LiveOperationLog CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月29日
 * @description 操作记录模型
 * @version 1.0
 */
public class LiveOperationLog {

  /** id 主键ID */
  @Column(isMajor = true, canUpdate = false)
  private String id;
  /** liveId 直播ID */
  @Column(canUpdate = false)
  private String liveId;
  /** userId 用户ID */
  @Column(canUpdate = false)
  private String userId;
  /** userIp 用户IP */
  @Column(canUpdate = false)
  private String userIp;
  /** operation 操作行为 */
  @Column(canUpdate = false)
  private String operation;
  /** note 操作说明 */
  @Column(canUpdate = false)
  private String note;
  /** operation_time 操作时间 */
  @Column(canUpdate = false)
  private Timestamp operationTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLiveId() {
    return liveId;
  }

  public void setLiveId(String liveId) {
    this.liveId = liveId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Timestamp getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Timestamp operationTime) {
    this.operationTime = operationTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(LiveOperationLog.class, "xd_live_operation_log",
        "D:/MyWorkSpace/MyEclipse8.5/xd-live/src/main/resources/conf/mybatis").buildXml();
  }
}
