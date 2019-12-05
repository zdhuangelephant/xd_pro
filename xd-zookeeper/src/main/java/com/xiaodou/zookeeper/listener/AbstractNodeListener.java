package com.xiaodou.zookeeper.listener;

import lombok.Data;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * @name @see com.xiaodou.zookeeper.listener.AbstractNodeListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月12日
 * @description 数据节点监听器
 * @version 1.0
 */
@Data
public abstract class AbstractNodeListener implements NodeCacheListener {

  public AbstractNodeListener() {}

  private CuratorFramework client;

  private NodeCache cache;

  @Override
  public void nodeChanged() throws Exception {
    nodeChanged(client, cache);
  }

  public abstract void nodeChanged(CuratorFramework client, NodeCache cache);

}
