package com.xiaodou.logmonitor.spout;

import java.io.IOException;
import java.net.SocketException;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.xiaodou.logmonitor.util.MemoryUtil;
import com.xiaodou.logmonitor.util.RabbitMqUtil;

public class DataSourceSpout extends BaseSpout {

  private static final long serialVersionUID = 4924189748702648696L;

  private static final Logger LOG = LoggerFactory.getLogger(DataSourceSpout.class);

  private Integer messageQueueLimit = Integer.MAX_VALUE;

  SpoutOutputCollector collector;

  public DataSourceSpout(int i) {
    messageQueueLimit = i;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
  }

  @Override
  public void nextTuple() {
    try {
      while (getMessageQueue().size() < messageQueueLimit&&MemoryUtil.getUsedMemory()<0.6) {
        if(RabbitMqUtil.consumer==null)RabbitMqUtil.init();
    	if(RabbitMqUtil.consumer==null)return;
        QueueingConsumer.Delivery delivery = RabbitMqUtil.consumer.nextDelivery();
        String message = new String(delivery.getBody());
        // 返回接收到消息的确认信息
        RabbitMqUtil.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        String[] s = message.split("\\|");
        // 过滤掉不符合规范的日志
        if (s.length == 15||s.length == 14||s.length == 19) {
          UUID msgId = UUID.randomUUID();
          getMessageQueue().offer(msgId);
          this.collector.emit(new Values(message), msgId);
        }else {
          LOG.info("不符合格式规范,丢弃.[" + message + "]");
        }
      }
    } catch(ShutdownSignalException|SocketException e){
    	LOG.error("rabbitMq连接失败", e);
    	RabbitMqUtil.init();
    } catch (IOException | InterruptedException e) {
      LOG.error("消息处理失败", e);
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("VEHICLE"));
  }


}
