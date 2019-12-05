package com.xiaodou.oms.statemachine.engine.model;

import java.util.Map;

import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;

/**
 * <p>产品线远程API库</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public class ProductLine {
  
  /**
   * 产品线代码
   */
  private String code;
  
  /**
   * 产品线名称
   */
  private String name;
  
  private Map<String, RemoteAPI> apis;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, RemoteAPI> getApis() {
    return apis;
  }

  public void setApis(Map<String, RemoteAPI> apis) {
    this.apis = apis;
  }
  
  public RemoteAPI getRemoteAPI(String name){
    if(null==apis)return null;
    return apis.get(name);
  }

}
