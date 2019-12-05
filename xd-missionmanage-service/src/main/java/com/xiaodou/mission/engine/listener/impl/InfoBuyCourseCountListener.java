package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.InfoBuyCourseCountListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 购买课程总数监听器
 * @version 1.0
 */
public class InfoBuyCourseCountListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getBuyCourse()) {
      return Integer.toString(context.getCoreParam().getBuyCourse().size());
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
