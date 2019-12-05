package com.xiaodou.course.web.controller.product;

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.service.product.ProductExamService;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.service.product.UserLearnAchieveService;
import com.xiaodou.course.service.product.UserLearnRecordDayService;
import com.xiaodou.course.service.product.UserLearnRecordService;
import com.xiaodou.course.service.product.UserProductOrderService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.forum.ProductForumListRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequestLT;
import com.xiaodou.course.web.request.product.MajorDetailByMajorIdRequest;
import com.xiaodou.course.web.request.product.MajorDetailRequest;
import com.xiaodou.course.web.request.product.PersonProductInfoRequest;
import com.xiaodou.course.web.request.product.ProcuctExamDetailRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;
import com.xiaodou.course.web.request.product.RecordDetailRequest;
import com.xiaodou.course.web.request.product.UserNotValidCourseRequest;
import com.xiaodou.course.web.request.product.UserProductOpenRequest;
import com.xiaodou.course.web.request.user.UserLearnRecordByDaysRequest;
import com.xiaodou.course.web.request.user.UserLearnRecordDataRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.forum.ForumListResponse;
import com.xiaodou.course.web.response.product.AddCourseListResponse;
import com.xiaodou.course.web.response.product.HomeResponse;
import com.xiaodou.course.web.response.product.LearnStaticsResponse;
import com.xiaodou.course.web.response.product.MajorDetailResponse;
import com.xiaodou.course.web.response.product.ProductDetailResponse;
import com.xiaodou.course.web.response.product.ProductExamDetailResponse;
import com.xiaodou.course.web.response.product.RecordDetailResponse;

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
   * 学习时间记录
   */
  @Resource
  UserLearnRecordService userLearnRecordService;
  /**
   * 用户购买课程
   */
  @Resource
  UserProductOrderService userProductOrderService;

  @Resource
  ProductExamService productExamService;

  @Resource
  UserLearnAchieveService userLearnAchieveService;

  @Resource
  UserLearnRecordDayService userLearnRecordDayService;

  /**
   * 自考app 首页
   * 
   * @param detailRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/home")
  @ResponseBody
  public String home(BaseRequest pojo) throws Exception {
    HomeResponse response = productService.home(pojo);
    return FastJsonUtil.toJson(response);
  }

  /**
   * 知识分享列表
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/forum_list")
  @ResponseBody
  public String forumList(ProductForumListRequest pojo) throws Exception {
    ForumListResponse response = productService.forumList(pojo);
    return FastJsonUtil.toJson(response);
  }

  /**
   * 打卡
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/sign")
  @ResponseBody
  public String sign(BaseRequest pojo) throws Exception {
    BaseResponse response = productService.sign(pojo);
    return FastJsonUtil.toJson(response);
  }

  /**
   * 课程页课程列表展示  添加课程页面中展示的课程为本专业中未开通过的课程
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/add_course_list")
  @ResponseBody
  public String addCourseList(BaseRequest pojo) throws Exception {
    AddCourseListResponse reponse = productService.addCourseList(pojo);
    return FastJsonUtil.toJson(reponse);
  }

  /**
   * 学习统计
   * 
   * @return
   */
  @RequestMapping("/statistic_info")
  @ResponseBody
  public String learnStatics(BaseRequest request) {
    LearnStaticsResponse response = userLearnRecordService.statics(request);
    return JSON.toJSONString(response);
  }

  /**
   * 课程详情
   * 
   * @param detailRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/course_detail")
  @ResponseBody
  public String courseDetail(ProductDetailRequest detailRequest) throws Exception {
    ProductDetailResponse reponse = productService.productDetail(detailRequest);
    return JSON.toJSONString(reponse);
  }

  /**
   * 专业详情详情
   * 
   * @param detailRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/major_detail")
  @ResponseBody
  public String majorDetail(MajorDetailRequest detailRequest) throws Exception {
    MajorDetailResponse reponse = productCategoryService.majorDetail(detailRequest);
    return JSON.toJSONString(reponse);
  }

  /**
   * @description 通过typeCode与module查询专业
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月23日
   * @param detailRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/major_detail_by_id")
  @ResponseBody
  public String majorDetailById(MajorDetailByMajorIdRequest detailRequest) throws Exception {
    String headVersion = StringUtils.EMPTY;
    MajorDetailResponse reponse =
        productCategoryService.majorDetailById(detailRequest, headVersion);
    return JSON.toJSONString(reponse);
  }

  @RequestMapping("/major_detail_by_id_v_1_4_9")
  @ResponseBody
  public String majorDetailByIdV149(MajorDetailByMajorIdRequest detailRequest) throws Exception {
    String headVersion = "149";
    MajorDetailResponse reponse =
        productCategoryService.majorDetailById(detailRequest, headVersion);
    return JSON.toJSONString(reponse);
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
   * 查看闯关记录详情
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("record_detail")
  public String record_detail(RecordDetailRequest request) {
    RecordDetailResponse response = productService.recordDetail(request);
    return response.toString0();
  }

  /**
   * 查看考试详情(距离考试天数)
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("exam_detail")
  public String getExamDetail(ProcuctExamDetailRequest pojo) {
    ProductExamDetailResponse response = productExamService.getExamDetail(pojo);
    return response.toString0();
  }

  /**
   * 学习记录/计时
   * 
   * @author 李德洪
   * @param recordRequest
   * @return
   * @throws ParseException
   */
  @RequestMapping("/study_record_time_lt_v1_3_8")
  @ResponseBody
  public String studyRecordTimeLT(LearnRecordRequestLT recordRequest) throws ParseException {
    BaseResponse response = userLearnRecordService.addLearnRecord(recordRequest);
    return response.toString0();
  }

  /**
   * 学习记录/计时
   * 
   * @author 李德洪
   * @param recordRequest
   * @return
   * @throws ParseException
   */
  @RequestMapping("/study_record_time")
  @ResponseBody
  public String studyRecordTime(LearnRecordRequest recordRequest) throws ParseException {
    BaseResponse response = userLearnRecordService.addLearnRecord(recordRequest);
    return response.toString0();
  }

  /**
   * 由学习统计页面 进入的每日学习时长数据 url /product/learn_time_data_list
   * 
   * @param pojo
   */
  @RequestMapping("learn_record_time_detail_list")
  @ResponseBody
  public String learnRecordTimeDetailList(UserLearnRecordDataRequest pojo) {
    return userLearnRecordDayService.learnRecordTimeDetailList(pojo).toString0();
  }

  /**
   * 学习统计页面的7天学习时长数据
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("learn_record_time_statistic_list")
  @ResponseBody
  public String learnRecordTimeStatisticList(UserLearnRecordByDaysRequest pojo) {
    return userLearnRecordDayService.learnRecordTimeStatisticList(pojo).toString0();
  }

  /**
   * 获取每日学习时长数据
   * 
   * @param pojo
   */
  @RequestMapping("learn_record_time_list")
  @ResponseBody
  public String learnRecordTimeList(BaseRequest pojo) {
    return userLearnRecordDayService.learnRecordTimeList(pojo).toString0();
  }

  /**
   * 获取用户星星排名，限50名
   * 
   * @param pojo
   */
  @RequestMapping("star_rank_list")
  @ResponseBody
  public String starRankList(BaseRequest pojo) {
    return productService.starRankList(pojo).toString0();
  }

  // 2、获取连续学习天数
  @RequestMapping("cost_learn_time")
  @ResponseBody
  public String costLearnTime(BaseRequest pojo) {
    return userLearnRecordDayService.costLearnTime(pojo.getUid()).toString();
  }

  @RequestMapping("/list_not_valid_course")
  @ResponseBody
  public String listNotValidCourse(UserNotValidCourseRequest pojo) {
    return productService.listNotValidCourse(pojo).toString0();
  }

  /**
   * 获取用户产品相关信息
   * 
   * @param pojo 基础POJOO
   * @return 用户产品相关信息
   */
  @RequestMapping("/person_product_info")
  @ResponseBody
  public String personProductInfo(PersonProductInfoRequest pojo) {
    return productService.personProductInfo(pojo).toString0();
  }
}
