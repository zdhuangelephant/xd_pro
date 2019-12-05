package com.xiaodou.forum.service;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.thrift.client.TRPCClient;
import com.xiaodou.thrift.model.DefaultModel;

public class ThriftTest extends BaseUnitils {
  
  @SpringBean("startClient")
  TRPCClient startClient;
  
  @Test
  public void checkTokenTest(){
    DefaultModel model = startClient.thriftStartClient("ucenterService", "thriftDecideRedisExsited", "be6f7e09-a167-44ed-b0fe-a62fd5a9e9b6");
    System.out.println(model);
  }
}
