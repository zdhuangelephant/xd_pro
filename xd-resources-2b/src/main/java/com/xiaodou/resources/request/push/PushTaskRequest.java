package com.xiaodou.resources.request.push;

import com.xiaodou.resources.request.BaseRequest;

public class PushTaskRequest extends BaseRequest {
  /**
   * 批处理个数
   */
  private String maxNum;

  public String getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(String maxNum) {
    this.maxNum = maxNum;
  }
}
