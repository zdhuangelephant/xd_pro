package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum CourseModelProperty {
	courseId("courseId", "1"), courseName("courseName", "1"), courseImage(
			"courseImage", "1"), examDate("examDate", "1"), isLatest(
			"isLatest", "1"), majorId("majorId", "1");
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

	private CourseModelProperty(String propertyName, String porpertyType) {
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
		output.put(CourseModelProperty.courseId.getPropertyName(),
				CourseModelProperty.courseId.getPropertyType());
		output.put(CourseModelProperty.courseName.getPropertyName(),
				CourseModelProperty.courseName.getPropertyType());
		output.put(CourseModelProperty.courseImage.getPropertyName(),
				CourseModelProperty.courseImage.getPropertyType());
		output.put(CourseModelProperty.examDate.getPropertyName(),
				CourseModelProperty.examDate.getPropertyType());
		output.put(CourseModelProperty.isLatest.getPropertyName(),
				CourseModelProperty.isLatest.getPropertyType());
		output.put(CourseModelProperty.majorId.getPropertyName(),
				CourseModelProperty.majorId.getPropertyType());
		return output;
	}
}
