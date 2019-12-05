package com.xiaodou.autotest.web.service.operation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.ApiStatus;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.core.ActionScheduler;
import com.xiaodou.autotest.core.deserialize.TransferHelper;
import com.xiaodou.autotest.core.deserialize.dmodel.MidAction;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.core.model.AfterSet;
import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.autotest.core.model.PreCondition;
import com.xiaodou.autotest.core.model.PreSet;
import com.xiaodou.autotest.core.model.TestCase;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.enums.TimingEnum;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;
import com.xiaodou.autotest.web.request.CaseSendRequest;
import com.xiaodou.autotest.web.request.DocSendRequest;
import com.xiaodou.autotest.web.request.SendByJsonRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.service.queue.QueueService;
import com.xiaodou.autotest.web.vo.RecordInfoVo;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

@Service("requestService")
public class RequestService {
  @Resource
  RequestServiceFacade requestServiceFacade;
  @Resource
  QueueService queueService;

  /**
   * 增加请求日志
   * 
   * @author zhouhuan
   * @return
   */
  public boolean insertCaseRequestLog(SendByJsonRequest send) {
    String actionInfo = send.getActionInfo();
    if (StringUtils.isJsonBlank(actionInfo)) {return false;};
    if (!actionInfo.startsWith("[")) {actionInfo = "[" + actionInfo;};
    if (!actionInfo.endsWith("]")) {actionInfo = actionInfo + "]";};
    List<MidAction> dActionList =
        FastJsonUtil.fromJsons(actionInfo, new TypeReference<List<MidAction>>() {});
    List<Action> actionList = TransferHelper.transferList(dActionList);
    for (Action action : actionList) {
      this.insertCaseRequestLog(action, send.getUserId(), send.getDesc(), send.getCollectionId());
    }
    return true;
  }

  /**
   * 增加请求日志
   * 
   * @author zhouhuan
   * @return
   */
  public void insertCaseRequestLog(Action action, String userId, String desc, String collectionId) {
    for (Api api : action.getApiList()) {
      CaseRequestLog caseRequestLog = new CaseRequestLog();
      caseRequestLog.setId(RandomUtil.randomString(20));
      caseRequestLog.setUrl(api.getUrl());
      caseRequestLog.setUserId(userId);
      caseRequestLog.setCaseDesc(desc);
      if (api.getProtocol() != null) {caseRequestLog.setProtocol(api.getProtocol().toString());};
      if (api.getMethod() != null) {caseRequestLog.setMethod(api.getMethod().toString());};
      if (api.getFormat() != null) {caseRequestLog.setFormat(api.getFormat().toString());};
      caseRequestLog.setName(api.getName());
      caseRequestLog.setCollectionId(collectionId);
      caseRequestLog.setParams(Base64Utils.encode(FastJsonUtil.toJson(api.getParams()).getBytes()));
      caseRequestLog.setPreConds(Base64Utils.encode(FastJsonUtil.toJson(api.getPreConds())
          .getBytes()));
      caseRequestLog
          .setPreSets(Base64Utils.encode(FastJsonUtil.toJson(api.getPreSets()).getBytes()));
      caseRequestLog.setAfterSets(Base64Utils.encode(FastJsonUtil.toJson(api.getAfterSets())
          .getBytes()));
      caseRequestLog.setTestCases(Base64Utils.encode(FastJsonUtil.toJson(api.getTestCases())
          .getBytes()));
      caseRequestLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
      caseRequestLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      requestServiceFacade.saveRequestLog(caseRequestLog);
    }
  }

  /**
   * 增加请求日志
   * 
   * @author zhouhuan
   * @return
   */
  public List<CaseRequestLog> insertCaseRequestLog(Action action, CaseSendRequest send) {
    List<CaseRequestLog> logList = Lists.newArrayList();
    for (Api api : action.getApiList()) {
      CaseRequestLog caseRequestLog = new CaseRequestLog();
      caseRequestLog.setId(RandomUtil.randomString(20));
      caseRequestLog.setUrl(api.getUrl());
      caseRequestLog.setUserId(send.getUserId());
      caseRequestLog.setCaseDesc(send.getCaseDesc());
      if (api.getProtocol() != null) {caseRequestLog.setProtocol(api.getProtocol().toString());};
      if (api.getMethod() != null) {caseRequestLog.setMethod(api.getMethod().toString());};
      if (api.getFormat() != null) {caseRequestLog.setFormat(api.getFormat().toString());};
      caseRequestLog.setName(send.getReqName());
      caseRequestLog.setCollectionId(send.getCaseId());
      caseRequestLog.setParams(send.getParams());
      caseRequestLog.setPreConds(send.getPreConds());
      caseRequestLog.setPreSets(send.getPreSets());
      caseRequestLog.setAfterSets(send.getAfterSets());
      caseRequestLog.setTestCases(send.getTestCases());
      caseRequestLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
      caseRequestLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      logList.add(requestServiceFacade.saveRequestLog(caseRequestLog));
    }
    return logList;
  }

