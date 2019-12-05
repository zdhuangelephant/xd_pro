package com.xiaodou.jmsg.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiaodou.jmsg.model.MessageConfig;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@Repository("messageConfigDao")
public class MessageConfigDao extends ExtendBaseDao<MessageConfig> {
	  @Autowired(required = false)
	  public final void setSummerSqlSessionFactory(
	      @Qualifier("jmsgSummerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
	      throws Exception {
	    super.setSummerSqlSessionFactory(sqlSessionFactory);
	  }
  
}
