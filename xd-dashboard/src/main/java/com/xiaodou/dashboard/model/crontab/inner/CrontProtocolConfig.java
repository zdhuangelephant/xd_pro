package com.xiaodou.dashboard.model.crontab.inner;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.inner.CrontProtocolConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月8日
 * @description 协议基础配置项
 * @version 1.0
 */
@Data
public class CrontProtocolConfig {
	/** protocolRetryTimes 协议重试次数 */
	private Integer protocolRetryTimes;
	/** protocolTimeOut 协议超时时间 */
	private Integer protocolTimeOut;
}
