package com.xiaodou.mission.engine.model.mission;

import java.sql.Timestamp;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.domain.UserMissionRecordModel;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.model.mission.TaskRecord.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务记录基础类
 * @version 1.0
 */
public abstract class AbstractTaskRecord extends AbstractMissionRecord {

  @Override
  public void storeRecord(Context context) {
    if (getDeadLine().after(new Timestamp(System.currentTimeMillis()))) {
      super.storeRecord(context);
    }
  }

  @Override
  public void touchAward(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    UserMissionRecordModel updateRecord = new UserMissionRecordModel();
    updateRecord.setId(getId());
    updateRecord.setIsReached(MissionConstant.TASK_STATUS_RECEIVED);
    if (TaskType.dynamic.equals(getMission().getTaskType())) {
      updateRecord.setRecordStatus(MissionConstant.RECORD_STATUS_BACKUP);
    }
    // 显式置空完成时间, 不更新完成时间字段
    updateRecord.setFinishTime(null);
    context.getMissionOperationFacade().updateUserMissionRecord(updateRecord);
    sendSystemMessage();
    addUserCredit();
  }

}
