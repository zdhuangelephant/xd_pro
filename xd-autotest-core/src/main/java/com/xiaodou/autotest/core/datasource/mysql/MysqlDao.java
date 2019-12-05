package com.xiaodou.autotest.core.datasource.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.core.datasource.mysql.MysqlDao.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description Mysql操作类
 * @version 1.0
 */
public class MysqlDao {
  public static Map<String, String> get(String alias, String sql) {
    Map<String, String> obj = Maps.newHashMap();

    Connection conn = ConnectionFactory.getConnection(alias);
    if (null == conn) {
      throw new IllegalArgumentException(String.format("MysqlDao:未识别的数据源.[Alias:%s]", alias));
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        setEntity(obj, rs);
      }
      obj = formatEntiry(obj);
      rs.close();
      stmt.close();
    } catch (Exception e) {
      LoggerUtil.error(String.format("MysqlDao:数据库操作失败.[Alias:%s]", alias), e);
      return null;
    } finally {
      try {
        if (null != conn && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        LoggerUtil.error(String.format("MysqlDao:关闭数据库连接失败.[Alias:%s]", alias), e);
      }
    }
    return obj;
  }

  private static Map<String, String> formatEntiry(Map<String, String> obj) {
    Map<String, String> resultMap = Maps.newHashMap();
    for (String key : obj.keySet()) {
      String value = obj.get(key);
      if (StringUtils.isBlank(value)) {
        continue;
      }
      if (value.indexOf(ActionConstant.SEPERATOR_DATASOURCE_VALUE) <= 0) {
        resultMap.put(key, value);
      } else {
        resultMap.put(key, FastJsonUtil.toJson(Lists.newArrayList(value
            .split(ActionConstant.SEPERATOR_DATASOURCE_VALUE))));
      }
    }
    return resultMap;
  }

  protected static void setEntity(Map<String, String> obj, ResultSet rs) throws Exception {
    ResultSetMetaData rsmd = rs.getMetaData();
    int count = rsmd.getColumnCount();
    for (int i = 1; i <= count; i++) {
      String key = rsmd.getColumnLabel(i);
      String value = rs.getString(i);
      if (obj.containsKey(key)) {
        String innerValue = obj.get(key);
        StringBuffer buffer = new StringBuffer(200);
        if (StringUtils.isNotBlank(innerValue)) {
          buffer.append(innerValue).append(ActionConstant.SEPERATOR_DATASOURCE_VALUE);
        }
        buffer.append(value);
        obj.put(key, buffer.toString());
      } else {
        obj.put(key, value);
      }
    }
  }
}
