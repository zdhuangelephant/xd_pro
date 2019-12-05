package com.xiaodou.ms.web.controller.exam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.exam.QuestionBankQuestionDao;
import com.xiaodou.ms.enums.WrongQues;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionAnswers;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.exam.QuestionSelection;
import com.xiaodou.ms.model.exam.RaiseWrongQues;
import com.xiaodou.ms.model.product.ProductQuestionModel;
import com.xiaodou.ms.service.course.CourseCategoryService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionResourceService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.ms.vo.exam.ImportQuestion;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.exam.ExamQuestionCreateRequest;
import com.xiaodou.ms.web.request.exam.ExamQuestionEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("questionController")
@RequestMapping("question")
public class QuestionController extends BaseController {

  // 资源
  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  // 章节
  @Resource
  CourseChapterService courseChapterService;

  // 课程
  @Resource
  CourseSubjectService courseSubjectService;

  // 课程分类
  @Resource
  CourseCategoryService courseCategoryService;

  // 题目类型
  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  // 考点
  @Resource
  CourseKeywordService courseKeywordService;

  // 试题DAO
  @Resource
  QuestionBankQuestionDao questionBankQuestionDao;

  // 试题来源
  @Resource
  QuestionBankQuestionResourceService questionBankQuestionResourceService;

  @Resource
  ProductQuestionService productQuestionService;

  /**
   * ajax 试题列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  @RequestMapping("/ajax_list")
  @ResponseBody
  public String ajax_resourceQuestion(Long courseId, Long chapterId) {
    if (chapterId == null) {
      chapterId = 0L;
    }
    Page<QuestionBankQuestionModel> courseResourceQuestionModelPage =
        questionBankQuestionService.cascadeQueryQuestionByChapterId(courseId, chapterId);
    return JSON.toJSONString(courseResourceQuestionModelPage.getResult());
  }

  /**
   * 根据章节查找题目
   * 
   * @param courseId
   * @param chapterIds
   * @return
   */
  @RequestMapping("/ajax_list_by_chapters")
  @ResponseBody
  public String ajax_list_byChapters(Long courseId, String chapterIds, Long productId) {

    if (StringUtils.isBlank(chapterIds)) {
      List<QuestionBankQuestionModel> result = new ArrayList<>();
      return JSON.toJSONString(result);
    }
    String[] ids = chapterIds.split(",");
    List<Long> chapters = new ArrayList<>();
    for (String chapterId : ids) {
      chapters.add(Long.parseLong(chapterId));
    }
    List<QuestionBankQuestionModel> questionModels =
        questionBankQuestionService.queryQuestionByChapterIds(courseId, chapters);


    List<Long> questionIds = new ArrayList<>();
    for (QuestionBankQuestionModel questionModel : questionModels) {
      questionIds.add(questionModel.getId());
    }
    List<ProductQuestionModel> productQuestionModels =
        productQuestionService.queryQuestionListByQuestionIds(productId, questionIds);
    Map<Long, List<ProductQuestionModel>> productQuestionMap = new HashMap<>();
    for (ProductQuestionModel productQuestion : productQuestionModels) {
      List<ProductQuestionModel> questionList = null;
      if (productQuestionMap.containsKey(productQuestion.getQuestionId())) {
        questionList = productQuestionMap.get(productQuestion.getQuestionId());
      } else {
        questionList = new ArrayList<>();
      }
      questionList.add(productQuestion);
      productQuestionMap.put(productQuestion.getQuestionId(), questionList);
    }

    List<QuestionBankQuestionModel> result = new ArrayList<>();
    for (QuestionBankQuestionModel questionModel : questionModels) {
      if (productQuestionMap.containsKey(questionModel.getId())) {
        questionModel.setRelationProductQuestions(productQuestionMap.get(questionModel.getId()));
      }
      result.add(questionModel);
    }

    return JSON.toJSONString(result);
  }

