package com.xiaodou.dashboard.dao;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;

/**
 * @name @see com.xiaodou.summer.dao.mongo.MongoProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月30日
 * @description mongoDb配置文件
 * @version 1.0
 */
public class MongoProp {

  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/mongoprop.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ConfigProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/mongoprop.properties");
    }
    return config;
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

  /**
   * 获取整型参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static Integer getInt(String code) {
    return getProperty().getPropertiesInt(code);
  }

}
