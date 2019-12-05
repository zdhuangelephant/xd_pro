package com.xiaodou.mooccrawler.dao.course;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.summer.dao.pagination.Page;


/**
 * 
 * @ClassName: KeywordModelDao
 * @Description: 关键词DAO
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午9:58:47
 */

@Repository("courseKeywordDao")
public class CourseKeywordDao extends BaseProcessDao<CourseKeywordModel> {

  /**
   * @param inputArgument 查询条件
   * @return Page<Entity>
   * @throws
   * @Title: queryListByPaging
   * @Description: 根据条件 查询 列表 (分页)
   */
  @SuppressWarnings("rawtypes")
  public Page<CourseKeywordModel> cascadeQueryKeywords(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    return this.selectPaginationList(
      this.getEntityClass().getSimpleName() + ".cascadeQueryKeywords", mapParam, null);
  }
}
