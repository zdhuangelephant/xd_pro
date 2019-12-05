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
import com.xiaodou.ms.dao.course.CourseResourceAudioDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.course.ResourceAudioCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceAudioEditRequest;
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
  public Page<CourseResourceAudioModel> cascadeQueryAudioByChapterId(Integer pageNo, Long courseId,Long chapterId) {
    Page<CourseResourceAudioModel> page = new Page<CourseResourceAudioModel>();
    if(pageNo == null) { 
      page = null;
    }else {
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
    
    Page<CourseResourceAudioModel> courseResourceAudioModelPage =
        courseResourceAudioDao.cascadeQueryAudio(input, page);
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
   * 更新文档
   * 
   * @param entity
   * @return
   */
  public Boolean editAudio(CourseResourceAudioModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    ResourceDescription description = new ResourceDescription();
    description.setUrl(entity.getUrl());
    description.setTimeLengthMinute(entity.getTimeLengthMinute());
    description.setTimeLengthSecond(entity.getTimeLengthSecond());
    description.setSize(entity.getSize());
    productItemService.updateItemResource(entity.getId(), ResourceType.AUDIO.getTypeId(),
        JSON.toJSONString(description));
    return courseResourceAudioDao.updateEntity(cond, entity);
  }

  /**
   * 更新文档
   * 
   * @param request
   * @return
   */
  public Boolean editAudio(ResourceAudioEditRequest request) {
    CourseResourceAudioModel courseResourceAudioModel = new CourseResourceAudioModel();
    courseResourceAudioModel.setId(request.getId());
    courseResourceAudioModel.setUrl(request.getUrl());
    courseResourceAudioModel.setName(request.getName());
    courseResourceAudioModel.setChapterId(request.getChapterId());
    courseResourceAudioModel.setDetail(request.getDetail());
    courseResourceAudioModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceAudioModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceAudioModel.setSize(request.getSize());
    return this.editAudio(courseResourceAudioModel);
  }

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public CourseResourceAudioModel findResourceAudioById(Long id) {
    CourseResourceAudioModel entity = new CourseResourceAudioModel();
    entity.setId(id);
    return courseResourceAudioDao.findEntityById(entity);
  }
  
  
  /**
   * 根据主键查询
   * @param itemId
 * @param i 
   * @return
   */
  public CourseResourceAudioModel findFirstResourceAudioById(Long itemId){
    Map<String, Object> cond = new HashMap<>();
	cond.put("chapterId", itemId);
	Map<String, Object> output = new HashMap<>();
	output.put("id", "");
	output.put("courseId", "");
	output.put("chapterId", "");
	output.put("name", "");
	output.put("url", "");
	output.put("timeLengthMinute", "");
	output.put("timeLengthSecond", "");
	output.put("size", "");
	output.put("detail", "");
	output.put("keyPoint", "");
	output.put("chapterName", "");
	output.put("status", "");
	List <CourseResourceAudioModel> list= courseResourceAudioDao.queryListByCond0(cond, output, null).getResult();
    if(list!=null&&list.size()>0){
    	return list.get(0);
    }
    return null;
  }

  /**
   * 根据chapterId查询
   * @return
   */
  public List<CourseResourceAudioModel> findAllResourceByChapterId(Long itemId){
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterId", itemId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("courseId", "");
    output.put("chapterId", "");
    output.put("name", "");
    output.put("url", "");
    output.put("fileUrl", "");
    output.put("detail", "");
    output.put("keyPoint", "");
    output.put("chapterName", "");
    output.put("status", "");
    output.put("timeLengthMinute", "");
    output.put("timeLengthSecond", "");
    output.put("size", "");
    List<CourseResourceAudioModel> list = courseResourceAudioDao
        .queryListByCond0(cond, output, null).getResult();
    
    return list;
  }
}
