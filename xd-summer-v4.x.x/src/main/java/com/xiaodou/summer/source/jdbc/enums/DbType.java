package com.xiaodou.summer.source.jdbc.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.JdbcConstants;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.summer.source.jdbc.enums.DbType.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年6月7日
 * @description 数据库类型枚举
 * @version 1.0
 */
public enum DbType {

  /** Mysql */
  Mysql(JdbcConstants.MYSQL),
  /** MARIADB */
  MARIADB(JdbcConstants.MARIADB),
  /** DERBY */
  DERBY(JdbcConstants.DERBY),
  /** HSQL */
  HSQL(JdbcConstants.HSQL),
  /** DB2 */
  DB2(JdbcConstants.DB2),
  /** SYBASE */
  SYBASE(JdbcConstants.SYBASE),
  /** SQL_SERVER */
  SQL_SERVER(JdbcConstants.SQL_SERVER),
  /** SQL_SERVER */
  Sqlite(JdbcConstants.SQLITE) {
    @Override
    public ArrayList<String> getAllUrl(String url) {
      url = url.replaceAll("\\[", StringUtils.EMPTY).replaceAll("\\]", StringUtils.EMPTY);
      return super.getAllUrl(url);
    }
  },
  /** Oracle */
  Oracle(JdbcConstants.ORACLE),
  /** Postgresql */
  Postgresql(JdbcConstants.POSTGRESQL),
  /** HBASE */
  HBASE(JdbcConstants.HBASE),
  /** HIVE */
  HIVE(JdbcConstants.HIVE),
  /** H2 */
  H2(JdbcConstants.H2),
  /** DM */
  DM(JdbcConstants.DM),
  /** Common */
  Common(JdbcConstants.DB2);

  DbType(String type) {
    this.type = type;
  }

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ArrayList<String> getAllUrl(String url) {
    ArrayList<String> result = new ArrayList<String>();
    int beginIndex = url.indexOf("[");
    int endIndex = url.indexOf("]");
    if (-1 == beginIndex || -1 == endIndex) {
      url = url.replaceAll("\\[", StringUtils.EMPTY).replaceAll("\\]", StringUtils.EMPTY);
      result.add(url);
    } else {
      String urlPrefix = url.substring(0, beginIndex);
      String ipPorts = url.substring(beginIndex + 1, endIndex);
      String urlSuffix = url.substring(endIndex + 1);
      LoggerUtil.debug("urlPrefix--->" + urlPrefix);
      LoggerUtil.debug("ipPorts--->" + ipPorts);
      LoggerUtil.debug("urlSuffix--->" + urlSuffix);
      String[] array = ipPorts.split(",");
      for (String ipPort : array) {
        result.add(urlPrefix + ipPort + urlSuffix);
      }
    }
    // 如果用户只写了1个ip:port,也就是用户只填了一个master,而没有填写slave地址
    // 修复此bug
    if (1 == result.size()) {
      String address = result.get(0);
      result.add(address);
    }
    return result;
  }

  private final static Map<String, DbType> _selectMap = new HashMap<String, DbType>() {
    private static final long serialVersionUID = 3105465691706879287L;

    private HashMap<String, DbType> initThis() {
      for (DbType dbType : DbType.values()) {
        if (null == dbType) continue;
        this.put(dbType.getType(), dbType);
      }
      return this;
    }
  }.initThis();

  public static DbType getDbType(String key) {
    if (StringUtils.isNotBlank(key)) {
      return _selectMap.get(key);
    }
    return Common;
  }

}
