package com.xiaodou.ms.web.controller.exam;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
   
/**
 * Created by zyp on 15/6/25.
 */
@Controller("questionTypeController")
@RequestMapping("questionType")
public class QuestionTypeController extends BaseController {

  @Resource
  private QuestionBankQuestionTypeService questionBankQuestionTypeService;

  /**
   * 试题类型列表
   * @return
   */
  @RequestMapping("list")
  public ModelAndView list(){
    ModelAndView modelAndView = new ModelAndView("/exam/questionTypeList");
    modelAndView.addObject("questionTypeList", questionBankQuestionTypeService.typeList());
    return modelAndView;
  }

  /**
   * 添加
   * @return
   */
  @RequestMapping("add")
  public ModelAndView add(){
    ModelAndView modelAndView = new ModelAndView("/exam/questionTypeAdd");
    return modelAndView;
  }

  /**
   * 试题类型添加
   * @return
   */
  @RequestMapping("doAdd")
  public ModelAndView doAdd(String typeName,String desc,Integer answerType){
    if (StringUtils.isBlank(typeName)){
      return this.showMessage("添加失败","类型不能为空",null,true);
    }
    QuestionBankQuestionTypeModel questionBankQuestionTypeModel = new QuestionBankQuestionTypeModel();
    questionBankQuestionTypeModel.setTypeName(typeName);
    questionBankQuestionTypeModel.setMdesc(desc);
    questionBankQuestionTypeModel.setAnswerType(answerType);
    questionBankQuestionTypeService.addType(questionBankQuestionTypeModel);
    return this.showMessage("添加成功",null,null,true);
  }

  /**
   * 编辑
   * @param id
   * @return
   */
  @RequestMapping("edit")
  public ModelAndView edit(Integer id){
    ModelAndView modelAndView = new ModelAndView("/exam/questionTypeEdit");
    QuestionBankQuestionTypeModel QuestionBankQuestionTypeModel = questionBankQuestionTypeService.findTypeById(id);
    modelAndView.addObject("questionType", QuestionBankQuestionTypeModel);
    return modelAndView;
  }

  /**
   * 试题类型更新
   * @return
   */
  @RequestMapping("doEdit")
  public ModelAndView doEdit(Integer id,String typeName,String desc,Integer answerType){
    if (id==null||id==0||StringUtils.isBlank(typeName)){
      return this.showMessage("更新失败","类型不能为空",null,true);
    }
    QuestionBankQuestionTypeModel quesbkQuestTypeModel = new QuestionBankQuestionTypeModel();
    quesbkQuestTypeModel.setId(id);
    quesbkQuestTypeModel.setMdesc(desc);
    quesbkQuestTypeModel.setTypeName(typeName);
    quesbkQuestTypeModel.setAnswerType(answerType);
    questionBankQuestionTypeService.updateType(quesbkQuestTypeModel);
    return this.showMessage("更新成功",null,null,true);
  }

  /**
   * 试题类型删除
   * @return
   */
  @RequestMapping("delete")
  @ResponseBody
  public String delete(Integer id){
    if (questionBankQuestionTypeService.deleteType(id)){
      return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
    } else {
      return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
    }
  }

}
