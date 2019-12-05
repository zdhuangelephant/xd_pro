package com.xiaodou.forum.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.forum.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.forum.request.forum.ForumDetailForumRequest;
import com.xiaodou.forum.request.forum.ForumListRequest;
import com.xiaodou.forum.request.forum.ForumPraiseRequest;
import com.xiaodou.forum.request.forum.ForumReplyCommentsRequest;
import com.xiaodou.forum.request.forum.ForumReplyPostRequest;
import com.xiaodou.forum.request.forum.ForumRequest;
import com.xiaodou.forum.request.forum.IgnoreRelateCommentsRequest;
import com.xiaodou.forum.request.forum.MyCommentRequest;
import com.xiaodou.forum.request.forum.MyForumRequest;
import com.xiaodou.forum.request.forum.RelateCommentsRequest;
import com.xiaodou.forum.service.forum.CommentListService;
import com.xiaodou.forum.service.forum.ForumCategoryService;
import com.xiaodou.forum.service.forum.ForumDetailService;
import com.xiaodou.forum.service.forum.ForumListService;
import com.xiaodou.forum.service.forum.ForumPraiseService;
import com.xiaodou.forum.service.forum.ForumRecommendService;
import com.xiaodou.forum.service.forum.ForumReplyService;
import com.xiaodou.forum.service.forum.ForumService;
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
@RequestMapping("/forum")
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

  /**
   * 话题分类列表获取
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/forumCategoryList")
  public @ResponseBody
  String forumCategoryList() throws Exception {
    return JSON.toJSONString(forumCategoryService.getForumCategoryList());
  }

  /**
   * 2. 话题列表
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/list")
  public @ResponseBody
  String forumList(ForumListRequest pojo) {
    return JSON.toJSONString(forumListService.queryForums(pojo));
  }

  /**
   * 3. 话题详情-话题部分
   * 
   * @param body
   * @return
   */
  @RequestMapping("/detailforum")
  public @ResponseBody
  String forumDetailForum(ForumDetailForumRequest pojo) {
    return FastJsonUtil.toJson(forumDetailService.forumContent(pojo));
  }

  /**
   * 4. 话题详情－评论部分：评论列表
   * 
   * @param body
   * @return
   */
  @RequestMapping("/detailcomment")
  public @ResponseBody
  String forumDetailComment(ForumDetailCommentsRequest pojo) {
    return FastJsonUtil.toJson(forumDetailService.forumComments(pojo));
  }

  /**
   * 5. 我参与－话题
   * 
   * @param body
   * @return
   * @throws Exception
   */
  @RequestMapping("/myForums")
  public @ResponseBody
  String myForums(MyForumRequest pojo) throws Exception {
    return FastJsonUtil.toJson(forumListService.getMyForum(pojo));
  }

  /**
   * 6. 我参与－评论
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
   * 7. 回复帖子
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
   * 7.1. 回复评论
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
   * 8. 推荐话题
   * 
   * @param body
   * @return
   */
  @RequestMapping("/recommend")
  public @ResponseBody
  String recommend() throws Exception {
    return JSON.toJSONString(forumRecommendService.getRecommendList());
  }

  /**
   * 9. 发布话题
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
   * 10.点赞
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
   * 12 与我相关的,发布的评论与点赞接口
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
   * 13 忽略我的所有未读消息  
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/ignoreRelateComments")
  @ResponseBody
  public String ignoreRelateComments(IgnoreRelateCommentsRequest pojo) {
    return FastJsonUtil.toJson(commentListService.ignoreRelateComments(pojo));
  }

}
