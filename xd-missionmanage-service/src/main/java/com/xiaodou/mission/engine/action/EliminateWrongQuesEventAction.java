package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.EliminateWrongQuesEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.EliminateWrongQuesEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 消灭错题事件动作
 * @version 1.0
 */
public class EliminateWrongQuesEventAction extends AbstractAction<EliminateWrongQuesEvent> {


  @Override
  public void processCoreParam(EliminateWrongQuesEvent event, UserCollectDataInstance coreParam) {
    Integer eliminateWrongQuesTotal = coreParam.getWrongQuesSolveTotal();
    Integer eliminateWrongQuesOneday = coreParam.getWrongQuesSolveOneday();
    coreParam.setWrongQuesSolveOneday(eliminateWrongQuesOneday + event.getCount());
    coreParam.setWrongQuesSolveTotal(eliminateWrongQuesTotal + event.getCount());
  }

  @Override
  public void processOtherParam(EliminateWrongQuesEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(EliminateWrongQuesEvent event, Map<String, Object> shareParam) {}

}
