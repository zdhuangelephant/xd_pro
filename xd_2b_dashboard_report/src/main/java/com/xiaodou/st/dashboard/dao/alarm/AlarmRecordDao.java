package com.xiaodou.st.dashboard.dao.alarm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.param.IQueryParam;

@Repository
public class AlarmRecordDao extends BaseDao<AlarmRecordDO> {

  @SuppressWarnings("rawtypes")
  public Integer findCountByCond(IQueryParam param) {
    Map<String, Map> cond = new HashMap<String, Map>();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    return (Integer) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findCountByCond", cond);
  }
  
}
