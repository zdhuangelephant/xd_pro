package com.xiaodou.zookeeper.bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableExecutorService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ScanPath;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledThreadPoolExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;
import com.xiaodou.summer.support.expand.IShutDown;
import com.xiaodou.zookeeper.annotions.NodeListener;
import com.xiaodou.zookeeper.annotions.PathListener;
import com.xiaodou.zookeeper.enums.ListenerType;
import com.xiaodou.zookeeper.listener.AbstractNodeListener;
import com.xiaodou.zookeeper.listener.AbstractPathListener;

/**
 * @name @see com.xiaodou.zookeeper.bean.ZkClientManager.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月12日
 * @description zookeeper客户端管理器
 * @version 1.0
 */
@Data
public class ZkClientManager implements IShutDown {

  /** scanPath 扫描包路径 */
  private String scanPath;
  /** host zookeeper地址 */
  private String host;
  /** coreSize 核心线程数 */
  private Integer coreSize;
  /** sleepTime 指数重试基础间隔 */
  private Integer sleepTime;
  /** timeout 超时时间 */
  private Integer timeout;
  /** retryTime 重试次数 */
  private Integer retryTime;
  /** nameSpace 名称空间 */
  private String nameSpace;
  /** zkClient 客户端实例 */
  private CuratorFramework zkClient;
  /** zkListenerManager 监听管理器 */
  private ZkListenerManager zkListenerManager;

  /** default constructor **/
  public ZkClientManager() {}

  /**
   * 获取客户端方法
   * 
   * @return CuratorFramework
   */
  public CuratorFramework getClient() {
    if (zkClient == null) synchronized (ZkClientManager.class) {
      if (zkClient == null) init();
    }
    return zkClient;
  }

  /**
   * 初始化客户端方法
   */
  public void init() {
    RetryPolicy rp = new ExponentialBackoffRetry(sleepTime, retryTime);// 重试机制
    Builder builder =
        CuratorFrameworkFactory.builder().connectString(host).connectionTimeoutMs(timeout)
            .sessionTimeoutMs(timeout).retryPolicy(rp);
    builder.namespace(nameSpace);
    zkClient = builder.build();
    zkClient.start();// 放在这前面执行
    zkClient.newNamespaceAwareEnsurePath("/" + nameSpace);
    zkListenerManager = new ZkListenerManager(scanPath);
  }

  public void registNodeListener(String path, Boolean compress, AbstractNodeListener listener) {
    if (null == zkListenerManager) return;
    try {
      zkListenerManager.registNodeListener(path, compress, listener);
    } catch (Exception e) {
      LoggerUtil.error("注册节点监听失败", e);
    }
  }

  public void registPathListener(String path, Boolean compress, Boolean cached,
      AbstractPathListener listener) {
    if (null == zkListenerManager) return;
    try {
      zkListenerManager.registPathListener(path, compress, cached, listener);
    } catch (Exception e) {
      LoggerUtil.error("注册路径监听失败", e);
    }
  }

  public void clearNodeListener(String path) {
    if (null == zkListenerManager) return;
    try {
      zkListenerManager.clearNodeListener(path);
    } catch (Exception e) {
      LoggerUtil.error("清空监听失败", e);
    }
  }

  public void clearPathListener(String path) {
    if (null == zkListenerManager) return;
    try {
      zkListenerManager.clearPathListener(path);
    } catch (Exception e) {
      LoggerUtil.error("清空监听失败", e);
    }
  }

  public boolean containNodeListener(String path) {
    if (null == zkListenerManager) return false;
    return zkListenerManager.containNodeListener(path);
  }

  public boolean containPathListener(String path) {
    if (null == zkListenerManager) return false;
    return zkListenerManager.containPathListener(path);
  }

  /**
   * 关闭资源方法
   */
  @Override
  public void shutDown() {
    if (null != zkClient) zkClient.close();
    if (null != zkListenerManager) zkListenerManager.closeAll();
  }

  /**
   * @name @see com.xiaodou.zookeeper.bean.ZkListenerManager.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年4月12日
   * @description zookeeper监听管理器
   * @version 1.0
   */
  private class ZkListenerManager extends ScanPath {

