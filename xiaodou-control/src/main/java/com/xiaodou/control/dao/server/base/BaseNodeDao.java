package com.xiaodou.control.dao.server.base;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO Nodedao层
 * @author zhouhuan
 * @date 2016/7/25
 * 
 */

@Repository("baseNodeDao")
public class BaseNodeDao extends MongoBaseDao<BaseNodeModel> {

}
