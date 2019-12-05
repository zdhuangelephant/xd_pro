package com.xiaodou.crontab.engine.protocol;


/**
 * @name @see com.xiaodou.crontab.engine.protocol.ICrontProtocol.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description 调度协议
 * @version 1.0
 */
public interface ICrontProtocol<T extends ICrontProtocolConfig> {

  public CrontResult crontabJob();

}
