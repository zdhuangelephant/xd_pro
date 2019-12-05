package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.MissionInstance;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.InfoBuyCourseListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 购买课程监听器
 * @version 1.0
 */
public class InfoBuyCourseListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    MissionInstance missionInstance = getMissionInstance();
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getBuyCourse()) {
      if (context.getCoreParam().getBuyCourse().contains(missionInstance.getThreshold())) {
        return missionInstance.getThreshold();
      }
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
