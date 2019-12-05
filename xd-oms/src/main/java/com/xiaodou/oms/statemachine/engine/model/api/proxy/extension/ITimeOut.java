package com.xiaodou.oms.statemachine.engine.model.api.proxy.extension;

import com.xiaodou.oms.statemachine.Context;

/**
 * <p>API代理超时拓展点</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 */
public interface ITimeOut {
  
  /**
   * 超时扩展点主方法,所有超时拓展点均需实现此方法
   * @param context 上下文对象
   * @throws Exception 异常
   */
  public void onTimeOut(Context context) throws Exception;

}
