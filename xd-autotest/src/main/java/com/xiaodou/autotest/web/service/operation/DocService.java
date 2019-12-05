package com.xiaodou.autotest.web.service.operation;

import java.net.URLDecoder;
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
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
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

@Service("docService")
public class DocService {
  @Resource
  RequestServiceFacade requestServiceFacade;

  public Doc findCollectionById(String id) {
    return requestServiceFacade.getDocById(id);
  }

  public Boolean updateCollection(Doc collection) {
    collection.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return requestServiceFacade.updateDocFace(collection);
  }

  public Boolean removeDoc(String id) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("docId", id);
    List<DocRequest> results = requestServiceFacade.getDocRequestModelByCond(input);
    if (results != null && results.size() > 0) {
      for (DocRequest docRequest : results) {
        requestServiceFacade.deleteDocRequest(docRequest.getId());
      }
    }
    return requestServiceFacade.deleteDoc(id);
  }

  public DocRequest insertDocRequest(DocRequest docRequest) {
    docRequest.setId(System.currentTimeMillis() + "");
    if (StringUtils.isBlank(docRequest.getUniqueId())) {
      docRequest.setUniqueId(RandomUtil.randomString(20));
    }
    docRequest.setCreateTime(new Timestamp(System.currentTimeMillis()));
    docRequest.setUpdateTime(docRequest.getCreateTime());
    DocRequest req = requestServiceFacade.saveDocRequest(docRequest);
    return req;
  }

  public DocRequest findDocRequestById(String id) {
    return requestServiceFacade.getDocRequestById(id);
  }

  public Boolean updateRequest(DocRequest request) {
    request.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return requestServiceFacade.updateDocRequestFace(request);
  }

  public Boolean removeDocRequest(String id) {
    return requestServiceFacade.deleteDocRequest(id);
  }

  public List<Doc> findDocument() {
    return requestServiceFacade.getDocByCond(null, null);
  }

  public List<Doc> findDocument(Map<String, Object> input, Map<String, Object> sorts) {

    return requestServiceFacade.getDocByCond(input, sorts);
  }

  public Doc insertDoc(Doc doc) {
    doc.setId(System.currentTimeMillis() + "");
    // doc.setCount(0L);
    doc.setCreateTime(new Timestamp(System.currentTimeMillis()));
    doc.setUpdateTime(doc.getCreateTime());
    if (StringUtils.isBlank(doc.getVersion())) {
      doc.setVersion("0.0.0");
    }
    if (StringUtils.isBlank(doc.getBusinessLine())) {
      doc.setBusinessLine("default-b001");
    }
    if (StringUtils.isBlank(doc.getUniqueId())) {
      doc.setUniqueId(RandomUtil.randomString(20));
    }
    Doc wrapDoc = requestServiceFacade.saveDoc(doc);
    return wrapDoc;
  }

  public List<DocRequest> findDocRequests(Map<String, Object> input) {
    return requestServiceFacade.getDocRequestModelByCond(input);
  }

  public Map<String, Object> generVersion(String docId, String newVersion, String newVersionCode) {
    HashMap<String, Object> errors = Maps.newHashMap();
    Doc doc = findCollectionById(docId);
    if (doc == null) {
      errors.put("status", "false");
      errors.put("msg", "目标Doc不存在");
      return errors;
    }

    doc.setVersion(newVersionCode);
    doc.setName(newVersion);

    HashMap<String, Object> input = Maps.newHashMap();
    input.put("docId", docId);
    List<DocRequest> docReqList = findDocRequests(input);
    if (docReqList == null || docReqList.size() == 0) {
      errors.put("status", "false");
      errors.put("msg", "该Doc下面没有挂载DocRequest，操作失败");
      return errors;
    }

    Doc nDoc = new Doc();
    nDoc.setBusinessLine(doc.getBusinessLine());
    nDoc.setCount(doc.getCount());
    nDoc.setCreateTime(new Timestamp(System.currentTimeMillis()));
    nDoc.setDesc(doc.getDesc());
    nDoc.setId(UUID.randomUUID().toString().substring(0, 16));
    nDoc.setName(doc.getName());
    nDoc.setPrevious(doc.getPrevious());
    nDoc.setResults(doc.getResults());
    nDoc.setUniqueId(doc.getUniqueId());
    nDoc.setUserId(doc.getUserId());
    nDoc.setVersion(doc.getVersion());

    Doc wrapDoc = insertDoc(nDoc);
    if (wrapDoc == null) {
      errors.put("status", "false");
      errors.put("msg", "生成新版本Doc失败");
      return errors;
    }


    ArrayList<Map<String, Object>> caseLists = Lists.newArrayList();
    try {
      for (DocRequest dRequest : docReqList) {
        DocRequest dr = new DocRequest();
        dr.setAfterSets(dRequest.getAfterSets());
        dr.setCreateTime(new Timestamp(System.currentTimeMillis()));
        dr.setDesc(dRequest.getDesc());
        dr.setDocId(wrapDoc.getId());
        dr.setFormat(dRequest.getFormat());
        dr.setId(UUID.randomUUID().toString().substring(0, 16));
        dr.setMethod(dRequest.getMethod().toLowerCase());
        dr.setName(dRequest.getName());
        dr.setOutParams(dRequest.getOutParams());
        dr.setParamList(dRequest.getParamList());

        String tmp =
            URLDecoder.decode(new String(Base64Utils.decode(dRequest.getParams())), "utf8");

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
          dr.setParams(Base64Utils.encode(FastJsonUtil.toJson(psLists).getBytes()));
        } else {
          dr.setParams(tmp);
        }

        // dr.setParams(dRequest.getParams());
        dr.setPreConds(dRequest.getPreConds());
        dr.setPreSets(dRequest.getPreSets());
        dr.setPrevious(dRequest.getPrevious());
        dr.setProtocol(dRequest.getProtocol());
        dr.setSort(dRequest.getSort());
        dr.setTestCases(dRequest.getTestCases());
        dr.setUniqueId(dRequest.getUniqueId());
        dr.setUrl(dRequest.getUrl());
        dr.setUserId(dRequest.getUserId());
        dr.setVersion(dRequest.getVersion());

        insertDocRequest(dr);

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("drId", dr.getId());
        map.put("drDocId", dr.getDocId());
        map.put("drName", dr.getName());
        caseLists.add(map);
      }
    } catch (Exception e) {
      e.printStackTrace();
      errors.put("status", "false");
      errors.put("msg", "生成新版本中的DocRequest失败");
      return errors;
    }
    errors.put("status", "true");
    errors.put("msg", "操作成功");
    errors.put("docData", wrapDoc);
    errors.put("docRequests", caseLists);
    return errors;
  }

}
