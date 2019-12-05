package com.xiaodou.ms.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;

/**
 * Created by zyp on 15/8/18.
 * @author zdhuang
 */
@Repository("finalExamDao")

public class FinalExamDao extends BaseProcessDao<FinalExamModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<FinalExamModel> cascadeQueryFinalExamMission(IJoinQueryParam param,
      Page<FinalExamModel> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin() && param.getJoin().size() > 0) {
    	cond.put("join", param.getJoin());
    }
    if (null != param && null != param.getInput() && param.getInput().size() > 0) {
    	cond.put("input", param.getInput());
    }
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryFinalExamMission", cond, page);
  }
}
