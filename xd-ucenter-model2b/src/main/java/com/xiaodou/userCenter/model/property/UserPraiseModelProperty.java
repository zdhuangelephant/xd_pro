package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum UserPraiseModelProperty {
	id("id", "1"), userId("userId", "1"), targetUserId("targetUserId", "1");
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

	private UserPraiseModelProperty(String propertyName, String propertyType) {
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
		output.put(UserPraiseModelProperty.id.getPropertyName(),
				UserPraiseModelProperty.id.getPropertyType());
		output.put(UserPraiseModelProperty.userId.getPropertyName(),
				UserPraiseModelProperty.userId.getPropertyType());
		output.put(UserPraiseModelProperty.targetUserId.getPropertyName(),
				UserPraiseModelProperty.targetUserId.getPropertyType());
		return output;
	}

}
