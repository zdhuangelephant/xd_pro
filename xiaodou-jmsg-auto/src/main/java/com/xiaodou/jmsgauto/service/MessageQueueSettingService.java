package com.xiaodou.jmsgauto.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsgauto.constant.InitConstant;
import com.xiaodou.jmsgauto.model.MessageQueueSetting;
import com.xiaodou.jmsgauto.model.Relation;
import com.xiaodou.jmsgauto.model.ServerQueueSetting;
import com.xiaodou.jmsgauto.service.facade.JmsgServerServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class MessageQueueSettingService {
	/**
	 * 检测配置信息
	 * 
	 */
	public ServerQueueSetting getServerSetting(String serverName) {
		ServerQueueSetting result = null;
		try {
			Map<String, Object> inputArgument = new HashMap<>();
			inputArgument.put("serverName", serverName);
			JmsgServerServiceFacade jmsgServerServiceFacade = SpringWebContextHolder
					.getBean("jmsgServerServiceFacade");
			List<ServerQueueSetting> sqSettings = jmsgServerServiceFacade
					.getServerQueueSettingListByCond(inputArgument);
			if (sqSettings != null && !sqSettings.isEmpty()) {
				result = sqSettings.get(0);
				if (result.getGroupId() == 0 || !result.isEnable()) {
					return result;
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
			LoggerUtil.error("Read server setting error", e);
		}
		InitConstant.serverQueueSetting = FastJsonUtil.toJson(result);
		return result;
	}

}
