package com.xiaodou.rabbitmqagent.pool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.xiaodou.rabbitmqagent.proxy.RabbitProxy;

/**
 * Date: 2014/5/5 Time: 15:27
 *
 * @author Tian.Dong
 */
public class RabbitPool {

  private RabbitPool() {
  }

  private static final RabbitPool RABBIT_POOL = new RabbitPool();

  public static RabbitPool getInstance() {
    return RABBIT_POOL;
  }

  /**
   * rabbit-mq服务器 Host
   */
  private String hostName;

  /**
   * rabbit-mq服务器 端口
   */
  private int hostPort;

  /**
   * rabbit-mq 服务器用户名
   */
  private String username;
  /**
   * rabbit-mq 服务器密码
   */
  private String password;


  Queue<RabbitProxy> rabbitProxyQueue = new LinkedList<RabbitProxy>();
  /**
   * 连接池已创建proxy个数
   */
  int createdCount;
  /**
   * 已使用proxy个数
   */
  int usedCount;
  /**
   * 连接池最大创建proxy个数
   */
  int maxCount = 20;

  public synchronized RabbitProxy getProxy() {
    if (rabbitProxyQueue.size() > 0) {
      RabbitProxy proxy = rabbitProxyQueue.poll();
      while ((proxy == null) || !proxy.isAvailable()) {
        if (proxy != null) {
          proxy.close();
          proxy = null;
        }
        this.createdCount--;
        if (this.rabbitProxyQueue.size() <= 0) {
          break;
        }
        proxy = rabbitProxyQueue.poll();
      }
      if ((proxy != null) && proxy.isAvailable()) {
        this.usedCount++;
        return proxy;
      }
    }
    if (createdCount > maxCount) {
      return null;
    } else {
      RabbitProxy proxy = this.newProxy();
      usedCount++;
      return proxy;
    }
  }

  public RabbitProxy newProxy() {
    RabbitProxy proxy = new RabbitProxy();
    proxy.setRabbitPool(this);
    proxy.setHostName(hostName);
    proxy.setHostPort(hostPort);
    proxy.setUsername(username);
    proxy.setPassword(password);

    createdCount++;
    return proxy;
  }

  public void returnProxy(RabbitProxy proxy) {
    if (!proxy.isAvailable() || rabbitProxyQueue.size() > maxCount) {
      proxy.close();
      createdCount--;
      usedCount--;
    } else {
      rabbitProxyQueue.add(proxy);
      usedCount--;
    }
  }


  private Map<String, RabbitProxy> listenerProxyMap = new HashMap<String, RabbitProxy>();

  private synchronized void registerListenerProxy(String queue, RabbitProxy proxy) {

    if (!listenerProxyMap.containsKey(queue)) {
      listenerProxyMap.put(queue, proxy);
    }

  }

  public RabbitProxy getListenerProxy(String queue) {
    //如果缓存中不包含
    if (!listenerProxyMap.containsKey(queue)) {
      RabbitProxy proxy = null;
      proxy = this.newProxy();
      registerListenerProxy(queue, proxy);
    }
    RabbitProxy proxy = listenerProxyMap.get(queue);
    //如果proxy失效
    if (!proxy.isAvailable()) {
      proxy.close();
      createdCount--;
      proxy = this.newProxy();
      listenerProxyMap.put(queue, proxy);
    }
    return proxy;
  }

  final Object lock = new Object();

  public void returnListener(String queue, RabbitProxy proxy) {
    if (!proxy.isAvailable()) {
      proxy.close();
      synchronized (lock) {
        listenerProxyMap.remove(queue);
      }
      registerListenerProxy(queue, newProxy());
    }
  }

  public void closeAll() {
    for (Map.Entry<String, RabbitProxy> entry : listenerProxyMap.entrySet()) {
      RabbitProxy reader = entry.getValue();
      reader.setAvailable(false);
      reader.close();
    }
    for (Iterator<RabbitProxy> iterator = rabbitProxyQueue.iterator(); iterator.hasNext(); ) {
      RabbitProxy sender = iterator.next();
      sender.setAvailable(false);
      sender.close();
      iterator.remove();
    }
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public int getHostPort() {
    return hostPort;
  }

  public void setHostPort(int hostPort) {
    this.hostPort = hostPort;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getCreatedCount() {
    return createdCount;
  }

  public void setCreatedCount(int createdCount) {
    this.createdCount = createdCount;
  }

  public int getUsedCount() {
    return usedCount;
  }

  public void setUsedCount(int usedCount) {
    this.usedCount = usedCount;
  }

  public int getMaxCount() {
    return maxCount;
  }

  public void setMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }
}