package com.xiaodou.dashboard.service.facade;

import com.xiaodou.dashboard.model.crontmonitor.MonitorApi;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.facade.CrontMonitorServiceFacade.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 主动监控Service层门面
 * @version 1.0
 */
public interface CrontMonitorServiceFacade {

  /**
   * 新增主动监控点
   * 
   * @param api 监控点
   * @return 监控点
   */
  MonitorApi insertCrontMonitor(MonitorApi api);

  /**
   * 根据ID更新监控点模型
   * 
   * @param api 监控点
   * @return 更新结果
   */
  Boolean updateCrontMonitorById(MonitorApi api);

  /**
   * 根据条件查询监控点列表
   * 
   * @param param 查询参数
   * @param pageInfo 分页参数
   * @return 监控点列表
   */
  Page<MonitorApi> queryCrontMonitorList(IQueryParam param, Page<MonitorApi> pageInfo);

  /**
   * 根据ID查询监控点
   * 
   * @param id 监控点ID
   * @return 监控点模型
   */
  MonitorApi queryCrontMonitor(String id);

  /**
   * 根据条件查询监控点执行日志列表
   * 
   * @param param 查询参数
   * @param pageInfo 分页参数
   * @return 监控点执行日志列表
   */
  Page<MonitorApiLog> queryCrontMonitorJobLogList(IQueryParam param, Page<MonitorApiLog> pageInfo);

  /**
   * 根据ID删除监控点
   * 
   * @param id 监控点ID
   */
  void deleteCrontMonitorById(String id);
  
}
