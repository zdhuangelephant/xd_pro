package com.xiaodou.common.util.log.model;

public interface IAlarmEntity {

  /**
   * 报警类型名称
   */
  String getEventModule();

  /**
   * 报警实例名称
   */
  String getEventName();

  /**
   * 报警短信内容（短信内容）
   */
  String getMessageInfo();


  /**
   * 报警邮件信息（邮件内容）
   */
  String getMailInfo();

  /**
   * 获取日志报警实体
   */
  IAlarmEntity getLoggerEntity();

}
