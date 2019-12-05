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
import com.xiaodou.logmonitor.constant.Constant;
import com.xiaodou.logmonitor.domain.OutInModel;

/**
 * @name @see com.xiaodou.logmonitor.statistic.OutInStatisticInfo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月26日
 * @description OutIn日志记录情况
 * @version 1.0
 */
public class OutInStatisticInfo {
  private static final DateFormat SDF_DATE_HOUR_MIN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  private static final DateFormat SDF_DATE_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");
  private static final DateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final String PROJECT_NAME_KEY = "logmonitor:outin:projectName";
  private static final String EXCUTE_POINT_KEY = "logmonitor:outin:%s:excutePoint";
  private static final String TOTAL_KEY_SERVER = "logmonitor:outin:server:%s";
  private static final String TOTAL_KEY_IP = "logmonitor:outin:ip:%s";
  private static final String TOTAL_KEY_MAC = "logmonitor:outin:mac:%s";
  private static final String TOTAL_KEY_ALL = "logmonitor:outin:total:all:%s";
  private static final String TOTAL_KEY_FAIL = "logmonitor:outin:total:fail:%s";
  private static final String PART_KEY_ALL = "logmonitor:outin:part:all:%s";
  private static final String PART_KEY_FAIL = "logmonitor:outin:part:fail:%s";
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
  private String serverSurfix;
  // server key
  private String totalServerKey;
  private String totalIpKey;
  private String totalMacKey;
  // log key
  private String partFailTimeMinKey;
  private String totalFailTimeMinKey;
  private String partFailTimeHourKey;
  private String totalFailTimeHourKey;
  private String partFailTimeDateKey;
  private String totalFailTimeDateKey;
  private String partAllTimeMinKey;
  private String totalAllTimeMinKey;
  private String partAllTimeHourKey;
  private String totalAllTimeHourKey;
  private String partAllTimeDateKey;
  private String totalAllTimeDateKey;
  private Long partFailTime = 0l, totalFailTime = 0l, partAllTime = 0l, totalAllTime = 0l;

  public OutInStatisticInfo(OutInModel outInModel) {
    model = outInModel;
    timeMin = SDF_DATE_HOUR_MIN.format(model.getLogTime());
    timeHour = SDF_DATE_HOUR.format(model.getLogTime());
    timeDate = SDF_DATE.format(model.getLogTime());

    serverSurfix = String.format("%s:%s", model.getProjectName(), model.getExcutePoint());
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
    // server key
    totalServerKey = String.format(TOTAL_KEY_SERVER, serverSurfix);
    totalIpKey = String.format(TOTAL_KEY_IP, serverSurfix);
    totalMacKey = String.format(TOTAL_KEY_MAC, serverSurfix);
    // log key
    partFailTimeMinKey = String.format(PART_KEY_FAIL, partSurfixMin);
    totalFailTimeMinKey = String.format(TOTAL_KEY_FAIL, totalSurfixMin);
    partAllTimeMinKey = String.format(PART_KEY_ALL, partSurfixMin);
    totalAllTimeMinKey = String.format(TOTAL_KEY_ALL, totalSurfixMin);
    partFailTimeHourKey = String.format(PART_KEY_FAIL, partSurfixHour);
    totalFailTimeHourKey = String.format(TOTAL_KEY_FAIL, totalSurfixHour);
    partAllTimeHourKey = String.format(PART_KEY_ALL, partSurfixHour);
    totalAllTimeHourKey = String.format(TOTAL_KEY_ALL, totalSurfixHour);
    partFailTimeDateKey = String.format(PART_KEY_FAIL, partSurfixDate);
    totalFailTimeDateKey = String.format(TOTAL_KEY_FAIL, totalSurfixDate);
    partAllTimeDateKey = String.format(PART_KEY_ALL, partSurfixDate);
    totalAllTimeDateKey = String.format(TOTAL_KEY_ALL, totalSurfixDate);
  }


