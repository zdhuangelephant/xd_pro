package com.xiaodou.summer.dao.mongo.param;

import com.xiaodou.summer.dao.mongo.enums.Scope;

/**
 * @name @see com.xiaodou.summer.dao.mongo.param.MongoFieldParam.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月16日
 * @description Mongo字段参数包装器
 * @version 1.0
 */
public class MongoFieldParam {
  public MongoFieldParam() {}

  public MongoFieldParam(Object value, Scope scope) {
    this.value = value;
    this.scope = scope;
  }

  /** scope 值范围 */
  private Scope scope = Scope.EQ;
  /** value 值 */
  private Object value;

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
