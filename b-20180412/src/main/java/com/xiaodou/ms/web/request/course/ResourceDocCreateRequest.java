package com.xiaodou.ms.web.request.course;

import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zyp on 15/7/5.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ResourceDocCreateRequest extends BaseRequest {
  private Long courseId;

  // 文档所属的大纲章节ID
  private Long chapterId;

  public Long getCourseId() {
	return courseId;
}

public void setCourseId(Long courseId) {
	this.courseId = courseId;
}

public Long getChapterId() {
	return chapterId;
}

public void setChapterId(Long chapterId) {
	this.chapterId = chapterId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public String getFileUrl() {
	return fileUrl;
}

public void setFileUrl(String fileUrl) {
	this.fileUrl = fileUrl;
}

public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
}

// 文档名称
  private String name;

  // 文档所在的URL地址
  private String url;

  // 下载地址
  private String fileUrl;

  // 文档详细描述
  private String detail;
}
