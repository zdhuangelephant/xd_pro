package com.xiaodou.resources.web.controller.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.AnyCountRequest;
import com.xiaodou.resources.request.forum.ColumnSearchRequest;
import com.xiaodou.resources.request.forum.ColumnistCountRequest;
import com.xiaodou.resources.request.forum.ColumnistDetailRequest;
import com.xiaodou.resources.request.forum.ColumnistListRequest;
import com.xiaodou.resources.request.forum.DelForumRequest;
import com.xiaodou.resources.request.forum.DeleteColumnistRequest;
import com.xiaodou.resources.request.forum.FollowColumnistRequest;
import com.xiaodou.resources.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.resources.request.forum.ForumDetailForumRequest;
import com.xiaodou.resources.request.forum.ForumListByColumnRequest;
import com.xiaodou.resources.request.forum.ForumListRequest;
import com.xiaodou.resources.request.forum.ForumListSearchRequest;
import com.xiaodou.resources.request.forum.ForumPraiseRequest;
import com.xiaodou.resources.request.forum.ForumReplyCommentsRequest;
import com.xiaodou.resources.request.forum.ForumReplyPostRequest;
import com.xiaodou.resources.request.forum.ForumRequest;
import com.xiaodou.resources.request.forum.IgnoreRelateCommentsRequest;
import com.xiaodou.resources.request.forum.ModifyColumnistRequest;
import com.xiaodou.resources.request.forum.MyCommentRequest;
import com.xiaodou.resources.request.forum.MyForumRequest;
import com.xiaodou.resources.request.forum.MyPraiseRequest;
import com.xiaodou.resources.request.forum.PublishColumnistRequest;
import com.xiaodou.resources.request.forum.ReCommendListRequest;
import com.xiaodou.resources.request.forum.RelateCommentsRequest;
import com.xiaodou.resources.request.forum.ResourcesListDynamicRequest;
import com.xiaodou.resources.request.forum.UnFollowColumnistRequest;
import com.xiaodou.resources.response.forum.AllListForSearchResponse;
import com.xiaodou.resources.response.forum.ColumnistCountResponse;
import com.xiaodou.resources.response.forum.ColumnistListResponse;
import com.xiaodou.resources.response.forum.ForumListResponse;
import com.xiaodou.resources.response.forum.PersonCountResponse;
import com.xiaodou.resources.response.forum.SumResponse;
import com.xiaodou.resources.service.forum.CommentListService;
import com.xiaodou.resources.service.forum.ForumCategoryService;
import com.xiaodou.resources.service.forum.ForumDetailService;
import com.xiaodou.resources.service.forum.ForumListService;
import com.xiaodou.resources.service.forum.ForumPraiseService;
import com.xiaodou.resources.service.forum.ForumRecommendService;
import com.xiaodou.resources.service.forum.ForumReplyService;
import com.xiaodou.resources.service.forum.ForumService;
import com.xiaodou.resources.service.forum.ResourcesColumnistService;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;

/**
 * 
 * 话题Controller
 * 
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2015年4月12日 上午11:12:16 weichao.zhai 1.0 初始化创建<br>
 * </p>
 * 
 * @author weichao.zhai
 * @version 1.0
 */
@Controller("forumController")
@RequestMapping("/resources")
public class ForumController extends BaseController {

  /** 话题分类service */
  @Resource
  ForumCategoryService forumCategoryService;

  /** 话题推荐service */
  @Resource
  ForumRecommendService forumRecommendService;

  @Resource
  ForumListService forumListService;

  @Resource
  ForumDetailService forumDetailService;

  /** 回复话题service */
  @Resource
  ForumReplyService forumReplyService;

  /** 话题发布service */
  @Resource
  ForumService forumService;

  /** 点赞service */
  @Resource
  ForumPraiseService forumPraiseService;

  /** 评论列表 */
  @Resource
  CommentListService commentListService;

  @Resource
  ResourcesColumnistService resourcesColumnistService;

