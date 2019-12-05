package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;

/**
 * @name ColumnistListRequest CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月31日
 * @description 专栏列表请求
 * @version 1.0
 */
public class ColumnistListRequest extends BaseRequest {
  /** lastColumnistId 最后一条专栏ID */
  private String lastColumnistId;

  public String getLastColumnistId() {
    return lastColumnistId;
  }

  public void setLastColumnistId(String lastColumnistId) {
    this.lastColumnistId = lastColumnistId;
  }

}
