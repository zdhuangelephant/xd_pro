/*
 * Copyright 2015-2115 eLong
 * Multiple DataSource Expand
 */
package com.xiaodou.summer.source.jdbc.datasource;

import java.sql.SQLException;

import org.logicalcobwebs.proxool.ProxoolDataSource;

import com.xiaodou.common.util.StringUtils;

/**
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2015年1月8日
 */
public class SummerDataSourceManager {
  
  private int loginTimeout = 0;
  private String alias;
  private String driver;
  private String fatalSqlExceptionWrapperClass;
  private int houseKeepingSleepTime = 0;
  private String houseKeepingTestSql;
  private long maximumActiveTime = 0;
  private int maximumConnectionCount = 0;
  private int maximumConnectionLifetime = 0;
  private int minimumConnectionCount = 0;
  private int overloadWithoutRefusalLifetime = 0;
  private String password;
  private int prototypeCount = 0;
  private int recentlyStartedThreshold = 0;
  private int simultaneousBuildThrottle = 0;
  private String statistics;
  private String statisticsLogLevel;
  private boolean trace = false;
  private String driverUrl;
  private String user;
  private boolean verbose = false;
  private boolean jmx = false;
  private String jmxAgentId;
  private boolean testBeforeUse = false;
  private boolean testAfterUse = false;
  private String fatalSqlExceptionsAsString;
  
  private int sloginTimeout = 0;
  private String salias;
  private String sdriver;
  private String sfatalSqlExceptionWrapperClass;
  private int shouseKeepingSleepTime = 0;
  private String shouseKeepingTestSql;
  private long smaximumActiveTime = 0;
  private int smaximumConnectionCount = 0;
  private int smaximumConnectionLifetime = 0;
  private int sminimumConnectionCount = 0;
  private int soverloadWithoutRefusalLifetime = 0;
  private String spassword;
  private int sprototypeCount = 0;
  private int srecentlyStartedThreshold = 0;
  private int ssimultaneousBuildThrottle = 0;
  private String sstatistics;
  private String sstatisticsLogLevel;
  private boolean strace = false;
  private String sdriverUrl;
  private String suser;
  private boolean sverbose = false;
  private boolean sjmx = false;
  private String sjmxAgentId;
  private boolean stestBeforeUse = false;
  private boolean stestAfterUse = false;
  private String sfatalSqlExceptionsAsString;
  
  public int getLoginTimeout() {
    return loginTimeout;
  }

  public void setLoginTimeout(int loginTimeout) {
    this.loginTimeout = loginTimeout;
    this.sloginTimeout = loginTimeout;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
    this.salias = alias;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
    this.sdriver = driver;
  }

  public String getFatalSqlExceptionWrapperClass() {
    return fatalSqlExceptionWrapperClass;
  }

  public void setFatalSqlExceptionWrapperClass(String fatalSqlExceptionWrapperClass) {
    this.fatalSqlExceptionWrapperClass = fatalSqlExceptionWrapperClass;
    this.sfatalSqlExceptionWrapperClass = fatalSqlExceptionWrapperClass;
  }

  public int getHouseKeepingSleepTime() {
    return houseKeepingSleepTime;
  }

  public void setHouseKeepingSleepTime(int houseKeepingSleepTime) {
    this.houseKeepingSleepTime = houseKeepingSleepTime;
    this.shouseKeepingSleepTime = houseKeepingSleepTime;
  }

  public String getHouseKeepingTestSql() {
    return houseKeepingTestSql;
  }

  public void setHouseKeepingTestSql(String houseKeepingTestSql) {
    this.houseKeepingTestSql = houseKeepingTestSql;
    this.shouseKeepingTestSql = houseKeepingTestSql;
  }

  public long getMaximumActiveTime() {
    return maximumActiveTime;
  }

  public void setMaximumActiveTime(long maximumActiveTime) {
    this.maximumActiveTime = maximumActiveTime;
    this.smaximumActiveTime = maximumActiveTime;
  }

  public int getMaximumConnectionCount() {
    return maximumConnectionCount;
  }

