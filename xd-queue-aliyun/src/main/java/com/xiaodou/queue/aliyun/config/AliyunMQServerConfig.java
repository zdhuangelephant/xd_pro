package com.xiaodou.queue.aliyun.config;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.queue.config.AbstractMQServerConfig;
import com.xiaodou.queue.config.IMQServerConfig;

public class AliyunMQServerConfig extends AbstractMQServerConfig {

  private FileUtil config = FileUtil.getInstance("/conf/custom/env/mq_aliyun_config.properties");

  private static IMQServerConfig single;

  public static IMQServerConfig getInstance() {
    if (null == single) synchronized (AliyunMQServerConfig.class) {
      single = new AliyunMQServerConfig();
    }
    return single;
  }

  private Map<String, String> _defaultMap = new HashMap<String, String>() {
    /** serialVersionUID */
    private static final long serialVersionUID = 1860920041116914136L;

    public Map<String, String> initThis() {
      return this;
    }
  }.initThis();

  @Override
  protected String _getParam0(String key) {
    String param = config.getProperties(key);
    if (StringUtils.isNotBlank(param)) return param;
    if (_defaultMap.containsKey(key)) return _defaultMap.get(key);
    return StringUtils.EMPTY;
  }

}
