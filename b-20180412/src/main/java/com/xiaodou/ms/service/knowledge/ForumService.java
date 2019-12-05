package com.xiaodou.ms.service.knowledge;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.knowledge.AuthorDao;
import com.xiaodou.ms.dao.knowledge.ForumDao;
import com.xiaodou.ms.dao.knowledge.PushRecordDao;
import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.model.knowledge.ForumType;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.common.PushService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.web.request.knowledge.ForumRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name ForumService CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月7日
 * @description 知识分享service
 * @version 1.0
 */
@Service("forumService")
public class ForumService {

  @Resource
  ForumDao forumDao;
  @Resource
  AuthorDao authorDao;
  @Resource
  PushRecordDao pushRecordDao;
  @Resource
  RegionService regionService;
  @Resource
  PushRecordService pushRecordService;

  public Page<ForumModel> search(String forumTitle, String authorId, String forumType,
      String forumClassify, Integer pageNo) {
    if (null == pageNo) pageNo = 1;
    IQueryParam param = new QueryParam();
    Map<String, Object> outputMap = CommUtil.getAllField(ForumModel.class);
    CommUtil.getAllField(outputMap, AuthorModel.class);
    if (StringUtils.isNotBlank(forumTitle)) param.addInput("forumTitleLike", forumTitle);
    if (StringUtils.isNotBlank(authorId)) param.addInput("authorId", authorId);
    if (StringUtils.isNotBlank(forumType))
      param.addInput("forumType", forumType);
    else
      param.addInput("forumTypeList",
          Lists.newArrayList(ForumType.SHARE.getCode(), ForumType.OPENCOURSE.getCode()));
    if (StringUtils.isNotBlank(forumClassify)) param.addInput("forumClassify", forumClassify);
    param.addOutputs(outputMap);
    param.addSort("updateTime", Sort.DESC);
    Page<ForumModel> forumPage = new Page<ForumModel>();
    forumPage.setPageNo(pageNo);
    forumPage.setPageSize(XdmsConstant.PAGE_SIZE);
    forumPage = forumDao.cascadeFindListByCond(param, forumPage);
    if (null == forumPage) return null;
    List<RegionModel> regionList = regionService.queryAllRegion();
    if (null == regionList || regionList.isEmpty()) {
      return forumPage;
    }
    Map<String, String> regionMap = Maps.newHashMap();
    for (RegionModel region : regionList) {
      regionMap.put(region.getModule(), region.getModuleName());
    }
    for (ForumModel forum : forumPage.getResult()) {
      forum.setRegionName(regionMap.get(forum.getRegion()));
    }
    return forumPage;
  }

  public ForumModel findById(String forumId) {
    ForumModel forum = new ForumModel();
    forum.setForumId(forumId);
    return forumDao.cascadeFindById(forum);
  }

  public boolean deleteForum(String forumId) {
    ForumModel model = new ForumModel();
    model.setForumId(forumId);
    model = forumDao.findEntityById(model);
    if (null == model) return true;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("forumId", forumId);
    Boolean res = forumDao.deleteEntityByCond(cond);
    updateAuthorWorkNums(model.getAuthorId());
    return res;
  }

  public BaseResponse updateForum(ForumRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    ForumModel model = new ForumModel();
    model.setForumId(request.getForumId());
    model = forumDao.findEntityById(model);
    if (null == model) return response;
    String oldAuthodId = model.getAuthorId();
    model = request.initModel();
    model.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    forumDao.updateEntityById(model);
    if (!oldAuthodId.equals(request.getAuthorId())) {
      updateAuthorWorkNums(oldAuthodId);
      updateAuthorWorkNums(request.getAuthorId());
    }
    return response;
  }

  public BaseResponse addForum(ForumRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    ForumModel model = request.initModel();
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    forumDao.addEntity(model);
    updateAuthorWorkNums(request.getAuthorId());
    return response;
  }

