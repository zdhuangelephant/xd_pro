package com.xiaodou.userCenter.model.message;

import java.util.UUID;

import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.userCenter.model.alarm.AlarmRecordModel;

import lombok.Data;

public class AlarmRecordMessage extends AbstractMessagePojo {

  /** 消息名 */
  private static final String ASYNC_MESSAGE_LOGIN_INFO = "%s_alarmRecord";

  public AlarmRecordMessage(AlarmRecordDTO messageBody) {
    super(String.format(ASYNC_MESSAGE_LOGIN_INFO, "2"));
    setTransferObject(messageBody);
    setCustomTag(UUID.randomUUID().toString());
  }

  public static void send(AlarmRecordMessage message) {
    RabbitMQSender.getInstance().send(message);
  }

  @Data
  public static class AlarmRecordDTO {

    public AlarmRecordDTO(AlarmRecordModel model) {
      if (null == model) return;
      this.userId = model.getUserId();
      this.deviceId = model.getDeviceId();
      this.loginInfoId = model.getLoginInfoId();
      if (null != model.getAlarmLevel()) this.alarmLevel = model.getAlarmLevel().toString();
      if (null != model.getAlarmType()) this.alarmType = model.getAlarmType().toString();
      if (null != model.getStatus()) this.status = model.getStatus().toString();
      if (null != model.getAlarmTime()) this.alarmTime = model.getAlarmTime().toString();
      if (null != model.getPretreatment()) this.pretreatment = model.getPretreatment().toString();
    }

    /* 学生id */
    private Long userId;
    /* 触发报警时的设备号 */
    private String deviceId;
    /* 触发报警的id 登录记录id */
    private Long loginInfoId;
    /* alarmLevel 报警级别 初级，中级，高级 */
    private String alarmLevel;
    /* alarmType 报警类型 */
    private String alarmType;
    /* status 状态 */
    private String status;
    /* pretreatment 预处理 */
    private String pretreatment;
    /* alarmTime 报警时间 */
    private String alarmTime;
  }
}
