package com.xiaodou.oms.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.oms.entity.order.ShopTag;
import com.xiaodou.oms.service.oms.ShopTagService;
import com.xiaodou.oms.vo.input.order.TagPropertyPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.order.ShopTagVO;
import com.xiaodou.oms.web.BaseController;
import com.xiaodou.oms.web.ServiceHandler;

@Controller
@RequestMapping("tag")
public class OmsTagController extends BaseController{
  
  @Resource
  ShopTagService shopTagService;
  
  @RequestMapping(value="/create" , headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String createTag(@RequestBody TagPropertyPojo pojo) throws Exception{
    return doMain(pojo, new ServiceHandler<TagPropertyPojo>(this) {
      @Override
      public ShopTagVO doService(TagPropertyPojo pojo) throws Exception {
        return shopTagService.createTag(pojo.getType(),pojo.getProductType());
      }
    });
  }

}
