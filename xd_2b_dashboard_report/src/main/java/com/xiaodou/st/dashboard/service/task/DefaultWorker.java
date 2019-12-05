package com.xiaodou.st.dashboard.service.task;

import java.util.List;

import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

/**
 * @name DefaultWorker 
 * @CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月1日
 * @description 默认消息处理器
 * @version 1.0
 */
public class DefaultWorker extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    // TODO Auto-generated method stub

  }
}
