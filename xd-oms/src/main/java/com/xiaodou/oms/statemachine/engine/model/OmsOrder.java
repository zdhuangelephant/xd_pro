package com.xiaodou.oms.statemachine.engine.model;

import java.util.Map;

import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;

/**
 * <p>OMS本地API库</p>
 * 保存了所有LocalAPI,所有Transaction中依赖的LocalAPI都保存在这里
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
public class OmsOrder {

  
  private Map<String, LocalAPI> apis;

  public Map<String, LocalAPI> getApis() {
    return apis;
  }

  public void setApis(Map<String, LocalAPI> apis) {
    this.apis = apis;
  }
  
  public LocalAPI getLocalAPI(String apiName){
    return this.apis.get(apiName);
  }
}
