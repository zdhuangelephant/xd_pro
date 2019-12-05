package com.xiaodou.autotest.web.service.operation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.model.Param;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.vo.RecordInfoVo;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.web.model.operation.Request.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月15日
 * @description
 * @version 1.0
 */

@Service("caseService")
public class CaseService {
  @Resource
  RequestServiceFacade requestServiceFacade;

  public List<Case> findAllCases(Integer userId, String share) {
    HashMap<String, Object> input = Maps.newHashMap();
    if (userId != null && StringUtils.isNotBlank(userId.toString())) {
      input.put("userId", userId);
    }
    if (share != null && StringUtils.isNotBlank(share)) {
      input.put("share", share);
    }
    return requestServiceFacade.getCaseByCond(input);
  }


  public List<CaseRequest> findCaseRequests(Map<String, Object> inputs) {
    return requestServiceFacade.getCaseRequestModelByCond(inputs);
  }

  public Case insertCase(Case caseArgs) {
    caseArgs.setCreateTime(new Timestamp(System.currentTimeMillis()));
    caseArgs.setId(UUID.randomUUID().toString().substring(0, 16));
    caseArgs.setUpdateTime(caseArgs.getCreateTime());
    return requestServiceFacade.saveCase(caseArgs);
  }

  public Boolean removeCase(String caseId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("collectionId", caseId);
    List<CaseRequest> lists = requestServiceFacade.getCaseRequestModelByCond(input);
    if (lists != null && lists.size() > 0) {
      for (CaseRequest caseRequest : lists) {
        requestServiceFacade.deleteCaseRequest(caseRequest.getId());
      }
    }
    return requestServiceFacade.deleteCase(caseId);
  }

  public Case findCaseById(String id) {
    return requestServiceFacade.getCaseById(id);
  }

  public Boolean updateCollection(Case caseArgs) {
    caseArgs.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return requestServiceFacade.updateCase(caseArgs);
  }

  public Map<String, Object> generVersion(String docId, String newVersion, String newVersionCode) {
    HashMap<String, Object> errors = Maps.newHashMap();
    Case caseArgs = findCaseById(docId);
    if (caseArgs == null) {
      errors.put("status", "false");
      errors.put("msg", "目标Case不存在");
      return errors;
    }
    caseArgs.setVersion(newVersionCode);
    caseArgs.setName(newVersion);


    HashMap<String, Object> input = Maps.newHashMap();
    input.put("collectionId", docId);
    List<CaseRequest> docReqList = findCaseRequests(input);
    if (docReqList == null || docReqList.size() == 0) {
      errors.put("status", "false");
      errors.put("msg", "该Case下面没有挂载CaseRequest，操作失败");
      return errors;
    }

    Case storeCase = new Case();
    storeCase.setBusinessLine(caseArgs.getBusinessLine());
    storeCase.setCaseDesc(caseArgs.getCaseDesc());
    storeCase.setCaseRequests(caseArgs.getCaseRequests());
    storeCase.setCount(caseArgs.getCount());
    storeCase.setFailCount(caseArgs.getFailCount());
    storeCase.setName(caseArgs.getName());
    storeCase.setResults(caseArgs.getResults());
    storeCase.setTimingTaskDesc(caseArgs.getTimingTaskDesc());
    storeCase.setUserId(caseArgs.getUserId());
    storeCase.setVersion(caseArgs.getVersion());
    Case wrapCase = insertCase(storeCase);
    if (wrapCase == null) {
      errors.put("status", "false");
      errors.put("msg", "生成新版本Case失败");
      return errors;
    }


    ArrayList<Map<String, Object>> caseLists = Lists.newArrayList();

    try {
      for (CaseRequest dr : docReqList) {
        CaseRequest cr = new CaseRequest();
        cr.setCollectionId(wrapCase.getId());
        cr.setAfterSets(dr.getAfterSets());
        cr.setCaseRequestDesc(dr.getCaseRequestDesc());
        cr.setCreateTime(new Timestamp(System.currentTimeMillis()));
        cr.setFormat(dr.getFormat());
        cr.setId(UUID.randomUUID().toString().substring(0, 16));
        cr.setMethod(dr.getMethod());
        cr.setName(dr.getName());


        String tmp = URLDecoder.decode(new String(Base64Utils.decode(dr.getParams())), "utf8");
        // 版本更新连带Header头部version也要更新
        if (StringUtils.isNotBlank(tmp)) {
          List<Param> psLists = FastJsonUtil.fromJsons(tmp, new TypeReference<List<Param>>() {});
          if (psLists != null && psLists.size() > 0) {
            for (Param param : psLists) {
              if (param == null) {
                continue;
              }
              if (param.getParamType().toString().equals(ParamType.HeaderParam.toString())
                  && param.getName().equals("version")) {
                param.setParamValue(newVersionCode);
              }
            }
          }
          cr.setParams(Base64Utils.encode(FastJsonUtil.toJson(psLists).getBytes()));
        } else {
          cr.setParams(tmp);
        }

        // cr.setParams(dr.getParams());
        cr.setPreConds(dr.getPreConds());
        cr.setPreSets(dr.getPreSets());
        cr.setProtocol(dr.getProtocol());
        cr.setSort(dr.getSort());
        cr.setTestCases(dr.getTestCases());
        cr.setUrl(dr.getUrl());
        cr.setUserId(dr.getUserId());

        insertCaseRequest(cr);

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("crId", cr.getId());
        map.put("crDocId", cr.getCollectionId());
        map.put("crName", cr.getName());
        caseLists.add(map);
      }
    } catch (Exception e) {
      errors.put("status", "false");
      errors.put("msg", "生成新版本中的CaseRequest失败");
      return errors;
    }
    errors.put("status", "true");
    errors.put("msg", "操作成功");
    errors.put("caseData", wrapCase);
    errors.put("caseRequests", caseLists);
    return errors;
  }

