package com.xiaodou.course.common.enums;

/**
 * Created by zyp on 15/4/19.
 */
public enum ResourceType {

  CHAPTER("章节","chapter",1),
  VIDEO("视频","video",2),
  DOC("文档","doc",3),
  HTML5("html5","html5",4),
  QUESTION("试题","question",5),
  EXERCISE("练习","exercise",6);

  /**
   * 类型名称
   */
  private String typeName;

  /**
   * 类型
   */
  private String type;

  /**
   * 类型Id
   */
  private Integer typeId;

  ResourceType(String typeName, String type, Integer typeId) {
    this.typeName = typeName;
    this.type = type;
    this.typeId = typeId;
  }

  public String getTypeName() {
    return typeName;
  }

  public String getType() {
    return type;
  }

  public Integer getTypeId() {
    return typeId;
  }
}
