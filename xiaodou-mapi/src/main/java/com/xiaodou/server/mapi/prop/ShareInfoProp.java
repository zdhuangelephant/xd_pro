package com.xiaodou.server.mapi.prop;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.summer.vo.out.ResultProp;

public class ShareInfoProp {

  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil.getInstance("/conf/custom/env/shareinfo.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null) synchronized (ResultProp.class) {
      if (errFile == null) errFile = FileUtil.getInstance("/conf/custom/env/shareinfo.properties");
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
