package com.xiaodou.mooccrawler.dao.course;

import com.xiaodou.mooccrawler.domain.course.CourseSubjectModel;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @ClassName: SubjectModelDao
 * @Description: 课程科目Dao
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午8:53:19
 */

@Repository("courseSubjectDao")
public class CourseSubjectDao extends BaseProcessDao<CourseSubjectModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<CourseSubjectModel> cascadeQuerySubject(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return this.selectPaginationList(
      this.getEntityClass().getSimpleName() + ".cascadeQuerySubject", mapParam, null);
  }

}
