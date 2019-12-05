package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.LeakFillingCompleteListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 完成查漏补缺事件监听器
 * @version 1.0
 */
public class LeakFillingCompleteListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null == context || null == context.getCoreParam()) {
      return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
    }
    UserCollectDataInstance userCollectData = context.getCoreParam();;
    if (null != userCollectData && userCollectData.getLeakFillingStatus()) {
      return context.getEvent().getCourseId();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