  public void setMaximumConnectionCount(int maximumConnectionCount) {
    this.maximumConnectionCount = maximumConnectionCount;
    this.smaximumConnectionCount = maximumConnectionCount;
  }

  public int getMaximumConnectionLifetime() {
    return maximumConnectionLifetime;
  }

  public void setMaximumConnectionLifetime(int maximumConnectionLifetime) {
    this.maximumConnectionLifetime = maximumConnectionLifetime;
    this.smaximumConnectionLifetime = maximumConnectionLifetime;
  }

  public int getMinimumConnectionCount() {
    return minimumConnectionCount;
  }

  public void setMinimumConnectionCount(int minimumConnectionCount) {
    this.minimumConnectionCount = minimumConnectionCount;
    this.sminimumConnectionCount = minimumConnectionCount;
  }

  public int getOverloadWithoutRefusalLifetime() {
    return overloadWithoutRefusalLifetime;
  }

  public void setOverloadWithoutRefusalLifetime(int overloadWithoutRefusalLifetime) {
    this.overloadWithoutRefusalLifetime = overloadWithoutRefusalLifetime;
    this.soverloadWithoutRefusalLifetime = overloadWithoutRefusalLifetime;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
    this.spassword = password;
  }

  public int getPrototypeCount() {
    return prototypeCount;
  }

  public void setPrototypeCount(int prototypeCount) {
    this.prototypeCount = prototypeCount;
    this.sprototypeCount = prototypeCount;
  }

  public int getRecentlyStartedThreshold() {
    return recentlyStartedThreshold;
  }

  public void setRecentlyStartedThreshold(int recentlyStartedThreshold) {
    this.recentlyStartedThreshold = recentlyStartedThreshold;
    this.srecentlyStartedThreshold = recentlyStartedThreshold;
  }

  public int getSimultaneousBuildThrottle() {
    return simultaneousBuildThrottle;
  }

  public void setSimultaneousBuildThrottle(int simultaneousBuildThrottle) {
    this.simultaneousBuildThrottle = simultaneousBuildThrottle;
    this.ssimultaneousBuildThrottle = simultaneousBuildThrottle;
  }

  public String getStatistics() {
    return statistics;
  }

  public void setStatistics(String statistics) {
    this.statistics = statistics;
    this.sstatistics = statistics;
  }

  public String getStatisticsLogLevel() {
    return statisticsLogLevel;
  }

  public void setStatisticsLogLevel(String statisticsLogLevel) {
    this.statisticsLogLevel = statisticsLogLevel;
    this.sstatisticsLogLevel = statisticsLogLevel;
  }

  public boolean isTrace() {
    return trace;
  }

  public void setTrace(boolean trace) {
    this.trace = trace;
    this.strace = trace;
  }

  public String getDriverUrl() {
    return driverUrl;
  }

  public void setDriverUrl(String driverUrl) {
    this.driverUrl = driverUrl;
    this.sdriverUrl = driverUrl;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
    this.suser = user;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
    this.sverbose = verbose;
  }

  public boolean isJmx() {
    return jmx;
  }

  public void setJmx(boolean jmx) {
    this.jmx = jmx;
    this.sjmx = jmx;
  }

  public String getJmxAgentId() {
    return jmxAgentId;
  }

  public void setJmxAgentId(String jmxAgentId) {
    this.jmxAgentId = jmxAgentId;
    this.sjmxAgentId = jmxAgentId;
  }

  public boolean isTestBeforeUse() {
    return testBeforeUse;
  }

  public void setTestBeforeUse(boolean testBeforeUse) {
    this.testBeforeUse = testBeforeUse;
    this.stestBeforeUse = testBeforeUse;
  }

  public boolean isTestAfterUse() {
    return testAfterUse;
  }

  public void setTestAfterUse(boolean testAfterUse) {
    this.testAfterUse = testAfterUse;
    this.stestAfterUse = testAfterUse;
  }

  public String getFatalSqlExceptionsAsString() {
    return fatalSqlExceptionsAsString;
  }

  public void setFatalSqlExceptionsAsString(String fatalSqlExceptionsAsString) {
    this.fatalSqlExceptionsAsString = fatalSqlExceptionsAsString;
    this.sfatalSqlExceptionsAsString = fatalSqlExceptionsAsString;
  }

