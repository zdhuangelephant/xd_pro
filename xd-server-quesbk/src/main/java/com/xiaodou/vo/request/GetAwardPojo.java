package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.GetAwardPojo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 获取奖励参数
 * @version 1.0
 */
public class GetAwardPojo extends QuesBasePojo {

  /** rebBonusId 红包ID */
  @NotEmpty
  private String bonusId;

  public String getBonusId() {
    return bonusId;
  }

  public void setBonusId(String bonusId) {
    this.bonusId = bonusId;
  }

}
