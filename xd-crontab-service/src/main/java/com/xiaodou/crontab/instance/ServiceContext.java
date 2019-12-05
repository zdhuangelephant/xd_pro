package com.xiaodou.crontab.instance;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.crontab.engine.model.ConfigEntity;
import com.xiaodou.crontab.engine.model.JobEntity;

/**
 * @name @see com.xiaodou.crontab.instance.ServiceContext.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月14日
 * @description 运行时全局唯一上下文
 * @version 1.0
 */
public class ServiceContext {
  /** scId 实例唯一ID */
  private final static String scId = UUID.randomUUID().toString();
  /** scName 实例别名 */
  private final static String scName = ConfigProp.getParams("nodeName");
  /** scIdList 集群实例集合 */
  private final static List<String> scIdList = Lists.newArrayList();
  /** localJobCache 本地任务缓存Hash */
  private final static Map<String, JobEntity> localJobCache = Maps.newHashMap();
  /** localConfigCache 本地配置缓存Hash */
  private final static Map<String, ConfigEntity> localConfigCache = Maps.newHashMap();

  /** scPoolLock 集群操作锁 */
  private final static ReentrantLock scPoolLock = new ReentrantLock();
  private final static ReentrantLock jobLock = new ReentrantLock();

  public final static List<String> getScidlist() {
    ServiceContext.scPoolLock.lock();
    try {
      return scIdList;
    } finally {
      ServiceContext.scPoolLock.unlock();
    }
  }

  public final static void refreshScIdList(List<String> scIDList) {
    ServiceContext.scPoolLock.lock();
    try {
      ServiceContext.scIdList.clear();
      ServiceContext.scIdList.addAll(scIDList);
    } finally {
      ServiceContext.scPoolLock.unlock();
    }
  }

  /**
   * 获取 任务实体
   * 
   * @param configId 配置ID
   * @return 任务实体
   */
  public final static JobEntity getJob(String configId) {
    return localJobCache.get(configId);
  }

  /**
   * 插入/更新 配置
   * 
   * @param configId 配置ID
   * @param entity 配置实体
   */
  public final static void pushConfig(String configId, ConfigEntity entity) {
    localConfigCache.put(configId, entity);
  }

  /**
   * 获取配置实体
   * 
   * @param configId 配置ID
   * @return 配置实体
   */
  public final static ConfigEntity getConfig(String configId) {
    return localConfigCache.get(configId);
  }

  public final static Set<String> getAllConfigId() {
    return Sets.newHashSet(localConfigCache.keySet());
  }

  /**
   * 获取实例ID
   */
  public final static String getId() {
    return scId;
  }

  /**
   * 获取实例别名
   */
  public final static String getName() {
    return StringUtils.isBlank(scName) ? CommUtil.getServerName() : scName;
  }

  public static void pushJobData(String configId, JobEntity entity) {
    if (!localConfigCache.containsKey(configId)) return;
    JobEntity job = localJobCache.get(configId);
    if (null == job) {
      try {
        jobLock.lock();
        localJobCache.put(configId, entity);
      } finally {
        jobLock.unlock();
      }
    } else {
      synchronized (job) {
        job.refreshData(entity);
      }
    }
  }
}
