package com.xiaodou.zookeeper.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @name @see com.xiaodou.zookeeper.listener.AbstractPathListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月12日
 * @description 路径节点监听器
 * @version 1.0
 */
public abstract class AbstractPathListener implements PathChildrenCacheListener {

  @Override
  public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
    switch (event.getType()) {
      case CHILD_ADDED: {
        addChild(client, event.getData());
        break;
      }
      case CHILD_UPDATED: {
        updateChild(client, event.getData());
        break;
      }
      case CHILD_REMOVED: {
        removeChild(client, event.getData());
        break;
      }
      case CONNECTION_SUSPENDED: {
        suspended(client, event.getData());
        break;
      }
      case CONNECTION_RECONNECTED: {
        reconnected(client, event.getData());
        break;
      }
      case CONNECTION_LOST: {
        lostConnection(client, event.getData());
        break;
      }
      case INITIALIZED: {
        initialized(client, event.getData());
        break;
      }
      default:
        break;
    }
  }

  /**
   * 暂停
   */
  public void suspended(CuratorFramework client, ChildData data) {}

  /**
   * 重连
   */
  public void reconnected(CuratorFramework client, ChildData data) {}

  /**
   * 丢失连接
   */
  public void lostConnection(CuratorFramework client, ChildData data) {}

  /**
   * 初始化
   */
  public void initialized(CuratorFramework client, ChildData data) {}

  /**
   * 删除节点
   */
  public abstract void removeChild(CuratorFramework client, ChildData data);

  /**
   * 更新节点
   */
  public abstract void updateChild(CuratorFramework client, ChildData data);

  /**
   * 添加节点
   */
  public abstract void addChild(CuratorFramework client, ChildData childData);

}
