package com.xiaodou.autotest.web.controller.operation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionEnum.Protocol;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.operation.DocService;
import com.xiaodou.autotest.web.util.DocHolder;
import com.xiaodou.autotest.web.util.DocReqHolder;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.web.controller.operation.DocController.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2017年09月06日
 * @description 文档Controller
 * @version 1.0
 */
@Controller("docController")
@RequestMapping("/doc")
public class DocController extends BaseController {


  @RequestMapping("/postman")
  public ModelAndView toDoc() {
    ModelAndView mv = new ModelAndView("overview/postman");
    return mv;
  }

  @RequestMapping("/addtab")
  public ModelAndView toAddtab() {
    ModelAndView mv = new ModelAndView("overview/addtab");
    return mv;
  }

  @Resource
  DocService docService;

  @RequestMapping("addDoc")
  @ResponseBody
  public String add(String docName, String desc, String docVersion, String docBusiness) {
    if (StringUtils.isNotBlank(docName)) {
      try {
        docName = URLDecoder.decode(new String(Base64Utils.decode(docName)), "utf8");
      } catch (UnsupportedEncodingException e) {
        docName = StringUtils.EMPTY;
      }
    }
    if (StringUtils.isNotBlank(desc)) {
      try {
        desc = URLDecoder.decode(new String(Base64Utils.decode(desc)), "utf8");
      } catch (UnsupportedEncodingException e) {
        desc = StringUtils.EMPTY;
      }
    }
    Doc doc = new Doc();
    doc.setUserId(getUser().getUserId() + "");
    doc.setName(docName);
    doc.setDesc(desc);
    doc.setCount(0L);
    if (StringUtils.isNotBlank(docBusiness)) {
      doc.setBusinessLine(docBusiness);
    }
    if (StringUtils.isNotBlank(docVersion)) {
      doc.setVersion(docVersion);
    }
    Doc wrapDoc = docService.insertDoc(doc);
    DocHolder.getInstance().registDoc(wrapDoc);
    if (wrapDoc != null) {
      return FastJsonUtil.toJson(wrapDoc);
    }

    return FastJsonUtil.toJson(StringUtils.EMPTY);
  }



