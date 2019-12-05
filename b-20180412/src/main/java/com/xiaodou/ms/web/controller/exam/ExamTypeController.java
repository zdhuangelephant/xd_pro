package com.xiaodou.ms.web.controller.exam;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.exam.QuestionBankExamTypeModel;
import com.xiaodou.ms.service.exam.QuestionBankExamTypeService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("examTypeController")
@RequestMapping("/examType")
public class ExamTypeController extends BaseController {

  @Resource
  private QuestionBankExamTypeService questionBankExamTypeService;

  /**
   * 考试类型列表
   * @return
   */
  @RequestMapping("list")
  public ModelAndView list(){
    ModelAndView modelAndView = new ModelAndView("/exam/examTypeList");
    modelAndView.addObject("examTypeList", questionBankExamTypeService.typeList());
    return modelAndView;
  }

  /**
   * 添加
   * @return
   */
  @RequestMapping("add")
  public ModelAndView add(){
    ModelAndView modelAndView = new ModelAndView("/exam/examTypeAdd");
    return modelAndView;
  }

  /**
   * 考试类型添加
   * @return
   */
  @RequestMapping("doAdd")
  public ModelAndView doAdd(String typeName,String desc){
    if (StringUtils.isBlank(typeName)){
      return this.showMessage("添加失败","类型不能为空",null,true);
    }
    QuestionBankExamTypeModel questionBankExamTypeModel = new QuestionBankExamTypeModel();
    questionBankExamTypeModel.setExamTypeName(typeName);
    questionBankExamTypeModel.setMdesc(desc);
    questionBankExamTypeService.addType(questionBankExamTypeModel);
    return this.showMessage("添加成功",null,null,true);
  }

  /**
   * 编辑
   * @param id
   * @return
   */
  @RequestMapping("edit")
  public ModelAndView edit(Integer id){
    ModelAndView modelAndView = new ModelAndView("/exam/examTypeEdit");
    QuestionBankExamTypeModel QuestionBankExamTypeModel = questionBankExamTypeService.findTypeById(id);
    modelAndView.addObject("examType", QuestionBankExamTypeModel);
    return modelAndView;
  }

  /**
   * 考试类型更新
   * @return
   */
  @RequestMapping("doEdit")
  public ModelAndView doEdit(Integer id,String typeName,String desc){
    if (id==null||id==0||StringUtils.isBlank(typeName)){
      return this.showMessage("更新失败","类型不能为空",null,true);
    }
    QuestionBankExamTypeModel questionBankExamTypeModel = new QuestionBankExamTypeModel();
    questionBankExamTypeModel.setId(id);
    questionBankExamTypeModel.setMdesc(desc);
    questionBankExamTypeModel.setExamTypeName(typeName);
    questionBankExamTypeService.updateType(questionBankExamTypeModel);
    return this.showMessage("更新成功",null,null,true);
  }

  /**
   * 考试类型删除
   * @return
   */
  @RequestMapping("delete")
  @ResponseBody
  public String delete(Integer id){
    if (questionBankExamTypeService.deleteType(id)){
      return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
    } else {
      return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
    }
  }

}
