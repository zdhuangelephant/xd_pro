package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.DailyPracticeContinListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 连续完成每日一练天数监听器
 * @version 1.0
 */
public class DailyPracticeContinListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null == context || null == context.getCoreParam()) {
      return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
    }
    UserTollgateDataDetailInstance tollgateInfo = context.getCoreParam().getTollgateInfo();
    if (null != tollgateInfo && null != tollgateInfo.getContinDailyPractice()) {
      return tollgateInfo.getContinDailyPractice().toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
