package com.xiaodou.course.service.facade;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.course.dao.product.ModuleSlideDao;
import com.xiaodou.course.dao.product.ProductCategoryDao;
import com.xiaodou.course.dao.product.ProductDao;
import com.xiaodou.course.dao.product.ProductItemDao;
import com.xiaodou.course.dao.user.UserLearnProcessDao;
import com.xiaodou.course.dao.user.UserLearnRecordDao;
import com.xiaodou.course.dao.user.UserLearnStaticsDao;
import com.xiaodou.course.dao.user.UserLearnTaskDao;
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
 * @name @see com.xiaodou.course.service.facade.ProductServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月20日
 * @description 产品数据源上层facade实现类
 * @version 1.0
 */
@Service("productServiceFacade")
public class ProductServiceFacadeImpl implements ProductServiceFacade {

  @Resource
  ProductDao productDao;
  
  @Resource
  ProductItemDao productItemDao;
  
  @Resource
  ProductCategoryDao productCategoryDao;
  
  @Resource
  ModuleSlideDao moduleSlideDao;
  
  @Resource
  UserLearnProcessDao userLearnProcessDao;
  
  @Resource
  UserLearnRecordDao userLearnRecordDao;
  
  @Resource
  UserLearnStaticsDao userLearnStaticsDao;
  
  @Resource
  UserLearnTaskDao userLearnTaskDao;

  @Override
  public Page<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return moduleSlideDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public Page<ProductCategoryModel> queryProductCategoryListByCondWithOutPage(
      Map<String, Object> cond, Map<String, Object> output) {
    return productCategoryDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public ProductCategoryModel queryProductCategoryById(ProductCategoryModel entity) {
    return productCategoryDao.findEntityById(entity);
  }

  @Override
  public ProductItemModel queryProductItemById(ProductItemModel entity) {
    return productItemDao.findEntityById(entity);
  }

  @Override
  public Page<ProductItemModel> queryProductItemListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return productItemDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public UserLearnProcessModel insertUserLearnProcess(UserLearnProcessModel learnProcess) {
    return userLearnProcessDao.addEntity(learnProcess);
  }

  @Override
  public ProductModel queryProductById(ProductModel productModel) {
    return productDao.findEntityById(productModel);
  }

  @Override
  public Page<ProductModel> queryCascadeProductByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    return productDao.cascadeQueryProduct(cond, output);
  }

  @Override
  public UserLearnRecordModel insertUserLearnRecord(UserLearnRecordModel learnRecord) {
    return userLearnRecordDao.addEntity(learnRecord);
  }

  @Override
  public Page<UserLearnRecordModel> queryUserLearnRecordListByCondWithOutPage(
      Map<String, Object> cond, Map<String, Object> output) {
    return userLearnRecordDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public UserLearnStaticsModel insertUserLearnStatics(UserLearnStaticsModel learnStatics) {
    return userLearnStaticsDao.addEntity(learnStatics);
  }

  @Override
  public void updateUserLearnStatics(Map<String, Object> cond, UserLearnStaticsModel learnStatics) {
    userLearnStaticsDao.updateEntity(cond, learnStatics);
  }

  @Override
  public UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Integer productId,
      Integer userId) {
    return userLearnStaticsDao.findEntityByUserAndProduct(productId, userId);
  }

  @Override
  public UserLearnTaskModel insertUserLearnTask(UserLearnTaskModel learnTask) {
    return userLearnTaskDao.addEntity(learnTask);
  }

  @Override
  public Page<UserLearnTaskModel> queryUserLearnTaskListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return userLearnTaskDao.queryListByCondWithOutPage(cond, output);
  }

}
