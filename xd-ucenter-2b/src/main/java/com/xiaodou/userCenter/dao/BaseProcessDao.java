package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 
 * @ClassName: BaseProcessDao
 * @Description: 封装通用增，删，改，查的方法
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午8:57:28
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseProcessDao<Entity> extends BaseDao<Entity> {

  /**
   * 
   * @Title: queryList
   * @Description: 根据条件 查询 列表 (不分页)
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @return List<Entity>
   */
  public List<Entity> queryList(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.getSqlSession().selectList(this.getEntityClass().getSimpleName() + ".queryList",
        mapParam);
  }

  /**
   * 
   * @Title: queryListByPaging @Description: 根据条件 查询 列表 (分页) @param inputArgument 查询条件 @param
   *         outputField 需要返回的字段 @param page 分页数据 @return Page<Entity> @throws
   */
  public Page<Entity> queryListByPaging(Map inputArgument, Map outputField, Page page) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".queryListByCond",
        mapParam, page);
  }

  /**
   * 
   * @Title: updateEntity
   * @Description: 根据条件 更新 数据结构
   * @param condition 更新条件
   * @param value 更新的值
   * @return Integer
   */
  public Integer updateEntity(Map condition, Entity value) {
    Map mapParam = new HashMap();
    mapParam.put("input", condition);
    mapParam.put("value", value);
    return getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
        mapParam);
  }

  /**
   * 
   * @Title: deleteList
   * @Description: 根据条件 批量删除 数据
   * @param inputArgument 删除条件
   * @return int
   */
  public int deleteList(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteList", mapParam);
  }

  /**
   * @Title: count
   * @Description: 根据条件 查询数量
   * @param queryCond 查询条件
   * @return int
   */
  public Integer count(Map<String, Object> queryCond) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", queryCond);
    return (Integer) getSqlSession().selectOne(this.getEntityClass().getSimpleName() + ".count",
        mapParam);
  }


  /**
   * 
   * @description 批量增加数据
   * @author 李德洪
   * @Date 2017年11月9日
   * @param column 字段
   * @param value 字段至
   * @return
   */
  public Integer addEntityList(Map<String, Object> column, List<Entity> value) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addEntityList",
        cond);
  }
}
