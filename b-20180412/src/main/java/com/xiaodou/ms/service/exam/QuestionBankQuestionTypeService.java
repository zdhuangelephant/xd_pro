package com.xiaodou.ms.service.exam;

import com.xiaodou.ms.dao.exam.QuestionBankQuestionTypeDao;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.web.request.exam.PaperEditRequest;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 15/7/11.
 */
@Service("questionBankQuestionTypeService")
public class QuestionBankQuestionTypeService {

  @Resource
  QuestionBankQuestionTypeDao questionBankQuestionTypeDao;

  /**
   * 获取试题类型
   * @param id
   * @return
   */
  public QuestionBankQuestionTypeModel findTypeById(Integer id){
    QuestionBankQuestionTypeModel questionBankQuestionTypeModel = new QuestionBankQuestionTypeModel();
    questionBankQuestionTypeModel.setId(id);
    return questionBankQuestionTypeDao.findEntityById(questionBankQuestionTypeModel);
  }

  /**
   * 添加
   * @param entity
   * @return
   */
  public QuestionBankQuestionTypeModel addType(QuestionBankQuestionTypeModel entity){
    return questionBankQuestionTypeDao.addEntity(entity);
  }

  /**
   * 删除类型
   * @param id
   * @return
   */
  public Boolean deleteType(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return questionBankQuestionTypeDao.deleteEntity(cond);
  }

  /**
   * 更新类型
   * @param entity
   * @return
   */
  public Boolean updateType(QuestionBankQuestionTypeModel entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    return questionBankQuestionTypeDao.updateEntity(cond,entity);
  }

  /**
   * 试卷类列表
   * @return
   */
  public List<QuestionBankQuestionTypeModel> typeList(){
    Map<String,Object> cond = new HashMap<>();
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("typeName","");
    output.put("status","");
    output.put("mdesc","");
    output.put("answerType","");
    Page<QuestionBankQuestionTypeModel> questionBankQuestionTypeModelPage =
      questionBankQuestionTypeDao.queryListByCond0(cond, output, null);
    return questionBankQuestionTypeModelPage.getResult();
  }
  /**
   * 试卷类列表
   * @return
   */
  public List<QuestionBankQuestionTypeModel> typeList(Integer questionType){
    Map<String,Object> cond = new HashMap<>();
    if (questionType != null) {
    	cond.put("questionType",questionType );
	}
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("typeName","");
    output.put("status","");
    output.put("mdesc","");
    output.put("answerType","");
    Page<QuestionBankQuestionTypeModel> questionBankQuestionTypeModelPage =
      questionBankQuestionTypeDao.queryListByCond0(cond, output, null);
    return questionBankQuestionTypeModelPage.getResult();
  }
}
