package com.xiaodou.dashboard.dao.crontab;

import org.springframework.stereotype.Repository;

import com.xiaodou.dashboard.model.crontab.CrontabConfig;

/**
 * @name @see com.xiaodou.dashboard.dao.crontab.CrontabConfigDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 任务定义模型类操作Dao
 * @version 1.0
 */
@Repository("crontabConfigDao")
public class CrontabConfigDao extends CrontabBaseDao<CrontabConfig> {
}