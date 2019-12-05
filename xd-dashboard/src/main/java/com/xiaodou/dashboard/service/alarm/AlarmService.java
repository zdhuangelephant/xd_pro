package com.xiaodou.dashboard.service.alarm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicTimingLocalCache;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.dao.alarm.AlarmEventDao;
import com.xiaodou.dashboard.engine.EventContainer;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.alarm.local.EventPojo;
import com.xiaodou.dashboard.request.AlarmRequestPojo;
import com.xiaodou.dashboard.util.EnvironMentUtil;
import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.dashboard.service.AlarmService.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警服务service
 * @version 1.0
 */
@Service("alarmService")
public class AlarmService {

  private Calendar cal = new GregorianCalendar();

  @Resource
  AlarmEventDao alarmEventDao;

  @Resource
  QueueService queueService;
  private static ConcurrentHashMap<String,AlarmRequestPojo>  alarmData =new ConcurrentHashMap<String,AlarmRequestPojo>();
  private long startTime=System.currentTimeMillis();
  public void filterAlarm(AlarmRequestPojo pojo){
	  String key=String.format("%s%s", pojo.getEventModule(),pojo.getEventName());
	  if(alarmData.get(key)==null){
		  alarmData.put(key, pojo);
	  }
	  if(System.currentTimeMillis()-startTime>60000){
		 Iterator<Map.Entry<String,AlarmRequestPojo>> it=alarmData.entrySet().iterator();
		 while(it.hasNext()){
			 Map.Entry<String,AlarmRequestPojo> t=it.next();
			 alarm(t.getValue());
		 }
		  alarmData.clear();
		  startTime=System.currentTimeMillis();
	  }
  }
  public ResultInfo alarm(AlarmRequestPojo pojo) {
    ResultInfo result = new ResultInfo(ResultType.SUCCESS);
    EventPojo event = new EventPojo(pojo);
    event = EventContainer.getContainer().get(event);
    if (null == event) return result;
    dealRate(event);
    if (dealThreshold(event)) {
      dealTimeLine(event, pojo);
    }
    return result;
  }


  private void dealRate(EventPojo event) {
    // 清理超时信息
    alarmEventDao.clearTimeEventModel(event.getModule(), event.getName(),
        System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(event.getRate()));
    // 报警count+1
    alarmEventDao.addCountEventModel(event.getModule(), event.getName());
  }

  /**
   * 判断报警次数是否超过报警阀值
   * 
   * @param event
   * @return
   */
  private boolean dealThreshold(EventPojo event) {
    AlarmEventDo vo = alarmEventDao.getEventModel(event.getModule(), event.getName());
    if (null != vo && null != vo.getCount()
        && Double.parseDouble(vo.getCount().toString()) >= vo.getThreshold()) return true;
    return false;
  }

  /**
   * 发短信+邮件报警
   * 
   * @param event
   * @param pojo
   */
  private void dealTimeLine(EventPojo event, AlarmRequestPojo pojo) {
    String key = String.format("module:%s:event:%s", pojo.getEventModule(), pojo.getEventName());
    if (null == DynamicTimingLocalCache.getCache(key)) {
      DynamicTimingLocalCache.cache(key, StringUtils.EMPTY, 2);
      SendInfoVo infovo = new SendInfoVo(event, pojo);
      cal.setTime(new Date());
      if (cal.get(Calendar.HOUR_OF_DAY) > event.getStarttime()
          && cal.get(Calendar.HOUR_OF_DAY) < event.getEndtime()) {
        if (EnvironMentUtil.isOnline() && StringUtils.isNotBlank(event.getMessage())) {
          queueService.sendMessage(infovo);
        }
        if (EnvironMentUtil.isOnline() && StringUtils.isNotBlank(event.getDingURL())) {
          queueService.sendDing(infovo);
        }
      }
      if (StringUtils.isNotBlank(event.getMail())) {
        queueService.sendMail(infovo);
      }
    }
    alarmEventDao.clearEventModel(event.getModule(), event.getName());
  }
}
