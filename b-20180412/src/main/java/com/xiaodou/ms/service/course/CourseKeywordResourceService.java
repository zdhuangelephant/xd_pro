package com.xiaodou.ms.service.course;

import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.course.CourseKeywordResourceDao;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseKeywordResourceModel;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseKeywordResourceService")
public class CourseKeywordResourceService {

  @Resource
  CourseKeywordResourceDao courseKeywordResourceDao;

  @Resource
  CourseKeywordService courseKeywordService;

  //视频资源service
  @Resource
  CourseResourceVideoService courseResourceVideoService;

  //html5 service
  @Resource
  CourseResourceHtml5Service courseResourceHtml5Service;

  //doc service
  @Resource
  CourseResourceDocService courseResourceDocService;

  // 题库service
  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  /**
   * 更新关键词
   * @param resourceId
   * @param resourceType
   * @return
   */
  public Boolean updateResourceKeyPoint(Long resourceId,Integer resourceType){

    Map<String,Object> cond = new HashMap<>();
    cond.put("resourceId",resourceId);
    cond.put("resourceType",resourceType);
    Map<String,Object> output = new HashMap<>();
    output.put("keywordId","");
    Page<CourseKeywordResourceModel> keywordResourceList =
      courseKeywordResourceDao.queryListByCond0(cond, output, null);
    List<Long> keywordIds = new ArrayList<>();
    for (CourseKeywordResourceModel keywordResourceModel:keywordResourceList.getResult()){
      keywordIds.add(keywordResourceModel.getKeywordId());
    }
    List<CourseKeywordModel> keywordList = new ArrayList<>();
    if (keywordIds!=null&&keywordIds.size()>0) {
      keywordList = courseKeywordService.queryKeywordsByIds(keywordIds);
    }

    if (ResourceType.QUESTION.getTypeId().equals(resourceType)){
      return questionBankQuestionService.updateKeyPoint(resourceId,keywordList);
    }
    if (ResourceType.VIDEO.getTypeId().equals(resourceType)){
      return courseResourceVideoService.updateKeyPoint(resourceId,keywordList);
    }
    if (ResourceType.HTML5.getTypeId().equals(resourceType)){
      return courseResourceHtml5Service.updateKeyPoint(resourceId,keywordList);
    }
    if (ResourceType.DOC.getTypeId().equals(resourceType)){
      return courseResourceDocService.updateKeyPoint(resourceId,keywordList);
    }
    return true;
  }

  /**
   * 增加资源关联
   * @param resourceId
   * @param resourceType
   * @param keywordIds
   * @return
   */
  public void addKeywordResource(Long resourceId,ResourceType resourceType,List<Long> keywordIds){
    for (Long keywordId:keywordIds){
      CourseKeywordResourceModel keywordResourceModel = new CourseKeywordResourceModel();
      keywordResourceModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      keywordResourceModel.setKeywordId(keywordId);
      keywordResourceModel.setResourceId(resourceId);
      keywordResourceModel.setResourceType(resourceType.getTypeId());
      courseKeywordResourceDao.addEntity(keywordResourceModel);
    }
  }

  /**
   * 增加关联关系
   * @param resourceId
   * @param resourceType
   * @param keywordId
   */
  public void addKeywordResource(Long resourceId,Integer resourceType,Long keywordId){
      CourseKeywordResourceModel keywordResourceModel = new CourseKeywordResourceModel();
      keywordResourceModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      keywordResourceModel.setKeywordId(keywordId);
      keywordResourceModel.setResourceId(resourceId);
      keywordResourceModel.setResourceType(resourceType);
      courseKeywordResourceDao.addEntity(keywordResourceModel);
  }

  /**
   * 编辑关联资源
   * @param resourceId
   * @param resourceType
   * @param keywordIds
   */
  public void editKeywordResource(Long resourceId,ResourceType resourceType,List<Long> keywordIds){
    this.deleteRelationByResourceId(resourceId,resourceType);
    this.addKeywordResource(resourceId,resourceType,keywordIds);
  }

  /**
   * 删除关联关系
   * @param resourceId
   * @param resourceType
   * @return
   */
  public Boolean deleteRelationByResourceId(Long resourceId,ResourceType resourceType){
    Map<String,Object> cond = new HashMap<>();
    cond.put("resourceId",resourceId);
    cond.put("resourceType",resourceType.getTypeId());
    return courseKeywordResourceDao.deleteEntity(cond);
  }

  /**
   * 删除关联关系
   * @param resourceId
   * @param resourceType
   * @return
   */
  public Boolean deleteRelation(Long resourceId,Integer resourceType,Long keywordId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("resourceId",resourceId);
    cond.put("resourceType",resourceType);
    cond.put("keywordId",keywordId);
    return courseKeywordResourceDao.deleteEntity(cond);
  }

  /**
   * 删除关联关系
   * @param keywordId
   * @return
   */
  public Boolean deleteRelationBykeywordId(Integer keywordId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("keywordId",keywordId);
    return courseKeywordResourceDao.deleteEntity(cond);
  }

  /**
   * 根据关键词查询关系
   * @param keywordId
   * @return
   */
  public List<CourseKeywordResourceModel> queryRelationByKeywordId(Integer keywordId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("keywordId",keywordId);
    Map<String,Object> output = new HashMap<>();
    output.put("resourceId",1);
    output.put("resourceType",1);
    output.put("keywordId",1);
    Page<CourseKeywordResourceModel> courseKeywordResourceModelPage =
      courseKeywordResourceDao.queryListByCond0(cond, output, null);
    return courseKeywordResourceModelPage.getResult();
  }

  /**
   * 根据关键词查询关系
   * @param resourceId
   * @return
   */
  public List<CourseKeywordResourceModel> queryRelationByResourceId(Long resourceId,Integer resourceType){
    Map<String,Object> cond = new HashMap<>();
    cond.put("resourceId",resourceId);
    cond.put("resourceType",resourceType);
    Map<String,Object> output = new HashMap<>();
    output.put("resourceId",1);
    output.put("resourceType",1);
    output.put("keywordId",1);
    Page<CourseKeywordResourceModel> courseKeywordResourceModelPage =
      courseKeywordResourceDao.queryListByCond0(cond, output, null);
    return courseKeywordResourceModelPage.getResult();
  }

  public CourseKeywordResourceModel addKeywordResource(CourseKeywordResourceModel ckrm) {
    return courseKeywordResourceDao.addEntity(ckrm);
  }

}
