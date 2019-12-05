package com.xiaodou.common.cache.redis.model.param;

/**
 * @name @see com.xiaodou.common.cache.redis.model.param.IJedisKeyValuePair.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月7日
 * @description Jedis键值对参数接口
 * @version 1.0
 */
public interface IJedisKeyValuePair {
  /**
   * 设置键
   * 
   * @param key
   */
  public void setKey(String key);

  /**
   * 获取键
   * 
   * @return
   */
  public String getKey();

  /**
   * 设置值
   * 
   * @param value
   */
  public void setValue(String value);

  /**
   * 获取值
   * 
   * @return
   */
  public String getValue();
}
