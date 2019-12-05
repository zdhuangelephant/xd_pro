package com.xiaodou.login.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.login.model.Admin;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;
/**
 * Created by zhouhuan on 17.08.15.
 */
@Repository("adminDao")
public class AdminDao extends ExtendBaseDao<Admin> {
	  @Autowired(required = false)
	  public final void setSummerSqlSessionFactory(
	      @Qualifier("loginSummerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
	      throws Exception {
	    super.setSummerSqlSessionFactory(sqlSessionFactory);
	  } 
}
