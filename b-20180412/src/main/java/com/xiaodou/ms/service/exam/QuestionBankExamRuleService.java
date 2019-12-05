package com.xiaodou.ms.service.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.exam.QuestionBankExamRuleDao;
import com.xiaodou.ms.model.exam.ExamRule;
import com.xiaodou.ms.model.exam.ExamRuleItem;
import com.xiaodou.ms.model.exam.QuestionBankExamRuleModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/7/28.
 */
@Service("questionBankExamRuleService")
public class QuestionBankExamRuleService {

  @Resource
  QuestionBankExamRuleDao questionBankExamRuleDao;

  @Resource
  QuestionBankExamRuleService questionBankExamRuleService;

  /**
   * 增加
   * 
   * @param model
   * @return
   */
  public QuestionBankExamRuleModel addEntity(QuestionBankExamRuleModel model) {
    return questionBankExamRuleDao.addEntity(model);
  }

  /**
   * 删除
   * 
   * @param id
   * @return
   */
  public Boolean delete(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return questionBankExamRuleDao.deleteEntity(cond);
  }

  /**
   * 编辑出题规则
   * 
   * @param questionBankExamRuleModel
   * @return
   */
  public Boolean edit(QuestionBankExamRuleModel questionBankExamRuleModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", questionBankExamRuleModel.getId());
    return questionBankExamRuleDao.updateEntity(cond, questionBankExamRuleModel);
  }

  /**
   * 编辑
   * 
   * @param cond
   * @param ruleModel
   * @return
   */
  public Boolean editByCond(Map<String, Object> cond, QuestionBankExamRuleModel ruleModel) {
    return questionBankExamRuleDao.updateEntity(cond, ruleModel);
  }

  /**
   * 根据Id查找
   * 
   * @param id
   * @return
   */
  public QuestionBankExamRuleModel findEntityById(Integer id) {
    QuestionBankExamRuleModel cond = new QuestionBankExamRuleModel();
    cond.setId(id);
    return questionBankExamRuleDao.findEntityById(cond);
  }

  /**
   * 查询默认命题蓝图列表
   * 
   * @return
   */
  public List<QuestionBankExamRuleModel> findDefaultRuleList() {
    return findExamRulesByConds(null, null, XdmsConstant.RULE_TYPE_DEFAULT);
  }

  /**
   * 查询产品命题蓝图列表
   * 
   * @param productId
   * @return
   */
  public List<QuestionBankExamRuleModel> findExamRulesByProductId(Long productId) {
    return findExamRulesByConds(productId, null, XdmsConstant.RULE_TYPE_NORMAL);
  }

  /**
   * 获取命题规则列表
   */
  private List<QuestionBankExamRuleModel> findExamRulesByConds(Long productId, Integer status,
      Integer type) {
    Map<String, Object> cond = new HashMap<>();
    if (null != productId) cond.put("productId", productId);
    if (null != status) cond.put("status", status);
    if (null != type) cond.put("type", type);
    Map<String, Object> output = new HashMap<>();
    output.put("id", 1);
    output.put("productId", 1);
    output.put("name", 1);
    output.put("examTypeId", 1);
    output.put("ruleDetail", 1);
    output.put("status", 1);
    output.put("type", 1);
    output.put("chapterId", 1);
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(output);
    Page<QuestionBankExamRuleModel> examRuleModelPage =
        questionBankExamRuleDao.findEntityListByCond(param, null);
    return examRuleModelPage.getResult();
  }

  /**
   * 获取产品规则列表
   * 
   * @param productId
   * @return
   */
  public List<QuestionBankExamRuleModel> findChapterRules(Integer productId, Integer chapterId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("productId", productId);
    cond.put("chapterId", chapterId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", 1);
    output.put("productId", 1);
    output.put("name", 1);
    output.put("examTypeId", 1);
    output.put("ruleDetail", 1);
    output.put("status", 1);
    output.put("type", 1);
    output.put("chapterId", 1);
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(output);
    Page<QuestionBankExamRuleModel> examRuleModelPage =
        questionBankExamRuleDao.findEntityListByCond(param, null);
    return examRuleModelPage.getResult();
  }

  /**
   * 从默认蓝图初始化
   * 
   * @param productId
   */
  public void initFromDefaultRule(Long productId) {
    List<QuestionBankExamRuleModel> defaultRules = findDefaultRuleList();
    if (null == defaultRules || defaultRules.isEmpty()) return;
    for (QuestionBankExamRuleModel defaultRule : defaultRules) {
      QuestionBankExamRuleModel questionBankExamRuleModel = new QuestionBankExamRuleModel();
      questionBankExamRuleModel.setName(defaultRule.getName());
      questionBankExamRuleModel.setExamTypeId(defaultRule.getExamTypeId());
      questionBankExamRuleModel.setProductId(productId);
      questionBankExamRuleModel.setStatus(defaultRule.getStatus());
      questionBankExamRuleModel.setType(XdmsConstant.RULE_TYPE_NORMAL);
      if (StringUtils.isJsonNotBlank(defaultRule.getRuleDetail())) {
        ExamRule examRule =
            FastJsonUtil.fromJsons(defaultRule.getRuleDetail(), new TypeReference<ExamRule>() {});
        if (null == examRule || null == examRule.getItemList() || examRule.getItemList().isEmpty()) {
          break;
        }
        List<ExamRuleItem> examRuleItemList = examRule.getItemList();
        for (ExamRuleItem ruleItem : examRuleItemList) {
          if (ruleItem.getProductChapterId().intValue() == XdmsConstant.COMMON_PRODUCT_ID) {
            ruleItem.setProductChapterId(productId);
          }
        }
        questionBankExamRuleModel.setRuleDetail(FastJsonUtil.toJson(examRule));
      }
      questionBankExamRuleService.addEntity(questionBankExamRuleModel);
    }
  }

  /**
   * 删除产品命题蓝图
   * 
   * @param id
   */
  public void deleteProductRule(Long id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("productId", id);
    questionBankExamRuleDao.deleteEntity(cond);
  }

}
