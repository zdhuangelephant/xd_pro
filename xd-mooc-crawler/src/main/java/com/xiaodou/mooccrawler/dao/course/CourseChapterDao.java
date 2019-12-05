package com.xiaodou.mooccrawler.dao.course;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: ChapterModelDao
 * @Description: 大纲章节DAO
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午9:17:05
 */

@Repository("courseChapterDao")
public class CourseChapterDao extends BaseProcessDao<CourseChapterModel> {
	/**
	 * @param inputArgument
	 *            查询条件
	 * @return Page<Entity>
	 * @throws
	 * @Title: queryListByPaging
	 * @Description: 根据条件 查询 列表 (分页)
	 */
	public Page<CourseChapterModel> cascadeQueryChapter(Map inputArgument,
			Map output) {
		Map<String, Map> mapParam = new HashMap<String, Map>();
		mapParam.put("input", inputArgument);
		mapParam.put("output", output);
		return this.selectPaginationList(this.getEntityClass().getSimpleName()
				+ ".cascadeQueryMission", mapParam, null);
	}
	
	
	  public Page<CourseChapterModel> findChapterId(Map inputArgument, Map output) {
		    Map<String, Map> mapParam = new HashMap<String, Map>();
		    mapParam.put("input", inputArgument);
		    mapParam.put("output", output);
		    
		    return this.selectPaginationList(
		        this.getEntityClass().getSimpleName() + ".findChapterId", mapParam, null);
	  }
}
