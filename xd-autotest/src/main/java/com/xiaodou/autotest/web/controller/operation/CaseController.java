
package com.xiaodou.autotest.web.controller.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.operation.CaseService;
import com.xiaodou.autotest.web.service.operation.DocService;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.login.model.Admin;
import com.xiaodou.login.service.AdminService;

/**
 * @name @see com.xiaodou.autotest.web.controller.operation.CaseController.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2017年09月06日
 * @description 用例Controller
 * @version 1.0
 */
@Controller("caseController")
@RequestMapping("/case")
public class CaseController extends BaseController {

  @Resource
  CaseService caseService;

  @Resource
  DocService docService;
  @Resource
  AdminService adminService;

  @RequestMapping("/testcase")
  public ModelAndView toTestCase() {
    ModelAndView mv = new ModelAndView("overview/testcase");
    return mv;
  }

  @RequestMapping("/getCaseAndReq")
  @ResponseBody
  public String getCaseAndReq() {
    List<Case> caseLists = caseService.findAllCases(getUserId(), null);
    ArrayList<Map<String, Object>> total = Lists.newArrayList();
    if (caseLists != null && caseLists.size() > 0) {
      for (Case c : caseLists) {
        HashMap<String, Object> caseArgs = Maps.newHashMap();
        HashMap<String, Object> cond = Maps.newHashMap();
        cond.put("collectionId", c.getId());
        cond.put("userId", getUserId());
        List<CaseRequest> subCaseReqList = caseService.findCaseRequests(cond);
        caseArgs.put("reqList", subCaseReqList);
        caseArgs.put("id", c.getId());
        caseArgs.put("name", c.getName());
        caseArgs.put("version", c.getVersion());
        caseArgs.put("share", c.getShare());
        total.add(caseArgs);
      }
    }
    return FastJsonUtil.toJson(total);
  }


  @RequestMapping("/getShareCaseAndReq")
  @ResponseBody
  public String getShareCaseAndReq() {
    List<Case> caseLists = caseService.findAllCases(null, Cons.SHARE);
    ArrayList<Map<String, Object>> total = Lists.newArrayList();
    if (caseLists != null && caseLists.size() > 0) {
      for (Case c : caseLists) {
        HashMap<String, Object> caseContainer = Maps.newHashMap();
        HashMap<String, Object> caseInputs = Maps.newHashMap();
        caseInputs.put("collectionId", c.getId());
        List<CaseRequest> subCaseReqList = caseService.findCaseRequests(caseInputs);
        caseContainer.put("reqList", subCaseReqList);
        caseContainer.put("id", c.getId());
        Admin admin = adminService.findAdminById(Integer.valueOf(c.getUserId()));
        caseContainer.put("name", c.getName() + "(" + admin.getRealName() + ")");
        caseContainer.put("version", c.getVersion());
        caseContainer.put("share", c.getShare());
        total.add(caseContainer);
      }
    }
    return FastJsonUtil.toJson(total);
  }

  @RequestMapping("addCase")
  @ResponseBody
  public String addCase(String caseName, String caseDesc, String caseVersion, String caseBusiness) {
    if (StringUtils.isNotBlank(caseName)) {
      try {
        caseName = URLDecoder.decode(new String(Base64Utils.decode(caseName)), "utf8");
      } catch (UnsupportedEncodingException e) {
        caseName = StringUtils.EMPTY;
      }
    }
    if (StringUtils.isNotBlank(caseDesc)) {
      try {
        caseDesc = URLDecoder.decode(new String(Base64Utils.decode(caseDesc)), "utf8");
      } catch (UnsupportedEncodingException e) {
        caseDesc = StringUtils.EMPTY;
      }
    }
    Case preCase = new Case();
    preCase.setUserId(getUserId() + "");
    preCase.setCaseDesc(caseDesc);

    preCase.setName(caseName);
    if (StringUtils.isNotBlank(caseBusiness)) {
      preCase.setBusinessLine(caseBusiness);
    } else {
      preCase.setBusinessLine(Cons.DEFAULT_BUSINESSLINE);
    }
    if (StringUtils.isNotBlank(caseVersion)) {
      preCase.setVersion(caseVersion);
    } else {
      preCase.setVersion(Cons.DEFAULT_VERSION);
    }

    Case wrapCase = caseService.insertCase(preCase);
    if (wrapCase != null) {
      return FastJsonUtil.toJson(wrapCase);
    }

    return FastJsonUtil.toJson(StringUtils.EMPTY);
  }

