package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedBackCategoryRequest extends MapiBaseRequest {

	@NotEmpty
	private String feedContent;

	/* 类型描述列表 */
	@NotEmpty
	private String categoryDescList;

	/* 邮箱或者手机号 */
	@NotEmpty
	private String number;

	/* 图片列表 */
	private String imageUrlList;

	/* 手机设备类型 */
	@NotEmpty
	private String deviceType;

	/* os版本 */
	@NotEmpty
	private String osVersion;

	public String getFeedContent() {
		return feedContent;
	}

	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}

	public String getCategoryDescList() {
		return categoryDescList;
	}

	public void setCategoryDescList(String categoryDescList) {
		this.categoryDescList = categoryDescList;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(String imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	// @Override
	// public Errors validate() {
	// Errors errors = super.validate();
	// if (errors.hasErrors()) return errors;
	// if (StringUtils.isNotBlank(feedContent) && feedContent.length() > 1000)
	// errors.rejectValue("feedContent", null, "反馈信息不能超过1000字符");
	// return errors;
	// }

}
