package com.xiaodou.course.web.controller.forum;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.model.forum.AuthorModel;
import com.xiaodou.course.model.product.MajorCourseModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.forum.ForumChartService;
import com.xiaodou.course.service.forum.ForumPraiseService;
import com.xiaodou.course.service.forum.ForumService;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.vo.forum.Author;
import com.xiaodou.course.vo.forum.Forum;
import com.xiaodou.course.vo.product.ModuleSlide;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.forum.AuthorDetailRequest;
import com.xiaodou.course.web.request.forum.ForumIndexRequest;
import com.xiaodou.course.web.request.forum.ForumListRequest;
import com.xiaodou.course.web.request.forum.ForumPraiseRequest;
import com.xiaodou.course.web.request.forum.ForumRequest;
import com.xiaodou.course.web.request.forum.MajorRequest;
import com.xiaodou.course.web.response.forum.ForumListResponse;
import com.xiaodou.course.web.response.forum.ForumResponse;
import com.xiaodou.course.web.response.forum.IndexResponse;
import com.xiaodou.course.web.response.forum.ShareAllResponse;
import com.xiaodou.course.web.response.forum.UserForumListResponse;
import com.xiaodou.summer.vo.out.ResultType;

@Controller
@RequestMapping("/forum")
public class ForumController extends BaseController {


  @Resource
  ForumService forumService;

  @Resource
  ForumPraiseService forumPraiseService;

  @Resource
  ForumChartService forumChartService;

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  ProductService productService;
  @Resource
  ProductCategoryService productCategoryService;
  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 发现首页接口
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("/index")
  @ResponseBody
  public String index(ForumIndexRequest request) {
    IndexResponse response = new IndexResponse(ResultType.SUCCESS);
    List<ModuleSlide> imageUrlList = forumChartService.getChart();
    Forum forum = forumService.getCampusVoice();
    List<Forum> list = forumService.getAllForumList(request);
    response.setForum(forum);
    response.setImageUrlList(imageUrlList);
    response.setList(list);
    return FastJsonUtil.toJson(response);
  }

  /**
   * 知识分享List
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/forum_share_list")
  @ResponseBody
  public String forumShareList(ForumListRequest request) throws Exception {
    ShareAllResponse response = new ShareAllResponse(ResultType.SUCCESS);
    response.setForumList(forumService.getForumShareList(request).getForumList());
    List<ProductModel> list = productService.getAllProduct();
    Set<String> set = Sets.newHashSet();
    for (ProductModel product : list) {
      if (null == product || StringUtils.isBlank(product.getCourseCode())
          || set.contains(product.getCourseCode())) continue;
      set.add(product.getCourseCode());
    }
    List<MajorCourseModel> majorCourseList =
        productServiceFacade.getMajorCourseByIds(Lists.newArrayList(set));
    for (MajorCourseModel m : majorCourseList) {
      ShareAllResponse.ProductTag p = response.new ProductTag();
      p.setCourseId(m.getId());
      p.setName(m.getName());
      response.getList().add(p);
    }
    return FastJsonUtil.toJson(response);
  }

  /**
   * 公开课
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/open_courese_list")
  @ResponseBody
  public String openCoureseList(ForumListRequest request) throws Exception {
    ForumListResponse reponse = forumService.getForumList(request);
    return FastJsonUtil.toJson(reponse);
  }


  /**
   * 公开课知识分享资源详情
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/forum_detail")
  @ResponseBody
  public String forumDetail(ForumRequest request) throws Exception {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    Forum forum = forumService.getForumByIdAddReader(request.getForumId(), request.getUid());
    if (forum == null) {
      response = new ForumResponse(ForumResType.NULLFORUM);
      return FastJsonUtil.toJson(response);
    } else if (!StringUtils.isBlank(forum.getForumTag())) {
      MajorCourseModel major =
          productServiceFacade.queryMajorCourseByIdAndRegion(forum.getForumTag(), null);
      if (major != null) {
        forum.setForumTagName(major.getName());
      }
    }
    if (forum.getAuthorId() != null) {
      AuthorModel author = forumServiceFacade.getAuthorById(forum.getAuthorId());
      if (author != null) {
        forum.setAuthorName(author.getName());
        forum.setAuthorPortrait(author.getPortrait());
      }
    }

    response.setForum(forum);
    return FastJsonUtil.toJson(response);
  }

  /**
   * 作者详情
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/author_detail")
  @ResponseBody
  public String authorDetail(AuthorDetailRequest request) throws Exception {
    UserForumListResponse response = new UserForumListResponse(ResultType.SUCCESS);
    AuthorModel author = forumServiceFacade.getAuthorById(request.getUid());
    if (author == null) {
      response = new UserForumListResponse(ForumResType.NULLAUTHOR);
      return FastJsonUtil.toJson(response);
    }
    Author a = new Author(author);
    List<Forum> list = forumService.getUserForumList(request);
    response.setList(list);
    response.setAuthor(a);
    Integer count = forumService.getForumListCount(request);
    response.getAuthor().setForumCount(count.toString());
    return FastJsonUtil.toJson(response);
  }

  /**
   * 特色专业
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/major_v_1_4_9")
  @ResponseBody
  public String major_v_1_4_9(MajorRequest request) throws Exception {
    return productCategoryService.queryOldAllCategory(request).toJsonString();
  }

  /**
   * @description 1.5.0接口
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月16日
   * @param request
   * @return
   */
  @RequestMapping("/major")
  @ResponseBody
  public String major(MajorRequest request) {
    return productCategoryService.queryAllCategory(request).toJsonString();
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



}
