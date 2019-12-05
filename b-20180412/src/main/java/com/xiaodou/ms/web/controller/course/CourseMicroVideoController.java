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
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.course.CourseMicroVideoDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceMicroVideoModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.service.course.CourseMicroVideoService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.ResourceMicroVideoCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceMicroVideoEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;


/**
 * @name CourseMicroVideoController 
 * CopyRright (c) 2018 by Corp.XiaodouTech
 *
 * @author <a href="mailto:hzd82274@gmail.com">zdhuang</a>
 * @date 2018年6月25日
 * @description TODO  微课视频
 * @version 1.0
 */
@Controller("courseMicroVideoController")
@RequestMapping("/microVideo")
public class CourseMicroVideoController extends BaseController {

  // 资源
  @Resource
  CourseMicroVideoService courseMicroVideoService;

  @Resource
  CourseMicroVideoDao courseMicroVideoDao;

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

  @Resource
  ProductItemService productItemService;

  /**
   * 文档资源
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView resourceAudio(Integer page, Long courseId, Long chapterId) {
    try {
      if (page == null || page == 0) {
        page = 1;
      }

      if (chapterId == null) {
        chapterId = 0L;
      }
      ModelAndView modelAndView = new ModelAndView("/course/microVideoList");

      Page<CourseResourceMicroVideoModel> courseResourceAudioModelPage =
          courseMicroVideoService.cascadeQueryMicroVideoByChapterId(page, courseId, chapterId);
      String tableTree = courseChapterService.ChapterJqueryTree(courseId, 0L);
      modelAndView.addObject("pageList", courseResourceAudioModelPage);
      modelAndView.addObject("tableTree", tableTree);

      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
      CourseChapterModel chapter = courseChapterService.findChapterById(chapterId);
      modelAndView.addObject("chapter", chapter);
      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
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
  public ModelAndView resourceAudioAdd(Long courseId, Long chapterId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/microVideoAdd");
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
  public ModelAndView resourceAudioDoAdd(ResourceMicroVideoCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseMicroVideoService.addMicroVideo(request);
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
  public ModelAndView resourceAudioEdit(Long courseId, Long audioId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/microVideoEdit");
      CourseResourceMicroVideoModel resourceAudio =
          courseMicroVideoService.findResourceMicroVideoById(audioId);
      String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L,
          resourceAudio.getChapterId().toString());
      modelAndView.addObject("courseAudio", resourceAudio);
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
  public ModelAndView resourceAudioDoEdit(ResourceMicroVideoEditRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseMicroVideoService.editMicroVideo(request);

        // 资源的信息更改一并同步到产品的冗余信息中
        List<ProductItemModel> pLists =
            productItemService.queryProductItemByResourceId(request.getId(),null);
        if (pLists != null && pLists.size() > 0) {
          for (ProductItemModel productItemModel : pLists) {
            if (productItemModel == null) continue;
            if (StringUtils.isNotBlank(request.getName()))
              productItemModel.setName(request.getName());
            if (StringUtils.isNotBlank(productItemModel.getDetail()))
              productItemModel.setDetail(request.getDetail());
            String line = productItemModel.getResource();
            ResourceDescription cram = FastJsonUtil.fromJson(line, ResourceDescription.class);
            if (cram != null && request.getSize() != null) cram.setSize(request.getSize());
            if (cram != null && request.getUrl() != null) cram.setUrl(request.getUrl());
            if (cram != null && request.getTimeLengthMinute() != null)
              cram.setTimeLengthMinute(request.getTimeLengthMinute());
            if (cram != null && request.getTimeLengthSecond() != null)
              cram.setTimeLengthSecond(request.getTimeLengthSecond());
            cram.setCover(request.getVideoCover());
            cram.setName(request.getName());
            productItemModel.setResource(FastJsonUtil.toJson(cram));
            productItemService.updateProductItem(productItemModel);;
          }
        }
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
  public String resourceAudioDel(Integer id) {
    try {
      Boolean aBoolean = courseMicroVideoService.deleteResourceMicroVideo(id);
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
   * 审核通过
   * 
   * @param ids
   * @return
   */
  @RequestMapping("/verify")
  @ResponseBody
  public String verify(String ids) {
    String[] idArray = ids.split(";");
    List<Integer> idList = new ArrayList<>();
    for (String id : idArray) {
      idList.add(Integer.parseInt(id));
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", idList);
    CourseResourceMicroVideoModel audioModel = new CourseResourceMicroVideoModel();
    audioModel.setStatus(99);
    return String.valueOf(courseMicroVideoDao.updateEntity(cond, audioModel));
  }

}
