package com.xiaodou.logmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.logmonitor.domain.CommonModel;
import com.xiaodou.logmonitor.domain.TraceModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.jstorm2b.dao.TraceDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2018年4月25日
 * @description 业务方法流Dao
 * @version 1.0
 */
@Repository("traceDao")
public class TraceDao extends MongoBaseDao<TraceModel> {}
