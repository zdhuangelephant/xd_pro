package com.xiaodou.queue.config;


/**
 * @name @see com.xiaodou.queue.client.config.AbstractMQServerConfig.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月23日
 * @description 定义MQ配置文件获取参数方法
 * @version 1.0
 */
public abstract class AbstractMQServerConfig implements IMQServerConfig {

  @Override
  public final String getParam(ConfigParam param) {
    return _getParam0(param.toString());
  }

  protected abstract String _getParam0(String key);

}
