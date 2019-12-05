package com.xiaodou.ms.model.selftaught;

import java.sql.Timestamp;

import com.xiaodou.ms.web.request.selftaught.MajorRequest;

public class MajorModel {
	/** majorId 专业唯一标识 */
	private String majorId;
	/** majorName 专业名称 */
	private String majorName;
	private String majorImage;
	private String degree;
	private String module;
	private Timestamp createTime;

	public MajorModel getMajorModel(MajorRequest request) {
		this.majorId = request.getMajorId();
		this.majorImage = request.getMajorImage();
		this.majorName = request.getMajorName();
		this.degree = request.getDegree();
		this.module = request.getModule();
		return this;
	}

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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
