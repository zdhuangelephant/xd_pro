package com.xiaodou.mooccrawler.dao.course;

import org.springframework.stereotype.Repository;

import com.xiaodou.mooccrawler.domain.course.CourseKeywordResourceModel;


/**
 * 
 * @ClassName: KeywordResourceModelDao
 * @Description: 关键词与资源绑定DAO
 * @author zhaoxu.yang
 * @date 2015年4月11日 下午10:34:28
 */

@Repository("courseKeywordResourceDao")
public class CourseKeywordResourceDao extends BaseProcessDao<CourseKeywordResourceModel> {
}
