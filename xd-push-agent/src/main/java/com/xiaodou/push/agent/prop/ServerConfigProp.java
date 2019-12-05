package com.xiaodou.push.agent.prop;

import com.xiaodou.common.util.FileUtil;

/**
 * @name @see cpm.xiaodou.push.agent.prop.ServerConfigProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 服务器信息配置文件
 * @version 1.0
 */
public class ServerConfigProp {

  /** 定义配置项 */

  /** HOST 服务器地址 */
  public static final String HOST = "message.server.host";

  /** PORT 服务器端口 */
  public static final String PORT = "message.server.port";

  /** PUSH_MESSAGE_URL 消息推送地址 */
  public static final String PUSH_MESSAGE_URL = "message.server.push.url";
  
  /**HAND_PUSH_MESSAGE_URL 消息手动推送地址*/
  public static final String HAND_PUSH_MESSAGE_URL = "message.server.hand.push.url";

  /** PUSH_MESSAGE_RETRY 消息推送重試次數 */
  public static final String PUSH_MESSAGE_RETRY = "message.server.push.retryTime";

  /** PUSH_MESSAGE_TIMEOUT 消息推送超時時間 */
  public static final String PUSH_MESSAGE_TIMEOUT = "message.server.push.timeout";

  /** PUSH_SHORT_MESSAGE_URL 短信推送地址 */
  public static final String PUSH_SHORT_MESSAGE_URL = "sMessage.server.push.url";

  /** PUSH_SHORT_MESSAGE_RETRY 短信推送重試次數 */
  public static final String PUSH_SHORT_MESSAGE_RETRY = "sMessage.server.push.retryTime";

  /** PUSH_SHORT_MESSAGE_TIMEOUT 短信推送超時時間 */
  public static final String PUSH_SHORT_MESSAGE_TIMEOUT = "sMessage.server.push.timeout";

  /** 配置文件相关操作 */

  /** config 配置文件 */
  private static FileUtil config = FileUtil
      .getInstance("/conf/custom/env/message_server.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ServerConfigProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/env/message_server.properties");
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
   * 获取Int型配置项
   * 
   * @param code
   * @return
   */
  public static int getInt(String code) {
    return getProperty().getPropertiesInt(code);
  }

}
