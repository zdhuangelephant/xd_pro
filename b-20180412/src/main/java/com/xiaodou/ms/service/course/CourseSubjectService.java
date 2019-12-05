package com.xiaodou.ms.service.course;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.course.CourseSubjectDao;
import com.xiaodou.ms.model.course.CourseCategoryModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.web.request.course.ResourceCourseCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceCourseEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

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
   * 
   * @param id
   * @return
   */
  public CourseSubjectModel findSubjectById(Long id) {
    CourseSubjectModel cond = new CourseSubjectModel();
    cond.setId(id);
    return courseSubjectDao.findEntityById(cond);
  }

  /**
   * 新增课程
   * 
   * @param entity
   * @return
   */
  public CourseSubjectModel addSubject(CourseSubjectModel entity) {
    return courseSubjectDao.addEntity(entity);
  }

  /**
   * 更新课程
   * 
   * @param courseSubjectModel
   * @return
   */
  public Boolean editSubject(CourseSubjectModel courseSubjectModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", courseSubjectModel.getId());
    return courseSubjectDao.updateEntity(cond, courseSubjectModel);
  }

  /**
   * 更新课程
   * 
   * @param request
   * @return
   */
  public Boolean editSubject(ResourceCourseEditRequest request) {
    CourseSubjectModel courseSubjectModel = new CourseSubjectModel();
    courseSubjectModel.setCategoryId(request.getCategoryId());
    courseSubjectModel.setDetail(request.getDetail());
    courseSubjectModel.setId(request.getId());
    courseSubjectModel.setName(request.getName());
    return this.editSubject(courseSubjectModel);
  }

  /**
   * 查询匹配条件的课程资源
   * 
   * @param courseName 模糊查询课程名
   * @return 匹配课程列表
   */
  public Page<CourseSubjectModel> searchMatchCourse(String courseName) {
    IQueryParam param = new QueryParam();
    if (StringUtils.isNotBlank(courseName)) {
      param.addInput("nameLike", courseName);
    }
    param.addOutputs(CommUtil.getAllField(CourseSubjectModel.class));
    Page<CourseSubjectModel> forumPage = new Page<>(10);
    forumPage = courseSubjectDao.findEntityListByCond(param, null);
    if (null == forumPage) return null;
    return forumPage;
  }

  /**
   * 查询所有的课程
   * 
   * @return
   */
  public Page<CourseSubjectModel> queryAllCourse() {
    Map<String, Object> cond = new HashMap<>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("categoryId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("createTime", "");
    return courseSubjectDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据栏目查询
   * 
   * @param catId
   * @return
   */
  public Page<CourseSubjectModel> queryCourseByCatId(Integer catId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("categoryId", catId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("categoryId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("createTime", "");
    return courseSubjectDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据课程ID查询
   * 
   * @param courseId
   * @author zhouhaun
   * @return
   */
  public Page<CourseSubjectModel> queryCourseByCatId(String courseId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("courseId", courseId);
    Map<String, Object> output = new HashMap<>();
    output.put("courseId", "");
    return courseSubjectDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据栏目级联查询课程
   * 
   * @param catId
   * @return
   */
  public Page<CourseSubjectModel> cascadeQueryCourseByCatId(Long catId) {
    List<CourseCategoryModel> courseCategoryModels =
        courseCategoryService.queryAllChildCategory(catId);
    List<Long> ids = new ArrayList<>();
    for (CourseCategoryModel categoryModel : courseCategoryModels) {
      ids.add(categoryModel.getId());
    }
    ids.add(catId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("categoryIds", ids);
    return courseSubjectDao.cascadeQuerySubject(cond);
  }

  /**
   * 删除课程
   * 
   * @param id
   * @return
   */
  public Boolean deleteSubject(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return courseSubjectDao.deleteEntity(cond);
  }


  /**
   * 新增课程
   * 
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addSubject(ResourceCourseCreateRequest request) {
    CourseSubjectModel subjectModel = new CourseSubjectModel();
    subjectModel.setCategoryId(request.getCategoryId());
    subjectModel.setName(request.getName());
    subjectModel.setDetail(request.getDetail());
    subjectModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    CourseSubjectModel resultModel = this.addSubject(subjectModel);
    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    response.setSubjectModel(resultModel);
    return response;
  }
}
