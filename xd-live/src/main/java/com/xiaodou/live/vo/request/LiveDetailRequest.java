package com.xiaodou.live.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name LiveDetailRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播详情请求
 * @version 1.0
 */
public class LiveDetailRequest extends LiveBaseRequest {

  /** liveId 直播ID */
  @NotEmpty
  private String liveId;

  public String getLiveId() {
    return liveId;
  }

  public void setLiveId(String liveId) {
    this.liveId = liveId;
  }
  
}