  /**
   * (异步)定时发送请求
   * 
   * @author zhouhuan
   * @return
   */
  public void externalSend(TimingEnum time) {
    // TODO Auto-generated method stub
    Map<String, Object> input = Maps.newHashMap();
    input.put("timingTaskDesc", time.getCode());
    List<Case> caseList = requestServiceFacade.getCaseByCond(input);
    if (caseList != null && caseList.size() > 0) {
      for (Case c : caseList) {
        Map<String, Object> input2 = Maps.newHashMap();
        input2.put("collectionId", c.getId());
        List<CaseRequest> requestList = requestServiceFacade.getCaseRequestModelByCond(input2);
        Action action = this.packageAction(requestList, c);
        queueService.executeTask(action);
      }
    }
  }

  /**
   * 单个集合发送请求
   * 
   * @author zhouhuan
   * @return
   */
  public Action externalSend(String id) {
    // TODO Auto-generated method stub
    Case userCase = requestServiceFacade.getCaseById(id);
    Map<String, Object> input = Maps.newHashMap();
    input.put("collectionId", id);
    List<CaseRequest> requestList = requestServiceFacade.getCaseRequestModelByCond(input);
    Action action = this.packageAction(requestList, userCase);
    ActionScheduler.getInstance().schedule(action);
    return action;
  }

  /**
   * 包装Action
   * 
   * @author zhouhuan
   * @return
   */
  public Action packageAction(List<CaseRequest> requestList, Case userCase) {
    Action action = new Action();
    action.setBusinessLine(userCase.getBusinessLine());
    action.setName(userCase.getName());
    action.setVersion(userCase.getVersion());
    action.setActionId(userCase.getId());
    for (CaseRequest request : requestList) {
      action.getApiList().add(packageApi(request, userCase));
    }
    return action;
  }

