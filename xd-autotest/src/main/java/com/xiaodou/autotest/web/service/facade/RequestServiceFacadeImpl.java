package com.xiaodou.autotest.web.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.dao.datasource.DataSourceDataDao;
import com.xiaodou.autotest.web.dao.operation.CaseDao;
import com.xiaodou.autotest.web.dao.operation.CaseRequestDao;
import com.xiaodou.autotest.web.dao.operation.CaseRequestLogDao;
import com.xiaodou.autotest.web.dao.operation.DocDao;
import com.xiaodou.autotest.web.dao.operation.DocRequestDao;
import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.CaseRequestLog;
import com.xiaodou.autotest.web.model.operation.Doc;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see
 *       com.xiaodou.autotest.web.service.facade.RequestServiceFacadeImpl.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月16日
 * @description 数据层访问门面实现
 * @version 1.0
 */
@Service("requestServiceFacade")
public class RequestServiceFacadeImpl implements RequestServiceFacade {
	@Resource
	DocDao docDao;
	@Resource
	CaseDao caseDao;
	@Resource
	DocRequestDao docRequestDao;
	@Resource
	CaseRequestDao caseRequestDao;
	@Resource
	CaseRequestLogDao caseRequestLogDao;
	@Resource
	DataSourceDataDao dataSourceDataDao;

	@Override
	public DocRequest getDocRequestById(String id) {
		DocRequest entity = new DocRequest();
		entity.setId(id);
		return docRequestDao.findEntityById(entity);
	}

	@Override
	public List<DocRequest> getDocRequestModelByCond(Map<String, Object> input) {
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		HashMap<String, QueryEnums.Sort> sorts = Maps.newHashMap();
		sorts.put("sort", Sort.ASC);
		param.addSorts(sorts);
		param.addOutputs(CommUtil.getGeneralField(DocRequest.class));
		Page<DocRequest> page = docRequestDao.findEntityListByCond(param, null);
		return page.getResult() != null ? page.getResult() : null;
	}

	@Override
	public Boolean updateDocRequestFace(DocRequest request) {
		return docRequestDao.updateEntityById(request);
	}

	@Override
	public DocRequest saveDocRequest(DocRequest request) {
		return docRequestDao.addEntity(request);
	}

	@Override
	public Boolean deleteDoc(String id) {
		Doc entity = new Doc();
		entity.setId(id);
		return docDao.deleteEntityById(entity);
	}

	@Override
	public Doc getDocById(String id) {
		// TODO Auto-generated method stub
		Doc entity = new Doc();
		entity.setId(id);
		return docDao.findEntityById(entity);
	}

	@Override
	public List<Doc> getDocByCond(Map<String, Object> input, Map<String,Object> sorts) {
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		param.addOutputs(CommUtil.getGeneralField(Doc.class));
//		param.addSort("updateTime", Sort.DESC);
		if(null != sorts && sorts.containsKey("version")) {
		  if("desc".equals(sorts.get("version"))) {
            param.addSort("version", Sort.DESC);
          }else {
            param.addSort("version", Sort.ASC);
          }
		}
		if(null != sorts && sorts.containsKey("updateTime")) {
		  if("desc".equals(sorts.get("updateTime"))) {
		    param.addSort("updateTime", Sort.DESC);
		  }else {
		    param.addSort("updateTime", Sort.ASC);
		  }
		}
		
		Page<Doc> page = docDao.findEntityListByCond(param, null);
		return page.getResult() != null ? page.getResult() : null;
	}
	
	

	@Override
	public Boolean updateDocFace(Doc entity) {
		return docDao.updateEntityById(entity);
	}

	@Override
	public Doc saveDoc(Doc collection) {
		return docDao.addEntity(collection);
	}

	@Override
	public Boolean deleteDocRequest(String id) {
		DocRequest entity = new DocRequest();
		entity.setId(id);
		return docRequestDao.deleteEntityById(entity);
	}

	@Override
	public CaseRequestLog saveRequestLog(CaseRequestLog entity) {
		return caseRequestLogDao.addEntity(entity);
	}

	@Override
	public Case getCaseById(String id) {
		Case entity = new Case();
		entity.setId(id);
		return caseDao.findEntityById(entity);
	}

