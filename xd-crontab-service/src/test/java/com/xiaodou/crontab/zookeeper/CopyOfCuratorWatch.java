package com.xiaodou.crontab.zookeeper;

import java.util.UUID;

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
import org.apache.zookeeper.KeeperException.BadVersionException;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.enums.DataFormat;
import com.xiaodou.crontab.engine.enums.Protocol;
import com.xiaodou.crontab.engine.model.ConfigEntity;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;

public class CopyOfCuratorWatch {
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

    // watch();
    // zkclient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
    // .forPath("/root/user", "lidehong".getBytes());
    try {
      ConfigEntity entity = new ConfigEntity();

      entity.setConfigId(UUID.randomUUID().toString());
      entity.setCrontExpression("*/30 * * * * ?");
      entity.setCrontProtocol(Protocol.HTTP.name());
      entity.setCrontRetryTime(3);
      entity.setCrontTarget("http://service.test.51xiaodou.com:8104/batch/learn_time_rank");
      entity.setCrontTimeOut(20000);
      entity.setProtocolRetryTimes(3);
      entity.setProtocolTimeOut(5000);
      entity.setVersion(UUID.randomUUID().toString());
      entity.setInUse(0);

      CrontHttpProtocolConfig protocolConfig = new CrontHttpProtocolConfig();
      protocolConfig.setDataProtocol(DataFormat.none.name());
      protocolConfig.getHeaderMap().put("zhaodan", "zhaodan");
      protocolConfig.getParamMap().put("zhaodan", "zhaodan");
      entity.setProtocolConfig(FastJsonUtil.toJson(protocolConfig));

      zkclient
          .create()
          .creatingParentsIfNeeded()
          .withMode(CreateMode.PERSISTENT)
          .forPath(String.format("/config/%s", entity.getConfigId()),
              FastJsonUtil.toJson(entity).getBytes());
    } catch (Exception e) {
      if (e instanceof BadVersionException)
        return;
      else
        e.printStackTrace();
    }

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
