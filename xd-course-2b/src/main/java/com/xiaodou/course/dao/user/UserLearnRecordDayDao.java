package com.xiaodou.course.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserLearnRecordDayModel;

@Repository("userLearnRecordDayDao")
public class UserLearnRecordDayDao extends BaseProcessDao<UserLearnRecordDayModel> {

  /**
   * 
   * @Title: selectLearnTimeByDay
   * @Description: 查询每日学习时长
   * @param inputArgument 查询条件
   * @param outputField 需要返回的字段
   * @return List<Entity>
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public List<UserLearnRecordDayModel> selectLearnTimeByDay(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectLearnTimeByDay", mapParam);
  }

  @SuppressWarnings("rawtypes")
  public UserLearnRecordDayModel queryCostLearnTime(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return (UserLearnRecordDayModel) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryCostLearnTime", mapParam);
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  public List<UserLearnRecordDayModel> findEntityListByGroup(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".findEntityListByGroup", mapParam);
  }
  
}
