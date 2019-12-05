/*
 * Copyright 2015-2115 eLong Multiple DataSource Expand
 */
package com.xiaodou.summer.source.jdbc.datasource;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2015年1月8日
 */
public class SummerDataSourceManager {
  private AtomicBoolean isInited = new AtomicBoolean(false);
  private DataSourceConfig defaultDataSourceConfig;
  private DynamicDataSource defaultDataSource;

  private Map<String, DataSourceConfig> otherDataSourceConfigs = Maps.newHashMap();
  private Map<String, DynamicDataSource> otherDataSources = Maps.newHashMap();

  public SummerDataSourceManager() {
    reset();
  }

  private void reset() {}

  public DynamicDataSource getDefaultDataSource() throws SQLException {
    init();
    return defaultDataSource;
  }

  public Map<String, DynamicDataSource> getOtherDataSources() throws SQLException {
    init();
    return otherDataSources;
  }

  public AtomicBoolean getIsInited() {
    return isInited;
  }

  public void setIsInited(AtomicBoolean isInited) {
    this.isInited = isInited;
  }

  public DataSourceConfig getDefaultDataSourceConfig() {
    return defaultDataSourceConfig;
  }

  public void setDefaultDataSourceConfig(DataSourceConfig defaultDataSourceConfig) {
    this.defaultDataSourceConfig = defaultDataSourceConfig;
  }

  public Map<String, DataSourceConfig> getOtherDataSourceConfigs() {
    return otherDataSourceConfigs;
  }

  public void setOtherDataSourceConfigs(Map<String, DataSourceConfig> otherDataSourceConfigs) {
    this.otherDataSourceConfigs = otherDataSourceConfigs;
  }

  public void setDefaultDataSource(DynamicDataSource defaultDataSource) {
    this.defaultDataSource = defaultDataSource;
  }

  public void setOtherDataSources(Map<String, DynamicDataSource> otherDataSources) {
    this.otherDataSources = otherDataSources;
  }

  private synchronized void init() throws SQLException {
    if (isInited.compareAndSet(false, true)) {
      initDefaultDataSource();
      initOtherDataSources();
    }
  }

  public void initDefaultDataSource() throws SQLException {
    if (null != defaultDataSourceConfig) {
      defaultDataSource = new DynamicDataSource(this.defaultDataSourceConfig);
    }
  }

  public void initOtherDataSources() {
    if (!CollectionUtils.isEmpty(otherDataSourceConfigs)) {
      for(Map.Entry<String, DataSourceConfig> entry: otherDataSourceConfigs.entrySet()) {
          otherDataSources.put(entry.getKey(), new DynamicDataSource(entry.getValue()));
      }
    }
  }

}
