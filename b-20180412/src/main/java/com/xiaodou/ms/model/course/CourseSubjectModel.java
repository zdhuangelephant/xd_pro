package com.xiaodou.ms.model.course;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @ClassName: SubjectModel
 * @Description: 科目课程Model
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午5:23:13
 */
@Data
public class CourseSubjectModel {

  // 课程ID
  private Long id;

  // 课程对应的分类ID
  private Long categoryId;

  // 课程名称
  private String name;

  // 课程详情
  private String detail;

  // 创建时间
  private Timestamp createTime;

  // 栏目名称
  private String categoryName;

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

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
  
}
