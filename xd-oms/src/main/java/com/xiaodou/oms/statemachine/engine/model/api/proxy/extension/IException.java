package com.xiaodou.oms.statemachine.engine.model.api.proxy.extension;

import com.xiaodou.oms.statemachine.Context;

/**
 * <p>API代理异常拓展点</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 */
public interface IException {
  
  /**
   * 异常扩展点主方法,所有异常拓展点均需实现此方法
   * @param context 上下文对象
   * @throws Exception 异常
   */
  public void onException(Context context) throws Exception;

}
