package com.xiaodou.resources.dao.quesbk;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;

public interface QuesbkQuesMapper {
  
  QuesbkQues selectByPrimaryKey(String id, String courseId);

  List<QuesbkQues> selectByPrimaryKeyList(Map<String, Object> paramMap);

  List<QuesbkQues> selectAbstractByPrimaryKeyList(Map<String, Object> paramMap);

  List<QuesbkQues> selectByRule(Rule rule);
  
  List<QuesbkQues> selectAbstractByRule(Rule rule);
}
