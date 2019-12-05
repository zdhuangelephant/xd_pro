package com.xiaodou.summer.dao.jdbc.seq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;

public class SeqDao {

  SummerDataSourceManager seqDataSource;

  public SummerDataSourceManager getSeqDataSource() {
    return seqDataSource;
  }

  public void setSeqDataSource(SummerDataSourceManager seqDataSource) {
    this.seqDataSource = seqDataSource;
  }

  public int querySeqId(String tableNmae) {

    int seq = 3000;

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      con = seqDataSource.getDefaultDataSource().getConnection();
      stmt = con.createStatement();
      stmt.executeUpdate("REPLACE INTO " + tableNmae + "(stub) VALUES('a')");
      rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
      while (rs.next()) {
        seq = rs.getInt(1);
      }

    } catch (Exception e) {
      LoggerUtil.error("getSeqId failure", e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return seq;
  }

}
