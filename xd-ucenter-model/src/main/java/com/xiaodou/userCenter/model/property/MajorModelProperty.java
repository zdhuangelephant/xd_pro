package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum MajorModelProperty {
	majorId("majorId", "1"), majorName("majorName", "1"), majorImage("magorImage", "1"), degree(
			"degree", "1"), module("module","1");
	private String propertyName;
	private String propertyType;

	/** 属性名称 */
	public String getPropertyName() {
		return propertyName;
	}

	/** 属性类型 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	private MajorModelProperty(String propertyName, String porpertyType) {
		this.propertyName = propertyName;
		this.propertyType = porpertyType;
	}

	/**
	 * 
	 * 所有出参map
	 * 
	 * @return
	 */
	public static Map<String, Object> getAllInfo() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put(MajorModelProperty.majorId.getPropertyName(),
				MajorModelProperty.majorId.getPropertyType());
		output.put(MajorModelProperty.majorName.getPropertyName(),
				MajorModelProperty.majorName.getPropertyType());
		output.put(MajorModelProperty.majorImage.getPropertyName(),
				MajorModelProperty.majorImage.getPropertyType());
		output.put(MajorModelProperty.degree.getPropertyName(),
				MajorModelProperty.degree.getPropertyType());
		output.put(MajorModelProperty.module.getPropertyName(),
				MajorModelProperty.module.getPropertyType());
		return output;
	}
}
