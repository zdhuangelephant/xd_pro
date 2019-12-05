package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.InfoImproveListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 完善个人信息监听器
 * @version 1.0
 */
public class InfoImproveListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (null != context && null != context.getCoreParam()) {
      if (context.getCoreParam().getImproveNickname() == MissionConstant.TRUE
          || context.getCoreParam().getImproveProtrait() == MissionConstant.TRUE
          || context.getCoreParam().getImproveSign() == MissionConstant.TRUE) {
        return MissionConstant.DEFAULT_THROHOLD_RECHED;
      }
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }

}
