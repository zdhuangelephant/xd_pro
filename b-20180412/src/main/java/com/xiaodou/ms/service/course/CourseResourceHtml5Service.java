package com.xiaodou.ms.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.course.CourseResourceHtml5Dao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.course.ResourceHtml5CreateRequest;
import com.xiaodou.ms.web.request.course.ResourceHtml5EditRequest;
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

  @Resource
  ProductItemService productItemService;

  /**
   * 更新关键词列表
   * @param resourceId
   * @param keywordList
   * @return
   */
  public Boolean updateKeyPoint(Long resourceId,List<CourseKeywordModel> keywordList){
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
  public Page<CourseResourceHtml5Model> cascadeQueryHtml5ByChapterId(Integer pageNo, Long courseId,Long chapterId){
    Page<CourseResourceHtml5Model> page = new Page<CourseResourceHtml5Model>();
    if(pageNo == null) { 
      page = null;
    }else {
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    List<CourseChapterModel> courseChapterModels = courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel:courseChapterModels){
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    Page<CourseResourceHtml5Model> courseResourceHtml5ModelPage =
      courseResourceHtml5Dao.cascadeQueryHtml5(cond, page);
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
   * 更新文档
   * @param entity
   * @return
   */
  public Boolean editHtml5(CourseResourceHtml5Model entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    ResourceDescription description = new ResourceDescription();
    description.setUrl(entity.getUrl());
    description.setFileUrl(entity.getFileUrl());
    productItemService.updateItemResource(entity.getId(),ResourceType.HTML5.getTypeId(),JSON.toJSONString(description));
    return courseResourceHtml5Dao.updateEntity(cond, entity);
  }

  /**
   * 更新文档
   * @param request
   * @return
   */
  public Boolean editHtml5(ResourceHtml5EditRequest request){
    CourseResourceHtml5Model courseResourceHtml5Model = new CourseResourceHtml5Model();
    courseResourceHtml5Model.setId(request.getId());
    courseResourceHtml5Model.setUrl(request.getUrl());
    courseResourceHtml5Model.setName(request.getName());
    courseResourceHtml5Model.setChapterId(request.getChapterId());
    courseResourceHtml5Model.setDetail(request.getDetail());
    courseResourceHtml5Model.setFileUrl(request.getFileUrl());
    return this.editHtml5(courseResourceHtml5Model);
  }

  /**
   * 根据主键查询
   * @param id
   * @return
   */
  public CourseResourceHtml5Model findResourceHtml5ById(Long id){
    CourseResourceHtml5Model entity = new CourseResourceHtml5Model();
    entity.setId(id);
    return courseResourceHtml5Dao.findEntityById(entity);
  }
  
  /**
   * 根据chapterId查询
   * @param id
   * @return
   */
  public List<CourseResourceHtml5Model> findAllResourceHtml5ById(Long itemId){
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
    List<CourseResourceHtml5Model> list = courseResourceHtml5Dao
        .queryListByCond0(cond, output, null).getResult();
    
    return list;
  }
  
}
