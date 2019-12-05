package com.xiaodou.dataclean.core.event;

import lombok.Data;

import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;


/**
 * @name DataCleanEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 数据清洗事件
 * @version 1.0
 */
@Data
public class DCCoreEvent<T> {

  /** MESSAGE_NAME 消息名 */
  private final static String MESSAGE_NAME = "dataclean";
  /** eventName 事件名 */
  private String eventName;
  /** module 所属模块 */
  private String module;
  /** dataModel 数据模型 */
  private T dataModel;

  /**
   * 发送清洗数据事件
   */
  public final void send() {
    AbstractMessagePojo pojo = new AbstractMessagePojo(MESSAGE_NAME);
    pojo.setTransferObject(this);
    RabbitMQSender.getInstance().send(pojo);
  }

}
