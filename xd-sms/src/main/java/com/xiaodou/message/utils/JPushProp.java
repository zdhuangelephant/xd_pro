package com.xiaodou.message.utils;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.message.enums.JpushTagsEnums;

public class JPushProp {

  private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/jPush_info.properties");

  private static FileUtil getProperty() {
    if (prop == null) synchronized (JPushProp.class) {
      if (prop == null) prop = FileUtil.getInstance("/conf/custom/env/jPush_info.properties");
    }
    return prop;
  }

  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }

  public static String getEnvParams(){
    return getParams("jpush.env");
  }
  
  public static Boolean isOnline(){
    if(getEnvParams().equals(JpushTagsEnums.XD_ONLINE.getName())){
      return true;
    }
    return false;
  }
  
}
