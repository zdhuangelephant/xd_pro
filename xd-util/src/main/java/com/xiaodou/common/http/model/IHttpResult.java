package com.xiaodou.common.http.model;

public interface IHttpResult {

  public boolean isResultOk();
  
  public Integer getHttpStatue();
  
  public String getContent();
  
}
