package com.xiaodou.mission.engine.model.mission;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.Data;

import com.xiaodou.async.model.SystemMessage;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.vo.alarm.OnEventAlarm;
import com.xiaodou.mission.vo.message.AddCreditMessage;
import com.xiaodou.userCenter.request.AddCreditRequest;

/**
 * @name AbstractMissionRecord
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 任务模型
 * @version 1.0
 */
@Data
public abstract class AbstractMissionRecord implements IMissionRecord {

  /** status 记录状态 */
  private Integer status;

  @Override
  public void storeRecord(Context context) {
    if (MissionConstant.MISSION_RECORD_STATUS_CREATE == status) {
      createRecord(context);
    } else if (MissionConstant.MISSION_RECORD_STATUS_MODIFY == status) {
      updateRecord(context);
    } else {
      LoggerUtil.alarmInfo(new OnEventAlarm(String.format("位置记录状态[missionId:%s, userId:%s]",
          getId(), context.getEvent().getUserId())));
    }
    afterStore(context);
  }

  /**
   * 创建记录
   * 
   * @param context 上下文
   */
  protected abstract void createRecord(Context context);

  /**
   * 更新记录
   * 
   * @param context 上下文
   */
  public void updateRecord(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    if (MissionConstant.TRUE != getIsReached()
        && MissionConstant.DEFAULT_THROHOLD_UNRECHED.equals(getCurrentNum())) {
      return;
    }
    UserMissionRecordModel updateRecord = new UserMissionRecordModel();
    updateRecord.setId(getId());
    updateRecord.setCurrentNum(getCurrentNum());
    updateRecord.setIsReached(getIsReached());
    if (MissionConstant.TRUE == getIsReached()) {
      updateRecord.setFinishTime(new Timestamp(System.currentTimeMillis()));
    } else {
      updateRecord.setFinishTime(null);
    }
    context.getMissionOperationFacade().updateUserMissionRecord(updateRecord);
  }

  /**
   * 保存后操作
   * 
   * @param context 上下文
   */
  protected abstract void afterStore(Context context);

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
  /** mission 任务实例 */
  private MissionInstance mission;
  /** buyCourseSet 购买课程Set */
  private Set<String> buyCourseSet;

  public AbstractMissionRecord() {}

  public UserMissionRecordModel buildDomain() {
    UserMissionRecordModel domainModel = new UserMissionRecordModel();
    domainModel.setId(id);
    domainModel.setModule(module);
    domainModel.setUserId(userId);
    domainModel.setMissionId(missionId);
    domainModel.setCourseId(courseId);
    domainModel.setChapterId(chapterId);
    domainModel.setMissionName(missionName);
    domainModel.setMissionDesc(missionDesc);
    domainModel.setMissionPicurl(missionPicurl);
    domainModel.setThresholdNum(thresholdNum);
    domainModel.setCurrentNum(currentNum);
    domainModel.setIsReached(isReached);
    domainModel.setCreateTime(createTime);
    domainModel.setRecordStatus(recordStatus);
    domainModel.setDeadLine(deadLine);
    domainModel.setFinishTime(finishTime);
    domainModel.setMission(mission);
    return domainModel;
  }

  public MissionInstance getMission() {
    return mission;
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
    return this.id != null && !(MissionConstant.TASK_STATUS_UNREACH == this.isReached)
        && null != this.finishTime
        && this.finishTime.before(new Timestamp(DateUtil.getTimesmorning(0)));
  }

  /**
   * 发送系统通知消息
   * 
   * @param missionRecord 用户任务记录模型
   */
  public void sendSystemMessage() {
    if (!MissionConstant.TRUE.equals(getIsReached())) {
      return;
    }
    SystemMessage message = new SystemMessage();
    message.setModule(getModule());
    message.setToUid(getUserId());
    message.setMessageBody(String.format(getMission().getMissionType().getInfo(), getMission()
        .getMissionName()));
    message.setMessageName(MissionConstant.ASYNC_SYSTEM_MESSAGE);
    message.send();
  }

  /**
   * 增加用户积分
   * 
   * @param missionRecord 用户任务记录模型
   */
  public void addUserCredit() {
    AddCreditRequest request = new AddCreditRequest();
    request.setUid(getUserId());
    request.setModule(getModule());
    request.setCreditUpper(getMission().getCreditUpper());
    request.setType(MissionConstant.CREDIT_CHANGE_TYPE_MISSION_FINISH);
    RabbitMQSender.getInstance().send(new AddCreditMessage(getModule(), request));
  }

}
