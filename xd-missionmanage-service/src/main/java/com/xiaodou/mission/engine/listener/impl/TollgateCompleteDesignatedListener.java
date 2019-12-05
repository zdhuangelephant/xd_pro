package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.TollgateCompleteDesignatedListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 闯关完成指定关卡监听器
 * @version 1.0
 */
public class TollgateCompleteDesignatedListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

  @Override
  public void processEvent(Context context, AbstractMissionRecord userMissionModel) {
    MissionInstance missionInstance = getMissionInstance();
    userMissionModel.setCurrentNum(getCurrentNum(context));
    if (context.getCoreParam().getTollgateOnestarSet().contains(missionInstance.getThreshold())
        || context.getCoreParam().getTollgateTwostarSet().contains(missionInstance.getThreshold())
        || context.getCoreParam().getTollgateThreestarSet()
            .contains(missionInstance.getThreshold())) {
      userMissionModel.setCurrentNum(missionInstance.getThreshold());
      userMissionModel.setIsReached(MissionConstant.TRUE);
    }
  }

}
