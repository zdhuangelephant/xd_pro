package com.xiaodou.forum.request.push;

import com.xiaodou.forum.request.forum.BaseRequest;

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
