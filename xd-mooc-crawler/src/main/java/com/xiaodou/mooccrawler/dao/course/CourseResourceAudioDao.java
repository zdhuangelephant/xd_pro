package com.xiaodou.mooccrawler.dao.course;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.course.CourseResourceAudioModel;
import com.xiaodou.summer.dao.pagination.Page;



/**
 * @name @see com.xiaodou.ms.dao.course.CourseResourceAudioDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 音频资源Dao
 * @version 1.0
 */
@Repository("courseResourceAudioDao")
public class CourseResourceAudioDao extends BaseProcessDao<CourseResourceAudioModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  public Page<CourseResourceAudioModel> cascadeQueryAudio(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".cascadeQueryAudio",
        mapParam, null);
  }

}
