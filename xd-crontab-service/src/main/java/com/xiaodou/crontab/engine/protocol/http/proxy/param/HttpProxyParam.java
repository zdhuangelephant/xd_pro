package com.xiaodou.crontab.engine.protocol.http.proxy.param;

import java.util.Map;

import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;

import lombok.Data;

/**
 * @name @see com.xiaodou.crontab.engine.protocol.http.proxy.IHttpProxyParam.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 代理参数基类
 * @version 1.0
 */
@Data
public abstract class HttpProxyParam {

  private Map<String, String> headerMap;
  private Map<String, String> paramMap;

  public Map<String, String> getHeader() {
    return headerMap;
  }

  public HttpProxyParam(CrontHttpProtocolConfig config) {
    this.headerMap = config.getHeaderMap();
    this.paramMap = config.getParamMap();
  }
}
