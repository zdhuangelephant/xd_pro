package com.xiaodou.supernetwork.role;

import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;

import com.xiaodou.supernetwork.meta.NetWorkEvent;
import com.xiaodou.supernetwork.meta.NetWorkState;

/**
 * @name @see com.xiaodou.supernetwork.role.INetWorkGod.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 网络上帝角色
 * @version 1.0
 */
@States({
    @State(name = "Common_Start", entryCallMethod = "entryCommonStart", exitCallMethod = "exitCommonStart", initialState = true),
    @State(name = "NetWork_Ready", entryCallMethod = "entryNetWorkReady", exitCallMethod = "exitNetWorkReady"),
    @State(name = "NetWork_Starting", entryCallMethod = "entryNetWorkStarting", exitCallMethod = "exitNetWorkStarting"),
    @State(name = "NetWork_Runing", entryCallMethod = "entryNetWorkRuning", exitCallMethod = "exitNetWorkRuning"),
    @State(name = "NetWork_Stoping", entryCallMethod = "entryNetWorkStoping", exitCallMethod = "exitNetWorkStoping"),
    @State(name = "NetWork_Stop", entryCallMethod = "entryNetWorkStop", exitCallMethod = "exitNetWorkStop"),
    @State(name = "Common_End", entryCallMethod = "entryCommonEnd", exitCallMethod = "exitCommonEnd", isFinal = true)})
@Transitions({
    @Transit(from = "Common_Start", to = "NetWork_Ready", on = "God_StartUp", callMethod = "fromStartToReadyOnGodStartUp"),
    @Transit(from = "NetWork_Ready", to = "NetWork_Starting", on = "God_AllNodeWait", callMethod = "fromReadyToStartingOnAllNodeWait"),
    @Transit(from = "NetWork_Starting", to = "NetWork_Runing", on = "God_AllNodeOnline", callMethod = "fromStartingToRuningOnAllNodeOnline"),
    @Transit(from = "NetWork_Runing", to = "NetWork_Stoping", on = "God_Shutdown", callMethod = "fromRuningToStopingOnShutdown"),
    @Transit(from = "NetWork_Stoping", to = "NetWork_Stop", on = "God_AllNodeOffline", callMethod = "fromStopingToStopOnAllNodeOffline"),
    @Transit(from = "NetWork_Stop", to = "Common_End", on = "God_AllNodeClear", callMethod = "fromStopToEndOnAllNodeClear")})
@StateMachineParameters(stateType = NetWorkState.class, eventType = NetWorkEvent.class, contextType = String.class)
public interface INetWorkGod extends UntypedStateMachine {

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
   * 进入准备状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNetWorkReady(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入组网状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNetWorkStarting(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入运行状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNetWorkRuning(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入关闭中状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNetWorkStoping(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入关闭状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryNetWorkStop(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 进入销毁状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void entryCommonEnd(NetWorkState from, NetWorkState to, NetWorkEvent event,
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
   * 结束准备状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNetWorkReady(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束组网状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNetWorkStarting(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束运行状态
   * 
   * @param from
   * @param to
   * @param event
   * @param contextId
   */
  public void exitNetWorkRuning(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束关闭中状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNetWorkStoping(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束关闭状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitNetWorkStop(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 结束销毁状态
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void exitCommonEnd(NetWorkState from, NetWorkState to, NetWorkEvent event, String contextId);

  /**
   * 触发[上帝开启网络]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromStartToReadyOnGodStartUp(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[上帝得知全部节点进入准备态]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromReadyToStartingOnAllNodeWait(NetWorkState from, NetWorkState to,
      NetWorkEvent event, String contextId);

  /**
   * 触发[上帝得知全部节点进入在线态]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromStartingToRuningOnAllNodeOnline(NetWorkState from, NetWorkState to,
      NetWorkEvent event, String contextId);

  /**
   * 触发[上帝关闭网络]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromRuningToStopingOnShutdown(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);

  /**
   * 触发[上帝得知全部节点进入离线态]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromStopingToStopOnAllNodeOffline(NetWorkState from, NetWorkState to,
      NetWorkEvent event, String contextId);

  /**
   * 触发[上帝得知全部节点进入清除态]事件
   * 
   * @param from 源状态
   * @param to 目标状态
   * @param event 触发事件
   * @param contextId 上下文ID
   */
  public void fromStopToEndOnAllNodeClear(NetWorkState from, NetWorkState to, NetWorkEvent event,
      String contextId);
}
