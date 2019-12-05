package com.xiaodou.mooccrawler.request;


public class CourseInfoPojo {

  private String deviceId;
  private String courseName;
  private String courseUrl;
  private String courseDetail;


  public String getDeviceId() {
    return deviceId;
  }


  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }


  public String getCourseName() {
    return courseName;
  }


  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


  public String getCourseUrl() {
    return courseUrl;
  }


  public void setCourseUrl(String courseUrl) {
    this.courseUrl = courseUrl;
  }


  public String getCourseDetail() {
    return courseDetail;
  }


  public void setCourseDetail(String courseDetail) {
    this.courseDetail = courseDetail;
  }


  public static class ChapterDetail {
    private String id;
    private Integer index;
    private String week;
    private String chapter;
    private String title;
    private String cid;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public Integer getIndex() {
      return index;
    }

    public void setIndex(Integer index) {
      this.index = index;
    }

    public String getWeek() {
      return week;
    }

    public void setWeek(String week) {
      this.week = week;
    }

    public String getChapter() {
      return chapter;
    }

    public void setChapter(String chapter) {
      this.chapter = chapter;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getCid() {
      return cid;
    }

    public void setCid(String cid) {
      this.cid = cid;
    }

  }
}
