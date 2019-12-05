package com.xiaodou.course.dao.comment;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.comment.CommentModel;

/**
 * 话题评论基础Dao
 * 
 * @author wuyunkuo
 * 
 */
@Repository("commentModelDao")
public class CommentModelDao extends BaseProcessDao<CommentModel> {

  public Integer queryCommentNumber(Long itemId) {
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> cond = Maps.newHashMap();
    input.put("itemId", itemId);
    cond.put("input", input);
    return (Integer) this.getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".queryCommentNumber", cond);
  }

}
