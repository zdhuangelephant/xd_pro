package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.LeakFillingEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name LeakFillingEventAction CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月16日
 * @description 查漏补缺事件动作
 * @version 1.0
 */
public class LeakFillingEventAction extends AbstractAction<LeakFillingEvent> {

  @Override
  public void processCoreParam(LeakFillingEvent event, UserCollectDataInstance coreParam) {
    if (null == event || null == event.getScore() || event.getScore() < MissionConstant.PASS_LINE) {
      return;
    }
    coreParam.setLeakFillingStatus(Boolean.TRUE);
  }

  @Override
  public void processOtherParam(LeakFillingEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(LeakFillingEvent event, Map<String, Object> shareParam) {}

}
