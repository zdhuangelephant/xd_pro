package com.xiaodou.mission.engine.listener;

import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;

/**
 * @name @see com.xiaodou.mission.engine.listener.IEventListener.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月18日
 * @description 事件监听器接口
 * @version 1.0
 */
public interface IEventListener {

  /**
   * 注册任务实例
   * 
   * @param missionInstance 任务实例
   */
  public void setMissionInstance(MissionInstance missionInstance);

  /**
   * 获取任务实例
   * 
   * @return 任务实例
   */
  public MissionInstance getMissionInstance();

  /**
   * 注册监听事件
   * 
   * @param event 事件
   */
  public <T extends BaseEvent> void registEvent(Class<T> event);

  /**
   * 触发事件后置行为
   * 
   * @param context 上下文
   * @param userMissionModel 用户任务数据模型
   * @return 用户任务数据模型
   */
  public AbstractMissionRecord onEvent(Context context, AbstractMissionRecord userMissionModel);

}
