package com.xiaodou.forum.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.forum.dao.BaseProcessDao;
import com.xiaodou.forum.model.forum.ForumPraiseModel;

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
