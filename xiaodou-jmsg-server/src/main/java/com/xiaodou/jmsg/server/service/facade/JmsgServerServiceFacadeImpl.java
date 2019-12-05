package com.xiaodou.jmsg.server.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.jmsg.server.dao.MessageBodyDao;
import com.xiaodou.jmsg.server.dao.MessageLogDao;
import com.xiaodou.jmsg.server.dao.MessageQueueSettingDao;
import com.xiaodou.jmsg.server.dao.RelationDao;
import com.xiaodou.jmsg.server.dao.ServerQueueSettingDao;
import com.xiaodou.jmsg.server.model.MessageBody;
import com.xiaodou.jmsg.server.model.MessageLog;
import com.xiaodou.jmsg.server.model.MessageQueueSetting;
import com.xiaodou.jmsg.server.model.Relation;
import com.xiaodou.jmsg.server.model.ServerQueueSetting;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("jmsgServerServiceFacade")
public class JmsgServerServiceFacadeImpl implements JmsgServerServiceFacade {
	@Resource
	ServerQueueSettingDao serverQueueSettingDao;

	@Resource
	MessageQueueSettingDao messageQueueSetting;

	@Resource
	MessageBodyDao messageBodyDao;
	@Resource
	MessageLogDao messageLogDao;
	@Resource
	RelationDao relationDao;

	@Override
	public List<ServerQueueSetting> getServerQueueSettingListByCond(
			Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(ServerQueueSetting.class));
		return serverQueueSettingDao.findEntityListByCond(param, null)
				.getResult();
	}

	@Override
	public ServerQueueSetting getServerQueueSettingListById(String id) {
		// TODO Auto-generated method stub
		ServerQueueSetting entity = new ServerQueueSetting();
		entity.setId(id);
		return serverQueueSettingDao.findEntityById(entity);
	}

	@Override
	public void addServerQueueSetting(ServerQueueSetting s) {
		// TODO Auto-generated method stub
		serverQueueSettingDao.addEntity(s);
	}

	@Override
	public void editServerQueueSetting(ServerQueueSetting s) {
		// TODO Auto-generated method stub
		serverQueueSettingDao.updateEntityById(s);

	}

	@Override
	public void delServerQueueSetting(ServerQueueSetting s) {
		serverQueueSettingDao.deleteEntityById(s);
	}

	@Override
	public List<MessageQueueSetting> getQueueListByCond(
			Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(MessageQueueSetting.class));
		return messageQueueSetting.findEntityListByCond(param, null)
				.getResult();
	}

	@Override
	public MessageQueueSetting getQueueListById(String id) {
		// TODO Auto-generated method stub
		MessageQueueSetting entity = new MessageQueueSetting();
		entity.setId(id);
		return messageQueueSetting.findEntityById(entity);
	}

	@Override
	public void addQueue(MessageQueueSetting s) {
		// TODO Auto-generated method stub
		messageQueueSetting.addEntity(s);
	}

	@Override
	public void editQueue(MessageQueueSetting s) {
		// TODO Auto-generated method stub
		messageQueueSetting.updateEntityById(s);

	}

	@Override
	public void delQueue(MessageQueueSetting s) {
		// TODO Auto-generated method stub
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("id", s.getId());
		// TODO Auto-generated method stub
		messageQueueSetting.deleteEntityById(s);

	}

	@Override
	public List<MessageBody> getMessageBodyListByCond(
			Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(MessageBody.class));
		return messageBodyDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public MessageBody getMessageBodyById(String id) {
		// TODO Auto-generated method stub
		MessageBody entity = new MessageBody();
		entity.setMessageId(id);
		return messageBodyDao.findEntityById(entity);
	}

	@Override
	public List<MessageLog> getMessageLogListByCond(
			Map<String, Object> inputArgument, Map<String, Object> outputField) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(outputField);
		return messageLogDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public MessageLog getMessageLogById(String id) {
		// TODO Auto-generated method stub
		MessageLog entity = new MessageLog();
		entity.setMessageId(id);
		return messageLogDao.findEntityById(entity);
	}

	@Override
	public MessageBody addMessageBody(MessageBody messageBody) {
		// TODO Auto-generated method stub
		return messageBodyDao.addEntity(messageBody);
	}

	@Override
	public boolean updateMessageBody(Map<String, Object> input,
			MessageBody messageBody) {
		// TODO Auto-generated method stub
		Map<String, Object> value = Maps.newHashMap();
		value.put("surfix", messageBody.getSurfix());
    	CommUtil.transferFromVO2Map(value, messageBody);
    	messageBody.setSurfix(messageBody.getSurfix());
		return messageBodyDao.updateEntityByCond(input, value);
	}

	@Override
	public MessageLog addMessageLog(MessageLog messageLog) {
		// TODO Auto-generated method stub
		return messageLogDao.addEntity(messageLog);
	}

	@Override
	public List<Relation> getRelationByCond(Map<String, Object> inputArgument) {
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(Relation.class));
		return relationDao.findEntityListByCond(param, null).getResult();
	}
}
