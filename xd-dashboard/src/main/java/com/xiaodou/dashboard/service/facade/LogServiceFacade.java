package com.xiaodou.dashboard.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.dashboard.model.log.ActionModel;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.dashboard.model.trace.TraceModel;
import com.xiaodou.dashboard.vo.log.request.ProjectModelRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

public interface LogServiceFacade {

  Page<ProjectModel> getProjectModelListByCond(Map<String, Object> input, Page<ProjectModel> page);

  Page<ActionModel> getActionModelPageByCond(IQueryParam param, Page<ActionModel> page);

  void insertProjectModel(ProjectModelRequest project);

  ProjectModel queryProjectById(String projectId);

  boolean deleteProjectById(String id);

  boolean updateProjectInfo(ProjectModelRequest project);
  
  
  //tarce
  
  List<TraceModel> getTraceModelListCond(Map<String, Object> cond);
  
  List<TraceModel> getTraceModelListById(String traceId);

}
