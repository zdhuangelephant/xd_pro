package com.xiaodou.mission.constants;

import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.mission.constants.MissionResultType.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务结果类型枚举
 * @version 1.0
 */
public enum MissionResultType {

  /** MISSETASKID 任务不存在 */
  MISSETASKID("40303", "任务不存在。"),
  /** HASBEACCEPTED 不能重复接受任务 */
  HASBEACCEPTED("40304", "不能重复接受任务。"),
  /** HASACCEPTED 已接受任务不能刷新 */
  HASACCEPTED("40305", "已接受任务不能刷新。");

  /**
   * 结果码
   */
  private String code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 服务器Ip
   */
  private String serverIp;

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public String getServerIp() {
    return serverIp;
  }

  MissionResultType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    if (StringUtils.isBlank(this.serverIp)) {
      this.serverIp = "127.0.0.0";
    }
  }
}
