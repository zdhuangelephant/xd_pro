package com.xiaodou.resources.enums.product;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by zyp on 15/4/19.
 */
public enum ResourceType {

  CHAPTER("章节","chapter",1),
  VIDEO("视频","video",2),
  DOC("文档","doc",3),
  HTML5("html5","html5",4),
  QUESTION("试题","question",5),
  EXERCISE("练习","exercise",6),
  QUIZ("随堂测验","quiz",7),
  TALK("讨论","talk",8),
  TASK("单元任务","task",9),
  EXAM("单元测验","exam",10),
  FINAL("期末测试","final",11);


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

  ResourceType(String typeName,String type, Integer typeId) {
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
  
  private final static Map<Integer, ResourceType> _allResourceType = Maps.newHashMap();

  private static void init() {
    for (ResourceType type : ResourceType.values()) {
      if (null == type) continue;
      _allResourceType.put(type.getTypeId(), type);
    }
  }

  static {
    init();
  }

  public static ResourceType getByTypeId(Integer code) {
    return _allResourceType.get(code);
  }
}
