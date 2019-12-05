package com.xiaodou.dao.behavior;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name UserWrongRecordDao 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月8日
 * @description 由UserWrongRecordMapper修改而来
 * @version 1.0
 */
@Repository
public class UserWrongRecordDao extends BaseDao<UserWrongRecord>{


  public Page<UserWrongRecord> findEntityByCond(Map<String, Object> cond, Page<UserWrongRecord> page) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".findEntityByCond",
        cond, page);
  }
  
  public boolean updateEntityByIdAndUid(UserWrongRecord record) {
    return 1 == this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateEntityByIdAndUid", record);
  }

  public boolean updateRightTimes(UserWrongRecord record) {
    return 1 == this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateRightTimes", record);
  }

  public boolean updateWrongTimes(UserWrongRecord record) {
    return 1 == this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateWrongTimes", record);
  }

  public Integer queryCountByCond(Map<String, Object> cond) {
    return (Integer) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryCountByCond", cond);
  }

  public Page<UserWrongRecord> findListByCond(Map<String, Object> cond,Page<UserWrongRecord> page) {
    return  this.selectPaginationList(this.getEntityClass().getSimpleName() +".findEntityListByCond"
      ,cond,page);
  }
}