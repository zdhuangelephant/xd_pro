package com.xiaodou.resources.service.forum.facade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.forum.CategoryRelationModel;
import com.xiaodou.resources.model.forum.CommentForumModel;
import com.xiaodou.resources.model.forum.CommentUserModel;
import com.xiaodou.resources.model.forum.ForumCategoryModel;
import com.xiaodou.resources.model.forum.ForumCommentModel;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.forum.ForumRelateInfoModel;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.model.forum.ModuleSlideModel;
import com.xiaodou.resources.model.forum.RelateInfoUserModel;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.resources.model.forum.ResourcesColumnistFollower;
import com.xiaodou.resources.model.forum.ResourcesColumnistVisit;
import com.xiaodou.resources.model.forum.SharePagePvModel;
import com.xiaodou.resources.model.reward.RewardRecord;
import com.xiaodou.resources.request.reward.H5OrderGiftRequest;
import com.xiaodou.resources.vo.forum.CommonCount;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

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

  /**
   * 话题
   * 
   * @return
   */

  ForumModel insertForumEntity(ForumModel forumModel);

  void updateForumEntity(Map<String, Object> input, ForumModel forumModel);

  Integer queryForumNumber(Map<String, Object> input);

  List<CommonCount> queryUsersForumNumber(Map<String, Object> input);

  List<ForumModel> queryForumList(Map<String, Object> queryCond, Map<String, Object> output);

  ForumUserModel queryForumUserById(String string);

  ForumUserModel queryForumUserByIdAndCategoryId(String string, String categoryId);

  List<ForumUserModel> queryForumUserListByCondNoPage(Map<String, Object> cond);

  void updateForumRepliesNumber(Long forumId, Integer commentNumber);

  /** 评论 */

  ForumCommentModel insertCommentEntity(ForumCommentModel forumCommentModel);

  void updateCommontEntity(Map<String, Object> input, ForumCommentModel forumCommentModel);

  Integer queryCommentNumber(Long forumId);

  Integer queryCommentNumber(String userId, String productId, String itemId);

  List<ForumCommentModel> queryCommentList(Map<String, Object> queryCond, Map<String, Object> output);

  List<CommentForumModel> queryCommentInForumListByCondNoPage(Map<String, Object> cond);

  List<CommentUserModel> queryForumCommentByForumIdForHot(String forumId, int propertiesInt,
      Map<String, Object> commentListOutput);

  List<CommentUserModel> queryForumCommentByForumId(String forumId, String commentId, Integer size,
      Map<String, Object> commentListOutput);

  List<CommentUserModel> queryForumCommentByForumIdPull(String forumId, String commentId,
      Integer size, Map<String, Object> commentListOutput);

  List<CommentUserModel> queryTalkCommentList(String itemId, String productId,
      String commentIdUpper, Integer size, Map<String, Object> commentListOutput);

  CommentUserModel queryTalkCommentByCId(String commentId);

  /** 点赞 */

  List<ForumPraiseModel> queryPraiseList(Map<String, Object> inputArgument,
      Map<String, Object> outputField);

  Integer queryPraiseNumber(Map<String, Object> inputArgument);

  List<CommonCount> queryUsersPraiseNumber(Map<String, Object> inputArgument);

  ForumPraiseModel insertPraiseEntity(ForumPraiseModel forumPraiseModel);

  int deletePraiseList(Map<String, Object> input);

  /** 我的通知消息 */
  ForumRelateInfoModel insertForumRelateInfo(ForumRelateInfoModel model);

  void updateForumRelateInfo(ForumRelateInfoModel model);

  void updateMyRelateInfoIgnore(String uid);

  List<RelateInfoUserModel> queryRelationInfoListByCondNoPage(Map<String, Object> cond);

  Integer countRelationInfo(Map<String, Object> cond);

  ForumRelateInfoModel queryRelationInfoById(String commentId);

  /** 话题分类与话题关系表 */
  void categoryRelationInsert(CategoryRelationModel model);

  /** 资源专栏 */

  ResourcesColumnist insertResourcesColumnist(ResourcesColumnist model);

  ResourcesColumnist queryDefaultResourcesColumnist(String uid);

  ResourcesColumnist queryResourcesColumnistById(String id, String userId);

  Page<ResourcesColumnist> queryResourcesColumnistList(String followerId, IQueryParam param,
      Page<ResourcesColumnist> page);

  Page<ResourcesColumnist> queryAllResourcesColumnistList(IQueryParam param,
      Page<ResourcesColumnist> page);

  void updateResourcesColumnistHeatById(String columnistModel);

  void updateResourcesColumnistById(ResourcesColumnist model);

  void deleteResourcesColumnistById(String id);

  Integer countResourcesColumnist(IQueryParam param);

  /** 资源专栏关注者 */
  ResourcesColumnistFollower followResourcesColumnist(ResourcesColumnistFollower model);

  void unfollowResourcesColumnist(ResourcesColumnistFollower model);

  Page<ResourcesColumnistFollower> queryResourcesFollowerList(IQueryParam param,
      Page<ResourcesColumnistFollower> page);

  Integer countResourcesColumnistFollower(IQueryParam param);
  
  /** 刷新专栏访问信息 */
  void refreshResourcesVisitInfo(ResourcesColumnistVisit model);

  /** 资源数量根据专栏 */
  Integer queryForumNumberByColumnId(String columnId);

  /** 分类-资源关联表删除 */
  void categoryRelationDelete(Map<String, Object> cond);

  /** 个人点赞的文章视频 */
  List<ForumUserModel> queryForumUserListByPariseNoPage(Map<String, Object> cond);

  /** 轮播图 */
  List<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  void deleteForumList(Map<String, Object> input);

  RewardRecord insertResourceReward(H5OrderGiftRequest pojo);

  RewardRecord quertResourceRewardById(String rewardId);

  List<RewardRecord> quertResourceRewardByResourceId(String recordId, String idUpper,
      String idLower, String size);

  Integer quertResourceRewardCountByResourceId(String recordId);

  void updateResourceRewardById(RewardRecord newecord);

  /* SharePagePvModel */
  SharePagePvModel querySharePagePvByResourceId(Long resourceId);

  boolean insertSharePagePv(SharePagePvModel sharePagePvModel);

  boolean updateSharePagePvByResourceId(Long resourceId, Long pv);

  Integer queryUnreadForumCount(String columnistId, Timestamp lastVisitTime);
}
