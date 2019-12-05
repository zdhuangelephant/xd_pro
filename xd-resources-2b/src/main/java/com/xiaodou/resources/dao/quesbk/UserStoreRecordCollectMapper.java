package com.xiaodou.resources.dao.quesbk;

import java.util.List;

import com.xiaodou.resources.model.quesbk.UserStoreRecordCollect;

public interface UserStoreRecordCollectMapper {
  /**
   * 插入新纪录
   * 
   * @param record
   * @return
   */
  int insert(UserStoreRecordCollect record);

  /**
   * 根据主键查询记录
   * 
   * @param id
   * @return
   */
  UserStoreRecordCollect selectByPrimaryKey(String id);

  /**
   * 根据用户ID和课程ID查询记录列表
   * 
   * @param userId
   * @param subject
   * @return
   */
  List<UserStoreRecordCollect> selectByUidAndSubjectId(String userId, String subject);

  /**
   * 根据用户ID、课程ID、节ID查询记录解表
   * 
   * @param userId
   * @param subject
   * @param item
   * @return
   */
  UserStoreRecordCollect selectByUSI(String userId, String subject, String item);

  /**
   * 根据用户ID、课程ID、章节ID增加记录数量
   * 
   * @param userId
   * @param subject
   * @param chapter
   * @return
   */
  int updateByUSI(String userId, String subject, String item);
  
  /**
   * 根据用户ID、课程ID、章节ID修改记录数量
   * 
   * @param userStoreRecordCollect
   * @return
   */
  int updateByUSISelective(UserStoreRecordCollect userStoreRecordCollect);
}
