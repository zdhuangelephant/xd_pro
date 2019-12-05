package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.TollgateTwostarSamecourseListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 同课程两星通过监听器
 * @version 1.0
 */
public class TollgateTwostarSamecourseListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getTollgateInfo()
        && null != context.getCoreParam().getTollgateInfo().getTollgateTwostarCountSamecourse()) {
      return context.getCoreParam().getTollgateInfo().getTollgateTwostarCountSamecourse()
          .toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

  @Override
  public void processEvent(Context context, AbstractMissionRecord userMissionModel) {
    MissionInstance missionInstance = getMissionInstance();
    String currentNum = getCurrentNum(context);
    if (StringUtils.isNotBlank(userMissionModel.getCurrentNum())
        && missionInstance.getScope().compare(currentNum, userMissionModel.getCurrentNum())) {
      userMissionModel.setCurrentNum(currentNum);
    }
    if (missionInstance.getScope().compare(userMissionModel.getCurrentNum(),
        missionInstance.getThreshold())) {
      userMissionModel.setCurrentNum(missionInstance.getThreshold());
      userMissionModel.setIsReached(MissionConstant.TRUE);
    }
  }
}
