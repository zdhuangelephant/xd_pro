package com.xiaodou.jmsg.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xiaodou.jmsg.model.MessageConsumersConfig;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@Repository("messageConsumersConfigDao")
public class MessageConsumersConfigDao extends ExtendBaseDao<MessageConsumersConfig> {
	  @Autowired(required = false)
	  public final void setSummerSqlSessionFactory(
	      @Qualifier("jmsgSummerSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
	      throws Exception {
	    super.setSummerSqlSessionFactory(sqlSessionFactory);
	  }

  
}
