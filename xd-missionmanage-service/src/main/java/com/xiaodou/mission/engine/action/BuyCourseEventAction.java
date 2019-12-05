package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.BuyCourseEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.BuyCourseEventAction.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月16日
 * @description 购买课程后续动作
 * @version 1.0
 */
public class BuyCourseEventAction extends AbstractAction<BuyCourseEvent> {

  @Override
  public void processCoreParam(BuyCourseEvent event, UserCollectDataInstance coreParam) {
    coreParam.getBuyCourse().add(event.getCourseId());
  }

  @Override
  public void processOtherParam(BuyCourseEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(BuyCourseEvent event, Map<String, Object> shareParam) {}

}