  @RequestMapping("removeCase")
  @ResponseBody
  public String removeCase(String caseId) {
    Boolean affectRow = caseService.removeCase(caseId);
    HashMap<String, Object> msg = Maps.newHashMap();
    if (affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      msg.put(Cons.MSG, Cons.SUCCESS);
    }
    if (!affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msg.put(Cons.MSG, Cons.FAILURE);
    }
    return FastJsonUtil.toJson(msg);
  }

  @RequestMapping("getCase")
  @ResponseBody
  public String getCase(String id) {
    if (StringUtils.isNotBlank(id)) {
      // 根据主键查找Doc即Collection
      Case coll = caseService.findCaseById(id);
      if (coll != null) {
        return FastJsonUtil.toJson(coll);
      }
    } else {
      List<Case> lists = caseService.findAllCases(getUserId(), null);
      if (lists != null) {
        return FastJsonUtil.toJson(lists);
      }
    }
    return StringUtils.EMPTY;
  }

  @RequestMapping("updateCase")
  @ResponseBody
  public String updateDoc(String docName, String docId, String version, String desc,
      String businessLine) {
    Case preCase = new Case();
    if (StringUtils.isNotBlank(docId)) {
      preCase.setId(docId);
    }
    if (StringUtils.isNotBlank(docName)) {
      preCase.setName(docName);
    }
    if (StringUtils.isNotBlank(version)) {
      preCase.setVersion(version);
    }
    if (StringUtils.isNotBlank(desc)) {
      preCase.setCaseDesc(desc);
    }
    if (StringUtils.isNotBlank(businessLine)) {
      preCase.setBusinessLine(businessLine);
    }
    HashMap<String, Object> msg = Maps.newHashMap();

    if (StringUtils.isNotBlank(docId)) {
      Boolean res = caseService.updateCollection(preCase);
      if (res) {
        msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
        msg.put(Cons.MSG, Cons.SUCCESS);
      } else {
        msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
        msg.put(Cons.MSG, Cons.FAILURE);
      }
    }
    return FastJsonUtil.toJson(msg);
  }

