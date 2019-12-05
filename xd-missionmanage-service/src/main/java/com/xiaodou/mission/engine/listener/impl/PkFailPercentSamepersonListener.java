package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.PkFailPercentSamepersonListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 针对同一个人失败率监听器
 * @version 1.0
 */
public class PkFailPercentSamepersonListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()
        && null != context.getCoreParam().getPkInfo()
        && null != context.getCoreParam().getPkInfo().getPkFailTimesSameperson()
        && null != context.getCoreParam().getPkInfo().getPkTotalTimesSameperson()) {
      Integer pkFailTimesSameperson = context.getCoreParam().getPkInfo().getPkFailTimesSameperson();
      Integer pkTotalTimesSameperson =
          context.getCoreParam().getPkInfo().getPkTotalTimesSameperson();
      Double pkfailPercentSameperson = MissionConstant.DOUBLE_ZERO;
      if (pkFailTimesSameperson > MissionConstant.INTEGER_ZERO
          && pkTotalTimesSameperson > MissionConstant.INTEGER_ZERO
          && pkFailTimesSameperson < pkTotalTimesSameperson) {
        pkfailPercentSameperson =
            Double.valueOf(pkFailTimesSameperson) / Double.valueOf(pkTotalTimesSameperson);
      }
      return pkfailPercentSameperson.toString();
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
