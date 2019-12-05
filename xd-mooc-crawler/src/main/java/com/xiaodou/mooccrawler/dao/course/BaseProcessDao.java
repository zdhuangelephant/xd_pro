package com.xiaodou.mooccrawler.dao.course;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @author zhaoxu.yang
 * @ClassName: BaseProcessDao
 * @Description: 封装通用增，删，改，查的方法
 * @date 2015年4月11日 下午8:57:28
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseProcessDao<Entity> extends BaseDao<Entity> {
  /**
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @param page 分页数据
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<Entity> queryListByCond(Map inputArgument, Map outputField, Page page) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(outputField);
    param.addSort("isVisible", Sort.DESC);
    param.addSort("updateTime", Sort.DESC); 
    param.addSort("createTime", Sort.DESC);
    return super.findEntityListByCond(param, page);
  }

  /**
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<Entity> queryListByCondWithOutPage(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findEntityListByCond", mapParam, null);
  }

  /**
   * @param condition 更新条件
   * @param value 更新的值
   * @return Integer
   * @Title: updateEntity
   * @Description: 根据条件 更新 数据结构
   */
  public Boolean updateEntity(Map condition, Entity value) {
    Map mapParam = new HashMap();
    mapParam.put("input", condition);
    mapParam.put("value", value);
    return getSqlSession()
        .update(this.getEntityClass().getSimpleName() + ".updateEntity", mapParam) > 0;
  }

  /**
   * 删除记录
   * 
   * @param condition
   * @return
   */
  public Boolean deleteEntity(Map condition) {
    Map mapParam = new HashMap();
    mapParam.put("input", condition);
    return getSqlSession()
        .delete(this.getEntityClass().getSimpleName() + ".deleteEntity", mapParam) == 1;
  }

}
