package com.xiaodou.mission.domain;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.model.MissionInstance;

/**
 * @name @see com.xiaodou.mission.domain.MissionModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务模型
 * @version 1.0
 */
public class MissionModel {

  /** 默认构造方法 */
  public MissionModel() {}

  /**
   * 带参构造方法
   * 
   * @param instance 任务实体模型
   */
  public MissionModel(MissionInstance instance) {
    if (StringUtils.isNotBlank(instance.getId())) {
      this.id = instance.getId();
    }
    this.module = instance.getModule();
    this.courseId = instance.getCourseId();
    this.chapterId = instance.getChapterId();
    if (null != instance.getMissionType()) {
      this.missionType = instance.getMissionType().toString();
    }
    if (null != instance.getPreCond()) {
      this.preCondList = FastJsonUtil.toJson(new MissionPreConditionModel(instance.getPreCond()));
    }
    if (null != instance.getCondType()) {
      this.condType = instance.getCondType().toString();
    }
    if (null != instance.getCondition()) {
      this.condition = instance.getCondition().toString();
    }
    if (null != instance.getScope()) {
      this.scope = instance.getScope().toString();
    }
    this.threshold = instance.getThreshold();
    this.missionDesc = instance.getMissionDesc();
    this.missionName = instance.getMissionName();
    this.missionPicUrl = instance.getMissionPicUrl();
    if (null != instance.getJumpType()) {
      this.jumpType = instance.getJumpType().toString();
    }
    if (null != instance.getTaskType()) {
      this.taskType = instance.getTaskType().toString();
    }
    this.missionState = instance.getMissionState();
    this.missionOrder = instance.getMissionOrder();
    this.creditUpper = instance.getCreditUpper();
    if (null != instance.getRedBonusType()) {
      this.redBonusType = instance.getRedBonusType().toString();
    }
    this.expiryDate = instance.getExpiryDate();
  }

  /** id 主键ID */
  private String id = UUID.randomUUID().toString();
  /** module 所属模块 */
  private String module;
  /** courseId 课程ID */
  private String courseId;
  /** chapterId 章ID */
  private String chapterId;
  /** missionType 任务类型 */
  private String missionType;
  /** preCondList 前置条件列表 */
  private String preCondList;
  /** condType 条件类型 */
  private String condType;
  /** condition 条件 */
  private String condition;
  /** scope 取值范围 */
  private String scope;
  /** threshold 取值范围阀值 */
  private String threshold;
  /** missionName 任务名 */
  private String missionName;
  /** missionDesc 任务描述 */
  private String missionDesc;
  /** missionPicUrl 任务图片 */
  private String missionPicUrl;
  /** jumpType 跳转类型 */
  private String jumpType;
  /** taskType 任务类型明细 */
  private String taskType;
  /** missionState 任务状态 */
  private Short missionState;
  /** missionOrder 任务顺序 */
  private Integer missionOrder;
  /** creditUpper 积分涨幅 */
  private Integer creditUpper;
  /** redBounsType 红包奖励类型 */
  private String redBonusType;
  /** expiryDate 有效期 */
  private Integer expiryDate;

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

  public String getMissionPicUrl() {
    return missionPicUrl;
  }

  public void setMissionPicUrl(String missionPicUrl) {
    this.missionPicUrl = missionPicUrl;
  }

  public String getJumpType() {
    return jumpType;
  }

  public void setJumpType(String jumpType) {
    this.jumpType = jumpType;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public Short getMissionState() {
    return missionState;
  }

  public void setMissionState(Short missionState) {
    this.missionState = missionState;
  }

  public Integer getMissionOrder() {
    return missionOrder;
  }

  public void setMissionOrder(Integer missionOrder) {
    this.missionOrder = missionOrder;
  }

  public Integer getCreditUpper() {
    return creditUpper;
  }

  public void setCreditUpper(Integer creditUpper) {
    this.creditUpper = creditUpper;
  }

  public String getRedBonusType() {
    return redBonusType;
  }

  public void setRedBonusType(String redBonusType) {
    this.redBonusType = redBonusType;
  }

  public String getMissionType() {
    return missionType;
  }

  public void setMissionType(String missionType) {
    this.missionType = missionType;
  }

  public String getPreCondList() {
    return preCondList;
  }

  public void setPreCondList(String preCondList) {
    this.preCondList = preCondList;
  }

  public String getCondType() {
    return condType;
  }

  public void setCondType(String condType) {
    this.condType = condType;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

  public Integer getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Integer expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public boolean equals(Object obj) {
    return StringUtils.isNotBlank(id) && null != obj && obj instanceof MissionModel
        && this.id.equals(((MissionModel) obj).getId());
  }

}
