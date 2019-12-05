package com.xiaodou.ms.dao.course;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.course.BaseUnitils;
import com.xiaodou.ms.model.course.CourseCategoryModel;
import com.xiaodou.summer.dao.pagination.Page;

public class CourseCategoryDaoTest extends BaseUnitils {

  @SpringBean("courseCategoryDao")
  CourseCategoryDao courseCategoryDao;

  @Test
  public void addEntityTest(){
    CourseCategoryModel courseCategoryModel = new CourseCategoryModel();
    courseCategoryModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    courseCategoryModel.setDetail("detail");
    courseCategoryModel.setName("ceshi");
    courseCategoryModel.setParentId(0L);
    courseCategoryDao.addEntity(courseCategoryModel);
  }

  @Test
  public void findEntityByIdTest(){
    CourseCategoryModel courseCategoryModel = new CourseCategoryModel();
    courseCategoryModel.setId(2L);
    CourseCategoryModel courseCategory = courseCategoryDao.findEntityById(courseCategoryModel);
    System.out.println(courseCategory.getName());
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
    page.setPageSize(1);
    Page<CourseCategoryModel> courseCategoryModelPage =
      courseCategoryDao.queryListByCond(input, output, page);
    for (CourseCategoryModel categoryModel:courseCategoryModelPage.getResult()){
      System.out.println(categoryModel.getName());
    }
  }

  @Test
  public void findEntityByCondWithoutPage(){
    Map<String,Object> input = new HashMap<>();
    input.put("parentId",0);
    Map<String,Object> output = new HashMap<>();
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    Page<CourseCategoryModel> courseCategoryModelPage =
      courseCategoryDao.queryListByCond0(input, output, null);
    for (CourseCategoryModel categoryModel:courseCategoryModelPage.getResult()){
      System.out.println(categoryModel.getName());
    }
  }

  @Test
  public void updateEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",6);
    CourseCategoryModel courseCategoryModel = new CourseCategoryModel();
    courseCategoryModel.setName("update");
    courseCategoryDao.updateEntity(cond,courseCategoryModel);
  }

  @Test
  public void deleteEntityTest(){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",5);
    courseCategoryDao.deleteEntity(cond);
  }

}
