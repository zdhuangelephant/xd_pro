package com.xiaodou.ms.vo;

import lombok.Data;

/**
 * Created by zyp on 15/9/1.
 */
@Data
public class NeedProcessCsv {

  // 真题
  public static Integer zhenti = 1;

  // 非真题
  public static Integer feizhenti = 0;

  // 类型
  private Integer type;

  // 文件地址
  private String file;

  // 课程ID
  private Long courseId;

public static Integer getZhenti() {
	return zhenti;
}

public static void setZhenti(Integer zhenti) {
	NeedProcessCsv.zhenti = zhenti;
}

public static Integer getFeizhenti() {
	return feizhenti;
}

public static void setFeizhenti(Integer feizhenti) {
	NeedProcessCsv.feizhenti = feizhenti;
}

public Integer getType() {
	return type;
}

public void setType(Integer type) {
	this.type = type;
}

public String getFile() {
	return file;
}

public void setFile(String file) {
	this.file = file;
}

public Long getCourseId() {
	return courseId;
}

public void setCourseId(Long courseId) {
	this.courseId = courseId;
}

  
}
