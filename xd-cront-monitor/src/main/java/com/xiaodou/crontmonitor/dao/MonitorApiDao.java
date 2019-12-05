package com.xiaodou.crontmonitor.dao;

import org.springframework.stereotype.Repository;
import com.xiaodou.crontmonitor.model.MonitorApi;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.crontmonitor.dao.MonitorApiDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 监控API操作Dao
 * @version 1.0
 */
@Repository("monitorApiDao")
public class MonitorApiDao extends BaseDao<MonitorApi> {
}