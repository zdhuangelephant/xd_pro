package com.xiaodou.mission.engine.action;

import java.util.Map;
import com.xiaodou.mission.engine.event.CreditTotalEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.CreditTotalEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 总积分事件动作
 * @version 1.0
 */
public class CreditTotalEventAction extends AbstractAction<CreditTotalEvent> {

  @Override
  public void processCoreParam(CreditTotalEvent event, UserCollectDataInstance coreParam) {
    coreParam.setCreditTotal(event.getTotalCredit());    
  }

  @Override
  public void processOtherParam(CreditTotalEvent event, Map<String, Object> otherParam) {
    
  }

  @Override
  public void processShareParam(CreditTotalEvent event, Map<String, Object> shareParam) {
    
  }

}
