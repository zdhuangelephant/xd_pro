package com.xiaodou.course.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.product.MajorCourseModel;
import com.xiaodou.course.model.product.MajorCourseModel.MajorCourseInfo;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.dao.pagination.Page;

@Service("abstractService")
public class AbstractService {

  @Resource
  protected ProductServiceFacade productServiceFacade;

  @Resource
  ProductCategoryService productCategoryService;

  /**
   * 校验课程有效性
   * 
   * @param pojo
   * @param courseId
   * @return
   * @throws ParseException
   */
  public CheckResult checkIsValidCourseId(BaseRequest pojo, String courseId) throws ParseException {
    String key =
        String.format("%s:%S:%s", Constant.CHECK_IS_VALID_COURSE_ID, pojo.getUid(), courseId);
    String value = JedisUtil.getStringFromJedis(key);
    CheckResult result = null;
    if (StringUtils.isJsonNotBlank(value)) {
      result = FastJsonUtil.fromJson(value, CheckResult.class);
      if (StringUtils.isNotEmpty(result.getMajorId())) {
        pojo.setMajorId(result.getMajorId());
        return result;
      }
    }
    result = _checkIsValidCourseId(pojo, courseId);
    JedisUtil.addStringToJedis(key, FastJsonUtil.toJson(result), 3600);
    return result;
  }

  private CheckResult _checkIsValidCourseId(BaseRequest pojo, String courseId)
      throws ParseException {
    CheckResult result = new CheckResult();
    // 1、首先判断新手课程
    List<ProductModel> noviceList = getNewbieProductList(pojo.getModule(), pojo.getUid());
    if (null != noviceList) {
      for (ProductModel course : noviceList) {
        if (null != course && (courseId.equals(course.getId().toString())))
          if (null != course.getBeginApplyTime() && null != course.getEndApplyTime()
              && DateUtil.ifIsValid(course.getBeginApplyTime(), course.getEndApplyTime())) {
            pojo.setMajorId(CourseConstant.COMMON_MAJOR_ID);
            result.setMajorId(CourseConstant.COMMON_MAJOR_ID);
            return result;
          }
      }
    }
    // 2、判断其它课程
    ProductCategoryModel productCategory =
        productCategoryService.findCategoryByAppCode(pojo.getTypeCode(), pojo.getModule());
    if (null == productCategory || null == productCategory.getId()) {
      result.setResType(ProductResType.NotFindMajor);
      return result;
    }
    List<ProductModel> myProductModelList =
        this.cascadeQueryMyProductByCatId(productCategory.getId(), pojo.getUid(), pojo.getModule());
    if (null != myProductModelList) {
      for (ProductModel course : myProductModelList) {
        if (null != course && courseId.equals(course.getId().toString()))
        // 判断课程的有效性
          if (null != course.getBeginApplyTime() && null != course.getEndApplyTime()
              && DateUtil.ifIsValid(course.getBeginApplyTime(), course.getEndApplyTime())) {
            pojo.setMajorId(productCategory.getId().toString());
            result.setMajorId(productCategory.getId().toString());
            return result;
          }
      }
    }
    result.setResType(ProductResType.NotFindCourse);
    return result;
  }


