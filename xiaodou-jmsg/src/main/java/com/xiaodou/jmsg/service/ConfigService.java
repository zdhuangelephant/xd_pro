package com.xiaodou.jmsg.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.constant.JMSGConstant;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.model.MessageConsumersConfig;
import com.xiaodou.jmsg.model.RabbitMQConfig;
import com.xiaodou.jmsg.prpo.RabbitMqProp;
import com.xiaodou.jmsg.service.facade.JmsgServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

public final class ConfigService {
	public static Map<String, MessageConfig> messageConfs;
	private static RabbitMQConfig rabbitConfs = new RabbitMQConfig();
	static {
		readConfigs(null);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getConfig(Class<T> clazz)
			throws InvalidParameterException {
		if (clazz == MessageConfig.class) {
			return (List<T>) new ArrayList<MessageConfig>(messageConfs.values());
		} else if (clazz == RabbitMQConfig.class) {
			return (List<T>) Lists.newArrayList(rabbitConfs);
		} else {
			throw new InvalidParameterException(
					"The class of config is not supported.");
		}
	}

	public static <T> RabbitMQConfig getRabbitMqConfig(Class<T> clazz)
			throws InvalidParameterException {
		if (clazz == RabbitMQConfig.class) {
			return rabbitConfs;
		} else {
			throw new InvalidParameterException(
					"The class of config is not supported.");
		}
	}

	/**
	 * 获取消息配置
	 * 
	 * @param msgName
	 *            消息名
	 * @return
	 */
	public static MessageConfig getMessageConfig(String msgName) {
		return messageConfs.get(msgName);
	}

	public static void readConfigs(Map<String, MessageConfig> m) {
		LoggerUtil.debug("Loading configs of rabbitMQ...");
		List<MessageConfig> mList = Lists.newArrayList();
		List<MessageConsumersConfig> cList = Lists.newArrayList();
		try {
			Map<String, Object> inputArgument = new HashMap<>();
			JmsgServiceFacade jmsgServiceFacade = SpringWebContextHolder
					.getBean("jmsgServiceFacade");
			mList = jmsgServiceFacade.getMessageConfigByCond(inputArgument);
			cList = jmsgServiceFacade
					.getMessageConsumersConfigByCond(inputArgument);
			initRabbitMqConfig();
		} catch (Exception e) {
			LoggerUtil.error("读取配置文件失败", e);
		}
		if (m == null) {
			messageConfs = mergeMessageConfigAndConsumers(mList, cList);
		} else {
			messageConfs = m;
		}
		JMSGConstant.messageConfs = FastJsonUtil.toJson(messageConfs);
		LoggerUtil.debug("Load completed.");
	}

	/**
	 * 包装config和consumers
	 * 
	 * @return
	 */
	public static Map<String, MessageConfig> mergeMessageConfigAndConsumers(
			List<MessageConfig> mList, List<MessageConsumersConfig> cList) {
		HashMap<String, List<MessageConsumersConfig>> consumerMap = new HashMap<String, List<MessageConsumersConfig>>();

		for (MessageConsumersConfig c : cList) {
			String name = c.getMessageName();
			if (!consumerMap.containsKey(name)) {
				consumerMap.put(name, new ArrayList<MessageConsumersConfig>());
			}

			consumerMap.get(name).add(c);
		}

		Map<String, MessageConfig> result = new HashMap<String, MessageConfig>();
		for (MessageConfig m : mList) {
			String name = m.getMessageName();
			if (consumerMap.containsKey(name)) {
				m.consumers = consumerMap.get(m.getMessageName());
				result.put(name, m);
				LoggerUtil.debug(String.format(
						"Load %1$s MessageConfig, contains %2$d consumers.",
						name, m.consumers.size()));
			}
		}
		return result;
	}

	/**
	 * 初始化rabbitMq信息
	 * 
	 * @return
	 */
	public static void initRabbitMqConfig() {
		rabbitConfs.setConnectionTimeOut(Integer.parseInt(RabbitMqProp
				.getParams("connectionTimeOut")));
		rabbitConfs.setFailedLogBaseDir((RabbitMqProp
				.getParams("failedLogBaseDir")));
		rabbitConfs.setMaxPoolSize(Integer.parseInt(RabbitMqProp
				.getParams("maxPoolSize")));
		rabbitConfs.setPassWord(RabbitMqProp.getParams("passWord"));
		rabbitConfs.setPort(Integer.parseInt(RabbitMqProp.getParams("port")));
		rabbitConfs.setReceiveTimeOut(Integer.parseInt(RabbitMqProp
				.getParams("receiveTimeOut")));
		rabbitConfs.setRequestedConnectionTimeOut(Integer.parseInt(RabbitMqProp
				.getParams("requestedConnectionTimeOut")));
		rabbitConfs.setRequestedHeartbeat(Integer.parseInt(RabbitMqProp
				.getParams("requestedHeartbeat")));
		rabbitConfs.setSendLogBaseDir(RabbitMqProp.getParams("sendLogBaseDir"));
		rabbitConfs.setSendTimeOut(Integer.parseInt(RabbitMqProp
				.getParams("sendTimeOut")));
		rabbitConfs.setServerIp(RabbitMqProp.getParams("serverIp"));
		rabbitConfs.setUserName(RabbitMqProp.getParams("userName"));
	}

}
