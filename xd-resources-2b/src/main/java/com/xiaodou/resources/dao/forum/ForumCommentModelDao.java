package com.xiaodou.resources.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.forum.ForumCommentModel;

/**
 * 话题评论基础Dao
 * 
 * @author wuyunkuo
 * 
 */
@Repository("forumCommentModelDao")
public class ForumCommentModelDao extends BaseProcessDao<ForumCommentModel> {

  public Integer queryCommentNumber(Long forumId) {
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> cond = Maps.newHashMap();
    input.put("resourcesId", forumId);
    cond.put("input", input);
    return (Integer) this.getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".queryCommentNumber", cond);
  }

  public Integer queryCommentNumber(String userId, String productId, String itemId) {
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> cond = Maps.newHashMap();
    if (StringUtils.isNotBlank(userId)) input.put("replyId", userId);
    if (StringUtils.isNotBlank(productId)) input.put("productId", productId);
    if (StringUtils.isNotBlank(itemId)) input.put("itemId", itemId);
    cond.put("input", input);
    return (Integer) this.getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".queryCommentNumber", cond);
  }

}
