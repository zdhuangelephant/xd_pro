package com.xiaodou.jmsg.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.model.MessageConsumersConfig;

public interface JmsgServiceFacade {
	List<MessageConfig> getMessageConfigByCond(Map<String, Object> inputArgument);
	List<MessageConsumersConfig> getMessageConsumersConfigByCond(Map<String, Object> inputArgument);
}
