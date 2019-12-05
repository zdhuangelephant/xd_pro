package com.xiaodou.dashboard.service.crontmonitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApi;
import com.xiaodou.dashboard.model.crontmonitor.MonitorApiLog;
import com.xiaodou.dashboard.service.facade.CrontMonitorServiceFacade;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiListRequest;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiLogListRequest;
import com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiOperationRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.crontmonitor.CrontMonitorService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 主动监控Service
 * @version 1.0
 */
@Service("crontMonitorService")
public class CrontMonitorService {

  /** crontMonitorServiceFacade 定时任务调度门面类 */
  @Resource
  CrontMonitorServiceFacade crontMonitorServiceFacade;

  /**
   * 查询定时任务列表
   * 
   * @param request 查询请求
   * @return 定时任务列表
   */
  public List<MonitorApi> crontMonitorList(MonitorApiListRequest request) {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(MonitorApi.class));
    Page<MonitorApi> pageInfo = new Page<>();
    pageInfo.setPageNo(request.getPageNo());
    pageInfo.setPageSize(request.getPageSize());
    Page<MonitorApi> configPage = crontMonitorServiceFacade.queryCrontMonitorList(param, pageInfo);
    if (null == configPage || null == configPage.getResult() || configPage.getResult().size() == 0) {
      return null;
    }
    return configPage.getResult();
  }

  public MonitorApi addCrontMonitor(MonitorApiOperationRequest request) {
    MonitorApi api = request.buildModel();
    return crontMonitorServiceFacade.insertCrontMonitor(api);
  }

  public MonitorApi queryCrontMonitor(MonitorApiOperationRequest request) {
    return crontMonitorServiceFacade.queryCrontMonitor(request.getId());
  }

  public MonitorApi editCrontMonitor(MonitorApiOperationRequest request) {
    MonitorApi api = request.buildModel();
    crontMonitorServiceFacade.updateCrontMonitorById(api);
    return api;
  }

  public void deleteCrontMonitor(MonitorApiOperationRequest request) {
    crontMonitorServiceFacade.deleteCrontMonitorById(request.getId());
  }

  public List<MonitorApiLog> monitorApiLogList(MonitorApiLogListRequest request) {
    IQueryParam param = new QueryParam();
    param.addInput("apiId", request.getApiId());
    param.addOutputs(CommUtil.getAllField(MonitorApiLog.class));
    param.addSort("createTime", Sort.DESC);
    Page<MonitorApiLog> pageInfo = new Page<>();
    pageInfo.setPageNo(request.getPageNo());
    pageInfo.setPageSize(request.getPageSize());
    Page<MonitorApiLog> logPage =
        crontMonitorServiceFacade.queryCrontMonitorJobLogList(param, pageInfo);
    if (null == logPage || null == logPage.getResult() || logPage.getResult().size() == 0)
      return null;
    return logPage.getResult();
  }
}
