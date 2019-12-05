package com.xiaodou.resources.dao.quesbk;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.resources.dao.BaseProcessDao;
import com.xiaodou.resources.model.quesbk.UserCourseStatisticDayModel;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.summer.dao.pagination.Page;

@Repository("userCourseStatisticDayMapper")
public class UserCourseStatisticDayMapper extends BaseProcessDao<UserCourseStatisticDayModel> {

  public Page<UserCourseStatisticDayModel> findEntityByCond(Map<String, Object> cond, Page<UserCourseStatisticDayModel> page) {
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
