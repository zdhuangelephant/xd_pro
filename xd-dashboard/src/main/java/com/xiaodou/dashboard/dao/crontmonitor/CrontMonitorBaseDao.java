package com.xiaodou.dashboard.dao.crontmonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

/**
 * @name @see com.xiaodou.dashboard.dao.crontmonitor.CrontMonitorBaseDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 主动监控基础Dao
 * @version 1.0
 * @param <Entity>
 */
@SuppressWarnings({})
public class CrontMonitorBaseDao<Entity> extends ExtendBaseDao<Entity> {

	@Autowired(required = false)
	public final void setSummerSqlSessionFactory(
			@Qualifier("crontMonitorSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
			throws Exception {
		super.setSummerSqlSessionFactory(sqlSessionFactory);
	}
}
