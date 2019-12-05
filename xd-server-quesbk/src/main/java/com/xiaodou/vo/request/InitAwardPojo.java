package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.InitAwardPojo.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 初始化奖励参数
 * @version 1.0
 */
public class InitAwardPojo extends QuesBasePojo {

  /** missionId 任务ID */
  @NotEmpty
  private String missionId;

  public String getMissionId() {
    return missionId;
  }

  public void setMissionId(String missionId) {
    this.missionId = missionId;
  }

}
