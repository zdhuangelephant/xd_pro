package com.xiaodou.ms.model.common;

public class XDcity {
  private Long id;
  private String name;
  private String uniqueId;
  private Integer parentId;
  private Integer locLevel;
  
  private String name1;
  private String name2;
  
  public String getName1() {
    return name1;
  }
  public void setName1(String name1) {
    this.name1 = name1;
  }
  public String getName2() {
    return name2;
  }
  public void setName2(String name2) {
    this.name2 = name2;
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
    this.name = name;
  }
  public String getUniqueId() {
    return uniqueId;
  }
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }
  public Integer getParentId() {
    return parentId;
  }
  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }
  public Integer getLocLevel() {
    return locLevel;
  }
  public void setLocLevel(Integer locLevel) {
    this.locLevel = locLevel;
  }
}
