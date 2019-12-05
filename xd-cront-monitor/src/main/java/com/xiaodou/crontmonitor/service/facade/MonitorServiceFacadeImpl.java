package com.xiaodou.crontmonitor.service.facade;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.crontmonitor.dao.MonitorApiDao;
import com.xiaodou.crontmonitor.dao.MonitorApiLogDao;
import com.xiaodou.crontmonitor.model.MonitorApi;
import com.xiaodou.crontmonitor.model.MonitorApiLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.crontmonitor.service.facade.MonitorServiceFacadeImpl.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月18日
 * @description Monitor服务层Facade实现
 * @version 1.0
 */
@Service("monitorServiceFacade")
public class MonitorServiceFacadeImpl implements MonitorServiceFacade {
  
  @Resource
  MonitorApiDao monitorApiDao;
  @Resource
  MonitorApiLogDao monitorApiLogDao;

  @Override
  public Page<MonitorApi> findAllMonitorApi() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(MonitorApi.class));
    return monitorApiDao.findEntityListByCond(param, null);
  }

  @Override
  public MonitorApiLog addMonitorLog(MonitorApiLog log) {
    log.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return monitorApiLogDao.addEntity(log);
  }

}
