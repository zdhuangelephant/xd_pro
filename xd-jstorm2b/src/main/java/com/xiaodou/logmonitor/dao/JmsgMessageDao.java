package com.xiaodou.logmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.logmonitor.domain.JmsgMessageModel;
import com.xiaodou.summer.dao.mongo.MongoBaseDao;

/**
 * @name @see com.xiaodou.logmonitor.dao.JmsgMessageDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月3日
 * @description 异步消息操作Dao
 * @version 1.0
 */
@Repository("jmsgMessageDao")
public class JmsgMessageDao extends MongoBaseDao<JmsgMessageModel> {
}
