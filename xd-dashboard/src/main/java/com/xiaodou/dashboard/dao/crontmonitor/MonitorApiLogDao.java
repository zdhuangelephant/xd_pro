package com.xiaodou.dashboard.dao.crontmonitor;

import org.springframework.stereotype.Repository;

import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;

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
public class MonitorApiLogDao extends CrontMonitorBaseDao<MonitorApiLog> {
}