package com.xiaodou.courseUtil.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.courseUtil.model.ProductUtilModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productUtilDao")
public class ProductUtilDao extends BaseProcessDao<ProductUtilModel> {
  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: cascadeQueryMyProduct
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductUtilModel> cascadeQueryMyProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryMyProduct", mapParam, null);
  }
}
