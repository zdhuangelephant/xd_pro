package com.xiaodou.mission.engine.model;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.domain.MissionModel;
import com.xiaodou.mission.domain.MissionPreConditionModel;
import com.xiaodou.mission.engine.MissionEnums;
import com.xiaodou.mission.engine.MissionEnums.MissionCondition;
import com.xiaodou.mission.engine.MissionEnums.MissionConditionType;
import com.xiaodou.mission.engine.MissionEnums.MissionJumpType;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.enums.RedBonusType;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * @name @see com.xiaodou.mission.engine.model.MissionInstance.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月8日
 * @description 任务实例
 * @version 1.0
 */
public class MissionInstance {

  public MissionInstance() {
    this.id = UUID.randomUUID().toString();
  }

  public MissionInstance(MissionModel model) {
    this.id = model.getId();
    this.module = model.getModule();
    this.courseId = model.getCourseId();
    this.chapterId = model.getChapterId();
    if (StringUtils.isNotBlank(model.getMissionType())) {
      this.missionType = MissionEnums.getEnumValue(MissionType.class, model.getMissionType());
    }
    if (StringUtils.isNotBlank(model.getPreCondList())) {
      MissionPreConditionModel preCond =
          FastJsonUtil.fromJson(model.getPreCondList(), MissionPreConditionModel.class);
      this.preCond = new MissionPreConditionInstance(preCond);
    }
    if (StringUtils.isNotBlank(model.getCondType())) {
      this.condType = MissionEnums.getEnumValue(MissionConditionType.class, model.getCondType());
    }
    if (StringUtils.isNotBlank(model.getCondition())) {
      this.condition = MissionEnums.getEnumValue(MissionCondition.class, model.getCondition());
    }
    if (StringUtils.isNotBlank(model.getScope())) {
      this.scope = MissionEnums.getEnumValue(ValueScope.class, model.getScope());
    }
    this.threshold = model.getThreshold();
    this.missionDesc = model.getMissionDesc();
    this.missionName = model.getMissionName();
    this.missionPicUrl = model.getMissionPicUrl();
    if (StringUtils.isNotBlank(model.getJumpType())) {
      this.jumpType = MissionEnums.getEnumValue(MissionJumpType.class, model.getJumpType());
    }
    if (StringUtils.isNotBlank(model.getTaskType())) {
      this.taskType = MissionEnums.getEnumValue(TaskType.class, model.getTaskType());
    }
    this.missionState = model.getMissionState();
    this.missionOrder = model.getMissionOrder();
    this.creditUpper = model.getCreditUpper();
    this.redBonusType = MissionEnums.getEnumValue(RedBonusType.class, model.getRedBonusType());
    this.expiryDate = model.getExpiryDate();
  }

  /** setting */
  private String id;
  private String module;
  private String courseId;
  private String chapterId;
  private MissionType missionType;
  private MissionPreConditionInstance preCond;
  private MissionConditionType condType;
  private MissionCondition condition;
  private ValueScope scope;
  private String threshold;

  /** show info */
  /** missionName 任务名 */
  private String missionName;
  /** missionDesc 任务描述 */
  private String missionDesc;
  /** missionPicUrl 任务图片 */
  private String missionPicUrl;
  /** jumpType 跳转类型 */
  private MissionJumpType jumpType;
  /** taskType 任务类型明细 */
  private TaskType taskType;
  /** missionState 任务状态 */
  private Short missionState;
  /** missionOrder 任务排序 */
  private Integer missionOrder;
  /** creditUpper 积分涨幅 */
  private Integer creditUpper;
  /** redBounsType 红包奖励类型 */
  private RedBonusType redBonusType = RedBonusType.Null;
  /** expiryDate 有效期 */
  private Integer expiryDate;

  public String getId() {
    return id;
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

  public MissionJumpType getJumpType() {
    return jumpType;
  }

  public void setJumpType(MissionJumpType jumpType) {
    this.jumpType = jumpType;
  }

  public TaskType getTaskType() {
    return taskType;
  }

  public void setTaskType(TaskType taskType) {
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

  public RedBonusType getRedBonusType() {
    return redBonusType;
  }

  public void setRedBonusType(RedBonusType redBonusType) {
    this.redBonusType = redBonusType;
  }

  public MissionType getMissionType() {
    return missionType;
  }

  public void setMissionType(MissionType missionType) {
    this.missionType = missionType;
  }

  public MissionPreConditionInstance getPreCond() {
    return preCond;
  }

  public void setPreCond(MissionPreConditionInstance preCond) {
    this.preCond = preCond;
  }

  public MissionConditionType getCondType() {
    return condType;
  }

  public void setCondType(MissionConditionType condType) {
    this.condType = condType;
  }

  public MissionCondition getCondition() {
    return condition;
  }

  public void setCondition(MissionCondition condition) {
    this.condition = condition;
  }

  public ValueScope getScope() {
    return scope;
  }

  public void setScope(ValueScope scope) {
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

  public boolean matchPreCond(Context context) {
    if (null == preCond) {
      return true;
    }
    return preCond.match(context);
  }

}
