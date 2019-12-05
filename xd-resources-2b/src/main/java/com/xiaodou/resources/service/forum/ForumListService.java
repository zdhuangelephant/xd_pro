package com.xiaodou.resources.service.forum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.dao.search.ResourcesDao;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.ForumCategoryModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.model.forum.ResourcesColumnistVisit;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.AnyCountRequest;
import com.xiaodou.resources.request.forum.ForumListByColumnRequest;
import com.xiaodou.resources.request.forum.ForumListRequest;
import com.xiaodou.resources.request.forum.ForumListSearchRequest;
import com.xiaodou.resources.request.forum.MyForumRequest;
import com.xiaodou.resources.request.forum.MyPraiseRequest;
import com.xiaodou.resources.request.forum.ResourcesListDynamicRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.forum.CommonCountListResponse;
import com.xiaodou.resources.response.forum.ForumCategoryResponse;
import com.xiaodou.resources.response.forum.ForumListResponse;
import com.xiaodou.resources.response.forum.MyForumResponse;
import com.xiaodou.resources.response.forum.SumResponse;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.FileUtils;
import com.xiaodou.resources.util.ForumUtil;
import com.xiaodou.resources.vo.forum.CommonCount;
import com.xiaodou.resources.vo.forum.Forum;
import com.xiaodou.resources.vo.search.ResourcesForEs;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题列表服务
 * 
 * @author weichao.zhai
 * 
 */
@Service
public class ForumListService {

  @Resource
  ForumServiceFacade forumServiceFacade;
  @Resource
  ResourcesDao resourcesDao;

  /**
   * 
   * 查询话题列表 满足需求： 1. 查询话题列表的上拉动作和下拉动作 2. 上拉动作－要求不该改变初始话题排序 3. 下拉动作－根据updateTime排序，为每个访问者生成排序
   * 
   * @param request
   * @return
   */
  public ForumListResponse resourcesDynamic(ResourcesListDynamicRequest request) {
    if (StringUtils.isBlank(request.getResourcesId()))
      return queryForumsForDrop(request.getUid(), null, new Integer(request.getSize()),
          request.getIds(), null, request.getResourcesType());
    return queryForumsForPull(request.getUid(), null, new Integer(request.getSize()),
        request.getIds(), request.getResourcesId(), null, request.getResourcesType());
  }

  /**
   * 
   * 查询话题列表 满足需求： 1. 查询话题列表的上拉动作和下拉动作 2. 上拉动作－要求不该改变初始话题排序 3. 下拉动作－根据updateTime排序，为每个访问者生成排序
   * 
   * @param request
   * @return
   */
  public ForumListResponse queryForums(ForumListRequest request) {
    if (null == forumServiceFacade.queryForumCategory(request.getClassificationId()))
      return new ForumListResponse(ForumResType.NULLCATAGORY);
    if (StringUtils.isBlank(request.getResourcesId()))
      return queryForumsForDrop(null, request.getClassificationId(),
          new Integer(request.getSize()), null, null, null);
    return queryForumsForPull(null, request.getClassificationId(), new Integer(request.getSize()),
        null, request.getResourcesId(), null, null);

  }

  /**
   * 
   * 根据栏目查询话题列表 满足需求： 1. 查询话题列表的上拉动作和下拉动作 2. 上拉动作－要求不该改变初始话题排序 3. 下拉动作－根据updateTime排序，为每个访问者生成排序
   * 
   * @param request
   * @return
   */
  public ForumListResponse queryForumsByColumnId(ForumListByColumnRequest request) {
    if (StringUtils.isBlank(request.getResourcesId()))
      return queryForumsForDrop(request.getUid(), null, new Integer(request.getSize()), null,
          request.getColumnId(), null);
    ResourcesColumnistVisit visit = new ResourcesColumnistVisit();
    visit.setColumnistId(request.getColumnId());
    visit.setUserId(request.getUid());
    visit.setModule(request.getModule());
    forumServiceFacade.refreshResourcesVisitInfo(visit);
    return queryForumsForPull(request.getUid(), null, request.getSize(), null,
        request.getResourcesId(), request.getColumnId(), null);

  }

