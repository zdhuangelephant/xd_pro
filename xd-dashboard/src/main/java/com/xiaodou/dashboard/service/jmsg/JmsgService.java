package com.xiaodou.dashboard.service.jmsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConfig;
import com.xiaodou.dashboard.model.jsmg.JmsgMessageConsumersConfig;
import com.xiaodou.dashboard.model.jsmg.MessageQueueSetting;
import com.xiaodou.dashboard.model.jsmg.Relation;
import com.xiaodou.dashboard.model.jsmg.ServerQueueSetting;
import com.xiaodou.dashboard.service.facade.DashboardJmsgServiceFacade;
import com.xiaodou.dashboard.vo.jmsg.request.MessageConfigRequest;
import com.xiaodou.dashboard.vo.jmsg.request.MessageConsumersRequest;
import com.xiaodou.dashboard.vo.jmsg.request.MessageQueueSettingRequest;
import com.xiaodou.dashboard.vo.jmsg.request.RelationRequest;
import com.xiaodou.dashboard.vo.jmsg.request.ServerQueueSettingRequest;

import org.springframework.stereotype.Service;

@Service("jmsgService")
public class JmsgService {
	@Resource
	DashboardJmsgServiceFacade JmsgServiceFacade;

	public List<ServerQueueSetting> getAllServerQueueSettingList() {
		Map<String, Object> input = Maps.newHashMap();
		Map<String, Object> output = Maps.newHashMap();
		CommUtil.getGeneralField(output, ServerQueueSetting.class);
		return JmsgServiceFacade.getAllServerQueueSettingList(input, output);
	}

	public void addServerQueueSetting(ServerQueueSettingRequest request) {
		ServerQueueSetting s = new ServerQueueSetting();
		s.setGroupId(request.getGroupId());
		s.setServerName(request.getServerName());
		s.setEnable(request.getEnable());
		JmsgServiceFacade.addServerQueueSetting(s);
	}

	public void editServerQueueSetting(ServerQueueSettingRequest request) {
		ServerQueueSetting s = new ServerQueueSetting();
		s.setId(request.getId());
		s.setGroupId(request.getGroupId());
		s.setServerName(request.getServerName());
		s.setEnable(request.getEnable());
		JmsgServiceFacade.editServerQueueSetting(s);

	}

	public void delServerQueueSetting(ServerQueueSettingRequest request) {
		ServerQueueSetting s = new ServerQueueSetting();
		s.setId(request.getId());
		JmsgServiceFacade.delServerQueueSetting(s);

	}

	public List<MessageQueueSetting> getAllQueueList() {
		Map<String, Object> input = Maps.newHashMap();
		Map<String, Object> output = Maps.newHashMap();
		CommUtil.getGeneralField(output, MessageQueueSetting.class);
		return JmsgServiceFacade.getAllQueueList(input, output);
	}

	public void addQueue(MessageQueueSettingRequest request) {
		MessageQueueSetting s = new MessageQueueSetting();
		s.setParallelCount(request.getParallelCount());
		s.setQos(request.getQos());
		s.setQueueName(request.getQueueName());
		JmsgServiceFacade.addQueue(s);
	}

	public void editQueue(MessageQueueSettingRequest request) {
		MessageQueueSetting s = new MessageQueueSetting();
		s.setId(request.getId());
		s.setParallelCount(request.getParallelCount());
		s.setQos(request.getQos());
		s.setQueueName(request.getQueueName());
		JmsgServiceFacade.editQueue(s);

	}

	public void delQueue(MessageQueueSettingRequest request) {
		MessageQueueSetting s = new MessageQueueSetting();
		s.setId(request.getId());
		JmsgServiceFacade.delQueue(s);

	}
	
	public List<Relation> getRelationList(RelationRequest request) {
		Map<String, Object> input = Maps.newHashMap();
		input.put("groupId", request.getGroupId());
		Map<String, Object> output = Maps.newHashMap();
		CommUtil.getGeneralField(output, Relation.class);
		return JmsgServiceFacade.getListByCond(input, output);
	}

	public void addRelation(String groupId,List<String> ids) {
		for (String id:ids){
			Relation r = new Relation();
			r.setGroupId(groupId);
			r.setQueueId(id);
			JmsgServiceFacade.addRelation(r);
		  }
	}


	public void delRelation(String groupId,List<String> ids) {
	    Map<String,Object> cond = new HashMap<>();
	    for (String id:ids){
	    	cond.put("groupId",groupId);
		    cond.put("queueId",id);
		    JmsgServiceFacade.delRelation(cond);
		  }    
	}
	
	
	public List<JmsgMessageConfig> getAllMessageConfig() {
		Map<String, Object> input = Maps.newHashMap();
		Map<String, Object> output = Maps.newHashMap();
		CommUtil.getGeneralField(output, JmsgMessageConfig.class);
		return JmsgServiceFacade.getAllMessageConfig(input, output);
	}

	public void addMessageConfig(MessageConfigRequest request) {
		JmsgMessageConfig s = new JmsgMessageConfig();
	    s.setDelayTime(request.getDelayTime());
        s.setExchangeName(request.getExchangeName());
        s.setMaxRetryCount(request.getMaxRetryCount());
        s.setMessageName(request.getMessageName());
        s.setPriority(request.getPriority());
        s.setUseDelayRetry(request.getUseDelayRetry());
		JmsgServiceFacade.addMessageConfig(s);
	}

	public void editMessageConfig(MessageConfigRequest request) {
		JmsgMessageConfig s = new JmsgMessageConfig();
		s.setId(request.getId());
	    s.setDelayTime(request.getDelayTime());
        s.setExchangeName(request.getExchangeName());
        s.setMaxRetryCount(request.getMaxRetryCount());
        s.setMessageName(request.getMessageName());
        s.setPriority(request.getPriority());
        s.setUseDelayRetry(request.getUseDelayRetry());
		JmsgServiceFacade.editMessageConfig(s);

	}

	public void delMessageConfig(MessageConfigRequest request) {
		JmsgMessageConfig s = new JmsgMessageConfig();
		s.setId(request.getId());
		JmsgServiceFacade.delMessageConfig(s);

	}
	
	
	public List<JmsgMessageConsumersConfig> getMessageConsumersConfigByName(String messageName) {
		Map<String, Object> input = Maps.newHashMap();
		input.put("messageName", messageName);
		Map<String, Object> output = Maps.newHashMap();
		CommUtil.getGeneralField(output, JmsgMessageConfig.class);
		return JmsgServiceFacade.getAllMessageConsumersConfig(input, output);
	}

	public void addMessageConsumersConfig(MessageConsumersRequest request) {
		JmsgMessageConsumersConfig s = new JmsgMessageConsumersConfig();
	    s.setMessageName(request.getMessageName());
	    s.setTimeOut(request.getTimeOut());
	    s.setUrl(request.getUrl());
		JmsgServiceFacade.addMessageConsumersConfig(s);
	}

	public void editMessageConsumersConfig(MessageConsumersRequest request) {
		JmsgMessageConsumersConfig s = new JmsgMessageConsumersConfig();
		s.setId(request.getId());
	    s.setMessageName(request.getMessageName());
	    s.setTimeOut(request.getTimeOut());
	    s.setUrl(request.getUrl());
		JmsgServiceFacade.editMessageConsumersConfig(s);

	}

	public void delMessageConsumersConfig(MessageConsumersRequest request) {
		JmsgMessageConsumersConfig s = new JmsgMessageConsumersConfig();
		s.setId(request.getId());
		JmsgServiceFacade.delMessageConsumersConfig(s);

	}

}
