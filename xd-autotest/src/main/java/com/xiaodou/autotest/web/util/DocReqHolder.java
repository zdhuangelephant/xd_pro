package com.xiaodou.autotest.web.util;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.web.util.DocHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月5日
 * @description DocReq容器
 * @version 1.0
 */
public class DocReqHolder {

  private DocReqHolder() {}

  private static final DocReqHolder INSTANCE = new DocReqHolder();

  public static DocReqHolder getInstance() {
    return INSTANCE;
  }

  private Map<String, DocRequest> docReqMapper = Maps.newHashMap();

  public void init(RequestServiceFacade requestServiceFacade) {
    List<Doc> docList = requestServiceFacade.getDocByCond(null, null);
    if (null == docList || docList.size() == 0) {
      return;
    }
    List<DocRequest> docReqList = requestServiceFacade.getDocRequestModelByCond(null);
    if (null == docReqList || docReqList.size() == 0) {
      return;
    }
    Map<String, String> innerDocMapper = Maps.newHashMap();
    Map<String, DocRequest> innerDocReqMapper = Maps.newHashMap();
    for (Doc doc : docList) {
      if (null == doc || StringUtils.isBlank(doc.getVersion())) {
        continue;
      }
      innerDocMapper.put(doc.getId(), doc.getVersion());
    }
    for (DocRequest docReq : docReqList) {
      if (null == docReq || StringUtils.isBlank(docReq.getUniqueId())) {
        continue;
      }
      if (StringUtils.isNotBlank(docReq.getDocId())) {
        docReq.setVersion(innerDocMapper.get(docReq.getDocId()));
      }
      if (innerDocReqMapper.containsKey(docReq.getUniqueId())) {
        innerDocReqMapper.put(docReq.getUniqueId(),
            registReq0(innerDocReqMapper.get(docReq.getUniqueId()), docReq));
      } else {
        innerDocReqMapper.put(docReq.getUniqueId(), docReq);
      }
    }
    docReqMapper = innerDocReqMapper;
  }

  public void registReq(DocRequest docReq) {
    if (null == docReq || StringUtils.isBlank(docReq.getUniqueId())) {
      return;
    }
    if (docReqMapper.containsKey(docReq.getUniqueId())) {
      docReqMapper.put(docReq.getUniqueId(),
          registReq0(docReqMapper.get(docReq.getUniqueId()), docReq));
    } else {
      docReqMapper.put(docReq.getUniqueId(), docReq);
    }
  }

  private DocRequest registReq0(DocRequest previous, DocRequest current) {
    if (null == previous) {
      return current;
    }
    int result = current.compareTo(previous);
    if (result == 0) {
      String errMessage =
          ErrorMessage.getErrMessage("error.doc.loaddoc.doc.same", current.getName());
      LoggerUtil.error(errMessage, new RuntimeException(errMessage));
      return current;
    } else if (result > 0) {
      current.setPrevious(previous);
      return current;
    } else {
      previous.setPrevious(registReq0(previous.getPrevious(), current));
      return previous;
    }
  }

  public DocRequest getReq(String uniqueId, String version) {
    if (StringUtils.isBlank(uniqueId)) {
      return null;
    }
    DocRequest targetDocReq = docReqMapper.get(uniqueId);
    if (null != targetDocReq) {
      return targetDocReq.getSuitable(version);
    }
    return null;
  }

  public List<DocRequest> listReq(String docId, String version) {
    if (null == docReqMapper || docReqMapper.size() == 0) {
      return null;
    }
    List<DocRequest> reqList = Lists.newArrayList();
    for (DocRequest req : docReqMapper.values()) {
      if (null == req || StringUtils.isBlank(req.getDocId()) || !docId.equals(req.getDocId())) {
        continue;
      }
      DocRequest suitableReq = req.getSuitable(version);
      if (null == suitableReq) {
        continue;
      }
      reqList.add(suitableReq);
    }
    return reqList;
  }

}
