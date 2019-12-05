package com.xiaodou.jmsg.server.scheduled;

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
import com.xiaodou.jmsg.server.constant.InitConstant;
import com.xiaodou.jmsg.server.model.MessageQueueSetting;
import com.xiaodou.jmsg.server.model.Relation;
import com.xiaodou.jmsg.server.model.ServerQueueSetting;
import com.xiaodou.jmsg.server.service.MessageServerService;
import com.xiaodou.jmsg.server.service.facade.JmsgServerServiceFacade;
import com.xiaodou.jmsg.service.ConfigService;
import com.xiaodou.jmsg.service.RabbitConnectionPoolService;
import com.xiaodou.jmsg.service.facade.JmsgServiceFacade;
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
public class ConfTask {
	public static void excute() {
		LoggerUtil.debug("定时检测配置信息");
		checkData();
		checkServerQueueSetting();
		checkPoolConfig();
	}

	public static void checkPoolConfig() {
		try {
			List<RabbitMQConfig> configs = ConfigService
					.getConfig(RabbitMQConfig.class);
			if (configs != null && !configs.isEmpty()) {
				RabbitMQConfig configItem = configs.get(0);
				if (!(FastJsonUtil.toJson(configItem)
						.equals(JMSGConstant.poolConfigs))) {
					RabbitConnectionPoolService.initPool(configItem);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("Read config error: ", e);
			return;
		}
	}

	public static void checkServerQueueSetting() {
		JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
				.getBean("jmsgServerServiceFacade");
		String serverName = InitConstant.serverName;
		ServerQueueSetting result = null;
		try {
			Map<String, Object> inputArgument = new HashMap<>();
			inputArgument.put("serverName", serverName);
			List<ServerQueueSetting> sqSettings = jmsgServerServiceFacade
					.getServerQueueSettingListByCond(inputArgument);
			if (sqSettings != null && !sqSettings.isEmpty()) {
				result = sqSettings.get(0);
				if (result.getGroupId() == 0 || !result.isEnable()) {
				} else {
					result.setServerName(serverName);
					Map<String, Object> input = new HashMap<>();
					input.put("groupId", result.getGroupId());
					List<Relation> queueIdList = jmsgServerServiceFacade
							.getRelationByCond(input);
					Map<String, MessageQueueSetting> settings = new HashMap<>();
					for (Relation r : queueIdList) {
						Map<String, Object> input2 = new HashMap<>();
						input2.put("id", r.getQueueId());
						List<MessageQueueSetting> queueSetting = jmsgServerServiceFacade
								.getQueueListByCond(input2);
						if (queueSetting != null
								&& !queueSetting.isEmpty()
								&& !settings.containsKey(queueSetting.get(0)
										.getQueueName())) {
							settings.put(queueSetting.get(0).getQueueName(),
									queueSetting.get(0));
						}
					}
					result.setQueueSettings(settings);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("Read server setting error: ", e);
			return;
		}
		String serverQueueSetting = FastJsonUtil.toJson(result);
		if (!serverQueueSetting.equals(InitConstant.serverQueueSetting)) {
			MessageServerService m = new MessageServerService();
			m.setQueueNames(result);
		}
	}

	public static void checkData() {
		try {
			JmsgServiceFacade jmsgServiceFacade = SpringWebContextHolder
					.getBean("jmsgServiceFacade");
			Map<String, MessageConfig> messageConfs;
			List<MessageConfig> mList = Lists.newArrayList();
			List<MessageConsumersConfig> cList = Lists.newArrayList();

			Map<String, Object> inputArgument = new HashMap<>();
			mList = jmsgServiceFacade.getMessageConfigByCond(inputArgument);
			cList = jmsgServiceFacade
					.getMessageConsumersConfigByCond(inputArgument);

			messageConfs = ConfigService.mergeMessageConfigAndConsumers(mList,
					cList);
			String m = FastJsonUtil.toJson(messageConfs);
			if (!(m.equals(JMSGConstant.messageConfs))) {
				ConfigService.readConfigs(messageConfs);
			}
		} catch (Exception e) {
			LoggerUtil.error("读取配置文件失败", e);
			return;
		}
	}
}
