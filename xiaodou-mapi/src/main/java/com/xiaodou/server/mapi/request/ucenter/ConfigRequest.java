package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigRequest extends MapiBaseRequest {
	/** majorList 专业列表 */
	@LegalValueList({ "0", "1" })
	private String majorList;

	/** thirdlogin 三方登录 */
	@LegalValueList({ "0", "1" })
	private String thirdlogin;
	/** shareplateform 分享平台 */
	@LegalValueList({ "0", "1" })
	private String shareplateform;
	/** blankNotice 系统公告和活动 */
	@LegalValueList({ "0", "1" })
	private String blankNotice;
	/** feedBackCategory 意见反馈类别 */
	@LegalValueList({ "0", "1" })
	private String feedbackCategory;
	/** sysCount 信息数目 */
	@LegalValueList({ "0", "1" })
	private String sysCount;
	public String getMajorList() {
		return majorList;
	}
	public void setMajorList(String majorList) {
		this.majorList = majorList;
	}
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
	public String getFeedbackCategory() {
		return feedbackCategory;
	}
	public void setFeedbackCategory(String feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}
	public String getSysCount() {
		return sysCount;
	}
	public void setSysCount(String sysCount) {
		this.sysCount = sysCount;
	}
	
}
