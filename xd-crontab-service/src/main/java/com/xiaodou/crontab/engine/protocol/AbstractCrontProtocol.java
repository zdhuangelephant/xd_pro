package com.xiaodou.crontab.engine.protocol;

import lombok.Data;

import com.xiaodou.crontab.engine.enums.Protocol;


/**
 * @name @see com.xiaodou.crontab.engine.protocol.AbstractCrontProtocol.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 抽象协议类
 * @version 1.0
 */
@Data
public abstract class AbstractCrontProtocol<T extends ICrontProtocolConfig>
    implements
      ICrontProtocol<T> {
  private String targetAddr;
  private Boolean structCheck;
  private Integer timeOut;
  private Integer retryTime;

  public abstract void setProtocol(Protocol protocol);
  public abstract void setConfig(T protocolConfig);
}
