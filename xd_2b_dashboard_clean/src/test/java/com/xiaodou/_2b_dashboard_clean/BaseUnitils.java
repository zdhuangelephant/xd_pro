package com.xiaodou._2b_dashboard_clean;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * 
 * 单元测试基类
 */
@SpringApplicationContext({"conf/core/xd_2b_dashboard_clean.xml"})
public class BaseUnitils extends UnitilsJUnit4 {
  @SpringApplicationContext
  public ApplicationContext applicationContext;

}
