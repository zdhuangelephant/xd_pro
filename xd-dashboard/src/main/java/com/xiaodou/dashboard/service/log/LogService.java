package com.xiaodou.dashboard.service.log;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.dao.alarm.AlarmEventDao;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.log.ActionModel;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.dashboard.service.facade.LogServiceFacade;
import com.xiaodou.dashboard.util.ActionStatisticInfo;
import com.xiaodou.dashboard.vo.log.ActionModelVo;
import com.xiaodou.dashboard.vo.log.request.ActionRequest;
import com.xiaodou.dashboard.vo.log.request.ProjectModelRequest;
import com.xiaodou.dashboard.vo.log.response.ProjectModelVO;
import com.xiaodou.dashboard.vo.log.response.ProjectResponse;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

@Service("logService")
public class LogService {
  @Resource
  LogServiceFacade logServiceFacade;
  @Resource
  AlarmEventDao alarmEventDao;

  public Page<ProjectModelVO> getProjectModelList(String projectName, String excutePoint,
      Integer pageNo) {
    Map<String, Object> input = Maps.newHashMap();
    if (StringUtils.isNotBlank(projectName))
      input.put("projectName", new MongoFieldParam(projectName, Scope.LIKE));
    if (StringUtils.isNotBlank(excutePoint))
      input.put("excutePoint", new MongoFieldParam(excutePoint, Scope.LIKE));
    Page<ProjectModel> projectModelPage = new Page<>();
    projectModelPage.setPageNo(pageNo);
    projectModelPage.setPageSize(Constant.PAGE_SIZE);
    projectModelPage = logServiceFacade.getProjectModelListByCond(input, projectModelPage);
    Page<ProjectModelVO> projectModelVoPage =
        new Page<>(projectModelPage.getPageNo(), projectModelPage.getPageSize());
    if (null == projectModelPage || null == projectModelPage.getResult()
        || projectModelPage.getResult().size() == 0) return projectModelVoPage;
    List<ProjectModelVO> projectModelVoList =
        ActionStatisticInfo.getProjectModelVOList(projectModelPage.getResult());
    projectModelVoPage.setResult(projectModelVoList);
    projectModelVoPage.setTotalPage(projectModelPage.getTotalPage());
    projectModelVoPage.setTotalCount(projectModelPage.getTotalCount());
    return projectModelVoPage;
  }
  
  public Page<ProjectModelVO> getErrorProjectModelList(String projectName, String excutePoint) {
	    Map<String, Object> input = Maps.newHashMap();
	    if (StringUtils.isNotBlank(projectName))
	      input.put("projectName", new MongoFieldParam(projectName, Scope.LIKE));
	    if (StringUtils.isNotBlank(excutePoint))
	      input.put("excutePoint", new MongoFieldParam(excutePoint, Scope.LIKE));
	    Page<ProjectModel> projectModelPage = new Page<>();
	    projectModelPage = logServiceFacade.getProjectModelListByCond(input, null);
	    Page<ProjectModelVO> projectModelVoPage =new Page<>();
	    if (null == projectModelPage.getResult()
	        || projectModelPage.getResult().size() == 0) return projectModelVoPage;
	    List<ProjectModelVO> projectModelVoList =
	        ActionStatisticInfo.getProjectModelVOList(projectModelPage.getResult());
	    List<ProjectModelVO> errorProjectModelVoList =Lists.newArrayList();
	    for(ProjectModelVO p:projectModelVoList){
	    	if(p.getCurrHourErrorPercent()>0||p.getOneHourAgoErrorPercent()>0||p.getTwoHourAgoErrorPercent()>0
	    	  ||p.getCurrHourOverTimePercent()>0||p.getOneHourAgoOverTimePercent()>0||p.getTwoHourAgoOverTimePercent()>0){
	    		errorProjectModelVoList.add(p);
	    	}
	    }
	    projectModelVoPage.setResult(errorProjectModelVoList);
	    return projectModelVoPage;
	  }

  public ProjectModel queryProjectById(String projectId) {
    return logServiceFacade.queryProjectById(projectId);
  }

  public ResultInfo deleteProject(String id) {
    if (logServiceFacade.deleteProjectById(id))
      return new ResultInfo(ResultType.SUCCESS);
    else
      return new ResultInfo(ResultType.SYSFAIL);
  }

  public ResultInfo updateProductModel(ProjectModelRequest project) {
    if (StringUtils.isBlank(project.getProjectId())) return new ResultInfo(ResultType.SYSFAIL);
    if (logServiceFacade.updateProjectInfo(project))
      return new ResultInfo(ResultType.SUCCESS);
    else
      return new ResultInfo(ResultType.SYSFAIL);
  }

