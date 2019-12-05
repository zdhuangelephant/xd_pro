package com.xiaodou.autotest.web.controller.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.operation.CaseService;
import com.xiaodou.autotest.web.service.operation.DocService;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.autotest.web.controller.operation.CaseController.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2017年09月06日
 * @description Controller
 * @version 1.0
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController {
  @Resource
  DocService docService;
  @Resource
  CaseService caseService;

  /**
   * 首页
   * 
   * @return
   */
  @RequestMapping("/")
  public ModelAndView index(String version, String updateTime) {
    ModelAndView mv = new ModelAndView("/common/index");
    // right slide
    HashMap<String, Object> cond = Maps.newHashMap();

    if (null == version) {
      version = "desc";
    }
    if (null == updateTime) {
      updateTime = "desc";
    }
    // 版本号
    if ("desc".equals(version)) {
      mv.addObject("version", "asc");
    }
    if ("asc".equals(version)) {
      mv.addObject("version", "desc");
    }
    cond.put("version", version);

    // 更新时间
    if ("desc".equals(updateTime)) {
      mv.addObject("updateTime", "asc");
    }
    if ("asc".equals(updateTime)) {
      mv.addObject("updateTime", "desc");
    }
    cond.put("updateTime", updateTime);



    List<Doc> docs = docService.findDocument(null,cond);
    ArrayList<Doc> docList = Lists.newArrayList();
    if (docs != null) {
      for (Doc doc : docs) {
        HashMap<String, Object> input = Maps.newHashMap();
        input.put("docId", doc.getId());
        List<DocRequest> reqsOfDoc = docService.findDocRequests(input);
        doc.setDocReqList(reqsOfDoc);

        doc.setHistoryTime(DateUtil.relativeDateFormat(doc.getUpdateTime()));
        docList.add(doc);
        // if (docList.size() > 5) {
        // break;
        // }
      }
    }
    mv.addObject("docList", docList);

    // left slide
    List<Case> cases = caseService.findAllCases(getUserId(), null);
    ArrayList<Case> caseList = Lists.newArrayList();
    if (null != cases) {
      for (Case c : cases) {
        Case cc = caseService.getStatisticsRate(c);
        caseList.add(cc);
        // if (caseList.size() > 7) {
        // break;
        // }
      }
    }
    mv.addObject("caseList", caseList);
    return mv;
  }

  /**
   * This url gets invoked when spring security invalidates session (ie timeout). Specific content
   * indicates ui layer that session has been invalidated and page should be redirected to logout.
   */
  @RequestMapping(value = "/public/invalidate")
  @ResponseBody
  public String invalidateSession(HttpServletRequest reqeust, HttpServletResponse response) {
    String ajaxHeader = reqeust.getHeader("X-Requested-With");
    boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
    if (isAjax) {
      return "invalidSession";
    } else {
      try {
        response.sendRedirect(reqeust.getContextPath() + "/admin/login");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "";
  }
}
