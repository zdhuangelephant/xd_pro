package com.xiaodou.course.service.facade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.dao.forum.AuthorModelDao;
import com.xiaodou.course.dao.forum.ForumDao;
import com.xiaodou.course.dao.forum.ForumPraiseModelDao;
import com.xiaodou.course.dao.forum.UserScanRecordDao;
import com.xiaodou.course.enums.ForumType;
import com.xiaodou.course.model.forum.AuthorModel;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.forum.ForumPraiseModel;
import com.xiaodou.course.model.forum.UserScanRecordModel;
import com.xiaodou.course.web.request.forum.ShareForumListRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

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
  @Resource
  ForumPraiseModelDao forumPraiseModelDao;
  @Resource
  ForumDao forumDao;
  @Resource
  AuthorModelDao authorModelDao;
  @Resource
  UserScanRecordDao userScanRecordDao;

  @Override
  public ForumPraiseModel insertPraiseEntity(ForumPraiseModel forumPraiseModel) {
    return forumPraiseModelDao.addEntity(forumPraiseModel);
  }

  @Override
  public Integer queryPraiseNumber(Map<String, Object> inputArgument) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", inputArgument);
    return forumPraiseModelDao.queryPraiseNumber(cond);
  }

  @Override
  public List<ForumPraiseModel> queryPraiseList(Map<String, Object> inputArgument,
      Map<String, Object> outputField) {
    return forumPraiseModelDao.getList(inputArgument, outputField);
  }


  @Override
  public void updateForumEntity(Map<String, Object> input, ForumModel forumModel) {
    forumDao.updateEntity(input, forumModel);
  }

  @Override
  public int deletePraiseList(Map<String, Object> input) {
    return forumPraiseModelDao.deleteList(input);
  }

  @Override
  public AuthorModel getAuthorById(String authorId) {
    AuthorModel model = new AuthorModel();
    model.setId(authorId);
    return authorModelDao.findEntityById(model);
  }

  @Override
  public Page<ForumModel> getForumShareList(ShareForumListRequest request) {
    if (null == request) return null;
    Map<String, Object> cond = Maps.newHashMap();
    if (request.getTagList() != null && request.getTagList().size() > 0)
      cond.put("forumTagList", request.getTagList());
    if (request.getCreateTimeUpper() != null)
      cond.put("createTimeUpper", request.getCreateTimeUpper());
    cond.put("status", Constant.YES);
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", Sort.DESC.name());
    cond.put("sort", sort);
    cond.put("forumType", ForumType.SHARE.getCode());
    Map<String, Object> limit = Maps.newHashMap();
    limit.put("limitStart", Constant.LIMIT_START);
    limit.put("limitCount", Constant.LIMIT_COUNT);
    cond.put("limit", limit);
    Map<String, Object> output = Maps.newHashMap();
    CommUtil.getGeneralField(output, ForumModel.class);
    Page<ForumModel> forumList = forumDao.cascadeQueryForum(cond, output);
    addScanRecord(request.getUid(), request.getModule(), request.getTypeCode());
    return forumList;
  }

  private void addScanRecord(String userId, String module, String typeCode) {
    /**
     * 基于更新记录的实现方法,这里考虑可以通过新增的方式统计用户查看本页的记录,所以先使用新增的方式实现本方法,后续根据数据增量决定是否修改为更新方法
     * 
     * */
    UserScanRecordModel model = new UserScanRecordModel();
    model.setUserId(userId);
    model.setModule(module);
    model.setTypeCode(typeCode);
    model.setRecordDate(new Timestamp(System.currentTimeMillis()));
    userScanRecordDao.addEntity(model);
  }

  @Override
  public Integer countUnreadForumShare(String userId, String module, String typeCode,
      List<String> tagList, Timestamp createTimeLower) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("typeCode", typeCode);
    param.addInput("recordDateLower", createTimeLower);
    param.addOutput("recordDate", Constant.YES);
    param.addSort("recordDate", Sort.DESC);
    Page<UserScanRecordModel> scanRecord = userScanRecordDao.findEntityListByCond(param, null);
    if (null != scanRecord && null != scanRecord.getResult() && scanRecord.getResult().size() > 0) {
      UserScanRecordModel scanRecordModel = scanRecord.getResult().get(0);
      if (null != scanRecordModel) createTimeLower = scanRecordModel.getRecordDate();
    }
    Map<String, Object> cond = Maps.newHashMap();
    if (tagList != null && tagList.size() > 0) cond.put("forumTagList", tagList);
    if (createTimeLower != null) cond.put("createTimeLower", createTimeLower);
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", Sort.DESC.name());
    cond.put("sort", sort);
    cond.put("forumType", ForumType.SHARE.getCode());
    Map<String, Object> output = Maps.newHashMap();
    output.put("forumId", Constant.YES);
    Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
    if (null != page && null != page.getResult() && page.getResult().size() > 0) {
      return page.getResult().size();
    }
    return 0;
  }

}
