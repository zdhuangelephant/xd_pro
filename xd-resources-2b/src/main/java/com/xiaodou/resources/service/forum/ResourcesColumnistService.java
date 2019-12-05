package com.xiaodou.resources.service.forum;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.async.toC.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.toC.model.AddAttentionMessage;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.resources.model.forum.ResourcesColumnistFollower;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.ColumnSearchRequest;
import com.xiaodou.resources.request.forum.ColumnistCountRequest;
import com.xiaodou.resources.request.forum.ColumnistDetailRequest;
import com.xiaodou.resources.request.forum.ColumnistListRequest;
import com.xiaodou.resources.request.forum.DeleteColumnistRequest;
import com.xiaodou.resources.request.forum.FollowColumnistRequest;
import com.xiaodou.resources.request.forum.ForumListByColumnRequest;
import com.xiaodou.resources.request.forum.ModifyColumnistRequest;
import com.xiaodou.resources.request.forum.PublishColumnistRequest;
import com.xiaodou.resources.request.forum.UnFollowColumnistRequest;
import com.xiaodou.resources.response.forum.ColumnistCountResponse;
import com.xiaodou.resources.response.forum.ColumnistDetailResponse;
import com.xiaodou.resources.response.forum.ColumnistListResponse;
import com.xiaodou.resources.response.forum.DeleteColumnistResponse;
import com.xiaodou.resources.response.forum.FollowColumnistResponse;
import com.xiaodou.resources.response.forum.ModifyColumnistResponse;
import com.xiaodou.resources.response.forum.PublishColumnistResponse;
import com.xiaodou.resources.response.forum.UnFollowColumnistResponse;
import com.xiaodou.resources.response.model.ColumnistMajorModel;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.IDGenerator;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ResourcesColumnistService CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月31日
 * @description 资源专栏service
 * @version 1.0
 */
@Service("resourcesColumnistService")
public class ResourcesColumnistService {

  @Resource
  ForumListService forumListService;

  @Resource
  ForumRecommendService forumRecommendService;

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  QueueService queueService;

  public PublishColumnistResponse publishDefaultColumnist(PublishColumnistRequest pojo) {
    PublishColumnistResponse response = new PublishColumnistResponse(ResultType.SUCCESS);
    ResourcesColumnist model = forumServiceFacade.queryDefaultResourcesColumnist(pojo.getUid());
    if (null != model && StringUtils.isNotBlank(model.getId())) return response;
    model = new ResourcesColumnist();
    model.setId(IDGenerator.getSeqID());
    model.setModule(pojo.getModule());
    model.setUserId(pojo.getUid());
    model.setTitle(pojo.getTitle());
    model.setCover(pojo.getCover());
    model.setInfo(pojo.getInfo());
    model.setType(ForumConstant.COLUMIST_TYPE_DEFAULT);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumServiceFacade.insertResourcesColumnist(model);
    return response;
  }

  public PublishColumnistResponse publishColumnist(PublishColumnistRequest pojo) {
    PublishColumnistResponse response = new PublishColumnistResponse(ResultType.SUCCESS);
    ResourcesColumnist model = new ResourcesColumnist();
    model.setId(IDGenerator.getSeqID());
    model.setModule(pojo.getModule());
    model.setUserId(pojo.getUid());
    model.setTitle(pojo.getTitle());
    model.setCover(pojo.getCover());
    model.setInfo(pojo.getInfo());
    model.setType(ForumConstant.COLUMIST_TYPE_USER);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumServiceFacade.insertResourcesColumnist(model);
    return response;
  }

  public ModifyColumnistResponse modifyColumnist(ModifyColumnistRequest pojo) {
    ModifyColumnistResponse response = new ModifyColumnistResponse(ResultType.SUCCESS);
    if (StringUtils.isBlank(pojo.getTitle(), pojo.getCover(), pojo.getInfo())) return response;
    ResourcesColumnist model = new ResourcesColumnist();
    model.setId(pojo.getColumnistId());
    model.setTitle(pojo.getTitle());
    model.setCover(pojo.getCover());
    model.setInfo(pojo.getInfo());
    forumServiceFacade.updateResourcesColumnistById(model);
    return response;
  }

