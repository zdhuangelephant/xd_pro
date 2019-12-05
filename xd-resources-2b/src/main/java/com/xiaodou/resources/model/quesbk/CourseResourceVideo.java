package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;

public class CourseResourceVideo extends BaseEntity {
  private Long id;

  private Long chapterId;

  private String name;

  private String url;

  private String detail;

  private String searchKeywords;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    this.name = name == null ? null : name.trim();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url == null ? null : url.trim();
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail == null ? null : detail.trim();
  }

  public String getSearchKeywords() {
    return searchKeywords;
  }

  public void setSearchKeywords(String searchKeywords) {
    this.searchKeywords = searchKeywords == null ? null : searchKeywords.trim();
  }
}
