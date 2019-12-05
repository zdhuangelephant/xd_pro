package com.xiaodou.dao.product;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkExamPaperDao extends ProcessBaseDao<QuesbkExamPaper>{
  public int deleteByPrimaryKey(String id) {
    QuesbkExamPaper paper = new QuesbkExamPaper();
    paper.setId(id);
    return super.deleteEntityById(paper) ? 1 : 0;
  }

  public int insert(QuesbkExamPaper record) {
    return super.addEntity(record).getId() != null ? 1 : 0;
  }

  public int insertSelective(QuesbkExamPaper record) {
    return super.addEntity(record).getId() != null ? 1 : 0;
  }

  public QuesbkExamPaper selectByPrimaryKey(String id) {
    QuesbkExamPaper paper = new QuesbkExamPaper();
    paper.setId(id);
    return super.findEntityById(paper);
  }

  public int updateByPrimaryKeySelective(QuesbkExamPaper record) {
    return super.updateEntityById(record) ? 1 : 0;
  }

  public int updateByPrimaryKey(QuesbkExamPaper record) {
    return super.updateEntityById(record) ? 1 : 0;
  }

  public List<QuesbkExamPaper> selectBySubjectIdAndExamType(String subjectId, String examType){
    IQueryParam param = new QueryParam();
    param.addInput("courseId", subjectId);
    param.addInput("examTypeId", examType);
    param.addOutputs(CommUtil.getAllField(QuesbkExamPaper.class));
    Page<QuesbkExamPaper> resLists = findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }
  //今日做题量
  public List<String> queryTodayExamQuesList(Map<String, Object> cond){
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".queryTodayExamQuesList",
        super.getCondWrap(param));
  }
  //总共做题量
  public List<String> queryTotalExamPaperList(Map<String, Object> cond){
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".queryTotalExamPaperList",
        super.getCondWrap(param));
  }
}
