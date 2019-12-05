package com.xiaodou.course.web.controller.product;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.course.service.product.ProductItemService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.product.ChapterCardListRequest;
import com.xiaodou.course.web.request.product.ChapterResourceRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;

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
    String version = StringUtils.EMPTY;
    return productItemService.chapterList(detailRequest, version).toString0();
  }

  @RequestMapping("/chapter_list_v_1.4.0")
  @ResponseBody
  public String chapterList_v_140(ProductDetailRequest detailRequest) throws Exception {
    String version = "140";
    return productItemService.chapterList(detailRequest, version).toString0();
  }

  /**
   * 章节修炼接口 获取本课程视频和评论列表 。 · url /product/chapter_resource
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("chapter_resource")
  public String chapterResource(ChapterResourceRequest pojo) {
    return productItemService.chapterResource(pojo).toString0();
  }
  
  /**
   * @description 获取音频
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年5月4日
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("chapter_audio")
  public String chapterAudio(ChapterResourceRequest pojo) {
    return productItemService.chapterAudio(pojo).toString0();
  }

  /**
   * 2、章节卡片接口 获取指定章的节卡片列表。 · url /product/chapter_card_list
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("chapter_card_list")
  public String chapterCardList(ChapterCardListRequest pojo) {
    String version = StringUtils.EMPTY;
    return productItemService.chapterCardList(pojo, version).toString0();
  }


  @ResponseBody
  @RequestMapping("chapter_card_list_v_1.4.0")
  public String chapter_card_list_v_140(ChapterCardListRequest pojo) {
    String version = "140";
    return productItemService.chapterCardList(pojo, version).toString0();
  }
}
