package com.xiaodou.ms.web.request.course;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/4/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryEditRequest extends BaseRequest {

	// 课程分类ID
	@NotEmpty
	private Long id;
	// 课程分类父ID
	private Long parentId;
	// 课程分类名称
	private String name;
	// 课程分类详细介绍
	private String detail;

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
