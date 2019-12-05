package com.xiaodou.logmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.logmonitor.domain.ProjectModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.jstorm2b.dao.ProjectDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月22日
 * @description 项目数据模型处理Dao
 * @version 1.0
 */
@Repository("projectDao")
public class ProjectDao extends MongoBaseDao<ProjectModel> {}
