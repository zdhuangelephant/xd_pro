package com.xiaodou.userCenter.response;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class BaseResultInfo extends ResultInfo {

  public BaseResultInfo(UcenterResType resType) {
    setRetcode(resType.getCode());
    setRetdesc(resType.getMsg());
    setServerIp(resType.getServerIp());
  }

  public BaseResultInfo(ResultType type) {
    super(type);
  }

  public BaseResultInfo() {
    super();
  }

  public boolean isRetOk() {
    return ResultType.SUCCESS.getCode().equals(this.getRetcode());
  }

  public String toJsonString() {
    return FastJsonUtil.toJson(this);
  }
}