  /**
   * 根据题目来源查找题目
   * 
   * @param resourceId
   * @return
   */
  @RequestMapping("/ajax_list_by_resource")
  @ResponseBody
  public String ajax_list_byResourceId(Long resourceId, Long productId) {
    List<QuestionBankQuestionModel> questionModels =
        questionBankQuestionService.queryQuestionByResourceId(resourceId);

    List<Long> ids = new ArrayList<>();
    for (QuestionBankQuestionModel questionModel : questionModels) {
      ids.add(questionModel.getId());
    }
    List<ProductQuestionModel> productQuestionModels =
        productQuestionService.queryQuestionListByQuestionIds(productId, ids);
    Map<Long, List<ProductQuestionModel>> productQuestionMap = new HashMap<>();
    for (ProductQuestionModel productQuestion : productQuestionModels) {
      List<ProductQuestionModel> questionList = null;
      if (productQuestionMap.containsKey(productQuestion.getQuestionId())) {
        questionList = productQuestionMap.get(productQuestion.getQuestionId());
      } else {
        questionList = new ArrayList<>();
      }
      questionList.add(productQuestion);
      productQuestionMap.put(productQuestion.getQuestionId(), questionList);
    }

    List<QuestionBankQuestionModel> result = new ArrayList<>();
    for (QuestionBankQuestionModel questionModel : questionModels) {
      if (productQuestionMap.containsKey(questionModel.getId())) {
        questionModel.setRelationProductQuestions(productQuestionMap.get(questionModel.getId()));
      }
      result.add(questionModel);
    }

    return JSON.toJSONString(result);
  }

