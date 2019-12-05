package com.xiaodou.summer.source.jdbc.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.enums.DynamicDataSourceGlobal;
import com.xiaodou.summer.source.jdbc.helper.DynamicDataSourceHolder;
import com.xiaodou.summer.source.jdbc.helper.JdbcUrlHolder;
import com.xiaodou.summer.source.jdbc.helper.UrlHelper;

/**
 * @name DynamicDataSource CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年1月25日
 * @description 动态数据源(使用)
 * @version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Log logger = LogFactory.getLog(DynamicDataSource.class);
    /** masterDataSource 主数据源，用于读操作 */
    private DataSource masterDataSource;
    /** slaveDataSources 从数据源，用于读操作 */
    private DataSource[] slaveDataSources;

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public DataSource[] getSlaveDataSources() {
        return slaveDataSources;
    }

    public void setSlaveDataSources(DataSource[] slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }


    public DynamicDataSource() {}

    public DynamicDataSource(DataSourceConfig dataSourceConfig) {
        try {
            Map<String, Object> map = Maps.newConcurrentMap();
            CommUtil.transferFromVO2Map(map, dataSourceConfig);
            List<String> urls = UrlHelper.handle(dataSourceConfig.getUrls());
            int index = 0;
            for (String url : urls) {
                // dataSourceConfig.setUrl(url);
                map.put("url", url);
                if (0 == index) {
                    // dataSourceConfig.setDefaultReadOnly(Boolean.FALSE.toString());
                    // masterDataSource = this.initDruidDataSource(dataSourceConfig);
                    map.put("defaultReadOnly", "false");
                    masterDataSource = DruidDataSourceFactory.createDataSource(map);
                } else {
                    slaveDataSources = new DataSource[urls.size() - 1];
                    // dataSourceConfig.setDefaultReadOnly(Boolean.TRUE.toString());
                    // slaveDataSources[index.get() - 1] =
                    // this.initDruidDataSource(dataSourceConfig);
                    map.put("defaultReadOnly", "true");
                    slaveDataSources[index - 1] = DruidDataSourceFactory.createDataSource(map);
                }
                index++;
            }
            this.afterPropertiesSet();
        } catch (Exception e) {
            LoggerUtil.error("druid configuration initialization filter", e);
        }
    }

    public DruidDataSource initDruidDataSource(DataSourceConfig dataSourceConfig) throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDbType(dataSourceConfig.getDbType());
        datasource.setDriverClassName(dataSourceConfig.getDriverClassName());
        datasource.setUrl(dataSourceConfig.getUrl());
        datasource.setUsername(dataSourceConfig.getUsername());
        datasource.setPassword(dataSourceConfig.getPassword());
        datasource.setInitialSize(Integer.valueOf(dataSourceConfig.getInitialSize()));
        datasource.setMinIdle(Integer.valueOf(dataSourceConfig.getMaxIdle()));
        datasource.setMaxActive(Integer.valueOf(dataSourceConfig.getMaxActive()));
        datasource.setMaxWait(Long.valueOf(dataSourceConfig.getMaxWait()));
        datasource.setTimeBetweenEvictionRunsMillis(Long.valueOf(dataSourceConfig.getTimeBetweenEvictionRunsMillis()));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(dataSourceConfig.getMinEvictableIdleTimeMillis()));
        datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
        datasource.setTestWhileIdle(Boolean.valueOf(dataSourceConfig.getTestWhileIdle()));
        datasource.setTestOnBorrow(Boolean.valueOf(dataSourceConfig.getTestOnBorrow()));
        datasource.setTestOnReturn(Boolean.valueOf(dataSourceConfig.getTestOnReturn()));
        datasource.setPoolPreparedStatements(Boolean.valueOf(dataSourceConfig.getPoolPreparedStatements()));
        datasource.setFilters(dataSourceConfig.getFilters());
        datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());
        datasource.setUseGlobalDataSourceStat(Boolean.valueOf(dataSourceConfig.getUseGlobalDataSourceStat()));
        if (Boolean.TRUE.toString().equals(dataSourceConfig.getInit())) {
            datasource.init();
        }
        return datasource;
    }

    private Map<Object, Object> targetDataSources = new HashMap<>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#afterPropertiesSet()
     * 把所有数据库都放在路由中
     */
    public void afterPropertiesSet() {
        if (this.masterDataSource == null) {
            throw new IllegalArgumentException("Property 'masterDataSource' is required");
        }
        setDefaultTargetDataSource(masterDataSource);
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), masterDataSource);
        if (slaveDataSources != null) {
            for (int i = 0; i < slaveDataSources.length; i++) {
                targetDataSources.put(DynamicDataSourceGlobal.READ.name() + i, slaveDataSources[i]);
            }
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    private AtomicInteger count = new AtomicInteger(0);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
     * determineCurrentLookupKey ()
     * 这是AbstractRoutingDataSource类中的一个抽象方法，而它的返回值是你所要的数据源dataSource的key值，有了这个key值，<br>
     * targetDataSources就从中取对应的DataSource,如果找不到，就用配置默认的数据源。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getHolder().getDataSourceType();
        if (dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE || slaveDataSources == null) {
            return DynamicDataSourceGlobal.WRITE.name();
        }
        DynamicDataSourceHolder.getHolder().clear();
        // 读库， 简单的负载均衡（轮询）。
        int number = count.incrementAndGet();
        int lookupKey = number % slaveDataSources.length;
        DruidDataSource currentDataSource =
                (DruidDataSource) targetDataSources.get(DynamicDataSourceGlobal.READ.name() + lookupKey);
        logger.info(DynamicDataSourceGlobal.READ.name() + lookupKey + " - url:" + currentDataSource.getUrl());
        JdbcUrlHolder.putJdbcUrl(currentDataSource.getUrl());
        return DynamicDataSourceGlobal.READ.name() + lookupKey;
    }

}
