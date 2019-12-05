package com.xiaodou.summer.source.jdbc.transaction;

import java.sql.SQLException;

import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;

public class SummerTransactionManager {
  
  public SummerTransactionManager(SummerDataSourceManager dataSourceManager) throws SQLException{
    mtransactionFactory = new SpringManagedTransactionFactory(dataSourceManager.getmDataSource());
    stransactionFactory = new SpringManagedTransactionFactory(dataSourceManager.getsDataSource());
  }

  private TransactionFactory mtransactionFactory;

  private TransactionFactory stransactionFactory;

  public TransactionFactory getMtransactionFactory() {
    return mtransactionFactory;
  }

  public void setMtransactionFactory(TransactionFactory mtransactionFactory) {
    this.mtransactionFactory = mtransactionFactory;
  }

  public TransactionFactory getStransactionFactory() {
    return stransactionFactory;
  }

  public void setStransactionFactory(TransactionFactory stransactionFactory) {
    this.stransactionFactory = stransactionFactory;
  }

  public boolean isInit() {
    return null != mtransactionFactory && null != stransactionFactory;
  }

  public synchronized void init(SummerDataSourceManager dataSourceManager) throws SQLException {
    mtransactionFactory = new SpringManagedTransactionFactory(dataSourceManager.getmDataSource());
    stransactionFactory = new SpringManagedTransactionFactory(dataSourceManager.getsDataSource());
  }

}