  /**
   * 试题资源
   * 
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping("/list")
  public ModelAndView resourceQuestion(Long resourceId, Integer questionType, Integer zhenti, Long courseId, Long chapterId, Integer page, String keyword, Integer status, String id)
      throws Exception {
    if (StringUtils.isNotBlank(keyword)) {
      keyword = URLDecoder.decode(new String(Base64Utils.decode(keyword)), "utf8");
    }

    try {
      if (chapterId == null) {
        chapterId = 0L;
      }
      if (page == null || page == 0) {
        page = 1;
      }
      ModelAndView modelAndView = new ModelAndView("/exam/questionList");
      // 试卷来源
      String tableTree = courseChapterService.ChapterJqueryTree(courseId, 0L);

      modelAndView.addObject("tableTree", tableTree);
      Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
      modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
      if (courseId != null) {
        List<QuestionBankQuestionResourceModel> resourceIdList =
            questionBankQuestionResourceService.findResourceByCourseId(courseId);
        modelAndView.addObject("resourceIdList", resourceIdList);
      }else{
    	  return modelAndView;
      }

      // 是否是真题


      // 来源
      Page<QuestionBankQuestionModel> courseResourceQuestionModelPage =
          questionBankQuestionService.cascadeQueryQuestionByChapterId(resourceId, questionType,
              zhenti, courseId, chapterId, page, keyword, status, id);
      List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
      for(QuestionBankQuestionModel c:courseResourceQuestionModelPage.getResult()){
    	  if(StringUtils.isNotEmpty(c.getAnswerList())){
    		  QuestionAnswers questionAnswers=FastJsonUtil.fromJson(c.getAnswerList(), QuestionAnswers.class);
    		  if(c.getAnswerIds().endsWith("_1\"]")){
    			  c.setAnswerIds(questionAnswers.getQuestionSelectionList().get(0).getSelection());
    		  }else if(c.getAnswerIds().endsWith("_2\"]")){
    			  c.setAnswerIds(questionAnswers.getQuestionSelectionList().get(1).getSelection());
    		  }else if(c.getAnswerIds().endsWith("_3\"]")){
    			  c.setAnswerIds(questionAnswers.getQuestionSelectionList().get(2).getSelection());
    		  }else if(c.getAnswerIds().endsWith("_4\"]")){
    			  c.setAnswerIds(questionAnswers.getQuestionSelectionList().get(3).getSelection());
    		  }
    			 
    	  }
      }
      // 单项、类型map
      Map<String, Object> typeMap = new HashMap<>();
      for (QuestionBankQuestionTypeModel typeModel : typeList) {
        typeMap.put(typeModel.getId().toString(), typeModel);
      }

      Map<String, String> easyLevel = new HashMap<>();
      easyLevel.put("1", "A");
      easyLevel.put("2", "B");
      easyLevel.put("3", "C");
      Map<String, String> diffcultLevel = new HashMap<>();
      diffcultLevel.put("1", "I");
      diffcultLevel.put("2", "II");
      diffcultLevel.put("3", "III");
      modelAndView.addObject("typeMap", typeMap);
      modelAndView.addObject("easyLevel", easyLevel);
      modelAndView.addObject("diffcultLevel", diffcultLevel);
      modelAndView.addObject("pageList", courseResourceQuestionModelPage);
      modelAndView.addObject("chapterId", chapterId);
      modelAndView.addObject("courseId", courseId);
      modelAndView.addObject("keyword", keyword);
      modelAndView.addObject("status", status);
      modelAndView.addObject("id", id);
      modelAndView.addObject("zhenti", zhenti);
      modelAndView.addObject("questionType", questionType);
      modelAndView.addObject("resourceId", resourceId);

  

      CourseChapterModel chapter = courseChapterService.findChapterById(chapterId);
      modelAndView.addObject("chapter", chapter);

      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 批量添加
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  @RequestMapping("batchAdd")
  public ModelAndView questionBatchAdd(Long courseId, String chapterId) {
    ModelAndView modelAndView = new ModelAndView("/exam/questionBatchAdd");
    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId);
    List<QuestionBankQuestionResourceModel> questionResourceList =
        questionBankQuestionResourceService.resourceList();
    modelAndView.addObject("selectTree", selectTree);
    modelAndView.addObject("typeList", typeList);
    modelAndView.addObject("courseId", courseId);
    modelAndView.addObject("chapterId", chapterId);
    modelAndView.addObject("questionResourceList", questionResourceList);
    return modelAndView;
  }

  /**
   * 试题添加
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceQuestionAdd(Long courseId, String chapterId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/exam/questionAdd");
      List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
      String selectTree = courseChapterService.ChapterSelectTree(courseId, 0L, chapterId);
      Page<CourseKeywordModel> courseKeywordModelPage =
          courseKeywordService.cascadeQueryKeywordByChapter(courseId, Long.parseLong(chapterId));

      List<QuestionBankQuestionResourceModel> questionBankQuestionResourceModelList =
          questionBankQuestionResourceService.findResourceByCourseId(courseId);

      modelAndView.addObject("selectTree", selectTree);
      modelAndView.addObject("typeList", typeList);
      modelAndView.addObject("keywordList", courseKeywordModelPage.getResult());
      modelAndView.addObject("courseId", courseId);
      modelAndView.addObject("chapterId", chapterId);
      modelAndView.addObject("questionResourceList", questionBankQuestionResourceModelList);
      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 新增试题
   * 
   * @param errors
   * @return
   */
  @RequestMapping("/doAdd")
  @ResponseBody
  public String resourceQuestionDoAdd(ExamQuestionCreateRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        QuestionBankQuestionModel question = questionBankQuestionService.addQuestion(request);
        Map<String, Long> cond = new HashMap<>();
        cond.put("id",question.getId());
        QuestionBankQuestionModel questionModel = new QuestionBankQuestionModel();
        questionModel.setStatus(99);
        questionBankQuestionDao.updateEntity(cond, questionModel);
        response = new BaseResponse(ResultType.SUCCESS);
      }
      if (response.getRetCode() == 0) {
        return "true";
      } else {
        return "false";
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
  public ModelAndView resourceQuestionEdit(Long courseId, Long questionId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/exam/questionEdit");
      QuestionBankQuestionModel resourceQuestion =
          questionBankQuestionService.findResourceQuesById(questionId);
      String selectTree =
          courseChapterService.ChapterSelectTree(courseId, 0L, resourceQuestion.getChapterId()
              .toString());
      List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
      Page<CourseKeywordModel> courseKeywordModelPage =
          courseKeywordService.cascadeQueryKeywordByChapter(courseId,
              resourceQuestion.getChapterId());
      modelAndView.addObject("typeList", typeList);
      modelAndView.addObject("courseQuestion", resourceQuestion);
      modelAndView.addObject("selectTree", selectTree);
      modelAndView.addObject("keywordList", courseKeywordModelPage.getResult());

      // 2017/11/17 Modify By Zhaodan 主观题也需要编辑选项
      // if (resourceQuestion.getQuestionType() == 1 || resourceQuestion.getQuestionType() == 2)
      INIT_ANSWER: {
        String answerList = resourceQuestion.getAnswerList();
        if (StringUtils.isJsonBlank(answerList)) break INIT_ANSWER;
        QuestionAnswers questionAnswers = JSON.parseObject(answerList, QuestionAnswers.class);
        List<QuestionSelection> selections = questionAnswers.getQuestionSelectionList();
        String answerIds = resourceQuestion.getAnswerIds();
        List<String> answers = JSON.parseObject(answerIds, new TypeReference<List<String>>() {});
        Map<String, String> answerMap = new HashMap<>();
        for (String answer : answers) {
          answerMap.put(answer, "");
        }
        for (QuestionSelection selection : selections) {
          if (answerMap.containsKey(selection.getId())) {
            selection.setIsAnswer(QuestionSelection.YES);
          } else {
            selection.setIsAnswer(QuestionSelection.NO);
          }
        }
        modelAndView.addObject("selections", selections);
        modelAndView.addObject("questionAnswers", questionAnswers);
      }

      List<QuestionBankQuestionResourceModel> questionResourceList =
          questionBankQuestionResourceService.findResourceByCourseId(courseId);
      modelAndView.addObject("questionResourceList", questionResourceList);

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
  public ModelAndView resourceQuestionDoEdit(ExamQuestionEditRequest request, Errors errors) {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        questionBankQuestionService.editQuestion(request);
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
  public String resourceQuestionDel(Integer id) {
    try {
      Boolean aBoolean = questionBankQuestionService.deleteResourceQuestion(id);
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
   * 审核通过。因需求变更，此部分代码不再使用，lirui注释于2018.07.27
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
//    QuestionBankQuestionModel questionModel = new QuestionBankQuestionModel();
//    questionModel.setStatus(99);
//    questionBankQuestionDao.updateEntity(cond, questionModel);
//    return "true";
//  }

  /**
   * 文档资源查看
   * 
   * @return
   */
  @RequestMapping("/detail")
  public ModelAndView resourceQuestionDetail(Long courseId, Long questionId) {
    try {
      ModelAndView modelAndView = new ModelAndView("/exam/questionDetail");
      QuestionBankQuestionModel resourceQuestion =
          questionBankQuestionService.findResourceQuesById(questionId);
      String selectTree =
          courseChapterService.ChapterSelectTree(courseId, 0L, resourceQuestion.getChapterId()
              .toString());
      List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
      Page<CourseKeywordModel> courseKeywordModelPage =
          courseKeywordService.cascadeQueryKeywordByChapter(courseId,
              resourceQuestion.getChapterId());
      modelAndView.addObject("typeList", typeList);
      modelAndView.addObject("courseQuestion", resourceQuestion);
      modelAndView.addObject("selectTree", selectTree);
      modelAndView.addObject("keywordList", courseKeywordModelPage.getResult());

      if (resourceQuestion.getQuestionType() == 1 || resourceQuestion.getQuestionType() == 2) {
        String answerList = resourceQuestion.getAnswerList();
        QuestionAnswers questionAnswers = JSON.parseObject(answerList, QuestionAnswers.class);
        List<QuestionSelection> selections = questionAnswers.getQuestionSelectionList();
        String answerIds = resourceQuestion.getAnswerIds();
        List<String> answers = JSON.parseObject(answerIds, new TypeReference<List<String>>() {});
        Map<String, String> answerMap = new HashMap<>();
        for (String answer : answers) {
          answerMap.put(answer, "");
        }
        for (QuestionSelection selection : selections) {
          if (answerMap.containsKey(selection.getId())) {
            selection.setIsAnswer(QuestionSelection.YES);
          } else {
            selection.setIsAnswer(QuestionSelection.NO);
          }
        }
        modelAndView.addObject("selections", selections);
        modelAndView.addObject("questionAnswers", questionAnswers);
      }

      List<QuestionBankQuestionResourceModel> questionResourceList =
          questionBankQuestionResourceService.resourceList();
      modelAndView.addObject("questionResourceList", questionResourceList);

      return modelAndView;
    } catch (Exception e) {
      throw e;
    }
  }

  // 下载上传所需要的模版文件
  @RequestMapping("/download_excel")
  public void downloadExcel(HttpServletResponse response) {
    List<ImportQuestion> list = Lists.newArrayList();
    ImportQuestion iq = new ImportQuestion();
    iq.setChapterName("第一章");
    iq.setSectionName("第一节");
    //iq.setQuestionSrc("《思修》2015年10月真题");
    iq.setZhenti("真题");
    iq.setQuestionType("单项选择题");
    iq.setMdesc("被称为人的认识、情感、意志的“合金”的是（）");
    iq.setManalyze("解析~");
    iq.setOptA("信念");
    iq.setOptB("理想");
    iq.setOptC("欲望");
    iq.setOptD("品质");
    iq.setAnswerIds("A");
    iq.setPoint("婴幼儿发展概述");
    list.add(iq);

    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    questionBankQuestionService.downloadExcel("示例", "template" + dateFileName, list, response);
  }

  // 进行对上传文件格式的校验
  @RequestMapping("/detection_excel_url")
  @ResponseBody()
  public String detectionExcelUrl(String url, String chooseCourseId) {
    return questionBankQuestionService.detectionExcelUrl(url, chooseCourseId);
  }

  // 对校验的错误信息进行excel下载浏览
  @RequestMapping("/create_error_excel")
  public void createErrorExcel(String url, String chooseCourseId, HttpServletResponse response) {
    List<ImportQuestion> errorExcelList =
        questionBankQuestionService.createErrorExcel(url, chooseCourseId);
    String dateFileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
    questionBankQuestionService.downloadExcel("检测", "detection" + dateFileName, errorExcelList,
        response);
  }

  // 正式导入文件
  @RequestMapping("/import_questions")
  @ResponseBody()
  public String importQuestions(String url, String chooseCourseId) { // QuestionBankQuestionModel
    return questionBankQuestionService.importQuestions(url, chooseCourseId);
  }


  /**
   * 错题反馈
   * 
   * @return
   */
  @RequestMapping("/errQuesList")
  public ModelAndView errQuesList(Integer page) {
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView modelAndView = new ModelAndView("/exam/feedBackList");
    Page<RaiseWrongQues> wrongQuesList = questionBankQuestionService.findAllErrorRecord(page);
    if (wrongQuesList != null && wrongQuesList.getResult().size() > 0)
      for (RaiseWrongQues raiseWrongQues : wrongQuesList.getResult()) {
        QuestionBankQuestionModel qbqm =
            questionBankQuestionService.findResourceQuesById(raiseWrongQues.getQuesId());
        if (qbqm == null) continue;
        raiseWrongQues.setMdesc(qbqm.getMdesc());
        if (StringUtils.isBlank(raiseWrongQues.getWrongType())) continue;
        String tmp =
            raiseWrongQues.getWrongType().substring(raiseWrongQues.getWrongType().indexOf("[") + 1,
                raiseWrongQues.getWrongType().indexOf("]"));
        Set<String> wrongTypeLists = Sets.newHashSet(tmp.split(","));
        StringBuilder sb = new StringBuilder("");
        if (wrongQuesList != null && wrongQuesList.getResult().size() > 0)
          for (String index : wrongTypeLists) {
            sb.append(",");
            String content = WrongQues.getByCode(index.replaceAll("\"", "")).getContent();
            sb.append(content);
          }
        String target =
            sb.toString().substring(sb.toString().indexOf(",") + 1, sb.toString().length());
        raiseWrongQues.setErrDesc(target);

      }
    modelAndView.addObject("wrongQuesList", wrongQuesList);
    return modelAndView;
  }



}
