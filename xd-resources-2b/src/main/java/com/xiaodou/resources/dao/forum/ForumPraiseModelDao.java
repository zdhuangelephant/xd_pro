package com.xiaodou.resources.dao.forum;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.vo.forum.CommonCount;

/**
 * 用户点赞 model
 * @author bing.cheng
 *
 */
@Repository("forumPraiseModelDao")
public class ForumPraiseModelDao extends BaseProcessDao<ForumPraiseModel> {

  public Integer queryPraiseNumber(Map<String, Object> inputArgument) {
    return (Integer) getSqlSession().selectOne(getEntityClass().getSimpleName() + ".queryPraiseNumber", inputArgument);
  }
  @SuppressWarnings("unchecked")
  public List<CommonCount> queryUsersPraiseNumber(Map<String, Object> cond) {
	    return this.getSqlSession()
	        .selectList(getEntityClass().getSimpleName() +".queryUsersPraiseNumber", cond);
	  }
  
}
