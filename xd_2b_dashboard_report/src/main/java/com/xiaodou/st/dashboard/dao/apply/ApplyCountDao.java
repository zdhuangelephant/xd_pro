package com.xiaodou.st.dashboard.dao.apply;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.dao.IGroupParam;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class ApplyCountDao extends BaseDao<ApplyCountDO> {
  public Page<ApplyCountDO> groupCatApply(IGroupParam param, Page<ApplyCountDO> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("group", param.getGroup());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".groupCatApply",
        cond, page);
  }
}
