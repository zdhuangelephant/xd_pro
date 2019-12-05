package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.PkWinPercentListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description PK胜率监听器
 * @version 1.0
 */
public class PkWinPercentListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getPkWinTimes()
        && null != context.getCoreParam().getPkTotalTimesForCaculate()) {
      Integer pkWinTimes = context.getCoreParam().getPkWinTimes();
      Integer pkTotalTimes = context.getCoreParam().getPkTotalTimesForCaculate();
      Double pkwinPercent = MissionConstant.DOUBLE_ZERO;
      if (pkWinTimes > MissionConstant.INTEGER_ZERO && pkTotalTimes > MissionConstant.INTEGER_ZERO
          && pkWinTimes < pkTotalTimes) {
        pkwinPercent = Double.valueOf(pkWinTimes) / Double.valueOf(pkTotalTimes);
      }
      return pkwinPercent.toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
