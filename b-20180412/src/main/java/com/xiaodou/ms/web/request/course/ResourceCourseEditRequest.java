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
public class ResourceCourseEditRequest extends BaseRequest {
  // 课程ID
  @NotEmpty
  private Long id;
  // 课程对应的分类ID
  @NotEmpty
  private Long categoryId;
  public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getCategoryId() {
	return categoryId;
}

public void setCategoryId(Long categoryId) {
	this.categoryId = categoryId;
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

// 课程名称
  @NotEmpty
  private String name;

  // 课程详情
  private String detail;
}