  public BaseResponse pushForum(String forumId, String typeId, Integer counts, String tag) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);

    ForumModel forum = new ForumModel();
    forum.setForumId(forumId);
    forum = forumDao.cascadeFindById(forum);

    Message pushMessage = new Message();

    pushMessage.setMessageContent("小编推荐：" + forum.getForumTitle());
    pushMessage.setNoticeContent("小编推荐：" + forum.getForumTitle());
    pushMessage.setMessageType(MessageType.ALL);

    SpreadRange scope = SpreadRange.TAG;
    pushMessage.setScope(scope);
    pushMessage.setTarget(TargetType.ALL);
    Map<String, String> messageextras = new HashMap<String, String>();
    Map<String, String> noticeextras = new HashMap<String, String>();
    pushMessage.addOtherTags("courseCode", forum.getForumTag());
    if (typeId.equals("1")) {
      noticeextras.put("forumId", forum.getForumId());
      noticeextras.put("forumType", forum.getForumType());
      noticeextras.put("forumClassify", forum.getForumClassify());

      messageextras.put("forumId", forum.getForumId());
      messageextras.put("forumType", forum.getForumType());
      messageextras.put("forumClassify", forum.getForumClassify());

      PushUtil.setRetCode(noticeextras, "10409");
      PushUtil.setRetDesc(noticeextras, "已发布的资源全网推送");
      PushUtil.setRetCode(messageextras, "10409");
      PushUtil.setRetDesc(messageextras, "已发布的资源全网推送");
      forum.getForumType();

    } else if (typeId.equals("2")) {

      PushUtil.setRetCode(noticeextras, "10410");
      PushUtil.setRetDesc(noticeextras, "已发布的资源全网推送");
      PushUtil.setRetCode(messageextras, "10410");
      PushUtil.setRetDesc(messageextras, "已发布的资源全网推送");
    }

    pushMessage.setMessageextras(messageextras); // 消息参数
    pushMessage.setNoticeextras(noticeextras); // 通知参数

    PushService.toTagPush(response, pushMessage, tag);

    PushRecordModel pushRecordModel = new PushRecordModel();
    pushRecordModel.setTypeId(Integer.parseInt(typeId));
    pushRecordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    pushRecordModel.setIsPush(1 + counts);
    HashMap<String, Object> inputs = Maps.newHashMap();
    inputs.put("typeId", typeId);
    inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
    List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
    if (lists != null && lists.size() > 0) {
      PushRecordModel prm = lists.get(0);
      prm.setIsPush(prm.getIsPush() + 1);
      pushRecordDao.updateEntityById(prm);
    } else
      pushRecordDao.addEntity(pushRecordModel);

    return response;
  }



  private void updateAuthorWorkNums(String authorId) {
    if (StringUtils.isBlank(authorId)) return;
    try {
      Long authorIdL = Long.parseLong(authorId);
      AuthorModel author = new AuthorModel();
      author.setId(authorIdL);
      IQueryParam param = new QueryParam();
      param.addInput("authorId", authorId);
      param.addOutput("forumId", XdmsConstant.YES);
      Page<ForumModel> forumPage = forumDao.cascadeFindListByCond(param, null);
      if (null == forumPage || null == forumPage.getResult() || forumPage.getResult().size() == 0)
        author.setWorksNum(0);
      else
        author.setWorksNum(forumPage.getResult().size());
      authorDao.updateEntityById(author);
    } catch (Exception e) {}
  }

  public List<ForumModel> getNewResource() {

    List<ForumModel> _result = new ArrayList<>();
    long currentTimes = System.currentTimeMillis();

    IQueryParam param = new QueryParam();
    param.addOutput("forumId", "");
    param.addOutput("forumTag", "");
    param.addOutput("createTime", "");
    Page<ForumModel> results = forumDao.findEntityListByCond(param, null);
    if (results != null) {
      List<ForumModel> lists = results.getResult();
      for (ForumModel forumModel : lists) {

        long diffValue = currentTimes - forumModel.getCreateTime().getTime();
        if (diffValue <= DateUtil.DAY_MILLISECOND) {
          _result.add(forumModel);
        }
      }
    }
    return _result;
  }


}
