package com.xiaodou.oms;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext({"/conf/core/elong-oms-servlet.xml"})
public class BaseUnitils extends UnitilsJUnit4 {

  @SpringApplicationContext
  public ApplicationContext applicationContext;

}
