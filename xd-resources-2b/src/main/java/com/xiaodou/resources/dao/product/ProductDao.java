package com.xiaodou.resources.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;

/**
 * @name ProductDao CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月14日
 * @description 产品DAO
 * @version 1.0
 */
@Repository("productDao")
public class ProductDao extends BaseDao<ProductModel> {


  private Map<String, Object> getCond(IJoinQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin() && param.getJoin().size() > 0)
      cond.put("join", param.getJoin());
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return cond;
  }


  /**
   * 级联查表
   * 
   * @param param 查询参数
   * @param page 分页参数
   * @return 查询结果
   */
  public Page<ProductModel> casecadeQueryByCond(IJoinQueryParam param, Page<ProductModel> page) {
    Map<String, Object> cond = this.getCond(param);
    return this.selectPaginationList("CasecadeProduct.casecadeQueryByCond", cond, page);
  }

}
