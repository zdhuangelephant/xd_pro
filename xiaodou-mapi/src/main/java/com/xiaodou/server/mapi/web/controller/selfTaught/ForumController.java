package com.xiaodou.server.mapi.web.controller.selfTaught;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.server.mapi.prop.ShareInfoProp;
import com.xiaodou.server.mapi.request.forum.ShareRequest;
import com.xiaodou.server.mapi.response.forum.ForumListResponse;
import com.xiaodou.server.mapi.response.forum.ForumResponse;
import com.xiaodou.server.mapi.response.forum.ForumShareResponse;
import com.xiaodou.server.mapi.response.forum.IndexResponse;
import com.xiaodou.server.mapi.response.forum.UserForumListResponse;
import com.xiaodou.server.mapi.response.product.ProductCategoryListResponse;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.service.forum.ForumService;
import com.xiaodou.server.mapi.util.RdUtil;
import com.xiaodou.server.mapi.vo.request.ForumIndexRequest;
import com.xiaodou.server.mapi.vo.request.ForumListRequest;
import com.xiaodou.server.mapi.vo.request.ForumPraiseRequest;
import com.xiaodou.server.mapi.vo.request.ForumRequest;
import com.xiaodou.server.mapi.vo.request.ForumUserRequest;
import com.xiaodou.server.mapi.web.controller.BaseMapiController;
import com.xiaodou.summer.vo.out.ResultType;

@Controller("ForumController")
@RequestMapping("forum")
public class ForumController extends BaseMapiController {

  @Resource
  ForumService forumService;


  /**
   * 发现首页
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("index")
  public String index(ForumIndexRequest request) {
    IndexResponse response=forumService.index(request);
    return response.toString();
  }
  
  /**
   * 公开课
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("open_course")
  public String openCourse(ForumListRequest request) {
	ForumListResponse response=forumService.openCourse(request);
    return response.toString();
  }

  /**
   * 知识分享
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("share")
  public String share(ForumListRequest request) {
	ForumShareResponse response=forumService.shareList(request);
    return response.toString();
  }
  
  /**
   * 特色专业
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("major")
  public String major(ForumListRequest request) {
	ProductCategoryListResponse response=forumService.major(request);
    return response.toJsonString();
  }
  
  
  /**
   * 各种资源详情
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("forum_detail")
  public String forumDetail(ForumRequest request) {
	ForumResponse response=forumService.forumDetail(request);
    return response.toString();
  }
  
  /**
   * 作者详情
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("author_detail")
  public String authorDetail(ForumUserRequest request) {
	UserForumListResponse response=forumService.authorDetail(request);
    return response.toString();
  }
  
  /**
   * 点赞接口
   * 
   * @param request
   * @param forumPraiseRequest
   * @return
   * @throws Exception
   */

  @ResponseBody
  @RequestMapping(value = "praise")
  public String praise(ForumPraiseRequest pojo) throws Exception {
    BaseResponse reponse = forumService.praise(pojo);
    return reponse.toString();
  }

  /**
   * 取消点赞
   * 
   * @param request
   * @param forumPraiseRequest
   * @param errors
   * @return
   * @throws Exception
   */

  @ResponseBody
  @RequestMapping(value = "cancel")
  public String cancel(ForumPraiseRequest pojo) throws Exception {
    BaseResponse reponse = forumService.cancel(pojo);
    return reponse.toString();
  }


  @ResponseBody
  @RequestMapping(value = "/share_data")
  public String share(ShareRequest pojo) {
    return forumService.share(pojo).toString0();
  }

  // @RequestMapping("/share_page")
  // @ResponseBody
  // public String sharePage(String resourceId, String _timeStamp) {
  // return askedShareService.sharePage(resourceId, _timeStamp);
  // }

  /**
   * 去往分享页面
   * 
   * @param catId
   * @return
   */
  @RequestMapping("/share_page")
  public ModelAndView sharePage(String resourceId, String _timeStamp) throws Exception {
    ModelAndView modelAndView = new ModelAndView("/resourceShare/resourceShare");
    ForumRequest request = new ForumRequest();
    request.setForumId(resourceId);
    ForumResponse response =forumService.forumDetail(request);
    if (ResultType.SUCCESS.getCode().equals(response.getRetcode())) {
    	response.getForum().setForumContent(RdUtil.delHTMLTag(response.getForum().getForumContent()));
    	modelAndView.addObject("forum", response.getForum());
      
      // 过滤标签以后的数据
//      modelAndView.addObject("forumwithoutTag", RdUtil.delHTMLTag(response.getForum().getForumContent()));
      
    }
    String iconUrl = ShareInfoProp.getParams("shareinfo.iconUrl");
    modelAndView.addObject("iconUrl", iconUrl);
    return modelAndView;
  }

  @RequestMapping("/get_js_api_map")
  @ResponseBody
  public String getJsApiMap(String url) {
    return forumService.getJsApiMap(url);
  }


  @RequestMapping("/not_wei_xin")
  public ModelAndView noweixin() throws Exception {
    ModelAndView modelAndView = new ModelAndView("public/notWeixin");
    String iconUrl = ShareInfoProp.getParams("shareinfo.iconUrl");
    modelAndView.addObject("iconUrl", iconUrl);
    return modelAndView;
  }
  @RequestMapping("/wait_page")
  public ModelAndView waitPage() {
    ModelAndView modelAndView = new ModelAndView("/resourceShare/waitPage");
    return modelAndView;
  }
}
