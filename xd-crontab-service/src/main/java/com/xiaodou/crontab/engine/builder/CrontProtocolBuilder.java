package com.xiaodou.crontab.engine.builder;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.enums.Protocol;
import com.xiaodou.crontab.engine.model.ConfigEntity;
import com.xiaodou.crontab.engine.protocol.AbstractCrontProtocol;
import com.xiaodou.crontab.engine.protocol.ICrontProtocol;
import com.xiaodou.crontab.engine.protocol.ICrontProtocolConfig;

/**
 * @name @see com.xiaodou.crontab.engine.builder.CrontProtocolBuilder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 协议构造器
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class CrontProtocolBuilder {

  /**
   * 构造协议
   */
  @SuppressWarnings("unchecked")
  public static ICrontProtocol buildProtocol(ConfigEntity config) {
    try {
      Protocol protocolEnum = Enum.valueOf(Protocol.class, config.getCrontProtocol());
      AbstractCrontProtocol protocol = protocolEnum.getProtocol().newInstance();
      ICrontProtocolConfig protocolConfig =
          FastJsonUtil.fromJson(config.getProtocolConfig(), protocolEnum.getProtocolConfig());
      protocol.setConfig(protocolConfig);
      protocol.setProtocol(protocolEnum);
      protocol.setTargetAddr(config.getCrontTarget());
      protocol.setTimeOut(config.getProtocolTimeOut());
      protocol.setRetryTime(config.getProtocolRetryTimes());
      protocol.setStructCheck(config.getProtocolStructCheck());
      return protocol;
    } catch (Exception e) {
      LoggerUtil.error("构造协议失败", e);
      return null;
    }

  }

}
