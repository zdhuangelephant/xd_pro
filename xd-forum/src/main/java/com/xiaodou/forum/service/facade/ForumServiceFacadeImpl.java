package com.xiaodou.forum.service.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.forum.cache.ForumCategoryCache;
import com.xiaodou.forum.dao.forum.CascadeQueryForumDao;
import com.xiaodou.forum.dao.forum.ForumCategoryModelDao;
import com.xiaodou.forum.dao.forum.ForumCommentModelDao;
import com.xiaodou.forum.dao.forum.ForumModelDao;
import com.xiaodou.forum.dao.forum.ForumPraiseModelDao;
import com.xiaodou.forum.dao.forum.ForumRelateInfoModelDao;
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
 * @name @see com.xiaodou.forum.service.facade.ForumServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 数据层访问门面实现
 * @version 1.0
 */
@Service("forumServiceFacade")
public class ForumServiceFacadeImpl implements ForumServiceFacade {

  /** cascadeQueryForumDao 联合查询dao */
  @Resource
  CascadeQueryForumDao cascadeQueryForumDao;

  /** forumCategoryModelDao 话题分类dao */
  @Resource
  ForumCategoryModelDao forumCategoryModelDao;

  /** forumPraiseModelDao 话题点赞dao */
  @Resource
  ForumPraiseModelDao forumPraiseModelDao;

  // 话题Dao
  @Resource
  private ForumModelDao forumModelDao;

  // 评论Dao
  @Resource
  private ForumCommentModelDao forumCommentModelDao;

  // 话题分类缓存service
  @Resource
  ForumCategoryCache forumCategoryCache;

  @Resource
  ForumRelateInfoModelDao forumRelateInfoModelDao;

  @Override
  public ForumUserModel queryForumUserById(String id) {
    return cascadeQueryForumDao.queryForumUserById(id);
  }

  @Override
  public ForumUserModel queryForumUserByIdAndCategoryId(String id, String categoryId) {
    return cascadeQueryForumDao.queryForumUserByIdAndCategoryId(id, categoryId);
  }

  @Override
  public List<CommentForumModel> queryCommentInForumListByCondNoPage(Map<String, Object> cond) {
    return cascadeQueryForumDao.queryCommentInForumListByCondNoPage(cond);
  }

  @Override
  public ForumCategoryModel queryForumCategory(String catagoryId) {
    // 1、查询缓存
    String result = forumCategoryCache.getForumCategory(catagoryId);
    if (StringUtils.isNotBlank(result)) {
      return FastJsonUtil.fromJsons(result, new TypeReference<ForumCategoryModel>() {});
    }
    // 2、查询数据库
    return forumCategoryModelDao.queryOneById(catagoryId, null);
  }

