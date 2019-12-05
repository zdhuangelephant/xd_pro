package com.xiaodou.resources.dao.quesbk;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;

public interface QuesbkExamPaperMapper {
  int deleteByPrimaryKey(String id);

  int insert(QuesbkExamPaper record);

  int insertSelective(QuesbkExamPaper record);

  QuesbkExamPaper selectByPrimaryKey(String id);

  int updateByPrimaryKeySelective(QuesbkExamPaper record);

  int updateByPrimaryKey(QuesbkExamPaper record);

  List<QuesbkExamPaper> selectBySubjectIdAndExamType(String subjectId, String examType);
  //今日做题量
  List<String> queryTodayExamPaperList(Map<String, Object> cond);
  //总共做题量
  List<String> queryTotalExamPaperList(Map<String, Object> cond);
}
