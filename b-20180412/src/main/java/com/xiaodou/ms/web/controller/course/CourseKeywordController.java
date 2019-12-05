package com.xiaodou.ms.web.controller.course;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseKeywordResourceModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordResourceService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/7/18.
 */
@Controller("courseKeywordController")
@RequestMapping("/courseKeyword")
public class CourseKeywordController extends BaseController {

  //课程章节
  @Resource
  CourseChapterService courseChapterService;

  //课程关键词
  @Resource
  CourseKeywordService courseKeywordService;

  //资源关键词
  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  /**
   * 关键词map
   * @param courseId
   * @param resourceId
   * @return
   */
  @RequestMapping("/keywordsMap")
  public ModelAndView keywordsMap(Long courseId,Long chapterId,Long resourceId,Integer resourceType){
    ModelAndView modelAndView = new ModelAndView("/course/keywordsMap");
    if (chapterId==null){
      chapterId = 0L;
    }
    List<CourseKeywordResourceModel> courseKeywordResourceModels =
      courseKeywordResourceService.queryRelationByResourceId(resourceId, resourceType);
    Map<Long,CourseKeywordResourceModel> selectMap = new HashMap<>();
    for (CourseKeywordResourceModel keywordResourceModel:courseKeywordResourceModels){
      selectMap.put(keywordResourceModel.getKeywordId(),keywordResourceModel);
    }

    Page<CourseKeywordModel> courseKeywordModelPage =
      courseKeywordService.cascadeQueryKeywordByChapter(courseId, chapterId);
    List<CourseKeywordModel> keywordsList = courseKeywordModelPage.getResult();
    for (CourseKeywordModel courseKeywordModel:keywordsList){
      if (selectMap.containsKey(courseKeywordModel.getId())){
        courseKeywordModel.setIsSelected(1);
      }
    }
    modelAndView.addObject("keywordsList",keywordsList);

    String parentTemplate =
      "<a target=\"listframe\" onclick=\"showChapterKeywords(${node.data.subjectId},${node.id},this)\" style=\"cursor: pointer;\" class=\"folder\">${node.data.sindex}[${node.name}]</a>";
    String childTemplate =
      "<a target=\"listframe\" onclick=\"showChapterKeywords(${node.data.subjectId},${node.id},this)\" style=\"cursor: pointer;\" class=\"file\">${node.data.sindex}[${node.name}]</a>";
    String chapterTree = courseChapterService.ChapterJqueryTree(courseId, 0L, parentTemplate, childTemplate);
    modelAndView.addObject("chapterTree",chapterTree);

    if (chapterId!=0){
      CourseChapterModel courseChapter = courseChapterService.findChapterById(chapterId);
      modelAndView.addObject("courseChapter",courseChapter);
    }
    modelAndView.addObject("courseId",courseId);
    modelAndView.addObject("chapterId",chapterId);
    modelAndView.addObject("resourceId",resourceId);
    modelAndView.addObject("resourceType",resourceType);
    return modelAndView;
  }

