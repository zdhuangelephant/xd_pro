package com.xiaodou.st.dashboard.dao.apply;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

@Repository
public class ApplyDao extends BaseDao<ApplyDO> {

  public Integer addEntityList(List<ApplyDO> value) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", CommUtil.getAllField(ApplyDO.class));
    cond.put("value", value);
    return this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addEntityList",
        cond);
  }

  public Page<ApplyDO> listApplyAndStudent(IQueryParam param, Page<ApplyDO> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(
        this.getEntityClass().getSimpleName() + ".listApplyAndStudent", cond, page);
  }


  public Integer deleteApplyByCond(Map<String, Object> entity) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", entity);
    int result =
        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntity", cond);
    return result;
  }

  public Integer updateOrAddEntityList(List<ApplyDO> value, Map<String, Object> column) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateOrAddEntityList", cond);
  }

}
