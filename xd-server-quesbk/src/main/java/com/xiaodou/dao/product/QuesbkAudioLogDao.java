package com.xiaodou.dao.product;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkAudioLog;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkAudioLogDao extends ProcessBaseDao<QuesbkAudioLog> {
  public List<QuesbkAudioLog> selectByUserId(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addLimitStart(0);
    param.addLimitCount(20);
    param.addSort("submitTime", Sort.DESC);
    param.addOutputs(CommUtil.getAllField(QuesbkAudioLog.class));
    Page<QuesbkAudioLog> resLists = super.findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }


  public Integer selectCountQuesVideoLogListByUserId(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(QuesbkAudioLog.class));
    Page<QuesbkAudioLog> resLists = super.findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult().size();
  }

//  public List<QuesbkAudioLog> selectByUserIdAndId(Map<String, Object> cond) {
//    Assert.notEmpty(cond);
//    Assert.notNull(cond.get("id"), "id is not Empty!");
//    return this.selectByUserIdAndId((Integer) cond.get("id"));
//  }

  public List<QuesbkAudioLog> selectByUserIdAndId(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(QuesbkAudioLog.class));
    param.addLimitStart(0);
    param.addLimitCount(20);
    param.addSort("submitTime", Sort.DESC);
    Page<QuesbkAudioLog> resLists = super.findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }

  // public List<QuesbkAudioLog> selectByUserIdAndId(Map<String, Object> cond) {
  // IQueryParam param = new QueryParam();
  // param.addInputs(cond);
  // param.addOutputs(CommUtil.getAllField(QuesbkAudioLog.class));
  // return this.getSqlSession().selectList(
  // this.getEntityClass().getSimpleName() + ".selectByUserIdAndId", super.getCondWrap(param));
  // }
}
