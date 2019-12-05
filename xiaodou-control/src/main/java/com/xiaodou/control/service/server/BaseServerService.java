package com.xiaodou.control.service.server;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.control.service.facade.MongoDbServiceFacade;

@Service("baseServerService")
public class BaseServerService {
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;

  public BaseServerService() {}

}
