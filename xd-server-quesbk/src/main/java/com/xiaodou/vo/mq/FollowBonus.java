package com.xiaodou.vo.mq;

/**
 * @name @see com.xiaodou.vo.mq.FollowBonus.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月20日
 * @description 红包后续操作激活消息
 * @version 1.0
 */
public class FollowBonus {
  /** bonusId 红包ID */
  private String bonusId;

  public String getBonusId() {
    return bonusId;
  }

  public void setBonusId(String bonusId) {
    this.bonusId = bonusId;
  }

}
