package com.xiaodou.userCenter.service;

import java.util.Map;

import com.xiaodou.im.IMSelfManager;

public class IMManagerFactory {

  private Map<String, IMSelfManager> selfManagerMap;

  public Map<String, IMSelfManager> getSelfManagerMap() {
    return selfManagerMap;
  }

  public void setSelfManagerMap(Map<String, IMSelfManager> selfManagerMap) {
    this.selfManagerMap = selfManagerMap;
  }

  public IMSelfManager getSelfManager(String module) {
    return selfManagerMap.get(module);
  }

}
