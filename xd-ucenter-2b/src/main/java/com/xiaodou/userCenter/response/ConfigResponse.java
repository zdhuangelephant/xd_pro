package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class ConfigResponse<T> extends BaseResultInfo {
  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public ConfigResponse(ResultType type) {
    super(type);
  }

  public ConfigResponse(UcenterResType type) {
    super(type);
  }

}
