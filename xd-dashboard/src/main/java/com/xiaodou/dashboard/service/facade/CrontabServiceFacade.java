package com.xiaodou.dashboard.service.facade;

import com.xiaodou.dashboard.model.crontab.CrontabConfig;
import com.xiaodou.dashboard.model.crontab.CrontabJobLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.dashboard.service.facade.CrontabServiceFacade.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 定时任务Service层门面
 * @version 1.0
 */
public interface CrontabServiceFacade {

  /**
   * 新增定时任务
   * 
   * @param configModel 定时任务模型
   * @return 定时任务模型
   */
  CrontabConfig insertCrontab(CrontabConfig configModel);

  /**
   * 根据ID更新定时任务模型
   * 
   * @param configModel 定时任务模型
   * @return 更新结果
   */
  Boolean updateCrontabById(CrontabConfig configModel);

  /**
   * 根据条件查询定时任务列表
   * 
   * @param param 查询参数
   * @param pageInfo 分页参数
   * @return 定时任务列表
   */
  Page<CrontabConfig> queryCrontabConfigList(IQueryParam param, Page<CrontabConfig> pageInfo);

  /**
   * 根据ID查询定时任务
   * 
   * @param id 定时任务ID
   * @return 定时任务模型
   */
  CrontabConfig queryCrontabConfig(Integer id);

  /**
   * 根据条件查询定时任务执行日志列表
   * 
   * @param param 查询参数
   * @param pageInfo 分页参数
   * @return 定时任务执行日志列表
   */
  Page<CrontabJobLog> queryCrontabJobLogList(IQueryParam param, Page<CrontabJobLog> pageInfo);

  /**
   * 根据ID删除定时任务
   * 
   * @param id 定时任务ID
   */
  void deleteCrontabById(Integer id);
}
