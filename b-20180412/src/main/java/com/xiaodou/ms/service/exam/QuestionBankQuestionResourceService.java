package com.xiaodou.ms.service.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.exam.QuestionBankQuestionResourceDao;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionResourceModel;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/8/6.
 */
@Service("questionBankQuestionResourceService")
public class QuestionBankQuestionResourceService {

  @Resource
  CourseSubjectService courseSubjectService;
	
  @Resource
  QuestionBankQuestionResourceDao questionBankQuestionResourceDao;

  /**
   * 来源列表
   * @return
   */
  /*public List<QuestionBankQuestionResourceModel> resourceList(){
	//条件
    Map<String,Object> cond = new HashMap<>();
    
    //需要返回的字段
    Map<String,Object> output = new HashMap<>();
    output.put("name","");
    output.put("id","");
    output.put("detail","");
    output.put("createTime","");
    //这是一个CourseSubjectModel的对象
    output.put("masterCourser", "");
    
    Page<QuestionBankQuestionResourceModel> questionBankQuestionResourceModelPage =
      questionBankQuestionResourceDao.queryListByCondWithOutPage(cond, output);
    return questionBankQuestionResourceModelPage.getResult();
  }*/
  /**
   * zdh 列表展示
   * @return
   */
  public List<QuestionBankQuestionResourceModel> resourceList(){
	//条件
    Map<String,Object> cond = new HashMap<>();
    
    Page<CourseSubjectModel> allCourse = courseSubjectService.queryAllCourse();
    List<CourseSubjectModel> result = allCourse.getResult();
    List<Long> list = new ArrayList<>();
    for (CourseSubjectModel val : result) {
		list.add(val.getId());
	}
    cond.put("courseIds", list);
    
    Page<QuestionBankQuestionResourceModel> questionBankQuestionResourceModelPage =
      questionBankQuestionResourceDao.queryListByCond0(cond, null, null);
    
    return questionBankQuestionResourceModelPage.getResult();
	}

  /**
   * 增加来源
   * @param resourceModel
   * @return
   */
  public QuestionBankQuestionResourceModel addResource(QuestionBankQuestionResourceModel resourceModel){
    return questionBankQuestionResourceDao.addEntity(resourceModel);
  }

  /**
   * 查找来源
   * @param resourceId
   * @return
   */
  public QuestionBankQuestionResourceModel findResource(Integer resourceId){
    QuestionBankQuestionResourceModel cond = new QuestionBankQuestionResourceModel();
    cond.setId(resourceId);
    return questionBankQuestionResourceDao.findEntityById(cond);
  }
  /**
   * 根据条件查找来源
   * @param resourceId
   * @return
   */
  public List<QuestionBankQuestionResourceModel> findResourceByCourseId(Long courseId){
	  Map<String,Object> cond = new HashMap<>();
      cond.put("courseId",courseId);
	 Page<QuestionBankQuestionResourceModel> resultSet = questionBankQuestionResourceDao.queryListByCond0(cond, null, null);
	 return resultSet.getResult();
  }
  /**
   * 编辑来源
   * @param resourceModel
   * @return
   */
  public Boolean editResource(QuestionBankQuestionResourceModel resourceModel){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",resourceModel.getId());
    return questionBankQuestionResourceDao.updateEntity(cond,resourceModel);
  }

  /**
   * 删除
   * @param resourceId
   * @return
   */
  public Boolean deleteResource(Integer resourceId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",resourceId);
    return questionBankQuestionResourceDao.deleteEntity(cond);
  }

}
