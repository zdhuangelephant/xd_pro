package com.xiaodou.crontab.engine.enums;

import com.xiaodou.crontab.engine.protocol.AbstractCrontProtocol;
import com.xiaodou.crontab.engine.protocol.ICrontProtocolConfig;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocol;
import com.xiaodou.crontab.engine.protocol.http.CrontHttpProtocolConfig;

/**
 * @name @see com.xiaodou.crontab.engine.builder.CrontProtocolBuilder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 调度协议枚举
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public enum Protocol {
  HTTP(CrontHttpProtocol.class, CrontHttpProtocolConfig.class), HTTPS(CrontHttpProtocol.class,
      CrontHttpProtocolConfig.class);
  Protocol(Class<? extends AbstractCrontProtocol> protocol,
      Class<? extends ICrontProtocolConfig> protocolConfig) {
    this.protocol = protocol;
    this.protocolConfig = protocolConfig;
  }

  private Class<? extends AbstractCrontProtocol> protocol;
  private Class<? extends ICrontProtocolConfig> protocolConfig;

  public Class<? extends AbstractCrontProtocol> getProtocol() {
    return protocol;
  }

  public Class<? extends ICrontProtocolConfig> getProtocolConfig() {
    return protocolConfig;
  }

}
