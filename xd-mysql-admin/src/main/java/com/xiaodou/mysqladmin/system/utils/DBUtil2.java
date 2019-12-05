package com.xiaodou.mysqladmin.system.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;

public class DBUtil2 {

  protected static final String SQL_END_DELIMITER = ";";
  protected static final String SQL_PART_DELMETER = ",";
  protected static final String SELECT = "select";
  protected static final String FROM = "from";
  protected static final String UNDESIGN_COLOUMN = "*";
  protected static final String GROUP_COUNT = "count(";
  protected static final String GROUP_SUM = "sum(";
  protected static final String GROUP_MAX = "max(";
  protected static final String GROUP_MIN = "min(";
  protected static final String GROUP_AVG = "avg(";

  private String driver;
  private String url;
  private String userName;
  private String password;

  public DBUtil2(String driverUrl, String ip, String port, String dbName, String userName,
      String password) {
    this.driver = driverUrl;
    this.url =
        "jdbc:mysql://"
            + ip
            + ":"
            + port
            + "/"
            + dbName
            + "?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf8&tinyInt1isBit=false";
    this.userName = userName;
    this.password = password;
  }

  public final synchronized Connection getConnection() {
    try {
      Class.forName(driver);
      Connection conn = DriverManager.getConnection(url, userName, password);

      return conn;
    } catch (Exception e) {}
    return null;
  }

  public boolean testConnection() {
    try {
      Class.forName(driver);
      Connection conn = DriverManager.getConnection(url, userName, password);

      return conn != null;
    } catch (Exception e) {}

    return false;
  }

