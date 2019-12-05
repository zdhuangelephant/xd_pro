package com.xiaodou.dashboard.dao.crontab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@SuppressWarnings({})
public class CrontabBaseDao<Entity> extends ExtendBaseDao<Entity> {

	@Autowired(required = false)
	public final void setSummerSqlSessionFactory(
			@Qualifier("crontabSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
			throws Exception {
		super.setSummerSqlSessionFactory(sqlSessionFactory);
	}
}
