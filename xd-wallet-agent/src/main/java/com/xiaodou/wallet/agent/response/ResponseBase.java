package com.xiaodou.wallet.agent.response;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;

public class ResponseBase extends ResultInfo{

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }

  public boolean isRetOk() {
    return "0".equals(getRetcode());
  }

}
