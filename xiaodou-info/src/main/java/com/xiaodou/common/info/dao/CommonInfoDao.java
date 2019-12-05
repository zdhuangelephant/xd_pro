package com.xiaodou.common.info.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.common.city.dao.CommonInfoDao.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月6日
 * @description 公共信息Dao
 * @version 1.0
 */
@Repository("commonInfoDao")
public class CommonInfoDao extends BaseDao<CommonInfo> {

  @SuppressWarnings("unchecked")
  public List<CommonInfo> findEntityListByCond(Map<String, Object> cond) {
    /*Page<CommonInfo> result = findEntityListByCond(cond, null);
    if (result != null && result.getResult() != null && result.getResult().size() > 0)
      return result.getResult();
    return null;*/
    
    // findEntityListByCond
    return (List<CommonInfo>) getSqlSession().selectOne(
      this.getEntityClass().getSimpleName() + ".findEntityListByCond", cond);
  }
  
  public CommonInfo findEntityByInfoCode(CommonInfo commonInfo) {
    return (CommonInfo) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityByInfoCode", commonInfo);
  }


  public boolean updateEntityByInfoCode(CommonInfo commonInfo) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateEntityByInfoCode", commonInfo);
    return result == 1;
  }
  
}
