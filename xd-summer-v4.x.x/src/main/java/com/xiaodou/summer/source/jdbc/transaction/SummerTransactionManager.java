package com.xiaodou.summer.source.jdbc.transaction;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import com.google.common.collect.Maps;
import com.xiaodou.summer.source.jdbc.datasource.DynamicDataSource;
import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;

public class SummerTransactionManager {

  public SummerTransactionManager(SummerDataSourceManager dataSourceManager) throws SQLException {
    defaultTransactionFactory = new SpringManagedTransactionFactory();

    othertransactionFactory = Maps.newConcurrentMap();
    
    for (Map.Entry<String, DynamicDataSource> entry : dataSourceManager.getOtherDataSources().entrySet()) {
        othertransactionFactory.put(entry.getKey(), new SpringManagedTransactionFactory());
    }

  }

  private TransactionFactory defaultTransactionFactory;

  private Map<String, TransactionFactory> othertransactionFactory;


  public TransactionFactory getDefaultTransactionFactory() {
    return defaultTransactionFactory;
  }

  public void setDefaultTransactionFactory(TransactionFactory defaultTransactionFactory) {
    this.defaultTransactionFactory = defaultTransactionFactory;
  }

  public Map<String, TransactionFactory> getOthertransactionFactory() {
    return othertransactionFactory;
  }

  public void setOthertransactionFactory(Map<String, TransactionFactory> othertransactionFactory) {
    this.othertransactionFactory = othertransactionFactory;
  }

  public boolean isInit() {
    return null != defaultTransactionFactory && null != othertransactionFactory;
  }

  public synchronized void init(SummerDataSourceManager dataSourceManager) throws SQLException {
    new SummerTransactionManager(dataSourceManager);
  }

}
