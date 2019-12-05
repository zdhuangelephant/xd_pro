package com.xiaodou.summer.source.jdbc.helper;

public final class JdbcUrlHolder {
  
  private static final ThreadLocal<String> holder = new ThreadLocal<String>();
  
  private JdbcUrlHolder() {}

  public static void putJdbcUrl(String jdbcUrl) {
    holder.set(jdbcUrl);
  }

  public static String getJdbcUrl() {
    return holder.get();
  }

  public static void clearJdbcUrl() {
    holder.remove();
  }
}
