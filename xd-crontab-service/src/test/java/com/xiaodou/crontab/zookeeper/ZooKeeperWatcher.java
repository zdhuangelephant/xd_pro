package com.xiaodou.crontab.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperWatcher implements Watcher {

  // 缓存时间
  private static final int SESSION_TIME = 2000;
  protected ZooKeeper zookeeper;

  public void connect(String hosts) throws IOException, InterruptedException {
    zookeeper = new ZooKeeper(hosts, SESSION_TIME, this);
  }

  @Override
  public void process(WatchedEvent event) {
    // TODO
    System.out.println(event.getState().name());
    System.out.println(event.getType().name());
  }

  public void close() throws InterruptedException {
    zookeeper.close();
  }

}
