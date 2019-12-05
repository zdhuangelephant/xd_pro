package com.xiaodou.ms.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.course.CourseMicroVideoDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceMicroVideoModel;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.course.ResourceMicroVideoCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceMicroVideoEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseMicroVideoService")
public class CourseMicroVideoService {

  @Resource
  CourseMicroVideoDao courseMicroVideoDao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  @Resource
  ProductItemService productItemService;

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
    CourseResourceMicroVideoModel videoModel = new CourseResourceMicroVideoModel();
    videoModel.setKeyPoint(JSON.toJSONString(keywordList));
    return courseMicroVideoDao.updateEntity(cond, videoModel);
  }

  /**
   * 级联获取文档列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceMicroVideoModel> cascadeQueryMicroVideoByChapterId(Integer pageNo,
      Long courseId, Long chapterId) {
    Page<CourseResourceMicroVideoModel> page = new Page<CourseResourceMicroVideoModel>();
    if (pageNo == null) {
      page = null;
    } else {
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    List<CourseChapterModel> courseChapterModels =
        courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel : courseChapterModels) {
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String, Object> input = new HashMap<>();
    input.put("chapterIds", ids);

    Page<CourseResourceMicroVideoModel> courseResourceAudioModelPage =
        courseMicroVideoDao.cascadeQueryMicroVideo(input, page);
    return courseResourceAudioModelPage;
  }

  /**
   * 新增文档
   * 
   * @param entity
   * @return
   */
  public CourseResourceMicroVideoModel addMicroVideo(CourseResourceMicroVideoModel entity) {
    return courseMicroVideoDao.addEntity(entity);
  }

  /**
   * 新增文档
   * 
   * @param request
   * @return
   */
  public CourseResourceMicroVideoModel addMicroVideo(ResourceMicroVideoCreateRequest request) {
    CourseResourceMicroVideoModel courseResourceAudioModel = new CourseResourceMicroVideoModel();
    courseResourceAudioModel.setDetail(request.getDetail());
    courseResourceAudioModel.setName(request.getName());
    courseResourceAudioModel.setChapterId(request.getChapterId());
    courseResourceAudioModel.setUrl(request.getUrl());
    courseResourceAudioModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceAudioModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceAudioModel.setSize(request.getSize());
    courseResourceAudioModel.setCourseId(request.getCourseId());
    courseResourceAudioModel.setVideoCover(request.getVideoCover());
    courseResourceAudioModel.setListOrder(request.getListOrder());
    CourseResourceMicroVideoModel result = this.addMicroVideo(courseResourceAudioModel);
    return result;
  }


  /**
   * 更新文档
   * 
   * @param request
   * @return
   */
  public Boolean editMicroVideo(ResourceMicroVideoEditRequest request) {
    CourseResourceMicroVideoModel courseResourceAudioModel = new CourseResourceMicroVideoModel();
    courseResourceAudioModel.setId(request.getId());
    courseResourceAudioModel.setUrl(request.getUrl());
    courseResourceAudioModel.setName(request.getName());
    courseResourceAudioModel.setChapterId(request.getChapterId());
    courseResourceAudioModel.setDetail(request.getDetail());
    courseResourceAudioModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceAudioModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceAudioModel.setSize(request.getSize());
    courseResourceAudioModel.setVideoCover(request.getVideoCover());
    courseResourceAudioModel.setListOrder(request.getListOrder());
    return this.editMicroVideo(courseResourceAudioModel);
  }
  public Boolean editMicroVideo(CourseResourceMicroVideoModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    ResourceDescription description = new ResourceDescription();
    description.setUrl(entity.getUrl());
    description.setTimeLengthMinute(entity.getTimeLengthMinute());
    description.setTimeLengthSecond(entity.getTimeLengthSecond());
    description.setSize(entity.getSize());
    productItemService.updateItemResource(entity.getId(), ResourceType.MICROVIDEO.getTypeId(),
        JSON.toJSONString(description));
    return courseMicroVideoDao.updateEntity(cond, entity);
  }
  

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public CourseResourceMicroVideoModel findResourceMicroVideoById(Long id) {
    CourseResourceMicroVideoModel entity = new CourseResourceMicroVideoModel();
    entity.setId(id);
    return courseMicroVideoDao.findEntityById(entity);
  }


  /**
   * 根据主键查询
   * 
   * @param itemId
   * @param i
   * @return
   */
  public CourseResourceMicroVideoModel findFirstResourceMicroVideoById(Long itemId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterId", itemId);
    Map<String, Object> output = CommUtil.getAllField(CourseResourceMicroVideoModel.class);
    List<CourseResourceMicroVideoModel> list =
        courseMicroVideoDao.queryListByCond0(cond, output, null).getResult();
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 根据chapterId查询
   * 
   * @return
   */
  public List<CourseResourceMicroVideoModel> findAllResourceByChapterId(Long itemId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterId", itemId);
    Map<String, Object> output = CommUtil.getAllField(CourseResourceMicroVideoModel.class);
    List<CourseResourceMicroVideoModel> list =
        courseMicroVideoDao.queryListByCond0(cond, output, null).getResult();

    return list;
  }
  /**
   * 删除文档
   * 
   * @param id
   * @return
   */
  public Boolean deleteResourceMicroVideo(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return courseMicroVideoDao.deleteEntity(cond);
  }
}
