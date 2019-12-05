package com.xiaodou.course.service.facade;

import java.util.Map;

import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserLearnProcessModel;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.model.user.UserLearnStaticsModel;
import com.xiaodou.course.model.user.UserLearnTaskModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.course.service.facade.ProductServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月20日
 * @description 产品数据源上层facade接口
 * @version 1.0
 */
public interface ProductServiceFacade {

  /** module slide */
  Page<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** product */
  ProductModel queryProductById(ProductModel productModel);
  
  Page<ProductModel> queryCascadeProductByCond(Map<String, Object> cond, Map<String, Object> output);

  /** product category */
  Page<ProductCategoryModel> queryProductCategoryListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  ProductCategoryModel queryProductCategoryById(ProductCategoryModel entity);

  /** product item */
  ProductItemModel queryProductItemById(ProductItemModel cond);

  Page<ProductItemModel> queryProductItemListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** user learn process */
  UserLearnProcessModel insertUserLearnProcess(UserLearnProcessModel learnProcess);

  /** user learn record */
  UserLearnRecordModel insertUserLearnRecord(UserLearnRecordModel learnRecord);

  Page<UserLearnRecordModel> queryUserLearnRecordListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** user learn statics */
  UserLearnStaticsModel insertUserLearnStatics(UserLearnStaticsModel learnStatics);

  void updateUserLearnStatics(Map<String, Object> cond, UserLearnStaticsModel learnStatics);

  UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Integer productId, Integer userId);

  /** user learn task */
  UserLearnTaskModel insertUserLearnTask(UserLearnTaskModel learnTask);

  Page<UserLearnTaskModel> queryUserLearnTaskListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

}
