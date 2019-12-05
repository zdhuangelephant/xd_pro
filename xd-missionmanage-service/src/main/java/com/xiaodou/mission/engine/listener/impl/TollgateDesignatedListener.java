package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.TollgateDesignatedListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 进行指定关卡闯关监听器
 * @version 1.0
 */
public class TollgateDesignatedListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

  @Override
  public void processEvent(Context context, AbstractMissionRecord userMissionModel) {
    MissionInstance missionInstance = getMissionInstance();
    userMissionModel.setCurrentNum(getCurrentNum(context));
    if (context.getCoreParam().getTollgateSet().contains(missionInstance.getThreshold())) {
      userMissionModel.setCurrentNum(missionInstance.getThreshold());
      userMissionModel.setIsReached(MissionConstant.TRUE);
    }
  }

}
