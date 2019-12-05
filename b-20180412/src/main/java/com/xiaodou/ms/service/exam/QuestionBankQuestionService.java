package com.xiaodou.ms.service.exam;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.course.CourseChapterDao;
import com.xiaodou.ms.dao.course.CourseKeywordDao;
import com.xiaodou.ms.dao.exam.QuestionBankQuestionDao;
import com.xiaodou.ms.dao.exam.QuestionBankQuestionResourceDao;
import com.xiaodou.ms.dao.exam.QuestionBankQuestionTypeDao;
import com.xiaodou.ms.dao.exam.RaiseWrongQuesDao;
import com.xiaodou.ms.dao.product.ProductDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseKeywordResourceModel;
import com.xiaodou.ms.model.exam.QuestionAnswers;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.exam.QuestionSelection;
import com.xiaodou.ms.model.exam.RaiseWrongQues;
import com.xiaodou.ms.queue.QueueService;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseKeywordResourceService;
import com.xiaodou.ms.service.course.CourseKeywordService;
import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.ms.util.ExcelUtil;
import com.xiaodou.ms.vo.exam.ImportQuestion;
import com.xiaodou.ms.web.request.exam.ExamQuestionCreateRequest;
import com.xiaodou.ms.web.request.exam.ExamQuestionEditRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by zyp on 15/4/19.
 */
@Service("questionBankQuestionService")
public class QuestionBankQuestionService {

  @Resource
  QuestionBankQuestionDao questionBankQuestionDao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  CourseKeywordService courseKeywordService;

  @Resource
  CourseChapterDao courseChapterDao;

  @Resource
  QuestionBankQuestionResourceDao questionBankQuestionResourceDao;

  @Resource
  CourseKeywordDao courseKeywordDao;

  @Resource
  QuestionBankQuestionTypeDao questionBankQuestionTypeDao;

  @Resource
  RaiseWrongQuesDao raiseWrongQuesDao;

  @Resource
  ProductDao productDao;
  @Resource
  QueueService queueService;

  public void updateTest() {
    for (int i = 0; i <= 200; i++) {
      List<CourseKeywordResourceModel> relations = courseKeywordResourceService
          .queryRelationByResourceId(Long.valueOf(i), ResourceType.QUESTION.getTypeId());
      List<Long> keywordIds = new ArrayList<>();
      for (CourseKeywordResourceModel resourceModel : relations) {
        keywordIds.add(resourceModel.getKeywordId());
      }
      if (keywordIds.size() > 0) {
        List<CourseKeywordModel> keywordModelList =
            courseKeywordService.queryKeywordsByIds(keywordIds);
        if (keywordModelList.size() > 0) {
          this.updateKeyPoint(new Long(i), keywordModelList);
        }
      }
    }
  }

  /**
   * 更新关键词列表
   * 
   * @param resourceId
   * @param keywordList
   * @return
   */
  public Boolean updateKeyPoint(Long resourceId, List<CourseKeywordModel> keywordList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", resourceId);
    QuestionBankQuestionModel questionModel = new QuestionBankQuestionModel();
    questionModel.setKeyPoint(JSON.toJSONString(keywordList));
    return questionBankQuestionDao.updateEntity(cond, questionModel);
  }

