package com.xiaodou.course.web.controller.product;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.service.product.CourseShareService;
import com.xiaodou.course.web.request.product.CourseShareRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.share.prop.ShareProp;
import com.xiaodou.share.weixin.WeiXinShareUtils;
import com.xiaodou.share.weixin.XDWeixinApi;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.web.controller.QuesShareController.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享controller
 * @version 1.0
 */
@Controller("productShareController")
@RequestMapping("/product")
public class ProductShareController {

  /** courseShareService 分享 */
  @Resource
  CourseShareService courseShareService;

  /**
   * 分享
   * 
   * @return
   */
  @RequestMapping("/share")
  @ResponseBody
  public String share(CourseShareRequest request) {
    try {
      String module = request.getUserModel().getModule();
      return JSON.toJSONString(courseShareService.doMain(module, request));
    } catch (Exception e) {
      return JSON.toJSONString(new BaseResponse(ResultType.SYSFAIL));
    }
  }

  /**
   * 去往分享页面
   * 
   * @param resultType 1:成功2:失败
   * @return
   */
  @RequestMapping("/shareToProduct")
  public ModelAndView shareToProduct(String resultType) throws Exception {
    ModelAndView modelAndView = new ModelAndView("productShare/productShare");
    modelAndView.addObject("resultType", resultType);
    modelAndView.addObject("shareResponse", ShareProp.getResponse("1", resultType));
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

}
