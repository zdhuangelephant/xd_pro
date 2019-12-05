package com.xiaodou.mission.engine.model.mission;

import java.sql.Timestamp;

import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.model.mission.StaticsRecord.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 静态任务类型数据
 * @version 1.0
 */
public class StaticsRecord extends AbstractTaskRecord {

  public StaticsRecord() {
    setRecordStatus(MissionConstant.RECORD_STATUS_NORMAL);
  }

  @Override
  protected void createRecord(Context context) {
    if (null == context.getMissionOperationFacade()) {
      return;
    }
    if (MissionConstant.TRUE != getIsReached()) {
      return;
    }
    setFinishTime(new Timestamp(System.currentTimeMillis()));
    context.getMissionOperationFacade().insertUserMissionRecord(buildDomain());
  }

  @Override
  protected void afterStore(Context context) {
    // 非动态任务标准流程,此处保留空实现
  }

}
