package com.xiaodou.resources.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.forum.ForumRelateInfoModel;

/**
 * @name @see com.xiaodou.resources.dao.forum.ForumRelateInfoModelDao.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月19日
 * @description 与我相关消息DAO
 * @version 1.0
 */
@Repository("forumRelateInfoModelDao")
public class ForumRelateInfoModelDao extends BaseProcessDao<ForumRelateInfoModel> {

  /**
   * 忽略所有未读消息
   * 
   * @param uid 用户ID
   */
  public void ignoreForumRelateInfos(String uid) {
    getSqlSession().update(getEntityClass().getSimpleName() + ".ignoreForumRelateInfos", uid);
  }

  /**
   * 根据条件查询我的相关信息数量
   * 
   * @param cond 查询条件
   * @return
   */
  public Integer countRelationInfo(Map<String, Object> cond) {
    return (Integer) getSqlSession().selectOne(
        getEntityClass().getSimpleName() + ".countRelationInfo", cond);
  }

}
