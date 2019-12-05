package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.FriendAddEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.FriendAddEventAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 添加好友事件行为
 * @version 1.0
 */
public class FriendAddEventAction extends AbstractAction<FriendAddEvent> {

  @Override
  public void processCoreParam(FriendAddEvent event, UserCollectDataInstance coreParam) {
    Integer friendTotal = coreParam.getFriendCountTotal();
    Integer friendOneday = coreParam.getFriendCountOneday();
    coreParam.setFriendCountTotal(++friendTotal);
    coreParam.setFriendCountOneday(++friendOneday);
  }

  @Override
  public void processOtherParam(FriendAddEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(FriendAddEvent event, Map<String, Object> shareParam) {}

}