  /**
   * 
   * 查询话题列表－下拉操作
   * 
   * @param classificationId －分类ID
   * @param size －一页数目
   * @param deviceId －设备号
   * @param forumId －最后一个话题ID
   * @return
   */
  public ForumListResponse queryForumsForPull(String userId, String classificationId, int size,
      String ids, String forumId, String columnId, String resourcesType) {

    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    /**
     * 1. 查询设置话题分类
     */
    if (!StringUtils.isBlank(classificationId)) {
      ForumCategoryModel category = forumServiceFacade.queryForumCategory(classificationId);
      response.setCategory(new ForumCategoryResponse(category));
    }
    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(forumId);
    if (null == forumUserModel || null == forumUserModel.getUpdateTime())
      return new ForumListResponse(ForumResType.NULLFORUM);
    List<Forum> list =
        query(userId, classificationId, ForumConstant.NotTopForum, size, ids,
            forumUserModel.getUpdateTime(), columnId, null);

    response.setList(list);
    return response;

  }

  /**
   * 
   * 上拉操作或初次进入话题列表页
   * 
   * @param classificationId -分类ID
   * @param size －每页数目
   * @param deviceId －设备号
   * @return
   */
  public ForumListResponse queryForumsForDrop(String userId, String classificationId, int size,
      String ids, String columnId, String resourcesType) {
    ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);
    /**
     * 1. 查询设置话题分类
     */
    if (!StringUtils.isBlank(classificationId)) {
      ForumCategoryModel category = forumServiceFacade.queryForumCategory(classificationId);
      reponse.setCategory(new ForumCategoryResponse(category));
    }
    /**
     * 2. 查询话题列表库
     */
    List<Forum> forums =
        query(userId, classificationId, ForumConstant.NotTopForum, size, ids, null, columnId,
            resourcesType);
    reponse.setList(forums);
    if (forums.size() == 0) return reponse;

