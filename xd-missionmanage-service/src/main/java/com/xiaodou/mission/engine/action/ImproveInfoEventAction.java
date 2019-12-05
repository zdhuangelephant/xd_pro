package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.ImproveInfoEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.ImproveInfoEventAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 更新信息事件行为
 * @version 1.0
 */
public class ImproveInfoEventAction extends AbstractAction<ImproveInfoEvent> {

  @Override
  public void processCoreParam(ImproveInfoEvent event, UserCollectDataInstance coreParam) {
    if (event.getImproveNickname()) {
      coreParam.setImproveNickname(MissionConstant.TRUE);
    }
    if (event.getImproveProtrait()) {
      coreParam.setImproveProtrait(MissionConstant.TRUE);
    }
    if (event.getImproveSign()) {
      coreParam.setImproveSign(MissionConstant.TRUE);
    }
  }

  @Override
  public void processOtherParam(ImproveInfoEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(ImproveInfoEvent event, Map<String, Object> shareParam) {}

}
