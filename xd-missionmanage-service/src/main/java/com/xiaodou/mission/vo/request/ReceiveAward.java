package com.xiaodou.mission.vo.request;

/**
 * @name @see com.xiaodou.vo.task.ReceiveAward.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 通知任务系统获取奖励消息类
 * @version 1.0
 */
public class ReceiveAward {

  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** missionId 任务ID */
  private String missionId;
  /** recordId 任务记录ID */
  private String recordId;
  /** bonusId 红包ID */
  private String bonusId;

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

  public String getMissionId() {
    return missionId;
  }

  public void setMissionId(String missionId) {
    this.missionId = missionId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getBonusId() {
    return bonusId;
  }

  public void setBonusId(String bonusId) {
    this.bonusId = bonusId;
  }
}
