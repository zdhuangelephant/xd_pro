package com.xiaodou.ucerCenter.agent.service;

import com.xiaodou.ucerCenter.agent.request.BaseRequest;
import com.xiaodou.ucerCenter.agent.request.ConfigRequest;
import com.xiaodou.ucerCenter.agent.response.ConfigResponse;

public interface IConfigService<CR extends ConfigRequest, V extends ConfigResponse<?>, D extends ConfigResponse<?>> {

  public V configVersion(BaseRequest pojo);
  
  public D config(CR request);

}
