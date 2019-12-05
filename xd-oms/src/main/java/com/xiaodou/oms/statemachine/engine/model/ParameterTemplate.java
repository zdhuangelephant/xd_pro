package com.xiaodou.oms.statemachine.engine.model;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.oms.exception.ExceptionMessageProp;

/**
 * <p>参数模板库</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月16日
 */
public class ParameterTemplate {
  
  /**
   * vm模板基础路径
   */
  private String basePath;

  /**
   * 模板集
   */
  private Map<String, Template> templateMap;

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String bathPath) {
    this.basePath = bathPath;
  }

  public Map<String, Template> getTemplateMap() {
    return templateMap;
  }

  public void setTemplateMap(Map<String, Template> templateMap) {
    this.templateMap = templateMap;
  }

  public Template getTemplate(String key) {
    if (null == templateMap) return null;
    return templateMap.get(key);
  }

  public void putTemplate(String key, Template template) {
    if (null == templateMap) {
      synchronized (this) {
        if (null == templateMap) templateMap = Maps.newHashMap();
      }
    }
    if(templateMap.containsKey(key))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
        "error.doc.loaddoc.template.same", key));
    templateMap.put(key, template);
  }
  
  public boolean hasTemplate(String key){
    if (null == templateMap) return false;
    return templateMap.containsKey(key);
  }

}
