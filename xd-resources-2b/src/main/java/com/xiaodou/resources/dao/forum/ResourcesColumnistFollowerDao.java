package com.xiaodou.resources.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.model.forum.ResourcesColumnistFollower;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name ResourcesColumnistFollowerDao CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 资源专栏关注者Dao
 * @version 1.0
 */
@Repository("resourcesColumnistFollowerDao")
public class ResourcesColumnistFollowerDao extends BaseDao<ResourcesColumnistFollower> {

  public Integer findEntityCountByCond(IQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    return (Integer) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityCountByCond", cond);
  }

}
