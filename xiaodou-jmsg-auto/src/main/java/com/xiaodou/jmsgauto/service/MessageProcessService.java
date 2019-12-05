package com.xiaodou.jmsgauto.service;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.MessageSender;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsgauto.broadcastresponse.BroadcastResponse;
import com.xiaodou.jmsgauto.broadcastresponse.BroadcastResponseItem;
import com.xiaodou.jmsgauto.model.MessageBody;
import com.xiaodou.jmsgauto.model.MessageLog;
import com.xiaodou.jmsgauto.service.facade.JmsgServerServiceFacade;
import com.xiaodou.jmsgauto.util.HostHelper;
import com.xiaodou.jmsgauto.vo.JmsgAlarm;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class MessageProcessService {
	private static MessageSender _sender = RabbitMQSender.getInstance();
	private static final MessageBody DEFAULT_MESSAGE_BODY = new MessageBody();
	private static final Map<UUID, Boolean> RECENT_MESSAGE_UUID_MAP = Collections
			.synchronizedMap(new FixedLinkedHashMap<UUID, Boolean>(700, 0.75f,
					true));

	/**
	 * 重新发送消息
	 * 
	 */
	public static void ProcessFailedLessMax(DefaultMessage message) {
		AbstractMessagePojo pojo = new AbstractMessagePojo(
				message.getMessageName());
		pojo.setTransferObject(message);
		pojo.setCustomTag(message.getCustomTag());
		_sender.send(pojo);
	}

	/**
	 * 获取MessageBody
	 * 
	 */
	public static MessageBody getSavedMessageBody(DefaultMessage message) {
		Map<String, Object> param = new HashMap<>();
		param.put("surfix", getShortDate(message.getSendTime()));
		param.put("messageId", message.getMessageID().toString());
		MessageBody messageBody;
		try {
			JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
					.getBean("jmsgServerServiceFacade");
			List<MessageBody> result = jmsgServerServiceFacade
					.getMessageBodyListByCond(param);
			if (result != null && result.size() > 0) {
				messageBody = result.get(0);
			} else {
				messageBody = null;
			}
		} catch (Exception e) {
			LoggerUtil.error("获取messageBody异常", e);
			LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"+message.getCustomTag()+" 报错信息:"+e.getMessage().toString()));
			messageBody = DEFAULT_MESSAGE_BODY; // 异常则返回默认MessageBody
		}
		return messageBody;
	}

	/**
	 * 创建MessageBody
	 * 
	 */
	public static boolean createMessageBody(DefaultMessage message,
			String queueName) {
		try {
			MessageBody messageBody = new MessageBody();
			messageBody.setSurfix(getShortDate(message.getSendTime()));
			messageBody.setContextId(message.getContextID().toString());
			messageBody.setCustomTag(message.getCustomTag());
			messageBody.setFailedCount(message.getFailedCount());
			messageBody.setFromClass(message.getSendFromClass());

			// 压缩字符串
			String compressData = zip(message.getTransferObjectJSON());
			compressData = "zip|" + compressData;
			int dataSize = compressData.length();

			messageBody.setMessageData(compressData);
			messageBody.setMessageSize(dataSize);

			messageBody.setMessageDataType(message.getTransferObjectTypeName());
			messageBody.setMessageId(message.getMessageID().toString());
			messageBody.setMessageName(message.getMessageName());
			messageBody.setMessageReceiveTime(new Timestamp(message
					.getReceiveTime().getTime()));
			messageBody.setMessageSendTime(new Timestamp(message.getSendTime()
					.getTime()));

			String priority = ConfigService.getMessageConfig(
					message.getMessageName()).getPriority();
			int pri = 255;
			try {
				pri = Integer
						.parseInt(priority.substring(priority.length() - 1));
			} catch (Exception e) {
			}
			messageBody.setPriority(pri);
			messageBody.setProcessServerIp(HostHelper.getHostAddress());
			messageBody.setProcessServerName(HostHelper.getHostName());
			messageBody.setProcessStatus("0");
			messageBody.setSendServerIp(message.getSendServerIP());
			messageBody.setSendServerName(message.getSendServerName());
			messageBody.setRouteKey(message.getRouteKey());
			messageBody.setQueueName(queueName);
			try {
				JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
						.getBean("jmsgServerServiceFacade");
				jmsgServerServiceFacade.addMessageBody(messageBody);
				message.setSaved(true);
			} catch (Exception e) {
				message.setSaved(false);
				Throwable cause = e.getCause();
				if (cause != null
						&& cause.getClass() == MySQLIntegrityConstraintViolationException.class) {
					return false; // 只有重复主键异常认为创建失败，其他异常忽略
				}
				LoggerUtil.error("persist message body error: ", e);
			}
		} catch (Exception e) {
			LoggerUtil.error("create message body error: ", e);
		}
		return true;
	}

	/**
	 * 改变Message状态
	 * 
	 */
	public static void setMessageBeginProcessStatus(DefaultMessage message) {
		try {
			MessageBody messageBody = new MessageBody();
			messageBody.setSurfix(getShortDate(message.getSendTime()));
			messageBody.setProcessStatus("1");
			messageBody.setBeginProcessTime(new Timestamp(System
					.currentTimeMillis()));
			Map<String, Object> inputArgument = new HashMap<>();
			inputArgument.put("messageId", message.getMessageID().toString());
			JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
					.getBean("jmsgServerServiceFacade");
			jmsgServerServiceFacade.updateMessageBody(inputArgument,
					messageBody);
		} catch (Exception e) {
			LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"+message.getCustomTag()+" 报错信息:"+e.getMessage().toString()));
			LoggerUtil.error("改变消息状态失败", e);
		}
	}

	/**
	 * 修改Message
	 * 
	 */
	public static void setMessageResult(DefaultMessage message,
			int processStatus, int result) {
		try {
			MessageBody messageBody = new MessageBody();
			messageBody.setSurfix(getShortDate(message.getSendTime()));
			messageBody.setProcessStatus(String.valueOf(processStatus));
			messageBody.setFailedCount(message.getFailedCount());
			messageBody.setResult(String.valueOf(result));
			messageBody.setEndProcessTime(new Timestamp(System
					.currentTimeMillis()));
			Map<String, Object> inputArgument = new HashMap<>();
			inputArgument.put("messageId", message.getMessageID().toString());
			JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
					.getBean("jmsgServerServiceFacade");
			jmsgServerServiceFacade.updateMessageBody(inputArgument,
					messageBody);
		} catch (Exception e) {
			LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"+message.getCustomTag()+" 报错信息:"+e.getMessage().toString()));
			LoggerUtil.error("修改messageBody失败", e);
		}
	}

	/**
	 * MessageLog增加
	 * 
	 */
	public static void saveMessageLog(DefaultMessage message,
			Date beginProcessTime, Date receiveTime, long processSpanTime,
			BroadcastResponse response) {
		try {
			// 4 记录消息执行结果
			MessageLog messageLog = new MessageLog();
			messageLog.setMessageId(message.getMessageID().toString());
			messageLog.setContextId(UUID.randomUUID().toString());
			messageLog.setCustomTag(message.getCustomTag());
			messageLog.setMessageName(message.getMessageName());
			messageLog.setBeginProcessTime(new Timestamp(beginProcessTime
					.getTime()));
			messageLog.setEndProcessTime(new Timestamp(System
					.currentTimeMillis()));
			messageLog.setMessageReceiveTime(new Timestamp(receiveTime
					.getTime()));
			if (response.isError()) {
				// 执行异常
				messageLog.setProcessResult(-1);
			} else {
				if (response.isFailed()) {
					// 执行失败
					messageLog.setProcessResult(1);
				} else {
					// 执行成功
					messageLog.setProcessResult(0);
				}
			}
			messageLog.setProcessServerIp(HostHelper.getHostAddress());
			messageLog.setProcessServerName(HostHelper.getHostName());
			messageLog.setProcessTimeSpan((int) processSpanTime);
			if (message.getDeadLetterCount() > 0) {
				// 死信处理
				messageLog.setProcessType(2);
			} else {
				if (message.getFailedCount()!=null&&Integer.parseInt(message.getFailedCount()) > 0) {
					// 重试处理
					messageLog.setProcessType(1);
				} else {
					// 正常处理
					messageLog.setProcessType(0);
				}
			}

			// 拼接处理结果日志文本
			StringBuilder processLog = new StringBuilder();
			for (int i = 0; i < response.getBroadcastResponseItemList().size(); i++) {
				BroadcastResponseItem resItem = response
						.getBroadcastResponseItemList().get(i);
				processLog.append(String.format("Consumer=%s|",
						resItem.getUrl()));
				processLog.append(String.format("ResponseCode=%d|",
						resItem.getResponseCode()));
				processLog.append(String.format("ResponseStr=%s|",
						resItem.getResponse()));
				processLog.append(String.format("Error=%s|",
						String.valueOf(resItem.isError())));
				processLog.append(String.format("Failed=%s|",
						String.valueOf(resItem.isFailed())));
				processLog.append(String.format("Interval: %d|",
						resItem.getInterval()));
				processLog.append(String.format("ExceptionMessage=%s|\r\n",
						resItem.getExceptionMsg()));
			}
			messageLog.setProcessLog(processLog.toString());
			messageLog.setSurfix(getShortDate(message.getSendTime()));
			JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
					.getBean("jmsgServerServiceFacade");
			jmsgServerServiceFacade.addMessageLog(messageLog);
		} catch (Exception e) {
			LoggerUtil.alarmInfo(new JmsgAlarm("错误消息:"+message.getCustomTag()+" 报错信息:"+e.getMessage().toString()));
			LoggerUtil.error("Save message log error: ", e);
		}
	}

	/**
	 * 获取日期拼接SQL
	 * 
	 */
    public  static String getShortDate(Date dateTime) {
		return new SimpleDateFormat("_yy_MM_dd").format(dateTime);
	}

	/**
	 * 检查消息
	 * 
	 */
	public static boolean checkRepeatMessageByMemory(DefaultMessage msg) {
		if (msg == null || msg.getProcessCount() > 0) { // 重发的消息不内存查重
			return false;
		}
		Boolean previous = RECENT_MESSAGE_UUID_MAP
				.put(msg.getMessageID(), true);
		return previous != null;
	}

	/**
	 * 压缩加密
	 * 
	 */
	private static String zip(String str) {
		if (str == null) {
			return null;
		}

		byte[] zipArray = zip(str.getBytes());
		return new String(Base64.encodeBase64(zipArray));
	}

	/**
	 * zip
	 * 
	 */
	private static byte[] zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	private static class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		private static final int MAX_ENTRIES = 500;

		/**
		 * 固定大小(500)LinkedHashMap, 达到大小后插入新元素时移除最老元素
		 * 
		 * @param initialCapacity
		 *            初始容量
		 * @param loadFactor
		 *            负载因子
		 * @param accessOrder
		 *            true为访问顺序策略，false为插入顺序策略
		 */
		public FixedLinkedHashMap(int initialCapacity, float loadFactor,
				boolean accessOrder) {
			super(initialCapacity, loadFactor, accessOrder);
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > MAX_ENTRIES;
		}
	}

}
