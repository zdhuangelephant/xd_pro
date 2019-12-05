package com.xiaodou.course.dao.forum;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.course.dao.order.ProductOrderDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月5日
 * @description Dao
 * @version 1.0
 */
@Repository("forumDao")
public class ForumDao extends BaseProcessDao<ForumModel> {
	 /**
	   * @param inputArgument 查询条件
	   * @return Page<Entity>
	   * @throws
	   * @Title: queryListByPaging
	   * @Description: 根据条件 查询 列表 (分页)
	   */
	  @SuppressWarnings("rawtypes")
	  public Page<ForumModel> cascadeQueryForum(Map inputArgument, Map output) {
	    Map<String, Map> mapParam = new HashMap<String, Map>();
	    mapParam.put("input", inputArgument);
	    mapParam.put("output", output);
	    return this.selectPaginationList(
	        this.getEntityClass().getSimpleName() + ".cascadeQueryForum", mapParam, null);
	  }


	public Integer getForumListCount(Map<String, Object> input) {
	    Map<String, Object> cond = Maps.newHashMap();
	    cond.put("input", input);
	    Integer count =
	        (Integer) this.getSqlSession().selectOne(
	            getEntityClass().getSimpleName() + ".getForumListCount", cond);
	    if (count != null) {
	      return count;
	    } else {
	      return 0;
	    }
	  }

	
}
