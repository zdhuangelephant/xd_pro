package com.xiaodou.crontab.engine.protocol.http;

import java.util.Map;

import lombok.Data;

import com.google.common.collect.Maps;
import com.xiaodou.crontab.engine.protocol.ICrontProtocolConfig;

@Data
public class CrontHttpProtocolConfig implements ICrontProtocolConfig {

  /** dataProtocol 数据协议 */
  private String dataProtocol;
  /** headerMap 请求头map */
  private Map<String, String> headerMap = Maps.newHashMap();
  /** paramMap 请求参数map */
  private Map<String, String> paramMap = Maps.newHashMap();

}
