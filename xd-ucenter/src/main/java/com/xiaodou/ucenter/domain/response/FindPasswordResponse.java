package com.xiaodou.ucenter.domain.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;

public class FindPasswordResponse extends BaseResultInfo {

  public FindPasswordResponse() {
    super();
  }

  public FindPasswordResponse(ResultType type) {
    super(type);
  }

  public FindPasswordResponse(UcenterResType resType) {
    super(resType);
  }


}
