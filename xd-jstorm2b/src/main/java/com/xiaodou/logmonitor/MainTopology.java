package com.xiaodou.logmonitor;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

import com.xiaodou.logmonitor.bolt.CommonBolt;
import com.xiaodou.logmonitor.bolt.JmsgMessageBolt;
import com.xiaodou.logmonitor.bolt.OutInBolt;
import com.xiaodou.logmonitor.spout.DataSourceSpout;
import com.xiaodou.logmonitor.util.RabbitMqUtil;

public class MainTopology {
  public static void main(String[] args) {
    TopologyBuilder builder = new TopologyBuilder();
    RabbitMqUtil.init();
    builder.setSpout("dataSourceSpout", new DataSourceSpout(50), 1);
    builder.setBolt("commonbolt", new CommonBolt(), 10).shuffleGrouping("dataSourceSpout");
    builder.setBolt("outinbolt", new OutInBolt(), 10).shuffleGrouping("commonbolt");
    builder.setBolt("jmsgMsgbolt", new JmsgMessageBolt(), 10).shuffleGrouping("commonbolt");
    Map<Object, Object> conf = new HashMap<Object, Object>();
    conf.put(Config.TOPOLOGY_WORKERS, Runtime.getRuntime().availableProcessors() * 2);
    conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 2000);
    conf.put(Config.STORM_CLUSTER_MODE, "distributed");
    conf.put(Config.TOPOLOGY_ACKER_EXECUTORS, 2);
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("testTopology", conf, builder.createTopology());
  }
}
