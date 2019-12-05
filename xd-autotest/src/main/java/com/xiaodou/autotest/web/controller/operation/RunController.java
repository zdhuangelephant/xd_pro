package com.xiaodou.autotest.web.controller.operation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.vo.RecordInfoVo;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * Created by zhouhuan on 17.08.15.
 */
@Controller("runController")
@RequestMapping("/run")
public class RunController extends BaseController {



  /**
   * RUNNER获取case列表
   * 
   * @author zhouhuan
   * @return
   */
  @Resource
  RequestServiceFacade requestServiceFacade;


  @RequestMapping("/runner")
  public ModelAndView runner() {
    ModelAndView modelAndView = new ModelAndView("run/runner");
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", this.getUserId().toString());
    List<Case> caseList = requestServiceFacade.getCaseByCond(input);
    modelAndView.addObject("caseList", caseList);
    return modelAndView;
  }

  /**
   * RUNNER获取case结果
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/getCase")
  @ResponseBody
  public String getCase(String caseId) {
    Map<String, Object> map = Maps.newHashMap();
    try {
      Map<String, Object> input = Maps.newHashMap();
      input.put("userId", this.getUserId().toString());
      input.put("id", caseId);
      List<Case> caseList = requestServiceFacade.getCaseByCond(input);
      if (caseList != null && caseList.size() > 0 && caseList.get(0).getResults() != null) {
        List<Api> api =
            FastJsonUtil.fromJsons(caseList.get(0).getResults(), new TypeReference<List<Api>>() {});
        map.put("data", api);
        if (caseList.get(0).getUpdateTime() != null) {
          map.put("time", caseList.get(0).getUpdateTime().toString());
        } else {
          map.put("time", caseList.get(0).getCreateTime().toString());
        }
        List<RecordInfoVo> recordList = Lists.newArrayList();
        if (StringUtils.isNotBlank(caseList.get(0).getRecord())) {
          recordList =
              FastJsonUtil.fromJsons(caseList.get(0).getRecord(),
                  new TypeReference<List<RecordInfoVo>>() {});
        }
        map.put("recordList", recordList);
        map.put("failCount", caseList.get(0).getFailCount());
        map.put("name", caseList.get(0).getName());
      }
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 策略列表
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/strategyList")
  public ModelAndView strategyList() {
    ModelAndView modelAndView = new ModelAndView("strategy/strategyList");
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", this.getUserId().toString());
    List<Case> caseList = requestServiceFacade.getCaseByCond(input);
    modelAndView.addObject("caseList", caseList);
    return modelAndView;
  }

  /**
   * 修改策略
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/editStrategy")
  @ResponseBody
  public String editStrategy(String caseId, String timingTaskDesc) {
    if (StringUtils.isBlank(caseId) || StringUtils.isBlank(timingTaskDesc)) {
      return null;
    };
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      Case userCase = new Case();
      userCase.setId(caseId);
      userCase.setTimingTaskDesc(timingTaskDesc);
      requestServiceFacade.updateCase(userCase);
      map.put("status", "success");
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", e.toString());
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 展示文档
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/doc")
  @ResponseBody
  public ModelAndView doc(String docId) {
    if (StringUtils.isBlank(docId)) {
      return null;
    };
    ModelAndView modelAndView = new ModelAndView("doc/docView");
    Doc doc = requestServiceFacade.getDocById(docId);
    if (doc == null) {
      return null;
    };
    doc.setMockUrl("http://autotest.51xiaodou.com/mock/" + doc.getId() + "/");
    Map<String, Object> input = Maps.newHashMap();
    input.put("docId", docId);
    List<DocRequest> docRequestList = requestServiceFacade.getDocRequestModelByCond(input);
    for (DocRequest docReq : docRequestList) {
      String params = "";
      String outParams = "";
      try {
        params = URLDecoder.decode(new String(Base64Utils.decode(docReq.getParams())), "utf8");
        outParams =
            URLDecoder.decode(new String(Base64Utils.decode(docReq.getOutParams())), "utf8");
        if (StringUtils.isNotBlank(params)) {
          List<Param> paramList =
              FastJsonUtil.fromJsons(params, new TypeReference<List<Param>>() {});
          docReq.setParamList(paramList);
        }
        docReq.setOutParams(outParams);
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      }
    }
    modelAndView.addObject("doc", doc);
    modelAndView.addObject("docRequestList", docRequestList);
    return modelAndView;
  }
}
