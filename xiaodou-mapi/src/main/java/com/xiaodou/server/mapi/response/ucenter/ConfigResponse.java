package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class ConfigResponse<T> extends BaseResponse {
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
