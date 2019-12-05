package com.xiaodou._2b_dashboard_report;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.http.HttpService;
import com.xiaodou.st.dashboard.service.task.StudentTask;

public class StudentTaskTest extends BaseUnitils {


  @SpringBean("studentTask")
  StudentTask studentTask;
  
  @SpringBean("stServiceFacade")
  IStServiceFacade stServiceFacade;

  @Test
  public void message() {
    //studentTask.updateStudentCount();
    StudentDO studentDO = new StudentDO();
    studentDO.setId(5547);
    studentDO.setUserId(null);
    studentDO.setStudentStatus((short)1);
    studentDO.setAdmissionCardCode(null);
    stServiceFacade.updateStudent(studentDO);
  }

}