  /**
   * 资源课程章节知识点
   *
   * @return
   */
  @RequestMapping("/keywordsList")
  public ModelAndView resourceCourseChapterKeywords(Long courseId,Long chapterId){
    ModelAndView modelAndView = new ModelAndView("/course/keywordList");
    if (chapterId==null){
      chapterId = 0L;
    }
    Page<CourseKeywordModel> keywordList =
      courseKeywordService.cascadeQueryKeywordByChapter(courseId, chapterId);
    String chapterTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId.toString());
    modelAndView.addObject("chapterTree",chapterTree);
    modelAndView.addObject("keywordList",keywordList.getResult());
    modelAndView.addObject("courseId",courseId);
    modelAndView.addObject("chapterId",chapterId);
    return modelAndView;
  }

  /**
   * 知识点列表
   * @param courseId
   * @param chapterId
   * @return
   */
  @RequestMapping("/ajax_keywordsList")
  @ResponseBody
  public String ajax_keywordsList(Long courseId,Long chapterId,Long resourceId, Integer resourceType){

    List<CourseKeywordResourceModel> courseKeywordResourceModels =
      courseKeywordResourceService.queryRelationByResourceId(resourceId, resourceType);
    Map<Long,CourseKeywordResourceModel> selectMap = new HashMap<>();
    for (CourseKeywordResourceModel keywordResourceModel:courseKeywordResourceModels){
      selectMap.put(keywordResourceModel.getKeywordId(),keywordResourceModel);
    }

    Page<CourseKeywordModel> courseKeywordModelPage =
      courseKeywordService.cascadeQueryKeywordByChapter(courseId, chapterId);
    List<CourseKeywordModel> keywordsList = courseKeywordModelPage.getResult();
    for (CourseKeywordModel courseKeywordModel:keywordsList){
      if (selectMap.containsKey(courseKeywordModel.getId())){
        courseKeywordModel.setIsSelected(1);
      }
    }
    return JSON.toJSONString(keywordsList);
  }

  /**
   * 批量添加
   * @param courseId
   * @param chapterId
   * @return
   */
  @RequestMapping("/batchAdd")
  public ModelAndView batchAdd(Long courseId,Long chapterId){
    ModelAndView modelAndView = new ModelAndView("/course/keywordBatchAdd");
    String chapterTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId.toString());
    modelAndView.addObject("chapterId",chapterId);
    modelAndView.addObject("chapterTree",chapterTree);
    modelAndView.addObject("courseId",courseId);
    return modelAndView;
  }

  /**
   * 资源课程章节知识点添加
   *
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView add(Long courseId,Long chapterId){
    ModelAndView modelAndView = new ModelAndView("/course/keywordAdd");
    String chapterTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId.toString());
    modelAndView.addObject("chapterId",chapterId);
    modelAndView.addObject("chapterTree",chapterTree);
    return modelAndView;
  }

  /**
   * 增加关系
   * @param keywordId
   * @param resourceId
   * @param resourceType
   * @return
   */
  @RequestMapping("/addRelation")
  @ResponseBody
  public String addRelation(Long keywordId,Long resourceId,Integer resourceType){
    courseKeywordResourceService.addKeywordResource(resourceId,resourceType,keywordId);
    courseKeywordResourceService.updateResourceKeyPoint(resourceId,resourceType);
    return "true";
  }

  /**
   * 删除关系
   * @param keywordId
   * @param resourceId
   * @param resourceType
   * @return
   */
  @RequestMapping("/deleteRelation")
  @ResponseBody
  public String deleteRelation(Long keywordId,Long resourceId,Integer resourceType){
    courseKeywordResourceService.deleteRelation(resourceId,resourceType,keywordId);
    courseKeywordResourceService.updateResourceKeyPoint(resourceId,resourceType);
    return "true";
  }

  //批量添加
  @RequestMapping("batchDoAdd")
  public ModelAndView batchDoAdd(Long chapterId,HttpServletRequest request){
    for (int i=1;i<=10;i++){
      if (StringUtils.isNotBlank(request.getParameter("name"+i))){
        CourseKeywordModel keywordModel = new CourseKeywordModel();
        keywordModel.setChapterId(chapterId);
        keywordModel.setName(request.getParameter("name"+i));
        keywordModel.setDetail(request.getParameter("detail"+i));
        keywordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        keywordModel.setImportanceLevel(Integer.parseInt(request.getParameter("importanceLevel"+i)));
        courseKeywordService.addKeyword(keywordModel);
      }
    }
    return this.showMessage("添加成功",null,null,true);
  }

  /**
   * 执行添加
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView doAdd(Long chapterId,Long courseId,String name,String detail,Integer importanceLevel){
    CourseKeywordModel keywordModel = new CourseKeywordModel();
    keywordModel.setChapterId(chapterId);
    keywordModel.setName(name);
    keywordModel.setDetail(detail);
    keywordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    keywordModel.setImportanceLevel(importanceLevel);
    courseKeywordService.addKeyword(keywordModel);
    return this.showMessage("添加成功",null,null,true);
  }

  @RequestMapping("/edit")
  public ModelAndView edit(Long keywordId,Long courseId){
    ModelAndView modelAndView = new ModelAndView("/course/keywordEdit");
    CourseKeywordModel keyword = courseKeywordService.findKeywordById(keywordId);
    String chapterTree = courseChapterService.ChapterSelectTree(courseId, 0L, keyword.getChapterId().toString());
    modelAndView.addObject("keyword",keyword);
    modelAndView.addObject("chapterTree",chapterTree);
    return modelAndView;
  }

  /**
   * 资源课程章节修改
   *
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView doEdit(Long id,String name,String detail,Long chapterId,Integer importanceLevel){
    CourseKeywordModel courseKeywordModel = new CourseKeywordModel();
    courseKeywordModel.setId(id);
    courseKeywordModel.setName(name);
    courseKeywordModel.setChapterId(chapterId);
    courseKeywordModel.setDetail(detail);
    courseKeywordModel.setImportanceLevel(importanceLevel);
    courseKeywordService.editKeyword(courseKeywordModel);
    return this.showMessage("修改成功",null,null,true);
  }


  /**
   * 资源课程章节删除
   *
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String keywordDelete(Integer id){
    try {
      Boolean aBoolean = courseKeywordService.deleteKeyword(id);
      if (aBoolean){
        return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
      } else {
        return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
      }
    } catch (Exception e){
      throw e;
    }
  }
}