    /** pathCacheMap 路径集合 */
    private Map<String, PathChildrenCache> pathCacheMap = Maps.newConcurrentMap();
    /** nodeCacheMap 节点集合 */
    private Map<String, NodeCache> nodeCacheMap = Maps.newConcurrentMap();

    private List<PathListenerProxy> firstPathListener;

    private List<NodeListenerProxy> firstNodeListener;

    private List<PathListenerProxy> normalPathListener;

    private List<NodeListenerProxy> normalNodeListener;

    private List<PathListenerProxy> lastPathListener;

    private List<NodeListenerProxy> lastNodeListener;

    public ZkListenerManager(String scanPath) {
      super(scanPath);
      processHolder();
    }

    private void initListenerHolder() {
      if (null == firstPathListener) firstPathListener = Lists.newArrayList();
      if (null == firstNodeListener) firstNodeListener = Lists.newArrayList();
      if (null == normalPathListener) normalPathListener = Lists.newArrayList();
      if (null == normalNodeListener) normalNodeListener = Lists.newArrayList();
      if (null == lastPathListener) lastPathListener = Lists.newArrayList();
      if (null == lastNodeListener) lastNodeListener = Lists.newArrayList();
    }

    private void processHolder() {
      registPathListener(firstPathListener);
      registPathListener(normalPathListener);
      registPathListener(lastPathListener);
      registNodeListener(firstNodeListener);
      registNodeListener(normalNodeListener);
      registNodeListener(lastNodeListener);
    }

    private class PathListenerProxy {
      private PathListener path;
      private Class<? extends AbstractPathListener> pathListener;

      PathListenerProxy(PathListener path, Class<? extends AbstractPathListener> pathListener) {
        this.path = path;
        this.pathListener = pathListener;
      }
    }

    private class NodeListenerProxy {
      private NodeListener node;
      private Class<? extends AbstractNodeListener> nodeListener;

      NodeListenerProxy(NodeListener node, Class<? extends AbstractNodeListener> nodeListener) {
        this.node = node;
        this.nodeListener = nodeListener;
      }
    }

    private void pushPathListener(PathListener path,
        Class<? extends AbstractPathListener> pathListener) {
      if (null == path || null == pathListener) return;
      if (ListenerType.First.equals(path.type()))
        firstPathListener.add(new PathListenerProxy(path, pathListener));
      else if (ListenerType.Last.equals(path.type()))
        lastPathListener.add(new PathListenerProxy(path, pathListener));
      else
        normalPathListener.add(new PathListenerProxy(path, pathListener));
    }

    private void pushNodeListener(NodeListener node,
        Class<? extends AbstractNodeListener> nodeListener) {
      if (null == node || null == nodeListener) return;
      if (ListenerType.First.equals(node.type()))
        firstNodeListener.add(new NodeListenerProxy(node, nodeListener));
      else if (ListenerType.Last.equals(node.type()))
        lastNodeListener.add(new NodeListenerProxy(node, nodeListener));
      else
        normalNodeListener.add(new NodeListenerProxy(node, nodeListener));
    }

    @Override
    protected void processClass(Class<?> clazz) {
      initListenerHolder();
      PathListener path;
      if ((path = clazz.getAnnotation(PathListener.class)) != null
          && CommUtil.isSub(clazz, AbstractPathListener.class))
        pushPathListener(path, clazz.asSubclass(AbstractPathListener.class));
      // registPathListener(path, clazz.asSubclass(AbstractPathListener.class));
      NodeListener node;
      if ((node = clazz.getAnnotation(NodeListener.class)) != null
          && CommUtil.isSub(clazz, AbstractNodeListener.class))
        pushNodeListener(node, clazz.asSubclass(AbstractNodeListener.class));
      // registNodeListener(node, clazz.asSubclass(AbstractNodeListener.class));

    }

    private void registNodeListener(List<NodeListenerProxy> proxyList) {
      for (NodeListenerProxy proxy : proxyList) {
        try {
          registNodeListener(proxy.node, proxy.nodeListener);
        } catch (Exception e) {
          LoggerUtil.error("注册监听失败", e);
          continue;
        }
      }
    }

    private void registNodeListener(NodeListener node, Class<? extends AbstractNodeListener> clazz)
        throws Exception {
      registNodeListener(node.path(), node.compressed(), clazz.newInstance());
    }

