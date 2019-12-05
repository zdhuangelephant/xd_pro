package com.xiaodou.summer.source.jdbc.transaction;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.source.jdbc.datasource.SummerDataSourceManager;
import com.xiaodou.summer.source.jdbc.helper.DynamicDataSourceHolder;
import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;

public class SummerDataSourceTransactionManager extends DataSourceTransactionManager {

    public SummerDataSourceTransactionManager() {
        super.setNestedTransactionAllowed(true);
    }

    public SummerDataSourceTransactionManager(SummerDataSourceManager dataSourceManager) throws SQLException {
        this();
        this.dataSourceManager = dataSourceManager;
        this.setDataSource();
        super.afterPropertiesSet();
    }

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
            DataSource dataSource = dataSourceManager.getDefaultDataSource();
            if (null != RealSqlSessionKeyHolder.getHolder().getRealSqlSessionKey()) {
                dataSource = dataSourceManager.getOtherDataSources()
                        .get(RealSqlSessionKeyHolder.getHolder().getRealSqlSessionKey());
            }
            super.setDataSource(dataSource);
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

    /**
     * 只读事务到读库，读写事务到写库
     * 
     * @param transaction
     * @param definition
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {

        // 设置数据源
        // boolean readOnly = definition.isReadOnly();
        // if (readOnly) {
        // DynamicDataSourceHolder.getHolder().setDataSourceType(DynamicDataSourceGlobal.READ);
        // } else {
        // DynamicDataSourceHolder.getHolder().setDataSourceType(DynamicDataSourceGlobal.WRITE);
        // }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     * 
     * @param transaction
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DynamicDataSourceHolder.getHolder().clear();
    }

}
