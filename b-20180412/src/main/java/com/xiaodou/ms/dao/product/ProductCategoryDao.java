package com.xiaodou.ms.dao.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productCategoryDao")
public class ProductCategoryDao extends BaseProcessDao<ProductCategoryModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<ProductCategoryModel> cascadeQueryProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", mapParam, null);
  }
}
