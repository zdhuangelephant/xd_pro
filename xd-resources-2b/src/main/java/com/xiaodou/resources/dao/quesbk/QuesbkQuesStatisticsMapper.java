package com.xiaodou.resources.dao.quesbk;

import com.xiaodou.resources.model.quesbk.QuesbkQuesStatistics;

public interface QuesbkQuesStatisticsMapper {
  int insert(QuesbkQuesStatistics record);

  int insertSelective(QuesbkQuesStatistics record);
  
  int updateByExam(QuesbkQuesStatistics record);
  
  QuesbkQuesStatistics selectByQuesId(String quesId,String courseId);
}
