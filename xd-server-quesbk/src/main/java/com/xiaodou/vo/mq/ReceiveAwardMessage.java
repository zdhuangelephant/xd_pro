package com.xiaodou.vo.mq;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.vo.task.ReceiveAward;

/**
 * @name ReceiveAwardMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 获取任务奖励异步消息参数类
 * @version 1.0
 */
public class ReceiveAwardMessage extends AbstractMessagePojo {

  /** RECEIVE_TASK_AWARD 获取任务奖励消息名 */
  private final static String RECEIVE_TASK_AWARD = "receiveTaskAward";

  public ReceiveAwardMessage() {
    super(null);
  }

  public ReceiveAwardMessage(ReceiveAward messageBody) {
    super(RECEIVE_TASK_AWARD);
    setTransferObject(messageBody);
  }


}
