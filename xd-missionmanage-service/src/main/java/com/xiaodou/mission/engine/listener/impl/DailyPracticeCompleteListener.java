package com.xiaodou.mission.engine.listener.impl;

import java.util.Date;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.DailyPracticeCompleteListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 完成指定课程每日一练监听器
 * @version 1.0
 */
public class DailyPracticeCompleteListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null == context || null == context.getCoreParam()) {
      return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
    }
    UserTollgateDataDetailInstance tollgateInfo = context.getCoreParam().getTollgateInfo();
    if (null != tollgateInfo && null != tollgateInfo.getDailyPracticeTime()) {
      String lastPracticeDate = DateUtil.SDF_YMD.format(tollgateInfo.getDailyPracticeTime());
      String currDate = DateUtil.SDF_YMD.format(new Date());
      if (lastPracticeDate.equals(currDate)) {
        return context.getEvent().getCourseId();
      }
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
