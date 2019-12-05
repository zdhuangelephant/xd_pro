package com.xiaodou.autopractise.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.autopractise.domain.UserTestInfo;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.autopractise.dao.UserTestInfoDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月7日
 * @description 用户练习信息操作Dao
 * @version 1.0
 */
@Repository
public class UserTestInfoDao extends MongoBaseDao<UserTestInfo> {}
