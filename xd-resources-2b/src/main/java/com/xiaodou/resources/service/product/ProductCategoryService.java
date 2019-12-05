package com.xiaodou.resources.service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.resources.model.product.ProductCategoryModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.resources.vo.product.CourseDetail;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productCategoryService")
public class ProductCategoryService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 产品service
   */
  @Resource
  ProductService productService;

  /**
   * 根据产品typeCode查找栏目
   * 
   * @param code
   * @return
   */
  public ProductCategoryModel findCategoryByAppCode(String typeCode) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("typeCode", typeCode);
    List<ProductCategoryModel> result = super.queryCategoryList(cond);
    if (null != result && result.size() > 0) return result.get(0);
    return null;
  }

  public List<ProductCategoryModel> queryAllChildCategory(Integer id) {
    if (id == 0) {
      return this.queryAllCategory();
    }
    ProductCategoryModel productCategory = this.findCategoryById(id);
    if (productCategory == null) {
      return new ArrayList<>();
    }
    return Lists.newArrayList(productCategory);
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ProductCategoryModel> queryAllCategory() {
    return super.queryCategoryList(null);
  }

  /**
   * 根据栏目id查找栏目
   * 
   * @param catId
   * @return
   */
  public ProductCategoryModel findCategoryById(Integer catId) {
    ProductCategoryModel entity = new ProductCategoryModel();
    entity.setId(catId);
    return productServiceFacade.queryProductCategoryById(entity);
  }

  /**
   * 根据父Id查询地区
   * 
   * @param ids
   * @return
   */
  public List<ProductCategoryModel> queryCategoryByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    return super.queryCategoryList(cond);
  }


  /**
   * 通过栏目id与app模块号查询课程详情
   * 
   * @param majorId
   * @param module
   * @return
   */
  public List<CourseDetail> getCourseDetailBymId(String catId, String module) {
    // 栏目（专业）
    ProductCategoryModel majorModel = this.findCategoryById(Integer.valueOf(catId));
    if (null == majorModel) return null;
    // 课程列表
    List<CourseDetail> courseList = Lists.newArrayList();
    // 产品（课程）列表
    List<ProductModel> productModelList = productService.cascadeQueryProductByMod(catId);
    if (null != productModelList && productModelList.size() > 0) {
      for (ProductModel productModel : productModelList) {
        if (null != productModel) courseList.add(productService.getCourseDetail(productModel));
      }
    }
    return courseList;
  }

}
