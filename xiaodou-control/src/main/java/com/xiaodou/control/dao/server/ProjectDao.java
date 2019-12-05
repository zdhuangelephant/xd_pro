package com.xiaodou.control.dao.server;

import org.springframework.stereotype.Repository;

import com.xiaodou.control.model.server.ProjectModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * 
 * 
 * 
 * @Title:
 * @Description: TODO ProjectDao层
 * @author zhouhuan
 * @date 2017/6/20
 * 
 */

@Repository("projectDao")
public class ProjectDao extends MongoBaseDao<ProjectModel> {

}
