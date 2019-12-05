package com.xiaodou.resources.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;

/**
 * @name ProductItemDao CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月14日
 * @description 产品资源节点模型
 * @version 1.0
 */
@Repository("productItemDao")
public class ProductItemDao extends BaseDao<ProductItemModel> {


  /**
   * 级联查表
   * 
   * @param param 查询参数
   * @param page 分页参数
   * @return 查询结果
   */
  public Page<ProductItemModel> casecadeQueryByCond(IJoinQueryParam param,
      Page<ProductItemModel> page) {
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
    return this.selectPaginationList("CasecadeProductItem.casecadeQueryByCond", cond, page);
  }

  @SuppressWarnings("unchecked")
  public boolean updateEntityById(ProductItemModel entity) {
    Map<String, Object> cond = Maps.newHashMap();
    if (entity instanceof Map) {
      cond.putAll((Map<String, Object>) entity);
    } else {
      CommUtil.transferFromVO2Map(cond, entity);
    }
    cond.put("value", entity);
    int result = this.getSqlSession().update("CasecadeProductItem.updateEntityById", cond);
    return result == 1;
  }
}
