package com.xiaodou.ms.web.request.course;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChapterEditRequest extends BaseRequest {

	/** 章节ID */
	private Long id;

	/** 章节父ID */
	private Long parentId;

	/** 当前章节的名称 */
	private String name;

	/** sIndex 中文序号 */
	private String sIndex;

	/** 章节的描述 */
	private String detail;

	private Long subjectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public void setsIndex(String sIndex) {
		this.sIndex = sIndex;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSIndex() {
		// TODO Auto-generated method stub
		return sIndex;
	}

}
