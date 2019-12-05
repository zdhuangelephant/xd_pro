package com.xiaodou.dashboard.dao.crontab;

import org.springframework.stereotype.Repository;

import com.xiaodou.dashboard.model.crontab.CrontabJobLog;

/**
 * @name @see com.xiaodou.dashboard.dao.crontab.CrontabJobLogDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 任务执行记录模型操作Dao
 * @version 1.0
 */
@Repository("crontabJobLogDao")
public class CrontabJobLogDao extends CrontabBaseDao<CrontabJobLog> {
}