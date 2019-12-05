package com.xiaodou.ms.web.request.common;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class UpTokenPojo {

  @NotEmpty
  private String scope;

  private Long deadLine = System.currentTimeMillis() / 1000 + 3600;

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Long getDeadline() {
    return deadLine;
  }

  @Override
  public String toString() {
    StringBuffer serialized = new StringBuffer(200);
    serialized.append("{\"scope\":\"").append(getScope()).append("\",\"deadline\":")
        .append(getDeadline()).append("}");
    return serialized.toString();
  }

}
