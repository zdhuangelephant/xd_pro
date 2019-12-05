package com.xiaodou.supernetwork.meta;

/**
 * @name @see com.xiaodou.supernetwork.communicate.NetWorkEvent.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 网络通信事件枚举
 * @version 1.0
 */
public enum NetWorkEvent {
  /** Common_Success 通用成功 */
  Common_Success,
  /** Common_Fail 通用失败 */
  Common_Fail,
  /** Common_Alarm 通用报警 */
  Common_Alarm,

  /** Node_Init 节点初始化 */
  Node_Init,
  /** Node_Mount 节点挂载 */
  Node_Mount,
  /** Node_UnMount 节点卸载 */
  Node_UnMount,
  /** Node_Clear 节点清除 */
  Node_Clear,

  /** God_StartUp 上帝开启网络 */
  God_StartUp,
  /** God_ShutDown 上帝关闭网络 */
  God_ShutDown,
  /** God_NodePass 上帝允许节点通过 */
  God_NodePass,
  /** God_NodeHoldUp 上帝将节点挂起 */
  God_NodeHoldUp,
  /** God_AllNodeWait 上帝得知全部节点进入准备态 */
  God_AllNodeWait,
  /** God_AllNodeOnline 上帝得知全部节点进入在线态 */
  God_AllNodeOnline,
  /** God_AllNodeOffline 上帝得知全部节点进入离线态 */
  God_AllNodeOffline,
  /** God_AllNodeClear 上帝得知全部节点进入清除态 */
  God_AllNodeClear,

  /** Pupil_Report 门徒报告 */
  Pupil_Report,

  /** Tutor_Discuss 导师讨论 */
  Tutor_Discuss,
  /** Tutor_Confirm 导师确认 */
  Tutor_Confirm,

  /** Tutor_Main_Notice 主导师通知 */
  Tutor_Main_Notice,
  /** Tutor_Other_Ask 从导师问询 */
  Tutor_Other_Ask
}
