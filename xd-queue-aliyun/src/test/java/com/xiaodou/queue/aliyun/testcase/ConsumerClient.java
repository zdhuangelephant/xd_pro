package com.xiaodou.queue.aliyun.testcase;

/***************************************************
 * 运行示例代码的前置条件：
 * 
 * 通过下面两种方式可以引入依赖(任选一种) 1、Maven方式引入依赖 <dependency> <groupId>com.aliyun.openservices</groupId>
 * <artifactId>ons-client</artifactId> <version>1.1.5</version> </dependency>
 * 
 * 2、下载依赖Jar包 http://onsteam.oss-cn-hangzhou.aliyuncs.com/aliyun-ons-client-java.zip
 ***************************************************/

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;


public class ConsumerClient {

  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.put(PropertyKeyConst.ConsumerId, "CID_799052986-101");
    properties.put(PropertyKeyConst.AccessKey, "8ykPgdET5LJD6egk");
    properties.put(PropertyKeyConst.SecretKey, "8Fbqmmarka3pQbfqUMKajSCanrUUMm");
    Consumer consumer = ONSFactory.createConsumer(properties);
    consumer.subscribe("xd_jz_regist_mes", "*", new MessageListener() {

      public Action consume(Message message, ConsumeContext context) {
        System.out.println(message);
        return Action.CommitMessage;
      }
    });

    consumer.start();

    System.out.println("Consumer Started");
  }
}
