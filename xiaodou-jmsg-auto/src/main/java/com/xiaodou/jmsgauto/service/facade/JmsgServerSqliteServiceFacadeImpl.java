package com.xiaodou.jmsgauto.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.jmsgauto.dao.sqlite.FailMessageDao;
import com.xiaodou.jmsgauto.model.sqlite.FailMessage;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
@Service("jmsgServerSqliteServiceFacade")
public class JmsgServerSqliteServiceFacadeImpl implements JmsgServerSqliteServiceFacade {
	@Resource
	FailMessageDao failMessageDao;
	@Override
	public FailMessage addFailMessage(FailMessage entity) {
		// TODO Auto-generated method stub
		return failMessageDao.addEntity(entity);
	}
	@Override
	public List<FailMessage> getFailMessageListByCond(
			Map<String, Object> inputArgument) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(inputArgument);
		param.addOutputs(CommUtil.getAllField(FailMessage.class));
		return failMessageDao.findEntityListByCond(param, null).getResult();
	}
	@Override
	public boolean delFailMessage(String id) {
		// TODO Auto-generated method stub
		FailMessage message=new FailMessage();
		message.setCustomTag(id);
		return failMessageDao.deleteEntityById(message);
	}
	@Override
	public boolean delFailMessageByCond(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		IDeleteParam param=new DeleteParam();
		param.addInputs(cond);
		return failMessageDao.deleteEntityByCond(param);
	}
	@Override
	public FailMessage getFailMessageById(String id) {
		// TODO Auto-generated method stub
		FailMessage message=new FailMessage();
		message.setCustomTag(id);
		return failMessageDao.findEntityById(message);
	}
	



}
