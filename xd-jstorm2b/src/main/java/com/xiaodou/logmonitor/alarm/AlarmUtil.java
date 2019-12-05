package com.xiaodou.logmonitor.alarm;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.logmonitor.constant.Constant;
import com.xiaodou.logmonitor.domain.OutInModel;
import com.xiaodou.logmonitor.statistic.OutInStatisticInfo;
import com.xiaodou.logmonitor.statistic.OutInStatisticInfo.StatisticEntity;
import com.xiaodou.logmonitor.statistic.OverTimeStatisticInfo;

public class AlarmUtil {

  public static void alarmOutIn(OutInStatisticInfo info) {
    OutInModel outInModel = info.getModel();
    Long partFailTime = info.getPartFailTime(), totalFailTime = info.getTotalFailTime(), partAllTime =
        info.getPartAllTime(), totalAllTime = info.getTotalAllTime();
    if (partFailTime > 0 && partAllTime > 0) {
      Double partFailPercent = partFailTime.doubleValue() / partAllTime.doubleValue();
      if (partFailPercent == 0d || partFailPercent < Constant.OUTIN_THRESHOLD_PERCENT) return;
      OutInPartAlarmRecord record = new OutInPartAlarmRecord();
      record.setProjectName(outInModel.getProjectName());
      record.setExcutePoint(outInModel.getExcutePoint());
      record.setFailPercent(partFailPercent);
      record.setServerName(outInModel.getServerName());
      LoggerUtil.alarmInfo(record);
    }
    if (totalFailTime > 0 && totalAllTime > 0) {
      Double totalFailPercent = totalFailTime.doubleValue() / totalAllTime.doubleValue();
      if (totalFailPercent == 0d || totalFailPercent < Constant.OUTIN_THRESHOLD_PERCENT) return;
      OutInTotalAlarmRecord record = new OutInTotalAlarmRecord();
      record.setProjectName(outInModel.getProjectName());
      record.setExcutePoint(outInModel.getExcutePoint());
      record.setFailPercent(totalFailPercent);
      List<StatisticEntity> statisticList = info.getServerStatisticList();
      if (null != statisticList && statisticList.size() > 0) {
        for (StatisticEntity entity : statisticList) {
          record.pushPartDistribution(entity.getServerName(), entity.getFailPercent());
        }
      }
      LoggerUtil.alarmInfo(record);
    }
  }

  
  public static void alarmOverTime(OverTimeStatisticInfo info,OutInStatisticInfo outInInfo) {
	    OutInModel outInModel = info.getModel();
	    Long partOverTime = info.getPartOverTime(), totalOverTime = info.getTotalOverTime(), partAllTime =
	    	outInInfo.getPartAllTime(), totalAllTime = outInInfo.getTotalAllTime();
	    if (partOverTime > 0 && partAllTime > 0) {
	      Double partOverTimePercent = partOverTime.doubleValue() / partAllTime.doubleValue();
	      if (partOverTimePercent == 0d || partOverTimePercent < Constant.OVERTIME_THRESHOLD_PERCENT) return;
	      OverTimePartAlarmRecord record = new OverTimePartAlarmRecord();
	      record.setProjectName(outInModel.getProjectName());
	      record.setExcutePoint(outInModel.getExcutePoint());
	      record.setOverTimePercent(partOverTime);
	      record.setServerName(outInModel.getServerName());
	      LoggerUtil.alarmInfo(record);
	    }
	    if (totalOverTime > 0 && totalAllTime > 0) {
	      Double totalOverTimePercent = totalOverTime.doubleValue() / totalAllTime.doubleValue();
	      if (totalOverTimePercent == 0d || totalOverTimePercent < Constant.OVERTIME_THRESHOLD_PERCENT) return;
	      OverTimeTotalAlarmRecord record = new OverTimeTotalAlarmRecord();
	      record.setProjectName(outInModel.getProjectName());
	      record.setExcutePoint(outInModel.getExcutePoint());
	      record.setOverTimePercent(totalOverTimePercent);
	      LoggerUtil.alarmInfo(record);
	    }
	  }

}
