package com.xiaodou.summer.source.jdbc.plugin;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.source.jdbc.helper.JdbcUrlHolder;
import com.xiaodou.summer.source.jdbc.mysql.domain.SqlLogDO;

/**
 * @name @see com.xiaodou.summer.source.jdbc.plugin.SqlStatementInterceptor.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年1月25日
 * @description 数据库操作性能拦截器，记录耗时<br>
 *              MyBatis 将mybatis要执行的sql拦截打印出来
 * @version 1.0
 */
@Intercepts(value = {
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
        Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class})})
public class SqlStatementInterceptor implements Interceptor {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    // 获取原始的MappedStatement
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    Object parameterObject = null;
    // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式的
    if (invocation.getArgs().length > 1) {
      parameterObject = invocation.getArgs()[1];
    }
    // 获取到节点的id，即sql语句的id
    String statementId = mappedStatement.getId();
    // BoundSql就是封装myBatis最终产生的sql类
    BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
    // 获取节点的配置
    Configuration configuration = mappedStatement.getConfiguration();
    // 获取到最终的sql语句
    String sql = getSql(boundSql, parameterObject, configuration);

    long start = System.currentTimeMillis();
    Object result = invocation.proceed();
    long end = System.currentTimeMillis();
    long executTiming = end - start;

    LoggerUtil.debug("执行sql耗时:" + executTiming + " ms" + " - statementId:" + statementId
        + " - Sql:");
    LoggerUtil.debug("   " + sql);

    SqlLogDO sqlLogDO = new SqlLogDO();
    sqlLogDO.setStatementId(statementId);
    sqlLogDO.setSql(sql);
    sqlLogDO.setExecutTiming(executTiming);
    sqlLogDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    sqlLogDO.setJdbcUrl(JdbcUrlHolder.getJdbcUrl());
    JdbcUrlHolder.clearJdbcUrl();

    String key = "sql-log:";
    int cacheSeconds = 60 * 60;
    Map<String, String> map = JedisUtil.getAllMapValueFromJedis(key);
    if (CollectionUtils.isEmpty(map)) {
      map = Maps.newConcurrentMap();
    }
    map.put(statementId, FastJsonUtil.toJson(sqlLogDO));
    JedisUtil.addHashMapToJedis(key, map, cacheSeconds);
    return result;
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof Executor) {
      return Plugin.wrap(target, this);
    }
    return target;
  }

  @Override
  public void setProperties(Properties properties) {}

  private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
    // sql语句中多个空格都用一个空格代替
    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\s]+", " ");
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
    TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    if (parameterMappings != null) {
      for (int i = 0; i < parameterMappings.size(); i++) {
        ParameterMapping parameterMapping = parameterMappings.get(i);
        if (parameterMapping.getMode() != ParameterMode.OUT) {
          Object value;
          String propertyName = parameterMapping.getProperty();
          if (boundSql.hasAdditionalParameter(propertyName)) {
            value = boundSql.getAdditionalParameter(propertyName);
          } else if (parameterObject == null) {
            value = null;
            // 如果根据parameterObject.getClass()可以找到对应的类型，则替换
          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            value = parameterObject;
          } else {
            // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值，主要支持对JavaBean、Collection、Map三种对象的操作
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            value = metaObject.getValue(propertyName);
          }
          sql = replacePlaceholder(sql, value);
        }
      }
    }
    return sql;
  }

  /**
   * @description 如果参数是String，则添加单引号，如果是日期，则转换为时间格式串并加单引号，对参数是null的情况做了处理
   * @author 李德洪
   * @Date 2018年1月25日
   * @param sql
   * @param propertyValue
   * @return
   */
  private String replacePlaceholder(String sql, Object propertyValue) {
    String result;
    if (propertyValue != null) {
      if (propertyValue instanceof String) {
        result = "'" + propertyValue + "'";
      } else if (propertyValue instanceof Date) {
        result = "'" + DATE_FORMAT.format(propertyValue) + "'";
      } else {
        result = propertyValue.toString();
      }
    } else {
      result = "null";
    }
    // 可使用 Matcher.quoteReplacement(java.lang.String)取消 反斜杠 (/) 和美元符号 ($) 这些字符的特殊含义。
    return sql.replaceFirst("\\?", Matcher.quoteReplacement(result));
  }
}
