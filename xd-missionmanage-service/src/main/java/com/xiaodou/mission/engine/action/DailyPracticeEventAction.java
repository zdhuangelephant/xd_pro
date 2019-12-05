package com.xiaodou.mission.engine.action;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.event.DailyPracticeEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;
import com.xiaodou.mission.engine.model.UserTollgateDataDetailInstance;

/**
 * @name DailyPracticeEventAction CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月16日
 * @description 每日练习事件动作
 * @version 1.0
 */
public class DailyPracticeEventAction extends AbstractAction<DailyPracticeEvent> {

  @Override
  public void processCoreParam(DailyPracticeEvent event, UserCollectDataInstance coreParam) {
    if (null == event || null == event.getScore() || null == event.getLCurrentTime()
        || event.getScore() < MissionConstant.PASS_LINE) {
      return;
    }
    Timestamp now = new Timestamp(System.currentTimeMillis());
    String currentDate = DateUtil.SDF_YMD.format(now);
    UserTollgateDataDetailInstance tollgateInfo = coreParam.getTollgateInfo();
    if (null != tollgateInfo && null != tollgateInfo.getDailyPracticeTime()) {
      String lastDailyPracticeDate = DateUtil.SDF_YMD.format(tollgateInfo.getDailyPracticeTime());
      if (currentDate.equals(lastDailyPracticeDate)) {
        return;
      }
      tollgateInfo.setTotalDailyPractice(tollgateInfo.getTotalDailyPractice() + 1);
      if (null == tollgateInfo.getDailyPracticeTime()) {
        tollgateInfo.setContinDailyPractice(1);
      } else {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Timestamp(tollgateInfo.getDailyPracticeTime()));
        cal.add(Calendar.DATE, 1);
        String continDate = DateUtil.SDF_YMD.format(cal.getTime());
        if (continDate.equals(currentDate)) {
          tollgateInfo.setContinDailyPractice(tollgateInfo.getContinDailyPractice() + 1);
        } else {
          tollgateInfo.setContinDailyPractice(1);
        }
      }
    } else {
      tollgateInfo.setTotalDailyPractice(tollgateInfo.getTotalDailyPractice() + 1);
      tollgateInfo.setContinDailyPractice(1);
    }
    tollgateInfo.setDailyPracticeTime(event.getLCurrentTime());
  }

  @Override
  public void processOtherParam(DailyPracticeEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(DailyPracticeEvent event, Map<String, Object> shareParam) {}

}
