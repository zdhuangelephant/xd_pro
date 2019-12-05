package com.xiaodou.userCenter.model.message;

import java.util.UUID;

import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.userCenter.model.alarm.LoginInfoModel;

import lombok.Data;

public class LoginInfoMessage extends AbstractMessagePojo {

  /** 消息名 */
  private static final String ASYNC_MESSAGE_LOGIN_INFO = "loginInfo";

  public LoginInfoMessage(LoginInfoDTO messageBody) {
    super(ASYNC_MESSAGE_LOGIN_INFO);
    setTransferObject(messageBody);
    setCustomTag(UUID.randomUUID().toString());
  }

  public static void send(LoginInfoMessage message) {
    RabbitMQSender.getInstance().send(message);
  }

  @Data
  public static class LoginInfoDTO {

    public LoginInfoDTO(LoginInfoModel model) {
      if (null == model) return;
      this.businessId = model.getId();
      this.userId = model.getUserId();
      this.systemType = model.getSystemType();
      this.deviceId = model.getDeviceId();
      if (null != model.getLoginTime()) this.loginTime = model.getLoginTime().toString();
      this.area = model.getArea();
    }

    /* id 主键id */
    private Long businessId;
    /* 学生id */
    private Long userId;
    /* systemType 系统类型 */
    private String systemType;
    /* deviceId 设备号 */
    private String deviceId;
    /* loginTime 登录时间 */
    private String loginTime;
    private String area;
  }

}
