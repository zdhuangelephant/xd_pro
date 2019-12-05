package com.xiaodou.forum.dao.forum;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.forum.dao.BaseProcessDao;
import com.xiaodou.forum.model.forum.ForumModel;

@Repository
public class ForumModelDao extends BaseProcessDao<ForumModel> {

  public void updateRepliesNumber(Integer id, Integer repliesNumber) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("updateTime", new Timestamp(System.currentTimeMillis()).toString());
    cond.put("id", id);
    cond.put("repliesNumber", repliesNumber);
    getSqlSession().update(getEntityClass().getSimpleName() + ".updateRepliesNumber", cond);
  }

  public Integer queryForumNumber(Long forumCategoryId) {
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> cond = Maps.newHashMap();
    input.put("categoryId", forumCategoryId);
    cond.put("input", input);
    return (Integer) this.getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".queryForumNumber", cond);
  }

}
