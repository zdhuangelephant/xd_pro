package com.xiaodou.mission.engine.model.mission;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.CascadeMissionRecordModel;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name MissionRecordFactory
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 任务模型工厂
 * @version 1.0
 */
public class MissionRecordFactory {

  /**
   * 内部构造方法
   * 
   * @param missionType 类型
   * @param taskType 任务类型明细
   * @return 任务模型类
   */
  private static AbstractMissionRecord buildMissionRecord(MissionType missionType, TaskType taskType) {
    if (MissionType.archieve.equals(missionType)) {
      return new ArchieveRecord();
    } else if (MissionType.task.equals(missionType) && TaskType.standard.equals(taskType)) {
      return new StandardRecord();
    } else if (MissionType.task.equals(missionType) && TaskType.dynamic.equals(taskType)) {
      return new DynamicRecord();
    } else if (MissionType.task.equals(missionType) && TaskType.statics.equals(taskType)) {
      return new StaticsRecord();
    }
    return null;
  }

  public static AbstractMissionRecord buildMissionRecord(UserCollectDataInstance coreParam,
      MissionInstance mission) {
    AbstractMissionRecord missionRecord =
        buildMissionRecord(mission.getMissionType(), mission.getTaskType());
    if (null == missionRecord) {
      return null;
    }
    missionRecord.setMission(mission);
    missionRecord.setModule(mission.getModule());
    missionRecord.setUserId(coreParam.getUserId());
    missionRecord.setStatus(MissionConstant.MISSION_RECORD_STATUS_CREATE);
    if (null != coreParam.getBuyCourse() && !coreParam.getBuyCourse().isEmpty()) {
      missionRecord.setBuyCourseSet(coreParam.getBuyCourse());
    }
    return missionRecord;
  }

  public static AbstractMissionRecord buildMissionRecord(CascadeMissionRecordModel missionModel) {
    MissionInstance mission = new MissionInstance(missionModel);
    AbstractMissionRecord missionRecord =
        buildMissionRecord(mission.getMissionType(), mission.getTaskType());
    if (null == missionRecord) {
      return null;
    }
    missionRecord.setMission(mission);
    missionRecord.setId(missionModel.getRecordId());
    missionRecord.setModule(missionModel.getModule());
    missionRecord.setUserId(missionModel.getUserId());
    missionRecord.setMissionId(missionModel.getId());
    missionRecord.setCourseId(missionModel.getCourseId());
    missionRecord.setChapterId(missionModel.getChapterId());
    missionRecord.setMissionName(missionModel.getMissionName());
    missionRecord.setMissionDesc(missionModel.getMissionDesc());
    missionRecord.setMissionPicurl(missionModel.getMissionPicUrl());
    missionRecord.setThresholdNum(missionModel.getThreshold());
    missionRecord.setIsReached(missionModel.getIsReached());
    missionRecord.setCreateTime(missionModel.getCreateTime());
    missionRecord.setRecordStatus(missionModel.getRecordStatus());
    if (null == missionModel.getRecordId()) {
      missionRecord.setStatus(MissionConstant.MISSION_RECORD_STATUS_CREATE);
    } else {
      missionRecord.setStatus(MissionConstant.MISSION_RECORD_STATUS_MODIFY);
    }
    return missionRecord;
  }

  public static AbstractMissionRecord buildMissionRecord(UserMissionRecordModel missionModel,
      MissionInstance mission) {
    AbstractMissionRecord missionRecord =
        buildMissionRecord(mission.getMissionType(), mission.getTaskType());
    if (null == missionRecord) {
      return null;
    }
    missionRecord.setMission(mission);
    missionRecord.setId(missionModel.getId());
    missionRecord.setModule(missionModel.getModule());
    missionRecord.setUserId(missionModel.getUserId());
    missionRecord.setMissionId(missionModel.getMissionId());
    missionRecord.setCourseId(missionModel.getCourseId());
    missionRecord.setChapterId(missionModel.getChapterId());
    missionRecord.setMissionName(missionModel.getMissionName());
    missionRecord.setMissionDesc(missionModel.getMissionDesc());
    missionRecord.setMissionPicurl(missionModel.getMissionPicurl());
    missionRecord.setThresholdNum(missionModel.getThresholdNum());
    missionRecord.setIsReached(missionModel.getIsReached());
    missionRecord.setCreateTime(missionModel.getCreateTime());
    missionRecord.setRecordStatus(missionModel.getRecordStatus());
    if (null == missionModel.getId()) {
      missionRecord.setStatus(MissionConstant.MISSION_RECORD_STATUS_CREATE);
    } else {
      missionRecord.setStatus(MissionConstant.MISSION_RECORD_STATUS_MODIFY);
    }
    return missionRecord;
  }

}
