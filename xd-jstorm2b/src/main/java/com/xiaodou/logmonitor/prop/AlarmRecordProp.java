package com.xiaodou.logmonitor.prop;

import com.xiaodou.common.util.FileUtil;

/**
 * @name @see com.xiaodou.prop.AlarmRecordProp.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月24日
 * @description 报警记录配置文件
 * @version 1.0
 */
public class AlarmRecordProp {
  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil.getInstance("/conf/custom/env/alarm.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null) synchronized (RabbitMqProp.class) {
      if (errFile == null) errFile = FileUtil.getInstance("/conf/custom/env/alarm.properties");
    }
    return errFile;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }
}
