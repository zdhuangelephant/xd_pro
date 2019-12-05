package com.xiaodou.st.dashboard.dao.student;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dashboard.dao.MisBaseDao;
import com.xiaodou.st.dashboard.domain.student.StudentMisDO;

@Repository
public class StudentMisDao extends MisBaseDao<StudentMisDO> {

  // 查询学生头像信息
  public StudentMisDO findUserOfficialInfoByUserId(StudentMisDO studentMisDO) {
    StudentMisDO result =
        (StudentMisDO) this.getSqlSession().selectOne(
            this.getEntityClass().getSimpleName() + ".findUserOfficialInfoByUserId", studentMisDO);
    return result;
  }

  // 重置学生头像
  public boolean updateUserOfficialInfoByUserId(StudentMisDO studentMisDO) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateUserOfficialInfoByUserId", studentMisDO);
    return result == 1;
  }

}
