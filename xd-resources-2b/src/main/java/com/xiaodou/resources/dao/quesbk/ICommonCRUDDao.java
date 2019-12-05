package com.xiaodou.resources.dao.quesbk;

import com.xiaodou.summer.dao.pagination.Page;

import java.util.List;
import java.util.Map;

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
  public boolean deleteEntity(Entity entity);

  /**
   * update
   *
   * @param entity
   * @return 是否更新成功
   */
  public boolean updateEntity(Entity entity);

  /**
   * delete list
   *
   * @param entityList
   * @return 返回删除成功实体列表
   */
  public List<Entity> deleteEntityList(List<Entity> entityList);

  /**
   * update list
   *
   * @param entityList
   * @return 返回更新成功实体列表
   */
  public List<Entity> updateEntityList(List<Entity> entityList);

  /**
   * select by condition
   *
   * @param cond
   * @param page
   * @return 分页实体列表
   */
  public Page<Entity> findEntityListByCond(Map<String, Object> cond, Page<Entity> page);

  /**
   * select by id
   *
   * @param entity
   * @return 实体
   */
  public Entity findEntityById(Entity entity);
}
