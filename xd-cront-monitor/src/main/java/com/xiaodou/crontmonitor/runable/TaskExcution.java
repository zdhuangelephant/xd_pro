package com.xiaodou.crontmonitor.runable;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.xiaodou.autotest.core.model.Api;
import com.xiaodou.autotest.core.proxy.AbstractApiProxy;
import com.xiaodou.autotest.core.vo.ApiResult;
import com.xiaodou.autotest.core.vo.GlobalParamMap;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.crontmonitor.alarm.CrontMonitorAlarm;
import com.xiaodou.crontmonitor.container.ApiHolder;
import com.xiaodou.crontmonitor.model.MonitorApiLog;
import com.xiaodou.crontmonitor.service.facade.MonitorServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.crontab.runable.TaskExcution.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月20日
 * @description Quartz任务调度单元
 * @version 1.0
 */
public class TaskExcution extends QuartzJobBean {

  MonitorServiceFacade monitorServiceFacade;
  
  @Override
  protected void executeInternal(JobExecutionContext jobexecutioncontext)
      throws JobExecutionException {
    if(null == monitorServiceFacade) {
      monitorServiceFacade = SpringWebContextHolder.getBean("monitorServiceFacade");
    }
    Trigger trigger = jobexecutioncontext.getTrigger();
    String uniqueId = trigger.getKey().getName();
    GlobalParamMap global = new GlobalParamMap();
    Api api = ApiHolder.getApi(uniqueId);
    if (null == api) {
      return;
    }
    MonitorApiLog log = new MonitorApiLog();
    log.setApiId(api.getUniqueId());
    try {
      AbstractApiProxy proxy = api.getProtocol().getProxyType().newInstance();
      proxy.setName(api.getName());
      proxy.setApi(api);
      proxy.excute(global);
      ApiResult apiResult = api.getApiResult();
      log.init(apiResult);
      monitorServiceFacade.addMonitorLog(log);
      if (null != apiResult && !apiResult.getHasError()) {
        return;
      }
      LoggerUtil.alarmInfo(new CrontMonitorAlarm(api.getName(), api.getUrl()));
    } catch (RuntimeException | InstantiationException | IllegalAccessException e) {
      log.setMessage(e.getMessage());
      monitorServiceFacade.addMonitorLog(log);
      LoggerUtil.alarmInfo(new CrontMonitorAlarm(api.getName(), api.getUrl()));
    }
    global.clear();
  }

}
