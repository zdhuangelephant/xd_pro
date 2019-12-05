package com.xiaodou.ms.web.controller.exam;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.model.exam.ExamRule;
import com.xiaodou.ms.model.exam.ExamRuleItem;
import com.xiaodou.ms.model.exam.QuestionBankExamRuleModel;
import com.xiaodou.ms.model.exam.QuestionBankExamTypeModel.ExamType;
import com.xiaodou.ms.model.exam.QuestionBankQuestionType;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.exam.QuestionBankSetting;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.service.exam.QuestionBankExamRuleService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * Created by zyp on 15/6/25.
 */
@Controller("examRuleController")
@RequestMapping("examRule")
public class ExamRuleController extends BaseController {

  @Resource
  ProductService productService;

  @Resource
  ProductItemService productItemService;

  @Resource
  QuestionBankExamRuleService questionBankExamRuleService;

  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  /**
   * 规则列表
   * 
   * @param productId
   * @return
   */
  @RequestMapping("default_list")
  public ModelAndView defaultRuleList() {
    ModelAndView modelAndView = new ModelAndView("/exam/defaultRuleList");
    List<QuestionBankExamRuleModel> questionBankExamRuleModels =
        questionBankExamRuleService.findDefaultRuleList();
    for (QuestionBankExamRuleModel model : questionBankExamRuleModels) {
      if (null == model || null == model.getExamTypeId()) continue;
      model.setExamTypeName(ExamType.getByCode(model.getExamTypeId().toString()).getShowName());
    }
    modelAndView.addObject("rules", questionBankExamRuleModels);
    return modelAndView;
  }

  /**
   * 规则列表
   * 
   * @param productId
   * @return
   */
  @RequestMapping("list")
  public ModelAndView ruleList(Long productId) {
    ModelAndView modelAndView = new ModelAndView("/exam/ruleList");
    List<QuestionBankExamRuleModel> questionBankExamRuleModels =
        questionBankExamRuleService.findExamRulesByProductId(productId);
    for (QuestionBankExamRuleModel model : questionBankExamRuleModels) {
      if (null == model || null == model.getExamTypeId()) continue;
      model.setExamTypeName(ExamType.getByCode(model.getExamTypeId().toString()).getShowName());
    }
    modelAndView.addObject("rules", questionBankExamRuleModels);
    return modelAndView;
  }

  /**
   * 章节规则列表
   * 
   * @param productId
   * @param chapterId
   * @return
   */
  @RequestMapping("chapterRuleList")
  public ModelAndView chapterRuleList(Integer productId, Integer chapterId) {
    ModelAndView modelAndView = new ModelAndView("/exam/chapterRuleList");
    List<QuestionBankExamRuleModel> questionBankExamRuleModels =
        questionBankExamRuleService.findChapterRules(productId, chapterId);
    for (QuestionBankExamRuleModel model : questionBankExamRuleModels) {
      if (null == model || null == model.getExamTypeId()) continue;
      model.setExamTypeName(ExamType.getByCode(model.getExamTypeId().toString()).getShowName());
    }
    modelAndView.addObject("rules", questionBankExamRuleModels);
    return modelAndView;
  }

  /**
   * 编辑出题规则
   * 
   * @param ruleId
   * @return
   */
  @RequestMapping("editChapterRule")
  public ModelAndView editChapterRule(Integer ruleId) {
    ModelAndView modelAndView = new ModelAndView("/exam/chapterRuleEdit");

    // 出题规则
    QuestionBankExamRuleModel chapterRule = questionBankExamRuleService.findEntityById(ruleId);
    modelAndView.addObject("chapterRule", chapterRule);

    // 出题数目规则
    ExamRule examRule = JSON.parseObject(chapterRule.getRuleDetail(), ExamRule.class);
    Map<String, Long> numMap = new HashMap<>();
    for (ExamRuleItem ruleItem : examRule.getItemList()) {
      String key =
          "chapter_" + ruleItem.getProductChapterId() + "_" + ruleItem.getQuestionType() + "_"
              + convertEasyLevel(ruleItem.getEasyLevel()) + "_"
              + convertCognitionLevel(ruleItem.getCognitionLevel());
      numMap.put(key, ruleItem.getQuestionNum());
    }
    modelAndView.addObject("numMap", numMap);
    if (examRule.getHasChildChapters() != null && examRule.getHasChildChapters().size() > 0) {
      Map<String, String> hasChildChaptersMap = new HashMap<>();
      for (String key : examRule.getHasChildChapters()) {
        hasChildChaptersMap.put("chapter" + key, "1");
      }
      modelAndView.addObject("hasChildChaptersMap", hasChildChaptersMap);
    }

    // 章节信息
    ProductItemModel chapter = productItemService.findItemById(chapterRule.getChapterId());
    List<ProductItemModel> childChapters =
        productItemService.queryChapterItemByParentId(chapterRule.getChapterId(),
            chapter.getProductId());
    modelAndView.addObject("chapter", chapter);
    modelAndView.addObject("childChapters", childChapters);

    // 产品信息
    ProductModel product = productService.findSubjectById(chapter.getProductId());
    modelAndView.addObject("product", product);

    // 题目类型信息
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    modelAndView.addObject("setting", setting);
    List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
    List<QuestionBankQuestionType> keguanList = new ArrayList<>();
    for (QuestionBankQuestionType type : setting.getTypeList()) {
      if (type.getAnswerType().equals("0")) {
        keguanList.add(type);
      } else {
        zhuguanList.add(type);
      }
    }
    modelAndView.addObject("zhuguan", zhuguanList);
    modelAndView.addObject("keguan", keguanList);

    return modelAndView;
  }

