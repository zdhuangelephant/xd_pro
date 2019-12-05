package com.xiaodou.ms.model.course;

import lombok.Data;

/**
 * 
 * @ClassName: VideoModel
 * @Description: 资源类型之一:视频Model
 * @author zhaoxu.yang
 * @date 2015年4月12日 上午10:57:55
 */
@Data
public class CourseResourceVideoModel {
  // 视频ID
  private Long id;

  // 课程Id
  private Long courseId;

  // 视频所属的大纲章节ID
  private Long chapterId;

  // 视频名称
  private String name;

  // 视频所在的URL地址
  private String url;

  // 文件下载地址
  private String fileUrl;

  // 视频详细描述
  private String detail;

  // 视频的关键字，方便全局搜索
  private String keyPoint;

  // 章节名称
  private String chapterName;

  private Integer status;
  private String type;
  private Integer timeLengthSecond;
  private Integer timeLengthMinute;
  private String img;
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

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

public String getKeyPoint() {
	return keyPoint;
}

public void setKeyPoint(String keyPoint) {
	this.keyPoint = keyPoint;
}

public String getChapterName() {
	return chapterName;
}

public void setChapterName(String chapterName) {
	this.chapterName = chapterName;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public Integer getTimeLengthSecond() {
	return timeLengthSecond;
}

public void setTimeLengthSecond(Integer timeLengthSecond) {
	this.timeLengthSecond = timeLengthSecond;
}

public Integer getTimeLengthMinute() {
	return timeLengthMinute;
}

public void setTimeLengthMinute(Integer timeLengthMinute) {
	this.timeLengthMinute = timeLengthMinute;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getImg() {
	return img;
}

public void setImg(String img) {
	this.img = img;
}

  
}
