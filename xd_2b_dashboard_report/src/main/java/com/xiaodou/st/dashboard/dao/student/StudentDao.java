package com.xiaodou.st.dashboard.dao.student;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class StudentDao extends BaseDao<StudentDO> {

  public Integer addEntityList(List<StudentDO> value, Map<String, Object> column) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".addEntityList",
        cond);
  }

  public Integer updateOrAddEntityList(List<StudentDO> value, Map<String, Object> column) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().update(
        this.getEntityClass().getSimpleName() + ".updateOrAddEntityList", cond);
  }

  public Integer deleteStudentByCond(Map<String, Object> entity) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", entity);
    int result =
        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntity", cond);
    return result;
  }


  public Page<StudentDO> findStudentCountListGByPilotUnit() {
    Map<String, Object> cond = Maps.newHashMap();
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".findStudentCountListGByPilotUnit", cond, null);
  }

  // 更新学生手机号码
  public boolean updateDashboardStudentTelephone(StudentDO studentDO) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateDashboardStudentTelephone", studentDO);
    return result == 1;
  }

  // 重置学生头像
  public boolean resetDashboardStudentPortrait(StudentDO studentDO) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".resetDashboardStudentPortrait", studentDO);
    return result == 1;
  }

}
