package com.xiaodou.ucerCenter.agent.request;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 七牛获取UpTokenPojo类
 * 
 * @author Administrator
 * 
 */
public class UpTokenPojo extends BaseRequest {

  @NotEmpty
  private String scope;

  private String key;

  private Long deadLine = System.currentTimeMillis() / 1000 + 3600;

  public String getScope() {
    return StringUtils.isNotBlank(key) ? scope + ":" + key : scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void setKey(String key) {
    this.key = key;
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
