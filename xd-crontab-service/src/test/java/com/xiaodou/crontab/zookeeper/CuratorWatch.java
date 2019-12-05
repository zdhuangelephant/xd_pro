package com.xiaodou.crontab.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorWatch {
  static CuratorFramework zkclient = null;
  static String nameSpace = "crontab";

  static {

    String zkhost = "119.61.66.52";// zk的host
    RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);// 重试机制
    Builder builder =
        CuratorFrameworkFactory.builder().connectString(zkhost).connectionTimeoutMs(5000)
            .sessionTimeoutMs(5000).retryPolicy(rp);
    builder.namespace(nameSpace);
    CuratorFramework zclient = builder.build();
    zkclient = zclient;
    zkclient.start();// 放在这前面执行
    zkclient.newNamespaceAwareEnsurePath("/" + nameSpace);



  }

  public static void main(String[] args) throws Exception {
    // zkclient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
    // .forPath("/root/user", "lidehong".getBytes());
    // zkclient.setData().forPath("/root/user", "lidehong123123".getBytes());
     zkclient.delete().forPath("/config/3769576a-8865-4165-a7e1-a58117b3b499");
  
  }

  /**
   * 
   * 监听节点变化
   * 
   */
  @SuppressWarnings("resource")
  public static void watch() throws Exception {
    PathChildrenCache cache = new PathChildrenCache(zkclient, "/root", false);
    cache.start();

    System.out.println("监听开始/zk........");
    PathChildrenCacheListener plis = new PathChildrenCacheListener() {

      @Override
      public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
          throws Exception {
        switch (event.getType()) {
          case CHILD_ADDED: {
            System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
            break;
          }

          case CHILD_UPDATED: {
            System.out.println("Node changed: "
                + ZKPaths.getNodeFromPath(event.getData().getPath()));
            break;
          }

          case CHILD_REMOVED: {
            System.out.println("Node removed: "
                + ZKPaths.getNodeFromPath(event.getData().getPath()));
            break;
          }
          default:
            break;
        }

      }
    };
    // 注册监听
    cache.getListenable().addListener(plis);

    final NodeCache nodeCache = new NodeCache(zkclient, "/root", false);
    nodeCache.start();
    NodeCacheListener nlis = new NodeCacheListener() {

      @Override
      public void nodeChanged() throws Exception {
        System.out.println("node data changed:" + new String(nodeCache.getCurrentData().getData()));
      }
    };
    nodeCache.getListenable().addListener(nlis);
  }

}
