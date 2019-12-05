package com.xiaodou.resources.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.resources.request.product.ProductDetailRequest;
import com.xiaodou.resources.response.product.UserChapterRecordResponse;
import com.xiaodou.resources.service.product.ProductItemService;
import com.xiaodou.summer.web.BaseController;

@Controller("chapterController")
@RequestMapping("/product")
public class ChapterController extends BaseController {
  /**
   * 章节
   */
  @Resource(name = "productItemService")
  ProductItemService productItemService;

  /**
   * 章节列表
   * 
   * @param detailRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/chapter_list")
  @ResponseBody
  public String chapterList(ProductDetailRequest detailRequest) throws Exception {
    UserChapterRecordResponse reponse = productItemService.chapterList(detailRequest);
    return JSON.toJSONString(reponse);
  }

}
