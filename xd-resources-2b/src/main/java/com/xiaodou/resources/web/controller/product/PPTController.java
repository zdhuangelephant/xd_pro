package com.xiaodou.resources.web.controller.product;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpResultContentType;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.share.prop.ShareProp;


/**
 */
@Controller("pptController")
@RequestMapping("/product")
public class PPTController {

  /**
   * 倒计时
   */
  // @RequestMapping("/timeCdown")
  // public ModelAndView timeCdown() throws Exception {
  // String examDate = null;
  // String yearTime = null;
  // String monthTime = null;
  // try {
  // if (null != UserTokenWrapper.getWrapper().getUserModel()) {
  // String module = UserTokenWrapper.getWrapper().getModule();
  // if (("1").equals(module)
  // && UserTokenWrapper.getWrapper().getUserModel() instanceof JzUserInfoResponse) {
  // JzUserInfoResponse jzUserInfoResponse =
  // (JzUserInfoResponse) UserTokenWrapper.getWrapper().getUserModel();
  // examDate = jzUserInfoResponse.getExamDate();
  // }
  // examDate = ("其它").equals(examDate) ? null : examDate;
  // // examDate = "2015年上半期";
  // String reg = "^\\d{4}.*";
  // if (null != examDate) {
  // boolean m = examDate.matches(reg);
  // if (m) {
  // String month = examDate.substring(5, 6);
  // yearTime = examDate.substring(0, 4);
  // if (("上").equals(month)) {
  // monthTime = "3";
  // } else if (("下").equals(month)) {
  // monthTime = "11";
  // }
  // }
  // }
  // }
  // } catch (Exception e) {
  // LoggerUtil.error("调到时间倒计时页面时出错！", e);
  // }
  //
  // ModelAndView modelAndView = new ModelAndView("timeCdown/timeCdown");
  // modelAndView.addObject("yearTime", yearTime);
  // modelAndView.addObject("monthTime", monthTime);
  // return modelAndView;
  // }

  /**
   * 揪花瓣
   * 
   * @param catId
   * @return
   */
  @RequestMapping("/pullPetals")
  public ModelAndView pullPetals(String flag, Integer time) throws Exception {
    ModelAndView modelAndView = new ModelAndView("game/pullPetals");
    if (null == time) time = 1;
    modelAndView.addObject("time", time ^ 1);
    if (StringUtils.isNotBlank(flag)) modelAndView.addObject("flag", "true");
    modelAndView.addObject("shareResponse", ShareProp.getResponse("1", "5"));
    return modelAndView;
  }

  /**
   * queryScoreInit
   * 
   * @param catId
   * @return
   */
  @RequestMapping("/queryScoreInit")
  public ModelAndView queryScore(HttpServletRequest req, HttpServletResponse res) throws Exception {
    return login(req, res, null);
  }

  private ModelAndView login(HttpServletRequest req, HttpServletResponse res, String errorInfo) {
    ModelAndView modelAndView = new ModelAndView("score/queryScoreInit");
    HttpResult httpResult = null;
    {
      HttpUtil http = HttpUtil.getInstance("chafen.ntce.cn", 80, "http");
      http.getClient().getParams().setContentCharset("UTF-8");
      HttpMethod method = HttpMethodUtil.getGetMethod("/");
      http.setMethod(method);
      httpResult = http.getHttpResult();
      if (httpResult.isResultOk()) {
        if (httpResult.getCookies() != null && httpResult.getCookies().length > 0) {
          StringBuilder sb = new StringBuilder(200);
          for (Cookie cookie : httpResult.getCookies()) {
            res.addCookie(new javax.servlet.http.Cookie(cookie.getName(), cookie.getValue()));
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
          }
          JedisUtil.addStringToJedis(req.getSession().getId(), sb.toString(), 300);
        }
      }
    }
    modelAndView.addObject("image", getImage(req.getSession().getId()));
    if (StringUtils.isNotBlank(errorInfo)) modelAndView.addObject("errorInfo", errorInfo);
    return modelAndView;
  }


  private String getImage(String key) {
    HttpUtil http = HttpUtil.getInstance("chafen.ntce.cn", 80, "http");
    http.getClient().getParams().setContentCharset("UTF-8");
    HttpMethod method =
        HttpMethodUtil.getGetMethod("/getYZM?s=" + Calendar.getInstance().get(Calendar.SECOND));
    method.addRequestHeader("Cookie", JedisUtil.getStringFromJedis(key));
    http.setMethod(method);
    HttpResult httpResult = http.getHttpResult();
    if (httpResult.isResultOk() && HttpResultContentType.IMAGE.equals(httpResult.getContentType())) {
      return httpResult.getContent();
    }
    return null;
  }


  /**
   * 微信跳转页
   */
  @RequestMapping("/notWeixing")
  public ModelAndView noweixing() throws Exception {
    ModelAndView modelAndView = new ModelAndView("public/notWeixing");
    return modelAndView;
  }
}
