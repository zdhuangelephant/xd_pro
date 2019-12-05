package com.xiaodou.dao.product;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkQuesStatistics;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkQuesStatisticsDao  extends ProcessBaseDao<QuesbkQuesStatistics> {
  public int insert(QuesbkQuesStatistics record) {
    return super.addEntity(record).getId() != null ? 1 : 0;
  }

  public int insertSelective(QuesbkQuesStatistics record) {
    return super.addEntity(record).getId() != null ? 1 : 0;
  }
  
  public int updateByExam(QuesbkQuesStatistics record) {
    return updateEntityById(record) ? 1 : 0;
  }
  
  public QuesbkQuesStatistics selectByQuesId(String quesId,String courseId) {
    IQueryParam param = new QueryParam();
    param.addInput("questionId", quesId);
    param.addInput("courseId", courseId);
    param.addOutputs(CommUtil.getAllField(QuesbkQuesStatistics.class));
    Page<QuesbkQuesStatistics> resLists = findEntityListByCond(param, null);
    return (null == resLists || resLists.getResult().size() == 0) ? null : resLists.getResult().get(0);
  }
}
