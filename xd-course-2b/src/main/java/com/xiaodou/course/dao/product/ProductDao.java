package com.xiaodou.course.dao.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productdao")
public class ProductDao extends BaseProcessDao<ProductModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: cascadeQueryProduct
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductModel> cascadeQueryProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", mapParam, null);
  }

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: cascadeQueryMyProduct
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductModel> cascadeQueryMyProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryMyProduct", mapParam, null);
  }

  public Page<ProductModel> cascadeQueryMyProduct0(Integer limitCount,Map<String,Object> input, Map<String,Object> output) {
    Map<String, Map<String,Object>> mapParam = Maps.newHashMap();
    mapParam.put("input", input);
    mapParam.put("output", output);
    if(null != limitCount){
      Map<String,Object> limit = Maps.newHashMap();
      limit.put("limitCount", limitCount);//Constant.LIMIT_COUNT
      mapParam.put("limit", limit);
    }
    Map<String,Object> sort = Maps.newHashMap();
    sort.put("id", Sort.DESC.toString());
    mapParam.put("sort", sort);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryMyProduct0", mapParam, null);
  }
  /**
   * 根据课程id查看课程详情
   */
  public ProductModel queryEntityById(ProductModel entity) {
    return (ProductModel) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryEntityById", entity);
  }
  
  
  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: cascadeQueryMyNewbieProduct
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<ProductModel> cascadeQueryMyNewbieProduct(Map inputArgument, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", output);
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".cascadeQueryMyNewbieProduct", mapParam, null);
  }
}
