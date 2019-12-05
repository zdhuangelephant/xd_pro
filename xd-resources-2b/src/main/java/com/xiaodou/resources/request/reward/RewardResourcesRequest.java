package com.xiaodou.resources.request.reward;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.forum.request.reward.RewardResourcesRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description 赞赏资源查询请求
 * @version 1.0
 */
public class RewardResourcesRequest extends BaseRequest {

  /** 赞赏记录ID */
  @NotEmpty
  private String rewardId;

  public String getRewardId() {
    return rewardId;
  }

  public void setRewardId(String rewardId) {
    this.rewardId = rewardId;
  }

}
