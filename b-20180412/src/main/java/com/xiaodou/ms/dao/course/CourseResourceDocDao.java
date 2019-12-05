package com.xiaodou.ms.dao.course;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.course.CourseResourceDocModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
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

@Repository("courseResourceDocDao")
public class CourseResourceDocDao extends BaseProcessDao<CourseResourceDocModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<CourseResourceDocModel> cascadeQueryDoc(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return this.selectPaginationList(
      this.getEntityClass().getSimpleName() + ".cascadeQueryDoc", mapParam, null);
  }

}
