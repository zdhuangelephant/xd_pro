package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.WrongQuesCountEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.WrongQuesCountEventAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 错题数量事件行为
 * @version 1.0
 */
public class WrongQuesCountEventAction extends AbstractAction<WrongQuesCountEvent> {

  @Override
  public void processCoreParam(WrongQuesCountEvent event, UserCollectDataInstance coreParam) {
    Integer wrongQuesCount = event.getCount();
    if (null != coreParam.getTollgateInfo()) {
      coreParam.getTollgateInfo().setWrongQuesNum(wrongQuesCount);
    }
  }

  @Override
  public void processOtherParam(WrongQuesCountEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(WrongQuesCountEvent event, Map<String, Object> shareParam) {}

}
