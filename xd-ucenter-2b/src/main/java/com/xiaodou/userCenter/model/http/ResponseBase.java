package com.xiaodou.userCenter.model.http;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class ResponseBase extends ResultInfo {

  public ResponseBase() {}

  public ResponseBase(ResultType type) {
    super(type);
  }
  
  public ResponseBase(UcenterResType resType) {
    super(resType.getCode(),resType.getMsg(),resType.getServerIp());
  }
  
  @Override
  public String toString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }

  public boolean isRetOk() {
    return "0".equals(getRetcode());
  }

}
