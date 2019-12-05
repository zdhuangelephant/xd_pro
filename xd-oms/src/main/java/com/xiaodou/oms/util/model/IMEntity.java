package com.xiaodou.oms.util.model;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.agent.qq.response.PollResponse;

/**
 * Date: 2014/12/25
 * Time: 17:47
 *
 * @author Tian.Dong
 */
public class IMEntity {
  private PollResponse response;

  public PollResponse getResponse() {
    return response;
  }

  public void setResponse(PollResponse response) {
    this.response = response;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
