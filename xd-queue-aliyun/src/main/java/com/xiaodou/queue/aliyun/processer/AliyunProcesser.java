package com.xiaodou.queue.aliyun.processer;

import java.util.List;

import com.aliyun.mns.model.Message;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.aliyun.config.AliyunConfig;
import com.xiaodou.queue.annotation.ProcesserHandler;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.consumer.AbstractConsumer;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.processer.AbstractProcesser;
import com.xiaodou.queue.puller.IPuller;

public class AliyunProcesser extends AbstractProcesser<Message, String> {

  public AliyunProcesser(Class<? extends AbstractConsumer<Message, String>> consumerType)
      throws InstantiationException, IllegalAccessException {
    puller = new AliyunPuller(StringUtils.EMPTY);
    consumer = consumerType.newInstance();
  }

  public AliyunProcesser(String queueName,
      Class<? extends AbstractConsumer<Message, String>> consumerType)
      throws InstantiationException, IllegalAccessException {
    puller = new AliyunPuller(queueName);
    consumer = consumerType.newInstance();
  }

  /** serialVersionUID */
  private static final long serialVersionUID = -651461326516227684L;

  @Override
  public IMQCallBacker<String> getCallBack(Message message) {
    return new IMQCallBacker<String>() {
      @Override
      public void onSuccess(String message) {
        puller.delete(message);
      }

      @Override
      public void onFail(CallBackStatus staus, String message) {
        LoggerUtil.error("拉取处理消息失败.", new RuntimeException(message));
      }
    };
  }

  @Override
  public IMQCallBacker<List<String>> getCallBackList(List<Message> messageList) {
    return new IMQCallBacker<List<String>>() {

      @Override
      public void onSuccess(List<String> message) {
        puller.delete(message);
      }

      @Override
      public void onFail(CallBackStatus staus, List<String> message) {
        LoggerUtil.error("拉取处理消息失败.", new RuntimeException(FastJsonUtil.toJson(message)));
      }
    };
  }

  private class AliyunPuller implements IPuller<Message, String> {

    /** serialVersionUID */
    private static final long serialVersionUID = 5345382788292686469L;

    public AliyunPuller(String queueName) {
      this.queueName = queueName;
    }

    private String queueName;

    @Override
    public Message pull() {
      return AliyunConfig.getQueueByName(queueName).popMessage();
    }

    @Override
    public List<Message> pullList() {
      return AliyunConfig.getQueueByName(queueName).batchPopMessage(AliyunConfig.getBatchLimit());
    }

    @Override
    public void delete(String m) {
      AliyunConfig.getQueueByName(queueName).deleteMessage(m);
    }

    @Override
    public void delete(List<String> mlist) {
      AliyunConfig.getQueueByName(queueName).batchDeleteMessage(mlist);
    }

  }

  @Override
  public void initialize(ProcesserHandler annotation) {
    if (null == puller) {
      puller = new AliyunPuller(StringUtils.EMPTY);
      ((AliyunPuller) puller).queueName = AliyunConfig.getQueueByShortName(annotation.queueName());
    }
  }

}
