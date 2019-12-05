package com.xiaodou.supernetwork.meta;

/**
 * @name @see com.xiaodou.supernetwork.meta.NetWorkState.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 网络状态枚举
 * @version 1.0
 */
public enum NetWorkState {
  /** Common_Start 通用初始态 */
  Common_Start,
  /** Common_End 通用完结态 */
  Common_End,

  /** Node_Wait 节点等待 */
  Node_Wait,
  /** Node_Online 节点在线 */
  Node_Online,
  /** Node_Offline 节点离线 */
  Node_Offline,

  /** NetWork_Ready 网络准备 */
  NetWork_Ready,
  /** NetWork_Runing 网络运行 */
  NetWork_Runing,
  /** NetWork_Stoping 网络停止中 */
  NetWork_Stoping,
  /** NetWork_Stop 网络停止 */
  NetWork_Stop,

  /** Tutor_Known 导师已知 */
  Tutor_Known,
  /** Tutor_Discuss 导师讨论 */
  Tutor_Discuss,
  /** Tutor_Decision_Ready 导师结论准备 */
  Tutor_Decision_Ready,
  /** Tutor_Decision_Finish 导师结论完成 */
  Tutor_Decision_Finish
}
