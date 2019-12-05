package com.xiaodou.forum.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.forum.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.forum.request.forum.ForumDetailForumRequest;
import com.xiaodou.forum.request.forum.ForumShareRequest;
import com.xiaodou.forum.response.forum.ForumDetailCommentsResponse;
import com.xiaodou.forum.service.forum.ForumDetailService;
import com.xiaodou.forum.service.forum.ForumShareService;
import com.xiaodou.share.weixin.WeiXinShareUtils;
import com.xiaodou.share.weixin.XDWeixinApi;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;

/**
 * @name @see com.xiaodou.web.controller.QuesShareController.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享controller
 * @version 1.0
 */
@Controller("forumShareController")
@RequestMapping("/forum")
public class ForumShareController {

  /** forumShareService 分享 */
  @Resource
  ForumShareService forumShareService;

  @Resource
  ForumDetailService forumDetailService;

  /**
   * 分享
   * 
   * @return
   */
  @RequestMapping("/share")
  @ResponseBody
  public String share(ForumShareRequest request) {
    String module = UserTokenWrapper.getWrapper().getUserModel().getModule();
    return FastJsonUtil.toJson(forumShareService.doMain(module, request));
  }

  /**
   * 去往分享页面
   * 
   * @param catId
   * @return
   */
  // @ModelAttribute("forumShareRequest") ForumShareRequest forumShareRequest
  @RequestMapping("/shareToForum")
  public ModelAndView shareToForum(String forumId, String categoryId) {
    ModelAndView modelAndView = new ModelAndView("forumShare/forumShare");
    ForumDetailForumRequest detailReq = new ForumDetailForumRequest();
    detailReq.setForumId(forumId);
    if (null == forumDetailService.forumContent(detailReq)
        || null == forumDetailService.forumContent(detailReq).getForum()) {
      modelAndView = new ModelAndView("forumShare/error");
      return modelAndView;
    }
    modelAndView.addObject("forumModel", forumDetailService.forumContent(detailReq).getForum());
    ForumDetailCommentsRequest commentReq = new ForumDetailCommentsRequest();
    commentReq.setForumId(forumId);
    commentReq.setSize(10);
    ForumDetailCommentsResponse forumComments = forumDetailService.forumComments(commentReq);
    if (null != forumComments) {
      modelAndView.addObject("hotCommentsCount", forumComments.getHot().size());
      modelAndView.addObject("hotComments", forumComments.getHot());
      modelAndView.addObject("normalCommentsCount", forumComments.getList().size());
      modelAndView.addObject("normalComments", forumComments.getList());
    }
    ForumShareRequest request = new ForumShareRequest();
    request.setCategoryId(categoryId);
    request.setForumId(forumId);
    request.setShareType("4");
    if (null == forumShareService.doMain("1", request)
        || null == forumShareService.doMain("1", request).getTitle()) {
      modelAndView = new ModelAndView("forumShare/error");
      return modelAndView;
    }
    modelAndView.addObject("shareResponse", forumShareService.doMain("1", request));
    return modelAndView;
  }

  /**
   * 获取微信jssdk的config配置信息
   * 
   * @param url
   */
  @RequestMapping("/getJsApiMap")
  @ResponseBody
  public String getJsApiMap(String url) {
    XDWeixinApi dmweixin = new XDWeixinApi();
    String jsapi_ticket = "";
    try {
      jsapi_ticket = dmweixin.getWeiXinJsApiTicket();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Map<String, String> jsMap = WeiXinShareUtils.sign(jsapi_ticket, url);
    return FastJsonUtil.toJson(jsMap);
  }

  @RequestMapping("/notWeixing")
  public ModelAndView noweixing() throws Exception {
    ModelAndView modelAndView = new ModelAndView("public/notWeixing");
    return modelAndView;
  }

}