  public final void pushOutInStatistic() {
    final Map<String, Response<Long>> valMap = Maps.newHashMap();
    JedisUtil.groupOperationFromJedis(new IJedisPipeLineOperation() {
      @Override
      public void operationWithPipeLine(IPipeLineProxy<?> pipeLine) {
        // 1 添加 projectName 与 excutePoint至缓存中
        pipeLine.hset(PROJECT_NAME_KEY, model.getProjectName(), Constant.YES);
        pipeLine.expire(PROJECT_NAME_KEY, CACHE_TIME);
        String expointKey = String.format(EXCUTE_POINT_KEY, model.getProjectName());
        pipeLine.hset(expointKey, model.getExcutePoint(), Constant.YES);
        pipeLine.expire(expointKey, CACHE_TIME);
        // 2 添加server信息至缓存中
        pipeLine.hset(totalServerKey, model.getServerName(), Constant.YES);
        pipeLine.expire(totalServerKey, CACHE_TIME);
        pipeLine.hset(totalIpKey, model.getLocalIp(), Constant.YES);
        pipeLine.expire(totalIpKey, CACHE_TIME);
        pipeLine.hset(totalMacKey, model.getLocalMac(), Constant.YES);
        pipeLine.expire(totalMacKey, CACHE_TIME);
        // 3 添加统计聚合信息至缓存中
        if (null != model.getHasError() && model.getHasError()) {
          valMap.put(partFailTimeMinKey, pipeLine.incr(partFailTimeMinKey));
          pipeLine.expire(partFailTimeMinKey, CACHE_TIME);
          valMap.put(totalFailTimeMinKey, pipeLine.incr(totalFailTimeMinKey));
          pipeLine.expire(totalFailTimeMinKey, CACHE_TIME);
          pipeLine.incr(partFailTimeHourKey);
          pipeLine.expire(partFailTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
          pipeLine.incr(totalFailTimeHourKey);
          pipeLine.expire(totalFailTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
          pipeLine.incr(partFailTimeDateKey);
          pipeLine.expire(partFailTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
          pipeLine.incr(totalFailTimeDateKey);
          pipeLine.expire(totalFailTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
        }
        valMap.put(partAllTimeMinKey, pipeLine.incr(partAllTimeMinKey));
        pipeLine.expire(partAllTimeMinKey, CACHE_TIME);
        valMap.put(totalAllTimeMinKey, pipeLine.incr(totalAllTimeMinKey));
        pipeLine.expire(totalAllTimeMinKey, CACHE_TIME);
        pipeLine.incr(partAllTimeHourKey);
        pipeLine.expire(partAllTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
        pipeLine.incr(totalAllTimeHourKey);
        pipeLine.expire(totalAllTimeHourKey, CACHE_TIME * CACHE_DAY_NUM);
        pipeLine.incr(partAllTimeDateKey);
        pipeLine.expire(partAllTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
        pipeLine.incr(totalAllTimeDateKey);
        pipeLine.expire(totalAllTimeDateKey, CACHE_TIME * CACHE_DAY_NUM * CACHE_MONTH_NUM);
      }
    });
    Response<Long> partFailTimeVal = valMap.get(partFailTimeMinKey);
    if (null != partFailTimeVal) partFailTime = partFailTimeVal.get();
    Response<Long> totalFailTimeVal = valMap.get(totalFailTimeMinKey);
    if (null != totalFailTimeVal) totalFailTime = totalFailTimeVal.get();
    Response<Long> partAllTimeVal = valMap.get(partAllTimeMinKey);
    if (null != partAllTimeVal) partAllTime = partAllTimeVal.get();
    Response<Long> totalAllTimeVal = valMap.get(totalAllTimeMinKey);
    if (null != totalAllTimeVal) totalAllTime = totalAllTimeVal.get();
  }

  public final Long getTotalFailTime() {
    return totalFailTime;
  }

  public final Long getTotalAllTime() {
    return totalAllTime;
  }

  public final Long getPartFailTime() {
    return partFailTime;
  }

  public final Long getPartAllTime() {
    return partAllTime;
  }

  public final List<String> getServerList() {
    Map<String, String> keyMap = JedisUtil.getAllMapValueFromJedis(totalServerKey);
    return Lists.newArrayList(keyMap.keySet());
  }

  public final List<String> getIpList() {
    Map<String, String> keyMap = JedisUtil.getAllMapValueFromJedis(totalIpKey);
    return Lists.newArrayList(keyMap.keySet());
  }

  public final List<String> getMacList() {
    Map<String, String> keyMap = JedisUtil.getAllMapValueFromJedis(totalMacKey);
    return Lists.newArrayList(keyMap.keySet());
  }

  public final OutInModel getModel() {
    return model;
  }

  public final List<StatisticEntity> getServerStatisticList() {
    List<String> serverList = getServerList();
    if (null != serverList && serverList.size() > 0) {
      List<StatisticEntity> list = Lists.newArrayList();
      for (String key : serverList) {
        String partSurfix =
            String.format("%s:%s:%s:%s", timeMin, model.getProjectName(), model.getExcutePoint(),
                key);
        String _partFailTimeKey = String.format(PART_KEY_FAIL, partSurfix);
        String _partAllTimeKey = String.format(PART_KEY_ALL, partSurfix);
        String _sPartFailTime = JedisUtil.getStringFromJedis(_partFailTimeKey);
        String _sPartAllTime = JedisUtil.getStringFromJedis(_partAllTimeKey);
        Long _partFailTime = 0l, _partAllTime = 0l;
        if (StringUtils.isAllNotBlank(_sPartFailTime, _sPartAllTime)) {
          _partFailTime = Long.parseLong(_sPartFailTime);
          _partAllTime = Long.parseLong(_sPartAllTime);
        }
        Double failPercent = 0d;
        if (_partFailTime > 0 && _partAllTime > 0)
          failPercent = _partFailTime.doubleValue() / _partAllTime.doubleValue();
        StatisticEntity entity = new StatisticEntity();
        entity.setServerName(key);
        entity.setPartFailTime(_partFailTime);
        entity.setPartAllTime(_partAllTime);
        entity.setFailPercent(failPercent);
        list.add(entity);
      }
      return list;
    }
    return null;
  }

  public static class StatisticEntity {
    private String serverName;
    private Long partFailTime;
    private Long partAllTime;
    private Double failPercent;

    public String getServerName() {
      return serverName;
    }

    public void setServerName(String serverName) {
      this.serverName = serverName;
    }

    public Long getPartFailTime() {
      return partFailTime;
    }

    public void setPartFailTime(Long partFailTime) {
      this.partFailTime = partFailTime;
    }

    public Long getPartAllTime() {
      return partAllTime;
    }

    public void setPartAllTime(Long partAllTime) {
      this.partAllTime = partAllTime;
    }

    public Double getFailPercent() {
      return failPercent;
    }

    public void setFailPercent(Double failPercent) {
      this.failPercent = failPercent;
    }

  }

}
