package com.xiaodou.forum.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.forum.model.forum.CommentForumModel;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumCategoryModel;
import com.xiaodou.forum.model.forum.ForumCommentModel;
import com.xiaodou.forum.model.forum.ForumModel;
import com.xiaodou.forum.model.forum.ForumPraiseModel;
import com.xiaodou.forum.model.forum.ForumRelateInfoModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;

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

  /** 话题分类 */

  void updateCatagoryEntity(Map<String, Object> input, ForumCategoryModel forumCategoryModel);

  ForumCategoryModel queryForumCategory(String catagoryId);

  List<ForumCategoryModel> queryCatagoryList(Map<String, Object> object, Map<String, Object> object2);

  void updateCategoryPeopleNumber(Long forumCategoryId, Integer peopleNumber);

  Integer queryCategoryPeopleNumber(Long forumCategoryId);

  /** 话题 */

  void insertForumEntity(ForumModel forumModel);

  void updateForumEntity(Map<String, Object> input, ForumModel forumModel);

  Integer queryForumNumber(Long catagoryId);

  List<ForumModel> queryForumList(Map<String, Object> queryCond, Map<String, Object> output);

  ForumUserModel queryForumUserById(String string);
  
  ForumUserModel queryForumUserByIdAndCategoryId(String string, String categoryId);

  List<ForumUserModel> queryForumUserListByCondNoPage(Map<String, Object> cond);

  void updateForumRepliesNumber(Integer forumId, Integer commentNumber);

  /** 评论 */

  ForumCommentModel insertCommentEntity(ForumCommentModel forumCommentModel);

  void updateCommontEntity(Map<String, Object> input, ForumCommentModel forumCommentModel);

  Integer queryCommentNumber(Integer forumId);

  List<ForumCommentModel> queryCommentList(Map<String, Object> queryCond, Map<String, Object> output);

  List<CommentForumModel> queryCommentInForumListByCondNoPage(Map<String, Object> cond);

  List<CommentUserModel> queryForumCommentByForumIdForHot(String forumId, int propertiesInt,
      Map<String, Object> commentListOutput);

  List<CommentUserModel> queryForumCommentByForumId(String forumId, String commentId, Integer size,
    Map<String, Object> commentListOutput);
  
  List<CommentUserModel> queryForumCommentByForumIdPull(String forumId, String commentId, Integer size,
      Map<String, Object> commentListOutput);

  /** 点赞 */

  List<ForumPraiseModel> queryPraiseList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  Integer queryPraiseNumber(Map<String, Object> inputArgument);

  ForumPraiseModel insertPraiseEntity(ForumPraiseModel forumPraiseModel);

  int deletePraiseList(Map<String, Object> input);

  /** 我的通知消息 */
  ForumRelateInfoModel insertForumRelateInfo(ForumRelateInfoModel model);
  
  void updateForumRelateInfo(ForumRelateInfoModel model);
  
  void updateMyRelateInfoIgnore(String uid);
  
  List<RelateInfoUserModel> queryRelationInfoListByCondNoPage(Map<String, Object> cond);
  
  Integer countRelationInfo(Map<String, Object> cond);
  
  ForumRelateInfoModel queryRelationInfoById(String commentId);
}
