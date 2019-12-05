package com.xiaodou.st.dataclean.dao.dashboard.score;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.ScoreDO;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

@Repository
public class ScoreDao extends BaseDao<ScoreDO> {
  /**防止在score表内插入过期产品的分数
   * @param param
   * @param page
   * @return
   */
  public Page<ScoreDO> findEntityListByCond0(IQueryParam param,
      Page<ScoreDO> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return super.selectPaginationList(getEntityClass().getSimpleName() + ".findEntityListByCond0",
        cond, page);
  }

}
