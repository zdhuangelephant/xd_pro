package com.xiaodou.queue.aliyun.worker;

import java.util.List;

import com.aliyun.mns.model.Message;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.aliyun.config.AliyunConfig;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.message.IMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

public class AliyunWorker extends AbstractDefaultWorker {


  /** serialVersionUID */
  private static final long serialVersionUID = 9153955115733714831L;

  public void onException(Throwable t) {
    t.printStackTrace();
    LoggerUtil.error("发送异步消息异常", t);
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    onException(t);
    callback.onFail(CallBackStatus.EXCEPTION, message);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    onException(t);
    callback.onFail(CallBackStatus.EXCEPTION, message);
  }

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (message == null) callback.onFail(CallBackStatus.NULLMESSAGE, message);
    String name = message.getMessageName();
    if (StringUtils.isNotBlank(name)) {
      Message _message = new Message();
      _message.setRequestId(message.getMessageId().toString());
      _message.setMessageBody(message.getMessageBodyJson());
      AliyunConfig.getQueueByMesName(name).putMessage(_message);
      callback.onSuccess(message);
      return;
    }
    callback.onFail(CallBackStatus.MISSINFO, message);
  }

  @Override
  public void domain(List<DefaultMessage> messageLst, IMQCallBacker<List<DefaultMessage>> callback) {
    if (messageLst == null || messageLst.size() == 0)
      callback.onFail(CallBackStatus.NULLMESSAGE, messageLst);
    String name = messageLst.get(0).getMessageName();
    if (StringUtils.isNotBlank(name)) {
      AliyunConfig.getQueueByMesName(name).batchPutMessage(
          Lists.transform(messageLst, new Function<IMessage, Message>() {
            @Override
            public Message apply(IMessage message) {
              Message _message = new Message();
              _message.setMessageBody(message.getMessageBodyJson());
              return _message;
            }
          }));
      callback.onSuccess(messageLst);
    } else {
      callback.onFail(CallBackStatus.MISSINFO, messageLst);
    }
  }

}