  public DeleteColumnistResponse deleteColumnist(DeleteColumnistRequest pojo) {
    DeleteColumnistResponse response = new DeleteColumnistResponse(ResultType.SUCCESS);
    forumServiceFacade.deleteResourcesColumnistById(pojo.getColumnistId());
    return response;
  }

  public ColumnistListResponse columnistList(ColumnistListRequest pojo) {
    ColumnistListResponse response = new ColumnistListResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("module", pojo.getModule());
    param.addInput("userId", pojo.getUid());
    if (StringUtils.isNotBlank(pojo.getLastColumnistId()))
      param.addInput("idLower", pojo.getLastColumnistId());
    param.addSort("id", Sort.ASC);
    param.addLimitStart(Constant.LIMIT_START);
    param.addLimitCount(Constant.LIMIT_COUNT);
    param.addOutputs(CommUtil.getAllField(ResourcesColumnist.class));
    Page<ResourcesColumnist> columnistPage =
        forumServiceFacade.queryResourcesColumnistList(pojo.getUid(), param, null);
    if (null == columnistPage || null == columnistPage.getResult()
        || columnistPage.getResult().size() == 0) return response;
    for (ResourcesColumnist columnist : columnistPage.getResult()) {
      response.getColumnistList().add(new ColumnistMajorModel(columnist));
    }
    return response;
  }

  public ColumnistListResponse heatColumnistList(ColumnistListRequest pojo) {
    ColumnistListResponse response = new ColumnistListResponse(ResultType.SUCCESS);

    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    param.addOutput("columnistId", Constant.YES);
    Page<ResourcesColumnistFollower> followerPage =
        forumServiceFacade.queryResourcesFollowerList(param, null);
    List<String> columnistIdList = Lists.newArrayList();
    if (null != followerPage && null != followerPage.getResult()
        && followerPage.getResult().size() > 0) {
      for (ResourcesColumnistFollower follower : followerPage.getResult())
        columnistIdList.add(follower.getColumnistId());
    }

    param = new QueryParam();
    param.addInput("module", pojo.getModule());
    if (StringUtils.isNotBlank(pojo.getLastColumnistId()))
      param.addInput("idLower", pojo.getLastColumnistId());
    param.addInput("exceptUserId", pojo.getUid());
    if (columnistIdList.size() > 0) param.addInput("exceptIdList", columnistIdList);
    param.addInput("type", ForumConstant.COLUMIST_TYPE_USER);
    param.addSort("id", Sort.ASC);
    param.addSort("columnistHeat", Sort.DESC);
    param.addLimitStart(Constant.LIMIT_START);
    param.addLimitCount(Constant.LIMIT_COUNT);
    param.addOutputs(CommUtil.getAllField(ResourcesColumnist.class));
    Page<ResourcesColumnist> columnistPage =
        forumServiceFacade.queryResourcesColumnistList(pojo.getUid(), param, null);
    if (null == columnistPage || null == columnistPage.getResult()
        || columnistPage.getResult().size() == 0) return response;
    for (ResourcesColumnist columnist : columnistPage.getResult()) {
      response.getColumnistList().add(new ColumnistMajorModel(columnist));
    }
    return response;
  }

  public ColumnistCountResponse columnistCount(BaseRequest pojo) {
    ColumnistCountResponse response = new ColumnistCountResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    response.setColumnistCount(forumServiceFacade.countResourcesColumnist(param));
    return response;
  }

