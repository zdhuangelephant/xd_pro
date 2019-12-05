package com.xiaodou.jmsg.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.jmsg.dao.FailSendMessageDao;
import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("sqliteServiceFacade")
public class SqliteServiceFacadeImpl implements SqliteServiceFacade {
	@Resource
	FailSendMessageDao failSendMessageDao;
	@Override
	public FailSendMessage addFailSendMessage(FailSendMessage entity) {
		// TODO Auto-generated method stub
		return failSendMessageDao.addEntity(entity);
	}
	@Override
	public List<FailSendMessage> getFailSendMessageListByCond(
			Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(FailSendMessage.class));
		return failSendMessageDao.findEntityListByCond(param, null).getResult();
	}
	@Override
	public boolean delFailSendMessage(String id) {
		// TODO Auto-generated method stub
		FailSendMessage message=new FailSendMessage();
		message.setCustomTag(id);
		return failSendMessageDao.deleteEntityById(message);
	}
	@Override
	public boolean delFailSendMessageByCond(Map<String, Object> cond) {
		// TODO Auto-generated method stub
	  IDeleteParam param = new DeleteParam();
	  param.addInputs(cond);
	  return failSendMessageDao.deleteEntityByCond(param);
	}
	@Override
	public FailSendMessage getFailSendMessageById(String id) {
		// TODO Auto-generated method stub
		FailSendMessage message=new FailSendMessage();
		message.setCustomTag(id);
		return failSendMessageDao.findEntityById(message);
	}



}
