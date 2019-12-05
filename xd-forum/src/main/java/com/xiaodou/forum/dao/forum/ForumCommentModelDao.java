package com.xiaodou.forum.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.forum.dao.BaseProcessDao;
import com.xiaodou.forum.model.forum.ForumCommentModel;

/**
 * 话题评论基础Dao
 * 
 * @author wuyunkuo
 * 
 */
@Repository("forumCommentModelDao")
public class ForumCommentModelDao extends BaseProcessDao<ForumCommentModel> {

  public Integer queryCommentNumber(Integer forumId) {
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> cond = Maps.newHashMap();
    input.put("forumId", forumId);
    cond.put("input", input);
    return (Integer) this.getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".queryCommentNumber", cond);
  }

}
