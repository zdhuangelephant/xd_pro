package com.xiaodou.ms.dao.product;

import java.util.Map;
import org.springframework.stereotype.Repository;
import com.google.common.collect.Maps;
import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productDao")
public class ProductDao extends BaseProcessDao<ProductModel> {

  /**
   * 级联查询产品及专业关系
   * 
   * @param param 参数
   * @return 产品列表
   */
/*  public Page<ProductModel> cascadeQueryProduct(IJoinQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin()) cond.put("join", param.getJoin());
    if (null != param && null != param.getInput()) cond.put("input", param.getInput());
    if (null != param && null != param.getOutput()) cond.put("output", param.getOutput());
    if (null != param && null != param.getSort()) cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit()) cond.put("limit", param.getLimit());
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", cond, null);
  }*/
  public Page<ProductModel> cascadeQueryProduct(IJoinQueryParam param,Page<ProductModel> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin()) cond.put("join", param.getJoin());
    if (null != param && null != param.getInput()) cond.put("input", param.getInput());
    if (null != param && null != param.getOutput()) cond.put("output", param.getOutput());
    if (null != param && null != param.getSort()) cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit()) cond.put("limit", param.getLimit());
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProductByCond", cond, page);
  }

  /**
   * 级联查询专业内产品列表
   * 
   * @param param 查询参数
   * @return 产品列表
   */
  public Page<ProductModel> cascadeQueryProductInCat(IJoinQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin()) cond.put("join", param.getJoin());
    if (null != param && null != param.getInput()) cond.put("input", param.getInput());
    if (null != param && null != param.getOutput()) cond.put("output", param.getOutput());
    if (null != param && null != param.getSort()) cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit()) cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryProductInCat", cond, null);
  }

}
