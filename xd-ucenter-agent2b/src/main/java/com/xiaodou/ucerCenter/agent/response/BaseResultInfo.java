package com.xiaodou.ucerCenter.agent.response;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

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
}
