package com.xiaodou.st.dashboard.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class RawDataProductDao extends BaseDao<RawDataProductDO> {

  public Page<RawDataProductDO> findExamDate() {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> output = Maps.newHashMap();
    output.put("examDate", 1);
    cond.put("output", output);
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findExamDate", cond, null);
  }
}
