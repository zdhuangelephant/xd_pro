package com.xiaodou.mission.vo.message;

import java.util.UUID;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.mission.domain.UserMissionRecordModel;

/**
 * @name CreateRedBonusMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 创建奖励红包异步消息参数类
 * @version 1.0
 */
public class CreateRedBonusMessage extends AbstractMessagePojo {

  /** ASYNC_CREATE_BONUS 创建奖励红包 */
  private final static String ASYNC_CREATE_BONUS = "createRedBonus";

  public CreateRedBonusMessage(UserMissionRecordModel message) {
    super(ASYNC_CREATE_BONUS);
    setCustomTag(UUID.randomUUID().toString());
    setTransferObject(message);
  }
}
