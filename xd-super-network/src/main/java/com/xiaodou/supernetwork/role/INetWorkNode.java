package com.xiaodou.supernetwork.role;

import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;

import com.xiaodou.supernetwork.meta.NetWorkEvent;
import com.xiaodou.supernetwork.meta.NetWorkState;

/**
 * @name @see com.xiaodou.supernetwork.role.INetWorkNode.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 网络节点角色
 * @version 1.0
 */
@States({
    @State(name = "Common_Start", entryCallMethod = "entryCommonStart", exitCallMethod = "exitCommonStart", initialState = true),
    @State(name = "Node_Wait", entryCallMethod = "entryNodeWait", exitCallMethod = "exitNodeWait"),
    @State(name = "Node_Online", entryCallMethod = "entryNodeOnline", exitCallMethod = "exitNodeOnline"),
    @State(name = "Node_Offline", entryCallMethod = "entryNodeOffline", exitCallMethod = "exitNodeOffline")})
@Transitions({
    @Transit(from = "Common_Start", to = "Node_Wait", on = "Node_Init", callMethod = "fromStartToWaitOnInit"),
    @Transit(from = "Node_Wait", to = "Node_Online", on = "Nod_Mount", callMethod = "fromWaitToOnlineOnMount"),
    @Transit(from = "Node_Online", to = "Node_Offline", on = "Node_UnMount", callMethod = "fromOnlineToOfflineOnUnMount"),
    @Transit(from = "Node_Offline", to = "Common_Start", on = "Node_Clear", callMethod = "fromOfflineToStartOnClear"),
    @Transit(from = "Node_Online", to = "Node_Wait", on = "Node_Init", callMethod = "fromOnlineToWaitOnInit"),
    @Transit(from = "Node_Offline", to = "Node_Wait", on = "Node_Init", callMethod = "fromOfflineToWaitOnInit")})
@StateMachineParameters(stateType = NetWorkState.class, eventType = NetWorkEvent.class, contextType = String.class)
public interface INetWorkNode {

  /**
   * 进入开始状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryCommonStart(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入等待状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNodeWait(NetWorkState from, NetWorkState to, NetWorkEvent event, String contextId);

  /**
   * 进入在线状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNodeOnline(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入离线状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNodeOffline(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束开始状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitCommonStart(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束等待状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNodeWait(NetWorkState from, NetWorkState to, NetWorkEvent event, String contextId);

  /**
   * 结束在线状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNodeOnline(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束离线状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNodeOffline(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[初始化]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromStartToWaitOnInit(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[挂载]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromWaitToOnlineOnMount(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[卸载]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromOnlineToOfflineOnUnMount(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[清除]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromOfflineToStartOnClear(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[初始化]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromOnlineToWaitOnInit(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[初始化]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromOfflineToWaitOnInit(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);
}
