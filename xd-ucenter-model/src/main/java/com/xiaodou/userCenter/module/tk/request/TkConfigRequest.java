package com.xiaodou.userCenter.module.tk.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.userCenter.request.ConfigRequest;

public class TkConfigRequest extends ConfigRequest{
	/** thirdlogin 三方登录 */
	@LegalValueList({ "0", "1" })
	private String thirdlogin;
	/** shareplateform 分享平台 */
	@LegalValueList({ "0", "1" })
	private String shareplateform;
	/** blankNotice 系统公告和活动 */
	@LegalValueList({ "0", "1" })
	private String blankNotice;
	public String getThirdlogin() {
		return thirdlogin;
	}
	public void setThirdlogin(String thirdlogin) {
		this.thirdlogin = thirdlogin;
	}
	public String getShareplateform() {
		return shareplateform;
	}
	public void setShareplateform(String shareplateform) {
		this.shareplateform = shareplateform;
	}
	public String getBlankNotice() {
		return blankNotice;
	}
	public void setBlankNotice(String blankNotice) {
		this.blankNotice = blankNotice;
	}
	
}
