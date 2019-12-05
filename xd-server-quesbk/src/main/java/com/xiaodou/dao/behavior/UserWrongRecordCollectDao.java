package com.xiaodou.dao.behavior;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.behavior.UserWrongRecordCollect;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name UserWrongRecordCollectDao CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月8日
 * @description TODO
 * @version 1.0
 */
@Repository
public class UserWrongRecordCollectDao extends ProcessBaseDao<UserWrongRecordCollect> {


  /**
   * 插入新纪录
   * 
   * @param entity
   * @return
   */
  public int insertEntity(UserWrongRecordCollect entity) {
    return super.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insertEntity",
        entity);
  }

  /**
   * 根据用户ID和课程ID获取错题集合列表
   * 
   * @param userId 用户ID
   * @param courseId 课程ID
   * @return 错题集合列表
   */
  public List<UserWrongRecordCollect> selectByUserIdAndCourseId(String userId, String courseId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("courseId", courseId);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByUserIdAndCourseId",
        super.getCondWrap(param));

  }

  /**
   * 根据用户ID和课程ID章节ID获取错题集合列表
   * 
   * @param userId 用户ID
   * @param courseId 课程ID
   * @param itemId 节ID
   * @return 错题集合列表
   */
  public UserWrongRecordCollect selectByUserIdAndCourseIdItemId(String userId, String courseId,
      String itemId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("courseId", courseId);
    param.addInput("chapterId", itemId);
    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByUserIdAndCourseIdItemId",
        super.getCondWrap(param));
  }

  /**
   * 根据用户ID、课程ID、章节ID修改记录数量
   * 
   * @param userWrongRecordCollect
   * @return
   */
  public int updateByUSISelective(UserWrongRecordCollect userWrongRecordCollect) {
    return this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateByUSISelective", userWrongRecordCollect);
  }
}
