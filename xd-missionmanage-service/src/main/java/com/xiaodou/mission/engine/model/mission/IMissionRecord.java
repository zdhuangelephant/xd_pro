package com.xiaodou.mission.engine.model.mission;

import com.xiaodou.mission.engine.model.Context;

/**
 * @name IMissionRecord
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 任务记录抽象描述接口
 * @version 1.0
 */
public interface IMissionRecord {

  /**
   * 保存记录
   * 
   * @param context 上下文
   */
  void storeRecord(Context context);

  /**
   * 达成任务记录
   * 
   * @param context 上下文
   */
  void touchAward(Context context);

}
