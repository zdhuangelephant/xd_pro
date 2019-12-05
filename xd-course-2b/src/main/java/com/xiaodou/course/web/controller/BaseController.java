package com.xiaodou.course.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.xiaodou.course.service.facade.ProductServiceFacade;

/**
 * @author zhaoxu.yang
 * @ClassName: BaseController
 * @Description: 基类controller，封装公共方法
 * @date 2015年4月11日 下午8:48:56
 */
public class BaseController {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 参数异常提示信息
   * 
   * @param errors 错误
   * @return 错误提示
   */
  public String getErrMsg(Errors errors) {
    List<ObjectError> errs = errors.getAllErrors();
    StringBuffer errInfo = new StringBuffer();
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage());
    }
    return errInfo.toString();
  }

  /**
   * 检查课程ID合法性 判断用户是否购买过该产品
   * 
   * @param pojo 参数pojo
   * @param courseId 课程ID
   * @return
   */
//  protected Boolean checkCourseId(BaseRequest pojo, String courseId) {
//    // 判断用户是否购买过该产品
//    Map<String, Object> params = Maps.newHashMap();
//    params.put("module", pojo.getModule());
//    params.put("typeCode", pojo.getTypeCode());
//    params.put("userId", pojo.getUid());
//    if (StringUtils.isNotBlank(courseId)) params.put("productId", courseId);
//    Page<ProductModel> course =
//        productServiceFacade.queryCascadeMyProductByCond(params,
//            CommUtil.getBasicField(ProductModel.class));
//    if (null == course || course.getResult() == null || course.getResult().size() == 0)
//      return false;
//    return true;
//  }

}
