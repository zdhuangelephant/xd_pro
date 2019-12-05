package com.xiaodou.resources.service.forum.facade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.cache.forum.ForumCategoryCache;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.dao.forum.CascadeQueryForumDao;
import com.xiaodou.resources.dao.forum.CategoryRelationModelDao;
import com.xiaodou.resources.dao.forum.ForumCategoryModelDao;
import com.xiaodou.resources.dao.forum.ForumCommentModelDao;
import com.xiaodou.resources.dao.forum.ForumModelDao;
import com.xiaodou.resources.dao.forum.ForumPraiseModelDao;
import com.xiaodou.resources.dao.forum.ForumRelateInfoModelDao;
import com.xiaodou.resources.dao.forum.ModuleSlideDao;
import com.xiaodou.resources.dao.forum.ResourcesColumnistDao;
import com.xiaodou.resources.dao.forum.ResourcesColumnistFollowerDao;
import com.xiaodou.resources.dao.forum.ResourcesColumnistVisitDao;
import com.xiaodou.resources.dao.forum.SharePagePvModelDao;
import com.xiaodou.resources.dao.reward.RewardRecordDao;
import com.xiaodou.resources.enums.forum.WalletOper;
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
import com.xiaodou.resources.util.IDGenerator;
import com.xiaodou.resources.vo.forum.CommonCount;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.wallet.agent.enums.H5Type;
import com.xiaodou.wallet.agent.vo.Remark;
import com.xiaodou.wallet.agent.vo.Remark.WeiXinH5User;

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

  @Resource
  CategoryRelationModelDao categoryRelationModelDao;

  @Resource
  ResourcesColumnistDao resourcesColumnistDao;

  @Resource
  ResourcesColumnistFollowerDao resourcesColumnistFollowerDao;

  @Resource
  ResourcesColumnistVisitDao resourcesColumnistVisitDao;

  @Resource
  ModuleSlideDao moduleSlideDao;

  @Resource
  RewardRecordDao rewardRecordDao;

  @Resource
  SharePagePvModelDao sharePagePvModelDao;

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
    return cascadeQueryForumDao.queryForumCommentByCond(forumId, null, null, commentId, null, size,
        commentListOutput);
  }

  @Override
  public List<CommentUserModel> queryForumCommentByForumIdPull(String forumId, String commentId,
      Integer size, Map<String, Object> commentListOutput) {
    return cascadeQueryForumDao.queryForumCommentByCond(forumId, null, null, null, commentId, size,
        commentListOutput);
  }

  @Override
  public List<CommentUserModel> queryTalkCommentList(String itemId, String productId,
      String commentId, Integer size, Map<String, Object> commentListOutput) {
    return cascadeQueryForumDao.queryForumCommentByCond(null, itemId, productId, null, commentId,
        size, commentListOutput);
  }

  @Override
  public CommentUserModel queryTalkCommentByCId(String commentId) {
    List<CommentUserModel> list =
        cascadeQueryForumDao.queryForumCommentByCond(null, null, null, commentId, null, null,
            CommUtil.getAllField(CommentUserModel.class));
    return (null != list) ? list.get(0) : null;
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
  public void updateForumRepliesNumber(Long forumId, Integer commentNumber) {
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
  public ForumModel insertForumEntity(ForumModel forumModel) {
    return forumModelDao.addEntity(forumModel);
  }

  @Override
  public Integer queryPraiseNumber(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    return forumPraiseModelDao.queryPraiseNumber(cond);
  }

  @Override
  public Integer queryCommentNumber(Long forumId) {
    if (null == forumId) throw new RuntimeException("forumId不能爲空");
    return forumCommentModelDao.queryCommentNumber(forumId);
  }

  @Override
  public Integer queryCommentNumber(String userId, String productId, String itemId) {
    return forumCommentModelDao.queryCommentNumber(userId, productId, itemId);
  }

  @Override
  public Integer queryCategoryPeopleNumber(Long forumCategoryId) {
    if (null == forumCategoryId) throw new RuntimeException("话题分类ID不能为空");
    HashSet<Object> peopleIdSets = Sets.newHashSet();
    peopleIdSets.addAll(cascadeQueryForumDao.queryCategoryPeopleId(forumCategoryId));
    return peopleIdSets.size();
  }

  @Override
  public Integer queryForumNumber(Map<String, Object> inputArgument) {
    return forumModelDao.queryForumNumber(inputArgument);
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

  @Override
  public void categoryRelationInsert(CategoryRelationModel model) {
    categoryRelationModelDao.addEntity(model);
  }

  @Override
  public void categoryRelationDelete(Map<String, Object> cond) {
    categoryRelationModelDao.deleteList(cond);
  }

  @Override
  public ResourcesColumnist insertResourcesColumnist(ResourcesColumnist model) {
    return resourcesColumnistDao.addEntity(model);
  }

  @Override
  public ResourcesColumnist queryResourcesColumnistById(String id, String userId) {
    ResourcesColumnist model = new ResourcesColumnist();
    model.setId(id);
    model.setUserId(userId);
    return resourcesColumnistDao.findEntityById(model);
  }

  @Override
  public ResourcesColumnist queryDefaultResourcesColumnist(String uid) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", uid);
    param.addInput("followerId", uid);
    param.addInput("type", ForumConstant.COLUMIST_TYPE_DEFAULT);
    param.addOutput("id", Constant.YES);
    Page<ResourcesColumnist> page = resourcesColumnistDao.findEntityListByCond(param, null);
    if (null != page && null != page.getResult() && page.getResult().size() > 0)
      return page.getResult().get(0);
    return null;
  }

  @Override
  public Page<ResourcesColumnist> queryResourcesColumnistList(String followerId, IQueryParam param,
      Page<ResourcesColumnist> page) {
    param.addInput("followerId", followerId);
    return resourcesColumnistDao.findEntityListByCond(param, page);
  }

  @Override
  public Page<ResourcesColumnist> queryAllResourcesColumnistList(IQueryParam param,
      Page<ResourcesColumnist> page) {
    return resourcesColumnistDao.findEntityListByCond(param, page);
  }


  @Override
  public void updateResourcesColumnistById(ResourcesColumnist model) {
    resourcesColumnistDao.updateEntityById(model);
  }

  @Override
  public void deleteResourcesColumnistById(String id) {
    ResourcesColumnist model = new ResourcesColumnist();
    model.setId(id);
    resourcesColumnistDao.deleteEntityById(model);
  }

  @Override
  public Integer countResourcesColumnist(IQueryParam param) {
    return resourcesColumnistDao.findEntityCountByCond(param);
  }

  @Override
  public ResourcesColumnistFollower followResourcesColumnist(ResourcesColumnistFollower model) {
    ResourcesColumnistFollower follower = resourcesColumnistFollowerDao.addEntity(model);
    ResourcesColumnistVisit visit = new ResourcesColumnistVisit();
    visit.setColumnistId(model.getColumnistId());
    visit.setUserId(model.getUserId());
    visit.setModule(model.getModule());
    visit.setLastVisitTime(new Timestamp(System.currentTimeMillis()));
    resourcesColumnistVisitDao.addEntity(visit);
    return follower;
  }

  @Override
  public void unfollowResourcesColumnist(ResourcesColumnistFollower model) {
    if (StringUtils.isBlank(model.getModule(), model.getUserId(), model.getColumnistId()))
      throw new RuntimeException(String.format("信息不完整，无法删除专栏关注信息[model=%s]",
          FastJsonUtil.toJson(model)));
    Map<String, Object> params = Maps.newHashMap();
    CommUtil.transferFromVO2Map(params, model);
    resourcesColumnistFollowerDao.deleteEntityByCond(params);
    ResourcesColumnistVisit visit = new ResourcesColumnistVisit();
    visit.setColumnistId(model.getColumnistId());
    visit.setUserId(model.getUserId());
    visit.setModule(model.getModule());
    Map<String, Object> visitParams = Maps.newHashMap();
    CommUtil.transferFromVO2Map(visitParams, model);
    resourcesColumnistVisitDao.deleteEntityByCond(visitParams);
  }

  @Override
  public Page<ResourcesColumnistFollower> queryResourcesFollowerList(IQueryParam param,
      Page<ResourcesColumnistFollower> page) {
    return resourcesColumnistFollowerDao.findEntityListByCond(param, page);
  }

  @Override
  public Integer countResourcesColumnistFollower(IQueryParam param) {
    return resourcesColumnistFollowerDao.findEntityCountByCond(param);
  }

  @Override
  public void refreshResourcesVisitInfo(ResourcesColumnistVisit model) {
    Map<String, Object> input = Maps.newHashMap();
    CommUtil.transferFromVO2Map(input, model);
    model = new ResourcesColumnistVisit();
    model.setLastVisitTime(new Timestamp(System.currentTimeMillis()));
    resourcesColumnistVisitDao.updateEntityByCond(input, model);
  }

  @Override
  public Integer queryForumNumberByColumnId(String columnId) {
    return forumModelDao.queryForumNumberByColumnId(columnId);
  }

  @Override
  public List<ForumUserModel> queryForumUserListByPariseNoPage(Map<String, Object> cond) {
    return cascadeQueryForumDao.queryForumUserListByPariseNoPage(cond);
  }

  @Override
  public List<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(
      Map<String, Object> queryCond, Map<String, Object> output) {
    return moduleSlideDao.queryList(queryCond, output);
  }

  @Override
  public void deleteForumList(Map<String, Object> input) {
    forumModelDao.deleteList(input);
  }

  @Override
  public void updateResourcesColumnistHeatById(String columnistId) {
    Integer columnistHeat = 0;
    // 关注
    CACULATE_FOLLOW: {
      ResourcesColumnist columnist = queryResourcesColumnistById(columnistId, null);
      if (null == columnist) break CACULATE_FOLLOW;
      if (null != columnist.getColumnistHeat()) columnistHeat += columnist.getColumnistHeat() * 5;
    }
    // 资源
    CACULATE_RESOURCE: {
      Map<String, Object> queryCond = Maps.newHashMap(), output = Maps.newHashMap();
      queryCond.put("columnId", columnistId);
      output.put("repliesNumber", Constant.YES);
      output.put("praiseNumber", Constant.YES);
      List<ForumModel> forumList = queryForumList(queryCond, output);
      if (null == forumList || forumList.size() == 0) break CACULATE_RESOURCE;
      for (ForumModel forum : forumList) {
        if (null != forum.getRepliesNumber()) columnistHeat += forum.getRepliesNumber() * 5;
        if (null != forum.getPraiseNumber()) columnistHeat += forum.getPraiseNumber() * 3;

      }
    }
    if (columnistHeat > 0) {
      ResourcesColumnist columnist = new ResourcesColumnist();
      columnist.setId(columnistId);
      columnist.setColumnistHeat(columnistHeat);
      updateResourcesColumnistById(columnist);
    }
  }

  @Override
  public RewardRecord insertResourceReward(H5OrderGiftRequest pojo) {
    RewardRecord record = new RewardRecord();
    record.setId(IDGenerator.getSeqID());
    record.setModule(pojo.getModule());
    record.setUserId(pojo.getUid());
    record.setRecordId(pojo.getResourceId());
    record.setTargetUserId(pojo.getTargetUserId());
    record.setGiftType(pojo.getGiftType());
    record.setGiftMoney(pojo.getGiftMoney());
    record.setCreateTime(new Timestamp(System.currentTimeMillis()));
    record.setRate(pojo.getRate());
    Double rate = Double.valueOf(pojo.getRate());
    Double f = rate*(pojo.getGiftMoney());
    BigDecimal b = new BigDecimal(f);
    double poundage = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    record.setPoundage(poundage);
    if (StringUtils.isNotBlank(pojo.getH5Type())
        && H5Type.WEIXIN.getCode().equals(pojo.getH5Type())) {
      record.setH5Type(pojo.getH5Type());
      Remark remark = new Remark();
      WeiXinH5User weixinH5User = new WeiXinH5User();
      weixinH5User.setWeixinName(pojo.getWeixinName());
      weixinH5User.setWeixinPortrait(pojo.getWeixinPortrait());
      remark.setWeixinH5User(weixinH5User);
      record.setRemark(FastJsonUtil.toJson(remark));
    }
    record.setPayType(pojo.getPayType());
    rewardRecordDao.addEntity(record);
    return record;
  }

  @Override
  public RewardRecord quertResourceRewardById(String id) {
    RewardRecord record = new RewardRecord();
    record.setId(id);
    return rewardRecordDao.findEntityById(record);
  }

  @Override
  public void updateResourceRewardById(RewardRecord newecord) {
    rewardRecordDao.updateEntityById(newecord);
  }

  @Override
  public List<RewardRecord> quertResourceRewardByResourceId(String recordId, String idUpper,
      String idLower, String size) {
    IQueryParam param = new QueryParam();
    param.addInput("recordId", recordId);
    // param.addInput("operateType", ForumConstant.OPERATE_TYPE_PAYED);
    List<Integer> operateTypeList = Lists.newArrayList();
    operateTypeList.add(WalletOper.GiftPay.getCode());
    operateTypeList.add(WalletOper.ClearIncome.getCode());
    param.addInput("operateTypeList", operateTypeList);
    param.addSort("createTime", Sort.DESC);
    param.addInput("idUpper", idUpper);
    param.addInput("idLower", idLower);
    param.addOutputs(CommUtil.getAllField(RewardRecord.class));
    Page<RewardRecord> pageL = new Page<RewardRecord>();
    pageL.setPageSize(Integer.valueOf(size));
    Page<RewardRecord> page = rewardRecordDao.findEntityListByCond(param, pageL);
    if (null == page) return null;
    return page.getResult();
  }

  @Override
  public Integer quertResourceRewardCountByResourceId(String recordId) {
    IQueryParam param = new QueryParam();
    param.addInput("recordId", recordId);
    // param.addInput("operateType", ForumConstant.OPERATE_TYPE_PAYED);
    List<Integer> operateTypeList = Lists.newArrayList();
    operateTypeList.add(WalletOper.GiftPay.getCode());
    operateTypeList.add(WalletOper.ClearIncome.getCode());
    param.addInput("operateTypeList", operateTypeList);
    return rewardRecordDao.findEntityCountByCond(param);
  }

  @Override
  public List<CommonCount> queryUsersForumNumber(Map<String, Object> inputArgument) {
    // TODO Auto-generated method stub
    return forumModelDao.queryForumUsersNumber(inputArgument);
  }

  @Override
  public List<CommonCount> queryUsersPraiseNumber(Map<String, Object> inputArgument) {
    return forumPraiseModelDao.queryUsersPraiseNumber(inputArgument);
  }

  @Override
  public SharePagePvModel querySharePagePvByResourceId(Long resourceId) {
    IQueryParam param = new QueryParam();
    param.addInput("resourceId", resourceId);
    param.addOutputs(CommUtil.getAllField(SharePagePvModel.class));
    Page<SharePagePvModel> page = sharePagePvModelDao.findEntityListByCond(param, null);
    if (null != page && null != page.getResult() && page.getResult().size() > 0)
      return page.getResult().get(0);
    return null;
  }

  @Override
  public boolean insertSharePagePv(SharePagePvModel model) {
    return null != sharePagePvModelDao.addEntity(model);
  }

  @Override
  public boolean updateSharePagePvByResourceId(Long resourceId, Long pv) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("resourceId", resourceId);
    SharePagePvModel entity = new SharePagePvModel();
    entity.setPv(pv);
    return sharePagePvModelDao.updateEntityByCond(input, entity);
  }

  @Override
  public Integer queryUnreadForumCount(String columnistId, Timestamp lastVisitTime) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("columnId", columnistId);
    input.put("createTimeLower", lastVisitTime);
    return forumModelDao.queryForumNumber(input);
  }

}
