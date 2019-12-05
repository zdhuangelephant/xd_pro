package com.xiaodou.dashboard.model.crontab.inner;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.enums.CrontEnums.Protocol;
import com.xiaodou.dashboard.model.crontab.CrontabConfig;

/**
 * @name @see
 *       com.xiaodou.dashboard.model.crontab.inner.CrontProtocolConfigBuilder
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月8日
 * @description 调度协议配置生成器
 * @version 1.0
 */
public class CrontProtocolConfigBuilder {

	/**
	 * 生成协议配置信息
	 * 
	 * @param config
	 * @return
	 */
	public static CrontProtocolConfig buildProtocolConfig(CrontabConfig config) {
		Protocol protocol = Enum.valueOf(Protocol.class,
				config.getCrontProtocol());
		return FastJsonUtil.fromJson(config.getProtocolConfig(),
				protocol.getConfig());
	}

}
