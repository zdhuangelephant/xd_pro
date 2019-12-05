package com.xiaodou.userCenter.module.jz.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.userCenter.request.ConfigRequest;

/**
 * @name @see com.xiaodou.userCenter.module.jz.request.JzConfigRequest.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月31日
 * @description 教师资格配置文件请求类
 * @version 1.0
 */
public class JzConfigRequest extends ConfigRequest {

	/** city 城市 */
	@LegalValueList({ "0", "1" })
	private String city;
	/** examDate 考期 */
	@LegalValueList({ "0", "1" })
	private String examDate;
	/** advertisement 广告 */
	@LegalValueList({ "0", "1" })
	private String advertisement;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}

	public String getBlankNotice() {
		return blankNotice;
	}

	public void setBlankNotice(String blankNotice) {
		this.blankNotice = blankNotice;
	}

}
