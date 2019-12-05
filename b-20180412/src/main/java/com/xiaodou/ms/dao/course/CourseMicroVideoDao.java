package com.xiaodou.ms.dao.course;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceMicroVideoModel;
import com.xiaodou.summer.dao.pagination.Page;

@Repository("courseMicroVideoDao")
public class CourseMicroVideoDao extends BaseProcessDao<CourseResourceMicroVideoModel>{

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<CourseResourceMicroVideoModel> cascadeQueryMicroVideo(Map<String,Object> input,Page<CourseResourceMicroVideoModel> page) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", input);
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".findEntityListQueryMicroVideoByCond",
        mapParam, page);
  }
  
}
