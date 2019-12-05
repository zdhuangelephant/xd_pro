package com.xiaodou.oms.statemachine.engine.model.temp;

import com.xiaodou.oms.statemachine.Context;

/**
 * <p>Template模板类入参Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public interface IBaseTempPojo {
  
  /**
   * 初始化一个Pojo实例
   * @param context
   * @return
   */
  public IBaseTempPojo initPojo(Context context);

}
