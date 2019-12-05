package com.xiaodou.ms.web.controller.course;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.ChapterCreateRequest;
import com.xiaodou.ms.web.request.course.ChapterEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("courseChapterController")
@RequestMapping("/courseChapter")
public class CourseChapterController extends BaseController {

  // 课程章节
  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseSubjectService courseSubjectService;


  /**
   * 资源课程章节
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView resourceCourseChapter(Long courseId) {
    try {
      if (courseId == null) {
        courseId = 0L;
      }
      ModelAndView modelAndView = new ModelAndView("/course/chapterList");
      String tableTree = courseChapterService.ChapterTableTree(courseId, 0L);
      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
      modelAndView.addObject("tableTree", tableTree);
      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
      modelAndView.addObject("courseId", courseId);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 添加章节
   * 
   * @param courseId
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView chaperAdd(Long courseId, Long parentId) {
    ModelAndView modelAndView = new ModelAndView("/course/chapterAdd");
    String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L, parentId + "");
    modelAndView.addObject("selectTree", selectTree);
    modelAndView.addObject("subjectId", courseId);
    return modelAndView;
  }

  /**
   * 资源课程章节添加
   *
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceCourseChapterAdd(ChapterCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseChapterService.addChapter(request);
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
   * 资源课程章节删除
   *
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String resourceCourseChapterDel(Long chapterId) {
    try {
      Boolean aBoolean = courseChapterService.deleteChapter(chapterId);
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
   * 编辑章节
   * 
   * @param chapterId
   * @return
   */
  @RequestMapping("edit")
  public ModelAndView chapterEdit(Long chapterId) {
    ModelAndView modelAndView = new ModelAndView("/course/chapterEdit");
    CourseChapterModel courseChapter = courseChapterService.findChapterById(chapterId);
    String selectTree = courseChapterService.ChapterSelectTree(courseChapter.getSubjectId(), 0L,
        courseChapter.getParentId().toString());
    modelAndView.addObject("courseChapter", courseChapter);
    modelAndView.addObject("selectTree", selectTree);
    return modelAndView;
  }

  /**
   * 资源课程章节修改
   *
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceCourseChapterEdit(ChapterEditRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseChapterService.editChapter(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "/courseChapter/edit?chapterId=" + request.getId(),
            true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(),
            "/courseChapter/edit?chapterId=" + request.getId(), true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 排序
   * 
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public String order(String orders) {
    String[] orderList = orders.split(";");
    for (String orderItem : orderList) {
      String[] split = orderItem.split(":");
      Long id = Long.parseLong(split[0]);
      Integer order = Integer.parseInt(split[1]);
      CourseChapterModel productItem = new CourseChapterModel();
      productItem.setId(id);
      productItem.setListOrder(order);
      courseChapterService.editChapter(productItem);
    }
    return "true";
  }


}
