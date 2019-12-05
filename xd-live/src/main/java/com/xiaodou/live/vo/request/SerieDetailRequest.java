package com.xiaodou.live.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name SerieDetailRequest 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 系列直播详情请求
 * @version 1.0
 */
public class SerieDetailRequest extends LiveBaseRequest {
  
  /** liveSerieId 直播系列ID */
  @NotEmpty
  private String liveSerieId;

  public String getLiveSerieId() {
    return liveSerieId;
  }

  public void setLiveSerieId(String liveSerieId) {
    this.liveSerieId = liveSerieId;
  }

}
