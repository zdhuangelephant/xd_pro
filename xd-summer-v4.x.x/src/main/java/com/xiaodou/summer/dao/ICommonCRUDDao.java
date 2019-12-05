package com.xiaodou.summer.dao;

import java.util.List;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;

/**
 * 
 * @Title:ICommonCRUDDao.java
 * 
 * @Description:定义而来通用的增删改查方法
 * 
 * @author zhaoyang
 * @date Jan 17, 2014 9:58:42 AM
 * @version V1.0
 */

public interface ICommonCRUDDao<Entity> {
  /**
   * insert
   * 
   * @param entity
   * @return 返回实体包含生成主键
   */
  public Entity addEntity(Entity entity);

  /**
   * delete
   * 
   * @param entity
   * @return 是否删除成功
   */
  public boolean deleteEntityById(Entity entity);

  /**
   * delete
   * 
   * @param entity
   * @return 是否删除成功
   */
  public boolean deleteEntityByCond(IDeleteParam param);

  /**
   * update
   * 
   * @param entity
   * @return 是否更新成功
   */
  public boolean updateEntityById(Entity entity);

  /**
   * update
   * 
   * @param entity
   * @return 是否更新成功
   */
  public boolean updateEntityByCond(IUpdateParam param);

  /**
   * delete list
   * 
   * @param entityList
   * @return 返回删除成功实体列表
   */
  public List<Entity> deleteEntityListById(List<Entity> entityList);

  /**
   * update list
   * 
   * @param entityList
   * @return 返回更新成功实体列表
   */
  public List<Entity> updateEntityListById(List<Entity> entityList);

  /**
   * select by id
   * 
   * @param entity
   * @return 实体
   */
  public Entity findEntityById(Entity entity);

  /**
   * select by condition
   * 
   * @param cond
   * @param page
   * @return 分页实体列表
   */
  public Page<Entity> findEntityListByCond(IQueryParam param, Page<Entity> page);
  
  /**
   * select by condition
   * 
   * @param cond
   * @param page
   * @return 分页实体列表
   */
  public Page<Entity> findEntityListByCond(IJoinQueryParam param, Page<Entity> page);
}
