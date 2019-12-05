package com.xiaodou.ms.service.topic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.topic.ForumCommentDao;
import com.xiaodou.ms.dao.topic.ForumListDao;
import com.xiaodou.ms.model.topic.ForumCommentModel;
import com.xiaodou.ms.web.request.topic.CommentCreateRequest;
import com.xiaodou.summer.dao.pagination.Page;

@Service("topicCommentService")
public class TopicCommentService {

  @Resource
  ForumCommentDao forumCommentDao;
  @Resource
  ForumListDao forumListDao;


  /**
   * 根据回帖id查询回帖数据
   * 
   * @param id
   * @return
   */
  public ForumCommentModel findCommentById(Integer id) {
    ForumCommentModel cond = new ForumCommentModel();
    cond.setId(id.toString());
    return forumCommentDao.findEntityById(cond);
  }


  /**
   * 更新回帖状态
   * 
   * @param forumCommentModel
   * @return
   */
  public Boolean editSubject(ForumCommentModel commentListModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", commentListModel.getId());
    return forumCommentDao.updateEntity(cond, commentListModel);
  }

  /**
   * 资源更新回帖
   * 
   * @param request
   * @return
   */
  public Boolean editSubject(CommentCreateRequest request) {
    ForumCommentModel forumCommentModel = new ForumCommentModel();
    forumCommentModel.setId(request.getId().toString());
    forumCommentModel.setStatus(request.getStatus());
    return this.editSubject(forumCommentModel);
  }

  /**
   * 查询所有的回帖
   * 
   * @return
   */
  public Page<ForumCommentModel> queryAllComment() {
    Map<String, Object> cond = new HashMap<>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("forumId", "");
    output.put("replyId", "");
    output.put("nickName", "");
    output.put("content", "");
    output.put("images", "");
    output.put("targeContent", "");
    output.put("status", "");
    output.put("createTime", "");
    return forumCommentDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据栏目级联查询话题
   * 
   * @param catId
   * @return
   */
  public Page<ForumCommentModel> cascadeQueryCourseByCatId(Integer forumId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("forumId", forumId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("status", "1");
    output.put("forumId", "1");
    output.put("nickName", "1");
    output.put("content", "1");
    output.put("images", "1");
    output.put("replyId", "1");
    output.put("targeId", "1");
    output.put("targeContent", "1");
    output.put("targeCommentId", "1");
    output.put("praiseNumber", "1");
    output.put("tag", "1");
    output.put("createTime", "1");
    output.put("operator", "1");
    output.put("operatorip", "1");
    output.put("tag", "1");
    output.put("operator", "1");
    output.put("operatorip", "1");
    output.put("type", "1");
    return forumCommentDao.cascadeQueryProduct(cond, output);
  }

  /**
   * 删除回帖
   * 
   * @param id
   * @return
   */
  public Boolean deleteComment(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return forumCommentDao.deleteEntity(cond);
  }


}
