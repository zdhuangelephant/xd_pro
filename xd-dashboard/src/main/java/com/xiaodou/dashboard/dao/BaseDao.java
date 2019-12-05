package com.xiaodou.dashboard.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.mjorm.MongoDao;
import com.googlecode.mjorm.MongoDaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.xiaodou.common.util.log.LoggerUtil;

public abstract class BaseDao<Entity> {

  public abstract DBCollection getDBCollection();

  MongoDao dao = new MongoDaoImpl();

  // 多数据源扩展结束

  private Class<Entity> entityClass = null;

  @SuppressWarnings("unchecked")
  public BaseDao() {
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

  /**
   * 
   * insert
   * 
   * @Title: insert
   * @Description: TODO
   * @param list
   * @param coll
   * @return
   */
  public List<Entity> insert(List<Entity> list) {
    List<Entity> resList = new ArrayList<Entity>();
    try {
      for (Entity vo : list) {
        resList.add(dao.createObject(getEntityClass().getSimpleName(), vo));
      }
      return resList;
    } catch (Exception e) {

      LoggerUtil.error("pojo 转化为DBOBject异常", e);
    }
    return null;
  }

  /**
   * 
   * insert
   * 
   * @Title: insert
   * @Description: TODO
   * @param obj
   * @param coll
   */
  public void insert(Entity obj) {
    DBObject doc;
    try {
      doc = pojoToDBObject(obj);
      getDBCollection().insert(doc);
    } catch (Exception e) {
      LoggerUtil.error("pojo 转化为DBOBject异常", e);
    }
  }

  /**
   * 
   * pojoToDBObject
   * 
   * @Title: pojoToDBObject
   * @Description: TODO
   * @param object
   * @return
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public DBObject pojoToDBObject(Entity object) throws IllegalArgumentException,
      IllegalAccessException {
    BasicDBObject doc = new BasicDBObject();
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.get(object) != null) {
        doc.put(field.getName(), field.get(object));
      }
    }
    return doc;
  }

  /**
   * dbObject2Pojo
   * 
   * @param object
   * @param clazz
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public Entity dbObject2Pojo(DBObject object, Class<Entity> clazz) throws InstantiationException,
      IllegalAccessException {
    Entity instance = clazz.newInstance();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (null != object.get(field.getName())) {
        field.set(instance, object.get(field.getName()));
      }
    }
    return instance;
  }

}
