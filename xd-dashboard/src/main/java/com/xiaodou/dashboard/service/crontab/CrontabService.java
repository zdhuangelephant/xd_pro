package com.xiaodou.dashboard.service.crontab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.model.crontab.CrontabConfig;
import com.xiaodou.dashboard.model.crontab.CrontabJobLog;
import com.xiaodou.dashboard.service.facade.CrontabServiceFacade;
import com.xiaodou.dashboard.vo.crontab.request.CrontabConfigOperationRequest;
import com.xiaodou.dashboard.vo.crontab.request.CrontabListRequest;
import com.xiaodou.dashboard.vo.crontab.request.CrontabScheduleListRequest;
import com.xiaodou.dashboard.vo.crontab.response.CrontabConfigModel;
import com.xiaodou.dashboard.vo.crontab.response.CrontabJobLogModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.crontab.CrontabService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月3日
 * @description 任务调度service
 * @version 1.0
 */
@Service("crontabService")
public class CrontabService {

  /** crontabServiceFacade 定时任务调度门面类 */
  @Resource
  CrontabServiceFacade crontabServiceFacade;

  /**
   * 查询定时任务列表
   * 
   * @param request 查询请求
   * @return 定时任务列表
   */
  public List<CrontabConfigModel> crontabList(CrontabListRequest request) {
    IQueryParam param = new QueryParam();
    param.addInput("businessCode", request.getBusinessCode());
    param.addOutputs(CommUtil.getAllField(CrontabConfig.class));
    Page<CrontabConfig> pageInfo = new Page<>();
    pageInfo.setPageNo(request.getPageNo());
    pageInfo.setPageSize(request.getPageSize());
    Page<CrontabConfig> configPage = crontabServiceFacade.queryCrontabConfigList(param, pageInfo);
    if (null == configPage || null == configPage.getResult() || configPage.getResult().size() == 0)
      return null;
    return Lists.transform(configPage.getResult(),
        new Function<CrontabConfig, CrontabConfigModel>() {
          @Override
          public CrontabConfigModel apply(CrontabConfig config) {
            return new CrontabConfigModel(config);
          }
        });
  }

  public CrontabConfigModel addCrontab(CrontabConfigOperationRequest request) {
    CrontabConfig config = request.buildModel();
    config = crontabServiceFacade.insertCrontab(config);
    return new CrontabConfigModel(config);
  }

  public CrontabConfigModel queryCrontab(CrontabConfigOperationRequest request) {
    return new CrontabConfigModel(crontabServiceFacade.queryCrontabConfig(request.getId()));
  }

  public CrontabConfigModel editCrontab(CrontabConfigOperationRequest request) {
    CrontabConfig config = request.buildModel();
    crontabServiceFacade.updateCrontabById(config);
    return new CrontabConfigModel(config);
  }

  public void deleteCrontab(CrontabConfigOperationRequest request) {
    crontabServiceFacade.deleteCrontabById(request.getId());
  }

  public List<CrontabJobLogModel> crontabScheduleList(CrontabScheduleListRequest request) {
    IQueryParam param = new QueryParam();
    param.addInput("configId", request.getConfigId());
    param.addOutputs(CommUtil.getAllField(CrontabJobLog.class));
    param.addSort("crontTime", Sort.DESC);
    Page<CrontabJobLog> pageInfo = new Page<>();
    pageInfo.setPageNo(request.getPageNo());
    pageInfo.setPageSize(request.getPageSize());
    Page<CrontabJobLog> logPage = crontabServiceFacade.queryCrontabJobLogList(param, pageInfo);
    if (null == logPage || null == logPage.getResult() || logPage.getResult().size() == 0)
      return null;
    Map<String, CrontabJobLogModel> logMap = Maps.newHashMap();
    for (CrontabJobLog logModel : logPage.getResult()) {
      if (null == logModel || StringUtils.isBlank(logModel.getExcutorId())) continue;
      CrontabJobLogModel log = logMap.get(logModel.getExcutorId());
      if (null == log) {
        log = new CrontabJobLogModel();
        log.setExcutorId(logModel.getExcutorId());
        logMap.put(logModel.getExcutorId(), log);
      }
      log.pushJob(logModel);
    }
    ArrayList<CrontabJobLogModel> logList = Lists.newArrayList(logMap.values());
    Collections.sort(logList, new Comparator<CrontabJobLogModel>() {
      @Override
      public int compare(CrontabJobLogModel o1, CrontabJobLogModel o2) {
        return o2.getFinalDataVersion().compareTo(o1.getFinalDataVersion());
      }
    });
    return logList;
  }
}
