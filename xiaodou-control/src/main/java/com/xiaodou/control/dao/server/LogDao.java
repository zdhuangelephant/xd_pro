package com.xiaodou.control.dao.server;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;


/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO Logdao层
 * @author zhouhuan
 * @date 2016/7/25
 * 
 */

@Repository("logDao")
public class LogDao extends MongoBaseDao<LogModel> {



}
