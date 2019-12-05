package com.xiaodou.mooccrawler.dao.course;

import com.xiaodou.mooccrawler.domain.course.CourseResourceHtml5Model;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @ClassName: DocModelDao
 * @Description: 资源类型之一:文档DAO
 * @author zhaoxu.yang
 * @date 2015年4月12日 上午11:02:44
 */

@Repository("courseResourceHtml5Dao")
public class CourseResourceHtml5Dao extends BaseProcessDao<CourseResourceHtml5Model> {


  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<CourseResourceHtml5Model> cascadeQueryHtml5(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return this.selectPaginationList(
      this.getEntityClass().getSimpleName() + ".cascadeQueryHtml5", mapParam, null);
  }

}
