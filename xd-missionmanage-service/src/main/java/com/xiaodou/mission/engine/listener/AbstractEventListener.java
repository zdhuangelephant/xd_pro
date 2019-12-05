package com.xiaodou.mission.engine.listener;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.EventListenerHolder;
import com.xiaodou.mission.engine.MissionEnums.MissionType;
import com.xiaodou.mission.engine.MissionEnums.TaskType;
import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;

/**
 * @name @see com.xiaodou.mission.engine.listener.AbstractEventListener.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月18日
 * @description 事件监听器主要实现
 * @version 1.0
 */
public abstract class AbstractEventListener implements IEventListener {

  private MissionInstance missionInstance;

  public MissionInstance getMissionInstance() {
    return missionInstance;
  }

  public void setMissionInstance(MissionInstance missionInstance) {
    this.missionInstance = missionInstance;
  }

  @Override
  public <T extends BaseEvent> void registEvent(Class<T> event) {
    EventListenerHolder.registListener(event, this);
  }

  public AbstractMissionRecord onEvent(Context context, AbstractMissionRecord userMissionModel) {
    if (null == context || null == context.getCoreParam()) {
      return userMissionModel;
    }
    if (null == userMissionModel) {
      return null;
    }
    if (MissionType.archieve.equals(getMissionInstance().getMissionType())
        || TaskType.standard.equals(getMissionInstance().getMissionState())) {
      userMissionModel.setRecordStatus(MissionConstant.RECORD_STATUS_AUTO);
    } else {
      userMissionModel.setRecordStatus(MissionConstant.RECORD_STATUS_NORMAL);
    }
    userMissionModel.setMission(getMissionInstance());
    processEvent(context, userMissionModel);
    return userMissionModel;
  }

  /**
   * 处理事件方法
   * 
   * @param context 上下文
   * @param userMissionModel 用户任务数据模型
   */
  public void processEvent(Context context, AbstractMissionRecord userMissionModel) {
    MissionInstance missionInstance = getMissionInstance();
    userMissionModel.setCurrentNum(getCurrentNum(context));
    if (StringUtils.isAllNotBlank(userMissionModel.getCurrentNum(), missionInstance.getThreshold())
        && missionInstance.getScope().compare(userMissionModel.getCurrentNum(),
            missionInstance.getThreshold())) {
      userMissionModel.setCurrentNum(missionInstance.getThreshold());
      userMissionModel.setIsReached(MissionConstant.TRUE);
    }
  }

  /**
   * 获取当前数量
   * 
   * @param context 上下文
   * @return 数量值
   */
  public abstract String getCurrentNum(Context context);

}
