package com.xiaodou.ms.web.controller.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceDocModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
import com.xiaodou.ms.model.course.CourseResourceVideoModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.ProductQuestionModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseResourceAudioService;
import com.xiaodou.ms.service.course.CourseResourceDocService;
import com.xiaodou.ms.service.course.CourseResourceHtml5Service;
import com.xiaodou.ms.service.course.CourseResourceVideoService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.ProductItemCreateRequest;
import com.xiaodou.ms.web.request.product.ProductItemEditRequest;
import com.xiaodou.ms.web.request.product.ProductTreeRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("productItemController")
@RequestMapping("/productItem")
public class ProductItemController extends BaseController {

  @Resource
  ProductItemService productItemService;

  @Resource
  ProductService productService;

  @Resource
  CourseResourceHtml5Service courseResourceHtml5Service;

  @Resource
  CourseResourceVideoService courseResourceVideoService;

  @Resource
  CourseResourceAudioService courseResourceAudioService;

  @Resource
  CourseResourceDocService courseResourceDocService;

  @Resource
  CourseSubjectService courseSubjectService;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  @Resource
  MissionService missionService;

  @Resource
  MajorCourseService majorCourseService;

  /**
   * 章节关联
   * 
   * @return
   */

  @RequestMapping("resourceRelation")
  public ModelAndView chapterResourceRelation(Long chapterId, Long productId) {
    ModelAndView modelAndView = new ModelAndView("/product/chapterResource");

    // 产品信息
    ProductModel product = productService.findSubjectById(productId);
    
    // 产品条目信息
    ProductItemModel productItem = productItemService.findItemById(chapterId);
    String resourceIds = productItem.getResourceId();
    List<String> ids = new ArrayList<>();
    if (StringUtils.isNotBlank(resourceIds)) {
      ids = Arrays.asList(resourceIds.split(","));
    }

    // 资源树
    String multiTree =
        courseChapterService.multiSelectChapterTree(ids, product.getResourceSubject());

    // 试题类型
    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();

    // 试题列表
    List<ProductQuestionModel> productQuestionModels =
        productQuestionService.queryQuestionListByChapterIdWithoutCascade(productId, chapterId);

    modelAndView.addObject("typeList", typeList);
    modelAndView.addObject("tree", multiTree);
    modelAndView.addObject("chapterId", chapterId);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("courseId", product.getResourceSubject());
    modelAndView.addObject("productQuestions", productQuestionModels);
    return modelAndView;
  }

  /**
   * 编辑关系
   * 
   * @param productId
   * @param chapterId
   * @param request
   * @return
   */
  @RequestMapping("editResourceRelation")
  public ModelAndView editResourceRelation(Long productId, Long chapterId,
      HttpServletRequest request) {

    // 修改关联章节
    ProductItemModel productItemModel = new ProductItemModel();
    String[] ids = request.getParameterValues("menuid[]");
    String[] questionIds = request.getParameterValues("questionIds");
    StringBuilder idsStr = new StringBuilder("");
    for (String id : ids) {
      idsStr.append(id + ",");
    }
    productItemModel.setResourceId(idsStr.toString());
    productItemModel.setId(chapterId);
    productItemService.editItem(productItemModel);

    // 资源题库
    List<Integer> questionIdList = new ArrayList<>();
    if (questionIds != null && questionIds.length > 0) {
      for (String questionId : questionIds) {
        questionIdList.add(Integer.parseInt(questionId));
      }
    }

    List<QuestionBankQuestionModel> questionList = new ArrayList<>();
    if (questionIdList.size() > 0) {
      questionList = questionBankQuestionService.queryQuestionByIds(questionIdList);
    }
    Map<Long, QuestionBankQuestionModel> questionMap = new HashMap<>();
    for (QuestionBankQuestionModel questionBankQuestionModel : questionList) {
      questionMap.put(questionBankQuestionModel.getId(), questionBankQuestionModel);
    }

    // 产品题库
    List<ProductQuestionModel> productQuestionList =
        productQuestionService.queryQuestionListByChapterIdWithoutCascade(productId, chapterId);
    Map<Long, ProductQuestionModel> productQuestionMap = new HashMap<>();
    for (ProductQuestionModel productQuestionModel : productQuestionList) {
      productQuestionMap.put(productQuestionModel.getQuestionId(), productQuestionModel);
    }

    List<QuestionBankQuestionModel> needAddList = new ArrayList<>();
    List<Long> needDeleteList = new ArrayList<>();

    for (QuestionBankQuestionModel questionModel : questionList) {
      if (!productQuestionMap.containsKey(questionModel.getId())) {
        needAddList.add(questionModel);
      }
    }

    for (ProductQuestionModel productQuestionModel : productQuestionList) {
      if (!questionMap.containsKey(productQuestionModel.getQuestionId())) {
        needDeleteList.add(productQuestionModel.getId());
      }
    }

    // 增加记录
    productQuestionService.addQuestions(chapterId, productId, needAddList);

    // 删除记录
    productQuestionService.deleteQuestions(needDeleteList);

    // 统计题目
    productQuestionService.staticsProductChapterQuestionNum(productId);

    return this.showMessage("编辑成功", null, "/productItem/resourceRelation?chapterId=" + chapterId
        + "&productId=" + productId, true);

    // return this.showMessage("编辑成功", null, "/productItem/list?productId=" + productId, true);

    // return this.showMessage("编辑成功", null, "", true);
  }

