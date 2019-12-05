package com.xiaodou.wallet.response;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class BaseResponse extends ResultInfo {

  public BaseResponse() {}

  public BaseResponse(ResultType resultType) {
    super(resultType);
  }

  public BaseResponse(WalletResType type) {
    setRetcode(type.getCode());
    setRetdesc(type.getMsg());
    setServerIp(type.getServerIp());
  }

}
