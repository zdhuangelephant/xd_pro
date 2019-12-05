package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

/**
 * @name @see com.xiaodou.server.mapi.request.ucenter.UpdateBenchmarkFaceRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月11日
 * @description 更新基准头像请求类
 * @version 1.0
 */
public class UpdateBenchmarkFaceRequest extends MapiBaseRequest {

  /** benchmarkFaceUrl 头像地址 */
  private String benchmarkFaceUrl;

  public String getBenchmarkFaceUrl() {
    return benchmarkFaceUrl;
  }

  public void setBenchmarkFaceUrl(String benchmarkFaceUrl) {
    this.benchmarkFaceUrl = benchmarkFaceUrl;
  }



}
