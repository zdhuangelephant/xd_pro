package com.xiaodou.resources.dao.quesbk;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.quesbk.UserExamTotal;

public interface UserExamTotalMapper {
  int deleteByPrimaryKey(String id);

  int selectAllUserCountBySubjectId(String subject);

  int insert(UserExamTotal record);

  int insertSelective(UserExamTotal record);

  UserExamTotal selectByPrimaryKey(String id);

  int updateByPrimaryKeySelective(UserExamTotal record);

  int updateByPrimaryKey(UserExamTotal record);

  UserExamTotal selectByUidAndSubjectId(String uid, String subjectId);

  List<UserExamTotal> selectByUid(String uid);

  List<UserExamTotal> selectByUidNotAll(Map<String, Object> input);

  List<UserExamTotal> queryByCond(Map<String, Object> cond);

  Integer queryTotalRankByCond(String count, String uid, String courseId);

  Integer queryRightRankByCond(String string, String uid, String courseId);

}
