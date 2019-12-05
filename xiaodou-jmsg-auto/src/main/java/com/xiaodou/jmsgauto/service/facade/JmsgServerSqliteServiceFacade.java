package com.xiaodou.jmsgauto.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.jmsgauto.model.sqlite.FailMessage;

public interface JmsgServerSqliteServiceFacade {
	public FailMessage addFailMessage(FailMessage entity);
	List<FailMessage> getFailMessageListByCond(Map<String, Object> cond);
	public FailMessage getFailMessageById(String id);
	
	public boolean delFailMessage(String id);
	public boolean delFailMessageByCond(Map<String, Object> cond);
}
