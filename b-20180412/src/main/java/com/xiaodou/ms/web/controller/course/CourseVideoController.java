package com.xiaodou.ms.web.controller.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.course.CourseResourceVideoDao;
import com.xiaodou.ms.enums.Platform;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceVideoModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.service.course.CourseResourceVideoService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.course.ResourceVideoCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceVideoEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */

@Controller("courseVideoController")
@RequestMapping("/courseVideo")
public class CourseVideoController extends BaseController {

  // 资源
  @Resource
  CourseResourceVideoService courseResourceVideoService;

  @Resource
  CourseResourceVideoDao courseResourceVideoDao;

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
  public ModelAndView resourceVideo(Integer page, Long courseId, Long chapterId) {
    try {
      if (page == null) {
        page = 1;
      }
      if (chapterId == null) {
        chapterId = 0L;
      }
      ModelAndView modelAndView = new ModelAndView("/course/videoList");
      String tableTree = courseChapterService.ChapterJqueryTree(courseId, 0L);
      modelAndView.addObject("tableTree", tableTree);
      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
      if(null==courseId){
    	  return modelAndView;
      }
      Page<CourseResourceVideoModel> courseResourceVideoModelPage =
          courseResourceVideoService.cascadeQueryVideoByChapterId(page, courseId, chapterId);
     
      CourseChapterModel chapter = courseChapterService.findChapterById(chapterId);
      modelAndView.addObject("chapter", chapter);

      modelAndView.addObject("pageList", courseResourceVideoModelPage);
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
  public ModelAndView resourceVideoAdd(Long courseId, Long chapterId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/videoAdd");
      Page<CourseKeywordModel> courseKeywordModelPage =
          courseKeywordService.cascadeQueryKeywordByChapter(courseId, chapterId);
      String selectTree =
          courseChapterService.ChapterSelectTree(courseId, 0l, chapterId.toString());
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
  public ModelAndView resourceVideoDoAdd(ResourceVideoCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        CourseResourceVideoModel result =  courseResourceVideoService.addVideo(request);
        Map<String,Long> cond = new HashMap<>();
        cond.put("id", result.getId());
        CourseResourceVideoModel videoModel = new CourseResourceVideoModel();
        videoModel.setStatus(99);
        courseResourceVideoDao.updateEntity(cond, videoModel);
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
  public ModelAndView resourceVideoEdit(Long courseId, Long videoId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/course/videoEdit");
      CourseResourceVideoModel resourceVideo =
          courseResourceVideoService.findResourceVideoById(videoId);
      String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L,
          resourceVideo.getChapterId().toString());
      modelAndView.addObject("courseVideo", resourceVideo);
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
  public ModelAndView resourceVideoDoEdit(ResourceVideoEditRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        courseResourceVideoService.editVideo(request);
        // 资源的信息更改一并同步到产品的冗余信息中
        List<ProductItemModel> pLists =
            productItemService.queryProductItemByResourceId(request.getId(),"2");
        List<ProductItemModel> pLists2 =
                productItemService.queryProductItemByResourceId(request.getId(),"8");
        pLists.addAll(pLists2);
        if (pLists != null && pLists.size() > 0) {
          for (ProductItemModel productItemModel : pLists) {
            if (productItemModel == null) continue;
            if (StringUtils.isNotBlank(request.getName()))
              productItemModel.setName(request.getName());
            if (StringUtils.isNotBlank(productItemModel.getDetail()))
              productItemModel.setDetail(request.getDetail());
            String line = productItemModel.getResource();
            ResourceDescription cram = FastJsonUtil.fromJson(line, ResourceDescription.class);
            if (cram != null && request.getUrl() != null) cram.setUrl(request.getUrl());
            if (cram != null && request.getTimeLengthMinute() != null)
              cram.setTimeLengthMinute(request.getTimeLengthMinute());
            if (cram != null && request.getTimeLengthSecond() != null)
              cram.setTimeLengthSecond(request.getTimeLengthSecond());
            if (cram != null && request.getFileUrl() != null)
               cram.setFileUrl(request.getFileUrl());
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
  public String resourceVideoDel(Integer id) {
    try {
      Boolean aBoolean = courseResourceVideoService.deleteResourceVideo(id);
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
//    CourseResourceVideoModel videoModel = new CourseResourceVideoModel();
//    videoModel.setStatus(99);
//    courseResourceVideoDao.updateEntity(cond, videoModel);
//    return "true";
//  }

  @RequestMapping("/builder_tree")
  public ModelAndView builderImportTree(Long chooseCourseId, final String videosName) {
    ModelAndView mv = new ModelAndView("/course/importTree");
    if(StringUtils.isBlank(videosName)) return mv;
    List<String> videoList =
        FastJsonUtil.fromJsons(videosName, new TypeReference<List<String>>() {});
    if(null == videoList || videoList.size() == 0) return mv;
    TreeUtils treeUtils = courseResourceVideoService.builderTreeUtils(chooseCourseId, 0l);
    StringBuilder sb = new StringBuilder();
    String head = "<table border=\"1\">"; 
    sb.append(head);
    for (String vName : videoList) {
      Long chapterId = builderChapter(chooseCourseId, vName);
      if(null == chapterId) continue;
      String table = courseResourceVideoService.builderTable(treeUtils, 0L, chapterId.toString());
      
      // 构造Table
      String str = courseResourceVideoService.builderTable(table, vName,chapterId);
      sb.append(str);
    }
    String tail = "</table>";
    sb.append(tail);
    mv.addObject("tableTree", sb.toString());
    return mv;
  }

  private Long builderChapter(Long chooseCourseId, String vName) {
    // 1.1.1 xxx.mp4  节下
    // 1.1   yyy.mp4  章下
    if(StringUtils.isBlank(vName)) return null;
    String[] arr = vName.split("\\s+");
    if(null == arr || arr.length < 2) return null;
    String prefix = arr[0];
    String [] bulks = prefix.split("\\.");
    if(bulks.length < 2 || bulks.length > 3) return null;
    // 章下
    if(bulks.length == 2) {
      String sIndexChapter = String.format("%s%s%s", "第",Transfer.transferByCode(bulks[0]),"章");
      return courseResourceVideoService.findChapterId(chooseCourseId.toString(), sIndexChapter, "");
    }
    // 节下
    else if (bulks.length == 3) {
      String sIndexChapter = String.format("%s%s%s", "第",Transfer.transferByCode(bulks[0]),"章");
      String sIndexItem = String.format("%s%s%s", "第",Transfer.transferByCode(bulks[1]),"节");
      return courseResourceVideoService.findChapterId(chooseCourseId.toString(), sIndexChapter, sIndexItem);
    }
    return null;
  }
  
  
  
  enum Transfer{
    one("1","一"),two("2","二"),
    three("3","三"),four("4","四"),
    five("5","五"),six("6","六"),
    seven("7","七"),eight("8","八"),
    nine ("9","九"),ten("10","十"),
    eleven("11","十一"),twelve("12","十二"),
    thirteen("13","十三"),fourteen("14","十四"),
    fifteen("15","十五"),sixteen("16","十六"),
    seventeen("17","十七"),eighteen("18","十八"),
    nineteen("19","十九"),twenty("20","二十");
    private Transfer(String beforeTransfer, String afterTransfer) {
      this.beforeTransfer = beforeTransfer;
      this.afterTransfer = afterTransfer;
    }
    private String beforeTransfer;
    private String afterTransfer;
    public String getBeforeTransfer() {
      return beforeTransfer;
    }
    public void setBeforeTransfer(String beforeTransfer) {
      this.beforeTransfer = beforeTransfer;
    }
    public String getAfterTransfer() {
      return afterTransfer;
    }
    public void setAfterTransfer(String afterTransfer) {
      this.afterTransfer = afterTransfer;
    }
    
    private static Map<String, Transfer> typeMap = Maps.newHashMap();

    static {
      for (Transfer type : Transfer.values()) {
        typeMap.put(type.getBeforeTransfer(), type);
      }
    }

    public static Transfer getByCode(String code) {
      Transfer type = typeMap.get(code);
      if (null == type) return null;
      return type;
    }
    public static String transferByCode(String code) {
      Transfer type = typeMap.get(code);
      if (null == type) return null;
      return type.getAfterTransfer();
    }
  }
}
