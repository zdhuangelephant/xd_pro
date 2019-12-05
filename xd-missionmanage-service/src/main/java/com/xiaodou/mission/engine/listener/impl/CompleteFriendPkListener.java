package com.xiaodou.mission.engine.listener.impl;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.FriendPkEvent;
import com.xiaodou.mission.engine.listener.AbstractEventListener;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.listener.impl.CompleteFriendPkListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 完成好友PK监听器
 * @version 1.0
 */
public class CompleteFriendPkListener extends AbstractEventListener {

  @Override
  public String getCurrentNum(Context context) {
    if (context.getEvent() != null && context.getEvent().getClass().equals(FriendPkEvent.class)) {
      return MissionConstant.DEFAULT_THROHOLD_RECHED;
    }
    return MissionConstant.DEFAULT_THROHOLD_UNRECHED;
  }
}
