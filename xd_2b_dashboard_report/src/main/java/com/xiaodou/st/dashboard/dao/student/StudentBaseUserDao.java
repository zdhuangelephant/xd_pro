package com.xiaodou.st.dashboard.dao.student;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dashboard.dao.BaseUserBaseDao;
import com.xiaodou.st.dashboard.domain.student.StudentBaseUserDO;

@Repository
public class StudentBaseUserDao extends BaseUserBaseDao<StudentBaseUserDO> {

  // 更新学生手机号码
  public boolean updateTelephone(StudentBaseUserDO studentBaseUserDO) {
    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateTelephone",
          studentBaseUserDO);
    return result == 1;
  }

  // 查询用户
  @SuppressWarnings("unchecked")
  public List<StudentBaseUserDO> findTelByTelephoneAndModule(StudentBaseUserDO studentBaseUserDO) {
    List<StudentBaseUserDO> result =
        this.getSqlSession().selectList(
            this.getEntityClass().getSimpleName() + ".findTelByTelephoneAndModule", studentBaseUserDO);
    return result;
  }
  
}
