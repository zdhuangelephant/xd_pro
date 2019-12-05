package com.xiaodou.course.service.facade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.xiaodou.course.model.forum.AuthorModel;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.forum.ForumPraiseModel;
import com.xiaodou.course.web.request.forum.ShareForumListRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.forum.service.facade.ForumServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 数据层访问门面接口
 * @version 1.0
 */
public interface ForumServiceFacade {

  /** 点赞 */
  List<ForumPraiseModel> queryPraiseList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  Integer queryPraiseNumber(Map<String, Object> inputArgument);

  ForumPraiseModel insertPraiseEntity(ForumPraiseModel forumPraiseModel);

  void updateForumEntity(Map<String, Object> input, ForumModel forumModel);

  int deletePraiseList(Map<String, Object> input);

  AuthorModel getAuthorById(String authorId);

  Page<ForumModel> getForumShareList(ShareForumListRequest request);

  Integer countUnreadForumShare(String userId, String module, String typeCode,
      List<String> tagList, Timestamp createTimeLower);
}
