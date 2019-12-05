package com.xiaodou.summer.dao.pagination.dialect;

import com.xiaodou.summer.dao.pagination.dialect.Dialect;

/**
 * 
 * @Title:MySQLDialect.java
 * 
 * @Description:MySql的Dialect实现类
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 10:09:59 AM
 * @version V1.0
 */

public class MySQLDialect implements Dialect {
  protected static final String SQL_END_DELIMITER = ";";
  protected static final String SQL_PART_DELMETER = ",";
  protected static final String SELECT = "select";

  public String getLimitString(String sql, boolean hasOffset) {
    return new StringBuffer(sql.length() + 20).append(trim(sql))
        .append(hasOffset ? " limit ?,?" : " limit ?").append(SQL_END_DELIMITER).toString();
  }

  public String getLimitString(String sql, int offset, int limit) {
    sql = trim(sql);
    StringBuffer sb = new StringBuffer(sql.length() + 20);
    sb.append(sql);
    if (offset > 0) {
      sb.append(" limit ").append(offset).append(',').append(limit).append(SQL_END_DELIMITER);
    } else {
      sb.append(" limit ").append(limit).append(SQL_END_DELIMITER);
    }
    return sb.toString();
  }

  public boolean supportsLimit() {
    return true;
  }

  public String getCountString(String sql) {
    String resultSql = sql;
    String firstField =
        resultSql.substring(resultSql.toLowerCase().indexOf(SELECT) + SELECT.length(),
            resultSql.indexOf(SQL_PART_DELMETER)).trim();
    firstField = " count(" + firstField + ") ";
    resultSql = SELECT + firstField + resultSql.substring(resultSql.indexOf(SQL_PART_DELMETER));
    if (resultSql.toLowerCase().lastIndexOf("order by") != -1) {
      resultSql = resultSql.substring(0, resultSql.toLowerCase().lastIndexOf("order by"));
    }
    return resultSql;
  }

  private String trim(String sql) {
    sql = sql.trim();
    if (sql.endsWith(SQL_END_DELIMITER)) {
      sql = sql.substring(0, sql.length() - SQL_END_DELIMITER.length());
    }
    return sql;
  }

  @Override
  public boolean supportsLimitOffset() {
    return true;
  }

}
