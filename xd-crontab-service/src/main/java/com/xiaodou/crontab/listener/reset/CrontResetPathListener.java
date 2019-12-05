package com.xiaodou.crontab.listener.reset;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;

import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.zookeeper.annotions.PathListener;
import com.xiaodou.zookeeper.bean.ZkClientManager;
import com.xiaodou.zookeeper.enums.ListenerType;
import com.xiaodou.zookeeper.listener.AbstractPathListener;

/**
 * @name @see com.xiaodou.crontab.listener.reset.CrontResetPathListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 重置调度任务路径监听器
 * @version 1.0
 */
@PathListener(cached = false, compressed = false, path = "/reset", type = ListenerType.Last)
public class CrontResetPathListener extends AbstractPathListener {

  @Override
  public void removeChild(CuratorFramework client, ChildData data) {
    if ("/reset".equals(data.getPath())) return;
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    bean.clearPathListener(data.getPath());
    bean.clearNodeListener(data.getPath());
  }

  @Override
  public void updateChild(CuratorFramework client, ChildData data) {
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    if (!bean.containPathListener(data.getPath()))
      bean.registPathListener(data.getPath(), Boolean.FALSE, Boolean.FALSE,
          new CrontResetPathListener());
    if (!bean.containNodeListener(data.getPath()))
      bean.registNodeListener(data.getPath(), Boolean.FALSE, new CrontResetTaskListener());
  }

  @Override
  public void addChild(CuratorFramework client, ChildData childData) {
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    bean.registPathListener(childData.getPath(), Boolean.FALSE, Boolean.FALSE,
        new CrontResetPathListener());
    bean.registNodeListener(childData.getPath(), Boolean.FALSE, new CrontResetTaskListener());
  }

}
