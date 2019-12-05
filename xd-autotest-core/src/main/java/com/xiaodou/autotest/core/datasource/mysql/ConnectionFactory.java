package com.xiaodou.autotest.core.datasource.mysql;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import com.xiaodou.autotest.core.datasource.DataSourceConstants;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.datasource.mysql.ConnectionFactory.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description 连接工厂
 * @version 1.0
 */
public class ConnectionFactory {

  private ConnectionFactory() {}

  private static Map<String, ProxoolDataSource> dsMap = Maps.newHashMap();

  /**
   * @param config
   */
  public static void registMysqlDataSource(DataSourceConfig config) {
    Assert.notNull(config, "ConnectionFactory:数据源配置不能为空.");
    if (StringUtils.isBlank(config.getAlias())) {
      String alias =
          String.format(DataSourceConstants.DEFAULT_MYSQL_ALIAS_TMP,
              HashCode.fromInt(dsMap.size() * 31 + 13).asInt());
      config.setAlias(alias);
    }
    Assert.isTrue(!dsMap.containsKey(config.getAlias()),
        String.format("ConnectionFactory:相同数据库别名不能重复注册.[Alias:%s]", config.getAlias()));
    ProxoolDataSource ds = new org.logicalcobwebs.proxool.ProxoolDataSource();
    // 设置JDBC的Driver类
    ds.setDriver("com.mysql.jdbc.Driver");
    // 设置连接池的最大连接数
    ds.setMaximumConnectionCount(100);
    // 设置连接池的最小连接数
    ds.setMinimumConnectionCount(10);
    // 设置数据库地址
    ds.setDriverUrl(config.getDriverUrl());
    // 设置用户名
    ds.setUser(config.getUserName());
    // 设置密码
    ds.setPassword(config.getPassword());
    dsMap.put(config.getAlias(), ds);
  }

  public static Connection getConnection(String alias) {
    try {
      ProxoolDataSource proxoolDataSource = dsMap.get(alias);
      if (null == proxoolDataSource) {
        throw new IllegalArgumentException(String.format("ConnectionFactory:未识别的数据源.[Alias:%s]",
            alias));
      }
      return proxoolDataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(String.format("ConnectionFactory:打开数据库连接失败.[Alias:%s]", alias), e);
    }
  }

  public static DataSource getDataSource(String alias) {
    return dsMap.get(alias);
  }
}
