package com.xiaodou.st.dashboard.prop;

import com.xiaodou.common.util.FileUtil;

/**
 * @name @see com.xiaodou.st.dashboard.prop.StandardProp.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年8月1日
 * @description 标准化配置文件
 * @version 1.0
 */
public class StandardProp {

  /** REGION_ID_KEY 地域ID键 */
  private static String REGION_ID_KEY = "report.region.id";
  /** REGION_NAME_KEY 地域NAME键 */
  private static String REGION_NAME_KEY = "report.region.name";
  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/standard.properties");

  private static FileUtil getProperty() {
    if (null == prop) {
      synchronized (StandardProp.class) {
        prop = FileUtil.getInstance("/conf/custom/env/standard.properties");
      }
    }
    return prop;
  }

  /**
   * 获取地域ID
   * 
   * @return 地域ID码
   */
  public static String getRegionId() {
    return getProperty().getProperties(REGION_ID_KEY);
  }

  /**
   * 获取地域名称
   * 
   * @return 地域名称
   */
  public static String getRegionName() {
    return getProperty().getProperties(REGION_NAME_KEY);
  }
}
