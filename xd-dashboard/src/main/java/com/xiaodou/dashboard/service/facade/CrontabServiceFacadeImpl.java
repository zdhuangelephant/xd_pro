package com.xiaodou.dashboard.service.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.dashboard.dao.crontab.CrontabConfigDao;
import com.xiaodou.dashboard.dao.crontab.CrontabJobLogDao;
import com.xiaodou.dashboard.model.crontab.CrontabConfig;
import com.xiaodou.dashboard.model.crontab.CrontabJobLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.facade.CrontabServiceFacadeImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 定时任务Service层门面实现
 * @version 1.0
 */
@Service("crontabServiceFacade")
public class CrontabServiceFacadeImpl implements CrontabServiceFacade {

  /** crontabConfigDao 定时任务操作Dao */
  @Resource
  CrontabConfigDao crontabConfigDao;

  /** crontabJobLogDao 定时任务日志操作Dao */
  @Resource
  CrontabJobLogDao crontabJobLogDao;

  @Override
  public CrontabConfig insertCrontab(CrontabConfig configModel) {
    return crontabConfigDao.addEntity(configModel);
  }

  @Override
  public Boolean updateCrontabById(CrontabConfig configModel) {
    return crontabConfigDao.updateEntityById(configModel);
  }

  @Override
  public Page<CrontabConfig> queryCrontabConfigList(IQueryParam param, Page<CrontabConfig> pageInfo) {
    return crontabConfigDao.findEntityListByCond(param, pageInfo);
  }

  @Override
  public CrontabConfig queryCrontabConfig(Integer id) {
    CrontabConfig config = new CrontabConfig();
    config.setId(id);
    return crontabConfigDao.findEntityById(config);
  }

  @Override
  public Page<CrontabJobLog> queryCrontabJobLogList(IQueryParam param, Page<CrontabJobLog> pageInfo) {
    return crontabJobLogDao.findEntityListByCond(param, pageInfo);
  }

  @Override
  public void deleteCrontabById(Integer id) {
    CrontabConfig config = new CrontabConfig();
    config.setId(id);
    if (crontabConfigDao.deleteEntityById(config)) {
      IQueryParam param = new QueryParam();
	  param.addInput("configId", id);
      crontabJobLogDao.deleteEntityByCond(param);
    }
  }
}