  /**
   * 根据试题来源查询试题列表
   * 
   * @param courseId
   * @param chapterIds
   * @return
   */
  public List<QuestionBankQuestionModel> queryQuestionByChapterIds(Long courseId,
      List<Long> chapterIds) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("courseId", courseId);
    cond.put("chapterIds", chapterIds);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("mdesc", "");
    output.put("status", "");
    output.put("courseId", "");
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond0(cond, output, null);
    return questionBankQuestionModelPage.getResult();
  }


  /**
   * 根据试题来源查询试题列表
   * 
   * @param resourceId
   * @return
   */
  public List<QuestionBankQuestionModel> queryQuestionByResourceId(Long resourceId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("resourceId", resourceId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("mdesc", "");
    output.put("status", "");
    output.put("courseId", "");
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond0(cond, output, null);
    return questionBankQuestionModelPage.getResult();
  }

  /**
   * 问题列表
   * 
   * @param ids
   * @return
   */
  public List<QuestionBankQuestionModel> queryQuestionByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", ids);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("mdesc", "");
    output.put("status", "");
    output.put("courseId", "");
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond0(cond, output, null);
    return questionBankQuestionModelPage.getResult();
  }

  /**
   * 级联获取文档列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<QuestionBankQuestionModel> cascadeQueryQuestionByChapterId(Long courseId,
      Long chapterId, Integer pageNo, String keyword, Integer status, String id) {
    List<CourseChapterModel> courseChapterModels =
        courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel : courseChapterModels) {
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterIds", ids);
    if (StringUtils.isNotBlank(id)) {
      cond.put("id", id);
    }
    if (StringUtils.isNotBlank(keyword)) {
      cond.put("keyword", keyword);
    }
    if (status != null) {
      cond.put("status", status);
    }
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("mdesc", "");
    output.put("status", "");
    output.put("courseId", "");
    output.put("zhenti", "");
    Page<QuestionBankQuestionModel> page = new Page<QuestionBankQuestionModel>();
    page.setPageNo(pageNo);
    page.setPageSize(20);
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond(cond, output, page);
    return questionBankQuestionModelPage;
  }


  /**
   * 级联获取文档列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<QuestionBankQuestionModel> cascadeQueryQuestionByChapterId(Long resourceId,
      Integer questionType, Integer zhenti, Long courseId, Long chapterId, Integer pageNo,
      String keyword, Integer status, String id) {
    List<CourseChapterModel> courseChapterModels =
        courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel : courseChapterModels) {
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterIds", ids);
    if (StringUtils.isNotBlank(id)) {
      cond.put("id", id);
    }
    if (StringUtils.isNotBlank(keyword)) {
      cond.put("keyword", keyword);
    }
    if (status != null) {
      cond.put("status", status);
    }

    // 新添加查询条件
    if (null != questionType) {
      cond.put("questionType", questionType);
    }
    if (zhenti != null) {
      cond.put("zhenti", zhenti);
    }
    if (resourceId != null) {
      cond.put("resourceId", resourceId);
    }
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("answerList", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("answerIds", "");// zwj
    output.put("mdesc", "");
    output.put("status", "");
    output.put("courseId", "");
    output.put("zhenti", "");
    Page<QuestionBankQuestionModel> page = new Page<QuestionBankQuestionModel>();
    page.setPageNo(pageNo);
    page.setPageSize(20);
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond(cond, output, page);
    return questionBankQuestionModelPage;
  }

  /**
   * 级联获取文档列表
   * 
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<QuestionBankQuestionModel> cascadeQueryQuestionByChapterId(Long courseId,
      Long chapterId) {
    List<CourseChapterModel> courseChapterModels =
        courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel : courseChapterModels) {
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterIds", ids);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("chapterId", "");
    output.put("chapterName", "");
    output.put("questionType", "");
    output.put("questionSrc", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("mdesc", "");
    Page<QuestionBankQuestionModel> questionBankQuestionModelPage =
        questionBankQuestionDao.queryListByCond0(cond, output, null);
    return questionBankQuestionModelPage;
  }

  /**
   * 新增文档
   * 
   * @param entity
   * @return
   */
  public QuestionBankQuestionModel addQuestion(QuestionBankQuestionModel entity) {
    return questionBankQuestionDao.addEntity(entity);
  }

  /**
   * 新增文档
   * 
   * @param request
   * @return
   */
  public QuestionBankQuestionModel addQuestion(ExamQuestionCreateRequest request) {
    QuestionBankQuestionModel questionBankQuestionModel = new QuestionBankQuestionModel();
    questionBankQuestionModel.setMdesc(request.getMdesc());
    questionBankQuestionModel.setChapterId(request.getChapterId());
    questionBankQuestionModel.setCognitionLevel(request.getCognitionLevel());
    questionBankQuestionModel.setCourseId(request.getCourseId());
    questionBankQuestionModel.setDiffcultLevel(request.getDiffcultLevel());
    questionBankQuestionModel.setManalyze(request.getManalyze());
    questionBankQuestionModel.setQuesImgUrl(request.getQuesImgUrl());
    questionBankQuestionModel.setQuesTime(new Timestamp(System.currentTimeMillis()));
//    questionBankQuestionModel.setResourceId(request.getResourceId() == null ? 0 : request.getResourceId());
    questionBankQuestionModel.setQuestionType(request.getQuestionType());
    questionBankQuestionModel.setZhenti(request.getZhenti());
    QuestionBankQuestionModel question = this.addQuestion(questionBankQuestionModel);

    QuestionBankQuestionModel updateQuestion = new QuestionBankQuestionModel();
    updateQuestion.setCognitionLevel(question.getCognitionLevel());
    updateQuestion.setDiffcultLevel(question.getDiffcultLevel());
    // 如果为单选题 或者 多选题。需要编辑答案
    // 2017/11/17 Modify by zhaodan 改为主关题也需要编辑答案
    // if (request.getQuestionType() == QuestionBankQuestionTypeModel.Radio
    // || request.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
    updateQuestion.setId(question.getId());
    // 单选题
    // if (request.getQuestionType() == QuestionBankQuestionTypeModel.Radio) {
    //
    // } else
    if (request.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
      String[] checkList = request.getCheckBoxSelection().split(";");
      Integer i = 1;
      List<QuestionSelection> selectionList = new ArrayList<>();
      List<String> answerId = new ArrayList<>();
      for (String checkItem : checkList) {
        String[] item = checkItem.split("\\u007C");
        String selection = item[0];
        String isAnswer = item[1];
        if (StringUtils.isNotBlank(selection)) {
          String id = question.getId() + "_" + i;
          if (isAnswer.equals("1")) {
            answerId.add(id);
          }
          QuestionSelection questionSelection = new QuestionSelection();
          questionSelection.setId(id);
          if (request.getOptionType() == QuestionSelection.imageOption) {
            questionSelection.setImgUrl(selection);
            questionSelection.setOptionType(QuestionSelection.imageOption);
          } else {
            questionSelection.setSelection(selection);
            questionSelection.setOptionType(QuestionSelection.textOption);
          }
          selectionList.add(questionSelection);
          i = i + 1;
        }

      }
      updateQuestion.setAnswerIds(JSON.toJSONString(answerId));
      QuestionAnswers questionAnswers = new QuestionAnswers();
      questionAnswers.setOptionType(request.getOptionType());
      questionAnswers.setQuestionSelectionList(selectionList);
      updateQuestion.setAnswerList(JSON.toJSONString(questionAnswers));
    } else {
      String[] radioList = request.getRadioSelection().split(";");
      Integer i = 1;
      List<QuestionSelection> selectionList = new ArrayList<>();
      List<String> answerId = new ArrayList<>();
      for (String radioItem : radioList) {
        String[] item = radioItem.split("\\u007C");
        String selection = item[0];
        String isAnswer = item[1];
        if (StringUtils.isNotBlank(selection)) {
          String id = question.getId() + "_" + i;
          if (isAnswer.equals("1")) {
            answerId.add(id);
          }
          QuestionSelection questionSelection = new QuestionSelection();
          questionSelection.setId(id);
          if (request.getOptionType() == QuestionSelection.imageOption) {
            questionSelection.setImgUrl(selection);
            questionSelection.setOptionType(QuestionSelection.imageOption);
          } else {
            questionSelection.setSelection(selection);
            questionSelection.setOptionType(QuestionSelection.textOption);
          }
          selectionList.add(questionSelection);
          i = i + 1;
        }
      }
      updateQuestion.setAnswerIds(JSON.toJSONString(answerId));
      QuestionAnswers questionAnswers = new QuestionAnswers();
      questionAnswers.setOptionType(request.getOptionType());
      questionAnswers.setQuestionSelectionList(selectionList);
      updateQuestion.setAnswerList(JSON.toJSONString(questionAnswers));
    }
    this.editQuestion(updateQuestion);
    question.setAnswerList(updateQuestion.getAnswerList());
    question.setAnswerIds(updateQuestion.getAnswerIds());
    return question;
  }

  /**
   * 删除文档
   * 
   * @param id
   * @return
   */
  public Boolean deleteResourceQuestion(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return questionBankQuestionDao.deleteEntity(cond);
  }

  /**
   * 更新文档
   * 
   * @param entity
   * @return
   */
  public Boolean editQuestion(QuestionBankQuestionModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    productQuestionService.updateProductQuestionByQuestionId(entity.getId(),
        entity.getCognitionLevel(), entity.getDiffcultLevel(), entity.getQuestionType());
    return questionBankQuestionDao.updateEntity(cond, entity);
  }

  /**
   * 更新文档
   * 
   * @param request
   * @return
   */
  public Boolean editQuestion(ExamQuestionEditRequest request) {
    QuestionBankQuestionModel question = this.findResourceQuesById(request.getId());

    QuestionBankQuestionModel questionBankQuestionModel = new QuestionBankQuestionModel();
    questionBankQuestionModel.setId(request.getId()); // 主键
    questionBankQuestionModel.setMdesc(request.getMdesc()); // 描述
    questionBankQuestionModel.setChapterId(request.getChapterId()); // 章节Id
    questionBankQuestionModel.setCognitionLevel(request.getCognitionLevel()); // 认知层次
    questionBankQuestionModel.setDiffcultLevel(request.getDiffcultLevel()); // 难易程度
    questionBankQuestionModel.setManalyze(request.getManalyze()); // 解析
    questionBankQuestionModel.setStatus(-1);
    questionBankQuestionModel.setQuesImgUrl(request.getQuesImgUrl()); // 题目url
    questionBankQuestionModel.setZhenti(request.getZhenti());
//    questionBankQuestionModel.setResourceId(request.getResourceId() == null ? 0:request.getResourceId()); // 来源Id
    questionBankQuestionModel.setQuestionType(request.getQuestionType());

    // if (question.getQuestionType() == QuestionBankQuestionTypeModel.Radio
    // || question.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
    // String answerList = question.getAnswerList();
    // QuestionAnswers questionAnswers = JSON.parseObject(answerList, QuestionAnswers.class);
    // List<QuestionSelection> questionSelectionList = questionAnswers.getQuestionSelectionList();
    // Map<String, QuestionSelection> selectionMap = new HashMap<>();
    // for (QuestionSelection questionSelection : questionSelectionList) {
    // selectionMap.put(questionSelection.getId(), questionSelection);
    // }
    // String selections = "";
    // if (question.getQuestionType() == QuestionBankQuestionTypeModel.Radio) {
    // selections = request.getRadioSelection();
    // } else if (question.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
    // selections = request.getCheckBoxSelection();
    // }
    // String[] selectionList = selections.split(";");
    // List<QuestionSelection> newList = new ArrayList<>();
    // for (String selection : selectionList) {
    // String[] selectionItems = selection.split(":");
    // String id = selectionItems[0];
    // String option = selectionItems[1];
    // QuestionSelection questionSelection = selectionMap.get(id);
    // questionSelection.setSelection(option);
    // newList.add(questionSelection);
    // }
    // questionAnswers.setQuestionSelectionList(newList);
    // 如果为单选题 或者 多选题。需要编辑答案
    // if (request.getQuestionType() == QuestionBankQuestionTypeModel.Radio
    // || request.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
    // 单选题
    // if (request.getQuestionType() == QuestionBankQuestionTypeModel.Radio) {
    //
    // } else
    // 2017/11/17 Modify By Zhaodan 改为主观题也需要编辑答案
    if (request.getQuestionType() == QuestionBankQuestionTypeModel.CheckBox) {
      String[] checkList = request.getCheckBoxSelection().split(";");
      Integer i = 1;
      List<QuestionSelection> selectionList = new ArrayList<>();
      List<String> answerId = new ArrayList<>();
      for (String checkItem : checkList) {
        String[] item = checkItem.split("\\u007C");
        String selection = item[0];
        String isAnswer = item[1];
        if (StringUtils.isNotBlank(selection)) {
          String id = question.getId() + "_" + i;
          if (isAnswer.equals("1")) {
            answerId.add(id);
          }
          QuestionSelection questionSelection = new QuestionSelection();
          questionSelection.setId(id);
          if (request.getOptionType() == QuestionSelection.imageOption) {
            questionSelection.setImgUrl(selection);
            questionSelection.setOptionType(QuestionSelection.imageOption);
          } else {
            questionSelection.setSelection(selection);
            questionSelection.setOptionType(QuestionSelection.textOption);
          }
          selectionList.add(questionSelection);
          i = i + 1;
        }

      }
      questionBankQuestionModel.setAnswerIds(JSON.toJSONString(answerId));
      QuestionAnswers questionAnswers = new QuestionAnswers();
      questionAnswers.setOptionType(request.getOptionType());
      questionAnswers.setQuestionSelectionList(selectionList);
      questionBankQuestionModel.setAnswerList(JSON.toJSONString(questionAnswers));
    } else {
      String[] radioList = request.getRadioSelection().split(";");
      Integer i = 1;
      List<QuestionSelection> selectionList = new ArrayList<>();
      List<String> answerId = new ArrayList<>();
      for (String radioItem : radioList) {
        String[] item = radioItem.split("\\u007C"); // '|'
        String selection = item[0];
        String isAnswer = item[1];
        if (StringUtils.isNotBlank(selection)) {
          String id = question.getId() + "_" + i;
          if (isAnswer.equals("1")) {
            answerId.add(id);
          }
          QuestionSelection questionSelection = new QuestionSelection();
          questionSelection.setId(id);
          if (request.getOptionType() == QuestionSelection.imageOption) {
            questionSelection.setImgUrl(selection);
            questionSelection.setOptionType(QuestionSelection.imageOption);
          } else {

            questionSelection.setSelection(selection);
            questionSelection.setOptionType(QuestionSelection.textOption);
          }
          selectionList.add(questionSelection);
          i = i + 1;
        }
      }
      questionBankQuestionModel.setAnswerIds(JSON.toJSONString(answerId));
      QuestionAnswers questionAnswers = new QuestionAnswers();
      questionAnswers.setOptionType(request.getOptionType());
      questionAnswers.setQuestionSelectionList(selectionList);
      questionBankQuestionModel.setAnswerList(JSON.toJSONString(questionAnswers));
    }
    // }


    // 同步更新 xd_course_product_question
    try {
      productQuestionService.updateProductQuestionByQuestionId(questionBankQuestionModel.getId(),
          questionBankQuestionModel.getCognitionLevel(),
          questionBankQuestionModel.getDiffcultLevel(),
          questionBankQuestionModel.getQuestionType());
    } catch (Exception e) {
      LoggerUtil.error("同步更新course_product_question失败", e);
    }

    return this.editQuestion(questionBankQuestionModel);
  }

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public QuestionBankQuestionModel findResourceQuesById(Long id) {
    QuestionBankQuestionModel entity = new QuestionBankQuestionModel();
    entity.setId(id);
    return questionBankQuestionDao.findEntityById(entity);
  }



  /**
   * 导入文件的业务接口
   * 
   * @param url 文件存储在七牛上的url
   * @param courseId 导入试题所属的课程Id
   * @return
   */
  public String importQuestions(String url, String courseId) {
    /**************************/
    // 查找当前课下的所有试题来源并临时存储到Set内
//    HashSet<QuestionBankQuestionResourceModel> uniquQuestionSrc = Sets.newHashSet();
//    getAllQBQR(courseId, uniquQuestionSrc);

    // 题目类型 同上查找出所有并存储在Set内
    HashSet<QuestionBankQuestionTypeModel> uniquQBQT = Sets.newHashSet();
    getAllQBQT(uniquQBQT);

    /**************************/
    // 查找该课下的所有考点
    HashSet<CourseKeywordModel> uniquKeypoint = Sets.newHashSet();
    getAllKeypoint(uniquKeypoint, courseId);



    List<ImportQuestion> questionsList = null;

    try {
      questionsList = this.readExcel(url);
    } catch (Exception e) {
      return "<font color=\"red\">请严格按照模版格式上传文档!</font><br/>共检测到试题信息0条，其中有效试题信息<span id='verifyCount'>0</span>条，错误试题信息<span id='errorCount'>0</span>条。<br/>请上传正常格式与数据正常的excel！";
    }

    Integer count = 0;
    for (ImportQuestion iq : questionsList) {
      // 校验不通过，将无法进行插入操作
      if (!this.detectionResult(uniquKeypoint, null, uniquQBQT, iq, courseId)) continue;

      QuestionBankQuestionModel qbqm = new QuestionBankQuestionModel();
      Long chapterId = findChapterId(courseId, iq);
      if (chapterId != null) {
        qbqm.setChapterId(chapterId);
      }
      if (iq != null && iq.getChapterName() != null) {
        qbqm.setChapterName(iq.getChapterName());
      }
      if (iq != null && iq.getManalyze() != null) {
        qbqm.setManalyze(iq.getManalyze());
      }
      qbqm.setCognitionLevel(1);
      qbqm.setCourseId(Long.parseLong(courseId));
      qbqm.setDiffcultLevel(1);
      qbqm.setKeyPoint(null);
      if (iq != null && iq.getMdesc() != null) {
        qbqm.setMdesc(iq.getMdesc());
      }

      qbqm.setQuesTime(new Timestamp(System.currentTimeMillis()));
//      for (QuestionBankQuestionResourceModel importQuestion : uniquQuestionSrc) {
//        if (iq != null && iq.getQuestionSrc() != null
//            && iq.getQuestionSrc().equals(importQuestion.getName())) {
//          qbqm.setResourceId(importQuestion.getId());
//          break;
//        }
//      }
//      qbqm.setResourceId(0); // 试题来源的外键、因为不需要了、故统一设置成ID
      
      for (QuestionBankQuestionTypeModel qbqtm : uniquQBQT) {
        if (iq != null && iq.getQuestionType() != null
            && iq.getQuestionType().equals(qbqtm.getTypeName())) {
          qbqm.setQuestionType(qbqtm.getId());
          break;
        }
      }
      qbqm.setStatus(99);
      Integer isZt = 0;
      if (iq != null && iq.getZhenti() != null && "真题".equals(iq.getZhenti().trim())) {
        isZt = 1;
      }
      qbqm.setZhenti(isZt);

      for (CourseKeywordModel keypoint : uniquKeypoint) {
        if (iq != null && iq.getPoint() != null && iq.getPoint().equals(keypoint.getName())) {
          qbqm.setKeyPoint(keypoint.getId().toString());
          break;
        }
      }
      // writeen by zdhuang at 2018年7月30日 14:13:00
      qbqm.setKeyPoint(iq.getPoint());

      QuestionBankQuestionModel model = questionBankQuestionDao.addEntity(qbqm);
      if (StringUtils.isNotBlank(model.getId() + "")) {
        String answerIds = getAnswerIds(model.getId() + "", iq);
        model.setAnswerIds(answerIds);

        String standardAnswerIds = getStandardAnswerIds(model.getId() + "", iq);
        model.setAnswerList(standardAnswerIds);
      }

      HashMap<String, Object> cond = Maps.newHashMap();
      cond.put("id", model.getId());
      if (questionBankQuestionDao.updateEntity(cond, model)) count++;

      
      
      // 挂载考点
//      CourseKeywordResourceModel ckrm = new CourseKeywordResourceModel();
//      ckrm.setResourceType(ResourceType.QUESTION.getTypeId()); // TODO 此处在页面手动挂在的时候、resourceType写死是5
//      ckrm.setCreateTime(new Timestamp(System.currentTimeMillis()));
//      ckrm.setKeywordId(Long.parseLong(qbqm.getKeyPoint()));
//      ckrm.setResourceId(model.getId().longValue());
//      queueService.addKeyworkResourceAndUpdateCourseResource(ckrm);

    }
    return count.toString();
  }


  public void downloadExcel(String sheetName, String fileName, List<ImportQuestion> list,
      HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("chapterName", "章");
    fieldMap.put("sectionName", "节");
    fieldMap.put("questionSrc", "题目来源");
    fieldMap.put("zhenti", "是否真题");
    fieldMap.put("questionType", "题目类型");
    fieldMap.put("mdesc", "题干");
    fieldMap.put("manalyze", "解析");
    fieldMap.put("optA", "选项A");
    fieldMap.put("optB", "选项B");
    fieldMap.put("optC", "选项C");
    fieldMap.put("optD", "选项D");
    fieldMap.put("answerIds", "答案");
    fieldMap.put("point", "考点");

    fieldMap.put("msg", "");
    try {
      ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }

  }

  public String detectionExcelUrl(String url, String courseId) {
    String msg = "";
    List<ImportQuestion> studentList = null;
    try {
      studentList = this.readExcel(url);
    } catch (Exception e) {
      return "<font size=\"5\" color=\"red\">请严格按照模版格式上传文档!</font><br/>共检测到试题信息0条，其中有效试题信息<span id='verifyCount'>0</span>条，错误试题信息<span id='errorCount'>0</span>条。<br/>请上传正常格式与数据正常的excel！";
    }


    if (null == studentList || studentList.isEmpty())
      return "共检测到试题信息0条，其中有效试题信息<span id='verifyCount'>0</span>条，错误试题信息<span id='errorCount'>0</span>条。<br/>请上传正常格式与数据正常的excel！";
    Integer totalCount = studentList.size();
    Integer errorCount = 0;

    /**************************/
    // 查找当前课下的所有试题来源并临时存储到Set内
    HashSet<QuestionBankQuestionResourceModel> uniquQuestionSrc = Sets.newHashSet();
    getAllQBQR(courseId, uniquQuestionSrc);

    // 题目类型 同上查找出所有并存储在Set内
    HashSet<QuestionBankQuestionTypeModel> uniquQBQT = Sets.newHashSet();
    getAllQBQT(uniquQBQT);

    // 查找该课下的所有考点
    HashSet<CourseKeywordModel> uniquKeypoint = Sets.newHashSet();
    getAllKeypoint(uniquKeypoint, courseId);

    /**************************/

    for (ImportQuestion isdto : studentList) {
      boolean flag =
          this.detectionResult(uniquKeypoint, uniquQuestionSrc, uniquQBQT, isdto, courseId);
      if (!flag) errorCount++;
    }
    int verifyCount = totalCount - errorCount;
    msg = "共检测到试题信息" + totalCount + "条，其中有效试题信息<span id='verifyCount'>" + verifyCount
        + "</span>条，错误试题信息<span id='errorCount'>" + errorCount + "</span>条。错误数据已为您标出，可直接下载";
    return msg;
  }



  /**
   * 根据courseId获取其所属资源下的所有的考点
   * 
   * @param uniquKeypoint 存储某个课程下的所有考点
   * @param courseId 课程ID
   */
  private void getAllKeypoint(HashSet<CourseKeywordModel> uniquKeypoint,
      String courseIdOfResource) {
    IQueryParam param = new QueryParam();
    param.addOutput("id", 1);
    param.addInput("subjectId", courseIdOfResource);
    Page<CourseChapterModel> page = courseChapterDao.findEntityListByCond(param, null);
    // 如果资源表内生成改产品的资源数据被删除
    if (null == page || page.getResult().size() == 0) return;
    ArrayList<Long> bulks = Lists.newArrayList();
    for (CourseChapterModel cm : page.getResult()) {
      bulks.add(cm.getId());
    }

    if (bulks.size() <= 0) return;
    IQueryParam param0 = new QueryParam();
    Map<String, Object> allField = CommUtil.getAllField(CourseKeywordModel.class);
    allField.remove("chapterName");
    allField.remove("isSelected");
    param0.addOutputs(allField);
    param0.addInput("chapterIds", bulks);
    Page<CourseKeywordModel> page0 = courseKeywordDao.findEntityListByCond(param0, null);
    if (null != page0 && page0.getResult().size() > 0) uniquKeypoint.addAll(page0.getResult());
  }


  public List<ImportQuestion> readExcel(String urlString) {
    List<ImportQuestion> lineList = Lists.newArrayList();
    try {
      String excelType = urlString.substring(urlString.lastIndexOf(".") + 1);
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      InputStream is = conn.getInputStream();
      if (XdmsConstant.FILE_TYPE_XLS.equals(excelType)) {
        lineList = this.readXLSFile(is);
      } else if (XdmsConstant.FILE_TYPE_XLSX.equals(excelType)) {
        lineList = this.readXLSXFile(is);
      }
    } catch (Exception e) {
      LoggerUtil.error("excel检测异常！", e);
    }
    return lineList;
  }


  @SuppressWarnings("rawtypes")
  public List<ImportQuestion> readXLSFile(InputStream is)
      throws IllegalArgumentException, IllegalAccessException, IOException {
    Map<String, Integer> scoreCondMap = Maps.newHashMap();
    scoreCondMap.put("章", 0);
    scoreCondMap.put("节", 0);
    scoreCondMap.put("题目来源", 0);
    scoreCondMap.put("是否真题", 0);
    scoreCondMap.put("题目类型", 0);
    scoreCondMap.put("题干", 0);
    scoreCondMap.put("解析", 0);
    scoreCondMap.put("选项A", 0);
    scoreCondMap.put("选项B", 0);
    scoreCondMap.put("选项C", 0);
    scoreCondMap.put("选项D", 0);
    scoreCondMap.put("答案", 0);
    scoreCondMap.put("考点", 0);

    List<ImportQuestion> lineList = Lists.newArrayList();
    HSSFWorkbook wb = new HSSFWorkbook(is);
    // 获得第一个工作表对象
    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFRow row;
    HSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportQuestion.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (HSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportQuestion idto = new ImportQuestion();
      // 每列
      while (cells.hasNext()) {
        String result = StringUtils.EMPTY;
        cell = (HSSFCell) cells.next();
        if (cell.getCellNum() > fields.length - 2) break;
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
          result = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
          double number = cell.getNumericCellValue();
          DecimalFormat df = new DecimalFormat("0");
          result = df.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (scoreCondMap.containsKey(result)) {
            scoreCondMap.put(result, scoreCondMap.get(result) + 1);
            if (scoreCondMap.get(result) > 1) {
              throw new RuntimeException(XdmsConstant.PROMPT_HEADERERROR_MSG);
            }
          } else {
            throw new RuntimeException(XdmsConstant.PROMPT_HEADERERROR_MSG);
          }
        } else if (row.getRowNum() > sheet.getFirstRowNum()) {
          fields[cell.getCellNum()].set(idto, result);
        }
      }
      if (row.getRowNum() != sheet.getFirstRowNum() && !StringUtils.isAllBlank(idto.getAnswerIds(),
          idto.getChapterName(), idto.getManalyze(), idto.getPoint(), idto.getMdesc(), idto.getOptA(),
          idto.getOptB(), idto.getOptC(), idto.getOptD(),
          idto.getQuestionType(), idto.getSectionName(), idto.getZhenti()))
        lineList.add(idto);
    }
    return lineList;
  }

  @SuppressWarnings("rawtypes")
  public List<ImportQuestion> readXLSXFile(InputStream is)
      throws IllegalArgumentException, IllegalAccessException, IOException {
    Map<String, Integer> scoreCondMap = Maps.newHashMap();
    scoreCondMap.put("章", 0);
    scoreCondMap.put("节", 0);
    scoreCondMap.put("题目来源", 0);
    scoreCondMap.put("是否真题", 0);
    scoreCondMap.put("题目类型", 0);
    scoreCondMap.put("题干", 0);
    scoreCondMap.put("解析", 0);
    scoreCondMap.put("选项A", 0);
    scoreCondMap.put("选项B", 0);
    scoreCondMap.put("选项C", 0);
    scoreCondMap.put("选项D", 0);
    scoreCondMap.put("答案", 0);
    scoreCondMap.put("考点", 0);
    List<ImportQuestion> lineList = Lists.newArrayList();
    XSSFWorkbook wb = new XSSFWorkbook(is);
    // 获得第一个工作表对象
    XSSFSheet sheet = wb.getSheetAt(0);
    XSSFRow row;
    XSSFCell cell;
    Iterator rows = sheet.rowIterator();
    Field[] fields = ImportQuestion.class.getDeclaredFields();
    while (rows.hasNext()) {
      row = (XSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      ImportQuestion idto = new ImportQuestion();
      // 每列
      while (cells.hasNext()) {
        String result = StringUtils.EMPTY;
        cell = (XSSFCell) cells.next();
        if (cell.getColumnIndex() > fields.length - 2) break;
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
          result = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
          double number = cell.getNumericCellValue();
          DecimalFormat df = new DecimalFormat("0");
          result = df.format(number);
        } else {
          // U Can Handel Boolean, Formula, Errors
        }
        if (row.getRowNum() == sheet.getFirstRowNum()) {
          if (scoreCondMap.containsKey(result)) {
            scoreCondMap.put(result, scoreCondMap.get(result) + 1);
            if (scoreCondMap.get(result) > 1) {
              throw new RuntimeException(XdmsConstant.PROMPT_HEADERERROR_MSG);
            }
          } else {
            throw new RuntimeException(XdmsConstant.PROMPT_HEADERERROR_MSG);
          }
        } else if (row.getRowNum() > sheet.getFirstRowNum()) {
          fields[cell.getColumnIndex()].set(idto, result);
        }
      }
      if (row.getRowNum() != sheet.getFirstRowNum() && !StringUtils.isAllBlank(idto.getAnswerIds(),
          idto.getChapterName(), idto.getManalyze(),idto.getPoint(), idto.getMdesc(), idto.getOptA(),
          idto.getOptB(), idto.getOptC(), idto.getOptD(),
          idto.getQuestionType(), idto.getSectionName(), idto.getZhenti()))
        lineList.add(idto);
    }
    return lineList;
  }



  /**
   * 读取七牛上的Excel文件，首行不进行读取， 解析其内容并且封装成List<ImportQuestioin> 进行返回
   * 
   * 读取Excel->ImportQuestion
   * 
   * @throws Exception
   */
  @Deprecated
  public List<ImportQuestion> readExcel0(String urlString) throws Exception {
    List<ImportQuestion> lineList = Lists.newArrayList();
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    InputStream is = conn.getInputStream();
    Workbook book = Workbook.getWorkbook(is);
    // 获得第一个工作表对象
    Sheet sheet = book.getSheet(0);
    // j:列，i:行
    // msg在不读取
    Field[] fields = ImportQuestion.class.getDeclaredFields();
    RuntimeException ex = new RuntimeException("请严格按照模版格式上传文档");
    int col = fields.length - 2;

    for (int i = 0; i <= sheet.getRows() - 1; i++) {
      String rowStr = "{";
      for (int j = 0; j <= col; j++) {
        if (i == 0) {
          Cell cell = sheet.getCell(j, i);
          if (null == cell) continue;
          String result = cell.getContents();
          if (j == 0 && !result.trim().equals("章")) {
            throw ex;
          }
          if (j == 1 && !result.trim().equals("节")) {
            throw ex;
          }
          if (j == 2 && !result.trim().equals("题目来源")) {
            throw ex;
          }
          if (j == 3 && !result.trim().equals("是否真题")) {
            throw ex;
          }
          if (j == 4 && !result.trim().equals("题目类型")) {
            throw ex;
          }
          if (j == 5 && !result.trim().equals("题干")) {
            throw ex;
          }
          if (j == 6 && !result.trim().equals("解析")) {
            throw ex;
          }
          if (j == 7 && !result.trim().equals("选项A")) {
            throw ex;
          }
          if (j == 8 && !result.trim().equals("选项B")) {
            throw ex;
          }
          if (j == 9 && !result.trim().equals("选项C")) {
            throw ex;
          }
          if (j == 10 && !result.trim().equals("选项D")) {
            throw ex;
          }
          if (j == 11 && !result.trim().equals("答案")) {
            throw ex;
          }
          if (j == 12 && !result.trim().equals("考点")) {
            throw ex;
          }
          if (j > 12) {
            throw ex;
          }
          continue;
        }
        Cell cell = sheet.getCell(j, i);
        if (null == cell) break;
        String result = cell.getContents();
        if (StringUtils.isNotBlank(result))
          rowStr += String.format("%s:\"%s\",", fields[j].getName(), result.trim());
      }
      if (!"{".equals(rowStr)) {
        rowStr = rowStr.substring(0, rowStr.lastIndexOf(",")) + "}";
        try {
          ImportQuestion headTest = FastJsonUtil.fromJson(rowStr, ImportQuestion.class);
          lineList.add(headTest);
        } catch (Exception e) {
          LoggerUtil.error("解析excel第" + i + "行出错(标题不算首行)，注意中英文的双引号。", e);
        }

      }
    }
    book.close();
    return lineList;
  }

  /**
   * 创建错误信息的Excel表格
   * 
   * @param url
   * @param chooseCourseId
   * @return
   */
  public List<ImportQuestion> createErrorExcel(String url, String chooseCourseId) {
    List<ImportQuestion> studentList = null;
    try {
      studentList = this.readExcel(url);
    } catch (Exception e) {
      LoggerUtil.error("读取Excel出错", e);
    }
    ArrayList<ImportQuestion> error_list = Lists.newArrayList();
    /**************************/
    // 查找当前课下的所有试题来源并临时存储到Set内
//    HashSet<QuestionBankQuestionResourceModel> uniquQuestionSrc = Sets.newHashSet();
//    getAllQBQR(chooseCourseId, uniquQuestionSrc);

    // 题目类型 同上查找出所有并存储在Set内
    HashSet<QuestionBankQuestionTypeModel> uniquQBQT = Sets.newHashSet();
    getAllQBQT(uniquQBQT);


    // 查找该课下的所有考点
    HashSet<CourseKeywordModel> uniquKeypoint = Sets.newHashSet();
    getAllKeypoint(uniquKeypoint, chooseCourseId);


    /**************************/

    for (ImportQuestion iq : studentList) {
      StringBuffer msg = new StringBuffer(200);

      // 章
      if (StringUtils.isBlank(iq.getChapterName())) {
        msg.append(" 章不允许为空");
      }
      // 节
      if (StringUtils.isBlank(iq.getSectionName())) {
        msg.append(" 节不允许为空");
      }

      // 查找课程Id
      Long chapterId = findChapterId(chooseCourseId, iq);
      if (chapterId == null) {
        msg.append(" 该课下没有对应章节");
      }
      // 试题来源
//      if (uniquQuestionSrc != null && uniquQuestionSrc.size() > 0) {
//        boolean flag = false;
//        for (QuestionBankQuestionResourceModel object : uniquQuestionSrc) {
//          if (iq != null && iq.getQuestionSrc() != null
//              && iq.getQuestionSrc().trim().equals(object.getName())) {
//            flag = true;
//            break;
//          }
//        }
//        if (!flag) {
//          msg.append(" 课程下该试题来源不存在");
//        }
//
//      } else
//        msg.append(" 课程下该试题来源不存在");

      // 是否是真题
      if (iq != null && StringUtils.isBlank(iq.getZhenti()))
        msg.append(" 是否是真题不允许为空");
      else if (iq != null && iq.getZhenti() != null
          && !(iq.getZhenti().trim().equals("真题") || iq.getZhenti().trim().equals("非真题"))) {
        msg.append(" 是否是真题不匹配(可选择: 真题/非真题)");
      }

      // 题目类型
      if (uniquQBQT != null && uniquQBQT.size() > 0) {
        boolean flag = false;
        for (QuestionBankQuestionTypeModel object : uniquQBQT) {
          if (iq != null && iq.getQuestionType() != null
              && iq.getQuestionType().trim().equals(object.getTypeName())) {
            flag = true;
            break;
          }
        }
        if (!flag) {
          msg.append(" 不存在该题目类型");
        }
      } else {
        msg.append(" 该试题类型不存在");
      }
      // 题干
      if (StringUtils.isBlank(iq.getMdesc())) {
        msg.append(" 题干不允许为空");
      }
      // 解析
      if (StringUtils.isBlank(iq.getManalyze())) {
        msg.append(" 解析不允许为空");
      }
      // 选项A
      if (StringUtils.isBlank(iq.getOptA())) {
        msg.append(" 选项A不允许为空");
      }
      // 选项B
      if (StringUtils.isBlank(iq.getOptB())) {
        msg.append(" 选项B不允许为空");
      }
      // 选项C
      if (StringUtils.isBlank(iq.getOptC())) {
        msg.append(" 选项C不允许为空");
      }
      // 选项D
      if (StringUtils.isBlank(iq.getOptD())) {
        msg.append(" 选项D不允许为空");
      }

      // 正确答案
      if (StringUtils.isBlank(iq.getAnswerIds())) {
        msg.append(" 答案不允许为空");
      }

      if (StringUtils.isNotBlank(iq.getQuestionType()) && iq.getQuestionType().equals("单项选择题")) {
        if (StringUtils.isNotBlank(iq.getAnswerIds())) {
          boolean flag = iq.getAnswerIds().trim().matches("[a-zA-Z]");
          if (!flag) msg.append("答案应该是[A/a,B/b]");
        }
      }

      if (StringUtils.isNotBlank(iq.getQuestionType()) && iq.getQuestionType().equals("多项选择题")) {
        if (StringUtils.isNotBlank(iq.getAnswerIds())) {
          boolean flag = iq.getAnswerIds().trim().matches("[a-zA-Z]{2,4}");
          if (!flag) msg.append("题型与答案不一致");
        }
      }


      // 校验考点
   // 选项C
      if (StringUtils.isBlank(iq.getPoint())) {
        msg.append(" 考点不允许为空");
      }
      
//      if (chapterId != null) {
//        if (StringUtils.isBlank(iq.getPoint().trim())) {
//          msg.append(" 考点不允许为空");
//        } else {
//          IQueryParam param0 = new QueryParam();
//          param0.addInput("chapterId", chapterId);
//          Map<String, Object> allField = CommUtil.getAllField(CourseKeywordModel.class);
//          allField.remove("chapterName");
//          allField.remove("isSelected");
//          param0.addOutputs(allField);
//          Page<CourseKeywordModel> page0 = courseKeywordDao.findEntityListByCond(param0, null);
//          int label = 0;
//          if (null != page0 && page0.getResult().size() != 0) {
//            for (CourseKeywordModel model : page0.getResult()) {
//              if (model.getName() != null && model.getName().equals(iq.getPoint().trim())) {
//                label++;
//              }
//            }
//          } else {
//            msg.append(" 该章节下未挂载任何考点");
//          }
//          if (label == 0) {
//            msg.append(" 该章节下不包含该考点");
//          }
//        }
//      }
      iq.setMsg(msg.toString());
      error_list.add(iq);
    }
    return error_list;
  }

  private void getAllQBQT(HashSet<QuestionBankQuestionTypeModel> uniquQBQT) {
    IQueryParam param_QBQT = new QueryParam();
    param_QBQT.addOutput("id", "");
    param_QBQT.addOutput("typeName", "");
    Page<QuestionBankQuestionTypeModel> _qbqt =
        questionBankQuestionTypeDao.findEntityListByCond(param_QBQT, null);
    if (_qbqt != null && _qbqt.getResult().size() > 0) {
      for (QuestionBankQuestionTypeModel qbqt : _qbqt.getResult()) {
        uniquQBQT.add(qbqt);
      }
    }
  }

  // 初始化存放来源列表，为了 性能
  private void getAllQBQR(String chooseCourseId,
      HashSet<QuestionBankQuestionResourceModel> uniquQuestionSrc) {
    IQueryParam param = new QueryParam();
    param.addInput("cId", chooseCourseId.trim());
    param.addOutput("id", "");
    param.addOutput("name", "");
    Page<QuestionBankQuestionResourceModel> _qbqr =
        questionBankQuestionResourceDao.findEntityListByCond(param, null);
    if (_qbqr != null && _qbqr.getResult().size() > 0) {
      for (QuestionBankQuestionResourceModel qbqr : _qbqr.getResult()) {
        uniquQuestionSrc.add(qbqr);
      }
    }
  }

  // 获取数据格式形如: ["25985_1","25985_2","25985_3","25985_4"]的接口
  private String getAnswerIds(String primarkId, ImportQuestion iq) {
    JSONArray arr = new JSONArray();
    INPUTS: {
      if (null == iq || iq.getAnswerIds() == null) break INPUTS;
      if (iq.getAnswerIds().contains("A") || iq.getAnswerIds().contains("a")) {
        arr.add(primarkId + "_1");
      }
      if (iq.getAnswerIds().contains("B") || iq.getAnswerIds().contains("b")) {
        arr.add(primarkId + "_2");
      }
      if (iq.getAnswerIds().contains("C") || iq.getAnswerIds().contains("c")) {
        arr.add(primarkId + "_3");
      }
      if (iq.getAnswerIds().contains("D") || iq.getAnswerIds().contains("d")) {
        arr.add(primarkId + "_4");
      }
    }

    return arr.toJSONString();
  }

  // 获取形如: {
  // "optionType":1,
  // "questionSelectionList":[
  // {"id":"25986_1","optionType":1,"selection":"物质文明"},
  // {"id":"25986_2","optionType":1,"selection":"和谐社会"},
  // {"id":"25986_3","optionType":1,"selection":"精神文明"},
  // {"id":"25986_4","optionType":1,"selection":"生态文明"}
  // ]
  // }
  // 的接口
  //
  private String getStandardAnswerIds(String primaryId, ImportQuestion iq) {
    ArrayList<Object> answers = Lists.newArrayList();
    HashMap<String, Object> answerIds = Maps.newHashMap();
    INPUTS: {
      if (iq == null) break INPUTS;
      OPTA: {
        if (iq.getOptA() == null) break OPTA;
        HashMap<String, Object> _A = Maps.newHashMap();
        _A.put("id", primaryId + "_1");
        _A.put("optionType", 1);
        _A.put("selection", iq.getOptA());
        answers.add(_A);
      }
      OPTB: {
        if (iq.getOptB() == null) break OPTB;
        HashMap<String, Object> _B = Maps.newHashMap();
        _B.put("id", primaryId + "_2");
        _B.put("optionType", 1);
        _B.put("selection", iq.getOptB());
        answers.add(_B);
      }
      OPTC: {
        if (iq.getOptC() == null) break OPTC;
        HashMap<String, Object> _C = Maps.newHashMap();
        _C.put("id", primaryId + "_3");
        _C.put("optionType", 1);
        _C.put("selection", iq.getOptC());
        answers.add(_C);
      }
      OPTD: {
        if (iq.getOptD() == null) break OPTD;
        HashMap<String, Object> _D = Maps.newHashMap();
        _D.put("id", primaryId + "_4");
        _D.put("optionType", 1);
        _D.put("selection", iq.getOptD());
        answers.add(_D);
      }
    }

    answerIds.put("questionSelectionList", answers);
    answerIds.put("optionType", 1);


    return FastJsonUtil.toJson(answerIds);
  }

  /**
   * 根据章、节(自关联)寻找课程Id
   * 
   * @param courseId
   * @param iq
   * @return
   */
  private Long findChapterId(String courseId, ImportQuestion iq) {
    try {
      Map<String, Object> input = Maps.newHashMap();
      if (iq != null && iq.getChapterName() != null) {
        input.put("s_index_zhang", iq.getChapterName().trim());
      }
      if (iq != null && iq.getSectionName() != null) {
        input.put("s_index_jie", iq.getSectionName().trim());
      }
      input.put("_subjectId", courseId);
      Map<String, Object> output = Maps.newHashMap();
      output.put("id", "");
      Page<CourseChapterModel> res = courseChapterDao.findChapterId(input, output);
      if (res != null && res.getResult().size() > 0) {
        CourseChapterModel result = res.getResult().get(0);
        return result.getId() == null ? null : result.getId();
      }
    } catch (Exception e) {
      LoggerUtil.error("导入试题，课程id为控", e);
    }

    return null;
  }

  /**
   * 对一条Excel数据的映射模型，进行校验操作
   * 
   * @param uniquQuestionSrc
   * @param uniquQBQT
   * @param iq
   * @param courseId
   * @return
   */
  private boolean detectionResult(Set<CourseKeywordModel> uniquKeypoint,
      Set<QuestionBankQuestionResourceModel> uniquQuestionSrc,
      Set<QuestionBankQuestionTypeModel> uniquQBQT, ImportQuestion iq, String courseId) {
    boolean flag = true;

    // 章
    if (StringUtils.isBlank(iq.getChapterName())) {
      flag = false;
      return flag;
    }
    // 节
    if (StringUtils.isBlank(iq.getSectionName())) {
      flag = false;
      return flag;
    }


    // 查找课程Id
    Long chapterId = findChapterId(courseId, iq);
    if (chapterId == null) {
      flag = false;
      return flag;
    }
    // 试题来源
//    if (uniquQuestionSrc != null && uniquQuestionSrc.size() > 0) {
//      boolean label = false;
//      for (QuestionBankQuestionResourceModel object : uniquQuestionSrc) {
//        if (iq != null && iq.getQuestionSrc() != null && object != null
//            && iq.getQuestionSrc().trim().equals(object.getName())) {
//          label = true;
//          break;
//        }
//      }
//      if (!label) {
//        flag = false;
//        return flag;
//      }
//    } else {
//      flag = false;
//      return flag;
//    }

    // 是否是真题
    if (iq != null && iq.getZhenti() != null) {
      if (!(iq.getZhenti().trim().equals("真题") || iq.getZhenti().trim().equals("非真题"))) {
        flag = false;
        return flag;
      }
    } else if (iq != null && StringUtils.isBlank(iq.getZhenti())) {
      flag = false;
      return flag;
    }



    // 题目类型
    if (uniquQBQT != null && uniquQBQT.size() > 0) {
      boolean label = false;
      for (QuestionBankQuestionTypeModel object : uniquQBQT) {
        if (iq != null && iq.getQuestionType() != null
            && iq.getQuestionType().trim().equals(object.getTypeName())) {
          label = true;
          break;
        }
      }
      if (!label) {
        flag = false;
        return flag;
      }
    } else {
      flag = false;
      return flag;
    }

    // 题干
    if (StringUtils.isBlank(iq.getMdesc())) {
      flag = false;
      return flag;
    }

    // 解析
    if (StringUtils.isBlank(iq.getManalyze())) {
      flag = false;
      return flag;
    }

    // 选项A
    if (StringUtils.isBlank(iq.getOptA())) {
      flag = false;
      return flag;
    }
    // 选项B
    if (StringUtils.isBlank(iq.getOptB())) {
      flag = false;
      return flag;
    }
    // 选项C
    if (StringUtils.isBlank(iq.getOptC())) {
      flag = false;
      return flag;
    }
    // 选项D
    if (StringUtils.isBlank(iq.getOptD())) {
      flag = false;
      return flag;
    }

    // 答案
    if (StringUtils.isBlank(iq.getAnswerIds())) {
      flag = false;
      return flag;
    }

    if (StringUtils.isNotBlank(iq.getQuestionType()) && iq.getQuestionType().equals("单项选择题")) {
      if (StringUtils.isNotBlank(iq.getAnswerIds()))
        flag = iq.getAnswerIds().trim().matches("[a-zA-Z]");
      if (!flag) return flag;
    }

    if (StringUtils.isNotBlank(iq.getQuestionType()) && iq.getQuestionType().equals("多项选择题")) {
      if (StringUtils.isNotBlank(iq.getAnswerIds()))
        flag = iq.getAnswerIds().trim().matches("[a-zA-Z]{2,4}");
      if (!flag) return flag;
    }



    // 考点
    if (uniquKeypoint != null && uniquKeypoint.size() > 0) {
      boolean label = false;
      for (CourseKeywordModel object : uniquKeypoint) {
        if (iq != null && iq.getPoint() != null && iq.getPoint().trim().equals(object.getName())) {
          label = true;
          break;
        }
      }
      if (!label) {
        flag = false;
        return flag;
      }
    } else {
      flag = false;
      return flag;
    }

    // 校验考点
    IQueryParam param0 = new QueryParam();
    param0.addInput("chapterId", chapterId);
    Map<String, Object> allField = CommUtil.getAllField(CourseKeywordModel.class);
    allField.remove("chapterName");
    allField.remove("isSelected");
    param0.addOutputs(allField);
    Page<CourseKeywordModel> page0 = courseKeywordDao.findEntityListByCond(param0, null);
    int label = 0;
    if (null != page0 && page0.getResult().size() != 0) {
      for (CourseKeywordModel model : page0.getResult()) {
        if (model.getName() != null && model.getName().equals(iq.getPoint().trim())) {
          label++;
        }
      }
    } else {
      flag = false;
      return flag;
    }
    if (label == 0) {
      flag = false;
      return flag;
    }


    return flag;
  }

  public Page<RaiseWrongQues> findAllErrorRecord(Integer pageNo) {

    Page<RaiseWrongQues> page = new Page<RaiseWrongQues>();
    page.setPageNo(pageNo);
    page.setPageSize(20);

    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("userId", "");
    output.put("courseId", "");
    output.put("chapterId", "");
    output.put("quesId", "");
    output.put("wrongType", "");
    output.put("wrongMsg", "");
    output.put("createTime", "");
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(output);
    param.addSort("id", Sort.DESC);
    Page<RaiseWrongQues> lists = raiseWrongQuesDao.findEntityListByCond(param, page);
    return lists;
  }

}
