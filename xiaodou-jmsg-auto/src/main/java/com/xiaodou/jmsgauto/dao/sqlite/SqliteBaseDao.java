package com.xiaodou.jmsgauto.dao.sqlite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;



@SuppressWarnings({})
public class SqliteBaseDao<Entity> extends ExtendBaseDao<Entity> {

	@Autowired(required = false)
	public final void setSummerSqlSessionFactory(
			@Qualifier("sqliteSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
			throws Exception {
		super.setSummerSqlSessionFactory(sqlSessionFactory);
	}
}
