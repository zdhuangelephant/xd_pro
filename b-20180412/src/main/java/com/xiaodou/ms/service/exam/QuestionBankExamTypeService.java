package com.xiaodou.ms.service.exam;

import com.xiaodou.ms.dao.exam.QuestionBankExamTypeDao;
import com.xiaodou.ms.model.exam.QuestionBankExamTypeModel;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 15/7/11.
 */
@Service("questionBankExamTypeService")
public class QuestionBankExamTypeService {

  @Resource
  QuestionBankExamTypeDao questionBankExamTypeDao;

  /**
   * 查找
   * @param id
   * @return
   */
  public QuestionBankExamTypeModel findTypeById(Integer id){
    QuestionBankExamTypeModel cond = new QuestionBankExamTypeModel();
    cond.setId(id);
    return questionBankExamTypeDao.findEntityById(cond);
  }

  /**
   * 添加
   * @param entity
   * @return
   */
  public QuestionBankExamTypeModel addType(QuestionBankExamTypeModel entity){
    return questionBankExamTypeDao.addEntity(entity);
  }

  /**
   * 删除类型
   * @param id
   * @return
   */
  public Boolean deleteType(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return questionBankExamTypeDao.deleteEntity(cond);
  }

  /**
   * 更新类型
   * @param entity
   * @return
   */
  public Boolean updateType(QuestionBankExamTypeModel entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    return questionBankExamTypeDao.updateEntity(cond,entity);
  }

  /**
   * 试卷类列表
   * @return
   */
  public List<QuestionBankExamTypeModel> typeList(){
    Map<String,Object> cond = new HashMap<>();
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("examTypeName","");
    output.put("status","");
    output.put("mdesc","");
    Page<QuestionBankExamTypeModel> questionBankExamTypeModelPage =
      questionBankExamTypeDao.queryListByCond0(cond, output, null);
    return questionBankExamTypeModelPage.getResult();
  }
}
