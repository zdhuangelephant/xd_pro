package com.xiaodou.mooccrawler.domain.course;

/**
 * @name @see com.xiaodou.ms.model.course.CourseResourceAudioModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频模型model
 * @version 1.0
 */
public class CourseResourceAudioModel {

  // 视频ID
  private Integer id;

  // 课程Id
  private Integer courseId;

  // 音频所属的大纲章节ID
  private Integer chapterId;

  // 音频名称
  private String name;

  // 音频所在的URL地址
  private String url;

  // 时长展示分钟段
  private Integer timeLengthMinute;

  // 时长展示秒数段
  private Integer timeLengthSecond;

  // 音频文件大小M
  private Double size;

  // 音频详细描述
  private String detail;

  // 音频的关键字，方便全局搜索
  private String keyPoint;

  // 章节名称
  private String chapterName;

  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
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

  public Integer getTimeLengthMinute() {
    return timeLengthMinute;
  }

  public void setTimeLengthMinute(Integer timeLengthMinute) {
    this.timeLengthMinute = timeLengthMinute;
  }

  public Integer getTimeLengthSecond() {
    return timeLengthSecond;
  }

  public void setTimeLengthSecond(Integer timeLengthSecond) {
    this.timeLengthSecond = timeLengthSecond;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
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
}
