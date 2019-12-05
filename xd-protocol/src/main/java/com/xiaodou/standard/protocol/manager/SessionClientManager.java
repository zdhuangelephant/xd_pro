package com.xiaodou.standard.protocol.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.standard.protocol.abtract.SessionClient;
import com.xiaodou.standard.protocol.exception.MissingClientException;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * @name @see com.xiaodou.standard.protocol.manager.SessionClientManager.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 会话Client管理器
 * @version 1.0
 */
public class SessionClientManager {

  /** INTERVAL 检测间隔 */
  private static final Long INTERVAL = 10000l;

  static {
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        loopCheckSessionClient();
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("会话保持其检查逻辑异常.", t);
      }
    }, 0, INTERVAL, TimeUnit.MILLISECONDS);
  }

  /** sessionClientHolder 会话client保持器 */
  private static final Map<String, SessionClientWrapper> sessionClientHolder = Maps
      .newConcurrentMap();

  /**
   * 会话保持其检查逻辑
   */
  private static void loopCheckSessionClient() {
    if (null == sessionClientHolder || sessionClientHolder.isEmpty()) {
      return;
    }
    Set<String> brokenClientSet = Sets.newHashSet();
    for (Entry<String, SessionClientWrapper> onlineClientEntry : sessionClientHolder.entrySet()) {
      try {
        onlineClientEntry.getValue().check();
      } catch (Exception e) {
        LoggerUtil
            .error(String.format("SessionClientManager:Broken Client.[%s]",
                onlineClientEntry.getKey()), e);
        brokenClientSet.add(onlineClientEntry.getKey());
      }
    }
    if (brokenClientSet.isEmpty()) {
      return;
    }
    for (String clientId : brokenClientSet) {
      sessionClientHolder.remove(clientId);
    }
  }

  /**
   * 注册Client到保持器中
   * 
   * @param client 客户端
   */
  public synchronized static void regist(SessionClient client) {
    try {
      if (null != client && client.isAlive()
          && !sessionClientHolder.containsKey(client.getUniqueId())) {
        SessionClientWrapper clientWrapper = new SessionClientWrapper(client);
        sessionClientHolder.put(client.uniqueId(), clientWrapper);
      }
    } catch (Exception e) {
      LoggerUtil.error(e.getMessage(), e);
    }
  }

  /**
   * @name @see com.xiaodou.standard.protocol.manager.SessionClientManager.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年2月12日
   * @description 会话客户端包装器
   * @version 1.0
   */
  private static class SessionClientWrapper {
    /** isOnline 在线状态 */
    private AtomicBoolean isOnline = new AtomicBoolean(true);
    /** interval 检查间隔 */
    private long interval = INTERVAL;
    /** maxInterval 最大检查间隔 */
    private long maxInterval = INTERVAL * 60;
    /** lastCheckTime 最后检查时间 */
    private long lastCheckTime = System.currentTimeMillis();
    /** client 会话客户端 */
    private SessionClient client;

    /**
     * 构造方法
     * 
     * @param client
     */
    public SessionClientWrapper(SessionClient client) {
      this.client = client;
    }

    /**
     * 检查客户端活跃性
     * 
     * @return 是否存活
     */
    public synchronized void check() {
      long currTime = System.currentTimeMillis();
      long passTime = currTime - lastCheckTime;
      if (passTime < interval) {
        return;
      }
      if (null == this.client) {
        throw new MissingClientException();
      }
      lastCheckTime = currTime;
      if (isOnline.get()) {
        onlineCheck();
      } else {
        offlineCheck();
      }
    }

    /**
     * 在线状态检查
     */
    private synchronized void onlineCheck() {
      try {
        if (!this.client.heartBeat()) {
          markOffline();
        }
      } catch (Exception e) {
        markOffline();
      }
    }

    /**
     * 离线状态检查
     */
    private synchronized void offlineCheck() {
      try {
        this.client.reConnect();
        if (this.client.heartBeat()) {
          markOnline();
        }
      } catch (Exception e) {
        markOffline();
      }
    }

    /**
     * 标记在线状态
     */
    private void markOnline() {
      isOnline.compareAndSet(Boolean.FALSE, Boolean.TRUE);
      this.interval = INTERVAL;
    }

    /**
     * 标记离线状态
     */
    private void markOffline() {
      isOnline.compareAndSet(Boolean.TRUE, Boolean.FALSE);
      this.interval *= 2;
      if (this.interval > this.maxInterval) {
        this.interval = this.maxInterval;
      }
    }
  }
}
