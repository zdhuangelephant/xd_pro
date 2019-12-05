package com.xiaodou.ms.enums;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @name ProductCatagoryClassify CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月14日
 * @description 专业分类
 * @version 1.0
 */
public enum ProductCatagoryClassify {
  SelfTaught(1, "自考专业"), Feature(2, "特设专业");
  ProductCatagoryClassify(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  private Integer code;
  private String name;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private static Map<Integer, ProductCatagoryClassify> typeMap = Maps.newHashMap();

  static {
    for (ProductCatagoryClassify type : ProductCatagoryClassify.values())
      typeMap.put(type.getCode(), type);
  }

  public static ProductCatagoryClassify getByCode(Integer code) {
    return typeMap.get(code);
  }
}
