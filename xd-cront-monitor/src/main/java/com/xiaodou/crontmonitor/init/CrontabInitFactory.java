package com.xiaodou.crontmonitor.init;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.crontmonitor.container.ApiHolder;
import com.xiaodou.crontmonitor.model.MonitorApi;
import com.xiaodou.crontmonitor.schedule.SchedulerService;
import com.xiaodou.crontmonitor.service.facade.MonitorServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;


/**
 * @name @see com.xiaodou.crontab.engine.init.CrontabInitFactory.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月27日
 * @description 定时任务初始化工厂 - 工程启动时执行初始化操作,并依周期定时更新
 * @version 1.0
 */
@Service("crontabInitFactory")
public class CrontabInitFactory {

  private static final String CRONT_MONITOR_EXPRESSION_KEY = "CRONT_MONITOR_EXPRESSION";
  
  @Resource
  private MonitorServiceFacade monitorServiceFacade;

  @Resource
  private SchedulerService schedulerService;

  private final AtomicBoolean initialize = new AtomicBoolean(false);

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer refreshListDelay = 30;

  @PostConstruct
  public void init() {
    if (initialize.compareAndSet(false, true)) {
      SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
        @Override
        public void doMain() {
          refreshCrontabConfigList();
        }

        /**
         * 刷新定时任务列表
         */
        private void refreshCrontabConfigList() {
          Page<MonitorApi> apiPage = monitorServiceFacade.findAllMonitorApi();
          if (null == apiPage || null == apiPage.getResult() || apiPage.getResult().size() == 0) {
            return;
          }
          Set<String> cacheApiIdSet = ApiHolder.getAllApiUniqueId();
          for (MonitorApi monitorApi : apiPage.getResult()) {
            Api api = monitorApi.getApi();
            if (null == api || StringUtils.isBlank(api.getUniqueId())) {
              continue;
            }
            String uniqueId = api.getUniqueId();
            // 将有效任务从缓存列表中过滤掉
            cacheApiIdSet.remove(uniqueId);
            Api oldApi = ApiHolder.getApi(uniqueId);
            if (null == oldApi) {
              // 1 更新数据至本地缓存
              ApiHolder.pushApi(api);
              // 2 更新调度任务
              schedulerService.schedule(uniqueId, ConfigProp.getParams(CRONT_MONITOR_EXPRESSION_KEY));
            }
          }
          // 删除失效任务
          for (String unExistApiId : cacheApiIdSet) {
            schedulerService.removeTrigdger(unExistApiId);
          }
        }

        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("刷新定时任务列表失败", t);
        }


      }, initialDelay, refreshListDelay, TimeUnit.SECONDS);

    }
  }
}
