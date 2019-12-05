package com.xiaodou.resources.dao.quesbk;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.quesbk.UserExamRecord;

public interface UserExamRecordMapper {
  int deleteByPrimaryKey(String id);

  int insert(UserExamRecord record);

  int insertSelective(UserExamRecord record);

  UserExamRecord selectByPrimaryKey(String id);

  UserExamRecord selectByUidAndExamId(String id, String uid);

  UserExamRecord selectByUidAndPaperId(String paperId, String uid);

  List<UserExamRecord> selectByUidAndSubjectId(String userId, String subject);

  List<UserExamRecord> selectByUidSubjectIdAndExamTypeId(String userId, String subject,
      String examTypeId);

  List<UserExamRecord> selectByUidProductIdAndItemId(String userId, String productId, String itemId);

  List<UserExamRecord> selectByNotUidSubjectIdAndExamTypeId(Map<String, Object> params);

  List<UserExamRecord> selectByPaperId(String paperId);

  int updateByPrimaryKeySelective(UserExamRecord record);

  int updateByPrimaryKey(UserExamRecord record);

  List<UserExamRecord> selectExamCostByExamCost(Map<String, Object> params);

}
