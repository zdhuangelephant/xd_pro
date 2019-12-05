package com.xiaodou.sms.common.constant;

/**
 * 常量类
 * 
 * @author peiru.zhang
 * 
 */
public interface Constant {

  /** 连接符 */
  public static final String COMMON_DELIMITER = ":";

  /** 短信 */
  public static String SMS = ConstantUtil.append("xiaodou", "sms");

  /** 短信模板 */
  public static String SMS_TEMPLATE = ConstantUtil.append(SMS, "template");

  /** 短信模板类型 */
  public static String SMS_TEMPLATE_TYPE = ConstantUtil.append(SMS, "templateType");

  /** SMS_CHANNEL 短信通道 */
  public static String SMS_CHANNEL = ConstantUtil.append(SMS, "channel");

  /** 验证码短信通道 */
  public static final String ChannelForCheckCode = "1";
  
  /** app外推送消息通道*/
  public static final String ChannelForPMNOTICE ="2";
  
  /** app内推送消息通道*/
  public static final String ChannelForSMSNOTICE = "3";
  /**
   * @name @see com.xiaodou.sms.common.constant.ConstantUtil.java
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月31日
   * @description 常量操作工具类
   * @version 1.0
   */
  public class ConstantUtil {
    public static String append(String src, String content) {
      return src + COMMON_DELIMITER + content;
    }
  }

}
