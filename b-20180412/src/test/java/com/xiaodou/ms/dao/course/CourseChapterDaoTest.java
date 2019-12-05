package com.xiaodou.ms.dao.course;

import com.xiaodou.course.BaseUnitils;
import com.xiaodou.ms.model.course.CourseCategoryModel;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.summer.dao.pagination.Page;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CourseChapterDaoTest extends BaseUnitils {

  @SpringBean("courseChapterDao")
  CourseChapterDao courseChapterDao;

  @Test
  public void addentityTest(){
    CourseChapterModel courseChapterModel = new CourseChapterModel();
    courseChapterModel.setParentId(1L);
    courseChapterModel.setDetail("detail");
    courseChapterModel.setName("name");
    courseChapterModel.setAllChildId("aaa");
    courseChapterModel.setAllParentId("aaa");
    courseChapterModel.setChildId("cddd");
    courseChapterModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    courseChapterModel.setLevel(1);
    courseChapterModel.setSubjectId(1L);
    courseChapterDao.addEntity(courseChapterModel);
  }

  @Test
  public void findEntityByIdTest(){
    CourseChapterModel courseChapterModel = new CourseChapterModel();
    courseChapterModel.setId(2L);
    CourseChapterModel entityById = courseChapterDao.findEntityById(courseChapterModel);
    System.out.println(entityById.getName());
  }

  @Test
  public void findEntityByCondTest(){
    Map<String,Object> input = new HashMap<>();
    input.put("parentId",0);
    Map<String,Object> output = new HashMap<>();
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    Page page = new Page();
    page.setPageNo(2);
    page.setPageSize(2);
    Page<CourseChapterModel> courseChapterModelPage =
      courseChapterDao.queryListByCond(input, output, page);
    for (CourseChapterModel chapterModel:courseChapterModelPage.getResult()){
      System.out.println(chapterModel.getName());
    }
  }

  @Test
  public void updateEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",6);
    CourseChapterModel courseChapterModel = new CourseChapterModel();
    courseChapterModel.setName("update");
    courseChapterDao.updateEntity(cond,courseChapterModel);
  }

  @Test
  public void deleteEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",5);
    courseChapterDao.deleteEntity(cond);
  }

}
