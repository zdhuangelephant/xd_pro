package com.xiaodou.autotest.web.util;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.autotest.web.util.DocHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年9月5日
 * @description Doc容器
 * @version 1.0
 */
public class DocHolder {

  private DocHolder() {}

  private static final DocHolder INSTANCE = new DocHolder();

  public static DocHolder getInstance() {
    return INSTANCE;
  }

  private Map<String, Doc> docMapper = Maps.newHashMap();

  public void init(RequestServiceFacade requestServiceFacade) {
    List<Doc> docList = requestServiceFacade.getDocByCond(null, null);
    if (null == docList || docList.size() == 0) {
      return;
    }
    Map<String, Doc> innerDocMapper = Maps.newHashMap();
    for (Doc doc : docList) {
      if (null == doc || StringUtils.isBlank(doc.getUniqueId())) {
        continue;
      }
      if (innerDocMapper.containsKey(doc.getUniqueId())) {
        innerDocMapper.put(doc.getUniqueId(), registDoc0(innerDocMapper.get(doc.getUniqueId()), doc));
      } else {
        innerDocMapper.put(doc.getUniqueId(), doc);
      }
    }
    docMapper = innerDocMapper;
  }

  public void registDoc(Doc doc) {
    if (null == doc || StringUtils.isBlank(doc.getUniqueId())) {
      return;
    }
    if (docMapper.containsKey(doc.getUniqueId())) {
      docMapper.put(doc.getUniqueId(), registDoc0(docMapper.get(doc.getUniqueId()), doc));
    } else {
      docMapper.put(doc.getUniqueId(), doc);
    }
  }

  private Doc registDoc0(Doc previous, Doc current) {
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
      previous.setPrevious(registDoc0(previous.getPrevious(), current));
      return previous;
    }
  }

  public Doc getDoc(String uniqueId, String version) {
    if (StringUtils.isBlank(uniqueId)) {
      return null;
    }
    Doc targetDoc = docMapper.get(uniqueId);
    if (null != targetDoc) {
      return targetDoc.getSuitable(version);
    }
    return null;
  }

  public List<Doc> listDoc(String version) {
    if (null == docMapper || docMapper.size() == 0) {
      return null;
    }
    List<Doc> docList = Lists.newArrayList();
    for (Doc doc : docMapper.values()) {
      if (null == doc) {
        continue;
      }
      Doc suitableDoc = doc.getSuitable(version);
      if (null == suitableDoc) {
        continue;
      }
      docList.add(suitableDoc);
    }
    return docList;
  }

}
