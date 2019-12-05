package com.xiaodou.crontmonitor.service.facade;

import com.xiaodou.crontmonitor.model.MonitorApi;
import com.xiaodou.crontmonitor.model.MonitorApiLog;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.crontmonitor.service.facade.MonitorServiceFacade.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月18日
 * @description Monitor服务层Facade
 * @version 1.0
 */
public interface MonitorServiceFacade {

  /**
   * 返回所有MonitorApi
   * @return 所有监控点
   */
  Page<MonitorApi> findAllMonitorApi();
  
  /**
   * 新增一条Monitor执行日志
   * @param log 执行日志
   * @return 日志
   */
  MonitorApiLog addMonitorLog(MonitorApiLog log);
  
}
