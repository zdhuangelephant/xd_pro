package com.xiaodou.logmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.logmonitor.domain.CommonModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.jstorm2b.dao.CommonDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月22日
 * @description 基础数据模型Dao
 * @version 1.0
 */
@Repository("commonDao")
public class CommonDao extends MongoBaseDao<CommonModel> {}
