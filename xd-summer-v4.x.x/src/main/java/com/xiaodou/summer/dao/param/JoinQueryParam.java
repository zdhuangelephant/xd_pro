package com.xiaodou.summer.dao.param;

import java.util.Map;

import com.google.common.collect.Maps;

public class JoinQueryParam extends QueryParam implements IJoinQueryParam {

  /** join SQL连接参数 */
  private Map<String, Object> join = Maps.newHashMap();

  public Map<String, Object> getJoin() {
    return join;
  }

  @Override
  public void addJoin(String key, Object value) {
    join.put(key, value);
  }

}
