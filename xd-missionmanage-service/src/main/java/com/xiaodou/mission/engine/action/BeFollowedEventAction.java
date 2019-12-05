package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.BeFollowedEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 被关注事件动作
 * @version 1.0
 */
public class BeFollowedEventAction extends AbstractAction<BeFollowedEvent> {

  @Override
  public void processCoreParam(BeFollowedEvent event, UserCollectDataInstance coreParam) {
    Integer followedCountOneday = coreParam.getFollowedCountOneday();
    Integer followedCountTotal = coreParam.getFollowedCountTotal();
    coreParam.setFollowedCountTotal(++followedCountTotal);
    coreParam.setFollowedCountOneday(++followedCountOneday);
  }

  @Override
  public void processOtherParam(BeFollowedEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(BeFollowedEvent event, Map<String, Object> shareParam) {}

}
