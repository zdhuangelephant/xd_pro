package com.xiaodou.dao.product;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class QuesbkQuesDao extends ProcessBaseDao<QuesbkQues> {

  public QuesbkQues selectByPrimaryKey(String questionId, String courseId) {
    IQueryParam param = new QueryParam();
    param.addInput("questionId", questionId);
    param.addInput("courseId", courseId);
    param.addInput("status", 99);
    List<QuesbkQues> result = this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByPrimaryKey", super.getCondWrap(param));
    return (result == null || result.isEmpty()) ? null : result.get(0);
  }

  public List<QuesbkQues> selectByPrimaryKeyList(Map<String, Object> paramMap) {
    IQueryParam param = new QueryParam();
    param.addInputs(paramMap);
    param.addInput("status", 99);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByPrimaryKeyList",
        super.getCondWrap(param));
  }

  public List<QuesbkQues> selectAbstractByPrimaryKeyList(Map<String, Object> paramMap) {
    IQueryParam param = new QueryParam();
    param.addInputs(paramMap);
    param.addInput("status", 99);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectAbstractByPrimaryKeyList",
        super.getCondWrap(param));
  }

//  public List<QuesbkQues> selectByRule(Rule rule) {
//    return this.getSqlSession().selectList(this.getEntityClass().getSimpleName() + ".selectByRule",
//        rule);
//  }

  public List<Long> selectQuesIdByRule(Rule rule) {
    return this.getSqlSession()
        .selectList(this.getEntityClass().getSimpleName() + ".selectQuesIdByRule", rule);
  }

  public List<QuesbkQues> selectByIdList(Map<String, Object> paramMap) {
    IQueryParam param = new QueryParam();
    param.addInputs(paramMap);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByIdList", super.getCondWrap(param));
  }

  public List<QuesbkQues> selectAbstractByRule(Rule rule) {
    return this.getSqlSession()
        .selectList(this.getEntityClass().getSimpleName() + ".selectAbstractByRule", rule);
  }

  public QuesbkQues selectQueByPrimaryKey(String quesId) {
    return null;
  }
}
