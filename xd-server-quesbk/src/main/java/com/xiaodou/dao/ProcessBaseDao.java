package com.xiaodou.dao;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

public class ProcessBaseDao<Entity> extends BaseDao<Entity> {

  /**
   * @description 包装Map入参
   * @author 李德洪
   * @Date 2018年1月3日
   * @param param
   * @return
   */
  protected Map<String, Object> getCondWrap(IQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0) {
      cond.put("input", param.getInput());
    }
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0){
      cond.put("output", param.getOutput());
    }
    if (null != param && null != param.getSort() && param.getSort().size() > 0){
      cond.put("sort", param.getSort());
    }
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0) {
      cond.put("limit", param.getLimit());
    }
    return cond;
  }

  
  public Page<Entity> queryListByCondWithOutPage(Map<String,Object> inputArgument, Map<String,Object> outputField) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(outputField);
    return super.findEntityListByCond(param, null);
  }
  
}
