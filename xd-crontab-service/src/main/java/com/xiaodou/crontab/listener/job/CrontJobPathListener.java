package com.xiaodou.crontab.listener.job;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;

import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.zookeeper.annotions.PathListener;
import com.xiaodou.zookeeper.bean.ZkClientManager;
import com.xiaodou.zookeeper.enums.ListenerType;
import com.xiaodou.zookeeper.listener.AbstractPathListener;

@PathListener(cached = false, compressed = false, path = "/job", type = ListenerType.Normal)
public class CrontJobPathListener extends AbstractPathListener {

  @Override
  public void removeChild(CuratorFramework client, ChildData data) {
    if ("/job".equals(data.getPath())) return;
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    bean.clearPathListener(data.getPath());
    bean.clearNodeListener(data.getPath());
  }

  @Override
  public void updateChild(CuratorFramework client, ChildData data) {
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    if (!bean.containPathListener(data.getPath()))
      bean.registPathListener(data.getPath(), Boolean.FALSE, Boolean.FALSE,
          new CrontJobPathListener());
    if (!bean.containNodeListener(data.getPath()))
      bean.registNodeListener(data.getPath(), Boolean.FALSE, new CrontJobTaskListener());
  }

  @Override
  public void addChild(CuratorFramework client, ChildData childData) {
    ZkClientManager bean = SpringWebContextHolder.getBean(ZkClientManager.class);
    bean.registPathListener(childData.getPath(), Boolean.FALSE, Boolean.FALSE,
        new CrontJobPathListener());
    bean.registNodeListener(childData.getPath(), Boolean.FALSE, new CrontJobTaskListener());
  }

}
