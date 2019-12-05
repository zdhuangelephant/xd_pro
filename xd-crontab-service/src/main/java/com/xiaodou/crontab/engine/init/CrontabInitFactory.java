package com.xiaodou.crontab.engine.init;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.crontab.domain.CrontabConfig;
import com.xiaodou.crontab.engine.model.ConfigEntity;
import com.xiaodou.crontab.engine.schedule.SchedulerService;
import com.xiaodou.crontab.instance.ServiceContext;
import com.xiaodou.crontab.service.facade.ICrontabServiceFacade;
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

  @Resource
  private ICrontabServiceFacade crontabServiceFacade;

  @Resource
  private SchedulerService schedulerService;

  private final AtomicBoolean initialize = new AtomicBoolean(false);

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer refreshListDelay = 1;

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
          Page<CrontabConfig> configPage = crontabServiceFacade.queryConfigPage();
          if (null == configPage || null == configPage.getResult()
              || configPage.getResult().size() == 0) return;
          Set<String> cacheConfigIdSet = ServiceContext.getAllConfigId();
          for (CrontabConfig config : configPage.getResult()) {
            ConfigEntity configEntity = new ConfigEntity(config);
            if (null == configEntity || StringUtils.isBlank(configEntity.getConfigId())) continue;
            String configId = configEntity.getConfigId();
            // 将有效任务从缓存列表中过滤掉
            cacheConfigIdSet.remove(configId);
            ConfigEntity oldConfigEntity = ServiceContext.getConfig(configId);
            if (null == oldConfigEntity || StringUtils.isBlank(oldConfigEntity.getVersion())
                || !oldConfigEntity.getVersion().equals(configEntity.getVersion())) {
              // 1 更新数据至本地缓存
              ServiceContext.pushConfig(configId, configEntity);
              // 2 更新调度任务
              schedulerService.removeTrigdger(configId);
              if (configEntity.isInUse())
                schedulerService.schedule(configId, configEntity.getCrontExpression());
            }
          }
          // 删除失效任务
          for (String unExistConfigId : cacheConfigIdSet)
            schedulerService.removeTrigdger(unExistConfigId);
        }

        @Override
        public void onException(Throwable t) {
          LoggerUtil.error("刷新定时任务列表失败", t);
        }


      }, initialDelay, refreshListDelay, TimeUnit.SECONDS);

    }
  }
}
