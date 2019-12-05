package com.xiaodou.logmonitor.statistic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Response;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.IJedisPipeLineOperation;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.logmonitor.domain.OutInModel;

/**
 * @name @see com.xiaodou.logmonitor.statistic.OverTimeStatisticInfo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年10月13日
 * @description 请求超时日志记录情况
 * @version 1.0
 */
public class OverTimeStatisticInfo {
  private static final DateFormat SDF_DATE_HOUR_MIN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  private static final DateFormat SDF_DATE_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");
  private static final DateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final String PART_KEY_ALL = "logmonitor:outin:part:all:%s";
  private static final String TOTAL_KEY_OVERTIME = "logmonitor:outin:total:overtime:%s";
  private static final String PART_KEY_OVERTIME = "logmonitor:outin:part:overtime:%s";
  private static final Integer CACHE_TIME = 86400;
  private static final Integer CACHE_DAY_NUM = 30;
  private static final Integer CACHE_MONTH_NUM = 12;
  private OutInModel model;
  private String timeMin;
  private String timeHour;
  private String timeDate;
  private String totalSurfixMin;
  private String partSurfixMin;
  private String totalSurfixHour;
  private String partSurfixHour;
  private String totalSurfixDate;
  private String partSurfixDate;
  // log key
  private String partOverTimeMinKey;
  private String totalOverTimeMinKey;
  private String partOverTimeHourKey;
  private String totalOverTimeHourKey;
  private String partOverTimeDateKey;
  private String totalOverTimeDateKey;
  private Long partOverTime = 0l, totalOverTime = 0l, partAllTime = 0l, totalAllTime = 0l;

  public OverTimeStatisticInfo(OutInModel outInModel) {
    model = outInModel;
    timeMin = SDF_DATE_HOUR_MIN.format(model.getLogTime());
    timeHour = SDF_DATE_HOUR.format(model.getLogTime());
    timeDate = SDF_DATE.format(model.getLogTime());

    String.format("%s:%s", model.getProjectName(), model.getExcutePoint());
    totalSurfixMin =
        String.format("%s:%s:%s", timeMin, model.getProjectName(), model.getExcutePoint());
    partSurfixMin =
        String.format("%s:%s:%s:%s", timeMin, model.getProjectName(), model.getExcutePoint(),
            model.getServerName());
    totalSurfixHour =
        String.format("%s:%s:%s", timeHour, model.getProjectName(), model.getExcutePoint());
    partSurfixHour =
        String.format("%s:%s:%s:%s", timeHour, model.getProjectName(), model.getExcutePoint(),
            model.getServerName());
    totalSurfixDate =
        String.format("%s:%s:%s", timeDate, model.getProjectName(), model.getExcutePoint());
    partSurfixDate =
        String.format("%s:%s:%s:%s", timeDate, model.getProjectName(), model.getExcutePoint(),
            model.getServerName());
    // log key
    partOverTimeMinKey = String.format(PART_KEY_OVERTIME, partSurfixMin);
    totalOverTimeMinKey = String.format(TOTAL_KEY_OVERTIME, totalSurfixMin);
    partOverTimeHourKey = String.format(PART_KEY_OVERTIME, partSurfixHour);
    totalOverTimeHourKey = String.format(TOTAL_KEY_OVERTIME, totalSurfixHour);
    partOverTimeDateKey = String.format(PART_KEY_OVERTIME, partSurfixDate);
    totalOverTimeDateKey = String.format(TOTAL_KEY_OVERTIME, totalSurfixDate);
  }