  /**
   * 包装Api
   * 
   * @author zhouhuan
   * @return
   */
  public Api packageApi(CaseRequest request, Case userCase) {
    Api api = new Api();
    api.setFormat(request.getFormat());
    api.setMethod(request.getMethod());
    api.setName(request.getName());
    api.setProtocol(request.getProtocol());
    api.setUrl(request.getUrl());
    api.setVersion(userCase.getVersion());
    try {
      if (StringUtils.isNotBlank(request.getParams())) {
        String param =
            URLDecoder.decode(new String(Base64Utils.decode(request.getParams())), "utf8");
        List<Param> params = FastJsonUtil.fromJsons(param, new TypeReference<List<Param>>() {});
        if (request.getFormat().equals(ActionConstant.DEFAULT_FORMAT.toString())) {
          api.setParams(params);
        } else {
          for (Param p : params) {
            if (p.getParamType().equals(ParamType.QueryParam.toString())) {
              api.setParamValue("json", p.getParamValue());
            } else {
              api.registParam(p.getName(), p.getDataType(), p.getParamType(), p.getDesc(),
                  p.getParamValue());
            }
          }
        }
      }
      if (StringUtils.isNotBlank(request.getPreConds())) {
        String preconds =
            URLDecoder.decode(new String(Base64Utils.decode(request.getPreConds())), "utf-8");

        List<PreCondition> precondList =
            FastJsonUtil.fromJsons(preconds, new TypeReference<List<PreCondition>>() {});
        for (PreCondition pr : precondList) {
            api.pushPreCond(pr.getKey(), pr.getTargetValue(), pr.getCondition());
          }
      }
      if (StringUtils.isNotBlank(request.getPreSets())) {
        String presets =
            URLDecoder.decode(new String(Base64Utils.decode(request.getPreSets())), "utf-8");
        List<PreSet> perSetList =
            FastJsonUtil.fromJsons(presets, new TypeReference<List<PreSet>>() {});
        for (PreSet ps : perSetList) {
           api.pushPreSet(ps.getKey(), ps.getDataSource(), ps.getAssignment(), ps.getScope(), ps.getDataType());
          }
      }
      if (StringUtils.isNotBlank(request.getTestCases())) {
        String testcases =
            URLDecoder.decode(new String(Base64Utils.decode(request.getTestCases())), "utf-8");

        List<TestCase> testCaseList =
            FastJsonUtil.fromJsons(testcases, new TypeReference<List<TestCase>>() {});
        for (TestCase tc : testCaseList) {
          api.pushTestCase(tc.getName(), tc.getTestKey(), tc.getTestValue(), tc.getDataType());
        }
      }
      if (StringUtils.isNotBlank(request.getAfterSets())) {
        String aftersets =
            URLDecoder.decode(new String(Base64Utils.decode(request.getAfterSets())), "utf-8");
        List<AfterSet> afterList =
            FastJsonUtil.fromJsons(aftersets, new TypeReference<List<AfterSet>>() {});
        for (AfterSet as : afterList) {
           api.pushAfterSet(as.getResultkey(), as.getMappingKey(), as.getDataType());
         }
      }
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return api;
  }

  public Case updateCaseResults(Action action) {
	Case userCase = requestServiceFacade.getCaseById(action.getActionId());
    if (action.getApiList() != null && action.getApiList().size() > 0) {
      userCase.setResults(FastJsonUtil.toJson(action.getApiList()));
    } else {
      userCase.setResults("[]");
    }
    userCase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    Integer failCount=this.getFailApi(action.getApiList());
    userCase.setFailCount(failCount);
    String record=userCase.getRecord();
    List<RecordInfoVo> recordList=Lists.newArrayList();
    if(StringUtils.isNotBlank(record)){
    	recordList=FastJsonUtil.fromJsons(record, new TypeReference<List<RecordInfoVo>>() {});    
    }
    RecordInfoVo recordVo=new RecordInfoVo();
    recordVo.setFailCount(failCount);
    if(failCount>0){	
        userCase.setCaseFailCount(userCase.getCaseFailCount()+1);
        recordVo.setRecord(Cons.FAIL);         
    }else{
    	userCase.setCaseSuccessCount(userCase.getCaseSuccessCount()+1);   
        recordVo.setRecord(Cons.SUCCESSD);
    }
    recordVo.setResult(FastJsonUtil.toJson(action.getApiList()));
    recordVo.setTime(new Timestamp(System.currentTimeMillis()).toString());
    recordVo.setName(action.getName());
    recordList.add(0, recordVo);   
    if(recordList.size()==6){
    	recordList.remove(5);
    }
    userCase.setRecord(FastJsonUtil.toJson(recordList));
    requestServiceFacade.updateCase(userCase);
    return userCase;
  }

  public Integer getFailApi(List<Api> list) {
    Integer failCount = 0;
    for (Api api : list) {
      if (api.getStatus()==ApiStatus.UnExcute||api.getApiResult() == null || api.getApiResult().getHasError()) {
        failCount++;
      }
    }
    return failCount;
  }
  

  public Action docSend(DocSendRequest send) {
    Action action = new Action();
    action.setBusinessLine("临时业务线");
    action.setName("临时名称");
    action.setVersion("临时版本");
    Api api = new Api();
    api.setMethod(send.getMethod());
    api.setName("临时测试");
    api.setProtocol(Protocol.http.toString());
    api.setUrl(send.getUrl());
    api.setVersion("临时版本");
    api.setFormat(send.getFormat());
    try {
      String param = URLDecoder.decode(new String(Base64Utils.decode(send.getParams())), "utf8");
      String preconds =
          URLDecoder.decode(new String(Base64Utils.decode(send.getPreConds())), "utf-8");
      String presets =
          URLDecoder.decode(new String(Base64Utils.decode(send.getPreSets())), "utf-8");
      String testcases =
          URLDecoder.decode(new String(Base64Utils.decode(send.getTestCases())), "utf-8");
      String aftersets =
          URLDecoder.decode(new String(Base64Utils.decode(send.getAfterSets())), "utf-8");
      if (param.equals("")){param = "[]";};
      if (preconds.equals("")){preconds = "[]";};
      if (presets.equals("")) {presets = "[]";};
      if (testcases.equals("")) {testcases = "[]";};
      if (aftersets.equals("")){aftersets = "[]";};
      List<Param> params = FastJsonUtil.fromJsons(param, new TypeReference<List<Param>>() {});
      if (send.getFormat().equals(ActionConstant.DEFAULT_FORMAT.toString())) {
        api.setParams(params);
      } else {
        for (Param p : params) {
          if (p.getParamType().equals(ParamType.QueryParam.toString())) {
            api.setParamValue("json", p.getParamValue());
          } else {
            api.registParam(p.getName(), p.getDataType(), p.getParamType(), p.getDesc(),
                p.getParamValue());
          }
        }
      }
      List<PreCondition> precondList =
          FastJsonUtil.fromJsons(preconds, new TypeReference<List<PreCondition>>() {});
      for (PreCondition pr : precondList) {
          api.pushPreCond(pr.getKey(), pr.getTargetValue(), pr.getCondition());
        }
      List<PreSet> perSetList =
          FastJsonUtil.fromJsons(presets, new TypeReference<List<PreSet>>() {});
      for (PreSet ps : perSetList) {
          api.pushPreSet(ps.getKey(), ps.getDataSource(), ps.getAssignment(), ps.getScope(), ps.getDataType());
         }
      List<TestCase> testCaseList =
          FastJsonUtil.fromJsons(testcases, new TypeReference<List<TestCase>>() {});
      for (TestCase tc : testCaseList) {
        api.pushTestCase(tc.getName(), tc.getTestKey(), tc.getTestValue(), tc.getDataType());
      }
      List<AfterSet> afterList =
          FastJsonUtil.fromJsons(aftersets, new TypeReference<List<AfterSet>>() {});
      for (AfterSet as : afterList) {
          api.pushAfterSet(as.getResultkey(), as.getMappingKey(), as.getDataType());
        }
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    action.registApi(api);
    ActionScheduler.getInstance().schedule(action);
    return action;
  }


  public Action caseSend(CaseSendRequest send) {
    Action action = new Action();
    action.setBusinessLine("临时业务线");
    action.setName("临时名称");
    action.setVersion("临时版本");
    Api api = new Api();
    api.setMethod(send.getMethod());
    if (StringUtils.isBlank(send.getReqName())) {
      api.setName("日志临时请求");
    } else {
      api.setName(send.getReqName());
    }
    api.setProtocol(Protocol.http.toString());
    api.setUrl(send.getUrl());
    api.setVersion("临时版本");
    api.setFormat(send.getFormat());
    try {
      String param = "";
      String preconds = "";
      String presets = "";
      String testcases = "";
      String aftersets = "";

      if (send != null && StringUtils.isNotBlank(send.getParams())) {
        param = URLDecoder.decode(new String(Base64Utils.decode(send.getParams())), "utf8");
      }
      if (send != null && StringUtils.isNotBlank(send.getPreConds())) {
        preconds = URLDecoder.decode(new String(Base64Utils.decode(send.getPreConds())), "utf-8");
      }
      if (send != null && StringUtils.isNotBlank(send.getPreSets())) {
        presets = URLDecoder.decode(new String(Base64Utils.decode(send.getPreSets())), "utf-8");
      }
      if (send != null && StringUtils.isNotBlank(send.getTestCases())) {
        testcases = URLDecoder.decode(new String(Base64Utils.decode(send.getTestCases())), "utf-8");
      }
      if (send != null && StringUtils.isNotBlank(send.getAfterSets())) {
        aftersets = URLDecoder.decode(new String(Base64Utils.decode(send.getAfterSets())), "utf-8");
      }
      if (param.equals("")){param = "[]";};
      if (preconds.equals("")){preconds = "[]";};
      if (presets.equals("")) {presets = "[]";};
      if (testcases.equals("")) {testcases = "[]";};
      if (aftersets.equals("")){aftersets = "[]";};
      List<Param> params = FastJsonUtil.fromJsons(param, new TypeReference<List<Param>>() {});
      if (send.getFormat().equals(ActionConstant.DEFAULT_FORMAT.toString())) {
        api.setParams(params);
      } else {
        for (Param p : params) {
          if (p.getParamType().equals(ParamType.QueryParam.toString())) {
            api.setParamValue("json", p.getParamValue());
          } else {
            api.registParam(p.getName(), p.getDataType(), p.getParamType(), p.getDesc(),
                p.getParamValue());
          }
        }
      }
      List<PreCondition> precondList =
              FastJsonUtil.fromJsons(preconds, new TypeReference<List<PreCondition>>() {});
      for (PreCondition pr : precondList) {
          api.pushPreCond(pr.getKey(), pr.getTargetValue(), pr.getCondition());
        }
      List<PreSet> perSetList =
          FastJsonUtil.fromJsons(presets, new TypeReference<List<PreSet>>() {});
      for (PreSet ps : perSetList) {
          api.pushPreSet(ps.getKey(), ps.getDataSource(), ps.getAssignment(), ps.getScope(), ps.getDataType());
         }
      List<TestCase> testCaseList =
          FastJsonUtil.fromJsons(testcases, new TypeReference<List<TestCase>>() {});
      for (TestCase tc : testCaseList) {
        api.pushTestCase(tc.getName(), tc.getTestKey(), tc.getTestValue(), tc.getDataType());
      }
      List<AfterSet> afterList =
          FastJsonUtil.fromJsons(aftersets, new TypeReference<List<AfterSet>>() {});
      for (AfterSet as : afterList) {
          api.pushAfterSet(as.getResultkey(), as.getMappingKey(), as.getDataType());
        }
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    action.registApi(api);
    ActionScheduler.getInstance().schedule(action);
    return action;
  }
}
