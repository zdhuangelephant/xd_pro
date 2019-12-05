package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.BaseEvent;
import com.xiaodou.mission.engine.model.Context;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.AbstractAction.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 基础响应动作类
 * @version 1.0
 * @param <T>
 */
public abstract class AbstractAction<T extends BaseEvent> implements IAction<T> {

  @Override
  public Context doAction(T event, Context context) {
    if (!event.getUserId().equals(context.getCoreParam().getUserId())) {
      return context;
    }
    if (!event.getModule().equals(context.getCoreParam().getModule())) {
      return context;
    }
    context.setEvent(event);
    processCoreParam(event, context.getCoreParam());
    processOtherParam(event, context.getOtherParam());
    processShareParam(event, context.getShares());
    return context;
  }

  /**
   * 处理核心参数
   * 
   * @param event 事件
   * @param coreParam 核心参数
   */
  public abstract void processCoreParam(T event, UserCollectDataInstance coreParam);

  /**
   * 处理其他参数
   * 
   * @param event 事件
   * @param otherParam 其它参数
   */
  public abstract void processOtherParam(T event, Map<String, Object> otherParam);

  /**
   * 处理共享参数
   * 
   * @param event 事件
   * @param shareParam 共享参数
   */
  public abstract void processShareParam(T event, Map<String, Object> shareParam);

}
