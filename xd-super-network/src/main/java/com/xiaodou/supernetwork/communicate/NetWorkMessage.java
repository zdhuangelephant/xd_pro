package com.xiaodou.supernetwork.communicate;

import java.util.UUID;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;
import com.xiaodou.supernetwork.meta.NetWorkEvent;

/**
 * @name @see com.xiaodou.supernetwork.communicate.NetWorkMessage.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月22日
 * @description 网络间通信消息载体
 * @version 1.0
 */
public class NetWorkMessage implements MessageAble {

  /** from 源地址 */
  private TargetSocket from;

  /** to 目标地址 */
  private TargetSocket to;

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public TargetSocket getFrom() {
    return from;
  }

  public void setFrom(TargetSocket from) {
    this.from = from;
  }

  public TargetSocket getTo() {
    return to;
  }

  public void setTo(TargetSocket to) {
    this.to = to;
  }

  /**
   * @name @see com.xiaodou.supernetwork.communicate.NetWorkMessage.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年2月22日
   * @description 消息类型
   * @version 1.0
   */
  public enum NetMessageType {
    /** NET 内部消息 */
    NET,
    /** APP 应用消息 */
    APP
  }

  /**
   * 默认构造方法
   */
  public NetWorkMessage() {
    this.messageId = UUID.randomUUID().toString();
  }

  /** messageId 消息ID */
  private String messageId;

  private String entityJson;

  public <T> NetWorkMessageEntity<T> getEntity() {
    return FastJsonUtil.fromJsons(entityJson, new TypeReference<NetWorkMessageEntity<T>>() {});
  }

  @SuppressWarnings("rawtypes")
  public void setEntity(NetWorkMessageEntity entity) {
    this.messageId = String.format("%s|%s", this.messageId, entity.getEventName());
    this.entityJson = FastJsonUtil.toJson(entity);
  }

  @Override
  public synchronized final String uniqueMessageName() {
    return this.messageId;
  }

  @Override
  public synchronized final String messageContent() {
    return entityJson;
  }

  @Override
  public TargetSocket from() {
    return from;
  }

  @Override
  public TargetSocket to() {
    return to;
  }

  @Override
  public void setUniqueMessageName(String uniqueMessageName) {
    this.messageId = uniqueMessageName;
  }

  @Override
  public void setMessageContent(String messageContent) {
    this.entityJson = messageContent;
  }


  @Override
  public String toString() {
    return String.format("NetWorkMessage:[Message:%s|Body:%s]", this.messageId, this.entityJson);
  }

  /**
   * @name @see com.xiaodou.supernetwork.communicate.NetWorkMessage.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年2月22日
   * @description 消息实体
   * @version 1.0
   * @param <T> 泛型
   */
  public static class NetWorkMessageEntity<T> {
    /** eventName 事件名 */
    private String eventName;
    /** entity 消息对象 */
    private T entity;
    /** contextId 会话ID */
    private String contextId;
    /** messageType 消息类型 */
    private String messageType = NetMessageType.APP.name();

    public NetWorkMessageEntity() {}

    public NetWorkMessageEntity(NetWorkEvent event) {
      if (null != event) {
        eventName = event.name();
      }
    }

    public String getEventName() {
      return eventName;
    }

    public void setEventName(String eventName) {
      this.eventName = eventName;
    }

    public String getContextId() {
      return contextId;
    }

    public void setContextId(String contextId) {
      this.contextId = contextId;
    }

    public String getMessageType() {
      return messageType;
    }

    public void setMessageType(String messageType) {
      this.messageType = messageType;
    }

    public T getEntity() {
      return entity;
    }

    public void setEntity(T entity) {
      this.entity = entity;
    }
  }
}
