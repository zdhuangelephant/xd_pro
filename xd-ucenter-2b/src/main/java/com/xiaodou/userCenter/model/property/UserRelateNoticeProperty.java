package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum UserRelateNoticeProperty {
	status("status", "1"), userId("userId", "1"), noticeId("noticeId", "1"), title("title",
			"1"), createTime("createTime", "1");
	/** 属性名称 */
	private String propertyName;

	/** 属性类型 */
	private String propertyType;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	private UserRelateNoticeProperty(String propertyName, String propertyType) {
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}

	/**
	 * 
	 * 所有出参map
	 * 
	 * @return
	 */
	public static Map<String, Object> getAllInfo() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put(UserRelateNoticeProperty.status.getPropertyName(),
				UserRelateNoticeProperty.status.getPropertyType());
		output.put(UserRelateNoticeProperty.userId.getPropertyName(),
				UserRelateNoticeProperty.userId.getPropertyType());
		output.put(UserRelateNoticeProperty.noticeId.getPropertyName(),
				UserRelateNoticeProperty.noticeId.getPropertyType());
		output.put(UserRelateNoticeProperty.title.getPropertyName(),
				UserRelateNoticeProperty.title.getPropertyType());
		output.put(UserRelateNoticeProperty.createTime.getPropertyName(),
				UserRelateNoticeProperty.createTime.getPropertyType());
		return output;
	}

}