  public CaseRequest saveCaseRequest(CaseRequest cr) {
    return requestServiceFacade.saveCaseRequest(cr);
  }

  public Boolean updateCaseRequest(CaseRequest docRequest) {
    docRequest.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return requestServiceFacade.updateCaseRequest(docRequest);
  }

  public CaseRequest insertCaseRequest(CaseRequest docRequest) {
    return requestServiceFacade.saveCaseRequest(docRequest);
  }

  public CaseRequest findCaseRequestById(String id) {
    return requestServiceFacade.getCaseRequestById(id);
  }

  public Boolean removeCaseReq(String id) {
    // TODO Auto-generated method stub
    return requestServiceFacade.deleteCaseReq(id);
  }

  public Map<String, Object> importCase(Integer userId, String line) {
    HashMap<String, Object> errors = Maps.newHashMap();

    Case caseArgs = FastJsonUtil.fromJson(line, Case.class);
    if (caseArgs == null) {
      errors.put("retCode", "-1");
      errors.put("msg", "请上传正确的json格式文件");
      return errors;
    }

    caseArgs.setUserId(userId + "");

    Case dbCase = insertCase(caseArgs);
    if (dbCase == null) {
      errors.put("retCode", "-1");
      errors.put("msg", "插入Case有误");
      return errors;
    }

    ArrayList<CaseRequest> lists = Lists.newArrayList();

    List<CaseRequest> crLists = caseArgs.getCaseRequests();
    if (crLists != null && crLists.size() > 0) {

      for (CaseRequest caseRequest : crLists) {
        System.out.println(caseRequest.getId());
        caseRequest.setId(UUID.randomUUID().toString().substring(0, 20));
        caseRequest.setUserId(userId + "");
        caseRequest.setCollectionId(dbCase.getId());
        caseRequest.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
          caseRequest.setParams(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getParams(), "utf8").getBytes()));
          caseRequest.setPreConds(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getPreConds(), "utf8").getBytes()));
          caseRequest.setPreSets(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getPreSets(), "utf8").getBytes()));
          caseRequest.setTestCases(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getTestCases(), "utf8").getBytes()));
          caseRequest.setAfterSets(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getAfterSets(), "utf8").getBytes()));

        } catch (UnsupportedEncodingException e) {
          LoggerUtil.error("导入失败", e);
          continue;
        }
        insertCaseRequest(caseRequest);
        lists.add(caseRequest);
      }
    }
    errors.put("retCode", "0");
    errors.put("msg", "导入成功");
    errors.put("caseId", dbCase.getId());
    errors.put("caseName", dbCase.getName());
    errors.put("caseVersion", dbCase.getVersion());
    errors.put("caseReqs", lists);
    return errors;
  }

  public List<CaseRequestLog> findHistory(Integer userId, Integer pageNo) {
    if (pageNo == null || pageNo == 0) {
      pageNo = 1;
    }
    return requestServiceFacade.getCaseRequestLog(userId, pageNo);
  }

  public CaseRequestLog findCaseRequestLogById(String id) {
    return requestServiceFacade.getCaseRequestLogById(id);
  }



  public Map copyCase(Integer userId, Case caseArgs) {
    HashMap<String, Object> errors = Maps.newHashMap();
    caseArgs.setUserId(userId + "");

    Case dbCase = insertCase(caseArgs);
    if (dbCase == null) {
      errors.put("retCode", "-1");
      errors.put("msg", "插入Case有误");
      return errors;
    }

    ArrayList<CaseRequest> lists = Lists.newArrayList();
    List<CaseRequest> crLists = caseArgs.getCaseRequests();
    if (crLists != null && crLists.size() > 0) {

      for (CaseRequest caseRequest : crLists) {
        System.out.println(caseRequest.getId());
        caseRequest.setId(UUID.randomUUID().toString().substring(0, 20));
        caseRequest.setUserId(userId + "");
        caseRequest.setCollectionId(dbCase.getId());
        caseRequest.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
          caseRequest.setParams(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getParams(), "utf8").getBytes()));
          caseRequest.setPreConds(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getPreConds(), "utf8").getBytes()));
          caseRequest.setPreSets(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getPreSets(), "utf8").getBytes()));
          caseRequest.setTestCases(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getTestCases(), "utf8").getBytes()));
          caseRequest.setAfterSets(
              Base64Utils.encode(URLEncoder.encode(caseRequest.getAfterSets(), "utf8").getBytes()));

        } catch (UnsupportedEncodingException e) {
          LoggerUtil.error("导入失败", e);
          continue;
        }
        insertCaseRequest(caseRequest);
        lists.add(caseRequest);
      }
    }
    errors.put("retCode", "0");
    errors.put("msg", "导入成功");
    errors.put("caseId", dbCase.getId());
    errors.put("caseName", dbCase.getName());
    errors.put("caseVersion", dbCase.getVersion());
    errors.put("caseReqs", lists);
    return errors;
  }


  /**
   * 首页用例统计通过率
   * 
   * @param c
   * @return updateTime: 2018年2月5日 下午2:18:00
   */
  public Case getStatisticsRate(Case c) {
    Integer failCount = c.getCaseFailCount();
    Integer successCount = c.getCaseSuccessCount();
    Double fc = 0D, sc = 0D;
    try {
      if (failCount != null) {
        fc = Double.valueOf(failCount);
      }
    } catch (Exception e) {}
    try {
      if (successCount != null) {
        sc = Double.valueOf(successCount);
      }
    } catch (Exception e) {}

    c.setSuccessRate((fc + sc == 0D) ? 0 : (sc / (fc + sc)) * 100);
    if (StringUtils.isNotBlank(c.getRecord())) {
      List<RecordInfoVo> recordList =
          FastJsonUtil.fromJsons(c.getRecord(), new TypeReference<List<RecordInfoVo>>() {});
      c.setLastTime(recordList.get(0).getRecord());
      ArrayList<String> unpassSize = Lists.newArrayList();
      ArrayList<String> passSize = Lists.newArrayList();
      for (RecordInfoVo s : recordList) {
        if (s.getRecord().equals("1")) {
          passSize.add(s.getRecord());
        } else {
          unpassSize.add(s.getRecord());
        }
      }
      c.setNearlyFiveSuccessRate((passSize.size() + unpassSize.size() == 0)
          ? 0D
          : (passSize.size() * 1.0 / (passSize.size() + unpassSize.size())) * 100);
    }
    return c;
  }

}
