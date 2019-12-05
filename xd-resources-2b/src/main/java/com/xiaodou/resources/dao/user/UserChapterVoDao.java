package com.xiaodou.resources.dao.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.vo.user.UserChapterRecordVo;

/**
 * Created by ldh on 16/2/26.
 */
@Repository("userChapterVoDao")
public class UserChapterVoDao extends BaseProcessDao<UserChapterRecordVo> {

  /**
   * 根据课程id与用户id查找课程
   */
  @SuppressWarnings("unchecked")
  public List<UserChapterRecordVo> queryChapterRecordList(String userId, String courseId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".queryChapterRecordList", cond);
  }

  /**
   * 根据用户id/课程id/章ID查找课程
   */
  public UserChapterRecordVo queryChapterRecord(String userId, String courseId, String chapterId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    cond.put("chapterId", chapterId);
    Object selectOne =
        this.getSqlSession().selectOne(
            this.getEntityClass().getSimpleName() + ".queryChapterRecordList", cond);
    return null == selectOne ? null : (UserChapterRecordVo) selectOne;
  }

  /**
   * 根据用户id/课程id/章ID查找课程
   */
  @SuppressWarnings("unchecked")
  public List<UserChapterRecordVo> queryItemRecordList(String userId, String courseId,
      String chapterId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    cond.put("courseId", courseId);
    cond.put("chapterId", chapterId);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".queryItemRecordList", cond);
  }
}
