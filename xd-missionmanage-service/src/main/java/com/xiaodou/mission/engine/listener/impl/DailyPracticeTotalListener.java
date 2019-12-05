package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.DailyPracticeTotalListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 完成每日一练总天数监听器
 * @version 1.0
 */
public class DailyPracticeTotalListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null == context || null == context.getCoreParam()) {
      return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
    }
    UserTollgateDataDetailInstance tollgateInfo = context.getCoreParam().getTollgateInfo();
    if (null != tollgateInfo && null != tollgateInfo.getTotalDailyPractice()) {
      return tollgateInfo.getTotalDailyPractice().toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
