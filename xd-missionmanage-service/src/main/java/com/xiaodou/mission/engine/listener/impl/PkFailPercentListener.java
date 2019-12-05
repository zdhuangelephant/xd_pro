package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.PkFailPercentListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description PK失败率监听器
 * @version 1.0
 */
public class PkFailPercentListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getPkFailTimes()
        && null != context.getCoreParam().getPkTotalTimesForCaculate()) {
      Integer pkFailTimes = context.getCoreParam().getPkFailTimes();
      Integer pkTotalTimes = context.getCoreParam().getPkTotalTimesForCaculate();
      Double pkFailPercent = MissionConstant.DOUBLE_ZERO;
      if (pkFailTimes > MissionConstant.INTEGER_ZERO && pkTotalTimes > MissionConstant.INTEGER_ZERO
          && pkFailTimes < pkTotalTimes) {
        pkFailPercent = Double.valueOf(pkFailTimes) / Double.valueOf(pkTotalTimes);
      }
      return pkFailPercent.toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
