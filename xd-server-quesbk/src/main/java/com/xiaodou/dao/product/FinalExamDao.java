package com.xiaodou.dao.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class FinalExamDao extends ProcessBaseDao<FinalExamModel> {
  public List<FinalExamModel> selectFinalExamByCond(String courseId, String userId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("courseId", courseId);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectFinalExamByCond", super.getCondWrap(param));

  }

  public FinalExamModel selectByPrimaryKey(Long id) {
    FinalExamModel model = new FinalExamModel();
    model.setId(id);
    return super.findEntityById(model);
  }
}
