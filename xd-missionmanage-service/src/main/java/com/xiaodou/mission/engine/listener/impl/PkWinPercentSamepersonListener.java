package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.PkWinPercentSamepersonListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 针对同一用户PK胜率监听器
 * @version 1.0
 */
public class PkWinPercentSamepersonListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getPkInfo()
        && null != context.getCoreParam().getPkInfo().getPkWinTimesSameperson()
        && null != context.getCoreParam().getPkInfo().getPkTotalTimesSameperson()) {
      Integer pkWinTimesSameperson = context.getCoreParam().getPkInfo().getPkWinTimesSameperson();
      Integer pkTotalTimesSameperson =
          context.getCoreParam().getPkInfo().getPkTotalTimesSameperson();
      Double pkwinPercentSameperson = MissionConstant.DOUBLE_ZERO;
      if (pkWinTimesSameperson > MissionConstant.INTEGER_ZERO
          && pkTotalTimesSameperson > MissionConstant.INTEGER_ZERO
          && pkWinTimesSameperson < pkTotalTimesSameperson) {
        pkwinPercentSameperson =
            Double.valueOf(pkWinTimesSameperson) / Double.valueOf(pkTotalTimesSameperson);
      }
      return pkwinPercentSameperson.toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
