package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum BlankNoticeModelProperty {
	id("id", "1"), type("type", "1"), module("module", "1"), jumpUrl(
			"jumpUrl", "1"),title("title","1"),image("image","1"),isVisible("isVisible","1");
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

	private BlankNoticeModelProperty(String propertyName, String porpertyType) {
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
		output.put(BlankNoticeModelProperty.id.getPropertyName(),
				BlankNoticeModelProperty.id.getPropertyType());
		output.put(BlankNoticeModelProperty.type.getPropertyName(),
				BlankNoticeModelProperty.type.getPropertyType());
		output.put(BlankNoticeModelProperty.module.getPropertyName(),
				BlankNoticeModelProperty.module.getPropertyType());
		output.put(BlankNoticeModelProperty.jumpUrl.getPropertyName(),
				BlankNoticeModelProperty.jumpUrl.getPropertyType());
		output.put(BlankNoticeModelProperty.title.getPropertyName(),
		  BlankNoticeModelProperty.title.getPropertyType());
		output.put(BlankNoticeModelProperty.image.getPropertyName(),
		  BlankNoticeModelProperty.image.getPropertyType());
		output.put(BlankNoticeModelProperty.isVisible.getPropertyName(),
		  BlankNoticeModelProperty.isVisible.getPropertyType());
		return output;
	}
}
