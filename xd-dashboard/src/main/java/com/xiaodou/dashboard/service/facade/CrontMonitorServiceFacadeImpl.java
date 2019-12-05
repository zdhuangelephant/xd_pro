package com.xiaodou.dashboard.service.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.dashboard.dao.crontmonitor.MonitorApiDao;
import com.xiaodou.dashboard.dao.crontmonitor.MonitorApiLogDao;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApi;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.facade.CrontMonitorServiceFacadeImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月20日
 * @description 主动监控Service层门面实现
 * @version 1.0
 */
@Service("crontMonitorServiceFacade")
public class CrontMonitorServiceFacadeImpl implements CrontMonitorServiceFacade {

  @Resource
  MonitorApiDao monitorApiDao;

  @Resource
  MonitorApiLogDao monitorApiLogDao;

  @Override
  public MonitorApi insertCrontMonitor(MonitorApi api) {
    return monitorApiDao.addEntity(api);
  }

  @Override
  public Boolean updateCrontMonitorById(MonitorApi api) {
    return monitorApiDao.updateEntityById(api);
  }

  @Override
  public Page<MonitorApi> queryCrontMonitorList(IQueryParam param, Page<MonitorApi> pageInfo) {
    return monitorApiDao.findEntityListByCond(param, pageInfo);
  }

  @Override
  public MonitorApi queryCrontMonitor(String id) {
    MonitorApi api = new MonitorApi();
    api.setId(id);
    return monitorApiDao.findEntityById(api);
  }

  @Override
  public Page<MonitorApiLog> queryCrontMonitorJobLogList(IQueryParam param,
      Page<MonitorApiLog> pageInfo) {
    return monitorApiLogDao.findEntityListByCond(param, pageInfo);
  }

  @Override
  public void deleteCrontMonitorById(String id) {
    MonitorApi api = new MonitorApi();
    api.setId(id);
    if (monitorApiDao.deleteEntityById(api)) {
      IQueryParam param = new QueryParam();
   	  param.addInput("apiId", id);
      monitorApiLogDao.deleteEntityByCond(param);
    }

  }

}
