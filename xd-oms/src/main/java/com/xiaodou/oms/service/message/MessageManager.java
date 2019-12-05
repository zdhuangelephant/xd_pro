package com.xiaodou.oms.service.message;

import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.oms.util.model.MessageEntity;
import com.xiaodou.oms.util.model.MessageRecord;

/**
 * <p>
 * RabbitMQ消息管理器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
@Component("messageManager")
public class MessageManager {

  @Resource
  MessageRecordService messageRecordService;

  /**
   * 发送消息
   * 
   * @param context
   * @param tempType
   * @param messageName
   * @return messageTag
   */
  public String recordMessage(Context context, String tempType, String messageName) {

    MessageContext messageContext = MessageContext.newInstance(context);

    MessagePojo pojo = new MessagePojo();
    pojo.setGorderId(context.getCoreParams().getGorderId());
    pojo.setOrderId(context.getCoreParams().getOrderId());
    pojo.setContext(messageContext);

    String tag = UUID.randomUUID().toString();
    MessageEntity messageEntity = new MessageEntity(pojo, tag, messageName);
    OrderLoggerUtil.messageInfo(messageEntity);
    return tag;
  }


  /**
   * 发送消息
   * 
   * @param context
   * @param tempType
   * @param messageName
   * @return messageTag
   */
  public String send(Context context, String tempType, String messageName) {

    MessageContext messageContext = MessageContext.newInstance(context);

    MessagePojo pojo = new MessagePojo();
    pojo.setGorderId(context.getCoreParams().getGorderId());
    pojo.setOrderId(context.getCoreParams().getOrderId());
    pojo.setContext(messageContext);

    String tag = UUID.randomUUID().toString();
    try {
      // 记录到异步消息发送表
      // 先记录后发送确保能记录成功.这样发送失败也没关系
      MessageRecord messageRecord =
          new MessageRecord(tag, context.getCoreParams().getProductLine(), context.getCoreParams()
              .getGorderId(), context.getCoreParams().getOrderId(), new Timestamp(
              System.currentTimeMillis()), FastJsonUtil.toJson(pojo), messageName);
      messageRecordService.insertNewMessage(messageRecord);
      AbstractMessagePojo message = new AbstractMessagePojo(messageName);
      message.setCustomTag(tag);
      message.setTransferObject(pojo);
      RabbitMQSender.getInstance().send(message);
    } catch (Exception e) {
      OrderLoggerUtil.error("[异步消息][发送失败][发送异步消息异常]", e);
    }
    return tag;
  }

}