  @RequestMapping("removeDoc")
  @ResponseBody
  public String remove(String id) {
    Boolean affectRow = docService.removeDoc(id);
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



  // 参数请求的封装
  @RequestMapping("requestAdd")
  @ResponseBody
  public String requestAdd(String format, String docReqId, String reqName, String method,
      String docId, String reqUrl, String desc, String params, String preConditions,
      String preSets, String testCases, String afterSets, String outParams) {
    DocRequest docRequest = new DocRequest();
    docRequest.setUserId(getUser().getUserId() + "");
    docRequest.setMethod(method.toLowerCase());
    docRequest.setDocId(docId);
    docRequest.setUrl(reqUrl);
    docRequest.setProtocol(Protocol.http + "");
    docRequest.setFormat(format);

    if (null != reqName && !reqName.equals("")) {
      docRequest.setName(reqName);
    }
    if (null != desc && !desc.equals("")) {
      docRequest.setDesc(desc);
    }
    docRequest.setParams(params);
    docRequest.setPreConds(preConditions);
    docRequest.setPreSets(preSets);
    docRequest.setTestCases(testCases);
    docRequest.setAfterSets(afterSets);
    docRequest.setOutParams(outParams);

    Map<String, Object> map = Maps.newConcurrentMap();
    try {
      if (StringUtils.isNotBlank(docReqId)) {

        DocRequest dbDocReq = docService.findDocRequestById(docReqId);
        if (!docId.trim().equals(dbDocReq.getDocId())) {
          // 对原先的减一.现在的加一
          Doc oldDoc = docService.findCollectionById(dbDocReq.getDocId());
          if (oldDoc.getCount() > 0) {
            oldDoc.setCount(oldDoc.getCount() - 1);
            docService.updateCollection(oldDoc);
          }


          Doc nowDoc = docService.findCollectionById(docId);
          nowDoc.setCount(nowDoc.getCount() + 1);
          docService.updateCollection(nowDoc);
        }

        docRequest.setId(docReqId);
        Boolean affectRow = docService.updateRequest(docRequest);
        if (affectRow) {
          map.put(Cons.STATUS, affectRow);
          map.put(Cons.MSG, Cons.SUCCESS);
        } else {
          map.put(Cons.STATUS, affectRow);
          map.put(Cons.MSG, Cons.FAILURE);
        }
      } else {

        //
        Doc dbDoc = docService.findCollectionById(docId);
        dbDoc.setCount(dbDoc.getCount() + 1);
        docService.updateCollection(dbDoc);


        DocRequest wrapDocReq = docService.insertDocRequest(docRequest);
        DocReqHolder.getInstance().registReq(wrapDocReq);
        if (wrapDocReq != null && wrapDocReq.getId() != null) {
          map.put(Cons.STATUS, true);
          map.put(Cons.MSG, Cons.SUCCESS);
          map.put("docReqId", wrapDocReq.getId());
          map.put("docReqName", wrapDocReq.getName());
          map.put("docId", wrapDocReq.getDocId());
        } else {
          map.put(Cons.STATUS, false);
          map.put(Cons.MSG, Cons.FAILURE);
        }
      }
    } catch (Exception e) {
      map.put(Cons.STATUS, false);
      map.put(Cons.MSG, e.getMessage());
    }

    return FastJsonUtil.toJson(map);
  }



  /**
   * 获取某个Doc的信息、或者获取所有的Doc信息
   * 
   * @param id
   * @return updateTime: 2018年1月30日 上午10:54:07
   */
  @RequestMapping("getDocuments")
  @ResponseBody
  public String getDocuments(String id) {
    if (StringUtils.isNotBlank(id)) {
      // 根据主键查找Doc即Collection
      Doc coll = docService.findCollectionById(id);
      if (coll != null) {
        return FastJsonUtil.toJson(coll);
      }
    } else {
      List<Doc> lists = docService.findDocument();
      if (lists != null) {
        for (Doc doc : lists) {
          doc.setHistoryTime(DateUtil.relativeDateFormat(doc.getUpdateTime()));
        }
        return FastJsonUtil.toJson(lists);
      }
    }
    return StringUtils.EMPTY;
  }



  @RequestMapping("generNewVersion")
  @ResponseBody
  public String generNewVersion(String docId, String newVersion, String newVersionCode) {
    if (StringUtils.isNotBlank(docId)) {
      Map<String,Object> msg = docService.generVersion(docId, newVersion, newVersionCode);
      if (msg != null) {

        return FastJsonUtil.toJson(msg);
      }

    }
    return FastJsonUtil.toJson(StringUtils.EMPTY);
  }

  @RequestMapping("updateDocReq")
  @ResponseBody
  public String updateDocReq(String docReqId, String docId, String name, String desc, String sort) {

    DocRequest dbDocReq = docService.findDocRequestById(docReqId);
    if (!docId.trim().equals(dbDocReq.getDocId())) {
      // 对原先的减一.现在的加一
      Doc oldDoc = docService.findCollectionById(dbDocReq.getDocId());
      if (oldDoc.getCount() > 0) {
        oldDoc.setCount(oldDoc.getCount() - 1);
        docService.updateCollection(oldDoc);
      }

      Doc nowDoc = docService.findCollectionById(docId);
      nowDoc.setCount(nowDoc.getCount() + 1);
      docService.updateCollection(nowDoc);
    }


    DocRequest doc = new DocRequest();
    if (StringUtils.isNotBlank(docId)) {
      doc.setDocId(docId);
    }
    if (StringUtils.isNotBlank(name)) {
      doc.setName(name);
    }
    if (StringUtils.isNotBlank(desc)) {
      doc.setDesc(desc);
    }
    if (StringUtils.isNotBlank(sort)) {
      doc.setSort(Integer.parseInt(sort));
    }
    HashMap<String, Object> errors = Maps.newHashMap();

    if (StringUtils.isNotBlank(docReqId)) {
      doc.setId(docReqId);
      Boolean res = docService.updateRequest(doc);
      if (res) {
        errors.put(Cons.STATUS, Cons.STATUS_SUCCESS);
        errors.put(Cons.MSG, Cons.SUCCESS);
      } else {
        errors.put(Cons.STATUS, Cons.STATUS_FAILURE);
        errors.put(Cons.MSG, Cons.FAILURE);
      }
    }
    return FastJsonUtil.toJson(errors);
  }

  @RequestMapping("getDocReqwithjson")
  @ResponseBody
  public String getDocReqwithjson(String id) {
    DocRequest docReq = docService.findDocRequestById(id);
    if (docReq == null) {
      return "";
    }
    docReq.setMethod(docReq.getMethod().toLowerCase());

    String params = "";
    String preConditions = "";
    String preSets = "";
    String testCases = "";
    String afterSets = "";
    String outParams = "";
    try {
      if (docReq != null && StringUtils.isNotBlank(docReq.getParams())) {
        params = URLDecoder.decode(new String(Base64Utils.decode(docReq.getParams())), "utf8");
      }
      if (docReq != null && StringUtils.isNotBlank(docReq.getPreConds())) {
        preConditions =
            URLDecoder.decode(new String(Base64Utils.decode(docReq.getPreConds())), "utf-8");
      }
      if (docReq != null && StringUtils.isNotBlank(docReq.getPreSets())) {
        preSets = URLDecoder.decode(new String(Base64Utils.decode(docReq.getPreSets())), "utf-8");
      }
      if (docReq != null && StringUtils.isNotBlank(docReq.getTestCases())) {
        testCases =
            URLDecoder.decode(new String(Base64Utils.decode(docReq.getTestCases())), "utf-8");
      }
      if (docReq != null && StringUtils.isNotBlank(docReq.getAfterSets())) {
        afterSets =
            URLDecoder.decode(new String(Base64Utils.decode(docReq.getAfterSets())), "utf-8");
      }
      if (docReq != null && StringUtils.isNotBlank(docReq.getOutParams())) {
        outParams =
            URLDecoder.decode(new String(Base64Utils.decode(docReq.getOutParams())), "utf-8");
      }

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    docReq.setParams(params);
    docReq.setPreConds(preConditions);
    docReq.setPreSets(preSets);
    docReq.setTestCases(testCases);
    docReq.setAfterSets(afterSets);
    docReq.setOutParams(outParams);

    return FastJsonUtil.toJson(docReq);
  }


  @RequestMapping("removeDocReq")
  @ResponseBody
  public String removeDocRequest(String id) {
    DocRequest docReq = docService.findDocRequestById(id);
    Boolean affectRow = docService.removeDocRequest(id);
    HashMap<String, Object> msg = Maps.newHashMap();
    if (affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      msg.put(Cons.MSG, Cons.SUCCESS);
    }
    if (!affectRow) {
      msg.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msg.put(Cons.MSG, Cons.FAILURE);
    }


    Doc doc = docService.findCollectionById(docReq.getDocId());
    if (doc.getCount() > 0) {
      doc.setCount(doc.getCount() - 1);
      docService.updateCollection(doc);
    }

    return FastJsonUtil.toJson(msg);
  }

  @RequestMapping("updateDoc")
  @ResponseBody
  public String updateDoc(String docName, String docId, String version, String desc,
      String businessLine) {
    Doc doc = new Doc();
    if (StringUtils.isNotBlank(docId)) {
      doc.setId(docId);
    }
    if (StringUtils.isNotBlank(docName)) {
      doc.setName(docName);
    }
    if (StringUtils.isNotBlank(version)) {
      doc.setVersion(version);
    }
    if (StringUtils.isNotBlank(desc)) {
      doc.setDesc(desc);
    }
    if (StringUtils.isNotBlank(businessLine)) {
      doc.setBusinessLine(businessLine);
    }
    HashMap<String, Object> errors = Maps.newHashMap();

    if (StringUtils.isNotBlank(docId)) {
      Boolean res = docService.updateCollection(doc);
      if (res) {
        errors.put(Cons.STATUS, Cons.STATUS_SUCCESS);
        errors.put(Cons.MSG, Cons.SUCCESS);
      } else {
        errors.put(Cons.STATUS, Cons.STATUS_FAILURE);
        errors.put(Cons.MSG, Cons.FAILURE);
      }
    }
    return FastJsonUtil.toJson(errors);
  }


  @RequestMapping("getDocAndReq")
  @ResponseBody
  public String getDocAndReq() {
    List<Doc> docLists = docService.findDocument();
    ArrayList<Map<String, Object>> total = Lists.newArrayList();
    if (docLists != null && docLists.size() > 0) {
      for (Doc doc : docLists) {
        HashMap<String, Object> targetDoc = Maps.newHashMap();
        HashMap<String, Object> inputs = Maps.newHashMap();
        inputs.put("docId", doc.getId());
        List<DocRequest> subDocReqList = docService.findDocRequests(inputs);

        targetDoc.put("reqList", subDocReqList);
        targetDoc.put("id", doc.getId());
        targetDoc.put("name", doc.getName());
        total.add(targetDoc);

      }
    }
    return FastJsonUtil.toJson(total);
  }


  /**
   * 获取某个Doc下的所有DocRequest;
   * 
   * @param docId
   * @return updateTime: 2018年1月30日 上午10:52:54
   */
  @RequestMapping("getDocReqList")
  @ResponseBody
  public String getDocRequestList(String docId) {
    if (StringUtils.isNotBlank(docId)) {
      HashMap<String, Object> inputs = Maps.newHashMap();
      inputs.put("docId", docId);
      List<DocRequest> lists = docService.findDocRequests(inputs);
      if (lists != null) {
        return FastJsonUtil.toJson(lists);
      }
    }
    return StringUtils.EMPTY;
  }


  @RequestMapping("getDocReqListByDocwithjson")
  @ResponseBody
  public String getDocReqListByDocwithjson(String docId, String version) {
    List<DocRequest> listReq = DocReqHolder.getInstance().listReq(docId, version);
    return FastJsonUtil.toJson(listReq);
  }

  @RequestMapping("getDocListwithjson")
  @ResponseBody
  public String getDocListwithjson(String version) {
    List<Doc> listDoc = DocHolder.getInstance().listDoc(version);
    return FastJsonUtil.toJson(listDoc);
  }


  @RequestMapping("getDoc")
  @ResponseBody
  public String getDoc(String id) {
    if (StringUtils.isNotBlank(id)) {
      // 根据主键查找Doc即Collection
      Doc coll = docService.findCollectionById(id);
      if (coll != null) {
        return FastJsonUtil.toJson(coll);
      }
    } else {
      ArrayList<Doc> results = Lists.newArrayList();
      List<Doc> lists = docService.findDocument();

      if (lists != null) {
        for (Doc doc : lists) {
          doc.setHistoryTime(DateUtil.relativeDateFormat(doc.getUpdateTime()));
          results.add(doc);
          if (results.size() > 5) {
            break;
          }
        }
        return FastJsonUtil.toJson(results);
      }
    }
    return StringUtils.EMPTY;
  }

}