  /**
   * 栏目select树
   * 
   * @param productId
   * @return
   */
  @RequestMapping
  public String itemCategoryTree(Integer productId) {
    return "";
  }

  /**
   * 资源目录
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView itemList(Long productId, String isExpired) {
    try {
      ModelAndView modelAndView = new ModelAndView("/product/itemList");
      ProductModel product = productService.findSubjectById(productId);
//      MajorCourseModel majorCourse = majorCourseService.findCourseById(product.getCourseCode());
      String tableTree = productItemService.jqueryTableTree(0L, productId, isExpired);
//      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
//      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
      modelAndView.addObject("chapterId", "");
      modelAndView.addObject("courseId", "");
      modelAndView.addObject("tableTree", tableTree);
      modelAndView.addObject("productId", productId);
      modelAndView.addObject("product", product);
//      modelAndView.addObject("majorCourse", majorCourse);
      modelAndView.addObject("isExpired", isExpired);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 资源添加页面
   * 
   * @param productId
   * @param selectId
   * @return
   */
  @RequestMapping("/addVideoResource")
  public ModelAndView videoResourceAdd(Long productId, String selectId, Long resourceId,String chapterId) {
	ModelAndView modelAndView = new ModelAndView("/product/videoResourceAdd");
    CourseResourceVideoModel videoModel =
        courseResourceVideoService.findResourceVideoById(resourceId);
    String chapterTree = productItemService.resourceAddSelectTree(productId, selectId);
    modelAndView.addObject("chapterTree", chapterTree);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("video", videoModel);
    return modelAndView;
  }

  /**
   * 资源添加页面
   * 
   * @param productId
   * @param selectId
   * @return
   */
  @RequestMapping("/addAudioResource")
  public ModelAndView audioResourceAdd(Long productId, String selectId, Long resourceId) {
    ModelAndView modelAndView = new ModelAndView("/product/audioResourceAdd");
    CourseResourceAudioModel audio = courseResourceAudioService.findResourceAudioById(resourceId);
    String chapterTree = productItemService.resourceAddSelectTree(productId, selectId);
    modelAndView.addObject("chapterTree", chapterTree);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("audio", audio);
    return modelAndView;
  }

  /**
   * 资源添加页面
   * 
   * @param productId
   * @param selectId
   * @return
   */
  @RequestMapping("/addHtml5Resource")
  public ModelAndView html5ResourceAdd(Long productId, String selectId, Long resourceId) {
    ModelAndView modelAndView = new ModelAndView("/product/html5ResourceAdd");
    CourseResourceHtml5Model html5Model =
        courseResourceHtml5Service.findResourceHtml5ById(resourceId);
    String chapterTree = productItemService.resourceAddSelectTree(productId, selectId);
    modelAndView.addObject("chapterTree", chapterTree);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("html5", html5Model);
    return modelAndView;
  }

  /**
   * 资源添加页面
   * 
   * @param productId
   * @param selectId
   * @return
   */
  @RequestMapping("/addDocResource")
  public ModelAndView docResourceAdd(Long productId, String selectId, Long resourceId) {
    ModelAndView modelAndView = new ModelAndView("/product/docResourceAdd");
    CourseResourceDocModel docModel = courseResourceDocService.findResourceDocById(resourceId);
    String chapterTree = productItemService.resourceAddSelectTree(productId, selectId);
    modelAndView.addObject("chapterTree", chapterTree);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("doc", docModel);
    return modelAndView;
  }

