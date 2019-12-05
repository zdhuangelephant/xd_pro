package com.xiaodou.userCenter.service;

import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.ConfigRequest;
import com.xiaodou.userCenter.response.ConfigResponse;

public interface IConfigService<CR extends ConfigRequest, V extends ConfigResponse<?>, D extends ConfigResponse<?>> {

  public V configVersion(BaseRequest pojo);
  
  public D config(CR request);

}
