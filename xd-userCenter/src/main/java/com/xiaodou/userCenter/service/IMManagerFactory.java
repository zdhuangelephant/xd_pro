package com.xiaodou.userCenter.service;

import java.util.Map;
import com.xiaodou.im.agent.AbstractSelfManager;

public class IMManagerFactory {

  private Map<String, AbstractSelfManager> selfManagerMap;

  public Map<String, AbstractSelfManager> getSelfManagerMap() {
    return selfManagerMap;
  }

  public void setSelfManagerMap(Map<String, AbstractSelfManager> selfManagerMap) {
    this.selfManagerMap = selfManagerMap;
  }

  public AbstractSelfManager getSelfManager(String module) {
    return selfManagerMap.get(module);
  }

}
