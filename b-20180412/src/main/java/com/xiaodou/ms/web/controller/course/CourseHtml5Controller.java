package com.xiaodou.ms.web.controller.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.dao.course.CourseResourceHtml5Dao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.service.course.CourseResourceHtml5Service;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.ResourceHtml5CreateRequest;
import com.xiaodou.ms.web.request.course.ResourceHtml5EditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */

@Controller("courseHtml5Controller")
@RequestMapping("/courseHtml5")
public class CourseHtml5Controller extends BaseController {

  // 资源
  @Resource
  CourseResourceHtml5Service courseResourceHtml5Service;

  @Resource
  CourseResourceHtml5Dao courseResourceHtml5Dao;

  // 章节
  @Resource
  CourseChapterService courseChapterService;

  // 课程
  @Resource
  CourseSubjectService courseSubjectService;

  // 课程分类
  @Resource
  CourseCategoryService courseCategoryService;

  @Resource
  CourseKeywordService courseKeywordService;

  /**
   * 文档资源
   *
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView resourceHtml5(Integer page,Long courseId, Long chapterId) {
    try {
      if (page == null || page == 0) {
        page = 1;
      }
      if (chapterId == null) {
        chapterId = 0L;
      }
      ModelAndView modelAndView = new ModelAndView("/course/html5List");
      String tableTree = courseChapterService.ChapterJqueryTree(courseId, 0L);
      modelAndView.addObject("tableTree", tableTree);
      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
      if(null==courseId){
    	  return modelAndView;
      }
      Page<CourseResourceHtml5Model> courseResourceHtml5ModelPage =
          courseResourceHtml5Service.cascadeQueryHtml5ByChapterId(page, courseId, chapterId);
      
      CourseChapterModel chapter = courseChapterService.findChapterById(chapterId);
      modelAndView.addObject("chapter", chapter);
     
      modelAndView.addObject("pageList", courseResourceHtml5ModelPage);
      modelAndView.addObject("chapterId", chapterId);
      modelAndView.addObject("courseId", courseId);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 文档资源添加
   *
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceHtml5Add(Long courseId, Long chapterId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/html5Add");
      Page<CourseKeywordModel> courseKeywordModelPage =
          courseKeywordService.cascadeQueryKeywordByChapter(courseId, chapterId);
      String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId + "");
      modelAndView.addObject("selectTree", selectTree);
      modelAndView.addObject("keywordList", courseKeywordModelPage.getResult());
      modelAndView.addObject("courseId", courseId);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 新增文档
   * 
   * @param errors
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceHtml5DoAdd(ResourceHtml5CreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        CourseResourceHtml5Model result = courseResourceHtml5Service.addHtml5(request);
        Map<String, Long> cond = new HashMap<>();
        cond.put("id", result.getId());
        CourseResourceHtml5Model html5Model = new CourseResourceHtml5Model();
        html5Model.setStatus(99);
        courseResourceHtml5Dao.updateEntity(cond, html5Model);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", "", true);
      } else {
        return this.showMessage("添加失败", "", "", true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 文档资源编辑
   *
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceHtml5Edit(Long courseId, Long html5Id) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/html5Edit");
      CourseResourceHtml5Model resourceHtml5 =
          courseResourceHtml5Service.findResourceHtml5ById(html5Id);
      String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L,
          resourceHtml5.getChapterId().toString());
      modelAndView.addObject("courseHtml5", resourceHtml5);
      modelAndView.addObject("selectTree", selectTree);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 编辑文档
   * 
   * @param errors
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceHtml5DoEdit(ResourceHtml5EditRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseResourceHtml5Service.editHtml5(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", "", true);
      } else {
        return this.showMessage("添加失败", "", "", true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 文档资源删除
   *
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String resourceHtml5Del(Integer id) {
    try {
      Boolean aBoolean = courseResourceHtml5Service.deleteResourceHtml5(id);
      if (aBoolean) {
        return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
      } else {
        return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 审核通过.因需求变更，此部分代码不再使用，lirui注释于2018.07.27
   * 
   * @param ids
   * @return
   */
//  @RequestMapping("/verify")
//  @ResponseBody
//  public String verify(String ids) {
//    String[] idArray = ids.split(";");
//    List<Integer> idList = new ArrayList<>();
//    for (String id : idArray) {
//      idList.add(Integer.parseInt(id));
//    }
//    Map<String, Object> cond = new HashMap<>();
//    cond.put("ids", idList);
//    CourseResourceHtml5Model html5Model = new CourseResourceHtml5Model();
//    html5Model.setStatus(99);
//    courseResourceHtml5Dao.updateEntity(cond, html5Model);
//    return "true";
//  }

}