  /**
   * 资源分类_完成
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/categoryList")
  public @ResponseBody
  String forumCategoryList() throws Exception {
    return JSON.toJSONString(forumCategoryService.getForumCategoryList());
  }

  /**
   * 发布话题_完成
   * 
   * @param body
   * @return
   */
  @RequestMapping("/post")
  public @ResponseBody
  String post(ForumRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumService.insertForum(pojo));
  }

  /**
   * 编辑话题
   * 
   * @param body
   * @return
   */
  @RequestMapping("/editResources")
  public @ResponseBody
  String edit(ForumRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumService.editForum(pojo));
  }

  /**
   * 话题列表_根据类型(文章-视频)
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/resourceslistById")
  public @ResponseBody
  String resourceslistById(ForumListRequest pojo) {
    return JSON.toJSONString(forumListService.queryForums(pojo));
  }

  /**
   * 搜索文章_完成
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/resourcesListByName")
  public @ResponseBody
  String resourcesListByName(ForumListSearchRequest pojo) {
    return JSON.toJSONString(forumListService.search(pojo));
  }

  /**
   * 首次搜索
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/searchAll")
  public @ResponseBody
  String searchAll(ForumListSearchRequest pojo) {
    ColumnSearchRequest request = new ColumnSearchRequest();
    request.setTitle(pojo.getName());
    ForumListResponse forumListResponse = forumListService.search(pojo);
    ColumnistListResponse reponse = resourcesColumnistService.columnistSearch(request);
    AllListForSearchResponse allReponse = new AllListForSearchResponse(ResultType.SUCCESS);
    allReponse.setColumnistList(reponse.getColumnistList());
    allReponse.setList(forumListResponse.getList());
    return JSON.toJSONString(allReponse);
  }


  /**
   * 回复帖子
   * 
   * @param body
   * @return
   */
  @RequestMapping("/replyPost")
  public @ResponseBody
  String replyPost(ForumReplyPostRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumReplyService.replyPost(pojo));
  }

  /**
   * 回复评论
   * 
   * @param body
   * @return
   */
  @RequestMapping("/replyComment")
  public @ResponseBody
  String replyComment(ForumReplyCommentsRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumReplyService.replyComments(pojo));
  }

  /**
   * 推荐话题
   * 
   * @param body
   * @return
   */
  @RequestMapping("/recommend")
  public @ResponseBody
  String recommend(ReCommendListRequest request) throws Exception {
    return JSON.toJSONString(forumRecommendService.getRecommendList(request));
  }


  /**
   * 资源详情-资源部分
   * 
   * @param body
   * @return
   */
  @RequestMapping("/detailResources")
  public @ResponseBody
  String forumDetailForum(ForumDetailForumRequest pojo) {
    return FastJsonUtil.toJson(forumDetailService.forumContent(pojo));
  }

  /**
   * 资源详情－评论部分：评论列表
   * 
   * @param body
   * @return
   */
  @RequestMapping("/detailComment")
  public @ResponseBody
  String forumDetailComment(ForumDetailCommentsRequest pojo) {
    return FastJsonUtil.toJson(forumDetailService.forumComments(pojo));
  }

  /**
   * 点赞
   * 
   * @param request
   * @param forumPraiseRequest
   * @return
   * @throws Exception
   */
  @RequestMapping("/praise")
  public @ResponseBody
  String praise(ForumPraiseRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumPraiseService.insertPraise(pojo));
  }

  /**
   * 取消点赞接口
   * 
   * @param request
   * @param forumPraiseRequest
   * @param errors
   * @return
   * @throws Exception
   */
  @RequestMapping("/cancel")
  public @ResponseBody
  String cancel(ForumPraiseRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumPraiseService.deletePraise(pojo));
  }

  /**
   * 我发布的资源
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/myResources")
  public @ResponseBody
  String myForums(MyForumRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumListService.getMyForum(pojo));
  }

  /**
   * 我赞过的文章视频
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/getMyPariseResources")
  @ResponseBody
  public String getMyPariseResources(MyPraiseRequest pojo) {
    return FastJsonUtil.toJson(forumListService.getMyPariseResources(pojo));
  }

  /**
   * 我的作品数量
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/getMyResourcesCount")
  @ResponseBody
  public String getMyResourcesCount(BaseRequest pojo) {
    return FastJsonUtil.toJson(forumListService.getMyResourcesCount(pojo));
  }

  /**
   * 用户组的作品数量
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/getAllResourcesCount")
  @ResponseBody
  public String getAllResourcesCount(AnyCountRequest pojo) {
    return FastJsonUtil.toJson(forumListService.getAllResourcesCount(pojo));
  }

  /**
   * 用户组的点赞数量
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/getAllPraiseCount")
  @ResponseBody
  public String getAllPraiseCount(AnyCountRequest pojo) {
    return FastJsonUtil.toJson(forumPraiseService.getAllResourcesCount(pojo));
  }

  /**
   * 轮播图
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/getChart")
  @ResponseBody
  public String getChart() {
    return FastJsonUtil.toJson(forumRecommendService.getChart());
  }


  /**
   * 我参与－评论
   * 
   * @param body
   * @return
   */
  @RequestMapping("/myComments")
  public @ResponseBody
  String myComments(MyCommentRequest pojo) throws Exception {
    return FastJsonUtil.toJson(commentListService.getMyComment(pojo));
  }

  /**
   * 与我相关的,发布的评论与点赞接口
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/myRelateComments")
  @ResponseBody
  public String myRelateComments(RelateCommentsRequest pojo) {
    return FastJsonUtil.toJson(commentListService.myRelateComments(pojo));
  }


  /**
   * 忽略我的所有未读消息
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/ignoreRelateComments")
  @ResponseBody
  public String ignoreRelateComments(IgnoreRelateCommentsRequest pojo) {
    return FastJsonUtil.toJson(commentListService.ignoreRelateComments(pojo));
  }

  /**
   * 获取个人信息总数
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/personCount")
  public @ResponseBody
  String personCount(BaseRequest pojo) {
    SumResponse sumResponse1 = forumPraiseService.pariseCountByUser(pojo);
    SumResponse sumResponse2 = forumListService.getMyResourcesCount(pojo);
    ColumnistCountResponse columnistCountResponse = resourcesColumnistService.columnistCount(pojo);
    PersonCountResponse response = new PersonCountResponse(ResultType.SUCCESS);
    response.setColumnistCount(columnistCountResponse.getColumnistCount());
    response.setPariseCount(sumResponse1.getSum());
    response.setResourcesCount(sumResponse2.getSum());
    return FastJsonUtil.toJson(response);
  }

  /**
   * 关注-动态
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/resourcesDynamic")
  public @ResponseBody
  String resourcesDynamic(ResourcesListDynamicRequest pojo) {
    return JSON.toJSONString(forumListService.resourcesDynamic(pojo));
  }

  /**
   * 获取个人获取点赞总数
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/pariseCountByUser")
  public @ResponseBody
  String pariseCountByUser(BaseRequest pojo) {
    return JSON.toJSONString(forumPraiseService.pariseCountByUser(pojo));
  }

  /**
   * 删除资源
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/delResources")
  public @ResponseBody
  String delResources(DelForumRequest pojo) {
    return JSON.toJSONString(forumService.delResources(pojo));
  }


  /**
   * 动态小红点
   * 
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/hasResourcesDynamic")
  public @ResponseBody
  String hasResourcesDynamic(ResourcesListDynamicRequest pojo) {
    return JSON.toJSONString(forumListService.hasResourcesDynamic(pojo));
  }

  /**
   * 发布默认专栏
   * 
   * @param pojo 发布专栏请求
   * @return
   */
  @RequestMapping("/publish_default_columnist")
  @ResponseBody
  public String publishDefaultColumnist(PublishColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.publishDefaultColumnist(pojo));
  }

  /**
   * 发布专栏
   * 
   * @param pojo 发布专栏请求
   * @return
   */
  @RequestMapping("/publish_columnist")
  @ResponseBody
  public String publishColumnist(PublishColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.publishColumnist(pojo));
  }

  /**
   * 修改专栏
   * 
   * @param pojo 修改专栏请求
   * @return
   */
  @RequestMapping("/modify_columnist")
  @ResponseBody
  public String modifyColumnist(ModifyColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.modifyColumnist(pojo));
  }

  /**
   * 删除专栏
   * 
   * @param pojo 删除专栏请求
   * @return
   */
  @RequestMapping("/delete_columnist")
  @ResponseBody
  public String deleteColumnist(DeleteColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.deleteColumnist(pojo));
  }

  /**
   * 专栏列表
   * 
   * @param pojo 专栏列表请求
   * @return
   */
  @RequestMapping("/columnist_list")
  @ResponseBody
  public String columnistList(ColumnistListRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.columnistList(pojo));
  }

  /**
   * 推荐专栏列表
   * 
   * @param pojo 专栏列表请求
   * @return
   */
  @RequestMapping("/heat_columnist_list")
  @ResponseBody
  public String heatColumnistList(ColumnistListRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.heatColumnistList(pojo));
  }

  /**
   * 专栏列表数
   * 
   * @param pojo 专栏列表请求
   * @return
   */
  @RequestMapping("/columnist_count")
  @ResponseBody
  public String columnistCount(BaseRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.columnistCount(pojo));
  }

  /**
   * 关注的专栏列表
   * 
   * @param pojo 专栏列表请求
   * @return
   */
  @RequestMapping("/follow_columnist_list")
  @ResponseBody
  public String followColumnistList(ColumnistListRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.followColumnistList(pojo));
  }

  /**
   * 关注的专栏数
   * 
   * @param pojo 专栏列表请求
   * @return
   */
  @RequestMapping("/follow_columnist_count")
  @ResponseBody
  public String followColumnistCount(ColumnistCountRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.followColumnistCount(pojo));
  }

  /**
   * 根据标题搜索专栏列表
   * 
   * @param pojo 搜索专栏请求
   * @return
   */
  @RequestMapping("/columnist_search")
  @ResponseBody
  public String columnistSearch(ColumnSearchRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.columnistSearch(pojo));
  }

  /**
   * 专栏详情
   * 
   * @param pojo 专栏详情请求
   * @return
   */
  @RequestMapping("/columnist_detail")
  @ResponseBody
  public String columnistDetail(ColumnistDetailRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.columnistDetail(pojo));
  }

  /**
   * 专栏最新话题列表
   * 
   * @param pojo 专栏话题列表请求
   * @return
   */
  @RequestMapping("/columnist_new_forum")
  @ResponseBody
  public String columnistNewForumList(ForumListByColumnRequest pojo) {
    return FastJsonUtil.toJson(forumListService.queryForumsByColumnId(pojo));
  }

  /**
   * 专栏最热话题列表
   * 
   * @param pojo 专栏话题列表请求
   * @return
   */
  @RequestMapping("/columnist_hot_forum")
  @ResponseBody
  public String columnistHotForumList(ForumListByColumnRequest pojo) {
    return FastJsonUtil.toJson(forumRecommendService.getRecommendListByColumnId(pojo));
  }

  /**
   * 关注专栏
   * 
   * @param pojo 关注专栏请求
   * @return
   */
  @RequestMapping("/follow_columnist")
  @ResponseBody
  public String followColumnist(FollowColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.followColumnist(pojo));
  }

  /**
   * 取消关注专栏
   * 
   * @param pojo 取消关注专栏请求
   * @return
   */
  @RequestMapping("/un_follow_columnist")
  @ResponseBody
  public String unfollowColumnist(UnFollowColumnistRequest pojo) {
    return FastJsonUtil.toJson(resourcesColumnistService.unfollowColumnist(pojo));
  }

}
