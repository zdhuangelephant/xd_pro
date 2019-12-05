package com.xiaodou.oms.service.order;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.service.oms.ShopTagService;




public class ShopTagServiceTest extends BaseSpringTest{
  
  @Resource
  ShopTagService shopTagService;
  
  //@Test
  public void createTagTest(){
    System.out.println(JSON.toJSONString(shopTagService.createTag("1", "1201")));
  }
  @Test
  public void transQueryTagAndUpdateForProcessTest(){
    //System.out.println(shopTagService.transQueryTagAndUpdateForProcess("dac43dd1-b6cf-43a8-9691-9d1e9783ba65"));
  }
  @Test
  public void updateTagStatusTest(){
    //System.out.println(shopTagService.updateTagStatus("dac43dd1-b6cf-43a8-9691-9d1e9783ba65","2"));
  }

}
