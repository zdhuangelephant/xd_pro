package com.xiaodou.dao.behavior;

import java.util.List;
import java.util.Map;

import com.xiaodou.domain.behavior.UserStoreRecord;

/**
 * @deprecated
 * @name UserStoreRecordMapper 
 * CopyRright (c) 2018 by 李德洪
 *
 * @author 李德洪
 * @date 2018年1月9日
 * @description TODO
 * @version 1.0
 */
public interface UserStoreRecordMapper {
  int deleteByPrimaryKey(String id);

  int insert(UserStoreRecord record);

  int insertSelective(UserStoreRecord record);

  UserStoreRecord selectByPrimaryKey(String id);

  UserStoreRecord selectByUSCQ(String userId, String subject, String chapter, String questionId);

  List<UserStoreRecord> selectByUidAndSubjectId(String userId, String subject);

  List<UserStoreRecord> selectByUSC(String userId, String subject, String chapter);
  
  Integer queryCountByUSC(String userId, String subject, String chapter);

  List<UserStoreRecord> selectByUSCList(UserStoreRecord userStoreRecord);
  List<UserStoreRecord> selectByUSCList(Map<String, Object> input);
  
  int updateByPrimaryKeySelective(UserStoreRecord record);

  int updateByPrimaryKey(UserStoreRecord record);
}
