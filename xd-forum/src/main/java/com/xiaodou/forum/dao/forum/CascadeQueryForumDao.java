package com.xiaodou.forum.dao.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.dao.BaseProcessDao;
import com.xiaodou.forum.model.forum.CommentForumModel;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class CascadeQueryForumDao extends BaseProcessDao {

  public Page<ForumUserModel> queryForumUserListByCond(Map<String, Object> cond,
      Page<ForumUserModel> page) {
    return this.selectPaginationList("cascadeQueryForum.queryForumUserListByCond", cond, page);
  }

  public List<ForumUserModel> queryForumUserListByCondNoPage(Map<String, Object> cond) {
    return this.getSqlSession()
        .selectList("cascadeQueryForum.queryForumUserListByCondNoPage", cond);
  }

  public ForumUserModel queryForumUserById(String id) {
    ForumUserModel model = new ForumUserModel();
    model.setId(new Integer(id));
    return (ForumUserModel) this.getSqlSession().selectOne("cascadeQueryForum.queryForumUserById",
        model);
  }

  public ForumUserModel queryForumUserByIdAndCategoryId(String id, String categoryId) {
    ForumUserModel model = new ForumUserModel();
    model.setId(new Integer(id));
    model.setCategoryId(categoryId);
    return (ForumUserModel) this.getSqlSession().selectOne(
        "cascadeQueryForum.queryForumUserByIdAndCategoryId", model);
  }

  public List<CommentUserModel> queryCommentUserListByCondNoPage(Map<String, Object> cond) {
    return this.getSqlSession().selectList("cascadeQueryForum.queryCommentUserListByCondNoPage",
        cond);
  }

  public List<CommentForumModel> queryCommentInForumListByCondNoPage(Map<String, Object> cond) {
    return this.getSqlSession().selectList("cascadeQueryForum.queryCommentInForumListByCondNoPage",
        cond);
  }

  public List<CommentUserModel> queryForumCommentByForumIdForHot(String forumdId, Integer size,
      Map<String, Object> output) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("forumId", forumdId);
    input.put("type", 1);
    cond.put("input", input);
    if (size == null) {
      cond.put("limitCount", 1);
    } else
      cond.put("limitCount", size);
    cond.put("output", output);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("id", "DESC");
    cond.put("sort", sort);
    return queryCommentUserListByCondNoPage(cond);
  }

  public List<CommentUserModel> queryForumCommentByForumId(String forumdId, String commentId,
      String commentIdUpper, Integer size, Map<String, Object> output) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("forumId", forumdId);
    if (StringUtils.isNotBlank(commentId)) input.put("id", commentId);
    if (StringUtils.isNotBlank(commentIdUpper)) input.put("idUpper", commentIdUpper);
    cond.put("input", input);
    if (size == null) {
      cond.put("limitCount", 5);
    } else
      cond.put("limitCount", size);
    cond.put("output", output);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("id", "DESC");
    cond.put("sort", sort);
    return queryCommentUserListByCondNoPage(cond);
  }

  public List<String> queryCategoryPeopleId(Long forumCategoryId) {
    return this.getSqlSession().selectList("cascadeQueryForum.queryCategoryPeopleNumber",
        forumCategoryId);
  }

  public List<RelateInfoUserModel> queryRelationInfoListByCondNoPage(Map<String, Object> cond) {
    return this.getSqlSession().selectList("cascadeQueryForum.queryRelationInfoListByCondNoPage",
        cond);
  }

}
