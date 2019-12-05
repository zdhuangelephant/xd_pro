package com.xiaodou.forum.service.forum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.constant.ForumConstant;
import com.xiaodou.forum.enums.ForumResType;
import com.xiaodou.forum.model.forum.ForumCategoryModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.request.forum.ForumListRequest;
import com.xiaodou.forum.request.forum.MyForumRequest;
import com.xiaodou.forum.response.forum.ForumCategoryResponse;
import com.xiaodou.forum.response.forum.ForumListResponse;
import com.xiaodou.forum.response.forum.MyForumResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.util.DateUtil;
import com.xiaodou.forum.util.FileUtils;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.forum.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.BaseUserModel;

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

  /**
   * 
   * 查询话题列表 满足需求： 1. 查询话题列表的上拉动作和下拉动作 2. 上拉动作－要求不该改变初始话题排序 3. 下拉动作－根据updateTime排序，为每个访问者生成排序
   * 
   * @param request
   * @return
   */
  public ForumListResponse queryForums(ForumListRequest request) {

    BaseUserModel userModel = request.getUserModel();
    String userId = null == userModel ? String.valueOf(Integer.MIN_VALUE) : userModel.getId();
    if (null == forumServiceFacade.queryForumCategory(request.getClassificationId()))
      return new ForumListResponse(ForumResType.NULLCATAGORY);
    if (StringUtils.isBlank(request.getForumId()))
      return queryForumsForDrop(userId, request.getClassificationId(),
          new Integer(request.getSize()), request.getDeviceId());
    return queryForumsForPull(userId, request.getClassificationId(),
        new Integer(request.getSize()), request.getDeviceId(), request.getForumId());

  }


  /**
   * 
   * 查询话题列表－下拉操作
   * 
   * @param classificationId－分类ID
   * @param size－一页数目
   * @param deviceId－设备号
   * @param forumId－最后一个话题ID
   * @return
   */
  public ForumListResponse queryForumsForPull(String userId, String classificationId, int size,
      String deviceId, String forumId) {

    ForumListResponse response = new ForumListResponse(ResultType.SUCCESS);
    /**
     * 1. 查询设置话题分类
     */

    ForumCategoryModel category = forumServiceFacade.queryForumCategory(classificationId);
    response.setCategory(new ForumCategoryResponse(category));

    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(forumId);
    if (null == forumUserModel || null == forumUserModel.getUpdateTime())
      return new ForumListResponse(ForumResType.NULLFORUM);

    List<Forum> list =
        query(userId, classificationId, ForumConstant.NotTopForum, size, null,
            forumUserModel.getUpdateTime());
    response.setList(list);
    return response;

  }

  /**
   * 
   * 上拉操作或初次进入话题列表页
   * 
   * @param classificationId-分类ID
   * @param size－每页数目
   * @param deviceId－设备号
   * @return
   */
  public ForumListResponse queryForumsForDrop(String userId, String classificationId, int size,
      String deviceId) {
    ForumListResponse reponse = new ForumListResponse(ResultType.SUCCESS);

    /**
     * 1. 查询设置话题分类
     */

    ForumCategoryModel category = forumServiceFacade.queryForumCategory(classificationId);
    reponse.setCategory(new ForumCategoryResponse(category));

    /**
     * 2. 查询话题列表库
     */
    List<Forum> topForums =
        query(userId, classificationId, ForumConstant.TopForum,
            FileUtils.FORUM_PROP.getPropertiesInt("forum.list.init.top.size"), null, null);
    reponse.setTop(topForums);
    List<Forum> forums =
        query(userId, classificationId, ForumConstant.NotTopForum,
            FileUtils.FORUM_PROP.getPropertiesInt("forum.list.init.nottop.size"), null, null);
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
   * 查询话题列表
   * 
   * @param classificationId
   * @param top
   * @param size
   * @param ids
   * @param timestamp
   * @return
   */
  private List<Forum> query(String userId, String classificationId, int top, int size, String ids,
      Timestamp timestamp) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("categoryId", classificationId);
    input.put("top", top);
    input.put("userId", userId);
    cond.put("input", input);
    if (StringUtils.isNotBlank(ids)) {
      List<String> idList = Arrays.asList(ids.split(","));
      input.put("ids", idList);
    } else {
      if (null != timestamp) input.put("updateTimeUpper", DateUtil.sdf1.format(timestamp));
      Map<String, Object> sort = new HashMap<String, Object>();
      sort.put("updateTime", "DESC");
      cond.put("sort", sort);
    }
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

    return topForums;
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
        queryMyForum(request.getUserModel().getId(), request.getSize(), null, request.getForumId());
    response.setList(list);
    return response;

  }

  private List<Forum> queryMyForum(String userId, int size, String ids, String forumId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("publisherId", userId);
    if (forumId == null || "".equals(forumId) || "0".equals(forumId)) {

    } else {
      input.put("forumId", forumId);
    }
    cond.put("input", input);
    if (StringUtils.isNotBlank(ids)) {
      input.put("ids", ids);
    } else {
      Map<String, Object> sort = new HashMap<String, Object>();
      sort.put("createTime", "DESC");
      cond.put("sort", sort);
    }
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

    return topForums;
  }

}
