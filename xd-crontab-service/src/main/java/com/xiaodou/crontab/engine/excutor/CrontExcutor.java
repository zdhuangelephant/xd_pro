package com.xiaodou.crontab.engine.excutor;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.builder.CrontProtocolBuilder;
import com.xiaodou.crontab.engine.model.ConfigEntity;
import com.xiaodou.crontab.engine.model.JobEntity;
import com.xiaodou.crontab.engine.protocol.CrontResult;
import com.xiaodou.crontab.engine.protocol.ICrontProtocol;
import com.xiaodou.crontab.instance.ServiceContext;
import com.xiaodou.crontab.service.facade.ICrontabServiceFacade;
import com.xiaodou.crontab.util.NodeHelper;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.zookeeper.bean.ZkClientManager;

/**
 * @name @see com.xiaodou.crontab.engine.excutor.CrontExcutor.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 定时任务执行器
 * @version 1.0
 */
public class CrontExcutor {

  private static ICrontabServiceFacade facade;

  public static ICrontabServiceFacade getFacade() {
    if (null == facade) facade = SpringWebContextHolder.getBean("crontabServiceFacade");
    return facade;
  }

  public static boolean voteJob(String configId) {
    // 1. 获取上次执行信息
    JobEntity job = ServiceContext.getJob(configId);
    if (null == job) {
      job = new JobEntity();
      job.setConfigId(configId);
      ServiceContext.pushJobData(configId, job);
    }
    synchronized (job) {
      job.setContextId(ServiceContext.getId());
      return markExcuted(job);
    }
  }

  /**
   * 执行调度任务
   * 
   * @param configId 配置ID
   */
  public static void excuteCrontJob(String configId) {
    // 1. 获取上次执行信息
    JobEntity job = ServiceContext.getJob(configId);
    if (null == job) return;
    synchronized (job) {
      if (!job.isExcutable()) {
        job.setExcutable(true);
        return;
      }
      long startTime = System.currentTimeMillis();
      ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
      CuratorFramework zkClient = bean.getClient();
      ConfigEntity config = ServiceContext.getConfig(configId);
      if (null == config) return;
      Boolean needReset = false;
      String excutorPath = NodeHelper.getExcutorPath(job.getConfigId());
      try {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
            .forPath(excutorPath, ServiceContext.getId().getBytes());
        ICrontProtocol<?> protocol = CrontProtocolBuilder.buildProtocol(config);
        if (null == protocol) return;
        CrontResult result = protocol.crontabJob();
        result.setCost(System.currentTimeMillis() - startTime);
        getFacade().insertJobLog(job, result);
        markOk(job);
      } catch (Exception e) {
        LoggerUtil.error("执行任务调度异常", e);
        if (config.getCrontRetryTime() != null && config.getCrontRetryTime() > job.getRetryTime())
          needReset = true;
      } finally {
        try {
          zkClient.delete().forPath(excutorPath);
        } catch (Exception e) {
          LoggerUtil.error("删除执行信息失败", e);
        }
        if (needReset) markReset(job);
      }
    }
  }

  public static boolean markExcuted(JobEntity job) {
    job.setContextId(ServiceContext.getId());
    job.preExcute();
    String jobPath = NodeHelper.getJobPath(job.getConfigId());
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    CuratorFramework zkClient = bean.getClient();
    try {
      if (null == zkClient.checkExists().forPath(jobPath)) {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
            .forPath(jobPath, FastJsonUtil.toJson(job).getBytes());
      } else {
        zkClient.setData().withVersion(job.getDataVersion())
            .forPath(jobPath, FastJsonUtil.toJson(job).getBytes());
      }
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      try {
        Stat stat = zkClient.checkExists().forPath(jobPath);
        job.setDataVersion(stat.getVersion());
      } catch (Exception e1) {}
    }
  }

  public static void markReset(JobEntity job) {
    job.retry();
    try {
      String resetPath = NodeHelper.getResetPath(job.getConfigId());
      ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
      CuratorFramework zkClient = bean.getClient();
      if (null == zkClient.checkExists().forPath(resetPath)) {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
            .forPath(resetPath, FastJsonUtil.toJson(job).getBytes());
      }
    } catch (Exception e) {
      LoggerUtil.error("执行任务调度异常", e);
    }
  }

  public static void markOk(JobEntity job) {
    job.ok();
    try {
      String okPath = NodeHelper.getOkPath(job.getConfigId());
      ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
      CuratorFramework zkClient = bean.getClient();
      if (null == zkClient.checkExists().forPath(okPath)) {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
            .forPath(okPath, FastJsonUtil.toJson(job).getBytes());
      } else {
        zkClient.setData().forPath(okPath, FastJsonUtil.toJson(job).getBytes());
      }
    } catch (Exception e) {
      LoggerUtil.error("执行任务调度异常", e);
    }
  }
}
