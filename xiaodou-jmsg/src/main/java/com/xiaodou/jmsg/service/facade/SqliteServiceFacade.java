package com.xiaodou.jmsg.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;

public interface SqliteServiceFacade {
	public FailSendMessage addFailSendMessage(FailSendMessage entity);
	List<FailSendMessage> getFailSendMessageListByCond(Map<String, Object> cond);
	public FailSendMessage getFailSendMessageById(String id);
	public boolean delFailSendMessage(String id);
	public boolean delFailSendMessageByCond(Map<String, Object> cond);
}
