package com.xiaodou.webfetch.incise;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.webfetch.action.IncisePoint;

public class WeChatInciseRule implements InciseRule {
  private IncisePoint incisePoint;
  private WeChatInciseRule(IncisePoint incisePoint) {
    super();
    this.incisePoint = incisePoint;
  }
  
  private static HashMap<String, String> categoryHolder = Maps.newHashMap();
  
  static {
    init();
  }
  public static void init() {
    categoryHolder.put(CategoryEnums.TITLE.name(), "");
    categoryHolder.put(CategoryEnums.AUTHOR.name(), "");
    categoryHolder.put(CategoryEnums.CONTENT.name(), "");
  }
  
  public String poll(String category){
    if(StringUtils.isBlank(category) || !categoryHolder.containsKey(category.toUpperCase()))
      return org.apache.commons.lang.StringUtils.EMPTY;
    return categoryHolder.get(category.toUpperCase());
  }
  
  public String push(String category, String rules) {
    if(StringUtils.isBlank(category) || StringUtils.isBlank(rules))
      return org.apache.commons.lang.StringUtils.EMPTY;
    categoryHolder.put(category.toUpperCase(), rules);
    return rules;
  }
  
  
  @Override
  public String unique() {
    return null;
  }

  @Override  
  public void doIncise() {

  }
  
  public enum CategoryEnums{
    TITLE,AUTHOR,CONTENT
  }

  public IncisePoint getIncisePoint() {
    return incisePoint;
  }
  public void setIncisePoint(IncisePoint incisePoint) {
    this.incisePoint = incisePoint;
  }
  
  
}
