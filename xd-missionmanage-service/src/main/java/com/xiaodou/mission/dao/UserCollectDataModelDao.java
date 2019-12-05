package com.xiaodou.mission.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.mission.domain.UserCollectDataModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.mission.dao.UserCollectDataModelDao.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月17日
 * @description 用户记录数据集Dao
 * @version 1.0
 */
@Repository("userCollectDataModelDao")
public class UserCollectDataModelDao extends BaseDao<UserCollectDataModel> {

  @SuppressWarnings("unchecked")
  public List<UserCollectDataModel> select4Update(UserCollectDataModel model) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", model);
    return (List<UserCollectDataModel>) this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".select4Update", cond);
  }

}