  public int setupdateData(String sql) throws Exception {
    sql = sql.replaceAll("\\s{1,}", " ");
    Connection conn = getConnection();
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      return stmt.executeUpdate(sql);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new Exception(e.getMessage());
      }
    }
  }

  public List<Map<String, Object>> queryForList(String sql) throws Exception {
    sql = sql.replaceAll("\\s{1,}", " ");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    int maxSize = -1;
    String[] fields = (String[]) null;
    List<String> times = Lists.newArrayList();
    List<Map<String, Object>> rows = Lists.newArrayList();
    Map<String, Object> row = null;

    conn = getConnection();
    pstmt = conn.prepareStatement(sql);
    rs = pstmt.executeQuery();
    rsmd = rs.getMetaData();

    maxSize = rsmd.getColumnCount();
    fields = new String[maxSize];
    for (int i = 0; i < maxSize; ++i) {
      fields[i] = rsmd.getColumnLabel(i + 1);
      if ("java.sql.Timestamp".equals(rsmd.getColumnClassName(i + 1))) {
        times.add(fields[i]);
      }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    while (rs.next()) {
      row = Maps.newHashMap();
      for (int i = 0; i < maxSize; ++i) {
        Object value =
            (times.contains(fields[i])) ? rs.getTimestamp(fields[i]) : rs.getObject(fields[i]);
        if ((times.contains(fields[i])) && (value != null)) {
          value = sdf.format(value);
        }
        row.put(fields[i], value);
      }
      rows.add(row);
    }
    try {
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      throw new Exception(e.getMessage());
    }

    return rows;
  }

  public List<Map<String, Object>> queryForListWithType(String sql) {
    sql = sql.replaceAll("\\s{1,}", " ");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Map<String, Object>> rows2 = Lists.newArrayList();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      ResultSetMetaData rsme = rs.getMetaData();
      int columnCount = rsme.getColumnCount();
      rs.next();
      for (int i = 1; i < columnCount + 1; ++i) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("column_name", rsme.getColumnName(i));
        map.put("column_value", rs.getObject(rsme.getColumnName(i)));
        map.put("data_type", rsme.getColumnTypeName(i));
        map.put("precision", Integer.valueOf(rsme.getPrecision(i)));
        map.put("isAutoIncrement", Boolean.valueOf(rsme.isAutoIncrement(i)));
        map.put("is_nullable", Integer.valueOf(rsme.isNullable(i)));
        map.put("isReadOnly", Boolean.valueOf(rsme.isReadOnly(i)));
        rows2.add(map);
      }
    } catch (Exception e) {
      System.out.println("queryForListWithType  " + e.getMessage());
    } finally {
      try {
        rs.close();
        pstmt.close();
        conn.close();
      } catch (SQLException localSQLException1) {}
    }
    return rows2;
  }

  public List<Map<String, Object>> queryForColumnOnly(String sql) {
    sql = sql.replaceAll("\\s{1,}", " ");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Map<String, Object>> rows2 = Lists.newArrayList();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      ResultSetMetaData rsme = rs.getMetaData();
      int columnCount = rsme.getColumnCount();
      for (int i = 1; i < columnCount + 1; ++i) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("column_name", rsme.getColumnName(i));
        map.put("data_type", rsme.getColumnTypeName(i));
        map.put("precision", Integer.valueOf(rsme.getPrecision(i)));
        map.put("isAutoIncrement", Boolean.valueOf(rsme.isAutoIncrement(i)));
        map.put("is_nullable", Integer.valueOf(rsme.isNullable(i)));
        map.put("isReadOnly", Boolean.valueOf(rsme.isReadOnly(i)));
        rows2.add(map);
      }
    } catch (Exception e) {
      System.out.println("queryForColumnOnly  " + e.getMessage());
    } finally {
      try {
        rs.close();
        pstmt.close();
        conn.close();
      } catch (SQLException localSQLException1) {}
    }
    return rows2;
  }

  public List<Map<String, Object>> executeSqlForColumns(String sql) throws Exception {
    sql = sql.replaceAll("\\s{1,}", " ");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    int maxSize = -1;
    List<Map<String, Object>> rows = Lists.newArrayList();
    conn = getConnection();
    pstmt = conn.prepareStatement(sql);
    rs = pstmt.executeQuery();
    rsmd = rs.getMetaData();
    maxSize = rsmd.getColumnCount();
    for (int i = 0; i < maxSize; ++i) {
      Map<String, Object> map = Maps.newHashMap();
      map.put("column_name", rsmd.getColumnLabel(i + 1));
      rows.add(map);
    }
    rs.close();
    pstmt.close();
    conn.close();
    return rows;
  }

  public int executeQueryForCount(String sql) {
    sql = sql.replaceAll("\\s{1,}", " ");
    int rowCount = 0;
    Connection conn = getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Long count = (Long) rs.getObject("count(*)");
        rowCount = count.intValue();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException localSQLException1) {}
    }
    return rowCount;
  }

  public int executeQueryForCount2(String sql) {
    sql = sql.replaceAll("\\s{1,}", " ");
    int rowCount = 0;
    Connection conn = getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.createStatement();
      SQL countSQL = getCountString(sql);
      rs = stmt.executeQuery(countSQL.getSql());
      if (countSQL.isOriginSQl()) {
        rs.last();
        rowCount = rs.getRow();
      } else {
        if (rs.next()) {
          rowCount = rs.getInt(1);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException localSQLException1) {}
    }
    return rowCount;
  }

  private SQL getCountString(String sql) {
    String resultSql = sql.toLowerCase();
    String firstField = StringUtils.EMPTY;
    if (resultSql.indexOf(GROUP_COUNT) > 0 || resultSql.indexOf(GROUP_AVG) > 0
        || resultSql.indexOf(GROUP_MAX) > 0 || resultSql.indexOf(GROUP_MIN) > 0
        || resultSql.indexOf(GROUP_SUM) > 0) {
      return new SQL(resultSql);
    }
    if (resultSql.indexOf(UNDESIGN_COLOUMN) > 0) {
      firstField =
          resultSql.substring(resultSql.indexOf(SELECT) + SELECT.length(),
              resultSql.indexOf(UNDESIGN_COLOUMN) + UNDESIGN_COLOUMN.length()).trim();
      firstField = " count(" + firstField + ") ";
      resultSql =
          SELECT
              + firstField
              + resultSql
                  .substring(resultSql.indexOf(UNDESIGN_COLOUMN) + UNDESIGN_COLOUMN.length());
    } else if (resultSql.indexOf(SQL_PART_DELMETER) > 0) {
      firstField =
          resultSql.substring(resultSql.indexOf(SELECT) + SELECT.length(),
              resultSql.indexOf(SQL_PART_DELMETER)).trim();
      firstField = " count(" + firstField + ") ";
      resultSql = SELECT + firstField + resultSql.substring(resultSql.indexOf(SQL_PART_DELMETER));
    } else {
      firstField =
          resultSql.substring(resultSql.indexOf(SELECT) + SELECT.length(), resultSql.indexOf(FROM))
              .trim();
      firstField = " count(" + firstField + ") ";
      resultSql = SELECT + firstField + resultSql.substring(resultSql.indexOf(FROM));
    }
    if (resultSql.toLowerCase().lastIndexOf("order by") != -1) {
      resultSql = resultSql.substring(0, resultSql.toLowerCase().lastIndexOf("order by"));
    }
    SQL resultSQL = new SQL(resultSql);
    resultSQL.setOriginSQl(false);
    return resultSQL;
  }

  // public boolean executeQuery(String sql) {
  // boolean bl = false;
  // Connection conn = getConnection();
  // Statement stmt = null;
  // ResultSet rs = null;
  // try {
  // stmt = conn.createStatement();
  // rs = stmt.executeQuery(sql);
  // if (!rs.next()) break label40;
  // label40: bl = true;
  // } catch (Exception localException) {} finally {
  // try {
  // rs.close();
  // stmt.close();
  // conn.close();
  // } catch (SQLException localSQLException1) {}
  // }
  // return bl;
  // }

  public boolean testConn() {
    boolean bl = false;
    Connection conn = getConnection();
    if (conn != null) bl = true;
    try {
      conn.close();
    } catch (Exception localException) {}
    return bl;
  }

  public String getPrimaryKeys(String databaseName, String tableName) {
    Connection conn = null;
    try {
      conn = getConnection();
      DatabaseMetaData metadata = conn.getMetaData();
      ResultSet rs2 = metadata.getPrimaryKeys(databaseName, null, tableName);
      if (!rs2.next()) return "";
      System.out.println("主键名称: " + rs2.getString(4));
      return rs2.getString(4);
    } catch (Exception e) {
      System.out.println("queryForColumnOnly  " + e.getMessage());
    } finally {
      try {
        conn.close();
      } catch (SQLException localSQLException2) {}
    }
    return "";
  }

  public List<String> getPrimaryKeyss(String databaseName, String tableName) {
    Connection conn = null;
    List<String> rows2 = Lists.newArrayList();
    try {
      conn = getConnection();
      DatabaseMetaData metadata = conn.getMetaData();
      ResultSet rs2 = metadata.getPrimaryKeys(databaseName, null, tableName);
      while (rs2.next()) {
        System.out.println("主键名称2: " + rs2.getString(4));
        rows2.add(rs2.getString(4));
      }
    } catch (Exception e) {
      System.out.println("queryForColumnOnly  " + e.getMessage());
    } finally {
      try {
        conn.close();
      } catch (SQLException localSQLException1) {}
    }
    return rows2;
  }

  /**
   * @name @see com.xiaodou.mysqladmin.system.utils.DBUtil2.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年4月23日
   * @description SQL包装类
   * @version 1.0
   */
  @Data
  public static class SQL {
    public SQL(String sql) {
      this.sql = sql;
    }

    private String sql;
    private boolean originSQl = true;
  }
}