  /**
   * 增加章节rule
   * 
   * @param ruleName
   * @param request
   * @return
   */
  @RequestMapping("/doEditChapterRule")
  public ModelAndView doEditChapterRule(Integer id, String ruleName, HttpServletRequest request) {

    ExamRule examRule = new ExamRule();

    QuestionBankExamRuleModel chapterRule = questionBankExamRuleService.findEntityById(id);

    // 产品信息
    ProductModel product = productService.findSubjectById(chapterRule.getProductId());
    // 1.章节信息
    List<ProductItemModel> childChapters =
        productItemService.queryChapterItemByParentId(chapterRule.getChapterId(),
            chapterRule.getProductId());
    ProductItemModel chapter = productItemService.findItemById(chapterRule.getChapterId());
    chapter.setChildList(childChapters);
    // 2.难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");
    // 3.认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");
    // 4.题型信息
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();

    List<ExamRuleItem> itemList = new ArrayList<>();

    // 有子章节的章节
    String[] hasChildChapters = request.getParameterValues("setChildChapter");
    if (hasChildChapters != null && hasChildChapters.length > 0) {
      examRule.setHasChildChapters(Arrays.asList(hasChildChapters));
      for (ProductItemModel childChapter : chapter.getChildList()) {
        itemList.addAll(this.buildRuleList(product, childChapter, request, questionTypes,
            easyLevelList, cognitionLevelList, ExamRuleItem.childChapter));
      }
    } else {
      examRule.setHasChildChapters(new ArrayList<String>());
      itemList.addAll(this.buildRuleList(product, chapter, request, questionTypes, easyLevelList,
          cognitionLevelList, ExamRuleItem.chapter));
    }

    examRule.setItemList(itemList);
    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setId(id);
    questionBankExamRuleModel.setName(ruleName);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    questionBankExamRuleService.edit(questionBankExamRuleModel);
    return this.showMessage("更新成功", null, "/examRule/editChapterRule?ruleId=" + id, true);
  }

  /**
   * 增加章节rule
   * 
   * @param productId
   * @param chapterId
   * @param ruleName
   * @param request
   * @return
   */
  @RequestMapping("/doAddChapterRule")
  public ModelAndView doAddChapterRule(Long productId, Long chapterId, String ruleName,
      HttpServletRequest request) {

    ExamRule examRule = new ExamRule();
    // 1.章节信息
    List<ProductItemModel> childChapters =
        productItemService.queryChapterItemByParentId(chapterId, productId);
    ProductItemModel chapter = productItemService.findItemById(chapterId);
    chapter.setChildList(childChapters);
    // 2.难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");
    // 3.认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");
    // 4.题型信息
    ProductModel product = productService.findSubjectById(productId);
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();

    List<ExamRuleItem> itemList = new ArrayList<>();

    // 有子章节的章节
    String[] hasChildChapters = request.getParameterValues("setChildChapter");
    if (hasChildChapters != null && hasChildChapters.length > 0) {
      examRule.setHasChildChapters(Arrays.asList(hasChildChapters));
      for (ProductItemModel childChapter : chapter.getChildList()) {
        itemList.addAll(this.buildRuleList(product, childChapter, request, questionTypes,
            easyLevelList, cognitionLevelList, ExamRuleItem.childChapter));
      }
    } else {
      examRule.setHasChildChapters(new ArrayList<String>());
      itemList.addAll(this.buildRuleList(product, chapter, request, questionTypes, easyLevelList,
          cognitionLevelList, ExamRuleItem.chapter));
    }

    examRule.setItemList(itemList);
    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setName(ruleName);
    questionBankExamRuleModel.setExamTypeId(Integer.parseInt(request.getParameter("ruleType")));
    questionBankExamRuleModel.setProductId(productId);
    questionBankExamRuleModel.setStatus(-1);
    questionBankExamRuleModel.setChapterId(chapterId);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    questionBankExamRuleService.addEntity(questionBankExamRuleModel);
    return this.showMessage("添加成功", null, null, true);
  }

