package com.xiaodou.ms.dao.course;

import com.xiaodou.course.BaseUnitils;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.summer.dao.pagination.Page;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CourseSubjectDaoTest extends BaseUnitils {

  @SpringBean("courseSubjectDao")
  CourseSubjectDao courseSubjectDao;

  @Test
  public void addentityTest(){
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setName("name");
    courseSubjectModel.setCategoryId(1L);
    courseSubjectModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    courseSubjectModel.setDetail("detail");
    courseSubjectDao.addEntity(courseSubjectModel);
  }

  @Test
  public void findEntityByIdTest(){
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setId(2L);
    CourseSubjectModel entityById = courseSubjectDao.findEntityById(courseSubjectModel);
    System.out.println(entityById.getName());
  }

  @Test
  public void findEntityByCondTest(){
    Map<String,Object> input = new HashMap<>();
    input.put("categoryId",2);
    Map<String,Object> output = new HashMap<>();
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(2);
    Page<CourseSubjectModel> courseSubjectModelPage =
      courseSubjectDao.queryListByCond(input, output, page);
    for (CourseSubjectModel chapterModel:courseSubjectModelPage.getResult()){
      System.out.println(chapterModel.getName());
    }
  }

  @Test
  public void updateEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",2);
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setName("update");
    courseSubjectDao.updateEntity(cond,courseSubjectModel);
  }

  @Test
  public void deleteEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",3);
    courseSubjectDao.deleteEntity(cond);
  }

}
