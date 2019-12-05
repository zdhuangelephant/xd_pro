package com.xiaodou.resources.web.controller.forum;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.request.forum.ResourceShareRequest;
import com.xiaodou.resources.request.forum.StatisticsSharePvRequest;
import com.xiaodou.resources.service.forum.ForumDetailService;
import com.xiaodou.resources.service.forum.ResourceShareService;
import com.xiaodou.resources.service.forum.SharePagePvService;
import com.xiaodou.share.weixin.WeiXinShareUtils;
import com.xiaodou.share.weixin.XDWeixinApi;
import com.xiaodou.summer.web.BaseController;

/**
 * @name ResourceShareControllrt CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月25日
 * @description 资源分享Controller
 * @version 1.0
 */
@Controller("resourceShareControllrt")
@RequestMapping("/resources")
public class ResourceShareControllrt extends BaseController {

  @Resource
  ResourceShareService resourceShareService;
  @Resource
  ForumDetailService forumDetailService;
  @Resource
  SharePagePvService sharePagePvService;

  /**
   * 分享
   * 
   * @return
   */
  @RequestMapping("/share")
  @ResponseBody
  public String share(ResourceShareRequest pojo) {
    return resourceShareService.doMain(pojo.getModule(), pojo).toString0();
  }

  /**
   * 获取微信jssdk的config配置信息
   * 
   * @param url
   */
  @RequestMapping("/get_js_api_map")
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

  @RequestMapping("/not_wei_xin")
  public ModelAndView noweixin() throws Exception {
    ModelAndView modelAndView = new ModelAndView("public/notWeixing");
    return modelAndView;
  }

  /**
   * 分享页面统计
   * 
   * @return
   */
  @RequestMapping("/statistics_share_pv")
  @ResponseBody
  public String statisticsSharePv(HttpServletRequest request, StatisticsSharePvRequest pojo) {
    return sharePagePvService.statisticsSharePv(pojo).toString0();
  }
}
