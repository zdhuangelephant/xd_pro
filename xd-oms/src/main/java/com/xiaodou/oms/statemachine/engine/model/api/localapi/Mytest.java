package com.xiaodou.oms.statemachine.engine.model.api.localapi;

import org.springframework.stereotype.Component;

@Component("mytest")
public class Mytest {
  
  public Integer test(String a, String b, String c){
    System.out.println("It's a test method");
    return 100;
  }

}
