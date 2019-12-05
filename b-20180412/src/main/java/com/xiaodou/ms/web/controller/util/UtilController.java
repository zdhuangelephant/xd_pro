package com.xiaodou.ms.web.controller.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.major.MajorCourseInfo;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.major.MajorInfo;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.vo.utils.CheckDataVO;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zhouhuan on 16/7/1. 用于抓取页面专业课程数据
 */
@Controller("utilController")
@RequestMapping("/util")
public class UtilController extends BaseController {
  /**
   * 专业
   */
  @Resource
  MajorDataService majorDataService;
  /**
   * 课程
   */
  @Resource
  MajorCourseService majorCourseService;

  /**
   * 获取所有专业数据
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/getData")
  @ResponseBody
  public String getData(HttpServletRequest req, HttpServletResponse res) throws Exception {
    // ModelAndView model = new ModelAndView("common/success");
    String cookie = getCookie("/portal/zhxxcx.jsp");
    String sessionId = getSession(cookie, "", "", "");
    /*
     * getCourseDetail("/showZKZY0070Cpt.action?kcdm=03708&zydm=01C1504",cookie, "01C1504");
     */
    Document document = getDocument(cookie, sessionId);
    Elements spans = document.getElementsByTag("span");
    System.out.print(spans);
    for (Element span : spans) {
      Elements td = span.getElementsByTag("td");
      if (td.size() > 0 && td.hasClass("f15")) {
        td.get(0).text();
        String hrefData = span.attr("onclick");
        int s = hrefData.indexOf("window.open");
        String u = hrefData.substring(s + 13);
        int last = u.indexOf("'");
        String href = u.substring(0, last);
        if (!StringUtil.isBlank(href)) {
          getsubJect("/" + href);
        }
      }
    }
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * 获取单个专业详细信息
   * 
   * @author zhouhuan
   * @param href
   * @return
   */
  private String getsubJect(String href) {
    String cookie = getCookie(href);
    int first = href.indexOf("zydm=");
    int last = href.indexOf("&");
    String srnr = href.substring(first + 5, last); // 专业代码

    if (!srnr.equals("01B0836")) {
      return null;
    }

    String sessionId = getSession(cookie, srnr, "/showZKZY0080Cpt", "");
    try {
      Document document = getDocument(cookie, sessionId);
      Elements trs1 = document.select("table.x-table").select("tr")
          .select("td.brw1,brss,bbw1,bbss,blw1,blss,btw1,btss").select("td.fh,vat,b0,bw");
      Elements trs2 = document.select("table.x-table").select("tr")
          .select("td.br0,bbw1,bbss,bl0,bt0").select("td.fh,b0,bw");

      MajorDataModel md = new MajorDataModel();
      md.setId(trs2.get(0).text());// 专业代码
      md.setName(trs2.get(2).text());// 专业名称
      MajorInfo mi = new MajorInfo();
      mi.setDetail(trs1.get(0).text());
      mi.setExamSchool(trs2.get(9).text());
      mi.setMajorLevel(trs2.get(5).text());
      mi.setDegree(trs2.get(7).text());
      mi.setCreateTime(new Timestamp(System.currentTimeMillis()));
      md.setMajorInfo(FastJsonUtil.toJson(mi));

      majorDataService.addMajor(md);
    } catch (Exception e) {
      LoggerUtil.error("页面解析异常1", e);
      return null;
    }
    getAllCourse(srnr, cookie);

    return null;
  }

  /**
   * 获取单个专业所有课程
   * 
   * @author zhouhuan
   * @param srnr 专业代码
   * @return
   */
  private String getAllCourse(String srnr, String cookie) {
    String sessionId = getSession(cookie, srnr, "/showZKZY0081Cpt", "");
    try {
      Document document = getDocument(cookie, sessionId);
      List<String> a = new ArrayList<String>();
      List<String> b = new ArrayList<String>();
      Elements trs = document.select("table.x-table").select("tr").select("td.bbw1,bbss,btw1,btss")
          .select("tr").select("td.fh,b0,bw");
      for (Element tr : trs) {
        String style = tr.attr("style");
        String className = tr.attr("class");
        if (style.equals("height:28px;width:70px;font-family:微软雅黑;")
            || style.equals("height:32px;width:70px;font-family:微软雅黑;")) {
          a.add(tr.text());
        }
        if ((style.equals("height:28px;width:0px;") || style.equals("height:32px;width:0px;"))
            && className.equals("fh b0 bw")) {
          b.add(tr.text());
          
        }
      }
      for (int i = 0; i < a.size(); i++) {
        String href = "/showZKZY0070Cpt.action?" + "kcdm=" + b.get(i) + "&zydm=" + srnr;
        getCourseDetail(href, cookie, srnr, a.get(i));
      }
    } catch (Exception e) {
      LoggerUtil.error("页面解析异常2", e);
      return null;
    }
    return null;
  }