  public final void pushOutInStatistic() {
    final Map<String, Response<Long>> valMap = Maps.newHashMap();
    JedisUtil.groupOperationFromJedis(new IJedisPipeLineOperation() {
      @Override
      public void operationWithPipeLine(IPipeLineProxy<?> pipeLine) {
        // 添加统计聚合信息至缓存中
        if (null != model.getUseTime()) {
          valMap.put(partOverTimeMinKey, pipeLine.incr(partOverTimeMinKey));
          pipeLine.expire(partOverTimeMinKey, CACHE_TIME);
          valMap.put(totalOverTimeMinKey, pipeLine.incr(totalOverTimeMinKey));
          pipeLine.expire(totalOverTimeMinKey, CACHE_TIME);
          pipeLine.incr(partOverTimeHourKey);
          pipeLine.expire(partOverTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
          pipeLine.incr(totalOverTimeHourKey);
          pipeLine.expire(totalOverTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
          pipeLine.incr(partOverTimeDateKey);
          pipeLine.expire(partOverTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
          pipeLine.incr(totalOverTimeDateKey);
          pipeLine.expire(totalOverTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
        }
      }
    });
    Response<Long> partOverTimeVal = valMap.get(partOverTimeMinKey);
    if (null != partOverTimeVal) partOverTime = partOverTimeVal.get();
    Response<Long> totalOverTimeVal = valMap.get(totalOverTimeMinKey);
    if (null != totalOverTimeVal) totalOverTime = totalOverTimeVal.get();
  }



  public final Long getTotalAllTime() {
    return totalAllTime;
  }


  public final Long getPartAllTime() {
    return partAllTime;
  }


  public final OutInModel getModel() {
    return model;
  }


  public String getPartOverTimeDateKey() {
    return partOverTimeDateKey;
  }


  public void setPartOverTimeDateKey(String partOverTimeDateKey) {
    this.partOverTimeDateKey = partOverTimeDateKey;
  }

  public String getPartOverTimeHourKey() {
    return partOverTimeHourKey;
  }


  public void setPartOverTimeHourKey(String partOverTimeHourKey) {
    this.partOverTimeHourKey = partOverTimeHourKey;
  }

  public String getPartOverTimeMinKey() {
    return partOverTimeMinKey;
  }


  public void setPartOverTimeMinKey(String partOverTimeMinKey) {
    this.partOverTimeMinKey = partOverTimeMinKey;
  }

  public String getTotalOverTimeMinKey() {
    return totalOverTimeMinKey;
  }


  public void setTotalOverTimeMinKey(String totalOverTimeMinKey) {
    this.totalOverTimeMinKey = totalOverTimeMinKey;
  }

  public String getTotalOverTimeHourKey() {
    return totalOverTimeHourKey;
  }


  public void setTotalOverTimeHourKey(String totalOverTimeHourKey) {
    this.totalOverTimeHourKey = totalOverTimeHourKey;
  }

  public String getTotalOverTimeDateKey() {
    return totalOverTimeDateKey;
  }


  public void setTotalOverTimeDateKey(String totalOverTimeDateKey) {
    this.totalOverTimeDateKey = totalOverTimeDateKey;
  }

  public Long getTotalOverTime() {
    return totalOverTime;
  }


  public void setTotalOverTime(Long totalOverTime) {
    this.totalOverTime = totalOverTime;
  }

  public Long getPartOverTime() {
    return partOverTime;
  }


  public void setPartOverTime(Long partOverTime) {
    this.partOverTime = partOverTime;
  }

  public static class StatisticEntity {
    private String serverName;
    private Long partOverTime;
    private Long partAllTime;
    private Double overTimePercent;

    public String getServerName() {
      return serverName;
    }

    public void setServerName(String serverName) {
      this.serverName = serverName;
    }



    public Long getPartAllTime() {
      return partAllTime;
    }

    public void setPartAllTime(Long partAllTime) {
      this.partAllTime = partAllTime;
    }

    public Long getPartOverTime() {
      return partOverTime;
    }

    public void setPartOverTime(Long partOverTime) {
      this.partOverTime = partOverTime;
    }

    public Double getOverTimePercent() {
      return overTimePercent;
    }

    public void setOverTimePercent(Double overTimePercent) {
      this.overTimePercent = overTimePercent;
    }



  }

}
