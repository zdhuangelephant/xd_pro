package com.xiaodou.ucenter.response;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.response.resultype.UcenterResType;

public class BaseResponse extends ResultInfo {

  public BaseResponse(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
    setServerIp(resType.getServerIp());
  }

  public BaseResponse(ResultType type) {
    super(type);
  }

  public BaseResponse() {
    super();
  }

  public boolean isRetOk() {
    return ResultType.SUCCESS.getCode().equals(this.getRetcode());
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
