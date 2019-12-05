package com.xiaodou.ms.model.mission;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.ms.model.mission.UserMissionRecordModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月18日
 * @description 用户任务记录模型
 * @version 1.0
 */
public class UserMissionRecordModel {

  /** id 主键ID */
  @Column(isMajor = true)
  private Long id;
  /** module 所属模块 */
  private String module;
  /** userId 用户ID */
  private String userId;
  /** missionId 任务ID */
  private String missionId;
  /** courseId 课程ID */
  private String courseId;
  /** chapterId 章ID */
  private String chapterId;
  /** missionName 任务名 */
  private String missionName;
  /** missionDesc 任务描述 */
  private String missionDesc;
  /** missionPicurl 任务图片地址 */
  private String missionPicurl;
  /** thresholdNum 任务阈值 */
  private String thresholdNum;
  /** currentNum 当前进度 */
  private String currentNum;
  /** isReached 是否达成 */
  private Integer isReached;
  /** recordStatus 记录状态 */
  private Integer recordStatus;
  /** createTime 创建时间 */
  private Timestamp createTime;
  /** finishTime 完成时间 */
  private Timestamp finishTime;
  /** deadLine 截止时间 */
  private Timestamp deadLine;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getMissionId() {
    return missionId;
  }

  public void setMissionId(String missionId) {
    this.missionId = missionId;
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

  public String getMissionName() {
    return missionName;
  }

  public void setMissionName(String missionName) {
    this.missionName = missionName;
  }

  public String getMissionDesc() {
    return missionDesc;
  }

  public void setMissionDesc(String missionDesc) {
    this.missionDesc = missionDesc;
  }

  public String getMissionPicurl() {
    return missionPicurl;
  }

  public void setMissionPicurl(String missionPicurl) {
    this.missionPicurl = missionPicurl;
  }

  public String getThresholdNum() {
    return thresholdNum;
  }

  public void setThresholdNum(String thresholdNum) {
    this.thresholdNum = thresholdNum;
  }

  public String getCurrentNum() {
    return currentNum;
  }

  public void setCurrentNum(String currentNum) {
    this.currentNum = currentNum;
  }

  public Integer getIsReached() {
    return isReached;
  }

  public void setIsReached(Integer isReached) {
    this.isReached = isReached;
  }

  public Integer getRecordStatus() {
    return recordStatus;
  }

  public void setRecordStatus(Integer recordStatus) {
    this.recordStatus = recordStatus;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Timestamp finishTime) {
    this.finishTime = finishTime;
  }

  public Timestamp getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(Timestamp deadLine) {
    this.deadLine = deadLine;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(UserMissionRecordModel.class, "xd_user_mission_record").buildXml();
  }
}
