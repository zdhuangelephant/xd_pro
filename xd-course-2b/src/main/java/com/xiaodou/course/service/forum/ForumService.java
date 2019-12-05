package com.xiaodou.course.service.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.dao.forum.ForumDao;
import com.xiaodou.course.enums.ForumType;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.forum.ForumPraiseModel;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.web.request.forum.AuthorDetailRequest;
import com.xiaodou.course.web.request.forum.ForumIndexRequest;
import com.xiaodou.course.web.request.forum.ForumListRequest;
import com.xiaodou.course.web.request.forum.ShareForumListRequest;
import com.xiaodou.course.web.response.forum.ForumListResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;

@Service("forumService")
public class ForumService {
  @Resource
  ForumDao forumDao;
  @Resource
  QueueService queueService;
  @Resource
  ForumServiceFacade forumServiceFacade;

  public ForumModel getForumModelById(String forumId) {
    ForumModel forum = new ForumModel();
    forum.setForumId(forumId);
    forum = forumDao.findEntityById(forum);
    return forum;
  }

  // 加阅读数的逻辑
  public Forum getForumByIdAddReader(String forumId, String userId) {
    ForumModel forum = new ForumModel();
    forum.setForumId(forumId);
    forum = forumDao.findEntityById(forum);
    if (null != userId) {
      // 根据用户id和话题id查询话题点赞是否被点赞过
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("resourcesId", forumId);
      // 这里保证查询的commentID为0，即查询的为针对话题点赞的记录
      queryCond.put("commentId", "0");
      queryCond.put("operator", userId);
      List<ForumPraiseModel> praiseList = forumServiceFacade.queryPraiseList(queryCond, null);
      // 这里记录条目大于0就认为是点赞成功，可以抛出异常供分析，程序本身要做容错
      if (null != praiseList && praiseList.size() > 0) {
        if (praiseList.size() > 1)
          LoggerUtil.error("针对话题点赞数异常。大于1条点赞。" + FastJsonUtil.toJson(praiseList),
              new RuntimeException());
        forum.setIsPraised("1");
      } else {
        if (forum != null) {
          forum.setIsPraised("0");
        } else {
          return null;
        }
      }
    } else {
      if (forum != null) {
        forum.setIsPraised("0");
      } else {
        return null;
      }
    }
    addReaderNum(forumId, forum.getForumReaderNum());
    Forum f = new Forum(forum);
    return f;
  }


