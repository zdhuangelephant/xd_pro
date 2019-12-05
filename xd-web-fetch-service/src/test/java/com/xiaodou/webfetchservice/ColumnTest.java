package com.xiaodou.webfetchservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaodou.webfetch.web.constants.Consts;
import com.xiaodou.webfetch.web.service.ColumnService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/core/xd-web-fetch-service.xml")
public class ColumnTest {
//  @Resource
//  ZhiHuController zhiHuController;
  
  @Resource
  ColumnService columnService;
  
  @Test
  public void testRunnerTask() {
//    zhiHuController.execute();
    columnService.save("zuxian", Consts.DEFAULT_PAGENO, Consts.DEFAULT_PAGSIZE);
  }

}
