package com.xiaodou.resources.dao.quesbk;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class UserWrongRecordMapper extends BaseDao<UserWrongRecord> {

  public Page<UserWrongRecord> findEntityByCond(Map<String, Object> cond, Page<UserWrongRecord> page) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".findEntityByCond",
        cond, page);
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

}
