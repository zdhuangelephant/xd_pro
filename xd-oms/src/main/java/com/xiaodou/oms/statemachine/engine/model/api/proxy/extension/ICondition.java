package com.xiaodou.oms.statemachine.engine.model.api.proxy.extension;

import com.xiaodou.oms.statemachine.Context;

/**
 * <p>API代理条件扩展点</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 */
public interface ICondition {
  
  /**
   * 条件扩展点主方法,所有条件拓展点均需实现此方法
   * @param context 上下文对象
   * @return 是否满足条件
   * @throws Exception 异常
   */
  public boolean onCondition(Context context) throws Exception;

}
