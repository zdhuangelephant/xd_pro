package com.xiaodou.course.service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productCategoryService")
public class ProductCategoryService {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 根据产品查找栏目
   * 
   * @param code
   * @return
   */
  public ProductCategoryModel findCategoryByAppCode(String code) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("typeCode", code);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    Page<ProductCategoryModel> productCategoryModelPage =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond, output);
    List<ProductCategoryModel> result = productCategoryModelPage.getResult();
    return result.get(0);
  }

  public List<ProductCategoryModel> queryAllChildCategory(Integer id) {
    if (id == 0) {
      return this.queryAllCategory();
    }
    ProductCategoryModel productCategory = this.findCategoryById(id);
    if (productCategory == null) {
      return new ArrayList<>();
    }
    if (StringUtils.isNotBlank(productCategory.getAllChildId())) {
      String[] ids = productCategory.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryCategoryByIds(idList);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ProductCategoryModel> queryAllCategory() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("parentId", "1");
    output.put("name", "1");
    output.put("detail", "1");
    output.put("createTime", "1");
    output.put("childId", "1");
    output.put("allChildId", "1");
    output.put("allParentId", "1");
    output.put("level", "1");
    output.put("isLeaf", "1");
    output.put("module", "1");
    output.put("typeCode", "1");
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond, output);
    return categoryList.getResult();
  }

  /**
   * 根据主键查询
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
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("parentId", "1");
    output.put("name", "1");
    output.put("detail", "1");
    output.put("createTime", "1");
    output.put("childId", "1");
    output.put("allChildId", "1");
    output.put("allParentId", "1");
    output.put("level", "1");
    output.put("isLeaf", "1");
    output.put("module", "1");
    output.put("typeCode", "1");
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond, output);
    return categoryList.getResult();
  }

}
