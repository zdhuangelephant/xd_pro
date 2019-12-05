package com.xiaodou.mooccrawler.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.mooccrawler.dao.course.CourseKeywordDao;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseKeywordModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseKeywordService")
public class CourseKeywordService {

  @Resource
  CourseKeywordDao courseKeywordDao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  /**
   * 根据栏目级联查询课程
   * @param chapterId
   * @param courseId
   * @return
   */
  public Page<CourseKeywordModel> cascadeQueryKeywordByChapter(Integer courseId,Integer chapterId){
    List<CourseChapterModel> courseChapterModels =
      courseChapterService.queryAllChildChapter(courseId,chapterId);
    List<Integer> ids = new ArrayList<>();
    for (CourseChapterModel chapterModel:courseChapterModels){
      ids.add(chapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    return courseKeywordDao.cascadeQueryKeywords(cond);
  }

  /**
   * 查询关键词列表
   * @param ids
   * @return
   */
  public List<CourseKeywordModel> queryKeywordsByIds(List<Integer> ids){
    Map<String,Object> cond = new HashMap<>();
    cond.put("ids",ids);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("name","");
    output.put("detail","");
    output.put("importanceLevel","");
    Page<CourseKeywordModel> courseKeywordModelPage =
      courseKeywordDao.queryListByCondWithOutPage(cond, output);
    return courseKeywordModelPage.getResult();
  }

  /**
   * 新增视频
   * @param entity
   * @return
   */
  public CourseKeywordModel addKeyword(CourseKeywordModel entity){
    return courseKeywordDao.addEntity(entity);
  }

  /**
   * 删除视频
   * @param id
   * @return
   */
  public Boolean deleteKeyword(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    courseKeywordResourceService.deleteRelationBykeywordId(id);
    return courseKeywordDao.deleteEntity(cond);
  }

  /**
   * 更新视频
   * @param entity
   * @return
   */
  public Boolean editKeyword(CourseKeywordModel entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    return courseKeywordDao.updateEntity(cond,entity);
  }

  /**
   * 根据主键查询
   * @param id
   * @return
   */
  public CourseKeywordModel findKeywordById(Integer id){
    CourseKeywordModel entity = new CourseKeywordModel();
    entity.setId(id);
    return courseKeywordDao.findEntityById(entity);
  }

}
