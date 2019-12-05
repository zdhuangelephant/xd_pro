package com.xiaodou.userCenter.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.userCenter.model.vo.UserPraiseVo;

@Repository("userPraiseVoDao")
public class UserPraiseVoDao extends BaseProcessDao<UserPraiseVo> {

  public Page<UserPraiseVo> queryUserListByPraise(Map<String, Object> inputArgument,
      Map<String, Object> outputField, Page<UserPraiseVo> page) {
    Map<String, Map<String, Object>> mapParam = new HashMap<String, Map<String, Object>>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".queryUserListByPraise", mapParam, page);
  }
}
