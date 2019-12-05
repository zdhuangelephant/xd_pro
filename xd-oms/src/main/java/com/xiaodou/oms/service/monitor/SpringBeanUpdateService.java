package com.xiaodou.oms.service.monitor;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xiaodou.common.monitor.PropertiesFiles;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.dao.datasource.SummerDataSourceManager;

/**
 * Date: 2014/9/28 Time: 10:04
 * 
 * @author Tian.Dong
 */
@Service
public class SpringBeanUpdateService {

  @Resource
  ThreadPoolTaskExecutor taskExecutor;

  @Resource
  SummerDataSourceManager summerDataSource;

  public void refresh(String fileName) {
    if (fileName.equals("config.properties")) {
      this.refreshTaskExecutor();
    } else if (fileName.equals("proxool_order.properties")) {
      this.refreshProxoolOrder();
    }
  }

  public void refreshTaskExecutor() {
    FileUtil configFileUtil = PropertiesFiles.get("config.properties");
    if (configFileUtil == null) {
      configFileUtil = FileUtil.getInstance("/conf/custom/env/config.properties");
    }
    try {
      int corePoolSize = Integer.valueOf(configFileUtil.getProperties("taskExecutor.corePoolSize"));
      int maxPoolSize = Integer.valueOf(configFileUtil.getProperties("taskExecutor.maxPoolSize"));
      int keepAliveSeconds =
          Integer.valueOf(configFileUtil.getProperties("taskExecutor.keepAliveSeconds"));
      int queueCapacity =
          Integer.valueOf(configFileUtil.getProperties("taskExecutor.queueCapacity"));

      taskExecutor.setCorePoolSize(corePoolSize);
      taskExecutor.setMaxPoolSize(maxPoolSize);
      taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
      taskExecutor.setQueueCapacity(queueCapacity);
    } catch (Exception e) {
      LoggerUtil.error("refresh taskExecutor错误", e);
    }
  }

  public void refreshProxoolOrder() {
    FileUtil proxoolOrderFileUtil = PropertiesFiles.get("proxool_order.properties");
    if (proxoolOrderFileUtil == null) {
      proxoolOrderFileUtil = FileUtil.getInstance("/conf/custom/env/proxool_order.properties");
    }

    try {
      summerDataSource.setDriver(proxoolOrderFileUtil.getProperties("order.proxool.driver-class"));
      summerDataSource.setDriverUrl(proxoolOrderFileUtil.getProperties("order.proxool.driver-url"));
      summerDataSource.setUser(proxoolOrderFileUtil.getProperties("order.user"));
      summerDataSource.setPassword(proxoolOrderFileUtil.getProperties("order.password"));
      summerDataSource.setAlias(proxoolOrderFileUtil.getProperties("order.proxool.alias"));
      summerDataSource.setPrototypeCount(proxoolOrderFileUtil
          .getPropertiesInt("order.proxool.prototype-count"));
      summerDataSource.setMaximumConnectionCount(proxoolOrderFileUtil
          .getPropertiesInt("order.proxool.maximum-connection-count"));
      summerDataSource.setMinimumConnectionCount(proxoolOrderFileUtil
          .getPropertiesInt("order.proxool.minimum-connection-count"));
      summerDataSource.setMaximumActiveTime(proxoolOrderFileUtil
          .getPropertiesInt("order.proxool.maximum-active-time"));
      summerDataSource.setTrace(proxoolOrderFileUtil.getPropertiesBool("order.proxool.trace"));
      summerDataSource.setVerbose(proxoolOrderFileUtil.getPropertiesBool("order.proxool.verbose"));
      summerDataSource
          .setStatistics(proxoolOrderFileUtil.getProperties("order.proxool.statistics"));
      summerDataSource.setHouseKeepingTestSql(proxoolOrderFileUtil
          .getProperties("order.proxool.house-keeping-test-sql"));
      summerDataSource.setSimultaneousBuildThrottle(proxoolOrderFileUtil
          .getPropertiesInt("order.proxool.simultaneous-build-throttle"));

      summerDataSource.setSdriver(proxoolOrderFileUtil
          .getProperties("order.r.proxool.driver-class"));
      summerDataSource.setSdriverUrl(proxoolOrderFileUtil
          .getProperties("order.r.proxool.driver-url"));
      summerDataSource.setSuser(proxoolOrderFileUtil.getProperties("order.r.user"));
      summerDataSource.setSpassword(proxoolOrderFileUtil.getProperties("order.r.password"));
      summerDataSource.setSalias(proxoolOrderFileUtil.getProperties("order.r.proxool.alias"));
      summerDataSource.setSprototypeCount(proxoolOrderFileUtil
          .getPropertiesInt("order.r.proxool.prototype-count"));
      summerDataSource.setSmaximumConnectionCount(proxoolOrderFileUtil
          .getPropertiesInt("order.r.proxool.maximum-connection-count"));
      summerDataSource.setSminimumConnectionCount(proxoolOrderFileUtil
          .getPropertiesInt("order.r.proxool.minimum-connection-count"));
      summerDataSource.setSmaximumActiveTime(proxoolOrderFileUtil
          .getPropertiesInt("order.r.proxool.maximum-active-time"));
      summerDataSource.setStrace(proxoolOrderFileUtil.getPropertiesBool("order.r.proxool.trace"));
      summerDataSource.setSverbose(proxoolOrderFileUtil
          .getPropertiesBool("order.r.proxool.verbose"));
      summerDataSource.setSstatistics(proxoolOrderFileUtil
          .getProperties("order.r.proxool.statistics"));
      summerDataSource.setShouseKeepingTestSql(proxoolOrderFileUtil
          .getProperties("order.r.proxool.house-keeping-test-sql"));
      summerDataSource.setSsimultaneousBuildThrottle(proxoolOrderFileUtil
          .getPropertiesInt("order.r.proxool.simultaneous-build-throttle"));
    } catch (Exception e) {
      LoggerUtil.error("refreshProxoolOrder异常", e);
    }
  }
}
