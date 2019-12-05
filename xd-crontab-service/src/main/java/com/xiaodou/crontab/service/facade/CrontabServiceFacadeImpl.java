package com.xiaodou.crontab.service.facade;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.dao.CrontabConfigDao;
import com.xiaodou.crontab.dao.CrontabJobLogDao;
import com.xiaodou.crontab.domain.CrontabConfig;
import com.xiaodou.crontab.domain.CrontabJobLog;
import com.xiaodou.crontab.engine.constant.CrontConstant;
import com.xiaodou.crontab.engine.model.JobEntity;
import com.xiaodou.crontab.engine.protocol.CrontResult;
import com.xiaodou.crontab.instance.ServiceContext;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.crontab.service.facade.CrontabServiceFacadeImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月10日
 * @description 任务调度service门面实现类
 * @version 1.0
 */
@Service("crontabServiceFacade")
public class CrontabServiceFacadeImpl implements ICrontabServiceFacade {

  @Resource
  CrontabConfigDao crontabConfigDao;
  @Resource
  CrontabJobLogDao crontabJobLogDao;

  @Override
  public CrontabJobLog insertJobLog(CrontabJobLog log) {
    return crontabJobLogDao.addEntity(log);
  }

  @Override
  public Boolean updateJobLogById(CrontabJobLog log) {
    return crontabJobLogDao.updateEntityById(log);
  }

  @Override
  public CrontabJobLog insertJobLog(JobEntity log, CrontResult result) {
    CrontabJobLog entity = new CrontabJobLog();
    entity.setConfigId(log.getConfigId());
    entity.setContextId(log.getContextId());
    entity.setExcutorId(log.getExcuteId());
    entity.setContextName(ServiceContext.getName());
    entity.setDataVersion(log.getDataVersion());
    entity.setCrontTime(new Timestamp(System.currentTimeMillis()));
    entity.setCrontCost(result.getCost().intValue());
    entity.setCrontResult(FastJsonUtil.toJson(result));
    entity.setCrontStatus(CrontConstant.JOB_FINISH);
    entity.setCrontRetry(log.getRetryTime());
    return insertJobLog(entity);
  }

  @Override
  public Page<CrontabConfig> queryConfigPage() {
    IQueryParam queryParam = new QueryParam();
    queryParam.addOutputs(CommUtil.getAllField(CrontabConfig.class));
    return crontabConfigDao.findEntityListByCond(queryParam, null);
  }

}
