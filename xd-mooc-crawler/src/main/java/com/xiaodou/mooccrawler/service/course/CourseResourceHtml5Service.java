package com.xiaodou.mooccrawler.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.mooccrawler.common.ResourceType;
import com.xiaodou.mooccrawler.dao.course.CourseResourceHtml5Dao;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceHtml5Model;
import com.xiaodou.mooccrawler.web.request.ResourceHtml5CreateRequest;
import com.xiaodou.mooccrawler.web.request.ResourceHtml5EditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceHtml5Service")
public class CourseResourceHtml5Service {

  @Resource
  CourseResourceHtml5Dao courseResourceHtml5Dao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;



  /**
   * 更新关键词列表
   * @param resourceId
   * @param keywordList
   * @return
   */
  public Boolean updateKeyPoint(Integer resourceId,List<CourseKeywordModel> keywordList){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",resourceId);
    CourseResourceHtml5Model h5Model = new CourseResourceHtml5Model();
    h5Model.setKeyPoint(JSON.toJSONString(keywordList));
    return courseResourceHtml5Dao.updateEntity(cond,h5Model);
  }

  /**
   * 级联获取文档列表
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceHtml5Model> cascadeQueryHtml5ByChapterId(Integer courseId,Integer chapterId){
    List<CourseChapterModel> courseChapterModels = courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Integer> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel:courseChapterModels){
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    Page<CourseResourceHtml5Model> courseResourceHtml5ModelPage =
      courseResourceHtml5Dao.cascadeQueryHtml5(cond);
    return courseResourceHtml5ModelPage;
  }

  /**
   * 新增文档
   * @param entity
   * @return
   */
  public CourseResourceHtml5Model addHtml5(CourseResourceHtml5Model entity){
    return courseResourceHtml5Dao.addEntity(entity);
  }

  /**
   * 新增文档
   * @param request
   * @return
   */
  public CourseResourceHtml5Model addHtml5(ResourceHtml5CreateRequest request){
    CourseResourceHtml5Model courseResourceHtml5Model = new CourseResourceHtml5Model();
    courseResourceHtml5Model.setDetail(request.getDetail());
    courseResourceHtml5Model.setName(request.getName());
    courseResourceHtml5Model.setChapterId(request.getChapterId());
    courseResourceHtml5Model.setUrl(request.getUrl());
    courseResourceHtml5Model.setFileUrl(request.getFileUrl());
    courseResourceHtml5Model.setCourseId(request.getCourseId());
    CourseResourceHtml5Model result = this.addHtml5(courseResourceHtml5Model);
    return result;
  }

  /**
   * 删除文档
   * @param id
   * @return
   */
  public Boolean deleteResourceHtml5(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id", id);
    return courseResourceHtml5Dao.deleteEntity(cond);
  }


  /**
   * 根据主键查询
   * @param id
   * @return
   */
  public CourseResourceHtml5Model findResourceHtml5ById(Integer id){
    CourseResourceHtml5Model entity = new CourseResourceHtml5Model();
    entity.setId(id);
    return courseResourceHtml5Dao.findEntityById(entity);
  }
}