  public ForumListResponse getForumList(ForumListRequest request) {
    ForumListResponse response;
    try {
      response = new ForumListResponse(ResultType.SUCCESS);
      Map<String, Object> cond = Maps.newHashMap();
      if (!StringUtils.isBlank(request.getForumType())) {
        cond.put("forumType", request.getForumType());
      }
      cond.put("status", Constant.YES);
      Map<String, Object> limit = Maps.newHashMap();
      limit.put("limitStart", Constant.LIMIT_START);
      limit.put("limitCount", Constant.LIMIT_COUNT);
      Map<String, Object> sort = Maps.newHashMap();
      sort.put("updateTime", "DESC");
      if (request.getForumId() != null) {
        ForumModel forum = this.getForumModelById(request.getForumId());
        if (forum != null) {
          cond.put("updateTimeUpper", forum.getUpdateTime());
        } else {
          return new ForumListResponse(ForumResType.NULLFORUM);
        }
      }
      cond.put("sort", sort);
      cond.put("limit", limit);
      Map<String, Object> output = Maps.newHashMap();
      CommUtil.getGeneralField(output, ForumModel.class);
      Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
      for (ForumModel forum : page.getResult()) {
        Forum f = new Forum(forum);
        f.setForumContent(StringUtils.EMPTY);
        response.getForumList().add(f);
      }
    } catch (Exception e) {
      response = new ForumListResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public ForumListResponse getForumShareList(ForumListRequest request) {
    ForumListResponse response;
    try {
      response = new ForumListResponse(ResultType.SUCCESS);
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("status", Constant.YES);
      cond.put("forumType", ForumType.SHARE.getCode());
      Map<String, Object> sort = Maps.newHashMap();
      sort.put("createTime", "DESC");
      if (!StringUtils.isBlank(request.getForumTag())) {
        cond.put("forumTag", request.getForumTag());
      }
      if (!StringUtils.isBlank(request.getForumTime())) {
        cond.put("forumTime", request.getForumTime());
      }
      if (!StringUtils.isBlank(request.getForumId())) {
        ForumModel forum = this.getForumModelById(request.getForumId());
        if (forum != null) {
          cond.put("createTimeUpper", forum.getCreateTime());
        } else {
          return new ForumListResponse(ForumResType.NULLFORUM);
        }
      }
      Map<String, Object> limit = Maps.newHashMap();
      limit.put("limitStart", Constant.LIMIT_START);
      limit.put("limitCount", Constant.LIMIT_COUNT);
      cond.put("limit", limit);
      cond.put("sort", sort);
      Map<String, Object> output = Maps.newHashMap();
      CommUtil.getGeneralField(output, ForumModel.class);
      Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
      List<ForumModel> list = page.getResult();
      for (ForumModel forum : list) {
        Forum f = new Forum(forum);
        f.setForumContent(StringUtils.EMPTY);
        response.getForumList().add(f);
      }
    } catch (Exception e) {
      response = new ForumListResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public List<Forum> getUserForumList(AuthorDetailRequest request) {
    List<Forum> list = Lists.newArrayList();
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("authorId", request.getUid());
    cond.put("status", Constant.YES);
    if (!StringUtils.isBlank(request.getForumId())) {
      ForumModel forum = this.getForumModelById(request.getForumId());
      if (forum != null) {
        cond.put("createTimeUpper", forum.getCreateTime());
      } else {
        return Lists.newArrayList();
      }
    }
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", "DESC");
    cond.put("sort", sort);
    Map<String, Object> limit = Maps.newHashMap();
    limit.put("limitStart", Constant.LIMIT_START);
    limit.put("limitCount", Constant.LIMIT_COUNT);
    cond.put("limit", limit);
    Map<String, Object> output = Maps.newHashMap();
    CommUtil.getGeneralField(output, ForumModel.class);
    Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
    for (ForumModel forum : page.getResult()) {
      Forum f = new Forum(forum);
      f.setForumContent(StringUtils.EMPTY);
      list.add(f);
    }
    return list;
  }

  public Integer getForumListCount(AuthorDetailRequest request) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("authorId", request.getUid());
    cond.put("status", Constant.YES);
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", "DESC");
    cond.put("sort", sort);
    Integer count = forumDao.getForumListCount(cond);
    return count;
  }

  /**
   * 增加阅读次数
   */
  public void addReaderNum(String forumId, Integer readNum) {
    ForumModel model = new ForumModel();
    model.setForumId(forumId);
    model.setForumReaderNum(readNum);
    queueService.addReaderNum(model);
  }

  // 校園之聲
  public Forum getCampusVoice() {
    List<ForumModel> list;
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("forumType", ForumType.CAMPUSVOICE.getCode());
    cond.put("status", Constant.YES);
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", "DESC");
    cond.put("sort", sort);
    Map<String, Object> limit = Maps.newHashMap();
    limit.put("limitStart", Constant.LIMIT_START);
    limit.put("limitCount", 1);
    cond.put("limit", limit);
    Map<String, Object> output = Maps.newHashMap();
    CommUtil.getGeneralField(output, ForumModel.class);
    Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
    list = page.getResult();
    if (list != null && list.size() > 0) {
      return new Forum(list.get(0));
    }
    return new Forum();
  }

  // 首页发现获取公开课和知识分享
  public List<Forum> getAllForumList(ForumIndexRequest request) {
    List<Forum> list = Lists.newArrayList();
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("allForumType", ForumType.COURSEANDSHARE.getCode());
    cond.put("status", Constant.YES);
    if (!StringUtils.isBlank(request.getForumId())) {
      ForumModel forum = this.getForumModelById(request.getForumId());
      if (forum != null) {
        cond.put("createTimeUpper", forum.getCreateTime());
      } else {
        return Lists.newArrayList();
      }
    }
    Map<String, Object> sort = Maps.newHashMap();
    sort.put("createTime", "DESC");
    cond.put("sort", sort);
    Map<String, Object> limit = Maps.newHashMap();
    limit.put("limitStart", Constant.LIMIT_START);
    limit.put("limitCount", Constant.LIMIT_COUNT);
    cond.put("limit", limit);
    Map<String, Object> output = Maps.newHashMap();
    CommUtil.getGeneralField(output, ForumModel.class);
    Page<ForumModel> page = forumDao.cascadeQueryForum(cond, output);
    for (ForumModel forum : page.getResult()) {
      Forum f = new Forum(forum);
      f.setForumContent(StringUtils.EMPTY);
      list.add(f);
    }
    return list;
  }


  public ForumListResponse getShareForumListByTag(ShareForumListRequest request) {
    ForumListResponse response;
    try {
      response = new ForumListResponse(ResultType.SUCCESS);

      Page<ForumModel> page = forumServiceFacade.getForumShareList(request);
      for (ForumModel forum : page.getResult()) {
        Forum f = new Forum(forum);
        f.setForumContent(StringUtils.EMPTY);
        response.getForumList().add(f);
      }
    } catch (Exception e) {
      response = new ForumListResponse(ResultType.SYSFAIL);
    }
    return response;
  }


}
