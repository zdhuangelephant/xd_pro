package com.xiaodou.jmsg.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.xiaodou.amqp.connectpool.RabbitConnectPool;
import com.xiaodou.amqp.connectpool.RabbitSendProxy;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.aopagent.util.TraceWrapper;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.ActionEntity;
import com.xiaodou.common.util.log.model.MessageEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.MessageSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsg.service.FailSendMessageService;
import com.xiaodou.jmsg.service.RabbitConnectionPoolService;

public class RabbitMQSender implements MessageSender {

  private RabbitMQSender() {}

  private static final MessageSender instance = new RabbitMQSender();

  public static MessageSender getInstance() {
    return instance;
  }

  private final static String CLASS_NAME = RabbitMQSender.class.getName();

  static {
    init();
  }

  private static void init() {
    try {
      Class.forName(ConfigService.class.getName());
      Class.forName(CodecHelper.class.getName());
      Class.forName(PersistentMessageQueue.class.getName());
    } catch (ClassNotFoundException e) {
      LoggerUtil.error("RabbitSender初始化异常", e);
      throw new RuntimeException(e.getCause());
    }
    RabbitConnectionPoolService.getPool().setConfirmCallback(new MessageConfirmCallback());
  }

  @Override
  public <T extends AbstractMessagePojo> void send(T message) {
    send(message.getTransferObject(), message.getCustomTag(), message.getMessageName(), true);
  }

  public void send(Object transferObject, String customTag, String messageName, boolean reSend) {
    MessageConfig matchedMsgConf = ConfigService.getMessageConfig(messageName);
    if (null == matchedMsgConf) {
      return;
    }
    DefaultMessage msg = parseObjToDefaultMessage(transferObject);
    msg.setCustomTag(customTag);
    msg.setMessageName(messageName);
    msg.setRouteKey(messageName + "." + matchedMsgConf.getPriority());
/*	if(TraceWrapper.getWrapper()!=null&&TraceWrapper.getWrapper().getTraceParam()!=null){
	    msg.setTraceId(TraceWrapper.getWrapper().getTraceParam().getTraceId());
	    msg.setMyId(TraceWrapper.getWrapper().getTraceParam().getMyId());
	}*/
    String msgJson = CodecHelper.parseObjectToJson(msg);

    RabbitConnectPool pool = RabbitConnectionPoolService.getPool();
    RabbitSendProxy sendProxy = null;
    try {
      sendProxy = pool.getSendingConnection(false);
      sendProxy.send(matchedMsgConf.getExchangeName(), msgJson, msg.getRouteKey());
      MessageEntity<Object> messageEntity =
          new MessageEntity<Object>(transferObject, customTag, messageName);
      LoggerUtil.messageInfo(messageEntity);
      ActionEntity action = new ActionEntity();
      action.setLogName("jmsg_message");
      action.setActionInfo(msg);
      LoggerUtil.actionInfo(action);
    } catch (AmqpClientException e) {
    	FailSendMessage failSendMessage=new FailSendMessage();
    	failSendMessage.setCustomTag(customTag);
    	failSendMessage.setMessageName(messageName);
    	failSendMessage.setReSend(reSend);
    	AbstractMessagePojo pojo = new AbstractMessagePojo(messageName);
    	pojo.setCustomTag(customTag);
    	pojo.setTransferObject(transferObject);
    	failSendMessage.setMessage(FastJsonUtil.toJson(pojo));
    	failSendMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	failSendMessage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    	FailSendMessageService.saveFailSendMessage(failSendMessage);
        processSendError(e, msgJson, reSend);
    } finally {
      if (null != sendProxy) {
        pool.returnToPool(sendProxy);
        sendProxy = null;
      }
    }
  }

  public static void processSendError(AmqpClientException e, String msg, boolean reSend) {
    if (reSend) {
      PersistentMessageQueue.getInstance().putQueue(msg);
    }
    LoggerUtil.error(msg, e);
  }

  public static DefaultMessage parseObjToDefaultMessage(Object obj) {
    DefaultMessage msg;

    if (obj instanceof DefaultMessage) {
      msg = (DefaultMessage) obj;
      if (msg.getTransferObjectJSON() == null) {
        msg.setTransferObjectJSON("null");
        msg.setTransferObjectTypeName("nullObject");
      }
      if (msg.getTransferObjectTypeName() == null) {
        msg.setTransferObjectTypeName("unset");
      }
    } else {
      msg = new DefaultMessage();

      msg.setDeadLetterCount(0);
      msg.setFailedCount("0");
      msg.setProcessCount(0);

      msg.setSaved(false);
      msg.setTransferObjectJSON(CodecHelper.parseObjectToJson(obj));
      msg.setTransferObjectTypeName(obj == null ? "nullObject" : obj.getClass().getName());
    }

    if (msg.getContextID() == null) {
      msg.setContextID(UUID.randomUUID());
    }
    if (msg.getMessageID() == null) {
      msg.setMessageID(UUID.randomUUID());
    }
    if (msg.getSendTime() == null) {
      msg.setSendTime(new Date());
    }

    if (msg.getSendServerIP() == null) {
      String ip = InetAddress.getLoopbackAddress().getHostAddress();
      msg.setSendServerIP(ip == null ? "unKnownIP" : ip);
    }
    if (msg.getSendServerName() == null) {
      try {
        String hostName = InetAddress.getLocalHost().getHostName();
        msg.setSendServerName(hostName == null ? "unKnownHostName" : hostName);
      } catch (UnknownHostException e) {
        msg.setSendServerName("getHostNameError");
        LoggerUtil.error("getHostNameError", e);
      }
    }
    if (msg.getSendFromClass() == null) {
      StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
      for (int i = 0; i < stackTrace.length; i++) {
        String stackClassName = stackTrace[i].getClassName();
        if (stackClassName != CLASS_NAME) {
          msg.setSendFromClass(stackClassName);
          break;
        }
        msg.setSendFromClass(CLASS_NAME);
      }
    }

    return msg;
  }
}
