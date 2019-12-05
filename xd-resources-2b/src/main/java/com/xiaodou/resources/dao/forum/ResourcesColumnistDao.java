package com.xiaodou.resources.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name ResourcesColumnistDao CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年8月31日
 * @description 资源专栏操作Dao
 * @version 1.0
 */
@Repository("resourcesColumnistDao")
public class ResourcesColumnistDao extends BaseDao<ResourcesColumnist> {

  public Integer findEntityCountByCond(IQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    return (Integer) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityCountByCond", cond);
  }
}
