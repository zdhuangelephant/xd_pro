package com.xiaodou.summer.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.summer.dao.datasource.SummerSqlSessionFactory;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.summer.dao.ExtendBaseDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月11日
 * @description 手动注入数据源
 * @version 1.0
 * @param <Entity>
 */
public abstract class ExtendBaseDao<Entity> extends DaoSupport implements ICommonCRUDDao<Entity> {

  private String TYPE_PRI = "pri";

  // 多数据源扩展

  private SqlSession sqlSession;

  private SqlSession rSqlSession;

  private boolean externalSqlSession;

  public void setSummerSqlSessionFactory(SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    if (!this.externalSqlSession) {
      this.sqlSession = new SqlSessionTemplate(sqlSessionFactory.getMObject());
      this.rSqlSession = new SqlSessionTemplate(sqlSessionFactory.getSObject());
    }
  }

  /**
   * Users should use this method to get a SqlSession to call its statement methods This is
   * SqlSession is managed by spring. Users should not commit/rollback/close it because it will be
   * automatically done.
   * 
   * @return Spring managed thread safe SqlSession
   */
  public final SqlSession getSqlSession() {
    if (null != SecurityContextHolder.getContext()
        && null != SecurityContextHolder.getContext().getAuthentication()
        && null != SecurityContextHolder.getContext().getAuthentication().getPrincipal()) {
      AdminUser currUser =
          (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (TYPE_PRI.equals(currUser.getType())) return rSqlSession;
    }
    return sqlSession;
  }

  /**
   * {@inheritDoc}
   */
  protected void checkDaoConfig() {
    Assert.notNull(this.sqlSession, "Property 'summerSqlSessionFactory' are required");
    Assert.notNull(this.rSqlSession, "Property 'summerSqlSessionFactory' are required");
  }

  // 多数据源扩展结束

  private Class<Entity> entityClass = null;

  @SuppressWarnings("unchecked")
  public ExtendBaseDao() {
    this.entityClass = null;
    Class<?> c = getClass();
    Type type = c.getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
      this.entityClass = (Class<Entity>) parameterizedType[0];
    }
  }

  public Class<Entity> getEntityClass() {
    return entityClass;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Page<Entity> selectPaginationList(String statement, Object parameter, Page<Entity> page) {
    if (parameter == null) {
      throw new RuntimeException("parameter can not be null");
    }
    if (parameter instanceof Map<?, ?>) {
      ((Map) parameter).put("paginationInfo", page);
    }
    List result = this.getSqlSession().selectList(statement, parameter);

    if (page == null) {
      page = new Page();
      page.setPageNo(1);
      page.setPageSize(result.size());
      page.setTotalPage(1);
      page.setTotalCount(result.size());
    }
    page.setResult(result);
    return page;
  }

  /**
   * 新版本中统一了mybatis_xml命名规则, 不建议继续使用该方法
   * 
   * @参考 {@link #deleteEntityById(Object)} {@link #deleteEntityByCond(Map)}
   */
  @Deprecated
  public boolean deleteEntity(Entity entity) {
    int result =
        this.getSqlSession()
            .delete(this.getEntityClass().getSimpleName() + ".deleteEntity", entity);
    return result == 1;
  }

  @Override
  public boolean deleteEntityById(Entity entity) {
    int result =
        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntityById",
            entity);
    return result == 1;
  }

  @Override
  public boolean deleteEntityByCond(Map<String, Object> entity) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", entity);
    int result =
        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntity", cond);
    return result == 1;
  }

  @Override
  public List<Entity> deleteEntityListById(List<Entity> entityList) {
    List<Entity> result = new ArrayList<Entity>();
    for (Entity entity : entityList) {
      int operationResult =
          this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntityById",
              entity);
      if (operationResult == 1) {
        result.add(entity);
      }
    }
    return result;
  }

  public Entity addEntity(Entity entity) {
    this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addEntity", entity);
    return entity;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entity findEntityById(Entity entity) {
    return (Entity) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityById", entity);
  }

  @Deprecated
  public Page<Entity> findEntityListByCond(Map<String, Object> cond, Page<Entity> page) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findEntityListByCond", cond, page);
  }

  @Override
  public Page<Entity> findEntityListByCond(IQueryParam param, Page<Entity> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findEntityListByCond", cond, page);
  }

  @Override
  public Page<Entity> findEntityListByCond(IJoinQueryParam param, Page<Entity> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getJoin() && param.getJoin().size() > 0)
      cond.put("join", param.getJoin());
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
      cond.put("output", param.getOutput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0)
      cond.put("sort", param.getSort());
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
      cond.put("limit", param.getLimit());
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findEntityListByCond", cond, page);
  }

  /**
   * 新版本中统一了mybatis_xml命名规则, 不建议继续使用该方法
   * 
   * @参考 {@link #updateEntityById(Object)} {@link #updateEntityByCond(Map, Object)}
   */
  @Deprecated
  public boolean updateEntity(Entity entity) {
    int result =
        this.getSqlSession()
            .update(this.getEntityClass().getSimpleName() + ".updateEntity", entity);
    return result == 1;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean updateEntityById(Entity entity) {
    Map<String, Object> cond = Maps.newHashMap();
    if (entity instanceof Map) {
      cond.putAll((Map) entity);
    } else {
      CommUtil.transferFromVO2Map(cond, entity);
    }
    cond.put("value", entity);
    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntityById",
            cond);
    return result == 1;
  }

  @Override
  public boolean updateEntityByCond(Map<String, Object> input, Entity entity) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    cond.put("value", entity);
    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity", cond);
    return result == 1;
  }

  @Override
  public List<Entity> updateEntityListById(List<Entity> entityList) {
    List<Entity> result = new ArrayList<Entity>();
    for (Entity entity : entityList) {
      int operationResult =
          this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
              entity);
      if (operationResult == 1) {
        result.add(entity);
      }
    }
    return result;
  }

}
