package com.xiaodou.mooccrawler.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.mooccrawler.common.ResourceType;
import com.xiaodou.mooccrawler.dao.course.CourseResourceVideoDao;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceVideoModel;
import com.xiaodou.mooccrawler.web.request.ResourceVideoCreateRequest;
import com.xiaodou.mooccrawler.web.request.ResourceVideoEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceVideoService")
public class CourseResourceVideoService {

  @Resource
  CourseResourceVideoDao courseResourceVideoDao;

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
    CourseResourceVideoModel videoModel = new CourseResourceVideoModel();
    videoModel.setKeyPoint(JSON.toJSONString(keywordList));
    return courseResourceVideoDao.updateEntity(cond,videoModel);
  }

  /**
   * 级联获取文档列表
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceVideoModel> cascadeQueryVideoByChapterId(Integer courseId,Integer chapterId){
    List<CourseChapterModel> courseChapterModels = courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Integer> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel:courseChapterModels){
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    Page<CourseResourceVideoModel> courseResourceVideoModelPage =
      courseResourceVideoDao.cascadeQueryVideo(cond);
    return courseResourceVideoModelPage;
  }

  /**
   * 新增文档
   * @param entity
   * @return
   */
  public CourseResourceVideoModel addVideo(CourseResourceVideoModel entity){
    return courseResourceVideoDao.addEntity(entity);
  }

  /**
   * 新增文档
   * @param request
   * @return
   */
  public CourseResourceVideoModel addVideo(ResourceVideoCreateRequest request){
    CourseResourceVideoModel courseResourceVideoModel = new CourseResourceVideoModel();
    courseResourceVideoModel.setDetail(request.getDetail());
    courseResourceVideoModel.setName(request.getName());
    courseResourceVideoModel.setChapterId(request.getChapterId());
    courseResourceVideoModel.setUrl(request.getUrl());
    courseResourceVideoModel.setFileUrl(request.getFileUrl());
    courseResourceVideoModel.setCourseId(request.getCourseId());
    CourseResourceVideoModel result = this.addVideo(courseResourceVideoModel);
    return result;
  }

  /**
   * 删除文档
   * @param id
   * @return
   */
  public Boolean deleteResourceVideo(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return courseResourceVideoDao.deleteEntity(cond);
  }

  /**
   * 根据主键查询
   * @param id
   * @return
   */
  public CourseResourceVideoModel findResourceVideoById(Integer id){
    CourseResourceVideoModel entity = new CourseResourceVideoModel();
    entity.setId(id);
    return courseResourceVideoDao.findEntityById(entity);
  }
}
