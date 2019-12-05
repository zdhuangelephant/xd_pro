package com.xiaodou.crontab.engine.protocol.http.proxy.param;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;

public class JsonHttpParam extends HttpProxyParam {

  public JsonHttpParam(CrontHttpProtocolConfig config) {
    super(config);
    this.jsonStr = FastJsonUtil.toJson(config.getParamMap());
  }

  private String jsonStr;

  public String getJson() {
    return jsonStr;
  }

}
