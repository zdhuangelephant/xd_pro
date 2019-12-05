package com.xiaodou.crontab.zookeeper;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperOperator extends ZooKeeperWatcher {

  public Stat create(String path, byte[] data) throws KeeperException, InterruptedException {
    Stat stat = this.zookeeper.exists(path, this);
    if (null != stat) return stat;
    this.zookeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    return this.zookeeper.exists(path, this);
  }

  public void getChild(String path) throws KeeperException, InterruptedException {
    List<String> list;
    try {
      list = this.zookeeper.getChildren(path, false);
      if (list.isEmpty()) {
        System.out.println(String.format("%s中没有节点", path));
      } else {
        System.out.println(String.format("%s中有节点", path));
        for (String child : list) {
          System.out.println(String.format("节点为%s", child));
        }
      }
    } catch (KeeperException | InterruptedException e) {
      throw e;
    }
  }

  public byte[] getData(String path) throws KeeperException, InterruptedException {
    return this.zookeeper.getData(path, false, null);
  }

  public static void main(String[] args) {
    try {
      ZooKeeperOperator zkoperator = new ZooKeeperOperator();
      zkoperator.connect("119.61.66.52");
      byte[] data = new byte[] {'a', 'b', 'c', 'd'};
      zkoperator.create("/mo", null);
      zkoperator.create("/mo/child1", data);
      zkoperator.create("/mo/child2", data);
      String zktest = "ZooKeeper的Java API测试";
      Charset.forName("utf8");
      zkoperator.create("/mo/child3", zktest.getBytes());
      zkoperator.create("/mo/child4", data);
      System.out.println(String.format("获取设置的信息: %s",
          new String(zkoperator.getData("/mo/child3"))));
      System.out.println("节点孩子信息:");
      zkoperator.getChild("/mo");
      zkoperator.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
