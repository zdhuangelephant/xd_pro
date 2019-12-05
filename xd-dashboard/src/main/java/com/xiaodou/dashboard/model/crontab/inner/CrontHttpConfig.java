package com.xiaodou.dashboard.model.crontab.inner;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Maps;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.inner.CrontHttpConfig.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月8日
 * @description HTTP协议配置项
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrontHttpConfig extends CrontProtocolConfig {
	/** dataProtocol 数据协议 */
	private String dataProtocol;
	/** headerMap 请求头map */
	private Map<String, String> headerMap = Maps.newHashMap();
	/** paramMap 请求参数map */
	private Map<String, String> paramMap = Maps.newHashMap();

	public String getDataProtocol() {
		return dataProtocol;
	}

	public void setDataProtocol(String dataProtocol) {
		this.dataProtocol = dataProtocol;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
