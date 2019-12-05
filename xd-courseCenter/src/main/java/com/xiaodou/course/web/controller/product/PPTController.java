package com.xiaodou.course.web.controller.product;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpResultContentType;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.vo.score.CheckImageResponse;
import com.xiaodou.course.vo.score.ScoreResult;
import com.xiaodou.course.vo.score.ScoreResult.NameInfo;
import com.xiaodou.share.prop.ShareProp;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;
import com.xiaodou.userCenter.module.jz.response.JzUserInfoResponse;


/**
 */
@Controller("pptController")
@RequestMapping("/product")
public class PPTController {

  /**
   * 倒计时
   */
  @RequestMapping("/timeCdown")
  public ModelAndView timeCdown() throws Exception {
    String examDate = null;
    String yearTime = null;
    String monthTime = null;
    try {
      if (null != UserTokenWrapper.getWrapper().getUserModel()) {
        String module = UserTokenWrapper.getWrapper().getModule();
        if (("1").equals(module)
            && UserTokenWrapper.getWrapper().getUserModel() instanceof JzUserInfoResponse) {
          JzUserInfoResponse jzUserInfoResponse =
              (JzUserInfoResponse) UserTokenWrapper.getWrapper().getUserModel();
          examDate = jzUserInfoResponse.getExamDate();
        }
        examDate = ("其它").equals(examDate) ? null : examDate;
        // examDate = "2015年上半期";
        String reg = "^\\d{4}.*";
        if (null != examDate) {
          boolean m = examDate.matches(reg);
          if (m) {
            String month = examDate.substring(5, 6);
            yearTime = examDate.substring(0, 4);
            if (("上").equals(month)) {
              monthTime = "3";
            } else if (("下").equals(month)) {
              monthTime = "11";
            }
          }
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("调到时间倒计时页面时出错！", e);
    }

    ModelAndView modelAndView = new ModelAndView("timeCdown/timeCdown");
    modelAndView.addObject("yearTime", yearTime);
    modelAndView.addObject("monthTime", monthTime);
    return modelAndView;
  }

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
    try {
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
      modelAndView.addObject("image", getImage(req.getSession().getId()));
    } catch (Exception e) {
      LoggerUtil.error("考试院网站抓取失败", e);
      modelAndView.addObject("errorInfo", "查分网站暂时无法访问");
    }
    if (StringUtils.isNotBlank(errorInfo)) modelAndView.addObject("errorInfo", errorInfo);
    return modelAndView;
  }

  /**
   * queryScoreInit
   * 
   * @param catId
   * @return
   */
  @RequestMapping("/queryScoreImage")
  @ResponseBody
  public String queryScoreImage(HttpServletRequest req, HttpServletResponse res) throws Exception {
    CheckImageResponse response = new CheckImageResponse(ResultType.SUCCESS);
    String image = getImage(req.getSession().getId());
    if (StringUtils.isBlank(image))
      response = new CheckImageResponse(ResultType.SYSFAIL);
    else
      response.setImage(image);
    return FastJsonUtil.toJson(response);
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
   * queryScoreInit
   * 
   * @param catId
   * @return
   */
  @RequestMapping("/queryScoreResult")
  public ModelAndView getScore(String name, String zjhm, String yzm, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    ModelAndView modelAndView = new ModelAndView("score/queryScoreResult");
    QueryScoreResult: {
      HttpResult httpResult = null;
      ScoreResult result = null;
      try {
        HttpUtil http = HttpUtil.getInstance("chafen.ntce.cn", 80, "http");
        http.getClient().getParams().setContentCharset("UTF-8");
        NameValuePair[] params = new NameValuePair[3];
        params[0] = new NameValuePair("name", name);
        params[1] = new NameValuePair("zjhm", zjhm);
        params[2] = new NameValuePair("yzm", yzm);
        HttpMethod method =
            HttpMethodUtil.getPostMethod("/selectScore.do?method=getMyScore", params);
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        String cookie = JedisUtil.getStringFromJedis(req.getSession().getId());
        method.addRequestHeader("Cookie", cookie);
        http.setMethod(method);
        httpResult = http.getHttpResult();
        if (!httpResult.isResultOk()) return showError();
        result = new ScoreResult();
        modelAndView.addObject("score", result);
        NameInfo nameInfo = new NameInfo();
        nameInfo.setName(name);
        nameInfo.setDocNumber(zjhm);
        result.setNameInfo(nameInfo);
      } catch (Exception e) {
        LoggerUtil.error("查分网站异常", e);
        modelAndView.addObject("errorInfo", "查分网站异常,无法查询分数");
        return modelAndView;
      }
      try {
        Document doc = Jsoup.parse(httpResult.getContent());
        Elements titles = doc.getElementsByTag("title");
        if (null != titles && titles.size() > 0) {
          String title = titles.get(0).text();
          if (title.contains("中小学教师资格考试")) {
            Elements loginForms = doc.getElementsByClass("login_form");
            if (null != loginForms && loginForms.size() > 0)
              return login(req, res, "信息已过期,请重新登录。");
            Elements tableNodes = doc.getElementsByTag("table");
            if (null == tableNodes) break QueryScoreResult;
            WrittenTable: {
              if (tableNodes.size() < 3) break WrittenTable;
              Element table = tableNodes.get(2);
              if (null == table) break WrittenTable;
              Elements trs = table.getElementsByClass("td_left_f");
              result.setWrittenScoreList(trs);
            }
            InterviewTable: {
              if (tableNodes.size() < 4) break InterviewTable;
              Element table = tableNodes.get(3);
              if (null == table) break InterviewTable;
              Elements trs = table.getElementsByClass("td_left_f");
              result.setInterviewScoreList(trs);
            }
            ProveTable: {
              if (tableNodes.size() < 5) break ProveTable;
              Element table = tableNodes.get(4);
              if (null == table) break ProveTable;
              Elements trs = table.getElementsByClass("td_left_f");
              result.setProveScoreList(trs);
            }
          } else if (title.contains("error")) {
            Elements errorMsg = doc.getElementsByTag("div");
            if (null != errorMsg && errorMsg.size() > 0) {
              String errorInfo = errorMsg.get(0).text();
              // if(errorInfo.contains("未找到姓名"))
              // errorInfo = "未参加考试";
              modelAndView.addObject("errorInfo", errorInfo);
            } else {
              modelAndView.addObject("errorInfo", "查询成绩异常,请重新登录");
            }
          }
        }
      } catch (Exception e) {
        LoggerUtil.error("页面解析异常", e);
        return showError();
      }
    }
    return modelAndView;
  }

  private ModelAndView showError() {
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
