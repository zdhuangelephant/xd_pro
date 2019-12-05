package com.xiaodou.st.dataclean.dao.raw;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.st.dataclean.dao.raw.RawDataExamTotalDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 原始数据:用户练习统计记录
 * @version 1.0
 */
@Repository("rawDataExamTotalDao")
public class RawDataExamTotalDao extends BaseDao<RawDataExamTotalModel> {

  public Page<RawDataExamTotalModel> queryTotalStatistic(IQueryParam param,
      Page<RawDataExamTotalModel> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return super.selectPaginationList(getEntityClass().getSimpleName() + ".queryTotalStatistic",
        cond, page);
  }

  public Double queryAvgRightPercent(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAvgRightPercent", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }

  public Double queryAvgScore(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    Double count =
        (Double) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryAvgScore", cond);
    if (count != null) {
      return count;
    } else {
      return (double) 0;
    }
  }

}
