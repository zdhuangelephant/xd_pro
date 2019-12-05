package com.xiaodou.crontmonitor.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.crontmonitor.model.MonitorApiLog;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.crontmonitor.dao.MonitorApiLogDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月18日
 * @description 监控API日志操作Dao
 * @version 1.0
 */
@Repository("monitorApiLogDao")
public class MonitorApiLogDao extends BaseDao<MonitorApiLog> {
}