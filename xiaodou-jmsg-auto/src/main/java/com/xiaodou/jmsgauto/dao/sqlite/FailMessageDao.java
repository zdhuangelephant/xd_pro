package com.xiaodou.jmsgauto.dao.sqlite;

import org.springframework.stereotype.Repository;

import com.xiaodou.jmsgauto.model.sqlite.FailMessage;
import com.xiaodou.summer.dao.jdbc.BaseDao;

@Repository("failMessageDao")
public class FailMessageDao extends SqliteBaseDao<FailMessage> {}