  public int getSloginTimeout() {
    return sloginTimeout;
  }

  public void setSloginTimeout(int sloginTimeout) {
    this.sloginTimeout = sloginTimeout;
  }

  public String getSalias() {
    return salias;
  }

  public void setSalias(String salias) {
    this.salias = salias;
  }

  public String getSdriver() {
    return sdriver;
  }

  public void setSdriver(String sdriver) {
    this.sdriver = sdriver;
  }

  public String getSfatalSqlExceptionWrapperClass() {
    return sfatalSqlExceptionWrapperClass;
  }

  public void setSfatalSqlExceptionWrapperClass(String sfatalSqlExceptionWrapperClass) {
    this.sfatalSqlExceptionWrapperClass = sfatalSqlExceptionWrapperClass;
  }

  public int getShouseKeepingSleepTime() {
    return shouseKeepingSleepTime;
  }

  public void setShouseKeepingSleepTime(int shouseKeepingSleepTime) {
    this.shouseKeepingSleepTime = shouseKeepingSleepTime;
  }

  public String getShouseKeepingTestSql() {
    return shouseKeepingTestSql;
  }

  public void setShouseKeepingTestSql(String shouseKeepingTestSql) {
    this.shouseKeepingTestSql = shouseKeepingTestSql;
  }

  public long getSmaximumActiveTime() {
    return smaximumActiveTime;
  }

  public void setSmaximumActiveTime(long smaximumActiveTime) {
    this.smaximumActiveTime = smaximumActiveTime;
  }

  public int getSmaximumConnectionCount() {
    return smaximumConnectionCount;
  }

  public void setSmaximumConnectionCount(int smaximumConnectionCount) {
    this.smaximumConnectionCount = smaximumConnectionCount;
  }

  public int getSmaximumConnectionLifetime() {
    return smaximumConnectionLifetime;
  }

  public void setSmaximumConnectionLifetime(int smaximumConnectionLifetime) {
    this.smaximumConnectionLifetime = smaximumConnectionLifetime;
  }

  public int getSminimumConnectionCount() {
    return sminimumConnectionCount;
  }

  public void setSminimumConnectionCount(int sminimumConnectionCount) {
    this.sminimumConnectionCount = sminimumConnectionCount;
  }

  public int getSoverloadWithoutRefusalLifetime() {
    return soverloadWithoutRefusalLifetime;
  }

  public void setSoverloadWithoutRefusalLifetime(int soverloadWithoutRefusalLifetime) {
    this.soverloadWithoutRefusalLifetime = soverloadWithoutRefusalLifetime;
  }

  public String getSpassword() {
    return spassword;
  }

  public void setSpassword(String spassword) {
    this.spassword = spassword;
  }

  public int getSprototypeCount() {
    return sprototypeCount;
  }

  public void setSprototypeCount(int sprototypeCount) {
    this.sprototypeCount = sprototypeCount;
  }

  public int getSrecentlyStartedThreshold() {
    return srecentlyStartedThreshold;
  }

  public void setSrecentlyStartedThreshold(int srecentlyStartedThreshold) {
    this.srecentlyStartedThreshold = srecentlyStartedThreshold;
  }

  public int getSsimultaneousBuildThrottle() {
    return ssimultaneousBuildThrottle;
  }

  public void setSsimultaneousBuildThrottle(int ssimultaneousBuildThrottle) {
    this.ssimultaneousBuildThrottle = ssimultaneousBuildThrottle;
  }

  public String getSstatistics() {
    return sstatistics;
  }

  public void setSstatistics(String sstatistics) {
    this.sstatistics = sstatistics;
  }

  public String getSstatisticsLogLevel() {
    return sstatisticsLogLevel;
  }

  public void setSstatisticsLogLevel(String sstatisticsLogLevel) {
    this.sstatisticsLogLevel = sstatisticsLogLevel;
  }

  public boolean isStrace() {
    return strace;
  }

  public void setStrace(boolean strace) {
    this.strace = strace;
  }

