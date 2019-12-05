package com.xiaodou.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;
import com.xiaodou.summer.source.jdbc.mapper.SummerMapperFactoryBean;

/**
 * 题库基础DaoSupport
 * 
 * @author Administrator
 *
 */
public class BaseMapperFactoryBean<T> extends SummerMapperFactoryBean<T> {

	@Autowired(required = false)
	public final void setSummerSqlSessionFactory(
			@Qualifier("summerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory) throws Exception {
		super.setSummerSqlSessionFactory(sqlSessionFactory);
	}

}
