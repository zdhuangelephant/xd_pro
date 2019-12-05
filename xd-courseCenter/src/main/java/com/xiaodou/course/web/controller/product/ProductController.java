package com.xiaodou.course.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.service.product.UserLearnProcessService;
import com.xiaodou.course.service.product.UserLearnRecordService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.product.LearnProcessRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest;
import com.xiaodou.course.web.request.product.ProductAuthRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;
import com.xiaodou.course.web.request.product.ProductDownloadRequest;
import com.xiaodou.course.web.request.product.ProductListRequst;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.product.LearnStaticsResponse;
import com.xiaodou.course.web.response.product.ProductDetailReponse;
import com.xiaodou.course.web.response.product.ProductDownloadResponse;
import com.xiaodou.course.web.response.product.ProductItemAuthResponse;
import com.xiaodou.course.web.response.product.ProductListResponse;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

  /**
   * 产品service
   */
  @Resource
  ProductService productService;

  /**
   * 学习进度记录
   */
  @Resource
  UserLearnProcessService userLearnProcessService;

  /**
   * 学习时间记录
   */
  @Resource
  UserLearnRecordService userLearnRecordService;

  /**
   * 课程列表接口
   * 
   * @return
   */
  @RequestMapping("/course_list")
  @ResponseBody
  public String courseList(ProductListRequst listRequst) {
    ProductListResponse response = productService.productList(listRequst);
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
    ProductDetailReponse reponse = productService.productDetail(detailRequest);
    return JSON.toJSONString(reponse);
  }

  /**
   * 条目授权
   * 
   * @param authRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/user_auth")
  @ResponseBody
  public String userAuth(ProductAuthRequest authRequest) throws Exception {
    ProductItemAuthResponse response = productService.itemAuth(authRequest);
    return JSON.toJSONString(response);
  }

  /**
   * 获取课程下载列表
   * @param downloadRequest
   * @return
   */
  @RequestMapping("/download_url")
  @ResponseBody
  public String downloadUrlList(ProductDownloadRequest downloadRequest) {
    ProductDownloadResponse response = productService.downloadUrlList(downloadRequest);
    return JSON.toJSONString(response);
  }

  /**
   * 学习进度
   * 
   * @param processRequest
   * @return
   */
  @RequestMapping("/study_process")
  @ResponseBody
  public String studyProcess(LearnProcessRequest processRequest) {
    BaseResponse resut = userLearnProcessService.recordLearnProcess(processRequest);
    return JSON.toJSONString(resut);
  }

  /**
   * 学习记录
   * 
   * @param recordRequest
   * @return
   */
  @RequestMapping("/study_record")
  @ResponseBody
  public String studyRecord(LearnRecordRequest recordRequest) {
    BaseResponse resut = userLearnRecordService.addLearnRecord(recordRequest);
    return JSON.toJSONString(resut);
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
}
