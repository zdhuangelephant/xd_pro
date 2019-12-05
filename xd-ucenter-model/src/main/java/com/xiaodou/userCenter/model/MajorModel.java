package com.xiaodou.userCenter.model;

public class MajorModel {
	/** majorId 专业唯一标识 */
	private String majorId;
	/** majorName 专业名称 */
	private String majorName;
	private String majorImage;
	private String degree;

	private String module;

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorImage() {
		return majorImage;
	}

	public void setMajorImage(String majorImage) {
		this.majorImage = majorImage;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
