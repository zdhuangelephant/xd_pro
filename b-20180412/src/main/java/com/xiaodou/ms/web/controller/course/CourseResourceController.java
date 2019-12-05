package com.xiaodou.ms.web.controller.course;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceDocModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
import com.xiaodou.ms.model.course.CourseResourceVideoModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseResourceAudioService;
import com.xiaodou.ms.service.course.CourseResourceDocService;
import com.xiaodou.ms.service.course.CourseResourceHtml5Service;
import com.xiaodou.ms.service.course.CourseResourceVideoService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/7/7.
 */
@Controller("courseResourceController")
@RequestMapping("/courseResource")
public class CourseResourceController extends BaseController {

  @Resource
  private CourseCategoryService courseCategoryService;

  @Resource
  private CourseSubjectService courseSubjectService;

  @Resource
  private CourseChapterService courseChapterService;

  @Resource
  private CourseResourceDocService courseResourceDocService;

  @Resource
  private CourseResourceHtml5Service courseResourceHtml5Service;

  @Resource
  private CourseResourceVideoService courseResourceVideoService;
  
  @Resource
  private CourseResourceAudioService courseResourceAudioService;

  @Resource
  private QuestionBankQuestionService questionBankQuestionService;
  @Resource
  private ProductService  productService;
  @Resource
  private ProductItemService  productItemService;
  /**
   * 章节框架
   * 
   * @return
   */
  @RequestMapping("/chapterFrame")
  public ModelAndView chapterFrame(Long catId, Long courseId, Long chapterId,
      Integer resourceType, Long productId, String selectId) {
    ModelAndView modelAndView = new ModelAndView("/course/resource");

    // 栏目树
    String catSelectTree = null;
    if (catId == null || catId == 0) {
      catSelectTree = courseCategoryService.categorySelectTree(0L, null);
    } else {
      catSelectTree = courseCategoryService.categorySelectTree(0L, catId.toString());
    }
    modelAndView.addObject("catTree", catSelectTree);

    // 课程表
    if (catId != null && catId != 0) {
      modelAndView.addObject("courseList", this.courseList(catId));
    } else {
      modelAndView.addObject("courseList", this.courseList(0L));
    }
    // 章节树
    if (courseId != null && courseId != 0) {
      modelAndView.addObject("chapterTree", this.chapterList(courseId,null));
      // 资源表
      if (chapterId != null && chapterId != 0) {
        modelAndView
            .addObject("resourceList", this.resourceList(courseId, chapterId, resourceType,null));
      } else {
        modelAndView.addObject("resourceList", this.resourceList(courseId, 0L, resourceType,null));
      }
    }
    modelAndView.addObject("resourceType", resourceType);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("selectId", selectId);
    return modelAndView;
  }
  
  /**
   * 章节框架
   * 
   * @return
   */
  @RequestMapping("/chapterFrame2")
  public ModelAndView chapterFrame2(Long courseId, Long chapterId,
      Integer resourceType, Long productId, String selectId) {
    ModelAndView modelAndView = new ModelAndView("/course/resource");
    // 章节树
    ProductModel product = productService.findSubjectById(productId);
    ProductItemModel item=productItemService.findItemById(chapterId);
    if (product != null && product.getResourceSubject() != 0) {
      if (item!=null&&item.getCourseItem() != null) {
    	CourseChapterModel c=  courseChapterService.findChapterById(Long.valueOf(item.getCourseItem()));
    	if(c.getParentId()!=0){
    		modelAndView.addObject("chapterTree","<ul id='chapterTree' class='filetree' ><li><a style='cursor: pointer;'  class='file'>"+c.getName()+"</a></li></ul>");
    	}else if(c.getParentId()==0){
    		modelAndView.addObject("chapterTree", this.chapterList(product.getResourceSubject(),Long.valueOf(item.getCourseItem())));
    	}
        modelAndView
            .addObject("resourceList", this.resourceList(product.getResourceSubject(), Long.valueOf(item.getCourseItem()), resourceType,chapterId));
      } else {
    	 modelAndView.addObject("chapterTree", this.chapterList(product.getResourceSubject(),null ));
        modelAndView.addObject("resourceList", this.resourceList(product.getResourceSubject(), 0L, resourceType,null));
      }
    }
    modelAndView.addObject("resourceType", resourceType);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("selectId", selectId);
    return modelAndView;
  }

