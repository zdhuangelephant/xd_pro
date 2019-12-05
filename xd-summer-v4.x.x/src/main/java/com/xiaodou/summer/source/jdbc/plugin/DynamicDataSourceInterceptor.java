package com.xiaodou.summer.source.jdbc.plugin;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.enums.DynamicDataSourceGlobal;
import com.xiaodou.summer.source.jdbc.helper.DynamicDataSourceHolder;

/**
 * @name @see com.xiaodou.summer.source.jdbc.plugin.DynamicDataSourceInterceptor.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年1月25日
 * @description Spring+MyBatis实现数据库读写分离
 * @version 1.0
 */
@Intercepts(value = {
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

  private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

  private static final Map<String, DynamicDataSourceGlobal> cacheMap = new ConcurrentHashMap<>();

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
   * 确定是走写库还是读库
   */
  @Override
  public Object intercept(Invocation invocation) throws Throwable {

    boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
    if (!synchronizationActive) {
      Object[] objects = invocation.getArgs();
      MappedStatement ms = (MappedStatement) objects[0];

      DynamicDataSourceGlobal dynamicDataSourceGlobal = null;

      if ((dynamicDataSourceGlobal = cacheMap.get(ms.getId())) == null) {
        // 读方法
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
          // !selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
          if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
            dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
          } else {
            BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
            String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
            if (sql.matches(REGEX)) {
              dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
            } else {
              dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
            }
          }
        } else {
          dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
        }
        cacheMap.put(ms.getId(), dynamicDataSourceGlobal);
      }
      DynamicDataSourceHolder.getHolder().setDataSourceType(dynamicDataSourceGlobal);
      LoggerUtil.debug(dynamicDataSourceGlobal.name() + "  -   " + ms.getSqlCommandType().name());
    }

    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof Executor) {
      return Plugin.wrap(target, this);
    } else {
      return target;
    }
  }

  @Override
  public void setProperties(Properties properties) {}
}
