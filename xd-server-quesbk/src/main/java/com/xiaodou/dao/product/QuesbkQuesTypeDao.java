package com.xiaodou.dao.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkQuesTypeDao extends ProcessBaseDao<QuesbkQuesType> {

  /**
   * 根据主键ID获取问题类型
   * 
   * @param id 主键ID
   * @return 问题类型记录
   */
  public QuesbkQuesType selectByPrimaryKey(String id) {
    QuesbkQuesType type = new QuesbkQuesType();
    type.setId(Long.valueOf(id));
    return super.findEntityById(type);
  }

  /**
   * 获取问题类型列表
   * 
   * @return 问题类型列表
   */
  public List<QuesbkQuesType> selectQuesType() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(QuesbkQuesType.class));
    Page<QuesbkQuesType> resLists = findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }
}
