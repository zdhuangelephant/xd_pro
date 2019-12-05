package com.xiaodou.course.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.forum.ForumPraiseModel;

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
  
}
