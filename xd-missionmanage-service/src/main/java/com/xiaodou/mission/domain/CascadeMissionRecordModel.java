package com.xiaodou.mission.domain;

import java.sql.Timestamp;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.vo.request.TaskListRequest;

/**
 * @name @see com.xiaodou.mission.domain.CascadeMissionRecordModel.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月17日
 * @description 级联任务模型-任务记录模型
 * @version 1.0
 */
public class CascadeMissionRecordModel extends MissionModel {

  /** userId 用户ID */
  private String userId;
  /** recordId 记录ID */
  private Long recordId;
  /** majorId 专业ID */
  private String majorId;
  /** currentNum 当前进度 */
  private String currentNum = MissionConstant.INTEGER_ZERO.toString();
  /** isReached 是否达成 */
  private Integer isReached = MissionConstant.FALSE;
  /** recordStatus 记录状态 */
  private Integer recordStatus;
  /** createTime 创建时间 */
  private Timestamp createTime;
  /** finishTime 完成时间 */
  private Timestamp finishTime;
  /** deadLine 截止时间 */
  private Timestamp deadLine;
  /** needBuildRecord 需要插入新记录 */
  private Boolean needBuildRecord = Boolean.TRUE;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
    this.needBuildRecord = false;
  }

  public String getMajorId() {
    if (MissionType.archieve.name().equals(getMissionType())
        || TaskType.statics.name().equals(getTaskType())) {
      return MissionConstant.COMMON_MAJOR_ID;
    }
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
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

  public Boolean getNeedBuildRecord() {
    return needBuildRecord;
  }

  public void setNeedBuildRecord(Boolean needBuildRecord) {
    this.needBuildRecord = needBuildRecord;
  }

  public void init(TaskListRequest request) {
    if (StringUtils.isBlank(this.userId)) {
      this.userId = request.getUid();
    }
    if (StringUtils.isBlank(getModule())) {
      setModule(request.getModule());
    }
  }

  public boolean currUnValid() {
    return MissionConstant.TASK_STATUS_UNDISPLAY == this.getIsReached()
        || this.getRecordId() != null
        && !(MissionConstant.TASK_STATUS_UNREACH == this.getIsReached())
        && null != this.getFinishTime()
        && this.getFinishTime().before(new Timestamp(DateUtil.getTimesmorning(0)));
  }

  public boolean isComplete() {
    return this.recordId != null && this.isReached != MissionConstant.TASK_STATUS_UNREACH;
  }
}
