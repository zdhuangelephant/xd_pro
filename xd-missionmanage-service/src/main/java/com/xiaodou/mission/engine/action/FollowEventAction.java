package com.xiaodou.mission.engine.action;

import java.util.Map;
import com.xiaodou.mission.engine.event.FollowEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.FollowEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 关注事件动作
 * @version 1.0
 */
public class FollowEventAction extends AbstractAction<FollowEvent> {

  @Override
  public void processCoreParam(FollowEvent event, UserCollectDataInstance coreParam) {
    Integer followTotal = coreParam.getFollowCountTotal();
    Integer followOneday = coreParam.getFollowCountOneday();
    coreParam.setFollowCountTotal(++followTotal);
    coreParam.setFollowCountOneday(++followOneday);
  }

  @Override
  public void processOtherParam(FollowEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(FollowEvent event, Map<String, Object> shareParam) {}

}
