package com.xiaodou.mission.vo.message;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

/**
 * @name @see com.xiaodou.mission.vo.message.FollowBonusMessage.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月20日
 * @description 激活红包后续操作消息
 * @version 1.0
 */
public class FollowBonusMessage extends AbstractMessagePojo {

  /** RECEIVE_TASK_AWARD 获取任务奖励消息名 */
  private final static String RECEIVE_TASK_AWARD = "followBonus";

  public FollowBonusMessage() {
    super(null);
  }

  public FollowBonusMessage(FollowBonus messageBody) {
    super(RECEIVE_TASK_AWARD);
    setTransferObject(messageBody);
  }

  public static class FollowBonus {
    /** bonusId 红包ID */
    private String bonusId;

    public String getBonusId() {
      return bonusId;
    }

    public void setBonusId(String bonusId) {
      this.bonusId = bonusId;
    }

  }

}
