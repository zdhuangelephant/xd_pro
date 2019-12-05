package com.xiaodou.mission.engine.action;

import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.model.Context;

/**
 * @name @see com.xiaodou.mission.engine.action.IAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 抽象行为接口
 * @version 1.0
 * @param <T>
 */
public interface IAction<T extends BaseEvent> {

  /**
   * 触发动作
   * 
   * @param event 事件
   * @param context 上下文
   * @return 上下文
   */
  public Context doAction(T event, Context context);

}
