package com.xiaodou.ms.web.request.selftaught;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class MajorRequest extends BaseRequest {
	/** majorId 专业唯一标识 */
	private String majorId;
	@NotEmpty
	/** majorName 专业名称 */
	private String majorName;
	@NotEmpty
	private String majorImage;
	@NotEmpty
	private String degree;
	@NotEmpty
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
