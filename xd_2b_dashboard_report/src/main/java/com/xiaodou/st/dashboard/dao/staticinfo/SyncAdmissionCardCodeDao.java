package com.xiaodou.st.dashboard.dao.staticinfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncAdmissionCardCodeDO;
import com.xiaodou.summer.dao.BaseDao;

@Repository
public class SyncAdmissionCardCodeDao extends BaseDao<SyncAdmissionCardCodeDO> {

  public Integer addEntityList(List<SyncAdmissionCardCodeDO> value, Map<String, Object> column) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addEntityList",
        cond);
  }
}
