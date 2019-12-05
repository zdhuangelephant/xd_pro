package com.xiaodou.mission.util;

import org.apache.log4j.Logger;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mission.domain.UserMissionRecordModel;

/**
 * @name MissionLoggerUtil
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 任务系统日志类拓展
 * @version 1.0
 */
public class MissionLoggerUtil extends LoggerUtil {

  /**
   * 流程信息日志
   */
  public static void missionRecord(UserMissionRecordModel msg) {
    Logger.getLogger("missionRecordLogger").info(msg);
  }

}
