package com.xiaodou.common.cache.redis.model.param;

/**
 * @name @see com.xiaodou.common.cache.redis.model.param.JedisKeyValuePair.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月7日
 * @description Jedis键值对参数类默认实现
 * @version 1.0
 */
public class JedisKeyValuePair implements IJedisKeyValuePair {

  public JedisKeyValuePair() {}

  public JedisKeyValuePair(String key) {
    this.key = key;
  }

  private String key;
  private String value;

  @Override
  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
