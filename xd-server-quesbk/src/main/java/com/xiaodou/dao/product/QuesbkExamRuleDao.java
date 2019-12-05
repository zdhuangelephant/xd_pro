package com.xiaodou.dao.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkExamRuleDao extends ProcessBaseDao<QuesbkExamRule>{
  /**
   * 根据产品ID和练习类型获取练习规则列表
   * 
   * @param productId 产品ID
   * @param examType 练习类型
   * @return 练习规则列表
   */
  public List<QuesbkExamRule> selectByProductIdAndExamType(String productId, String examType){
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("examTypeId", examType);
    param.addInput("status", 99);
    param.addOutputs(CommUtil.getAllField(QuesbkExamRule.class));
    Page<QuesbkExamRule> resLists = findEntityListByCond(param, null);
    return (null == resLists || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }
}
