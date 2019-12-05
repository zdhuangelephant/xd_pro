package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.CollectionEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.CollectionEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 收藏题目事件动作
 * @version 1.0
 */
public class CollectionEventAction extends AbstractAction<CollectionEvent> {

  @Override
  public void processCoreParam(CollectionEvent event, UserCollectDataInstance coreParam) {
    Integer collectTotal = coreParam.getCollectionTotal();
    Integer collectOneday = coreParam.getCollectionOneday();
    coreParam.setCollectionTotal(++collectTotal);
    coreParam.setCollectionOneday(++collectOneday);
  }

  @Override
  public void processOtherParam(CollectionEvent event, Map<String, Object> otherParam) {
    // TODO Auto-generated method stub

  }

  @Override
  public void processShareParam(CollectionEvent event, Map<String, Object> shareParam) {
    // TODO Auto-generated method stub

  }

}