  /**
   * 资源添加
   * 
   * @return
   */
  @RequestMapping("doAddVideoResource")
  public ModelAndView videoResourceDoAdd(ProductItemCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.addVideoResource(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 资源添加
   * 
   * @return
   */
  @RequestMapping("doAddAudioResource")
  public ModelAndView audioResourceDoAdd(ProductItemCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.addAudioResource(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 资源添加
   * 
   * @return
   */
  @RequestMapping("doAddHtml5Resource")
  public ModelAndView html5ResourceDoAdd(ProductItemCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.addHtml5Resource(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 资源添加
   * 
   * @return
   */
  @RequestMapping("doAddDocResource")
  public ModelAndView docResourceDoAdd(ProductItemCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.addDocResource(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }
  
  
  
  /**
   * 添加微课
   * 
   * @return
   */
  @RequestMapping("doAddMicroVideoResource")
  public ModelAndView doAddMicroVideoResource(ProductItemCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.addMicroVideoResource(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 目录添加页面
   * 
   * @param productId
   * @return
   */
  @RequestMapping("/addChapter")
  public ModelAndView chapterAdd(Long productId, String selectId) {
    ModelAndView modelAndView = new ModelAndView("/product/itemAdd");
    String chapterTree = productItemService.chapterAddSelectTree(productId, selectId);
    modelAndView.addObject("chapterTree", chapterTree);
    modelAndView.addObject("productId", productId);
    return modelAndView;
  }

  /**
   * 资源目录添加
   * 
   * @return
   */
  @RequestMapping("/doAddChapter")
  public ModelAndView chapterDoAdd(ProductItemCreateRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
    	ProductModel productModel = productService.findSubjectById(request.getProductId()); 
    	request.setModule(productModel.getModule());//设置地域
        productItemService.addChapter(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }

      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 目录编辑
   * 
   * @param itemId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView itemEdit(Long itemId) {
    ModelAndView modelAndView = new ModelAndView("/product/itemEdit");
    ProductItemModel productItemModel = productItemService.findItemById(itemId);
    String selectTree =
        productItemService.resourceAddSelectTree(productItemModel.getProductId(), productItemModel
            .getParentId().toString());
    modelAndView.addObject("productItem", productItemModel);
    modelAndView.addObject("selectTree", selectTree);
    return modelAndView;
  }

  /**
   * 资源目录修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView itemDoEdit(ProductItemEditRequest request, Errors errors) throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        productItemService.editItem(request);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", "/productItem/edit?itemId=" + request.getId(), true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), "/productItem/edit?itemId="
            + request.getId(), true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录编辑异常", e);
      throw e;
    }
  }

  /**
   * 资源目录删除,同时删除闯关任务,同时删除资源关联的章节中的关联关系
   * 
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String itemDel(Long id) {
    try {
      BaseResponse response = null;
      ProductItemModel productItemModel = productItemService.findItemById(id);
      Boolean aBoolean = productItemService.deleteItem(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      List<ProductItemModel> listn=productItemService.findItems(id).getResult();
      for(ProductItemModel p:listn){
    	  productItemService.deleteItem(p.getId());
      }
      List<ProductItemModel> list = productItemService.queryItemsById(id);
      if (list.size() > 0) {
        for (ProductItemModel model : list) {
          ProductItemModel item = new ProductItemModel();
          item.setId(model.getId());
          item.setRelationItem(0L);
          item.setRelationItemName("");
          productItemService.editItem(item);
        }
      }
      missionService.deleteStandardMission(id.toString());
      // 统计题目
      if (productItemModel != null && productItemModel.getProductId() != 0) {
        productQuestionService.staticsProductChapterQuestionNum(productItemModel.getProductId());
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
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
      ProductItemModel productItem = new ProductItemModel();
      productItem.setId(id);
      productItem.setListOrder(order);
      productItemService.editItem(productItem);
      List<MissionModel>  missionList=missionService.findStMissionById(id.toString());
  	  if(missionList!=null&&missionList.size()>0){
      	MissionModel mission = missionList.get(0);
          mission.setMissionOrder(order);
          missionService.editMission(mission);
  	 }
    }
    return "true";
  }

  /**
   * 排序
   * 
   * @return
   */
  @RequestMapping("/taskRatio")
  @ResponseBody
  public String taskRatio(String orders) {
    String[] orderList = orders.split(";");
    for (String orderItem : orderList) {
      String[] split = orderItem.split(":");
      Long id = Long.parseLong(split[0]);
      Integer ratio = Integer.parseInt(split[1]);
      ProductItemModel productItem = new ProductItemModel();
      productItem.setId(id);
      productItem.setTaskRatio(ratio);
      productItemService.editItem(productItem);
    }
    return "true";
  }



  /**
   * 关联关系
   * 
   * @param itemId
   * @return
   */
  @RequestMapping("/itemRelation")
  public ModelAndView itemRelation(Long itemId) {
    ModelAndView modelAndView = new ModelAndView("/product/itemRelation");
    ProductItemModel item = productItemService.findItemById(itemId);
    List<ProductItemModel> itemList =
        productItemService.queryAllChildItem(item.getId(), item.getProductId());
    modelAndView.addObject("relationId", item.getRelationItem());
    modelAndView.addObject("itemList", itemList);
    modelAndView.addObject("itemId", item.getId());
    return modelAndView;
  }

  /**
   * 维护关系
   * 
   * @param itemId
   * @param relationItem
   * @return
   */
  @RequestMapping("/doItemRelation")
  public ModelAndView doItemRelation(Long itemId, Long relationItem) {
    if (relationItem == null) {
      relationItem = 0L;
    }
    String relationItemName = "";
    if (relationItem != 0) {
      ProductItemModel relation = productItemService.findItemById(relationItem);
      relationItemName = relation.getName();
    }
    ProductItemModel item = new ProductItemModel();
    item.setId(itemId);
    item.setRelationItem(relationItem);
    item.setRelationItemName(relationItemName);
    productItemService.editItem(item);
    return this.showMessage("成功", null, null, true);
  }

  /**
   * 目录添加页面(根据资源自动添加)
   * 
   * @author zhouhuan
   * @return
   */
  @RequestMapping("/addChapterByCourse")
  public ModelAndView chapterAddByCourse(Long productId, String selectId, Long courseId,
                                         Long chapterId) {
    ModelAndView modelAndView = new ModelAndView("/product/itemAddByCourse");
    modelAndView.addObject("productId", productId);
    try {
      if (chapterId == null) {
        chapterId = 0L;
      }
      if (null == courseId && null != productId) {
        ProductModel productModel = productService.findSubjectById(productId);
        if (null == productModel || null == productModel.getResourceSubject()) {
          return modelAndView;
        }
        courseId = productModel.getResourceSubject();
      }
      CourseSubjectModel subject = courseSubjectService.findSubjectById(courseId);
      if (null != subject) {
        modelAndView.addObject("courseName", subject.getName());
      }
      String tableTree = courseChapterService.ChapterJqueryTreeCheckBox(courseId, 0L);
      modelAndView.addObject("courseId", courseId);
      modelAndView.addObject("tableTree", tableTree);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 目录添加页面
   * 
   * @param productId
   * @return
   */
//  @SuppressWarnings("unused")
//  @RequestMapping("/addChapterByChoose")
//  public ModelAndView addChapterByChoose(String parentJson, String childJson, Integer productId) {
//    ProductItemModel model = new ProductItemModel();
//    List<ProductTreeRequest> parentList =
//        FastJsonUtil.fromJsons(parentJson, new TypeReference<List<ProductTreeRequest>>() {});
//    List<ProductTreeRequest> childList =
//        FastJsonUtil.fromJsons(childJson, new TypeReference<List<ProductTreeRequest>>() {});
//    productItemService.addChapterByChoose(parentList, childList, productId);
//    return this.showMessage("成功", "", null, true);
//
//  }
  
  
  @SuppressWarnings("unused")
  @RequestMapping("/addChapterByChoose")
  @ResponseBody
  public String addChapterByChoose(String parentJson, String childJson, Long productId) {
    BaseResponse response = null;
    ProductItemModel model = new ProductItemModel();
    List<ProductTreeRequest> parentList =
        FastJsonUtil.fromJsons(parentJson, new TypeReference<List<ProductTreeRequest>>() {});
    List<ProductTreeRequest> childList =
        FastJsonUtil.fromJsons(childJson, new TypeReference<List<ProductTreeRequest>>() {});
    productItemService.addChapterByChoose(parentList, childList, productId);
    response = new BaseResponse(ResultType.SUCCESS);
    return FastJsonUtil.toJson(response);

  }


  @RequestMapping("/resetWeight")
  @ResponseBody
  public String resetWeight(String orders) {
    String[] orderList = orders.split(";");
    for (String orderItem : orderList) {
      String[] split = orderItem.split(":");
      Long id = Long.parseLong(split[0]);
      Double weight = Double.parseDouble(split[1]);
      ProductItemModel productItem = new ProductItemModel();
      productItem.setId(id);
      productItem.setWeight(weight);
      productItemService.editItem(productItem);
    }
    return "true";
  }
}
