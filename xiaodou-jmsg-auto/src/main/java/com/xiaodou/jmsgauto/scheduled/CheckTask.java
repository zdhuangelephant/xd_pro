package com.xiaodou.jmsgauto.scheduled;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.model.MessageConsumersConfig;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsg.service.facade.JmsgServiceFacade;
import com.xiaodou.jmsgauto.broadcastresponse.BroadcastResponse;
import com.xiaodou.jmsgauto.constant.InitConstant;
import com.xiaodou.jmsgauto.model.MessageBody;
import com.xiaodou.jmsgauto.model.sqlite.FailMessage;
import com.xiaodou.jmsgauto.service.MessageBusService;
import com.xiaodou.jmsgauto.service.MessageProcessService;
import com.xiaodou.jmsgauto.service.facade.JmsgServerServiceFacade;
import com.xiaodou.jmsgauto.service.facade.JmsgServerSqliteServiceFacade;
import com.xiaodou.jmsgauto.vo.JmsgAlarm;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.jmsg.server.scheduled.Task.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 定时任务检测消息配置，重新注入配置
 * @version 1.0
 */
public class CheckTask {
	public static void excute() {
		LoggerUtil.debug("定时检测错误消息");
		if(InitConstant.autoState==1){
			return;
		}
		checkSqliteMessage();
		InitConstant.autoState=0;
	}

	public static void checkSqliteMessage() {
		// TODO Auto-generated method stub
		InitConstant.autoState=1;
		Map<String, Object> cond = Maps.newConcurrentMap();
		JmsgServiceFacade jmsgServiceFacade = SpringWebContextHolder.getBean("jmsgServiceFacade");
		List<MessageConsumersConfig>  configList=jmsgServiceFacade.getMessageConsumersConfigByCond(cond);
		for(MessageConsumersConfig m:configList){
			send(m.getUrl());
		}	
	}

	public static void send(String url){
		JmsgServerSqliteServiceFacade jmsgServerSqliteServiceFacade = SpringWebContextHolder.getBean("jmsgServerSqliteServiceFacade");
		JmsgServerServiceFacade jmsgServerServiceFacade=SpringWebContextHolder.getBean("jmsgServerServiceFacade");
		try {
			Map<String, Object> cond = Maps.newConcurrentMap();
			cond.put("uniqueUrl", url);
			List<FailMessage> failMessageList = jmsgServerSqliteServiceFacade.getFailMessageListByCond(cond);
			for (FailMessage failMessage : failMessageList) {
				if (StringUtils.isNotBlank(failMessage.getCustomTag())) {
					Map<String, Object> param = new HashMap<>();
					param.put("customTag", failMessage.getCustomTag());
					param.put("surfix", MessageProcessService.getShortDate(new Date(failMessage.getCreateTime().getTime())));	
					List<MessageBody> messageList = jmsgServerServiceFacade.getMessageBodyListByCond(param);
					if (messageList != null && messageList.size() > 0) {
						if (messageList.get(0).getResult() != null&& messageList.get(0).getResult().equals("0")) {
							jmsgServerSqliteServiceFacade.delFailMessage(failMessage.getCustomTag());
						} else {
							reReceive(failMessage.getMessage());
						}
					}else{
						jmsgServerSqliteServiceFacade.delFailMessage(failMessage.getCustomTag());
					}
				}
			}} catch (Exception e) {
				return;
			}
	}
	
	public static void reReceive(String msg) throws Exception {
		if (StringUtils.isNotBlank(msg)) {
		      DefaultMessage message = CodecHelper.parseJsonToObject(msg, DefaultMessage.class);
			Date receiveTime = new Date(System.currentTimeMillis());
			StringBuilder sb = null;
			MessageConfig mbc = null;
			MessageBody savedMessageBody = null;
			long checkConfigTime = 0;
			check(message, sb, mbc, savedMessageBody, checkConfigTime, receiveTime);
		}
	}