  public ColumnistListResponse followColumnistList(ColumnistListRequest pojo) {
    ColumnistListResponse response = new ColumnistListResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    if (StringUtils.isNotBlank(pojo.getLastColumnistId()))
      param.addInput("columnistIdLower", pojo.getLastColumnistId());
    param.addOutput("columnistId", Constant.YES);
    param.addSort("columnistId", Sort.ASC);
    param.addLimitStart(Constant.LIMIT_START);
    param.addLimitCount(Constant.LIMIT_COUNT);
    Page<ResourcesColumnistFollower> followerPage =
        forumServiceFacade.queryResourcesFollowerList(param, null);
    if (null == followerPage || null == followerPage.getResult()
        || followerPage.getResult().size() == 0) return response;
    List<String> columnistIdList = Lists.newArrayList();
    for (ResourcesColumnistFollower follower : followerPage.getResult())
      columnistIdList.add(follower.getColumnistId());
    param = new QueryParam();
    param.addInput("idList", columnistIdList);
    param.addOutputs(CommUtil.getAllField(ResourcesColumnist.class));
    Page<ResourcesColumnist> columnistPage =
        forumServiceFacade.queryResourcesColumnistList(pojo.getUid(), param, null);
    if (null == columnistPage || null == columnistPage.getResult()
        || columnistPage.getResult().size() == 0) return response;
    for (ResourcesColumnist columnist : columnistPage.getResult()) {
      response.getColumnistList().add(new ColumnistMajorModel(columnist));
    }
    return response;
  }

  public ColumnistCountResponse followColumnistCount(ColumnistCountRequest pojo) {
    ColumnistCountResponse response = new ColumnistCountResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    param.addOutput("columnistId", Constant.YES);
    param.addSort("columnistId", Sort.ASC);
    param.addLimitStart(Constant.LIMIT_START);
    param.addLimitCount(Constant.LIMIT_COUNT);
    Page<ResourcesColumnistFollower> followerPage =
        forumServiceFacade.queryResourcesFollowerList(param, null);
    if (null == followerPage || null == followerPage.getResult()
        || followerPage.getResult().size() == 0) return response;
    List<String> columnistIdList = Lists.newArrayList();
    for (ResourcesColumnistFollower follower : followerPage.getResult())
      columnistIdList.add(follower.getColumnistId());
    param = new QueryParam();
    param.addInput("idList", columnistIdList);
    response.setColumnistCount(forumServiceFacade.countResourcesColumnist(param));
    return response;
  }

  public ColumnistDetailResponse columnistDetail(ColumnistDetailRequest pojo) {
    ColumnistDetailResponse response = new ColumnistDetailResponse(ResultType.SUCCESS);
    ResourcesColumnist columnist =
        forumServiceFacade.queryResourcesColumnistById(pojo.getColumnistId(), pojo.getUid());
    if (null == columnist) return new ColumnistDetailResponse(ForumResType.NULLFORUM);
    ColumnistMajorModel columnistModel = new ColumnistMajorModel(columnist);
    if (pojo.getUid().equals(columnist.getUserId())) columnistModel.setCanFollow(Constant.NO);
    response.setColumnist(columnistModel);
    ForumListByColumnRequest request = new ForumListByColumnRequest();
    if (StringUtils.isNotBlank(pojo.getUid())) request.setUid(pojo.getUid());
    request.setModule(pojo.getModule());
    request.setColumnId(pojo.getColumnistId());
    response.setNewForumList(forumListService.queryForumsByColumnId(request).getList());
    response.setHotForumList(forumRecommendService.getRecommendListByColumnId(request).getList());
    IQueryParam param = new QueryParam();
    param.addInput("columnistId", pojo.getColumnistId());
    param.addInput("module", pojo.getModule());
    param.addOutput("userId", Constant.YES);
    Page<ResourcesColumnistFollower> followerPage =
        forumServiceFacade.queryResourcesFollowerList(param, null);
    if (null == followerPage || null == followerPage.getResult()
        || followerPage.getResult().size() == 0) return response;
    for (ResourcesColumnistFollower follower : followerPage.getResult())
      response.getFollowerList().add(follower.getUserId());
    return response;
  }

