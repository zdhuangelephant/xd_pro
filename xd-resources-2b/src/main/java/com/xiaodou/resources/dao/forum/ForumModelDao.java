package com.xiaodou.resources.dao.forum;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.vo.forum.CommonCount;

@Repository
public class ForumModelDao extends BaseProcessDao<ForumModel> {

  public void updateRepliesNumber(Long id, Integer repliesNumber) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("updateTime", new Timestamp(System.currentTimeMillis()).toString());
    cond.put("id", id);
    cond.put("repliesNumber", repliesNumber);
    getSqlSession().update(getEntityClass().getSimpleName() + ".updateRepliesNumber", cond);
  }

  
  public Integer queryForumNumber(Map<String, Object> inputArgument) {
	    Map<String, Object> cond = Maps.newHashMap();
	    cond.put("input", inputArgument);
	    return (Integer) this.getSqlSession().selectOne(
	        getEntityClass().getSimpleName() + ".queryForumNumber", cond);
	  }
  
  public Integer queryForumNumberByColumnId(String columnId) {
	    Map<String, Object> input = Maps.newHashMap();
	    Map<String, Object> cond = Maps.newHashMap();
	    input.put("columnId", columnId);
	    cond.put("input", input);
	    return (Integer) this.getSqlSession().selectOne(
	        getEntityClass().getSimpleName() + ".queryForumNumber", cond);
	  }

  
  @SuppressWarnings("unchecked")
  public List<CommonCount> queryForumUsersNumber(Map<String, Object> cond) {
	    return this.getSqlSession()
	        .selectList(getEntityClass().getSimpleName() +".queryCommenCountUserList", cond);
	  }
  
  
}