	@Override
	public List<Case> getCaseByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		param.addOutputs(CommUtil.getGeneralField(Case.class));
		param.addSort("updateTime", Sort.DESC);
		Page<Case> page = caseDao.findEntityListByCond(param, null);
		return page.getResult() != null ? page.getResult() : null;
	}

	@Override
	public List<CaseRequest> getCaseRequestModelByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		param.addSort("sort", Sort.ASC);
		param.addOutputs(CommUtil.getGeneralField(CaseRequest.class));
		Page<CaseRequest> page = caseRequestDao.findEntityListByCond(param, null);
		return page.getResult() != null ? page.getResult() : null;
	}

	@Override
	public Boolean updateCase(Case caseD) {
		// TODO Auto-generated method stub
		Map<String, Object> cond = Maps.newConcurrentMap();
		cond.put("id", caseD.getId());
		return caseDao.updateEntityByCond(cond, caseD);
	}

	@Override
	public List<DataSourceData> getDataSourceDataByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		param.addOutputs(CommUtil.getGeneralField(DataSourceData.class));
		Page<DataSourceData> page = dataSourceDataDao.findEntityListByCond(param, null);
		return page.getResult() != null ? page.getResult() : null;
	}

	@Override
	public Boolean updateDataSourceData(DataSourceData dataSourceData) {
		return dataSourceDataDao.updateEntityById(dataSourceData);
	}

	@Override
	public DataSourceData saveDataSourceData(DataSourceData dataSourceData) {
		// TODO Auto-generated method stub
		return dataSourceDataDao.addEntity(dataSourceData);
	}

	@Override
	public Boolean deleteDataSourceData(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> cond = Maps.newConcurrentMap();
		cond.put("id", id);
		return dataSourceDataDao.deleteEntityByCond(cond);
	}

	@Override
	public Case saveCase(Case usercase) {
		return caseDao.addEntity(usercase);
	}

	@Override
	public Boolean deleteCase(String caseId) {
		// TODO Auto-generated method stub
		Case usercase = new Case();
		usercase.setId(caseId);
		return caseDao.deleteEntityById(usercase);
	}

	@Override
	public Boolean deleteCaseRequest(String id) {
		CaseRequest entity = new CaseRequest();
		entity.setId(id);
		return caseRequestDao.deleteEntityById(entity);
	}

	@Override
	public Boolean updateCaseRequest(CaseRequest docRequest) {
		return caseRequestDao.updateEntityById(docRequest);
	}

	@Override
	public CaseRequest saveCaseRequest(CaseRequest docRequest) {
		return caseRequestDao.addEntity(docRequest);
	}
	
	@Override
	public CaseRequest getCaseRequestById(String id){
		CaseRequest cr = new CaseRequest();
		cr.setId(id);
		return caseRequestDao.findEntityById(cr);
	}

	@Override
	public Boolean deleteCaseReq(String id) {
		// TODO Auto-generated method stub
		CaseRequest cr = new CaseRequest();
		cr.setId(id);
		return caseRequestDao.deleteEntityById(cr);
	}

	@Override
	public List<CaseRequestLog> getCaseRequestLog(Integer userId,Integer pageNo) {
		IQueryParam param = new QueryParam();
		param.addOutput("id", "");
		param.addOutput("name", "");
		param.addOutput("url", "");
		param.addOutput("method", "");
		
		param.addInput("userId", userId);
		if (pageNo == null) {
			param.addLimitStart(0);
		}else{
			param.addLimitStart((pageNo-1)*10);
		}
		param.addLimitCount(10);
		param.addSort("updateTime", Sort.DESC);
		/*Page<CaseRequestLog> page = new Page<CaseRequestLog>();
		if (null != pageNo) {
			page.setPageNo(pageNo);
			page.setPageSize(10);
		} else {
			page = null;
		}*/
		Page<CaseRequestLog> res = caseRequestLogDao.findEntityListByCond(param, null);
		if (res != null && res.getResult() != null && res.getResult().size() > 0) {
			return res.getResult();
		}
		return null;
	}

	@Override
	public CaseRequestLog getCaseRequestLogById(String id) {
		CaseRequestLog log = new CaseRequestLog();
		log.setId(id);
		return caseRequestLogDao.findEntityById(log);
	}

}
