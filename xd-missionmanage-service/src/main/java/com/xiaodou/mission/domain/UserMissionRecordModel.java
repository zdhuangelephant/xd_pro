package com.xiaodou.mission.domain;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;
import com.xiaodou.mission.vo.model.UserMissionRecordHolder;

/**
 * @name @see com.xiaodou.mission.domain.UserMissionRecordModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 用户任务数据模型
 * @version 1.0
 */
public class UserMissionRecordModel {

  /** id 主键ID */
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
  private Integer isReached = MissionConstant.TASK_STATUS_UNREACH;
  /** recordStatus 记录状态 */
  private Integer recordStatus;
  /** createTime 创建时间 */
  private Timestamp createTime = new Timestamp(System.currentTimeMillis());
  /** finishTime 完成时间 */
  private Timestamp finishTime = createTime;
  /** deadLine 截止时间 */
  private Timestamp deadLine = createTime;
  /** status 记录状态 */
  private Integer status = MissionConstant.MISSION_RECORD_STATUS_MODIFY;
  /** mission 任务实例 */
  private MissionInstance mission;
  /** holder 用户任务记录管理器 */
  private UserMissionRecordHolder holder;

  /** 默认构造方法 */
  public UserMissionRecordModel() {}

  /**
   * 带参构造方法
   * 
   * @param coreParam 用户核心参数
   */
  public UserMissionRecordModel(UserCollectDataInstance coreParam) {
    this.module = coreParam.getModule();
    this.userId = coreParam.getUserId();
    this.status = MissionConstant.MISSION_RECORD_STATUS_CREATE;
  }

  /**
   * 带参构造方法
   * 
   * @param missionModel 级联模型-用户任务记录
   */
  public UserMissionRecordModel(CascadeMissionRecordModel missionModel) {
    this.id = missionModel.getRecordId();
    this.module = missionModel.getModule();
    this.userId = missionModel.getUserId();
    this.missionId = missionModel.getId();
    this.courseId = missionModel.getCourseId();
    this.chapterId = missionModel.getChapterId();
    this.missionName = missionModel.getMissionName();
    this.missionDesc = missionModel.getMissionDesc();
    this.missionPicurl = missionModel.getMissionPicUrl();
    this.thresholdNum = missionModel.getThreshold();
    this.isReached = missionModel.getIsReached();
    this.createTime = new Timestamp(System.currentTimeMillis());
    this.recordStatus = missionModel.getRecordStatus();
    setMission(new MissionInstance(missionModel));
  }

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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public MissionInstance getMission() {
    return mission;
  }

  public UserMissionRecordHolder getHolder() {
    return holder;
  }

  public void setHolder(UserMissionRecordHolder holder) {
    this.holder = holder;
  }

  public void setMission(MissionInstance mission) {
    this.mission = mission;
    if (null != mission) {
      if (StringUtils.isBlank(this.missionId)) {
        this.missionId = mission.getId();
      }
      if (StringUtils.isBlank(this.courseId)) {
        this.courseId = mission.getCourseId();
      }
      if (StringUtils.isBlank(this.chapterId)) {
        this.chapterId = mission.getChapterId();
      }
      if (StringUtils.isBlank(this.missionName)) {
        this.missionName = mission.getMissionName();
      }
      if (StringUtils.isBlank(this.missionDesc)) {
        this.missionDesc = mission.getMissionDesc();
      }
      if (StringUtils.isBlank(this.missionPicurl)) {
        this.missionPicurl = mission.getMissionPicUrl();
      }
      if (null == this.createTime) {
        this.createTime = new Timestamp(System.currentTimeMillis());
      }
      if (null != mission.getThreshold()) {
        this.thresholdNum = mission.getThreshold();
      }
      if (null != mission.getExpiryDate()) {
        this.deadLine =
            new Timestamp(createTime.getTime() + TimeUnit.DAYS.toMillis(mission.getExpiryDate()));
        this.finishTime = deadLine;
      }
    }
  }

  public boolean currUnValid() {
    return this.getId() != null && !(MissionConstant.TASK_STATUS_UNREACH == this.getIsReached())
        && null != this.getFinishTime()
        && this.getFinishTime().before(new Timestamp(DateUtil.getTimesmorning(0)));
  }
}