    /**
     * 3. 去掉多余话题
     */
    if (forums.size() <= size) return reponse;
    reponse.setList(forums.subList(0, size));
    return reponse;
  }

  /**
   * 
   * 查询资源列表
   * 
   * @param classificationId
   * @param top
   * @param size
   * @param userids
   * @param timestamp
   * @return
   */
  private List<Forum> query(String userId, String classificationId, int top, int size,
      String userids, Timestamp timestamp, String columnId, String resourcesType) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    if (!StringUtils.isBlank(classificationId)) {
      input.put("categoryId", classificationId);
    }
    if (!StringUtils.isBlank(userId)) {
      input.put("userId", userId);
    }
    if (!StringUtils.isBlank(columnId)) {
      input.put("columnId", columnId);
    }
    if (StringUtils.isNotBlank(userids)) {
      List<String> idList = Arrays.asList(userids.split(","));
      input.put("userids", idList);
      if (StringUtils.isNotBlank(resourcesType)) {
        input.put("digest", resourcesType);
      }
    } else {
      input.put("digestSearch", 0);
    }
    cond.put("input", input);
    if (null != timestamp) input.put("updateTimeUpper", DateUtil.SDF_FULL.format(timestamp));
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("updateTime", "DESC");
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getForumListOutput());
    cond.put("limitCount", size);
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    List<Forum> forumsList = new ArrayList<Forum>();
    if (null != forums && forums.size() > 0) {
      for (ForumUserModel model : forums) {
        try {
          Forum forum = new Forum(model);
          forum.setContent(null);
          forumsList.add(forum);
        } catch (Exception e) {
          LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
          continue;
        }
      }
    }
    return getAllList(forumsList);
  }

  /**
   * 我发布的话题列表
   * 
   * @param request
   * @return
   * @throws Exception
   */
  public MyForumResponse getMyForum(MyForumRequest request) throws Exception {

    MyForumResponse response = new MyForumResponse(ResultType.SUCCESS);
    List<Forum> list =
        queryMyForum(request.getUid(), request.getSize(), null, request.getResourcesId(),
            request.getResourcesType());
    response.setList(list);
    return response;
  }

  /**
   * 我发布的话题列表
   * 
   * @param request
   * @return
   * @throws Exception
   */
  private List<Forum> queryMyForum(String userId, int size, String ids, String forumId, String type) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("publisherId", userId);
    if (type.equals("0")) {
      input.put("digest", 0);
    } else if (type.equals("1")) {
      input.put("digest", 1);
    } else if (type.equals("2")) {
      input.put("digest", 2);
    } else {
      input.put("digestSearch", 0);
    }
    if (forumId == null || "".equals(forumId) || "0".equals(forumId)) {

    } else {
      input.put("forumId", forumId);
    }
    if (StringUtils.isNotBlank(ids)) {
      input.put("ids", ids);
    } else {
      Map<String, Object> sort = new HashMap<String, Object>();
      sort.put("createTime", "DESC");
      cond.put("sort", sort);
    }
    cond.put("input", input);
    cond.put("output", ForumUtil.getMyForumListOutput());
    cond.put("limitCount", size);
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);

    List<Forum> topForums = new ArrayList<Forum>();
    for (ForumUserModel model : forums) {
      try {
        Forum forum = new Forum(model);
        forum.setContent(null);
        topForums.add(forum);
      } catch (Exception e) {
        LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
        continue;
      }
    }

    return getAllList(topForums);
  }

  /**
   * 
   * 上拉操作或初次进入话题列表页
   * 
   * @param name -文章名称
   * @param size －每页数目
   * @param deviceId －设备号
   * @return
   */
  public ForumListResponse queryForumListSearch(String userId, String name, int size,
      String deviceId) {
    ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);
    /**
     * 1. 查询话题列表库
     */
    List<Forum> forums =
        query(userId, name, ForumConstant.NotTopForum,
            FileUtils.FORUM_PROP.getPropertiesInt("forum.list.init.nottop.size"), null, null, null,
            null);
    reponse.setList(forums);
    if (forums.size() == 0) return reponse;

    /**
     * 2. 去掉多余话题
     */
    if (forums.size() <= size) return reponse;
    reponse.setList(forums.subList(0, size));
    return reponse;
  }

  /************************ 查询文章 *************************/
  /**
   * 
   * 查询文章
   * 
   * @param request
   * @return
   */
  public ForumListResponse search(ForumListSearchRequest request) {
    if (StringUtils.isBlank(request.getResourcesId()))
      return queryForumsByName(null, request.getName(), new Integer(request.getSize()));
    return queryForumsByNameForPull(null, request.getName(), new Integer(request.getSize()),
        request.getResourcesId());
  }

  public ForumListResponse queryForumsByNameForPull(String userId, String name, int size,
      String resourcesId) {
    ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);
    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(resourcesId);
    if (null == forumUserModel || null == forumUserModel.getUpdateTime())
      return new ForumListResponse(ForumResType.NULLFORUM);
    List<Forum> forums =
        searchByName(userId, name,
            FileUtils.FORUM_PROP.getPropertiesInt("forum.list.init.nottop.size"),
            forumUserModel.getUpdateTime());
    reponse.setList(forums);
    if (forums.size() == 0) return reponse;
    if (forums.size() <= size) return reponse;
    reponse.setList(forums.subList(0, size));
    return reponse;
  }

  /*	*//**
   * 
   * 文章列表下拉
   * 
   * @param Administrator
   * @param classificationId -分类ID
   * @param size －每页数目
   * @param deviceId －设备号
   * @return
   */
  /*
   * public ForumListResponse queryForumsByNameForPull(String userId, String name, int size, String
   * resourcesId) { ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);
   * ForumUserModel forumUserModel = forumServiceFacade .queryForumUserById(resourcesId); if (null
   * == forumUserModel || null == forumUserModel.getUpdateTime()) return new
   * ForumListResponse(ForumResType.NULLFORUM); List <ResourcesForEs>list=new
   * ArrayList<ResourcesForEs>(); Map <String, String> query =new HashMap <String, String>();
   * query.put("title",name); Map <String, SortType>sort =new HashMap <String, SortType>();
   * sort.put("createTime", SortType.DESC); List<Filter> filterList =new ArrayList<Filter> ();
   * Filter f=new Filter(); f.setColumn("createTime");
   * f.setData(forumUserModel.getCreateTime().toString()); f.setType(FilterType.TO);
   * filterList.add(f); list=resourcesDao.search(query, filterList,sort,1,size+1); List<Forum>
   * forumList=createForumList(list); reponse.setList(forumList); return reponse; }
   */
  public ForumListResponse queryForumsByName(String userId, String name, int size) {
    ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);
    List<Forum> forums =
        searchByName(userId, name,
            FileUtils.FORUM_PROP.getPropertiesInt("forum.list.init.nottop.size"), null);
    reponse.setList(forums);

    if (forums.size() == 0) return reponse;
    if (forums.size() <= size) return reponse;
    reponse.setList(forums.subList(0, size));
    return reponse;
  }


  /**
   * 
   * 文章列表
   * 
   * @param Administrator
   * @param classificationId -分类ID
   * @param size －每页数目
   * @param deviceId －设备号
   * @return
   */
  /*
   * public ForumListResponse queryForumsByName(String userId, String name, int size) {
   * ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS); QueryBuilder query =
   * QueryBuilders.termQuery("title", name); List<FieldSortBuilder> sortBuilders = new
   * ArrayList<FieldSortBuilder>();
   * sortBuilders.add(SortBuilders.fieldSort("createTime").order(SortOrder.DESC)); List
   * <ResourcesForEs>list=new ArrayList<ResourcesForEs>(); try { ResourcesForEs r=new
   * ResourcesForEs(); list=resourcesDao.search(query, null,null, 0, 20,r); } catch
   * (NoNodeAvailableException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
   * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); } List<Forum>
   * forumList=createForumList(list); reponse.setList(forumList); return reponse; }
   */

  public List<Forum> createForumList(List<ResourcesForEs> relist) {
    List<Forum> list = new ArrayList<Forum>();
    for (ResourcesForEs re : relist) {
      Forum forum = new Forum();
      forum.setResourcesId(re.getId());
      forum.setContent(re.getContent());
      forum.setOutline(re.getOutline());
      forum.setTitle(re.getTitle());
      forum.setDigest(re.getDigest());
      forum.setUserId(re.getPublisherId().toString());
      list.add(forum);
    }
    return list;
  }

  /**
   * 
   * 搜索文章列表
   * 
   * @param classificationId
   * @param top
   * @param size
   * @param ids
   * @param timestamp
   * @return
   */
  private List<Forum> searchByName(String userId, String name, int size, Timestamp timestamp) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("titleSearch", name);
    input.put("digestSearch", 0);
    if (null != timestamp) input.put("updateTimeUpper", DateUtil.SDF_FULL.format(timestamp));
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("updateTime", "DESC");
    cond.put("input", input);
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getForumListOutput());
    cond.put("limitCount", size);
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    List<Forum> topForums = new ArrayList<Forum>();
    if (null != forums && forums.size() > 0) {
      for (ForumUserModel model : forums) {
        try {
          Forum forum = new Forum(model);
          forum.setContent(null);
          topForums.add(forum);
        } catch (Exception e) {
          LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
          continue;
        }
      }
    }

    return getAllList(topForums);
  }

  /************************ 其他方法 *************************/

  /**
   * 
   * 获取专栏下作品数量
   * 
   * @param columnId
   * @return
   */
  public Integer getForumNumberByColumnId(String columnId) {
    Integer count = forumServiceFacade.queryForumNumberByColumnId(columnId);
    return count;
  }

  /**
   * 
   * 获取我点赞过的文章视频
   * 
   * @param columnId
   * @return
   */
  public Object getMyPariseResources(MyPraiseRequest pojo) {
    MyForumResponse response = new MyForumResponse(ResultType.SUCCESS);
    List<Forum> list = queryMyPariseResources(pojo.getUid(), pojo.getSize(), pojo.getResourcesId());
    response.setList(list);
    return response;
  }

  /**
   * 
   * 获取我点赞过的文章视频
   * 
   * @param columnId
   * @return
   */
  private List<Forum> queryMyPariseResources(String userId, int size, String forumId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("replyId", userId);
    if (forumId == null || "".equals(forumId) || "0".equals(forumId)) {} else {
      Map<String, Object> input1 = new HashMap<String, Object>();
      Map<String, Object> output1 = new HashMap<String, Object>();
      input1.put("replyId", userId);
      input1.put("forumId", forumId);
      input1.put("commentId", 0);
      output1.put("createTime", "1");
      List<ForumPraiseModel> list = forumServiceFacade.queryPraiseList(input1, output1);
      if (list != null && list.size() > 0) {
        input.put("praiseCreateTimeUpper", DateUtil.SDF_FULL.format(list.get(0).getCreateTime()));
      }
    }
    input.put("commentId", 0);
    cond.put("input", input);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("praiseCreateTime", "DESC");
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getMyForumListOutput());
    cond.put("limitCount", size);
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByPariseNoPage(cond);
    List<Forum> topForums = new ArrayList<Forum>();
    for (ForumUserModel model : forums) {
      try {
        Forum forum = new Forum(model);
        forum.setContent(null);
        topForums.add(forum);
      } catch (Exception e) {
        LoggerUtil.error("话题表中存在脏数据  : " + model.getId(), e);
        continue;
      }

    }

    return getAllList(topForums);
  }

  /**
   * 
   * 个人文章视频数量
   * 
   * @param columnId
   * @return
   */
  public SumResponse getMyResourcesCount(BaseRequest pojo) {
    SumResponse response = new SumResponse(ResultType.SUCCESS);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("publisherId", Integer.valueOf(pojo.getUid()));
    input.put("digestSearch", 0);
    Integer i = forumServiceFacade.queryForumNumber(input);
    response.setSum(i);
    return response;
  }

  /**
   * 
   * 所有人的文章视频数量
   * 
   * @param columnId
   * @return
   */
  public CommonCountListResponse getAllResourcesCount(AnyCountRequest pojo) {
    CommonCountListResponse response = new CommonCountListResponse(ResultType.SUCCESS);
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    List<String> idList = Arrays.asList(pojo.getIds().split(","));
    input.put("ids", idList);
    input.put("digestSearch", 0);
    cond.put("input", input);
    List<CommonCount> list = forumServiceFacade.queryUsersForumNumber(cond);
    response.setList(list);
    return response;
  }

  public BaseResponse hasResourcesDynamic(ResourcesListDynamicRequest pojo) {
    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(pojo.getResourcesId());
    if (null == forumUserModel || null == forumUserModel.getUpdateTime())
      return new BaseResponse(ForumResType.NULLFORUM);
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    List<String> idList = Arrays.asList(pojo.getIds().split(","));
    input.put("userids", idList);
    input.put("updateTimeLower", DateUtil.SDF_FULL.format(forumUserModel.getUpdateTime()));
    cond.put("input", input);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("updateTime", "DESC");
    cond.put("sort", sort);
    cond.put("output", ForumUtil.getForumListOutput());
    cond.put("limitCount", pojo.getSize());
    List<ForumUserModel> forums = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    if (forums.size() > 1) {
      BaseResponse response = new BaseResponse(ForumResType.NEWDYNAMIC);
      return response;
    } else {
      return new BaseResponse(ResultType.SUCCESS);
    }
  }

  /**
   * 根据赞赏包装资源Response
   * 
   * @author zhouhuan
   */
  public List<Forum> getAllList(List<Forum> list) {
    if (list != null) {
      for (Forum f : list) {
        Integer rewardCount =
            forumServiceFacade.quertResourceRewardCountByResourceId(f.getResourcesId());
        if (rewardCount != null) {
          f.setRewardCount(rewardCount.toString());
        }
      }
    }
    return list;
  }
}
