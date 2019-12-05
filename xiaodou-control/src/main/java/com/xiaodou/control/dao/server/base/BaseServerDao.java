package com.xiaodou.control.dao.server.base;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO BaseServerdao层
 * @author zhouhuan
 * @date 2017/12/14
 * 
 */

@Repository("baseServerDao")
public class BaseServerDao extends MongoBaseDao<BaseServerModel> {


}
