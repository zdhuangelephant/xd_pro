package com.xiaodou.autotest.web.controller.operation;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionScheduler;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.enums.TimingEnum;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;
import com.xiaodou.autotest.web.request.CaseSendRequest;
import com.xiaodou.autotest.web.request.DocSendRequest;
import com.xiaodou.autotest.web.request.SendByJsonRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.service.mail.MailService;
import com.xiaodou.autotest.web.service.operation.RequestService;
import com.xiaodou.autotest.web.vo.RecordInfoVo;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * Created by zhouhuan on 17.08.15.
 */
@Controller("requestController")
@RequestMapping("/request")
public class RequestController extends BaseController {
  @Resource
  RequestService requestService;

  @Resource
  RequestServiceFacade requestServiceFacade;
  @Resource
  MailService mailService;

  /**
   * 用例发送请求JSON
   * 
   * @param actionInfo 前台根据请求的实际情况包装好的JSON串
   * @return
   * @author zhouhuan
   */
  @RequestMapping("/caseSendByJson")
  @ResponseBody
  public String caseSendByJson(SendByJsonRequest request) {
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      List<Action> actionList = ActionScheduler.getInstance().schedule(request.getActionInfo());
      requestService.insertCaseRequestLog(request);
      map.put("status", "success");
      map.put("data", actionList);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
    return FastJsonUtil.toJson(map);
  }

  /**
   * 文档发送请求JSON
   * 
   * @param actionInfo 前台根据请求的实际情况包装好的JSON串
   * @return
   * @author zhouhuan
   */
  @RequestMapping("/docSendByJson")
  @ResponseBody
  public String docSendByJson(SendByJsonRequest request) {
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      List<Action> actionList = ActionScheduler.getInstance().schedule(request.getActionInfo());
      map.put("status", "success");
      map.put("data", actionList);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
    return FastJsonUtil.toJson(map);
//    return FastJsonUtil.toJson(map);
  }

  /**
   * 单个用例请求发布请求
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/externalSend")
  @ResponseBody
  public String externalSend(String caseId) {
    if (StringUtils.isBlank(caseId)) {
      return new String();
    } ;
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      Action action = requestService.externalSend(caseId);
      Case userCase = requestService.updateCaseResults(action);
      List<RecordInfoVo> recordList = Lists.newArrayList();
      if (StringUtils.isNotBlank(userCase.getRecord())) {
        recordList = FastJsonUtil.fromJsons(userCase.getRecord(),
            new TypeReference<List<RecordInfoVo>>() {});
      }
      map.put("recordList", recordList);
      map.put("status", "success");
      map.put("data", action.getApiList());
      map.put("failCount", requestService.getFailApi(action.getApiList()));
      map.put("time", userCase.getUpdateTime().toString());
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
//    return FastJsonUtil.toJson(map);
    return FastJsonUtil.toJson(map);
  }

  /**
   * 单个用例请求发布请求
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/aosSend")
  @ResponseBody
  public void aosSend(String caseId) {
    if (StringUtils.isBlank(caseId)) {
      return;
    } ;
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      Action action = requestService.externalSend(caseId);
      requestService.updateCaseResults(action);
      mailService.sendMail(action, requestService.getFailApi(action.getApiList()));
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
  }

  /**
   * 批量发送请求
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/batchSend")
  @ResponseBody
  public String batchSend(String caseIds) {
    if (StringUtils.isBlank(caseIds)) {
      return new String();
    } ;
    String[] ids = caseIds.split(",");
    if (ids.length == 0) {
      return new String();
    } ;
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      List<Action> actionList = Lists.newArrayList();
      for (String id : ids) {
        Action action = requestService.externalSend(id);
        requestService.updateCaseResults(action);
        actionList.add(action);
      }
      return FastJsonUtil.toJson(actionList);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
//    return FastJsonUtil.toJson(map);
    return FastJsonUtil.toJson(map);
  }

  /**
   * 定时发送请求
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/timingSend")
  @ResponseBody
  public String timingSend(String time) {
    if (time == null) {
      return new String();
    } ;
    TimingEnum timeEnum = TimingEnum.getInstance(time);
    try {
      if (timeEnum != null) {
        requestService.externalSend(timeEnum);
        return "true";
      } else {
        return "false";
      }
    } catch (Exception e) {
      return StringUtils.toString(e);
    }
  }


  // 实时发送请求
  @RequestMapping("docSend")
  @ResponseBody
  public String docSend(DocSendRequest send) {
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      if (StringUtils.isNotBlank(send.getParams())) {
        URLDecoder.decode(new String(Base64Utils.decode(send.getParams())), "utf8");
      }
      Action action = requestService.docSend(send);
      map.put("status", "success");
      if (action.getApiList().get(0).getApiResult() == null
          || action.getApiList().get(0).getApiResult().getHasError()) {
        map.put("srcResult", "");
      } else {
        map.put("srcResult", action.getApiList().get(0).getApiResult().getSrcResult());
      }
      map.put("requestBody", action.getApiList().get(0).getApiRequest());
      map.put("testCase", action.getApiList().get(0).getTestCases());
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
    return FastJsonUtil.toJson(map);
  }

  // 实时发送请求
  @RequestMapping("caseSend")
  @ResponseBody
  public String caseSend(CaseSendRequest send) {
    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      List<CaseRequestLog> logList = Lists.newArrayList();
      CaseRequestLog log = new CaseRequestLog();
      send.setUserId(this.getUserId().toString());
      Action action = requestService.caseSend(send);
      if (StringUtils.isNotBlank(send.getReqName())) {
        logList = requestService.insertCaseRequestLog(action, send);
        if (logList != null && logList.size() > 0) {
          log = logList.get(0);
        }
      }
      map.put("status", "success");
      if (action.getApiList().get(0).getApiResult() == null
          || action.getApiList().get(0).getApiResult().getHasError()) {
        map.put("srcResult", "");
      } else {
        map.put("srcResult", action.getApiList().get(0).getApiResult().getSrcResult());
      }
      map.put("requestBody", action.getApiList().get(0).getApiRequest());
      map.put("testCase", action.getApiList().get(0).getTestCases());
      map.put("log", log);
    } catch (Exception e) {
      map.put("status", "fail");
      map.put("data", StringUtils.toString(e));
    }
//    return FastJsonUtil.toJson(map);
    return FastJsonUtil.toJson(map);
  }

}
