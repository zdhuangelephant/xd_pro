package com.xiaodou.crontab.service.facade;

import com.xiaodou.crontab.domain.CrontabConfig;
import com.xiaodou.crontab.domain.CrontabJobLog;
import com.xiaodou.crontab.engine.model.JobEntity;
import com.xiaodou.crontab.engine.protocol.CrontResult;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.crontab.service.facade.ICrontabServiceFacade.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月10日
 * @description 任务调度service门面
 * @version 1.0
 */
public interface ICrontabServiceFacade {

  CrontabJobLog insertJobLog(CrontabJobLog log);
  
  CrontabJobLog insertJobLog(JobEntity log, CrontResult result);
  
  Boolean updateJobLogById(CrontabJobLog log);

  Page<CrontabConfig> queryConfigPage();
  
}