  public String getSdriverUrl() {
    return sdriverUrl;
  }

  public void setSdriverUrl(String sdriverUrl) {
    this.sdriverUrl = sdriverUrl;
  }

  public String getSuser() {
    return suser;
  }

  public void setSuser(String suser) {
    this.suser = suser;
  }

  public boolean isSverbose() {
    return sverbose;
  }

  public void setSverbose(boolean sverbose) {
    this.sverbose = sverbose;
  }

  public boolean isSjmx() {
    return sjmx;
  }

  public void setSjmx(boolean sjmx) {
    this.sjmx = sjmx;
  }

  public String getSjmxAgentId() {
    return sjmxAgentId;
  }

  public void setSjmxAgentId(String sjmxAgentId) {
    this.sjmxAgentId = sjmxAgentId;
  }

  public boolean isStestBeforeUse() {
    return stestBeforeUse;
  }

  public void setStestBeforeUse(boolean stestBeforeUse) {
    this.stestBeforeUse = stestBeforeUse;
  }

  public boolean isStestAfterUse() {
    return stestAfterUse;
  }

  public void setStestAfterUse(boolean stestAfterUse) {
    this.stestAfterUse = stestAfterUse;
  }

  public String getSfatalSqlExceptionsAsString() {
    return sfatalSqlExceptionsAsString;
  }

  public void setSfatalSqlExceptionsAsString(String sfatalSqlExceptionsAsString) {
    this.sfatalSqlExceptionsAsString = sfatalSqlExceptionsAsString;
  }

  public SummerDataSourceManager(){
    reset();
  }
  
  private void reset()
  {
    setMaximumConnectionLifetime(14400000);
    setPrototypeCount(0);
    setMinimumConnectionCount(0);
    setMaximumConnectionCount(15);
    setHouseKeepingSleepTime(30000);
    setSimultaneousBuildThrottle(10);
    setRecentlyStartedThreshold(60000);
    setOverloadWithoutRefusalLifetime(60000);
    setMaximumActiveTime(300000L);
    setVerbose(false);
    setTrace(false);
  }

  private ProxoolDataSource mDataSource;

  private ProxoolDataSource sDataSource;

  public ProxoolDataSource getmDataSource() throws SQLException {
    if(null == mDataSource)
      synchronized (this) {
        if(null == mDataSource)
          init();
      }
    return mDataSource;
  }

  public void setmDataSource(ProxoolDataSource mDataSource) {
    this.mDataSource = mDataSource;
  }

  public ProxoolDataSource getsDataSource() throws SQLException {
    if(null == sDataSource)
      synchronized (this) {
        if(null == sDataSource)
          init();
      }
    return sDataSource;
  }

  public void setsDataSource(ProxoolDataSource sDataSource) {
    this.sDataSource = sDataSource;
  }
  
  private synchronized void init() throws SQLException{
    initMDataSource();
    if(StringUtils.isBlank(salias)||salias.equals(alias)){
      this.sDataSource = this.mDataSource;
    }else{
      initSDataSource();
    }
  }
  
