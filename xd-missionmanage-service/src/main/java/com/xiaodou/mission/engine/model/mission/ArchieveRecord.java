package com.xiaodou.mission.engine.model.mission;

import com.xiaodou.mission.engine.model.Context;

/**
 * @name ArchieveRecord
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 成就任务记录
 * @version 1.0
 */
public class ArchieveRecord extends AbstractMissionRecord {

  @Override
  public void createRecord(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    context.getMissionOperationFacade().insertUserMissionRecord(buildDomain());
  }

  @Override
  public void touchAward(Context context) {
    sendSystemMessage();
  }

  @Override
  protected void afterStore(Context context) {
    touchAward(context);
  }

}
