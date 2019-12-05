package com.xiaodou.dashboard.service.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dashboard.constant.Constant;
import com.xiaodou.dashboard.dao.alarm.AlarmEventDao;
import com.xiaodou.dashboard.dao.log.ActionModelDao;
import com.xiaodou.dashboard.dao.log.ProjectModelDao;
import com.xiaodou.dashboard.dao.trace.TraceModelDao;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.log.ActionModel;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.dashboard.model.trace.TraceModel;
import com.xiaodou.dashboard.vo.log.request.ProjectModelRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("logServiceFacade")
public class LogServiceFacadeImpl implements LogServiceFacade {

  @Resource
  ProjectModelDao projectModelDao;

  @Resource
  AlarmEventDao alarmEventDao;

  @Resource
  ActionModelDao actionModelDao;
  
  @Resource
  TraceModelDao traceModelDao;

  @Override
  public Page<ProjectModel> getProjectModelListByCond(Map<String, Object> input,
      Page<ProjectModel> page) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(CommUtil.getAllField(ProjectModel.class));
    return projectModelDao.findEntityListByCond(param, page);
  }

  @Override
  public void insertProjectModel(ProjectModelRequest projectRequest) {
    AlarmEventDo eventDo =
        alarmEventDao.getEventModel(projectRequest.getProjectName(),
            projectRequest.getExcutePoint());
    if (null != eventDo) {
      eventDo.setRate(projectRequest.getRate());
      eventDo.setType(Constant.ALARM_MONITOR_TYPE);
      eventDo.setThreshold(projectRequest.getThreshold());
      alarmEventDao.updateEventModelById(eventDo);
    } else {
      eventDo = projectRequest.getEventDo();
      eventDo.setAlarmEventId(UUID.randomUUID().toString());
      eventDo.setType(Constant.ALARM_MONITOR_TYPE);
      eventDo.setCount(0d);
      eventDo.setSystime(System.currentTimeMillis());
      alarmEventDao.insert(eventDo);
    }
    ProjectModel project = projectRequest.getProjectModel();
    project.setAlarmEventId(eventDo.getAlarmEventId());
    projectModelDao.addEntity(project);
  }

  @Override
  public ProjectModel queryProjectById(String projectId) {
    ProjectModel project = new ProjectModel();
    project.setProjectId(projectId);
    return projectModelDao.findEntityById(project);
  }

  @Override
  public boolean deleteProjectById(String id) {
    ProjectModel project = new ProjectModel();
    project.setProjectId(id);
    return projectModelDao.deleteEntityById(project);
  }

  @Override
  public boolean updateProjectInfo(ProjectModelRequest projectRequest) {
    AlarmEventDo eventDo =
        alarmEventDao.getEventModel(projectRequest.getProjectName(),
            projectRequest.getExcutePoint());
    if (null != eventDo) {
      eventDo.setRate(projectRequest.getRate());
      eventDo.setType(Constant.ALARM_MONITOR_TYPE);
      eventDo.setThreshold(projectRequest.getThreshold());
      alarmEventDao.updateEventModelById(eventDo);
    } else {
      eventDo = projectRequest.getEventDo();
      eventDo.setAlarmEventId(UUID.randomUUID().toString());
      eventDo.setType(Constant.ALARM_MONITOR_TYPE);
      eventDo.setCount(0d);
      eventDo.setSystime(System.currentTimeMillis());
      alarmEventDao.insert(eventDo);
    }
    ProjectModel project = projectRequest.getProjectModel();
    project.setAlarmEventId(eventDo.getAlarmEventId());
    return projectModelDao.updateEntityById(project);
  }

  @Override
  public Page<ActionModel> getActionModelPageByCond(IQueryParam param, Page<ActionModel> page) {
    return actionModelDao.findEntityListByCond(param, page);
  }

@Override
public List<TraceModel> getTraceModelListCond(Map<String, Object> cond) {
	// TODO Auto-generated method stub
	 IQueryParam param = new QueryParam();
	 param.addInputs(cond);
	 param.addOutputs(CommUtil.getAllField(TraceModel.class));
	return traceModelDao.findEntityListByCond(param, null).getResult();
}

@Override
public List<TraceModel> getTraceModelListById(String traceId) {
	// TODO Auto-generated method stub
	 Map<String, Object> cond=Maps.newConcurrentMap();
	 cond.put("traceId", traceId);
	 IQueryParam param = new QueryParam();
		param.addInputs(cond);
	 param.addOutputs(CommUtil.getAllField(TraceModel.class));
	return traceModelDao.findEntityListByCond(param, null).getResult();
}
}