    private void registPathListener(List<PathListenerProxy> proxyList) {
      for (PathListenerProxy proxy : proxyList) {
        try {
          registPathListener(proxy.path, proxy.pathListener);
        } catch (Exception e) {
          LoggerUtil.error("注册监听失败", e);
          continue;
        }
      }
    }

    private void registPathListener(PathListener path, Class<? extends AbstractPathListener> clazz)
        throws Exception {
      registPathListener(path.path(), path.compressed(), path.cached(), clazz.newInstance());
    }

    public boolean containNodeListener(String path) {
      if (null == nodeCacheMap || StringUtils.isBlank(path)) return false;
      return nodeCacheMap.containsKey(path);
    }

    public boolean containPathListener(String path) {
      if (null == pathCacheMap || StringUtils.isBlank(path)) return false;
      return pathCacheMap.containsKey(path);
    }

    public void registNodeListener(String path, Boolean compress, AbstractNodeListener listener)
        throws Exception {
      if (null == nodeCacheMap) nodeCacheMap = Maps.newHashMap();
      if (StringUtils.isBlank(path))
        throw new IllegalArgumentException("监听路径不能为空.clazz=" + listener.getClass().getName());
      NodeCache cache = nodeCacheMap.get(path);
      if (null == cache) synchronized (nodeCacheMap) {
        if (null == cache) {
          cache = new NodeCache(getClient(), path, compress);
          cache.start();
          nodeCacheMap.put(path, cache);
        }
      }
      listener.setClient(getZkClient());
      listener.setCache(cache);
      cache.getListenable().addListener(listener);
    }

    public void clearNodeListener(String path) throws IOException {
      if (null == nodeCacheMap || StringUtils.isBlank(path)) return;
      NodeCache cache = nodeCacheMap.get(path);
      if (null == cache) return;
      nodeCacheMap.remove(path);
      cache.getListenable().clear();
      cache.close();
      cache = null;
    }

    public void registPathListener(String path, Boolean compress, Boolean cached,
        AbstractPathListener listener) throws Exception {
      if (null == pathCacheMap) pathCacheMap = Maps.newHashMap();
      if (StringUtils.isBlank(path))
        throw new IllegalArgumentException("监听路径不能为空.clazz=" + listener.getClass().getName());
      PathChildrenCache cache = pathCacheMap.get(path);
      if (null == cache)
        synchronized (pathCacheMap) {
          if (null == cache) {
            if (null != coreSize) {
              SummerThreadFactoryBuilder _summerThreadFactoryBuilder =
                  new SummerThreadFactoryBuilder();
              _summerThreadFactoryBuilder.setNameFormat("xd-zookeeper");
              _summerThreadFactoryBuilder.setDaemon(false);
              cache =
                  new PathChildrenCache(getClient(), path, cached, compress,
                      new CloseableExecutorService(new SummerScheduledThreadPoolExecutor(coreSize,
                          _summerThreadFactoryBuilder.build()), true));
            } else
              cache =
                  new PathChildrenCache(getClient(), path, cached, compress,
                      new CloseableExecutorService(SummerCommonScheduledExecutor.getExecutor(),
                          true));
            cache.start();
            pathCacheMap.put(path, cache);
          }
        }
      cache.getListenable().addListener(listener);
    }

    public void clearPathListener(String path) throws IOException {
      if (null == pathCacheMap || StringUtils.isBlank(path)) return;
      PathChildrenCache cache = pathCacheMap.get(path);
      if (null == cache) return;
      pathCacheMap.remove(path);
      cache.getListenable().clear();
      cache.clear();
      cache.close();
      cache = null;
    }

    public void closeAll() {
      // 关闭路径监听
      if (!pathCacheMap.isEmpty()) {
        for (PathChildrenCache childrenCache : pathCacheMap.values())
          try {
            childrenCache.close();
          } catch (IOException e) {
            LoggerUtil.error("关闭资源失败", e);
          }
      }
      // 关闭数据监听
      if (!nodeCacheMap.isEmpty()) {
        for (NodeCache nodeCache : nodeCacheMap.values())
          try {
            nodeCache.close();
          } catch (IOException e) {
            LoggerUtil.error("关闭资源失败", e);
          }
      }
    }

  }

}