  protected Long getAheadOfTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, 20);
    return calendar.getTime().getTime();
  }

  /**
   * 
   * @param catId(专业id)
   * @param orderStatus 购买状态
   * @return
   * @throws ParseException
   */
  public List<ProductModel> cascadeQueryMyProductByCatId(Integer catId, String userId, String module) {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    cond.put("module", module);
    cond.put("productCategoryId", catId);// 专业id
    cond.put("showStatus", 1);// 课程状态
    cond.put("status", CourseConstant.BUY_COURSE_FINISH);// 购买状态 1:正常购买状态，2：试用状态。。。
    cond.put("userId", userId);// 用户id
    cond.put("moduleCourse", 0);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    Page<ProductModel> myProductModelPage =
        productServiceFacade.queryCascadeMyProductByCond(cond, output);
    if (null != myProductModelPage && null != myProductModelPage.getResult()) {
      productModelList.addAll(myProductModelPage.getResult());
    }
    return productModelList;
  }

  /**
   * @description 所有无效课程
   * @author 李德洪
   * @Date 2018年1月29日
   * @param limitCount
   * @param upperId
   * @param userId
   * @param module
   * @return
   */
  public List<ProductModel> cascadeQueryMyProductByUpperId(Integer limitCount, String upperId,
      String userId, String module) {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    cond.put("showStatus", 1);// 课程状态
    cond.put("status", CourseConstant.BUY_COURSE_FINISH);// 购买状态 1:正常购买状态，2：试用状态。。。
    cond.put("userId", userId);// 用户id
    cond.put("moduleCourse", 0);
    cond.put("module", module);
    if (StringUtils.isNotBlank(upperId)) {
      cond.put("upperId", upperId);
    }
    cond.put("notValid", true);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    Page<ProductModel> myProductModelPage =
        productServiceFacade.cascadeQueryMyProduct0(limitCount, cond, output);
    if (null != myProductModelPage && null != myProductModelPage.getResult()) {
      productModelList.addAll(myProductModelPage.getResult());
    }
    return productModelList;
  }

  /**
   * 查询新手课程
   * 
   * @param orderStatus
   * @param userId
   * @return
   */
  public List<ProductModel> getNewbieProductList(String module, String uid) {
    // 新手课程
    // 1、新手课程 moduleCourse为相应的模块号，2、普通课程为0
    List<ProductModel> myProductModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    cond.put("showStatus", 1);// 课程状态
    cond.put("moduleCourse", module);
    if (StringUtils.isNotBlank(uid)) cond.put("uid", uid);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("beginApplyTime", 1);
    output.put("endApplyTime", 1);
    output.put("name", 1);
    output.put("courseCode", 1);
    output.put("score", 1);
    output.put("imageUrl", 1);
    Page<ProductModel> myProductModelPage =
        productServiceFacade.cascadeQueryMyNewbieProduct(cond, output);
    if (null != myProductModelPage && null != myProductModelPage.getResult()) {
      myProductModelList = myProductModelPage.getResult();
    }
    return myProductModelList;
  }


  public Date gainRecentExamDate(String courseCode) throws ParseException {
    Date recentExamDate = null;
    if (StringUtils.isBlank(courseCode)) return null;
    MajorCourseModel majorCourseModel =
        productServiceFacade.queryMajorCourseByIdAndRegion(courseCode, null);
    majorCourseModel.getMajorCourseInfo();
    if (StringUtils.isNotBlank(majorCourseModel.getMajorCourseInfo())) {
      MajorCourseInfo majorCourseInfo =
          FastJsonUtil.fromJson(majorCourseModel.getMajorCourseInfo(), MajorCourseInfo.class);
      String examDate = majorCourseInfo.getExamDate();
      // String[] arry = examDate.split(";");
      if (StringUtils.isNotBlank(examDate)) for (String eDate : examDate.split(";")) {
        if (StringUtils.isBlank(eDate) || eDate.length() < 12) continue;
        Date formatDate = DateUtil.getSYMDFormat().parse(eDate.substring(0, 11));
        if (formatDate.after(new Date())) {
          if (recentExamDate != null) {
            if (formatDate.before(recentExamDate)) recentExamDate = formatDate;
          } else
            recentExamDate = formatDate;
        }
      }
    }
    return recentExamDate;
  }

  /**
   * 判断 课程为近期或者非近期
   * 
   * @return
   * @throws ParseException
   */
  /*
   * public String getExamDateStatus(String courseCode) throws ParseException { Date recentExamDate
   * = gainRecentExamDate(courseCode); if (null != recentExamDate) return
   * CourseConstant.RECENT_EXAM_DATE_STATUS; else return
   * CourseConstant.OTHER_RECENT_EXAM_DATE_STATUS; }
   */

  public static class CheckResult {
    private ProductResType resType;
    private String majorId;

    public String getMajorId() {
      return majorId;
    }

    public void setMajorId(String majorId) {
      this.majorId = majorId;
    }

    public Boolean isRetOk() {
      return resType == null;
    }

    public ProductResType getResType() {
      return resType;
    }

    public void setResType(ProductResType resType) {
      this.resType = resType;
    }
  }

  /**
   * 根据module格式化productLine
   * 
   * @param module
   * @return
   */
  protected String formatProductLine() {
    String module = CourseConstant.PAY_PRODUCT_LINE;
    return module.length() > 2 ? module.substring(module.length() - 2) : module.length() == 1
        ? String.format("0%s", module)
        : module;
  }

}
