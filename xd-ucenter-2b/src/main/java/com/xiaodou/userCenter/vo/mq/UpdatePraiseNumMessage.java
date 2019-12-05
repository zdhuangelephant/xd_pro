package com.xiaodou.userCenter.vo.mq;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.userCenter.model.UserPraiseModel;

/**
 * @name UpdatePraiseNumMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 更新点赞数量异步消息参数
 * @version 1.0
 */
public class UpdatePraiseNumMessage extends AbstractMessagePojo {

  /** MESSAGE_NAME 更新点赞数量异步消息名 */
  private final static String MESSAGE_NAME = "%s_updatePraiseNum";

  public UpdatePraiseNumMessage(UserPraiseModel model) {
    super(String.format(MESSAGE_NAME, model.getModule()));
    setTransferObject(model);
  }

}
