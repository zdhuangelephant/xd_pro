package com.xiaodou.queue.aliyun.testcase;

import java.util.List;

import com.aliyun.mns.model.Message;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.queue.aliyun.consumer.AliyunConsumer;
import com.xiaodou.queue.aliyun.processer.AliyunProcesser;
import com.xiaodou.queue.annotation.ProcesserHandler;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.WeightEnum;

@ProcesserHandler(weight = WeightEnum.ONE, queueName = "xd-st-test-001-test")
public class TestProcesser extends AliyunProcesser {

  /** serialVersionUID */
  private static final long serialVersionUID = -7055605911530483608L;

  public TestProcesser() throws InstantiationException, IllegalAccessException {
    super(TestConsumer.class);
  }

  public TestProcesser(String queueName) throws InstantiationException, IllegalAccessException {
    super(queueName, TestConsumer.class);
  }

  public static class TestConsumer extends AliyunConsumer {
    public TestConsumer() {}

    /** serialVersionUID */
    private static final long serialVersionUID = -4222024573137162603L;

    @Override
    public void domain(Message message, IMQCallBacker<String> callback) {
      callback.onSuccess(message.getReceiptHandle());
    }

    @Override
    public void domain(List<Message> message, IMQCallBacker<List<String>> callback) {
      callback.onSuccess(Lists.transform(message, new Function<Message, String>() {
        @Override
        public String apply(Message message) {
          return message.getReceiptHandle();
        }
      }));
    }

    @Override
    public void onException(Throwable t, List<Message> message, IMQCallBacker<List<String>> callback) {

    }

    @Override
    public void onException(Throwable t, Message message, IMQCallBacker<String> callback) {

    }


  }

}
