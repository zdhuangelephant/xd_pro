package com.xiaodou.ms.service.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.dao.exam.QuestionBankExamPaperDao;
import com.xiaodou.ms.model.exam.QuestionBankExamPaperModel;
import com.xiaodou.ms.web.request.exam.PaperAddRequest;
import com.xiaodou.ms.web.request.exam.PaperEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/7/28.
 */
@Service("questionBankExamPaperService")
public class QuestionBankExamPaperService {

  @Resource
  QuestionBankExamPaperDao questionBankExamPaperDao;

  /**
   * 试卷列表
   * @param productId
   * @return
   */
  public List<QuestionBankExamPaperModel> paperList(Integer productId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("productId",productId);
    cond.put("examTypeId",1);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("productId","");
    output.put("examName","");
    output.put("quesNum","");
    output.put("status","");
    output.put("diffculty","");
    output.put("mdesc","");
    Page<QuestionBankExamPaperModel> questionBankExamPaperModelPage =
      questionBankExamPaperDao.queryListByCond0(cond, output, null);
    return questionBankExamPaperModelPage.getResult();
  }

  /**
   * 添加试卷
   * @return
   */
  public QuestionBankExamPaperModel addPaper(QuestionBankExamPaperModel paper){
    return questionBankExamPaperDao.addEntity(paper);
  }

  /**
   * 添加试卷
   * @param request
   * @return
   */
  public QuestionBankExamPaperModel addPaper(PaperAddRequest request){
    QuestionBankExamPaperModel paper = new QuestionBankExamPaperModel();
    String[] ids = request.getQuesIds().split(",");
    List<Integer> questionIds = new ArrayList<>();
    for (String id:ids){
      questionIds.add(Integer.parseInt(id));
    }
    paper.setId(UUID.randomUUID().toString());
    paper.setExamName(request.getExamName());
    paper.setQuesIds(JSON.toJSONString(questionIds));
    paper.setMdesc(request.getMdesc());
    paper.setProductId(request.getProductId());
    paper.setQuesNum(questionIds.size());
    paper.setExamTypeId(1);
    paper.setStatus("-1");
    return this.addPaper(paper);
  }

  /**
   * 查询试卷
   * @param id
   * @return
   */
  public QuestionBankExamPaperModel findPaperById(String id){
    QuestionBankExamPaperModel paperModel = new QuestionBankExamPaperModel();
    paperModel.setId(id);
    return questionBankExamPaperDao.findEntityById(paperModel);
  }

  /**
   * 编辑试卷
   * @param paper
   * @return
   */
  public Boolean editPaper(QuestionBankExamPaperModel paper){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",paper.getId());
    return questionBankExamPaperDao.updateEntity(cond,paper);
  }

  /**
   * 编辑试卷
   * @param cond
   * @param paper
   * @return
   */
  public Boolean editPaper(Map<String,Object> cond,QuestionBankExamPaperModel paper){
    return questionBankExamPaperDao.updateEntity(cond,paper);
  }

  /**
   * 编辑试卷
   * @param request
   * @return
   */
  public Boolean editPaper(PaperEditRequest request){
    QuestionBankExamPaperModel paper = new QuestionBankExamPaperModel();
    String[] ids = request.getQuesIds().split(",");
    List<Integer> questionIds = new ArrayList<>();
    for (String id:ids){
      questionIds.add(Integer.parseInt(id));
    }
    paper.setId(request.getId());
    paper.setMdesc(request.getMdesc());
    paper.setExamName(request.getExamName());
    paper.setQuesIds(JSON.toJSONString(questionIds));
    paper.setQuesNum(questionIds.size());
    return this.editPaper(paper);
  }

  /**
   * 删除试卷
   * @param id
   * @return
   */
  public Boolean deletePaper(String id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return questionBankExamPaperDao.deleteEntity(cond);
  }

}