  /**
   * 获取单个课程详细信息
   * 
   * @author zhouhuan
   * @param srnr 专业代码
   * @param cookie
   * @param href
   * @param courseType 课程类型
   * @return
   */
  private void getCourseDetail(String href, String cookie, String srnr, String courseType) {
    int first = href.indexOf("kcdm=");
    int last = href.indexOf("&");
    String kcdm = href.substring(first + 5, last);
    String sessionId = getSession(cookie, srnr, "", kcdm);
    try {
      Document document = getDocument(cookie, sessionId);
      Elements trs = document.select("table.x-table").select("td.fh,b0,bw");
      MajorCourseModel majorCourseModel = new MajorCourseModel();
      // MajorCourseModel model = new MajorCourseModel();
      MajorCourseModel model = null;
      if (!StringUtil.isBlank(trs.get(5).text())) {
        model = majorCourseService.findCourseById(trs.get(5).text());
        if(null != model) {
          majorCourseService.deleteById(model.getId());
        }
      }
        majorCourseModel.setName(trs.get(1).text());
        majorCourseModel.setId(trs.get(5).text());
        MajorCourseInfo mc = new MajorCourseInfo();
        Elements trs2 = document.select("table.x-table")
            .select("td.brw1,brss,bbw1,bbss,blw1,blss,btw1,btss").select("td.fh,tac,b0,bw");
        StringBuffer s = new StringBuffer("");
        int flag = 0;
        for (Element e : trs2) {
          String style = e.attr("style");
          if (style.equals("height:28px;width:593px;font-family:微软雅黑;")
              && !e.text().equals("考试日期和时间")) {
            s.append(e.text()).append(";");
            flag++;
          }
        }
        if (flag == 2) {
          mc.setExamDateType("2");
        } else if (flag == 1) {
          if (s.toString().indexOf("04月") > 0) {
            mc.setExamDateType("0");
          } else if (s.toString().indexOf("10月") > 0) {
            mc.setExamDateType("1");
          } else {
            mc.setExamDateType("");
          }
        } else {
          mc.setExamDateType("");
        }
        mc.setCredit(trs.get(29).text());
        mc.setExamDate(s.toString());
        if (StringUtil.isBlank(s.toString())) {
          mc.setMode("非笔试");
        } else {
          mc.setMode("笔试");
        }
        mc.setCourseType(courseType);
        mc.setCreateTime(new Timestamp(System.currentTimeMillis()));
        mc.setDetail("");
        majorCourseModel.setMajorCourseInfo(FastJsonUtil.toJson(mc));
        majorCourseService.addCourse(majorCourseModel);
    } catch (Exception e) {
      LoggerUtil.error("页面解析异常3", e);
    }

  }

  /**
   * 获取cookie
   * 
   * @author zhouhuan
   * @param href
   * @return
   */
  private String getCookie(String href) {
    HttpUtil http = HttpUtil.getInstance("zkxcx.bjeea.cn", 80, "http");
    http.getClient().getParams().setContentCharset("UTF-8");
    HttpMethod method = HttpMethodUtil.getGetMethod(href);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    http.setMethod(method);
    HttpResult httpResult = http.getHttpResult();
    Cookie[] cookies = httpResult.getCookies();
    return cookies[0].toString();

  }

  /**
   * 获取session
   * 
   * @author zhouhuan
   * @param srnr 专业代码
   * @param kcdm 课程代码
   * @return
   */
  private String getSession(String cookie, String srnr, String href, String kcdm) {
    HttpUtil http = HttpUtil.getInstance("zkxcx.bjeea.cn", 80, "http");
    http.getClient().getParams().setContentCharset("UTF-8");
    String methodHref = "";
    if (srnr.equals("")) {
      methodHref = "/showZKZY0030NewCpt.action";
    } else if (kcdm.equals("")) {
      methodHref = href + ".action?srnr=" + srnr;
    } else {
      methodHref = "/showZKZY0070Cpt.action?kcdm=" + kcdm + "&zydm=" + srnr;
    }
    HttpMethod method = HttpMethodUtil.getGetMethod(methodHref);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    method.addRequestHeader("Cookie", cookie);
    http.setMethod(method);
    HttpResult httpResult = http.getHttpResult();
    httpResult = http.getHttpResult();
    Document doc = Jsoup.parse(httpResult.getContent());
    Elements eles = doc.getElementsByTag("script");
    String sessionId = "";
    for (Element ele : eles) {
      String script = ele.toString();
      if (script.indexOf("sessionID") > -1) {
        int t = script.indexOf("sessionID");
        sessionId = script.substring(t + 10);
        int last = sessionId.indexOf("'");
        sessionId = sessionId.substring(0, last);
      }
    }
    return sessionId;
  }

  /**
   * 获取网页数据
   * 
   * @param cookie
   * @param sessionId
   * @return
   */
  private Document getDocument(String cookie, String sessionId) {
    HttpUtil http = HttpUtil.getInstance("zkxcx.bjeea.cn", 80, "http");
    http.getClient().getParams().setContentCharset("UTF-8");
    NameValuePair[] params = new NameValuePair[3];
    params[0] = new NameValuePair("op", "page_content");
    params[1] = new NameValuePair("sessionID", sessionId);
    params[2] = new NameValuePair("pn", "0");
    HttpMethod method = HttpMethodUtil.getPostMethod("/ReportServer", params);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    method.addRequestHeader("Cookie", cookie);
    http.setMethod(method);
    HttpResult httpResult = http.getHttpResult();
    Document document = Jsoup.parse(httpResult.getContent());
    return document;
  }


  @RequestMapping("/toGetInputDataDetail")
  public ModelAndView toInputDataDetail() {
    ModelAndView mv = new ModelAndView("/config/checkDetail");
    return mv;
  }

  @RequestMapping("/getInputDataDetail")
  @ResponseBody
  public String getInputDataDetail(String decrypeData) {
    try {
      CheckDataVO vo = checkInputDataDetail(decrypeData);
      return FastJsonUtil.toJson(vo);
    } catch (Exception e) {
      LoggerUtil.error("校验异常", e);
      throw e;
    }
  }

  private CheckDataVO checkInputDataDetail(String normalData) {
    CheckDataVO vo = new CheckDataVO();
    if (StringUtils.isBlank(normalData)) {
      return vo;
    }
    char[] charArray = normalData.toCharArray();
    vo.setCharsCounts(charArray.length);
    vo.setBytesCounts(normalData.getBytes().length);
    vo.setContent(normalData);;
    return vo;
  }
}
