package com.xiaodou.summer.source.jdbc.transaction;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;

public class SummerDataSourceTransactionManager extends DataSourceTransactionManager {

  private SummerDataSourceManager dataSourceManager;

  public SummerDataSourceManager getDataSourceManager() {
    return dataSourceManager;
  }

  public void setDataSourceManager(SummerDataSourceManager dataSourceManager) {
    this.dataSourceManager = dataSourceManager;
    this.setDataSource();
  }

  private void setDataSource() {
    try {
      super.setDataSource(dataSourceManager.getmDataSource());
    } catch (SQLException e) {
      LoggerUtil.error("SummerDataSourceManager can't get MDataSource.", e);
    }
  }

  /** 唯一标识ID */
  private static final long serialVersionUID = -8932818014184151115L;

  @Override
  public void setDataSource(DataSource dataSource) {
    this.setDataSource();
  }

  public SummerDataSourceTransactionManager() {
    super.setNestedTransactionAllowed(true);
  }

  public SummerDataSourceTransactionManager(SummerDataSourceManager dataSourceManager)
      throws SQLException {
    this();
    super.setDataSource(dataSourceManager.getmDataSource());
    super.afterPropertiesSet();
  }

}
