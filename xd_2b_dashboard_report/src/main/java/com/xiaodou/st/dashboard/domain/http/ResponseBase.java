package com.xiaodou.st.dashboard.domain.http;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class ResponseBase extends ResultInfo {
  public ResponseBase() {}

  public ResponseBase(ResultType type) {
    super(type);
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }

  public boolean isRetOk() {
    return "0".equals(getRetcode());
  }

}