  public FollowColumnistResponse followColumnist(FollowColumnistRequest pojo) {
    if (checkOwner(pojo)) {
      return new FollowColumnistResponse(ForumResType.OWNERCANTFOLLOWCULUMNIST);
    }
    if (checkFollower(pojo)) {
      return new FollowColumnistResponse(ForumResType.HASFOLLOWCULUMNIST);
    }
    ResourcesColumnistFollower follower = new ResourcesColumnistFollower();
    follower.setId(IDGenerator.getSeqID());
    follower.setUserId(pojo.getUid());
    follower.setModule(pojo.getModule());
    follower.setColumnistId(pojo.getColumnistId());
    follower.setFollowTime(new Timestamp(System.currentTimeMillis()));
    forumServiceFacade.followResourcesColumnist(follower);
    ResourcesColumnist columnist =
        forumServiceFacade.queryResourcesColumnistById(pojo.getColumnistId(), pojo.getUid());
    if (columnist != null) {
      AddAttentionMessage message = new AddAttentionMessage();
      message.setSrcUid(pojo.getUid());
      message.setToUid(columnist.getUserId());
      message.setModule(pojo.getModule());
      message.setRetCode(AsyncResInfo.AddColumnistAttention.getRetCode());
      message.setRetDesc(String.format(AsyncResInfo.AddColumnistAttention.getRetDesc(),
          columnist.getTitle()));
      message.setMessageBody(String.format(AsyncResInfo.AddColumnistAttention.getMesTmp(),
          columnist.getTitle()));
      message.addCallBackContent("columnistId", columnist.getId());
      message.addCallBackContent("name", columnist.getTitle());
      message.send();
    }
    queueService.refreshFollowerNum(pojo.getColumnistId());
    return new FollowColumnistResponse(ResultType.SUCCESS);
  }

  public UnFollowColumnistResponse unfollowColumnist(UnFollowColumnistRequest pojo) {
    if (checkOwner(pojo)) {
      return new UnFollowColumnistResponse(ForumResType.OWNERCANTFOLLOWCULUMNIST);
    }
    if (checkFollower(pojo)) {
      ResourcesColumnistFollower follower = new ResourcesColumnistFollower();
      follower.setUserId(pojo.getUid());
      follower.setModule(pojo.getModule());
      follower.setColumnistId(pojo.getColumnistId());
      forumServiceFacade.unfollowResourcesColumnist(follower);
      queueService.refreshFollowerNum(pojo.getColumnistId());
      return new UnFollowColumnistResponse(ResultType.SUCCESS);
    }
    return new UnFollowColumnistResponse(ForumResType.HASNOTFOLLOWCULUMNIST);
  }

  private boolean checkOwner(FollowColumnistRequest pojo) {
    IQueryParam param = new QueryParam();
    param.addInput("id", pojo.getColumnistId());
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    return forumServiceFacade.countResourcesColumnist(param) > 0;
  }

  private boolean checkFollower(FollowColumnistRequest pojo) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    param.addInput("columnistId", pojo.getColumnistId());
    return forumServiceFacade.countResourcesColumnistFollower(param) > 0;
  }

  public ColumnistListResponse columnistSearch(ColumnSearchRequest pojo) {
    ColumnistListResponse response = new ColumnistListResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("module", pojo.getModule());
    param.addInput("titleLike", pojo.getTitle());
    if (StringUtils.isNotBlank(pojo.getLastColumnistId()))
      param.addInput("idLower", pojo.getLastColumnistId());
    param.addSort("id", Sort.ASC);
    param.addLimitStart(Constant.LIMIT_START);
    param.addLimitCount(Constant.LIMIT_COUNT);
    param.addOutputs(CommUtil.getAllField(ResourcesColumnist.class));
    Page<ResourcesColumnist> columnistPage =
        forumServiceFacade.queryResourcesColumnistList(pojo.getUid(), param, null);
    if (null == columnistPage || null == columnistPage.getResult()
        || columnistPage.getResult().size() == 0) return response;
    for (ResourcesColumnist columnist : columnistPage.getResult()) {
      response.getColumnistList().add(new ColumnistMajorModel(columnist));
    }
    return response;
  }
}
