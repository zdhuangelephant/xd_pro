package com.xiaodou.jmsg.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.jmsg.dao.MessageConfigDao;
import com.xiaodou.jmsg.dao.MessageConsumersConfigDao;
import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.jmsg.model.MessageConsumersConfig;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("jmsgServiceFacade")
public class JmsgServiceFacadeImpl implements JmsgServiceFacade {
	@Resource
	MessageConfigDao messageConfigDao;
	@Resource
	MessageConsumersConfigDao messageConsumersConfigDao;
	@Override
	public List<MessageConfig> getMessageConfigByCond(
			Map<String, Object> inputArgument) {
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(MessageConfig.class));
		return messageConfigDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public List<MessageConsumersConfig> getMessageConsumersConfigByCond(
			Map<String, Object> inputArgument) {
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(MessageConsumersConfig.class));
		return messageConsumersConfigDao.findEntityListByCond(param, null).getResult();
	}
	
}