	public static void check(com.xiaodou.jmsg.entity.DefaultMessage message,
			StringBuilder sb, MessageConfig mbc, MessageBody savedMessageBody,
			long checkConfigTime, Date receiveTime) throws Exception {
		if (Calendar.getInstance().get(Calendar.SECOND) == 1) {
			// 记录1/60的日志
			sb = new StringBuilder();
			sb.append("Broadcast|");
		}
		long startTime = System.currentTimeMillis();
		// 1.l 消息为空或消息名为空则丢弃
		if (message == null) {
			return;
		}
		if (message.getMessageName().isEmpty()) {
			return;
		}

		if (sb != null) {
			sb.append(message.getMessageName());
			sb.append("|");
		}

		// 1.1 配置信息检查
		mbc = ConfigService.getMessageConfig(message.getMessageName());
		if (mbc == null) {
			LoggerUtil.debug("Can not find the message '%s' config!");
			return;
		}

		if (mbc.consumers == null || mbc.consumers.size() <= 0) {
			return;
		}

		checkConfigTime = System.currentTimeMillis();
		if (sb != null) {
			sb.append(String.format("Conf Check:%d|", checkConfigTime
					- startTime));
		}
		if (message.getSendFromClass().equals(
				"com.xiaodou.dashboard.web.controller.jmsg.JmsgController")
				|| message.getSendFromClass().equals(
						"com.xiaodou.logmonitor.bolt.JmsgMessageBolt")) {
		} else if (MessageProcessService.checkRepeatMessageByMemory(message)) {
			// 检查内存,消息重复则退出
			return;
		}
		savedMessageBody = MessageProcessService.getSavedMessageBody(message);
		if (message.getSendFromClass().equals(
				"com.xiaodou.dashboard.web.controller.jmsg.JmsgController")
				|| message.getSendFromClass().equals(
						"com.xiaodou.logmonitor.bolt.JmsgMessageBolt")
				|| message.getSendFromClass().equals(
						"com.xiaodou.dashboard.job.SyncJmsgMessageJob")) {
		} else if (savedMessageBody != null
				&& savedMessageBody.getProcessStatus().equals("2")) {
			// 存在MessageBody且处理完成则重复,不再处理
			return;
		}
		long checkRepeatTime = System.currentTimeMillis();
		if (sb != null) {
			sb.append(String.format("CheckRepeatMessage:%d", checkRepeatTime
					- checkConfigTime));
		}
		// 2.保存数据初步处理
		save(message, sb, mbc, savedMessageBody, checkConfigTime, receiveTime);
	}

	public static void save(com.xiaodou.jmsg.entity.DefaultMessage message,
			StringBuilder sb, MessageConfig mbc, MessageBody savedMessageBody,
			long checkConfigTime, Date receiveTime) throws Exception {
		// 首次接收的消息，保存消息体.
		if (!message.isSaved() && savedMessageBody == null) {
			if (!MessageProcessService.createMessageBody(message,
					message.getQueueName())) {
				return;
			}
		}

		long saveMessageTime = System.currentTimeMillis();
		if (sb != null) {
			sb.append(String.format("Save body:%d|", saveMessageTime
					- checkConfigTime));
		}

		// 首次接受的消息；初始化ConsumerExecutedResultDic，ConsumerTypeFullNameConsumerStrDic
		if (message.getProcessCount() <= 0) {
			// 首次接收的消息，所有consumer均未执行，相当于全都是失败
			HashMap<String, Boolean> resultDic = new HashMap<>();
			for (int i = 0; i < mbc.consumers.size(); i++) {
				resultDic.put(mbc.consumers.get(i).getUrl(), false);
			}
			message.setConsumerExcutedResultDic(resultDic);
		}

		MessageProcessService.setMessageBeginProcessStatus(message);
		// 标识开始处理
		// 3.处理以及异常处理
		handle(message, sb, mbc, savedMessageBody, checkConfigTime, receiveTime);
	}

	public static void handle(com.xiaodou.jmsg.entity.DefaultMessage message,
			StringBuilder sb, MessageConfig mbc, MessageBody savedMessageBody,
			long checkConfigTime, Date receiveTime) throws Exception {
		// 3 将消息发送到MessageBus进行广播
		Date beginProcessTime = new Date(System.currentTimeMillis());
		long beforeBroadCast = System.currentTimeMillis();
		BroadcastResponse response = MessageBusService.broadcast(message, mbc);

		long afterBroadCast = System.currentTimeMillis();

		if (sb != null) {
			sb.append(String.format("Msg Broadcast:%d|", afterBroadCast
					- beforeBroadCast));
		}
		// 5 消息失败时，当消息没有达到最大失败次数时，进行重试；达到则存档

		int result = 0;
		int processStatus = 1;
		if (response.isFailedOrError()) {
			if (message.getFailedCount() != null
					&& Integer.valueOf(message.getFailedCount()) >= mbc
							.getMaxRetryCount()) {
				// 达到最大失败次数
				if (response.isError()) {
					result = -1;
				} else {
					result = 1;
				}
				LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"
						+ message.getCustomTag()));
				// 将消息处理状态改为“已处理”
				processStatus = 2;
			} else {
				if (response.isError()) {
					result = -1;
				} else {
					result = 1;
				}
				// 未达到最大失败次数
				MessageProcessService.ProcessFailedLessMax(message);
			}
		} else {
			// 将消息处理状态改为“已处理”
			processStatus = 2;
		}

		// 错误消息处理
		JmsgServerSqliteServiceFacade jmsgServerSqliteServiceFacade = SpringWebContextHolder
				.getBean("jmsgServerSqliteServiceFacade");
		String customTag = message.getCustomTag();
		MessageProcessService.setMessageResult(message, processStatus, result);

		long recordResultTime = System.currentTimeMillis();
		if (sb != null) {
			sb.append(String.format("ProcessResult:%d\r\n", recordResultTime
					- afterBroadCast));
		}

		// 记录消息执行结果
		MessageProcessService.saveMessageLog(message, beginProcessTime,
				receiveTime, afterBroadCast - beforeBroadCast, response);
		if (result == 0||result==1) {
			if (StringUtils.isNotBlank(customTag)) {
				jmsgServerSqliteServiceFacade.delFailMessage(customTag);
			}
		} else if(result == -1) {
			throw new Exception();
		} 
	}
}
