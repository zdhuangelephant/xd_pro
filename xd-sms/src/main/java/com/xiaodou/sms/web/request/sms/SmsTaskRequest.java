package com.xiaodou.sms.web.request.sms;

import com.xiaodou.sms.web.request.BaseRequest;

/**
 * 日志表入参
 * @author wuyunkuo
 *
 */
public class SmsTaskRequest extends BaseRequest {
	
	/**
	 * 批处理个数
	 */
	private int maxMum;

	public int getMaxMum() {
		return maxMum;
	}

	public void setMaxMum(int maxMum) {
		this.maxMum = maxMum;
	}
	
	
	
}