  /**
   * 添加规则
   * 
   * @param request
   * @return
   */
  @RequestMapping("do_add_default_rule")
  public ModelAndView doAddDefaultRule(HttpServletRequest request) {
    String ruleType = request.getParameter("ruleType");
    if (ExamType.UNKNOWN.getCode().equals(ruleType)) {
      return this.showMessage("添加失败.<br>非法练习类型.", null, null, true);
    }
    ExamRule examRule = new ExamRule();

    // 难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");

    // 认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");

    // 题型信息
    List<QuestionBankQuestionType> questionTypes = Lists.newArrayList();
    List<QuestionBankQuestionTypeModel> questionBankQuestionTypeList =
        questionBankQuestionTypeService.typeList();
    for (QuestionBankQuestionTypeModel typeModel : questionBankQuestionTypeList) {
      QuestionBankQuestionType type = new QuestionBankQuestionType(typeModel);
      if (null != type) questionTypes.add(type);
    }


    List<ExamRuleItem> itemList = new ArrayList<>();
    // 2017_11_09 修改出题规则逻辑,如果章节规则为空,尝试添加课程规则
    if (itemList.isEmpty()) {
      itemList.addAll(buildProductRuleList(XdmsConstant.COMMON_PRODUCT_ID.longValue(), request,
          questionTypes, easyLevelList, cognitionLevelList));
    }

    // 添加出题数量不为空限制
    if (null == itemList || itemList.isEmpty()) {
      return this.showMessage("添加失败.<br>出题数量不能为空.", null, null, true);
    }

    examRule.setItemList(itemList);

    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setName(request.getParameter("ruleName"));
    questionBankExamRuleModel.setExamTypeId(Integer.parseInt(ruleType));
    questionBankExamRuleModel.setProductId(XdmsConstant.COMMON_PRODUCT_ID.longValue());
    questionBankExamRuleModel.setStatus(XdmsConstant.INIT_STATUS);
    questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_DEFAULT);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    QuestionBankExamRuleModel result = questionBankExamRuleService.addEntity(questionBankExamRuleModel);
    Map<String, Object> cond = new HashMap<>();
    cond.put("id",result.getId());
    QuestionBankExamRuleModel ruleModel = new QuestionBankExamRuleModel();
    ruleModel.setStatus(XdmsConstant.NORMAL_STATUS);
    questionBankExamRuleService.editByCond(cond, ruleModel);
    return this.showMessage("添加成功", null, null, true);
  }


  /**
   * 添加规则
   * 
   * @param request
   * @return
   */
  @RequestMapping("doAddRule")
  public ModelAndView doAddRule(Long productId, HttpServletRequest request) {
    String ruleType = request.getParameter("ruleType");
    if (ExamType.UNKNOWN.getCode().equals(ruleType)) {
      return this.showMessage("添加失败.<br>非法练习类型.", null, null, true);
    }
    ExamRule examRule = new ExamRule();

    // 有子章节的章节
    String[] hasChildChapters = request.getParameterValues("setChildChapter");
    Map<Integer, Object> hasChildChaptersMap = new HashMap<>();
    if (hasChildChapters != null && hasChildChapters.length > 0) {
      for (String hasChildChapter : hasChildChapters) {
        hasChildChaptersMap.put(Integer.parseInt(hasChildChapter), null);
      }
    }


    // 重点章节
    String[] importChapters = request.getParameterValues("importChapter");
    if (hasChildChapters != null) {
      examRule.setHasChildChapters(Arrays.asList(hasChildChapters));
    }
    if (importChapters != null) {
      examRule.setImportChapters(Arrays.asList(importChapters));
    }

    // 难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");

    // 认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");

    // 章节信息
    List<ProductItemModel> chapterList = productItemService.queryAllChapterItem(productId);
    Map<String, ProductItemModel> chapterMap = new HashMap<>();
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() == 0) {
        chapterMap.put(chapter.getId().toString(), chapter);
      }
    }
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() != 0) {
        ProductItemModel parent = chapterMap.get(chapter.getParentId().toString());
        List<ProductItemModel> childChapterList = parent.getChildList();
        childChapterList.add(chapter);
        parent.setChildList(childChapterList);
        chapterMap.put(chapter.getParentId().toString(), parent);
      }
    }
    List<ProductItemModel> chapters = new ArrayList<>();
    for (String key : chapterMap.keySet()) {
      chapters.add(chapterMap.get(key));
    }

    // 题型信息
    ProductModel product = productService.findSubjectById(productId);
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();


    List<ExamRuleItem> itemList = new ArrayList<>();
    // 章节
    for (ProductItemModel chapter : chapters) {
      // 设置
      if (hasChildChaptersMap.containsKey(chapter.getId())) {
        for (ProductItemModel childChapter : chapter.getChildList()) {
          itemList.addAll(buildRuleList(product, childChapter, request, questionTypes,
              easyLevelList, cognitionLevelList, ExamRuleItem.childChapter));
        }
      } else {
        itemList.addAll(buildRuleList(product, chapter, request, questionTypes, easyLevelList,
            cognitionLevelList, ExamRuleItem.chapter));
      }
    }
    // 2017_11_09 修改出题规则逻辑,如果章节规则为空,尝试添加课程规则
    if (itemList.isEmpty()) {
      itemList.addAll(buildProductRuleList(product.getId(), request, questionTypes, easyLevelList,
          cognitionLevelList));
    }

    // 添加出题数量不为空限制
    if (null == itemList || itemList.isEmpty()) {
      return this.showMessage("添加失败.<br>出题数量不能为空.", null, null, true);
    }

    examRule.setItemList(itemList);

    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setName(request.getParameter("ruleName"));
    questionBankExamRuleModel.setExamTypeId(Integer.parseInt(ruleType));
    questionBankExamRuleModel.setProductId(productId);
    questionBankExamRuleModel.setStatus(XdmsConstant.INIT_STATUS);
    questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_NORMAL);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    QuestionBankExamRuleModel result = questionBankExamRuleService.addEntity(questionBankExamRuleModel);
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", result.getId());
    QuestionBankExamRuleModel ruleModel = new QuestionBankExamRuleModel();
    ruleModel.setStatus(XdmsConstant.NORMAL_STATUS);
    questionBankExamRuleService.editByCond(cond, ruleModel);
    return this.showMessage("添加成功", null, null, true);
  }

  /**
   * 构建规则列表
   * 
   * @param chapter
   * @param request
   * @param questionTypes
   * @param easyLevelList
   * @param cognitionLevelList
   * @return
   */
  public List<ExamRuleItem> buildRuleList(ProductModel product, ProductItemModel chapter,
      HttpServletRequest request, List<QuestionBankQuestionType> questionTypes,
      List<String> easyLevelList, List<String> cognitionLevelList, Long itemType) {
    List<ExamRuleItem> itemList = new ArrayList<>();
    // 题目类型
    for (QuestionBankQuestionType questionType : questionTypes) {
      // 难易程度
      for (String easyLevel : easyLevelList) {
        // 认知层次
        for (String cognitionLevel : cognitionLevelList) {

          String key = "";
          if (itemType.longValue() == ExamRuleItem.chapter) {
            key =
                "chapter_" + chapter.getId() + "_" + questionType.getId() + "_" + easyLevel + "_"
                    + cognitionLevel;
          } else if (itemType.longValue() == ExamRuleItem.childChapter) {
            key =
                "childChapter_" + chapter.getId() + "_" + questionType.getId() + "_" + easyLevel
                    + "_" + cognitionLevel;
          }

          String num = request.getParameter(key);
          if (StringUtils.isBlank(num)) {
            continue;
          }
          ExamRuleItem examRuleItem = new ExamRuleItem();
          examRuleItem.setQuestionNum(Long.parseLong(num));
          if (chapter.getResourceId() != null) {
            examRuleItem.setChapterIds(chapter.getResourceId().toString());
          }
          examRuleItem.setQuestionType(questionType.getId());
          examRuleItem.setItemType(itemType.intValue());
          examRuleItem.setCognitionLevel(convertCognitionLevel(cognitionLevel));
          examRuleItem.setEasyLevel(convertEasyLevel(easyLevel));
          examRuleItem.setProductChapterId(chapter.getId());
          itemList.add(examRuleItem);
        }
      }
    }
    return itemList;
  }

  /**
   * 构建规则列表
   * 
   * @param chapter
   * @param request
   * @param questionTypes
   * @param easyLevelList
   * @param cognitionLevelList
   * @return
   */
  public List<ExamRuleItem> buildProductRuleList(Long productId, HttpServletRequest request,
      List<QuestionBankQuestionType> questionTypes, List<String> easyLevelList,
      List<String> cognitionLevelList) {
    List<ExamRuleItem> itemList = new ArrayList<>();
    // 题目类型
    for (QuestionBankQuestionType questionType : questionTypes) {
      // 难易程度
      for (String easyLevel : easyLevelList) {
        // 认知层次
        for (String cognitionLevel : cognitionLevelList) {

          String key =
              "chaptersum_" + productId + "_" + questionType.getId() + "_" + easyLevel + "_"
                  + cognitionLevel;

          String num = request.getParameter(key);
          if (StringUtils.isBlank(num)) {
            continue;
          }
          ExamRuleItem examRuleItem = new ExamRuleItem();
          examRuleItem.setQuestionNum(Long.parseLong(num));
          examRuleItem.setQuestionType(questionType.getId());
          examRuleItem.setItemType(ExamRuleItem.product.intValue());
          examRuleItem.setCognitionLevel(convertCognitionLevel(cognitionLevel));
          examRuleItem.setEasyLevel(convertEasyLevel(easyLevel));
          examRuleItem.setProductChapterId(productId);
          itemList.add(examRuleItem);
        }
      }
    }
    return itemList;
  }

  /**
   * 新增章节规则
   * 
   * @param productId
   * @param chapterId
   * @return
   */
  @RequestMapping("/addChapterRule")
  public ModelAndView addChapterRule(Long productId, Long chapterId) {
    ModelAndView modelAndView = new ModelAndView("/exam/chapterRuleAdd");
    // 产品信息
    ProductModel product = productService.findSubjectById(productId);
    modelAndView.addObject("product", product);
    if (StringUtils.isBlank(product.getQuestionBankSetting())) {
      return this.showMessage("请先进行设置题库", null, null, true);
    }
    modelAndView.addObject("examTypeList", Lists.newArrayList(ExamType.values()));
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    modelAndView.addObject("setting", setting);
    List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
    List<QuestionBankQuestionType> keguanList = new ArrayList<>();
    for (QuestionBankQuestionType type : setting.getTypeList()) {
      if (type.getAnswerType().equals("0")) {
        keguanList.add(type);
      } else {
        zhuguanList.add(type);
      }
    }
    modelAndView.addObject("zhuguan", zhuguanList);
    modelAndView.addObject("keguan", keguanList);
    // 章节信息
    List<ProductItemModel> childChapters =
        productItemService.queryChapterItemByParentId(chapterId, productId);
    ProductItemModel chapter = productItemService.findItemById(chapterId);
    modelAndView.addObject("chapter", chapter);
    modelAndView.addObject("childChapters", childChapters);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("chapterId", chapterId);
    return modelAndView;
  }

  /**
   * 删除规则
   * 
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(Integer id) {
    questionBankExamRuleService.delete(id);
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * 规则编辑
   * 
   * @param id
   * @return
   */
  @RequestMapping("/doEditRule")
  public ModelAndView doEditRule(Integer id, String ruleName, HttpServletRequest request) {
    ExamRule examRule = new ExamRule();

    QuestionBankExamRuleModel rule = questionBankExamRuleService.findEntityById(id);

    // 有子章节的章节
    String[] hasChildChapters = request.getParameterValues("setChildChapter");
    Map<Integer, Object> hasChildChaptersMap = new HashMap<>();
    if (hasChildChapters != null && hasChildChapters.length > 0) {
      for (String hasChildChapter : hasChildChapters) {
        hasChildChaptersMap.put(Integer.parseInt(hasChildChapter), null);
      }
    }

    // 重点章节
    String[] importChapters = request.getParameterValues("importChapter");
    if (hasChildChapters != null) {
      examRule.setHasChildChapters(Arrays.asList(hasChildChapters));
    }
    if (importChapters != null) {
      examRule.setImportChapters(Arrays.asList(importChapters));
    }

    // 难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");

    // 认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");

    // 章节信息
    List<ProductItemModel> chapterList =
        productItemService.queryAllChapterItem(rule.getProductId());
    Map<String, ProductItemModel> chapterMap = new HashMap<>();
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() == 0) {
        chapterMap.put(chapter.getId().toString(), chapter);
      }
    }
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() != 0) {
        ProductItemModel parent = chapterMap.get(chapter.getParentId().toString());
        List<ProductItemModel> childChapterList = parent.getChildList();
        childChapterList.add(chapter);
        parent.setChildList(childChapterList);
        chapterMap.put(chapter.getParentId().toString(), parent);
      }
    }
    List<ProductItemModel> chapters = new ArrayList<>();
    for (String key : chapterMap.keySet()) {
      chapters.add(chapterMap.get(key));
    }

    // 题型信息
    ProductModel product = productService.findSubjectById(rule.getProductId());
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();


    List<ExamRuleItem> itemList = new ArrayList<>();
    // 章节
    for (ProductItemModel chapter : chapters) {
      // 设置
      if (hasChildChaptersMap.containsKey(chapter.getId())) {
        for (ProductItemModel childChapter : chapter.getChildList()) {
          itemList.addAll(buildRuleList(product, childChapter, request, questionTypes,
              easyLevelList, cognitionLevelList, ExamRuleItem.childChapter));
        }
      } else {
        itemList.addAll(buildRuleList(product, chapter, request, questionTypes, easyLevelList,
            cognitionLevelList, ExamRuleItem.chapter));
      }
    }
    // 2017_11_09 修改出题规则逻辑,如果章节规则为空,尝试添加课程规则
    if (itemList.isEmpty()) {
      itemList.addAll(buildProductRuleList(product.getId(), request, questionTypes, easyLevelList,
          cognitionLevelList));
    }

    // 添加出题数量不为空限制
    if (null == itemList || itemList.isEmpty()) {
      return this.showMessage("编辑失败.<br>出题数量不能为空.", null, "/examRule/editRule?ruleId=" + id, true);
    }

    examRule.setItemList(itemList);

    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setId(id);
    questionBankExamRuleModel.setName(ruleName);
    questionBankExamRuleModel.setStatus(XdmsConstant.INIT_STATUS);
    questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_NORMAL);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    questionBankExamRuleService.edit(questionBankExamRuleModel);
    return this.showMessage("编辑成功", null, "/examRule/editRule?ruleId=" + id, true);
  }


  /**
   * 规则编辑
   * 
   * @param id
   * @return
   */
  @RequestMapping("/do_edit_default_rule")
  public ModelAndView doEditDefaultRule(Integer id, String ruleName, HttpServletRequest request) {
    ExamRule examRule = new ExamRule();

    // 难易程度
    List<String> easyLevelList = new ArrayList<>();
    easyLevelList.add("A");
    easyLevelList.add("B");
    easyLevelList.add("C");

    // 认知层次
    List<String> cognitionLevelList = new ArrayList<>();
    cognitionLevelList.add("Ⅰ");
    cognitionLevelList.add("Ⅱ");
    cognitionLevelList.add("Ⅲ");

    // 题型信息
    List<QuestionBankQuestionType> questionTypes = Lists.newArrayList();
    List<QuestionBankQuestionTypeModel> questionBankQuestionTypeList =
        questionBankQuestionTypeService.typeList();
    for (QuestionBankQuestionTypeModel typeModel : questionBankQuestionTypeList) {
      QuestionBankQuestionType type = new QuestionBankQuestionType(typeModel);
      if (null != type) questionTypes.add(type);
    }

    List<ExamRuleItem> itemList = new ArrayList<>();
    // 2017_11_09 修改出题规则逻辑,如果章节规则为空,尝试添加课程规则
    if (itemList.isEmpty()) {
      itemList.addAll(buildProductRuleList(XdmsConstant.COMMON_PRODUCT_ID.longValue(), request,
          questionTypes, easyLevelList, cognitionLevelList));
    }

    examRule.setItemList(itemList);

    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setId(id);
    questionBankExamRuleModel.setName(ruleName);
    questionBankExamRuleModel.setStatus(XdmsConstant.INIT_STATUS);
    questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_DEFAULT);
    questionBankExamRuleModel.setRuleDetail(JSON.toJSONString(examRule));
    questionBankExamRuleService.edit(questionBankExamRuleModel);
    return this.showMessage("编辑成功", null, "/examRule/edit_default_rule?ruleId=" + id, true);
  }

  /**
   * 规则编辑
   * 
   * @param id
   * @return
   */
  @RequestMapping("/set_default_rule")
  public ModelAndView setDefaultRule(Integer id) {
    QuestionBankExamRuleModel rule = questionBankExamRuleService.findEntityById(id);
    if (null == rule)
      return this.showMessage("编辑失败,规则不存在.", null, "/examRule/editRule?ruleId=" + id, true);
    QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
    questionBankExamRuleModel.setId(id);
    questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_DEFAULT);
    questionBankExamRuleService.edit(questionBankExamRuleModel);
    return this.showMessage("编辑成功", null, "/examRule/editRule?ruleId=" + id, true);
  }

  /**
   * 编辑章节信息
   * 
   * @param ruleId
   * @return
   */
  @RequestMapping("/editRule")
  public ModelAndView editRule(Integer ruleId) {
    ModelAndView modelAndView = new ModelAndView("/exam/ruleEdit");

    // 出题规则
    QuestionBankExamRuleModel chapterRule = questionBankExamRuleService.findEntityById(ruleId);
    modelAndView.addObject("chapterRule", chapterRule);

    // 出题数目规则
    ExamRule examRule = JSON.parseObject(chapterRule.getRuleDetail(), ExamRule.class);
    Map<String, Long> numMap = new HashMap<>();
    for (ExamRuleItem ruleItem : examRule.getItemList()) {
      if (ruleItem.getItemType().longValue() == ExamRuleItem.product) {
        String key =
            "chaptersum_" + ruleItem.getProductChapterId() + "_" + ruleItem.getQuestionType() + "_"
                + convertEasyLevel(ruleItem.getEasyLevel()) + "_"
                + convertCognitionLevel(ruleItem.getCognitionLevel());
        numMap.put(key, ruleItem.getQuestionNum());
      } else {
        String key =
            "chapter_" + ruleItem.getProductChapterId() + "_" + ruleItem.getQuestionType() + "_"
                + convertEasyLevel(ruleItem.getEasyLevel()) + "_"
                + convertCognitionLevel(ruleItem.getCognitionLevel());
        numMap.put(key, ruleItem.getQuestionNum());
      }
    }
    modelAndView.addObject("numMap", numMap);
    if (examRule.getHasChildChapters() != null && examRule.getHasChildChapters().size() > 0) {
      Map<String, String> hasChildChaptersMap = new HashMap<>();
      for (String key : examRule.getHasChildChapters()) {
        hasChildChaptersMap.put("chapter" + key, "1");
      }
      modelAndView.addObject("hasChildChaptersMap", hasChildChaptersMap);
    }
    if (examRule.getImportChapters() != null && examRule.getImportChapters().size() > 0) {
      Map<String, String> importChaptersMap = new HashMap<>();
      for (String key : examRule.getImportChapters()) {
        importChaptersMap.put("chapter" + key, "1");
      }
      modelAndView.addObject("importChaptersMap", importChaptersMap);
    }

    // 章节信息
    List<ProductItemModel> chapterList =
        productItemService.queryAllChapterItem(chapterRule.getProductId());
    Map<String, ProductItemModel> chapterMap = new HashMap<>();
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() == 0) {
        chapterMap.put(chapter.getId().toString(), chapter);
      }
    }
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() != 0) {
        ProductItemModel parent = chapterMap.get(chapter.getParentId().toString());
        List<ProductItemModel> childChapterList = parent.getChildList();
        childChapterList.add(chapter);
        parent.setChildList(childChapterList);
        chapterMap.put(chapter.getParentId().toString(), parent);
      }
    }
    List<ProductItemModel> chapters = new ArrayList<>();
    for (String key : chapterMap.keySet()) {
      chapters.add(chapterMap.get(key));
    }
    modelAndView.addObject("chapters", chapters);

    // 产品信息
    ProductModel product = productService.findSubjectById(chapterRule.getProductId());
    modelAndView.addObject("product", product);
    QuestionBankSetting setting = new QuestionBankSetting();
    List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
    List<QuestionBankQuestionType> keguanList = new ArrayList<>();
    // 题目类型信息
    if (StringUtils.isJsonBlank(product.getQuestionBankSetting())) {
      modelAndView.addObject("setting", setting);
      modelAndView.addObject("zhuguan", zhuguanList);
      modelAndView.addObject("keguan", keguanList);
      return modelAndView;
    }
    setting = JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    for (QuestionBankQuestionType type : setting.getTypeList()) {
      if (type.getAnswerType().equals("0")) {
        keguanList.add(type);
      } else {
        zhuguanList.add(type);
      }
    }
    modelAndView.addObject("setting", setting);
    modelAndView.addObject("zhuguan", zhuguanList);
    modelAndView.addObject("keguan", keguanList);
    return modelAndView;
  }


  /**
   * 编辑默认命题蓝图信息
   * 
   * @param ruleId
   * @return
   */
  @RequestMapping("/edit_default_rule")
  public ModelAndView editDefaultRule(Integer ruleId) {
    ModelAndView modelAndView = new ModelAndView("/exam/defaultRuleEdit");

    // 出题规则
    QuestionBankExamRuleModel chapterRule = questionBankExamRuleService.findEntityById(ruleId);
    modelAndView.addObject("chapterRule", chapterRule);

    // 出题数目规则
    ExamRule examRule = JSON.parseObject(chapterRule.getRuleDetail(), ExamRule.class);
    Map<String, Long> numMap = new HashMap<>();
    for (ExamRuleItem ruleItem : examRule.getItemList()) {
      if (ruleItem.getItemType().longValue() == ExamRuleItem.product) {
        String key =
            "chaptersum_" + ruleItem.getProductChapterId() + "_" + ruleItem.getQuestionType() + "_"
                + convertEasyLevel(ruleItem.getEasyLevel()) + "_"
                + convertCognitionLevel(ruleItem.getCognitionLevel());
        numMap.put(key, ruleItem.getQuestionNum());
      } else {
        String key =
            "chapter_" + ruleItem.getProductChapterId() + "_" + ruleItem.getQuestionType() + "_"
                + convertEasyLevel(ruleItem.getEasyLevel()) + "_"
                + convertCognitionLevel(ruleItem.getCognitionLevel());
        numMap.put(key, ruleItem.getQuestionNum());
      }
    }
    modelAndView.addObject("numMap", numMap);

    // 题目类型信息
    List<QuestionBankQuestionTypeModel> questionBankQuestionTypeList =
        questionBankQuestionTypeService.typeList();
    if (null != questionBankQuestionTypeList && questionBankQuestionTypeList.size() > 0) {
      List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
      List<QuestionBankQuestionType> keguanList = new ArrayList<>();
      QuestionBankSetting setting = new QuestionBankSetting();
      setting.setTotalScore(100);
      for (QuestionBankQuestionTypeModel typeModel : questionBankQuestionTypeList) {
        QuestionBankQuestionType type = new QuestionBankQuestionType(typeModel);
        setting.getTypeList().add(type);
        if (type.getAnswerType().equals("0")) {
          keguanList.add(type);
        } else {
          zhuguanList.add(type);
        }
      }
      modelAndView.addObject("setting", setting);
      modelAndView.addObject("zhuguan", zhuguanList);
      modelAndView.addObject("keguan", keguanList);
    }

    return modelAndView;
  }

  /**
   * 添加默认命题蓝图
   * 
   * @return
   */
  @RequestMapping("/add_default_rule")
  public ModelAndView addDefaultRule() {
    ModelAndView modelAndView = new ModelAndView("/exam/defaultRuleAdd");
    List<ExamType> examTypeList = Lists.newArrayList();
    for (ExamType examType : ExamType.values()) {
      if (ExamType.UNKNOWN.equals(examType)) {
        continue;
      }
      examTypeList.add(examType);
    }
    modelAndView.addObject("examTypeList", examTypeList);
    // 产品信息
    List<QuestionBankQuestionTypeModel> questionBankQuestionTypeList =
        questionBankQuestionTypeService.typeList();
    if (null != questionBankQuestionTypeList && questionBankQuestionTypeList.size() > 0) {
      List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
      List<QuestionBankQuestionType> keguanList = new ArrayList<>();
      QuestionBankSetting setting = new QuestionBankSetting();
      setting.setTotalScore(100);
      for (QuestionBankQuestionTypeModel typeModel : questionBankQuestionTypeList) {
        QuestionBankQuestionType type = new QuestionBankQuestionType(typeModel);
        setting.getTypeList().add(type);
        if (type.getAnswerType().equals("0")) {
          keguanList.add(type);
        } else {
          zhuguanList.add(type);
        }
      }
      modelAndView.addObject("setting", setting);
      modelAndView.addObject("zhuguan", zhuguanList);
      modelAndView.addObject("keguan", keguanList);
    }
    return modelAndView;
  }

  /**
   * 规则添加
   * 
   * @return
   */
  @RequestMapping("/addRule")
  public ModelAndView addRule(Long productId) {
    ModelAndView modelAndView = new ModelAndView("/exam/ruleAdd");
    List<ExamType> examTypeList = Lists.newArrayList();
    for (ExamType examType : ExamType.values()) {
      if (ExamType.UNKNOWN.equals(examType)) {
        continue;
      }
      examTypeList.add(examType);
    }
    modelAndView.addObject("examTypeList", examTypeList);
    // 产品信息
    ProductModel product = productService.findSubjectById(productId);
    modelAndView.addObject("product", product);
    if (StringUtils.isBlank(product.getQuestionBankSetting())) {
      return this.showMessage("请先进行设置题库", null, null, true);
    }
    QuestionBankSetting setting =
        JSON.parseObject(product.getQuestionBankSetting(), QuestionBankSetting.class);
    modelAndView.addObject("setting", setting);
    List<QuestionBankQuestionType> zhuguanList = new ArrayList<>();
    List<QuestionBankQuestionType> keguanList = new ArrayList<>();
    for (QuestionBankQuestionType type : setting.getTypeList()) {
      if (type.getAnswerType().equals("0")) {
        keguanList.add(type);
      } else {
        zhuguanList.add(type);
      }
    }
    modelAndView.addObject("zhuguan", zhuguanList);
    modelAndView.addObject("keguan", keguanList);
    // 章节信息
    List<ProductItemModel> chapterList = productItemService.queryAllChapterItem(productId);
    Map<String, ProductItemModel> chapterMap = new HashMap<>();
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() == 0) {
        chapterMap.put(chapter.getId().toString(), chapter);
      }
    }
    for (ProductItemModel chapter : chapterList) {
      if (chapter.getParentId() != 0) {
        ProductItemModel parent = chapterMap.get(chapter.getParentId().toString());
        // 2018年7月30日 14:06:38 written by zdhuang
        if(null == parent) continue;
        List<ProductItemModel> childChapterList = parent.getChildList();
        childChapterList.add(chapter);
        parent.setChildList(childChapterList);
        chapterMap.put(chapter.getParentId().toString(), parent);
      }
    }
    List<ProductItemModel> chapters = new ArrayList<>();
    for (String key : chapterMap.keySet()) {
      chapters.add(chapterMap.get(key));
    }
    modelAndView.addObject("chapters", chapters);
    modelAndView.addObject("productId", productId);
    modelAndView.addObject("product", product);
    return modelAndView;
  }

  private Integer convertEasyLevel(String level) {
    if (level.equals("A")) {
      return 1;
    } else if (level.equals("B")) {
      return 2;
    } else if (level.equals("C")) {
      return 3;
    } else {
      return 0;
    }
  }

  private String convertEasyLevel(int level) {
    if (level == 1) {
      return "A";
    } else if (level == 2) {
      return "B";
    } else if (level == 3) {
      return "C";
    } else {
      return "";
    }
  }

  private Integer convertCognitionLevel(String cognition) {
    if (cognition.equals("Ⅰ")) {
      return 1;
    } else if (cognition.equals("Ⅱ")) {
      return 2;
    } else if (cognition.equals("Ⅲ")) {
      return 3;
    } else {
      return 0;
    }
  }

  private String convertCognitionLevel(int cognition) {
    if (cognition == 1) {
      return "Ⅰ";
    } else if (cognition == 2) {
      return "Ⅱ";
    } else if (cognition == 3) {
      return "Ⅲ";
    } else {
      return "";
    }
  }

  /**
   * 审核通过。因需求变更，需注释，lirui 2018.7.30。
   * 
   * @param ids
   * @return
   */
//  @RequestMapping("/verify")
//  @ResponseBody
//  public String verify(String ids) {
//    String[] idArray = ids.split(",");
//    List<Integer> idList = new ArrayList<>();
//    for (String id : idArray) {
//      idList.add(Integer.parseInt(id));
//    }
//    Map<String, Object> cond = new HashMap<>();
//    cond.put("ids", idList);
//    QuestionBankExamRuleModel ruleModel = new QuestionBankExamRuleModel();
//    ruleModel.setStatus(XdmsConstant.NORMAL_STATUS);
//    questionBankExamRuleService.editByCond(cond, ruleModel);
//    return "true";
//  }

}
