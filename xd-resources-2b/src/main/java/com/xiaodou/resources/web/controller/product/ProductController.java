package com.xiaodou.resources.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.ForumPraiseRequest;
import com.xiaodou.resources.request.forum.TalkReplyPostRequest;
import com.xiaodou.resources.request.product.CourseDetailRequest;
import com.xiaodou.resources.request.product.CourseListRequest;
import com.xiaodou.resources.request.product.NewestCourseRequest;
import com.xiaodou.resources.request.product.TalkCommentListRequest;
import com.xiaodou.resources.request.product.TalkDetailRequest;
import com.xiaodou.resources.request.product.UserProductOpenRequest;
import com.xiaodou.resources.request.product.UserProductOrderRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.product.AddCourseListResponse;
import com.xiaodou.resources.response.product.MyCourseListResponse;
import com.xiaodou.resources.service.forum.ForumReplyService;
import com.xiaodou.resources.service.product.ProductCategoryService;
import com.xiaodou.resources.service.product.ProductService;
import com.xiaodou.resources.service.product.UserProductOrderService;
import com.xiaodou.summer.web.BaseController;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

  /**
   * 栏目（专业）service
   */
  @Resource
  ProductCategoryService productCategoryService;

  /**
   * 产品service
   */
  @Resource
  ProductService productService;

  /**
   * 用户购买课程
   */
  @Resource
  UserProductOrderService userProductOrderService;

  /** 回复话题service */
  @Resource
  ForumReplyService forumReplyService;


  /**
   * 我的课程列表页
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/my_course_list")
  @ResponseBody
  public String mycourseList(BaseRequest pojo) {
    MyCourseListResponse response = productService.mycourseList(pojo);
    return response.toString0();
  }

  /**
   * 分类课程列表
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/category_course_list")
  @ResponseBody
  public String categoryCourseList(BaseRequest pojo) throws Exception {
    AddCourseListResponse reponse = productService.categoryCourseList(pojo);
    return FastJsonUtil.toJson(reponse);
  }

  /**
   * 课程详情
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/course_detail")
  @ResponseBody
  public String courseDetail(CourseDetailRequest pojo) {
    return productService.courseDetail(pojo).toString0();
  }

  /**
   * 获取推荐列表
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/recommend_course_list")
  @ResponseBody
  public String recommendCourseList(BaseRequest pojo) {
    return productService.recommendCourseList(pojo).toString0();
  }

  /**
   * 查找课程
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/search")
  @ResponseBody
  public String serach(CourseListRequest pojo) {
    return productService.search(pojo).toString0();
  }

  /**
   * 课程主页-目录
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/directory")
  @ResponseBody
  public String directory(CourseDetailRequest pojo) {
    return productService.directory(pojo).toString0();
  }

  /**
   * 课程主页-考核
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/check")
  @ResponseBody
  public String check(CourseDetailRequest pojo) {
    return productService.check(pojo).toString0();
  }

  /**
   * 课程主页-讨论
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/talk")
  @ResponseBody
  public String talk(CourseDetailRequest pojo) {
    return productService.talk(pojo).toString0();
  }

  /**
   * 课程主页-最新
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/newest_course")
  @ResponseBody
  public String newestCourse(NewestCourseRequest pojo) {
    return productService.newestCourse(pojo).toString0();
  }



  /**
   * 购买课程接口。
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("/purchase_product")
  public String purchaseProduct(UserProductOrderRequest request) throws InstantiationException,
      IllegalAccessException {
    BaseResponse response = userProductOrderService.purchaseProduct(request);
    return response.toString0();
  }

  /**
   * 添加课程
   * 
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  @ResponseBody
  @RequestMapping("/open_product")
  public String openProduct(UserProductOpenRequest request) throws InstantiationException,
      IllegalAccessException {
    BaseResponse response = userProductOrderService.openProduct(request);
    return response.toString0();
  }

  /**
   * 讨论详情
   * 
   * @param body
   * @return
   */
  @RequestMapping("/talk_detail")
  public @ResponseBody
  String talkDetail(TalkDetailRequest pojo) {
    return productService.talkDetail(pojo).toString0();
  }


  /**
   * 讨论模块 评论列表
   * 
   * @param body
   * @return
   */
  @RequestMapping("/talk_comment_list")
  public @ResponseBody
  String talkCommentList(TalkCommentListRequest pojo) {
    return productService.talkCommentList(pojo).toString0();
  }

  /**
   * 26. 回复单元讨论
   * 
   * @param body
   * @return
   */
  @RequestMapping("/reply_talk")
  public @ResponseBody
  String replyTalk(TalkReplyPostRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumReplyService.replyTalk(pojo));
  }

  /**
   * 点赞
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/praise")
  public @ResponseBody
  String praise(ForumPraiseRequest pojo) throws Exception {
    return productService.praise(pojo).toString0();
  }

  /**
   * 取消点赞接口
   * 
   * @param request
   * @param forumPraiseRequest
   * @param errors
   * @return
   * @throws Exception
   */
  @RequestMapping("/cancel_praise")
  public @ResponseBody
  String cancelPraise(ForumPraiseRequest pojo) throws Exception {
    
    return productService.cancelPraise(pojo).toString0();
  }

}
