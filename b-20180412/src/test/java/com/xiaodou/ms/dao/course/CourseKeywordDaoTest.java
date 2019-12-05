package com.xiaodou.ms.dao.course;

import com.xiaodou.course.BaseUnitils;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.summer.dao.pagination.Page;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CourseKeywordDaoTest extends BaseUnitils {

  @SpringBean("courseKeywordDao")
  CourseKeywordDao courseKeywordDao;

  @Test
  public void addentityTest(){
    CourseKeywordModel courseKeywordModel = new CourseKeywordModel();
    courseKeywordModel.setName("name");
    courseKeywordModel.setChapterId(1L);
    courseKeywordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    courseKeywordDao.addEntity(courseKeywordModel);
  }

  @Test
  public void findEntityByIdTest(){
    CourseKeywordModel courseKeywordModel = new CourseKeywordModel();
    courseKeywordModel.setId(1L);
    CourseKeywordModel entityById = courseKeywordDao.findEntityById(courseKeywordModel);
    System.out.println(entityById.getName());
  }

  @Test
  public void findEntityByCondTest(){
    Map<String,Object> input = new HashMap<>();
    input.put("chapterId",1);
    Map<String,Object> output = new HashMap<>();
    output.put("name","");
    output.put("createTime","");
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(2);
    Page<CourseKeywordModel> courseKeywordModelPage =
      courseKeywordDao.queryListByCond(input, output, page);
    for (CourseKeywordModel chapterModel:courseKeywordModelPage.getResult()){
      System.out.println(chapterModel.getName());
    }
  }

  @Test
  public void updateEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",1);
    CourseKeywordModel courseKeywordModel = new CourseKeywordModel();
    courseKeywordModel.setName("update");
    courseKeywordDao.updateEntity(cond,courseKeywordModel);
  }

  @Test
  public void deleteEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",1);
    courseKeywordDao.deleteEntity(cond);
  }

}
