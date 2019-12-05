package com.xiaodou.jmsg.server.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.jmsg.server.model.MessageBody;
import com.xiaodou.summer.dao.jdbc.BaseDao;

@Repository("messageBodyDao")
public class MessageBodyDao extends BaseDao<MessageBody> {

	public boolean updateEntityByCond(Map<String, Object> input,
			Map<String, Object> value) {
		Map<String, Object> cond = Maps.newHashMap();
		cond.put("input", input);
		cond.put("entity", value);
		int result = this.getSqlSession().update(
				this.getEntityClass().getSimpleName() + ".updateEntity", cond);
		return result == 1;
	}

}