  /**
   * 导出
   */
  @RequestMapping("exportCase")
  @ResponseBody
  public String exportCase(String caseId) {
    Case wrapCase = caseService.findCaseById(caseId);
    if (wrapCase != null) {
      Map<String, Object> inputs = Maps.newHashMap();
      inputs.put("collectionId", wrapCase.getId());
      inputs.put("userId", getUserId());
      List<CaseRequest> crList = caseService.findCaseRequests(inputs);
      ArrayList<CaseRequest> normalCrList = Lists.newArrayList();
      if (crList != null && crList.size() > 0) {
        for (CaseRequest caseRequest : crList) {
          String params = StringUtils.EMPTY;
          String preConditions = StringUtils.EMPTY;
          String preSets = StringUtils.EMPTY;
          String testCases = StringUtils.EMPTY;
          String afterSets = StringUtils.EMPTY;
          try {
            params =
                URLDecoder.decode(new String(Base64Utils.decode(caseRequest.getParams())), "utf8");
            preConditions = URLDecoder
                .decode(new String(Base64Utils.decode(caseRequest.getPreConds())), "utf8");
            preSets =
                URLDecoder.decode(new String(Base64Utils.decode(caseRequest.getPreSets())), "utf8");
            testCases = URLDecoder
                .decode(new String(Base64Utils.decode(caseRequest.getTestCases())), "utf8");
            afterSets = URLDecoder
                .decode(new String(Base64Utils.decode(caseRequest.getAfterSets())), "utf8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
          caseRequest.setParams(params);
          caseRequest.setPreConds(preConditions);
          caseRequest.setPreSets(preSets);
          caseRequest.setTestCases(testCases);
          caseRequest.setAfterSets(afterSets);

          normalCrList.add(caseRequest);
        }
      }
      wrapCase.setCaseRequests(normalCrList);
      return FastJsonUtil.toJson(wrapCase);
    }
    return StringUtils.EMPTY;
  }

  @RequestMapping("generNewVersion")
  @ResponseBody
  public String generNewVersion(String docId, String newVersion, String newVersionCode) {

    if (StringUtils.isNotBlank(docId)) {
      Map<String,Object> msg = caseService.generVersion(docId, newVersion, newVersionCode);
      if (msg != null) {
        return FastJsonUtil.toJson(msg);
      }
    }
    return FastJsonUtil.toJson(StringUtils.EMPTY);
  }

  @RequestMapping("caseRequestAdd")
  @ResponseBody
  public String caseRequestAdd(String format, String docReqId, String reqName, String method,
      String docId, String reqUrl, String params, String preConditions, String preSets,
      String testCases, String afterSets) {
    CaseRequest docRequest = new CaseRequest();
    if (StringUtils.isNotBlank(format)) {
      docRequest.setFormat(format);
    }

    if (StringUtils.isNotBlank(reqName)) {
      docRequest.setName(reqName);
    }
    if (StringUtils.isNotBlank(method)) {
      docRequest.setMethod(method.toLowerCase());
    }
    if (StringUtils.isNotBlank(docId)) {
      docRequest.setCollectionId(docId);
    }
    if (StringUtils.isNotBlank(reqUrl)) {
      docRequest.setUrl(reqUrl);
    }
    docRequest.setUserId(getUser().getUserId() + "");
    docRequest.setProtocol(Protocol.http + "");

    docRequest.setParams(params);
    docRequest.setPreConds(preConditions);
    docRequest.setPreSets(preSets);
    docRequest.setTestCases(testCases);
    docRequest.setAfterSets(afterSets);

    // 更新该Case下的count数量


    Map<String, Object> msg = new HashMap<String, Object>();
    try {
      if (StringUtils.isNotBlank(docReqId)) {

        CaseRequest dbCaseReq = caseService.findCaseRequestById(docReqId);
        if (!docId.trim().equals(dbCaseReq.getCollectionId())) {
          // 对原先的减一.现在的加一
          Case oldCase = caseService.findCaseById(dbCaseReq.getCollectionId());
          if (oldCase.getCount() > 0) {
            oldCase.setCount(oldCase.getCount() - 1);
            caseService.updateCollection(oldCase);
          }
          Case nowCase = caseService.findCaseById(docId);
          nowCase.setCount(nowCase.getCount() + 1);
          caseService.updateCollection(nowCase);

        }

        docRequest.setId(docReqId);
        Boolean affectRow = caseService.updateCaseRequest(docRequest);
        if (affectRow) {
          msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
          msg.put(Cons.MSG, Cons.SUCCESS);
        } else {
          msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
          msg.put(Cons.MSG, Cons.FAILURE);
        }
      } else {
        //
        Case dbCase = caseService.findCaseById(docId);
        dbCase.setCount(dbCase.getCount() + 1);
        caseService.updateCollection(dbCase);

        CaseRequest caseReq = caseService.insertCaseRequest(docRequest);
        if (caseReq != null && caseReq.getId() != null) {
          msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
          msg.put(Cons.MSG, Cons.SUCCESS);
          msg.put("docReqId", caseReq.getId());
          msg.put("docReqName", caseReq.getName());
          msg.put("docId", caseReq.getCollectionId());
        } else {
          msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
          msg.put(Cons.MSG, Cons.FAILURE);
        }
      }
    } catch (Exception e) {
      msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msg.put(Cons.MSG, e.getMessage());
    }

    return FastJsonUtil.toJson(msg);
  }

  @RequestMapping("addCaseReq")
  @ResponseBody
  public String addCaseReq(String caseId, String docReqId, String caseName) {
    if (docReqId != null) {
      DocRequest docRequest = docService.findDocRequestById(docReqId);

      CaseRequest cRequest = new CaseRequest();
      cRequest.setAfterSets(docRequest.getAfterSets());
      cRequest.setCaseRequestDesc(docRequest.getDesc());
      cRequest.setCollectionId(caseId);
      cRequest.setCreateTime(new Timestamp(System.currentTimeMillis()));
      cRequest.setFormat(docRequest.getFormat());
      cRequest.setId(RandomUtil.randomString(10));
      cRequest.setMethod(docRequest.getMethod().toLowerCase());
      if (StringUtils.isBlank(caseName)) {
        cRequest.setName(docRequest.getName());
      } else {
        cRequest.setName(caseName);
      }

      cRequest.setParams(docRequest.getParams());
      cRequest.setPreConds(docRequest.getPreConds());
      cRequest.setPreSets(docRequest.getPreSets());
      cRequest.setProtocol(docRequest.getProtocol());
      cRequest.setSort(docRequest.getSort());
      cRequest.setTestCases(docRequest.getTestCases());
      cRequest.setUrl(docRequest.getUrl());
      cRequest.setUserId(getUserId() + "");
      CaseRequest wrapCase = caseService.insertCaseRequest(cRequest);

      Case dbCase = caseService.findCaseById(caseId);
      dbCase.setCount(dbCase.getCount() + 1);
      caseService.updateCollection(dbCase);

      return FastJsonUtil.toJson(wrapCase);
    }
    return StringUtils.EMPTY;

  }

  @RequestMapping("getCaseReqwithjson")
  @ResponseBody
  public String getDocReqwithjson(String id) {
    CaseRequest caseReq = caseService.findCaseRequestById(id);
    if (caseReq == null) {
      return StringUtils.EMPTY;
    }
    String params = StringUtils.EMPTY;
    String preConditions = StringUtils.EMPTY;
    String preSets = StringUtils.EMPTY;
    String testCases = StringUtils.EMPTY;
    String afterSets = StringUtils.EMPTY;

    try {
      params = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getParams())), "utf8");
      preConditions =
          URLDecoder.decode(new String(Base64Utils.decode(caseReq.getPreConds())), "utf8");
      preSets = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getPreSets())), "utf8");
      testCases = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getTestCases())), "utf8");
      afterSets = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getAfterSets())), "utf8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    caseReq.setParams(params);
    caseReq.setPreConds(preConditions);
    caseReq.setPreSets(preSets);
    caseReq.setTestCases(testCases);
    caseReq.setAfterSets(afterSets);

    return FastJsonUtil.toJson(caseReq);
  }


  @RequestMapping("getCaseReqLogwithjson")
  @ResponseBody
  public String getCaseReqLogwithjson(String id) {
    CaseRequestLog caseReq = caseService.findCaseRequestLogById(id);
    if (caseReq == null) {
      return StringUtils.EMPTY;
    }
    String params = StringUtils.EMPTY;
    String preConditions = StringUtils.EMPTY;
    String preSets = StringUtils.EMPTY;
    String testCases = StringUtils.EMPTY;
    String afterSets = StringUtils.EMPTY;
    try {
      params = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getParams())), "utf8");
      preConditions =
          URLDecoder.decode(new String(Base64Utils.decode(caseReq.getPreConds())), "utf8");
      preSets = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getPreSets())), "utf8");
      testCases = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getTestCases())), "utf8");
      afterSets = URLDecoder.decode(new String(Base64Utils.decode(caseReq.getAfterSets())), "utf8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    caseReq.setParams(params);
    caseReq.setPreConds(preConditions);
    caseReq.setPreSets(preSets);
    caseReq.setTestCases(testCases);
    caseReq.setAfterSets(afterSets);

    return FastJsonUtil.toJson(caseReq);
  }

  @RequestMapping("updateCaseReq")
  @ResponseBody
  public String updateCaseReq(String docReqId, String docId, String name, String caseRequestDesc,
      String sort) {

    CaseRequest dbCaseReq = caseService.findCaseRequestById(docReqId);
    if (docId != "" && !docId.trim().equals(dbCaseReq.getCollectionId())) {
      // 对原先的减一.现在的加一
      Case oldCase = caseService.findCaseById(dbCaseReq.getCollectionId());
      if (oldCase.getCount() > 0) {
        oldCase.setCount(oldCase.getCount() - 1);
        caseService.updateCollection(oldCase);
      }


      Case nowCase = caseService.findCaseById(docId);
      nowCase.setCount(nowCase.getCount() + 1);
      caseService.updateCollection(nowCase);

    }

    CaseRequest doc = new CaseRequest();
    if (StringUtils.isNotBlank(docId)) {
      doc.setCollectionId(docId);
    }
    if (StringUtils.isNotBlank(name)) {
      doc.setName(name);
    }
    if (StringUtils.isNotBlank(caseRequestDesc)) {
      doc.setCaseRequestDesc(caseRequestDesc);
    }
    if (StringUtils.isNotBlank(sort)) {
      doc.setSort(Integer.parseInt(sort));
    }
    HashMap<String, Object> msg = Maps.newHashMap();

    if (StringUtils.isNotBlank(docReqId)) {
      doc.setId(docReqId);
      Boolean res = caseService.updateCaseRequest(doc);
      if (res) {
        msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
        msg.put(Cons.MSG, Cons.SUCCESS);
      } else {
        msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
        msg.put(Cons.MSG, Cons.FAILURE);
      }
    }
    return FastJsonUtil.toJson(msg);
  }

  @RequestMapping("removeCaseReq")
  @ResponseBody
  public String removeDocRequest(String id) {
    CaseRequest dbCaseReq = caseService.findCaseRequestById(id);
    Boolean affectRow = caseService.removeCaseReq(id);
    HashMap<String, Object> msg = Maps.newHashMap();
    if (affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      msg.put(Cons.MSG, Cons.SUCCESS);
    }
    if (!affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msg.put(Cons.MSG, Cons.FAILURE);
    }

    //

    if (dbCaseReq != null) {
      Case oldCase = caseService.findCaseById(dbCaseReq.getCollectionId());
      if (oldCase.getCount() > 0) {
        oldCase.setCount(oldCase.getCount() - 1);
        caseService.updateCollection(oldCase);
      }
    }

    return FastJsonUtil.toJson(msg);
  }



  @RequestMapping("importCase")
  @ResponseBody
  public String springUpload(
      @RequestParam(value = "targetFile", required = true) MultipartFile file,
      HttpServletRequest request) throws IllegalStateException, IOException {
    String path = request.getSession().getServletContext().getRealPath("upload");
    if (!file.getOriginalFilename().endsWith(".json")) {
      return Cons.MSFINFO;
    }
    String fileName = RandomUtil.randomNumber(8) + "_" + file.getOriginalFilename();
    File targetFile = new File(path, fileName);
    if (!targetFile.exists()) {
      targetFile.mkdirs();
    }
    // 保存
    try {
      file.transferTo(targetFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
    BufferedReader streamReader = null;
    Map<String, Object> errors = null;
    try {
      streamReader =
          new BufferedReader(new InputStreamReader(new FileInputStream(targetFile), "UTF-8"));
      StringBuilder responseStrBuilder = new StringBuilder();

      String inputStr;
      while ((inputStr = streamReader.readLine()) != null) {
        responseStrBuilder.append(inputStr);
      } ;
      String line = responseStrBuilder.toString();


      errors = caseService.importCase(getUserId(), line);


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (streamReader != null) {
        streamReader.close();
      }
    }

    return FastJsonUtil.toJson(errors);
  }


  @RequestMapping("getShareCase")
  @ResponseBody
  public String getShareCase(String id) {
    if (StringUtils.isNotBlank(id)) {
      // 根据主键查找Doc即Collection
      Case coll = caseService.findCaseById(id);
      if (coll != null) {
        return FastJsonUtil.toJson(coll);
      }
    } else {
      List<Case> lists = caseService.findAllCases(getUserId(), Cons.SHARE);
      if (lists != null) {
        return FastJsonUtil.toJson(lists);
      }
    }
    return StringUtils.EMPTY;
  }


  @RequestMapping("acquireHistory")
  @ResponseBody
  public String acquireHistory(Integer pageNo) {
    List<CaseRequestLog> logs = caseService.findHistory(getUserId(), pageNo);
    return FastJsonUtil.toJson(logs);
  }



  @RequestMapping("shareCase")
  @ResponseBody
  public String shareCase(String caseId, String share) {
    Case wrapCase = caseService.findCaseById(caseId);
    if (share.equals(Cons.SHARE)) {
      share = Cons.NO_SHARE;
    } else {
      share = Cons.SHARE;
    }
    wrapCase.setShare(share);
    Boolean affectRow = caseService.updateCollection(wrapCase);
    HashMap<String, Object> msg = Maps.newHashMap();
    if (affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      msg.put(Cons.MSG, Cons.SUCCESS);
      msg.put("share", share);
    }
    if (!affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msg.put(Cons.MSG, Cons.FAILURE);
    }
    return FastJsonUtil.toJson(msg);
  }

  /**
   * 拉取
   */
  @RequestMapping("pullCase")
  @ResponseBody
  public String pullCase(String caseId) {
    Case wrapCase = caseService.findCaseById(caseId);
    Map<String, Object> map = new HashMap<String, Object>();
    Case newCase = new Case();
    List<CaseRequest> newCaseRequestList = Lists.newArrayList();
    try {
      if (wrapCase != null) {
        newCase = wrapCase;
        newCase.setId(UUID.randomUUID().toString().substring(0, 20));
        newCase.setUserId(this.getUserId().toString() + "");
        newCase.setShare(Cons.NO_SHARE);
        newCase.setCreateTime(new Timestamp(System.currentTimeMillis()));
        newCase = caseService.insertCase(newCase);
        Map<String, Object> inputs = Maps.newHashMap();
        inputs.put("collectionId", caseId);
        List<CaseRequest> caseRequestList = caseService.findCaseRequests(inputs);
        if (caseRequestList != null) {
          for (CaseRequest caseRequest : caseRequestList) {
            CaseRequest newCaseRequest = new CaseRequest();
            newCaseRequest = caseRequest;
            newCaseRequest.setId(UUID.randomUUID().toString().substring(0, 20));
            newCaseRequest.setUserId(this.getUserId().toString() + "");
            newCaseRequest.setCollectionId(newCase.getId());
            newCaseRequest.setCreateTime(new Timestamp(System.currentTimeMillis()));
            caseService.insertCaseRequest(newCaseRequest);
            newCaseRequestList.add(newCaseRequest);
          }
        }
      }
      map.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      map.put(Cons.MSG, Cons.SUCCESS);
      map.put("caseId", newCase.getId());
      map.put("caseName", newCase.getName());
      map.put("version", newCase.getVersion());
      map.put("caseRequestList", newCaseRequestList);
    } catch (Exception e) {
      map.put(Cons.STATUS, Cons.STATUS_FAILURE);
      map.put(Cons.MSG, Cons.FAILURE);
    }
    return FastJsonUtil.toJson(map);
  }
}
