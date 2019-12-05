package com.xiaodou.jmsg.client.scheduled;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.amqp.connectpool.RabbitConnectPool;
import com.xiaodou.amqp.connectpool.RabbitSendProxy;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.ActionEntity;
import com.xiaodou.common.util.log.model.MessageEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.Constants;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.client.vo.JmsgClientAlarm;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsg.service.RabbitConnectionPoolService;
import com.xiaodou.jmsg.service.facade.SqliteServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;
/**
 * @name @see com.xiaodou.jmsg.server.scheduled.Task.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 定时任务检测服务器连接状态
 * @version 1.0
 */
public class Task {
	public static void excute() {
		LoggerUtil.debug("定时发送未成功发送的消息");
		if(Constants.FAIL_MESSAGE_STATE==1){
			return;
		}
		checkNode();
		Constants.FAIL_MESSAGE_STATE=0;
	}
	
	public static void checkNode(){
		try{
			Constants.FAIL_MESSAGE_STATE=1;
			SqliteServiceFacade sqliteServiceFacade = SpringWebContextHolder
					.getBean("sqliteServiceFacade");
			Map<String, Object> cond=Maps.newConcurrentMap();
			List<FailSendMessage> failSendList=sqliteServiceFacade.getFailSendMessageListByCond(cond);
		if(failSendList.size()>0){
			LoggerUtil.alarmInfo(new JmsgClientAlarm("未成功发送的消息数量:"+failSendList.size()));		
		}
		for(FailSendMessage failSendMessage:failSendList){  
			    AbstractMessagePojo abstractMessagePojo=FastJsonUtil.fromJson(failSendMessage.getMessage(), AbstractMessagePojo.class);
			    try {
			       reSend(abstractMessagePojo);
			       sqliteServiceFacade.delFailSendMessage(failSendMessage.getCustomTag());
			    } catch (AmqpClientException e) {
			    	LoggerUtil.error("处理未成功发送的消息失败",e);
			    	LoggerUtil.alarmInfo(new JmsgClientAlarm("定时任务向rabbitMq未成功发送的消息:"+failSendMessage.getMessage()));		
			    } 
	    }
		}catch(Exception e) {		
			LoggerUtil.error("定时发送未成功发送的消息失败",e);
		}
	}
	
	public static void reSend( AbstractMessagePojo abstractMessagePojo) throws Exception{
		MessageConfig matchedMsgConf = ConfigService.getMessageConfig(abstractMessagePojo.getMessageName());
	    if (null == matchedMsgConf) {
	      return;
	    }
	    DefaultMessage msg = RabbitMQSender.parseObjToDefaultMessage(abstractMessagePojo.getTransferObject());
	    msg.setCustomTag(abstractMessagePojo.getCustomTag());
	    msg.setMessageName(abstractMessagePojo.getMessageName());
	    msg.setRouteKey(abstractMessagePojo.getMessageName() + "." + matchedMsgConf.getPriority());
	    String msgJson = CodecHelper.parseObjectToJson(msg);
	    RabbitConnectPool pool = RabbitConnectionPoolService.getPool();
	    RabbitSendProxy sendProxy = null;
	    try {
	      sendProxy = pool.getSendingConnection(false);
	      sendProxy.send(matchedMsgConf.getExchangeName(), msgJson, msg.getRouteKey());
	      MessageEntity<Object> messageEntity =
	          new MessageEntity<Object>(abstractMessagePojo.getTransferObject(), abstractMessagePojo.getCustomTag(), abstractMessagePojo.getMessageName());
	      LoggerUtil.messageInfo(messageEntity);
	      ActionEntity action = new ActionEntity();
	      action.setLogName("jmsg_message");
	      action.setActionInfo(messageEntity);
	      LoggerUtil.actionInfo(action);
	    } finally {
	      if (null != sendProxy) {
	        pool.returnToPool(sendProxy);
	        sendProxy = null;
	      }
	    }
	}
}