  private void initMDataSource() throws SQLException{
    if(StringUtils.isNotBlank(alias))this.mDataSource = new ProxoolDataSource(alias);
    if(StringUtils.isNotBlank(driver))this.mDataSource.setDriver(driver);
    if(StringUtils.isNotBlank(driverUrl))this.mDataSource.setDriverUrl(driverUrl);
    if(StringUtils.isNotBlank(fatalSqlExceptionsAsString))this.mDataSource.setFatalSqlExceptionsAsString(fatalSqlExceptionsAsString);
    if(StringUtils.isNotBlank(fatalSqlExceptionWrapperClass))this.mDataSource.setFatalSqlExceptionWrapperClass(fatalSqlExceptionWrapperClass);
    this.mDataSource.setHouseKeepingSleepTime(houseKeepingSleepTime);
    if(StringUtils.isNotBlank(houseKeepingTestSql))this.mDataSource.setHouseKeepingTestSql(houseKeepingTestSql);
    this.mDataSource.setJmx(jmx);
    if(StringUtils.isNotBlank(jmxAgentId))this.mDataSource.setJmxAgentId(jmxAgentId);
    this.mDataSource.setLoginTimeout(loginTimeout);
    this.mDataSource.setMaximumActiveTime(maximumActiveTime);
    this.mDataSource.setMaximumConnectionCount(maximumConnectionCount);
    this.mDataSource.setMaximumConnectionLifetime(maximumConnectionLifetime);
    this.mDataSource.setMinimumConnectionCount(minimumConnectionCount);
    this.mDataSource.setOverloadWithoutRefusalLifetime(overloadWithoutRefusalLifetime);
    if(StringUtils.isNotBlank(password))this.mDataSource.setPassword(password);
    this.mDataSource.setPrototypeCount(prototypeCount);
    this.mDataSource.setRecentlyStartedThreshold(recentlyStartedThreshold);
    this.mDataSource.setSimultaneousBuildThrottle(simultaneousBuildThrottle);
    if(StringUtils.isNotBlank(statistics))this.mDataSource.setStatistics(statistics);
    if(StringUtils.isNotBlank(statisticsLogLevel))this.mDataSource.setStatisticsLogLevel(statisticsLogLevel);
    this.mDataSource.setTestAfterUse(testAfterUse);
    this.mDataSource.setTestBeforeUse(testBeforeUse);
    this.mDataSource.setTrace(trace);
    if(StringUtils.isNotBlank(user))this.mDataSource.setUser(user);
    this.mDataSource.setVerbose(verbose);
  }
  
  private void initSDataSource() throws SQLException{
    if(StringUtils.isNotBlank(salias))this.sDataSource = new ProxoolDataSource(salias);
    if(StringUtils.isNotBlank(sdriver))this.sDataSource.setDriver(sdriver);
    if(StringUtils.isNotBlank(sdriverUrl))this.sDataSource.setDriverUrl(sdriverUrl);
    if(StringUtils.isNotBlank(sfatalSqlExceptionsAsString))this.sDataSource.setFatalSqlExceptionsAsString(sfatalSqlExceptionsAsString);
    if(StringUtils.isNotBlank(sfatalSqlExceptionWrapperClass))this.sDataSource.setFatalSqlExceptionWrapperClass(sfatalSqlExceptionWrapperClass);
    this.sDataSource.setHouseKeepingSleepTime(shouseKeepingSleepTime);
    if(StringUtils.isNotBlank(shouseKeepingTestSql))this.sDataSource.setHouseKeepingTestSql(shouseKeepingTestSql);
    this.sDataSource.setJmx(sjmx);
    if(StringUtils.isNotBlank(sjmxAgentId))this.sDataSource.setJmxAgentId(sjmxAgentId);
    this.sDataSource.setLoginTimeout(sloginTimeout);
    this.sDataSource.setMaximumActiveTime(smaximumActiveTime);
    this.sDataSource.setMaximumConnectionCount(smaximumConnectionCount);
    this.sDataSource.setMaximumConnectionLifetime(smaximumConnectionLifetime);
    this.sDataSource.setMinimumConnectionCount(sminimumConnectionCount);
    this.sDataSource.setOverloadWithoutRefusalLifetime(soverloadWithoutRefusalLifetime);
    if(StringUtils.isNotBlank(spassword))this.sDataSource.setPassword(spassword);
    this.sDataSource.setPrototypeCount(sprototypeCount);
    this.sDataSource.setRecentlyStartedThreshold(srecentlyStartedThreshold);
    this.sDataSource.setSimultaneousBuildThrottle(ssimultaneousBuildThrottle);
    if(StringUtils.isNotBlank(sstatistics))this.sDataSource.setStatistics(sstatistics);
    if(StringUtils.isNotBlank(sstatisticsLogLevel))this.sDataSource.setStatisticsLogLevel(sstatisticsLogLevel);
    this.sDataSource.setTestAfterUse(stestAfterUse);
    this.sDataSource.setTestBeforeUse(stestBeforeUse);
    this.sDataSource.setTrace(strace);
    if(StringUtils.isNotBlank(suser))this.sDataSource.setUser(suser);
    this.sDataSource.setVerbose(sverbose);
  }

}
