package com.xiaodou.resources.web.controller.quesbk;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.service.quesbk.QuesBaseServices;
import com.xiaodou.resources.service.quesbk.QuesShareService;
import com.xiaodou.share.weixin.WeiXinShareUtils;
import com.xiaodou.share.weixin.XDWeixinApi;

/**
 * @name @see com.xiaodou.web.controller.QuesShareController.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享controller
 * @version 1.0
 */
@Controller("quesShareController")
@RequestMapping("/quesbk")
public class QuesShareController {


  @Resource
  QuesShareService quesShareService;
  @Resource
  QuesBaseServices quesBaseServices;

  /**
   * 分享
   * 
   * @return
   */
//  @RequestMapping("/share")
//  @ResponseBody
//  public String share(QuesSharePojo request) {
//    String module = UserTokenWrapper.getWrapper().getUserModel().getModule();
//    ShareResponse doMain = quesShareService.doMain(request., request);
//    return FastJsonUtil.toJson(doMain);
//  }

  /**
   * 去往分享页面
   * 
   * @param catId
   * @return
   */
  // @RequestMapping("/shareToQuesbk")
  // public ModelAndView shareToQuesbk(String paperId, String courseId, String score, String
  // shareType)
  // throws Exception {
  // if (StringUtils.isBlank(shareType)) shareType = "3";
  // if (StringUtils.isBlank(score)) score = "A";
  // ShareResponse response = ShareProp.getResponse("1", shareType);
  // if (("3").equals(shareType)) {
  // String content = String.format(response.getContent(), score);
  // response.setContent(content);
  // }
  // ModelAndView modelAndView = new ModelAndView("quesbkShare/quesbkShare");
  // ExamDetailPojo pojo = new ExamDetailPojo();
  // pojo.setExamType("1");// 真题
  // pojo.setPaperId(paperId);
  // pojo.setCourseId(courseId);
  // ExamDetailResponse examDetailResponse = quesShareService.getExamDetail(pojo);
  // modelAndView.addObject("examDetailResponse", examDetailResponse);
  // modelAndView.addObject("shareResponse", response);
  // return modelAndView;
  // }

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
