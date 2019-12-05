package com.xiaodou.ms.dao.course;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaodou.ms.model.course.CourseSubjectModel;

public class Test {

  @Autowired
  private CourseSubjectDao courseSubjectDao;


  @Ignore
  public void findEntityByIdTest() {
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setId(2L);
    CourseSubjectModel entityById = courseSubjectDao.findEntityById(courseSubjectModel);
    System.out.println(entityById.getName());
  }
  @org.junit.Test
  public void fun() {
   
  }
  

}
