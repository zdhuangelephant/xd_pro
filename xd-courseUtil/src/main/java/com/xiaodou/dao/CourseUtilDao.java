package com.xiaodou.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.model.CourseModel;

@Repository("courseUtilDao")
public class CourseUtilDao extends BaseProcessDao<CourseModel> {
  /**
   * 根据课程用户id查找课程
   */
  @SuppressWarnings("unchecked")
  public List<CourseModel> queryEntityByUserId(Map<String, Object> cond) {
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".findEntityByUserId", cond);
  }

  /**
   * @param cond
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<CourseModel> queryBuyCourseByCond(Map<String, Object> cond) {
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".findBuyCourseByCond", cond);
  }

}
