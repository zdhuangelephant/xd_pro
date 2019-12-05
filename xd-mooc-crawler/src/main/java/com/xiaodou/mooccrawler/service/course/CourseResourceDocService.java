package com.xiaodou.mooccrawler.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.mooccrawler.common.ResourceType;
import com.xiaodou.mooccrawler.dao.course.CourseResourceDocDao;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceDocModel;
import com.xiaodou.mooccrawler.web.request.ResourceDocCreateRequest;
import com.xiaodou.mooccrawler.web.request.ResourceDocEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceDocService")
public class CourseResourceDocService {

  @Resource
  CourseResourceDocDao courseResourceDocDao;

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
    CourseResourceDocModel docModel = new CourseResourceDocModel();
    docModel.setKeyPoint(JSON.toJSONString(keywordList));
    return courseResourceDocDao.updateEntity(cond,docModel);
  }

  /**
   * 级联获取文档列表
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceDocModel> cascadeQueryDocByChapterId(Integer courseId,Integer chapterId){
    List<CourseChapterModel> courseChapterModels = courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Integer> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel:courseChapterModels){
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    Page<CourseResourceDocModel> courseResourceDocModelPage =
      courseResourceDocDao.cascadeQueryDoc(cond);
    return courseResourceDocModelPage;
  }

  /**
   * 新增文档
   * @param entity
   * @return
   */
  public CourseResourceDocModel addDoc(CourseResourceDocModel entity){
    return courseResourceDocDao.addEntity(entity);
  }

  /**
   * 新增文档
   * @param request
   * @return
   */
  public CourseResourceDocModel addDoc(ResourceDocCreateRequest request){
    CourseResourceDocModel courseResourceDocModel = new CourseResourceDocModel();
    courseResourceDocModel.setDetail(request.getDetail());
    courseResourceDocModel.setName(request.getName());
    courseResourceDocModel.setChapterId(request.getChapterId());
    courseResourceDocModel.setUrl(request.getUrl());
    courseResourceDocModel.setFileUrl(request.getFileUrl());
    courseResourceDocModel.setCourseId(request.getCourseId());
    CourseResourceDocModel result = this.addDoc(courseResourceDocModel);
    return result;
  }

  /**
   * 删除文档
   * @param id
   * @return
   */
  public Boolean deleteResourceDoc(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return courseResourceDocDao.deleteEntity(cond);
  }




  /**
   * 根据主键查询
   * @param catId
   * @return
   */
  public CourseResourceDocModel findResourceDocById(Integer catId){
    CourseResourceDocModel entity = new CourseResourceDocModel();
    entity.setId(catId);
    return courseResourceDocDao.findEntityById(entity);
  }
}
