package com.xiaodou.course.dao.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.user.UserLearnRecordModel;

@Repository("userLearnRecordDao")
public class UserLearnRecordDao extends BaseProcessDao<UserLearnRecordModel> {
  
  @SuppressWarnings("rawtypes")
  public Integer sumLearnTimeByCond(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return (Integer) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".sumLearnTimeByCond", mapParam);
  }
  
}
