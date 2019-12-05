package com.xiaodou.jmsg.server.dao.sqlite;

import org.springframework.stereotype.Repository;

import com.xiaodou.jmsg.server.model.sqlite.FailMessage;
import com.xiaodou.summer.dao.jdbc.BaseDao;

@Repository("failMessageDao")
public class FailMessageDao extends SqliteBaseDao<FailMessage> {}
