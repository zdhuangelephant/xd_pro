package com.xiaodou.resources.dao.quesbk;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.model.quesbk.UserWrongRecordCollect;

@Repository
public interface UserWrongRecordCollectMapper {

  /**
   * 插入新纪录
   * 
   * @param entity
   * @return
   */
  int insertEntity(UserWrongRecordCollect entity);

  /**
   * 根据用户ID和课程ID获取错题集合列表
   * 
   * @param userId 用户ID
   * @param courseId 课程ID
   * @return 错题集合列表
   */
  public List<UserWrongRecordCollect> selectByUserIdAndCourseId(String userId, String courseId);

  /**
   * 根据用户ID和课程ID章节ID获取错题集合列表
   * 
   * @param userId 用户ID
   * @param courseId 课程ID
   * @param itemId 节ID
   * @return 错题集合列表
   */
  public UserWrongRecordCollect selectByUserIdAndCourseIdItemId(String userId, String courseId,
      String itemId);

  /**
   * 根据用户ID、课程ID、章节ID修改记录数量
   * 
   * @param userWrongRecordCollect
   * @return
   */
  int updateByUSISelective(UserWrongRecordCollect userWrongRecordCollect);

}
