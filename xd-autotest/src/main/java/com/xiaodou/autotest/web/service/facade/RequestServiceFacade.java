package com.xiaodou.autotest.web.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;

/**
 * @name @see com.xiaodou.autotest.web.service.facade.RequestServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月16日
 * @description 数据层访问门面接口
 * @version 1.0
 */
public interface RequestServiceFacade {

  DocRequest getDocRequestById(String id);


  List<DocRequest> getDocRequestModelByCond(Map<String, Object> input);

  Boolean updateDocRequestFace(DocRequest request);

  DocRequest saveDocRequest(DocRequest request);

  Boolean deleteDocRequest(String id);


  Doc getDocById(String id);

  List<Doc> getDocByCond(Map<String, Object> input,Map<String,Object> sorts);

  Boolean updateDocFace(Doc collection);

  Doc saveDoc(Doc collection);

  Boolean deleteDoc(String id);


  CaseRequestLog saveRequestLog(CaseRequestLog requestLog);


  Case getCaseById(String id);

  Boolean updateCase(Case caseD);

  List<Case> getCaseByCond(Map<String, Object> input);

  List<CaseRequest> getCaseRequestModelByCond(Map<String, Object> input);


  // dataSrouceData

  List<DataSourceData> getDataSourceDataByCond(Map<String, Object> input);

  Boolean updateDataSourceData(DataSourceData dataSourceData);

  DataSourceData saveDataSourceData(DataSourceData dataSourceData);

  Boolean deleteDataSourceData(String alias);

  Case saveCase(Case usercase);

  Boolean deleteCase(String caseId);

  Boolean deleteCaseRequest(String id);

  Boolean updateCaseRequest(CaseRequest docRequest);

  CaseRequest saveCaseRequest(CaseRequest docRequest);

  CaseRequest getCaseRequestById(String id);

  Boolean deleteCaseReq(String id);

  List<CaseRequestLog> getCaseRequestLog(Integer userId, Integer pageNo);

  CaseRequestLog getCaseRequestLogById(String id);


}
