package com.xiaodou.webfetch.unique;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.webfetch.data.TargetData.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月12日
 * @description 可达实现
 * @version 1.0
 */
public class Target {

  /** uniqueId 唯一标识ID */
  private String uniqueId;

  public Target() {};

  public Target(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public synchronized String unique() {
    if (StringUtils.isBlank(uniqueId)) {
      this.uniqueId = UUID.randomUUID().toString();
    }
    return uniqueId;
  }

}
