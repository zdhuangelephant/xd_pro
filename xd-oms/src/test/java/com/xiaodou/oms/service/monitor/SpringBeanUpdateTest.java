package com.xiaodou.oms.service.monitor;

import com.xiaodou.oms.BaseSpringTest;

import org.junit.Test;
import org.logicalcobwebs.proxool.ProxoolDataSource;

import javax.annotation.Resource;

/**
 * Date: 2014/9/30
 * Time: 15:41
 *
 * @author Tian.Dong
 */
public class SpringBeanUpdateTest extends BaseSpringTest{

  @Resource
  ProxoolDataSource orderDataSource;

  @Test
  public void test(){
    System.out.println(orderDataSource);
  }
}
