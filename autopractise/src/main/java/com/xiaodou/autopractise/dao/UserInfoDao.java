package com.xiaodou.autopractise.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.autopractise.dao.UserInfoDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月28日
 * @description 用户信息操作Dao
 * @version 1.0
 */
@Repository
public class UserInfoDao extends MongoBaseDao<UserInfo> {}
