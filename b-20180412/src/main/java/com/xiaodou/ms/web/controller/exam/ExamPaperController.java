package com.xiaodou.ms.web.controller.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.ms.model.exam.QuestionBankExamPaperModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionType;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.exam.QuestionBankSetting;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.service.exam.QuestionBankExamPaperService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionResourceService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.exam.PaperAddRequest;
import com.xiaodou.ms.web.request.exam.PaperEditRequest;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("examPaperController")
@RequestMapping("examPaper")
public class ExamPaperController extends BaseController {


  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  ProductItemService productItemService;

  @Resource
  QuestionBankExamPaperService questionBankExamPaperService;

  @Resource
  ProductService productService;

  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  @Resource
  QuestionBankQuestionResourceService questionBankQuestionResourceService;

  /**
   * 试卷添加
   * @param productId
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView paperAdd(Long productId){
    ModelAndView modelAndView = new ModelAndView("/exam/paperAdd");

    // 试题来源
    List<QuestionBankQuestionResourceModel> resourceList =
      questionBankQuestionResourceService.resourceList();
    modelAndView.addObject("resourceList",resourceList);

    // 产品信息--题库设置
    ProductModel product = productService.findSubjectById(productId);
    QuestionBankSetting setting = JSON.parseObject(product.getQuestionBankSetting(),QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();
    Map<String,QuestionBankQuestionType> sortMap = new TreeMap<>();
    for (QuestionBankQuestionType questionType:questionTypes){
      sortMap.put(questionType.getId().toString(),questionType);
    }
    modelAndView.addObject("typeMap",sortMap);

    // 题目类型列表
    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    modelAndView.addObject("typeList",typeList);
    modelAndView.addObject("productId",productId);
    
    
    return modelAndView;
  }

  /**
   * 试卷添加
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView doPaperAdd(PaperAddRequest request,Errors errors){
    request.validate();
    if (errors.hasErrors()){
      return this.showMessage("失败",this.getErrMsg(errors),null,true);
    }
    QuestionBankExamPaperModel result = questionBankExamPaperService.addPaper(request);
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",result.getId());
    QuestionBankExamPaperModel paperModel = new QuestionBankExamPaperModel();
    paperModel.setStatus("99");
    questionBankExamPaperService.editPaper(cond,paperModel);
    return this.showMessage("成功",null,null,true);
  }

  /**
   * 试卷列表
   * @param productId
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView paperList(Integer productId){
    ModelAndView modelAndView = new ModelAndView("/exam/paperList");
    List<QuestionBankExamPaperModel> paperList =
      questionBankExamPaperService.paperList(productId);
    modelAndView.addObject("paperList", paperList);
    return modelAndView;
  }

  /**
   * 编辑
   * @param id
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView paperEdit(String id){

    ModelAndView modelAndView = new ModelAndView("/exam/paperEdit");

    //试卷信息
    QuestionBankExamPaperModel paper = questionBankExamPaperService.findPaperById(id);
    modelAndView.addObject("paper",paper);

    //试题列表
    List<Integer> ids =
      JSON.parseObject(paper.getQuesIds(), new TypeReference<List<Integer>>() {
      });
    List<QuestionBankQuestionModel> questionBankQuestionModels =
      questionBankQuestionService.queryQuestionByIds(ids);

    //试题类型map
    Map<String,List<QuestionBankQuestionModel>> questionTypeMap = new HashMap<>();
    for (QuestionBankQuestionModel questionModel:questionBankQuestionModels){
      List<QuestionBankQuestionModel> questionModels = new ArrayList<>();
      if (questionTypeMap.containsKey(questionModel.getQuestionType().toString())){
        questionModels = questionTypeMap.get(questionModel.getQuestionType().toString());
      }
      questionModels.add(questionModel);
      questionTypeMap.put(questionModel.getQuestionType().toString(),questionModels);
    }
    modelAndView.addObject("questionTypeMap",questionTypeMap);

    //产品设置
    ProductModel product = productService.findSubjectById(paper.getProductId());
    QuestionBankSetting setting = JSON.parseObject(product.getQuestionBankSetting(),QuestionBankSetting.class);
    List<QuestionBankQuestionType> questionTypes = setting.getTypeList();
    Map<String,QuestionBankQuestionType> sortMap = new TreeMap<>();
    for (QuestionBankQuestionType questionType:questionTypes){
      sortMap.put(questionType.getId().toString(),questionType);
    }
    modelAndView.addObject("typeMap",sortMap);

    //类型列表
    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    modelAndView.addObject("typeList",typeList);

    // 试题来源
    List<QuestionBankQuestionResourceModel> resourceList =
      questionBankQuestionResourceService.resourceList();
    modelAndView.addObject("resourceList",resourceList);

    return modelAndView;
  }

  /**
   * 编辑
   * @param request
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView paperDoEdit(PaperEditRequest request,Errors errors){
    request.validate();
    if (errors.hasErrors()){
      return this.showMessage("失败",this.getErrMsg(errors),null,true);
    }
    questionBankExamPaperService.editPaper(request);
    return this.showMessage("成功",null,null,true);
  }

  /**
   * 删除记录
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(String id){
    questionBankExamPaperService.deletePaper(id);
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  /**
   * 审核通过
   * @param ids
   * @return
   */
/*  @RequestMapping("/verify")
  @ResponseBody
  public String verify(String ids){
    String[] idArray = ids.split(",");
    List<String> idList = new ArrayList<>();
    for (String id:idArray){
      idList.add(id);
    }
    Map<String,Object> cond = new HashMap<>();
    cond.put("ids",idList);
    QuestionBankExamPaperModel paperModel = new QuestionBankExamPaperModel();
    paperModel.setStatus("99");
    questionBankExamPaperService.editPaper(cond,paperModel);
    return "true";
  }*/

}
