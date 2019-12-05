package com.xiaodou.logmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.logmonitor.domain.ServerModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.jstorm2b.dao.ServerDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月22日
 * @description 服务器模型操作Dao
 * @version 1.0
 */
@Repository("serverDao")
public class ServerDao extends MongoBaseDao<ServerModel> {}