  public ResultInfo addProductModel(ProjectModelRequest project) {
    String projectId = project.getUniqueId();
    ProjectModel projectModel = logServiceFacade.queryProjectById(projectId);
    if (null == projectModel) {
      project.setProjectId(projectId);
      logServiceFacade.insertProjectModel(project);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ResultInfo addAllProductModel(ProjectModelRequest project) {
    Page<ProjectModel> projectList = logServiceFacade.getProjectModelListByCond(null, null);
    Map<String, String> projectMap = packageProject(projectList);
    Map<String, String> allProjectMap = ActionStatisticInfo.getProjectList();
    if (null == allProjectMap || allProjectMap.size() == 0)
      return new ResultInfo(ResultType.SUCCESS);
    for (String projectName : allProjectMap.keySet()) {
      Map<String, String> excutePointMap = ActionStatisticInfo.getExcutePointList(projectName);
      if (null == excutePointMap || excutePointMap.size() == 0) continue;
      for (String excutePoint : excutePointMap.keySet()) {
        String key = String.format("%s:%s", projectName, excutePoint);
        if (projectMap.containsKey(key)) continue;
        project.setProjectName(projectName);
        project.setExcutePoint(excutePoint);
        project.setProjectId(project.getUniqueId());
        logServiceFacade.insertProjectModel(project);
      }
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ProjectResponse getProjectById(String id) {
    ProjectResponse response = new ProjectResponse(ResultType.SUCCESS);
    ProjectModel project = logServiceFacade.queryProjectById(id);
    response.setProject(project);
    if (StringUtils.isNotBlank(project.getAlarmEventId())) {
      AlarmEventDo event = alarmEventDao.getEventModelById(project.getAlarmEventId());
      response.setEvent(event);
    }
    return response;
  }

  public List<ProjectModelVO> getWaitProjectModelList() {
    List<ProjectModel> waitProjectList = Lists.newArrayList();
    Page<ProjectModel> projectList = logServiceFacade.getProjectModelListByCond(null, null);
    Map<String, String> projectMap = packageProject(projectList);
    Map<String, String> allProjectMap = ActionStatisticInfo.getProjectList();
    if (null == allProjectMap || allProjectMap.size() == 0) return null;
    for (String projectName : allProjectMap.keySet()) {
      Map<String, String> excutePointMap = ActionStatisticInfo.getExcutePointList(projectName);
      if (null == excutePointMap || excutePointMap.size() == 0) continue;
      for (String excutePoint : excutePointMap.keySet()) {
        String key = String.format("%s:%s", projectName, excutePoint);
        if (projectMap.containsKey(key)) continue;
        ProjectModel project = new ProjectModel();
        project.setProjectName(projectName);
        project.setExcutePoint(excutePoint);
        waitProjectList.add(project);
      }
    }
    return ActionStatisticInfo.getProjectModelVOList(waitProjectList);
  }

  private Map<String, String> packageProject(Page<ProjectModel> projectPage) {
    Map<String, String> projectMap = Maps.newHashMap();
    if (null != projectPage && null != projectPage.getResult()
        && projectPage.getResult().size() > 0) {
      for (ProjectModel project : projectPage.getResult())
        projectMap.put(String.format("%s:%s", project.getProjectName(), project.getExcutePoint()),
            project.getProjectId());
    }
    return projectMap;
  }

  public List<String> getServerList(ProjectModel project) {
    ActionModelVo m = new ActionModelVo();
    m.setProjectName(project.getProjectName());
    m.setExcutePoint(project.getExcutePoint());
    m.setLogTime(new Timestamp(System.currentTimeMillis()));
    ActionStatisticInfo info = new ActionStatisticInfo(m);
    return info.getServerList();
  }

  public Page<ActionModel> getActionPage(ActionRequest request) {
    IQueryParam param = new QueryParam();
    param.addInput("logName", "out_in");
    if (StringUtils.isNotBlank(request.getProjectName()))
      param.addInput("projectName", request.getProjectName());
    if (StringUtils.isNotBlank(request.getExcutePoint()))
      param.addInput("excutePoint", request.getExcutePoint());
    if (StringUtils.isNotBlank(request.getServerName()))
      param.addInput("serverName", request.getServerName());
    if (null != request.getHasError()) param.addInput("hasError", request.getHasError());
    if (StringUtils.isNotBlank(request.getLowerTime())) {
      String a[] = request.getLowerTime().split("/");
      Calendar cal = new GregorianCalendar();
      cal.set(Integer.parseInt(a[2]), Integer.parseInt(a[0]) - 1, Integer.parseInt(a[1]));
      MongoFieldParam fieldParam = new MongoFieldParam();
      fieldParam.setScope(Scope.GE);
      fieldParam.setValue(cal.getTime());
      param.addInput("logTime", fieldParam);
    }
    if (StringUtils.isNotBlank(request.getUpperTime())) {
      String a[] = request.getUpperTime().split("/");
      Calendar cal = new GregorianCalendar();
      cal.set(Integer.parseInt(a[2]), Integer.parseInt(a[0]) - 1, Integer.parseInt(a[1]));
      MongoFieldParam fieldParam = new MongoFieldParam();
      fieldParam.setScope(Scope.LE);
      fieldParam.setValue(cal.getTime());
      param.addInput("logTime", fieldParam);
    }
    param.addSort("logTime", Sort.DESC);
    param.addOutputs(CommUtil.getAllField(ActionModel.class));
    Page<ActionModel> pageInfo = new Page<>(request.getPage(), Constant.PAGE_SIZE);
    return logServiceFacade.getActionModelPageByCond(param, pageInfo);
  }

}
