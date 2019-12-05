package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.userCenter.model.RankUserInfo;

@Repository("userRankDao")
public class UserRankDao extends BaseProcessDao<RankUserInfo> {

  /**
   * 
   * @Title: queryPraiseListByCond
   * @Description: 根据条件 查询 列表 (分页)
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @param page 分页数据
   * @return Page<Entity>
   * @throws
   */
  public Page<RankUserInfo> queryPraiseListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page) {
    Map<String, Map<String, Object>> mapParam = new HashMap<String, Map<String, Object>>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".queryPraiseListByCond", mapParam, page);
  }

  /**
   * 
   * @Title: queryCreditListByCond
   * @Description: 根据条件 查询 列表 (分页)
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @param page 分页数据
   * @return Page<Entity>
   * @throws
   */
  public Page<RankUserInfo> queryCreditListByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<RankUserInfo> page) {
    Map<String, Map<String, Object>> mapParam = new HashMap<String, Map<String, Object>>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".queryCreditListByCond", mapParam, page);
  }

  public RankUserInfo queryMyPraiseByCond(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    Map<String, Map<String, Object>> mapParam = new HashMap<String, Map<String, Object>>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return (RankUserInfo) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryMyPraiseByCond", mapParam);
  }

  public Integer queryCreditBySelf(Long userId){
    return (Integer) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryCreditBySelf", userId);
  }
  
  public Integer countGtSelf(Integer credit) {
    return (Integer) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".countGtSelf", credit);
  }
  
  public Integer countEqSelf(Long userId,Integer credit) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", userId);
    input.put("credit", credit);
    return (Integer) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".countEqSelf", input);
  }
}
