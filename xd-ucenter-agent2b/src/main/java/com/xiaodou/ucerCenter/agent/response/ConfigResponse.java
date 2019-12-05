package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class ConfigResponse<T> extends BaseResultInfo {
  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public ConfigResponse() {}

  public ConfigResponse(ResultType type) {
    super(type);
  }

  public ConfigResponse(UcenterResType type) {
    super(type);
  }

}
