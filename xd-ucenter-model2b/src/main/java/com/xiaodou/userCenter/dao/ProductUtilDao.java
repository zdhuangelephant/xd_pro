package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.userCenter.model.ProductUtilModel;

@Repository("productUtilDao")
public class ProductUtilDao extends BaseModelProcessDao<ProductUtilModel> {
  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: cascadeQueryProduct
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductUtilModel> cascadeQueryProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", mapParam, null);
  }
}
