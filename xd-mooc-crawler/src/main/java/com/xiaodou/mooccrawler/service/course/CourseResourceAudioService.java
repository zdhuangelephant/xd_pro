package com.xiaodou.mooccrawler.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.mooccrawler.common.ResourceType;
import com.xiaodou.mooccrawler.dao.course.CourseResourceAudioDao;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceAudioModel;
import com.xiaodou.mooccrawler.web.request.ResourceAudioCreateRequest;
import com.xiaodou.mooccrawler.web.request.ResourceAudioEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceAudioService")
public class CourseResourceAudioService {

  @Resource
  CourseResourceAudioDao courseResourceAudioDao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;


  /**
   * 更新关键词列表
   * 
   * @param resourceId
   * @param keywordList
   * @return
   */
  public Boolean updateKeyPoint(Integer resourceId, List<CourseKeywordModel> keywordList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", resourceId);
    CourseResourceAudioModel videoModel = new CourseResourceAudioModel();
    videoModel.setKeyPoint(JSON.toJSONString(keywordList));
    return courseResourceAudioDao.updateEntity(cond, videoModel);
  }

  /**
   * 级联获取文档列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceAudioModel> cascadeQueryAudioByChapterId(Integer courseId,
      Integer chapterId) {
    List<CourseChapterModel> courseChapterModels =
        courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Integer> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel : courseChapterModels) {
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterIds", ids);
    Page<CourseResourceAudioModel> courseResourceAudioModelPage =
        courseResourceAudioDao.cascadeQueryAudio(cond);
    return courseResourceAudioModelPage;
  }

  /**
   * 新增文档
   * 
   * @param entity
   * @return
   */
  public CourseResourceAudioModel addAudio(CourseResourceAudioModel entity) {
    return courseResourceAudioDao.addEntity(entity);
  }

  /**
   * 新增文档
   * 
   * @param request
   * @return
   */
  public CourseResourceAudioModel addAudio(ResourceAudioCreateRequest request) {
    CourseResourceAudioModel courseResourceAudioModel = new CourseResourceAudioModel();
    courseResourceAudioModel.setDetail(request.getDetail());
    courseResourceAudioModel.setName(request.getName());
    courseResourceAudioModel.setChapterId(request.getChapterId());
    courseResourceAudioModel.setUrl(request.getUrl());
    courseResourceAudioModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceAudioModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceAudioModel.setSize(request.getSize());
    courseResourceAudioModel.setCourseId(request.getCourseId());
    CourseResourceAudioModel result = this.addAudio(courseResourceAudioModel);
    return result;
  }

  /**
   * 删除文档
   * 
   * @param id
   * @return
   */
  public Boolean deleteResourceAudio(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return courseResourceAudioDao.deleteEntity(cond);
  }


  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public CourseResourceAudioModel findResourceAudioById(Integer id) {
    CourseResourceAudioModel entity = new CourseResourceAudioModel();
    entity.setId(id);
    return courseResourceAudioDao.findEntityById(entity);
  }
}
