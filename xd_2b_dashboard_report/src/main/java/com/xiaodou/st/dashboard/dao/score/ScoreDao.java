package com.xiaodou.st.dashboard.dao.score;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

@Repository
public class ScoreDao extends BaseDao<ScoreDO> {

  public Page<ScoreDO> findPureScoreListByCond(IQueryParam param, Page<ScoreDO> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findPureScoreListByCond", cond, page);
  }


  public Page<ScoreDO> findScoreListJoinProduct(IQueryParam param, Page<ScoreDO> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findScoreListJoinProductByCond", cond, page);
  }

}
