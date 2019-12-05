package com.xiaodou.control.dao.server;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO Serverdao层
 * @author zhouhuan
 * @date 2016/7/25
 * 
 */

@Repository("serverDao")
public class ServerDao extends MongoBaseDao<ServerModel> {


}