  /**
   * 课程目录
   * 
   * @return
   */
  @RequestMapping("/courseList")
  @ResponseBody
  public String courseList(Long catId) {
    Page<CourseSubjectModel> courseSubjectModelPage = null;
    if (catId == null || catId == 0) {
      courseSubjectModelPage = courseSubjectService.queryAllCourse();
    } else {
      courseSubjectModelPage = courseSubjectService.cascadeQueryCourseByCatId(catId);
    }
    StringBuilder result = new StringBuilder("");
    for (CourseSubjectModel courseSubject : courseSubjectModelPage.getResult()) {
      result
          .append("<tr><td><a style=\"cursor: pointer;\" onclick=\"showChapter("
              + courseSubject.getId()
              + ",this)\"><span class=\"glyphicon glyphicon-book\" aria-hidden=\"true\" style=\"padding-right: 8px;\"></span>"
              + courseSubject.getName() + "</a></td></tr>");
    }
    return result.toString();
  }

  /**
   * 章节目录
   * 
   * @return
   */
  @RequestMapping("/chapterList")
  @ResponseBody
  public String chapterList(Long courseId,Long chapterId) {
    String parentTemplate =
        "<a style=\"cursor: pointer;\" onclick=\"showResource(${node.data.subjectId},${node.id},this)\" class=\"folder\">${node.data.sindex}[${node.name}]</a>";
    String childTemplate =
        "<a style=\"cursor: pointer;\" onclick=\"showResource(${node.data.subjectId},${node.id},this)\" class=\"file\">${node.data.sindex}[${node.name}]</a>";
    String chapterTree;
    if(chapterId!=null){   
    	chapterTree=courseChapterService.ChapterJqueryTree(courseId, chapterId, parentTemplate, childTemplate);
    }else{
    	chapterTree=courseChapterService.ChapterJqueryTree(courseId, 0L, parentTemplate, childTemplate);
    }
    return chapterTree;
  }

  /**
   * 资源列表
   * 
   * @param chapterId
   * @param resourceType
   * @return
   */
  @RequestMapping("/resourceList")
  @ResponseBody
  public String resourceList(Long courseId, Long chapterId, Integer resourceType,Long itemId) {
    StringBuilder result = new StringBuilder("");
    if (resourceType == null) {
      resourceType = 2;
    }
    if (2 == resourceType||8==resourceType) {
      Page<CourseResourceVideoModel> videoList =
          courseResourceVideoService.cascadeQueryVideoByChapterId(null, courseId, chapterId);
      for (CourseResourceVideoModel videoModel : videoList.getResult()) {
    	if(videoModel.getType()!=null&&videoModel.getType().equals("2")){
        result.append("<tr><td>" + videoModel.getName()+"(普通视频)"
            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
            + videoModel.getId() +"," + itemId +")\">应用</a></td></tr>");
    	}
    	if(videoModel.getType()!=null&&videoModel.getType().equals("8")){
            result.append("<tr><td>" + videoModel.getName()+"(微课)"
                + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
                + videoModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
        	}
      }
    } else if (3 == resourceType) {
      Page<CourseResourceDocModel> docList =
          courseResourceDocService.cascadeQueryDocByChapterId(courseId, chapterId);
      for (CourseResourceDocModel docModel : docList.getResult()) {
        result.append("<tr><td>" + docModel.getName()
            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
            + docModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
      }
    } else if (4 == resourceType) {
      Page<CourseResourceHtml5Model> html5List =
          courseResourceHtml5Service.cascadeQueryHtml5ByChapterId(null, courseId, chapterId);
      for (CourseResourceHtml5Model html5Model : html5List.getResult()) {
        result.append("<tr><td>" + html5Model.getName()
            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
            + html5Model.getId() +"," + itemId + ")\">应用</a></td></tr>");
      }
    } else if (5 == resourceType) {
      Page<QuestionBankQuestionModel> questionList =
          questionBankQuestionService.cascadeQueryQuestionByChapterId(courseId, chapterId);
      for (QuestionBankQuestionModel questionModel : questionList.getResult()) {
//        result.append("<tr><td>" + questionModel.getQuestionSrc()
//            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
//            + questionModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
        result.append("<tr><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
        + questionModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
      }
    } else if (7 == resourceType) {
      Page<CourseResourceAudioModel> audioList =
          courseResourceAudioService.cascadeQueryAudioByChapterId(null, courseId, chapterId);
      for (CourseResourceAudioModel audioModel : audioList.getResult()) {
        result.append("<tr><td>" + audioModel.getName()
            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
            + audioModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
      }
    } else {
      Page<CourseResourceVideoModel> videoList =
          courseResourceVideoService.cascadeQueryVideoByChapterId(null, courseId, chapterId);
      for (CourseResourceVideoModel videoModel : videoList.getResult()) {
        result.append("<tr><td>" + videoModel.getName()
            + "</td><td style=\"width:50px;\"><a style=\"cursor: pointer;\" onclick=\"apply("
            + videoModel.getId() +"," + itemId + ")\">应用</a></td></tr>");
      }
    }
    return result.toString();
  }



}
