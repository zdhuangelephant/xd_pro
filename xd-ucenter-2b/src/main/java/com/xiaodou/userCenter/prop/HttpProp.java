package com.xiaodou.userCenter.prop;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;

public class HttpProp {
  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/env/http_config.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ConfigProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/http_config.properties");
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

  public static String register() {
    return getParams("user.register");
  }

  public static String login() {
    return getParams("user.login");
  }

  public static String findPassword() {
    return getParams("user.findPassword");
  }

  public static String modifyPassword() {
    return getParams("user.modifyPassword");
  }

  public static String modifyDefaultPassword() {
    return getParams("user.modifyDefaultPassword");
  }

  public static String findUser() {
    return getParams("user.findUser");
  }
  
  public static String sendDingtalk() {
    return getParams("user.sendDingtalk");
  }
  
  public static String sendDingtalkMock() {
    return getParams("user.sendDingtalk.mock");
  }
  
  public static String getUserByTelAndMod(){
    return getParams("user.getUserByTelAndMod");
  }
}
