package com.xiaodou.mooccrawler.service.course;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.mooccrawler.dao.course.CourseSubjectDao;
import com.xiaodou.mooccrawler.domain.course.CourseCategoryModel;
import com.xiaodou.mooccrawler.domain.course.CourseSubjectModel;
import com.xiaodou.mooccrawler.web.request.ResourceCourseCreateRequest;
import com.xiaodou.mooccrawler.web.request.ResourceCourseEditRequest;
import com.xiaodou.mooccrawler.web.response.ResourceCourseCreateResponse;
import com.xiaodou.mooccrawler.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseSubjectService")
public class CourseSubjectService {

  @Resource
  CourseSubjectDao courseSubjectDao;

  @Resource
  CourseCategoryService courseCategoryService;

  /**
   * 查询
   * @param id
   * @return
   */
  public CourseSubjectModel findSubjectById(Integer id){
    CourseSubjectModel cond = new CourseSubjectModel();
    cond.setId(id);
    return courseSubjectDao.findEntityById(cond);
  }

  /**
   * 新增课程
   * @param entity
   * @return
   */
  public CourseSubjectModel addSubject(CourseSubjectModel entity){
    return courseSubjectDao.addEntity(entity);
  }

  /**
   * 更新课程
   * @param courseSubjectModel
   * @return
   */
  public Boolean editSubject(CourseSubjectModel courseSubjectModel){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",courseSubjectModel.getId());
    return courseSubjectDao.updateEntity(cond, courseSubjectModel);
  }

  /**
   * 更新课程
   * @param request
   * @return
   */
  public Boolean editSubject(ResourceCourseEditRequest request){
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setCategoryId(request.getCategoryId());
    courseSubjectModel.setDetail(request.getDetail());
    courseSubjectModel.setId(request.getId());
    courseSubjectModel.setName(request.getName());
    return this.editSubject(courseSubjectModel);
  }

  /**
   * 查询所有的课程
   * @return
   */
  public Page<CourseSubjectModel> queryAllCourse(){
    Map<String,Object> cond = new HashMap<>();
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("categoryId","");
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    return courseSubjectDao.queryListByCondWithOutPage(cond, output);
  }

  /**
   * 根据栏目查询
   * @param catId
   * @return
   */
  public Page<CourseSubjectModel> queryCourseByCatId(Integer catId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("categoryId",catId);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("categoryId","");
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    return courseSubjectDao.queryListByCondWithOutPage(cond, output);
  }

  /**
   * 根据课程ID查询
   * @param courseId
   * @author zhouhaun
   * @return
   */
  public Page<CourseSubjectModel> queryCourseByCatId(String courseId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("courseId",courseId);
    Map<String,Object> output = new HashMap<>();
    output.put("courseId","");
    return courseSubjectDao.queryListByCondWithOutPage(cond, output);
  }

  /**
   * 根据栏目级联查询课程
   * @param catId
   * @return
   */
  public Page<CourseSubjectModel> cascadeQueryCourseByCatId(Integer catId){
    List<CourseCategoryModel> courseCategoryModels =
      courseCategoryService.queryAllChildCategory(catId);
    List<Integer> ids = new ArrayList<>();
    for (CourseCategoryModel categoryModel:courseCategoryModels){
      ids.add(categoryModel.getId());
    }
    ids.add(catId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("categoryIds",ids);
    return courseSubjectDao.cascadeQuerySubject(cond);
  }

  /**
   * 删除课程
   * @param id
   * @return
   */
  public Boolean deleteSubject(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return courseSubjectDao.deleteEntity(cond);
  }


  /**
   * 新增课程
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addSubject(ResourceCourseCreateRequest request){
    CourseSubjectModel subjectModel = new CourseSubjectModel();
    subjectModel.setCategoryId(request.getCategoryId());
    subjectModel.setName(request.getName());
    subjectModel.setDetail(request.getDetail());
    subjectModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    CourseSubjectModel resultModel = this.addSubject(subjectModel);

    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }
}
