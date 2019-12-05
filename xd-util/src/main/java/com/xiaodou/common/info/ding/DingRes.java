package com.xiaodou.common.info.ding;

import lombok.Data;

/**
 * @name @see com.xiaodou.common.info.ding.req.IDingRes.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知响应类
 * @version 1.0
 */
@Data
public class DingRes {
	/** errcode 异常码 */
	private String errcode;
	/** errmsg 异常信息 */
	private String errmsg;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
}
