package com.xiaodou.summer.dao.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DaoSupport;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.reflect.ReflectionField;
import com.xiaodou.summer.dao.ICommonCRUDDao;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;
import com.xiaodou.summer.dao.param.QueryEnums.Limit;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.summer.dao.mongo.BaseDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月22日
 * @description 自动注入数据源
 * @version 1.0
 * @param <Entity>
 */
public class ExtendMongoBaseDao<Entity> extends DaoSupport implements ICommonCRUDDao<Entity> {
  @SuppressWarnings("unchecked")
  public ExtendMongoBaseDao() {
    this.entityClass = null;
    Class<?> c = getClass();
    Type type = c.getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
      this.entityClass = (Class<Entity>) parameterizedType[0];
      Class<Entity> entityType = this.getEntityClass();
      CollectionName collectionNameAnno = entityType.getAnnotation(CollectionName.class);
      if (null != collectionNameAnno && StringUtils.isNotBlank(collectionNameAnno.value())) {
        this.collectionName = collectionNameAnno.value();
      } else {
        this.collectionName = entityType.getSimpleName().toLowerCase();
      }
    }
  }

  private Class<Entity> entityClass = null;
  private MongoTemplate mongoTemplate;
  private String collectionName = null;

  public void setSummerMongoTemplate(MongoTemplate mongoTemplate) throws Exception {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  protected void checkDaoConfig() throws IllegalArgumentException {
    Assert.notNull(this.mongoTemplate, "Property 'summerMongoDbFactory' are required");
  }

  private String getPk(String key) {
    if ("id".equals(key)) {
      throw new IllegalArgumentException("Pk field can't be 'id'.");
    }
    return key;
  }

  private boolean checkPk(Entity entity) {
    if (null == entity) {
      return false;
    }
    for (Field field : entity.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      Object value = ReflectionField.getField(field, entity);
      if (null == value) continue;
      if (field.isAnnotationPresent(Pk.class)) {
        return StringUtils.isNotBlank(getPk(field.getName()));
      }
      field.setAccessible(false);
    }
    return false;
  }

  /************************** 其他 **********************************/
  /**
   * 通过条件查询实体(集合) 类自用方法
   * 
   * @param query
   */
  private List<Entity> find(Query query) {
    return mongoTemplate.find(query, this.getEntityClass(), collectionName);
  }

  /**
   * 获取需要操作的实体类class
   * 
   * @return
   */
  public Class<Entity> getEntityClass() {
    return entityClass;
  }

  @SuppressWarnings({"rawtypes"})
  private Query packageCondQuery(Map cond) {
    Query query = new Query();
    if (null != cond && cond.size() > 0) {
      Criteria c = new Criteria();
      for (Object key : cond.keySet()) {
        Object valueWrapper = cond.get(key);
        if (valueWrapper instanceof MongoFieldParam) {
          MongoFieldParam mongoFieldParam = (MongoFieldParam) valueWrapper;
          Scope scope = mongoFieldParam.getScope();
          if (null == scope) scope = Scope.EQ;
          c = scope.append(c, key.toString(), mongoFieldParam.getValue());
        } else {
          c = c.and((key.toString())).is(valueWrapper);
        }
      }
      query = new Query(c);
    }
    return query;
  }

  @SuppressWarnings("rawtypes")
  private Update packageUpdateQuery(Map modify) {
    Update update = new Update();
    if (modify != null) {
      for (Object key : modify.keySet()) {
        if (modify.get(key) != null) {
          update.set(key.toString(), modify.get(key));
        }
      }
    }
    return update;
  }

  @Override
  public Entity addEntity(Entity entity) {
    if (!checkPk(entity)) {
      throw new IllegalArgumentException("ERROR: Can't Find PK Field.");
    }
    mongoTemplate.save(entity, collectionName);
    return entity;
  }

  @Override
  public boolean deleteEntityById(Entity entity) {
    try {
      Assert.notNull(entity, "entity can't be null");
      Map<String, Object> params = Maps.newHashMap();
      for (Field field : entity.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = ReflectionField.getField(field, entity);
        if (null == value) continue;
        if (field.isAnnotationPresent(Pk.class)) params.put(getPk(field.getName()), value);
        field.setAccessible(false);
      }
      Assert.isTrue(params.size() > 0, "can't find pk.");
      Query query = this.packageCondQuery(params);
      this.mongoTemplate.remove(query, collectionName);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("mongo delete by id fail.", e);
      return false;
    }
  }

  @Override
  public boolean deleteEntityByCond(IDeleteParam param) {
    try {
      Query query = this.packageCondQuery(param.getInput());
      this.mongoTemplate.remove(query, collectionName);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("mongo delete by id fail.", e);
      return false;
    }
  }

  @Override
  public boolean updateEntityById(Entity entity) {
    try {
      Assert.notNull(entity, "entity can't be null");
      Map<String, Object> cond = Maps.newHashMap();
      Map<String, Object> modify = Maps.newHashMap();
      for (Field field : entity.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = ReflectionField.getField(field, entity);
        if (null == value) continue;
        if (field.isAnnotationPresent(Pk.class))
          cond.put(getPk(field.getName()), value);
        else
          modify.put(field.getName(), value);
        field.setAccessible(false);
      }
      Assert.isTrue(cond.size() > 0, "can't find pk.");
      Assert.isTrue(modify.size() > 0, "modify value is empty.");
      Query query = this.packageCondQuery(cond);
      Update update = this.packageUpdateQuery(modify);
      this.mongoTemplate.updateMulti(query, update, collectionName);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("mongo update by id fail.", e);
      return false;
    }
  }

  @Override
  public boolean updateEntityByCond(IUpdateParam param) {
    try {
      Assert.isTrue(null != param.getInput() && !param.getInput().isEmpty(),
          "Condition can't be empty.");
      Assert.isTrue(null != param.getValue() && !param.getValue().isEmpty(),
          "SetValue can't be empty");
      Query query = this.packageCondQuery(param.getInput());
      Update update = this.packageUpdateQuery(param.getValue());
      this.mongoTemplate.updateMulti(query, update, collectionName);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("mongo update by cond fail.", e);
      return false;
    }
  }

  @Override
  public List<Entity> deleteEntityListById(List<Entity> entityList) {
    if (null == entityList || entityList.size() == 0) return null;
    for (Entity entity : entityList)
      deleteEntityById(entity);
    return entityList;
  }

  @Override
  public List<Entity> updateEntityListById(List<Entity> entityList) {
    if (null == entityList || entityList.size() == 0) return null;
    for (Entity entity : entityList)
      updateEntityById(entity);
    return entityList;
  }

  @Override
  public Entity findEntityById(Entity entity) {
    Assert.notNull(entity, "entity can't be null");
    Map<String, Object> param = Maps.newHashMap();
    for (Field field : entity.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      Object value = ReflectionField.getField(field, entity);
      if (null == value) continue;
      if (field.isAnnotationPresent(Pk.class)) param.put(getPk(field.getName()), value);
      field.setAccessible(false);
    }
    Assert.notNull(param, "pk can't be null");
    Query query = this.packageCondQuery(param);
    List<Entity> entityList = mongoTemplate.find(query, entityClass, collectionName);
    if (null == entityList || entityList.size() == 0) return null;
    return entityList.get(0);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public Page<Entity> findEntityListByCond(IQueryParam param, Page<Entity> page) {
    Assert.isTrue(null != param, "param can't be null.");
    Map<String, Object> limit = Maps.newHashMap();
    Map<String, Object> sort = Maps.newHashMap();
    Query query = packageCondQuery(param.getInput());
    if (null != param && null != param.getSort() && param.getSort().size() > 0) {
      sort = param.getSort();
      if (sort != null) {
        for (Object key : sort.keySet()) {
          if (sort.get(key).toString().equals(Sort.ASC.name())) {
            query.sort().on((String) key, Order.ASCENDING);
          } else {
            query.sort().on((String) key, Order.DESCENDING);
          }
        }
      }
    }

    long totalCount = this.mongoTemplate.count(query, collectionName);

    if (null != param && null != param.getLimit() && param.getLimit().size() > 0) {
      limit = param.getLimit();
      if (limit != null) {
        query.skip((int) limit.get(Limit.limitStart.toString()));// skip相当于从那条记录开始
        query.limit((int) limit.get(Limit.limitCount.toString()));// 从skip开始,取多少条记录
      }
      totalCount = this.mongoTemplate.count(query, collectionName);
    } else if (null != page) {
      query.skip((page.getPageNo() - 1) * page.getPageSize());// skip相当于从那条记录开始
      query.limit(page.getPageSize());// 从skip开始,取多少条记录
    }
    if (page == null) {
      page = new Page<Entity>();
      page.setPageNo(1);
      page.setPageSize((int) totalCount);
      page.setTotalPage(1);
    } else {
      if ((int) totalCount % page.getPageSize() == 0)
        page.setTotalPage((int) totalCount / page.getPageSize());
      else
        page.setTotalPage(((int) totalCount / page.getPageSize()) + 1);
    }
    page.setTotalCount(totalCount);
    List<Entity> datas = this.find(query);
    page.setResult(datas);
    return page;
  }

  @Override
  public Page<Entity> findEntityListByCond(IJoinQueryParam param, Page<Entity> page) {
    return findEntityListByCond(param, page);
  }

}
