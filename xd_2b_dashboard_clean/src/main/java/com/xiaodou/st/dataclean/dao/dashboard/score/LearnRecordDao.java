package com.xiaodou.st.dataclean.dao.dashboard.score;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.LearnRecordDO;
import com.xiaodou.summer.dao.jdbc.BaseDao;

@Repository
public class LearnRecordDao extends BaseDao<LearnRecordDO> {

  public List<LearnRecordDO> getStudentForMaxLearnTime(Integer stuId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("stuId", stuId);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".getStudentForMaxLearnTime", cond);
  }


}
