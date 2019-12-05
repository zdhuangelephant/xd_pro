package com.xiaodou.resources.service.product;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.model.admin.Admin;
import com.xiaodou.resources.model.product.ProductCategoryModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;

@Service("abstractService")
public class AbstractService {

  @Resource
  protected ProductServiceFacade productServiceFacade;

  @Resource
  ProductCategoryService productCategoryService;


  /**
   * 查找产品信息
   * 
   * @param id
   * @return
   */
  public ProductModel findProductById(Long productId) {
    ProductModel productModel = new ProductModel();
    productModel.setId(productId);
    return productServiceFacade.queryProductById(productModel);
  }

  /**
   * 检查课程ID合法性
   * 
   * @param courseId
   * @return
   */
  public CheckResult checkCourseId(String courseId) {
    CheckResult result = new CheckResult();
    if (null != this.findProductById(Long.valueOf(courseId))) {
      return result;
    }
    result.setResType(ProductResType.FindCourseIdFailed);
    return result;
  }

  /**
   * 检查课程ID合法性 判断用户是否购买过该产品
   * 
   * @param pojo 参数pojo
   * @param courseId 课程ID
   * @return
   * @throws ParseException
   */
  public CheckResult checkCourseId(BaseRequest pojo, String courseId) throws ParseException {
    CheckResult result = new CheckResult();
    List<ProductModel> myProductModelList =
        this.cascadeQueryMyProductByCond(CourseConstant.ORDER_STATUS_NORMAL, pojo.getUid(),
            pojo.getModule());
    if (null != myProductModelList) {
      for (ProductModel course : myProductModelList) {
        if (null != course && Long.valueOf(courseId).equals(course.getId())) return result;
      }
    }
    result.setResType(ProductResType.UnValidCourseId);
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
   * @param orderStatus 购买状态
   * @param userId 用户id
   * @param module 模块号
   * @return
   * @throws ParseException
   */
  public List<ProductModel> cascadeQueryMyProductByCond(String orderStatus, String userId,
      String module) throws ParseException {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    // cond.put("module", module);
    cond.put("showStatus", 1);// 课程状态
    cond.put("isExp", "1");// 购买有效
    cond.put("orderStatus", orderStatus);// 购买状态 1:正常购买状态，2：试用状态。。。
    cond.put("userId", userId);// 用户id
    // 在有效期内的课程
    // 2分钟兼容（当前时间地提前2分钟）
    cond.put("nowTime", new Timestamp(getAheadOfTime()));
    Page<ProductModel> myProductModelPage = this.getProductModelPage(cond);
    if (null != myProductModelPage && null != myProductModelPage.getResult()) {
      // 获取已经购买的正常态课程
      productModelList.addAll(myProductModelPage.getResult());
    }
    return productModelList;
  }

  /**
   * 是否购买了该课程
   * 
   * @param courseId
   * @param userId
   * @return
   * @throws ParseException
   */
  public boolean ifOrderProduct(String courseId, String userId) throws ParseException {
    boolean flag = false;
    Map<String, Object> cond = new HashMap<>();
    cond.put("showStatus", 1);// 课程状态
    cond.put("isExp", "1");// 购买有效
    cond.put("orderStatus", 1);// 购买状态 1:正常购买状态，2：试用状态。。。
    cond.put("userId", userId);// 用户id
    cond.put("id", courseId);// 课程id
    // 在有效期内的课程
    // 2分钟兼容（当前时间地提前2分钟）
    cond.put("nowTime", new Timestamp(getAheadOfTime()));
    // cond.put("moduleCourse", "1");
    // Map<String, Object> output = new HashMap<>();
    // CommUtil.getGeneralField(output, ProductModel.class);
    // 查询我的课程
    Page<ProductModel> myProductModelPage = this.getProductModelPage(cond);
    if (null != myProductModelPage && null != myProductModelPage.getResult()
        && myProductModelPage.getResult().size() > 0) {
      flag = true;
    }
    return flag;
  }


  public static class CheckResult {
    private ProductResType resType;

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
   * 根据管理员id查询管理员
   * 
   * @param adminId
   * @return
   */
  public Admin queryAdminById(Integer adminId) {
    return productServiceFacade.queryAdminById(adminId);
  }

  /**
   * 根据条件查询课程
   * 
   * @param cond
   * @return
   */
  protected Page<ProductModel> getProductModelPage(Map<String, Object> cond) {
    IJoinQueryParam queryParam = new JoinQueryParam();
    queryParam.addInputs(cond);
    if (null != cond.get("userId")) queryParam.addJoin("userId", cond.get("userId"));
    queryParam.addOutputs(CommUtil.getAllField(ProductModel.class));
    Page<ProductModel> productModelPage =
        productServiceFacade.queryCascadeProductByCond(queryParam);
    return productModelPage;
  }

  /**
   * 根据条件查询专栏
   * 
   * @param cond
   * @return
   */
  public List<ProductCategoryModel> queryCategoryList(Map<String, Object> cond) {
    IJoinQueryParam queryParam = new JoinQueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(ProductCategoryModel.class));
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryByCond(queryParam);
    return (null != categoryList && null != categoryList.getResult())
        ? categoryList.getResult()
        : null;
  }
}
