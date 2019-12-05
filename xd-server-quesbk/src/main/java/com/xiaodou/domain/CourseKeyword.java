package com.xiaodou.domain;


public class CourseKeyword extends BaseEntity {
  private Long id;

  private Integer importanceLevel;

  private String name;

  private String detail;

  public Integer getImportanceLevel() {
    return importanceLevel;
  }

  public void setImportanceLevel(Integer importanceLevel) {
    this.importanceLevel = importanceLevel;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

}