  @Override
  public List<ForumCategoryModel> queryCatagoryList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    // 1、查询缓存
    String result = forumCategoryCache.getForumCategoryList();
    if (StringUtils.isNotBlank(result)) {
      return FastJsonUtil.fromJsons(result, new TypeReference<List<ForumCategoryModel>>() {});
    }
    // 2、查询数据库
    return forumCategoryModelDao.queryList(inputArgument, outputField);
  }

  @Override
  public List<CommentUserModel> queryForumCommentByForumIdForHot(String forumId, int propertiesInt,
      Map<String, Object> commentListOutput) {
    return cascadeQueryForumDao.queryForumCommentByForumIdForHot(forumId, propertiesInt,
        commentListOutput);
  }

  @Override
  public List<CommentUserModel> queryForumCommentByForumId(String forumId, String commentId,
      Integer size, Map<String, Object> commentListOutput) {
    return cascadeQueryForumDao.queryForumCommentByForumId(forumId, commentId, null, size,
        commentListOutput);
  }

  @Override
  public List<CommentUserModel> queryForumCommentByForumIdPull(String forumId, String commentId,
      Integer size, Map<String, Object> commentListOutput) {
    return cascadeQueryForumDao.queryForumCommentByForumId(forumId, null, commentId, size,
        commentListOutput);
  }

  @Override
  public List<ForumPraiseModel> queryPraiseList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return forumPraiseModelDao.queryList(inputArgument, outputField);
  }

  @Override
  public List<ForumUserModel> queryForumUserListByCondNoPage(Map<String, Object> cond) {
    return cascadeQueryForumDao.queryForumUserListByCondNoPage(cond);
  }

  @Override
  public List<ForumModel> queryForumList(Map<String, Object> queryCond, Map<String, Object> output) {
    return forumModelDao.queryList(queryCond, output);
  }

  @Override
  public void updateForumEntity(Map<String, Object> input, ForumModel forumModel) {
    forumModelDao.updateEntity(input, forumModel);
  }

  @Override
  public void updateCommontEntity(Map<String, Object> input, ForumCommentModel forumCommentModel) {
    forumCommentModelDao.updateEntity(input, forumCommentModel);
  }

  @Override
  public List<ForumCommentModel> queryCommentList(Map<String, Object> queryCond,
      Map<String, Object> output) {
    return forumCommentModelDao.queryList(queryCond, output);
  }

  @Override
  public ForumPraiseModel insertPraiseEntity(ForumPraiseModel forumPraiseModel) {
    return forumPraiseModelDao.addEntity(forumPraiseModel);
  }

  @Override
  public int deletePraiseList(Map<String, Object> input) {
    return forumPraiseModelDao.deleteList(input);
  }

  @Override
  public void updateCategoryPeopleNumber(Long forumCategoryId, Integer peopleNumber) {
    if (null != forumCategoryId && null != peopleNumber)
      forumCategoryModelDao.updatePeopleNumber(forumCategoryId, peopleNumber);
  }

  @Override
  public void updateForumRepliesNumber(Integer forumId, Integer commentNumber) {
    if (null != forumId && null != commentNumber) {
      forumModelDao.updateRepliesNumber(forumId, commentNumber);
    }
  }

  @Override
  public ForumCommentModel insertCommentEntity(ForumCommentModel forumCommentModel) {
    return forumCommentModelDao.addEntity(forumCommentModel);
  }

  @Override
  public void updateCatagoryEntity(Map<String, Object> input, ForumCategoryModel forumCategoryModel) {
    forumCategoryModelDao.updateEntity(input, forumCategoryModel);
  }

  @Override
  public void insertForumEntity(ForumModel forumModel) {
    forumModelDao.addEntity(forumModel);
  }

  @Override
  public Integer queryPraiseNumber(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    return forumPraiseModelDao.queryPraiseNumber(cond);
  }

  @Override
  public Integer queryCommentNumber(Integer forumId) {
    if (null == forumId) throw new RuntimeException("forumId不能爲空");
    return forumCommentModelDao.queryCommentNumber(forumId);
  }

  @Override
  public Integer queryCategoryPeopleNumber(Long forumCategoryId) {
    if (null == forumCategoryId) throw new RuntimeException("话题分类ID不能为空");
    HashSet<Object> peopleIdSets = Sets.newHashSet();
    peopleIdSets.addAll(cascadeQueryForumDao.queryCategoryPeopleId(forumCategoryId));
    return peopleIdSets.size();
  }

  @Override
  public Integer queryForumNumber(Long forumCategoryId) {
    if (null == forumCategoryId) throw new RuntimeException("forumCategoryId不能爲空");
    return forumModelDao.queryForumNumber(forumCategoryId);
  }

  @Override
  public ForumRelateInfoModel insertForumRelateInfo(ForumRelateInfoModel model) {
    return forumRelateInfoModelDao.addEntity(model);
  }

  @Override
  public List<RelateInfoUserModel> queryRelationInfoListByCondNoPage(Map<String, Object> cond) {
    return cascadeQueryForumDao.queryRelationInfoListByCondNoPage(cond);
  }

  @Override
  public ForumRelateInfoModel queryRelationInfoById(String commentId) {
    ForumRelateInfoModel model = new ForumRelateInfoModel();
    model.setId(Long.parseLong(commentId));
    return forumRelateInfoModelDao.findEntityById(model);
  }

  @Override
  public void updateForumRelateInfo(ForumRelateInfoModel model) {
    forumRelateInfoModelDao.updateEntity(model);
  }

  @Override
  public void updateMyRelateInfoIgnore(String uid) {
    forumRelateInfoModelDao.ignoreForumRelateInfos(uid);
  }

  @Override
  public Integer countRelationInfo(Map<String, Object> cond) {
    Integer countRelationInfo = forumRelateInfoModelDao.countRelationInfo(cond);
    if (null == countRelationInfo) return 0;
    return countRelationInfo;
  }

}
