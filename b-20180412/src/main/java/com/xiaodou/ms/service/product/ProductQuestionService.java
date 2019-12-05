package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.dao.product.ProductQuestionDao;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductQuestionModel;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/8/11.
 */
@Service("productQuestionService")
public class ProductQuestionService {

  @Resource
  ProductQuestionDao productQuestionDao;

  @Resource
  ProductItemService productItemService;

  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  /**
   * 统计产品题目数量
   * 
   * @param productId
   */
  public void staticsProductChapterQuestionNum(Long productId) {
    List<ProductItemModel> itemList = productItemService.queryAllChapterItem(productId);
    for (ProductItemModel productItem : itemList) {
      String chapterIds = productItem.getAllChildId() + productItem.getId();
      String[] chapterIdArray = chapterIds.split(",");
      List<Long> chapterIdList = new ArrayList<>();
      for (String chapterIdString : chapterIdArray) {
        chapterIdList.add(Long.parseLong(chapterIdString));
      }
      List<ProductQuestionModel> questionList =
          this.queryQuestionListByChapterIds(productId, chapterIdList);
      int questionNum = questionList.size();
      if (productItem.getQuesNum() != questionNum) {
        ProductItemModel updateItem = new ProductItemModel();
        updateItem.setId(productItem.getId());
        updateItem.setQuesNum(questionNum);
        productItemService.editItem(updateItem);
      }
    }
  }

  /**
   * 更新记录
   * 
   * @param questionId
   * @param cognitionLevel
   * @param diffcultLevel
   * @return
   */
  public Boolean updateProductQuestionByQuestionId(Long questionId, Integer cognitionLevel,
      Integer diffcultLevel, Integer questionType) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("questionId", questionId);
    ProductQuestionModel productQuestionModel = new ProductQuestionModel();
    productQuestionModel.setCognitionLevel(cognitionLevel);
    productQuestionModel.setDiffcultLevel(diffcultLevel);
    if (questionType != null) productQuestionModel.setQuestionType(questionType);
    return productQuestionDao.updateEntity(cond, productQuestionModel);
  }

  /**
   * 非级联查找题目
   * 
   * @param productId
   * @param chapterId
   * @return
   */
  public List<ProductQuestionModel> queryQuestionListByChapterIdWithoutCascade(Long productId,
      Long chapterId) {
    Map<String, Object> input = new HashMap<>();
    input.put("productId", productId);
    input.put("chapterId", chapterId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("questionId", "");
    output.put("createTime", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("questionType", "");
    output.put("questionDesc", "");
    Page<ProductQuestionModel> productQuestionModelPage =
        productQuestionDao.queryListByCond0(input, output, null);
    return productQuestionModelPage.getResult();
  }

  /**
   * 
   * @param productId
   * @param questionIds
   * @return
   */
  public List<ProductQuestionModel> queryQuestionListByQuestionIds(Long productId,
      List<Long> questionIds) {
    if (questionIds == null || questionIds.size() <= 0) {
      return new ArrayList<ProductQuestionModel>();
    }
    Map<String, Object> input = new HashMap<>();
    input.put("productId", productId);
    input.put("questionIds", questionIds);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("questionId", "");
    output.put("createTime", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("questionType", "");
    output.put("questionDesc", "");
    output.put("chapterName", "");
    output.put("chapterIdAlias", "");
    Page<ProductQuestionModel> productQuestionModelPage =
        productQuestionDao.queryListByCond0(input, output, null);
    return productQuestionModelPage.getResult();
  }

  /**
   * 根据章节ID查询题库数
   * 
   * @param productId
   * @param chapterIds
   * @return
   */
  public List<ProductQuestionModel> queryQuestionListByChapterIds(Long productId,
      List<Long> chapterIds) {
    Map<String, Object> input = new HashMap<>();
    input.put("productId", productId);
    input.put("chapterIds", chapterIds);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("questionId", "");
    output.put("createTime", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("questionType", "");
    output.put("questionDesc", "");
    Page<ProductQuestionModel> productQuestionModelPage =
        productQuestionDao.queryListByCond0(input, output, null);
    return productQuestionModelPage.getResult();
  }

  /**
   * 根据章节查询试题列表
   * 
   * @param productId
   * @param chapterId
   * @return
   */
  public List<ProductQuestionModel> queryQuestionListByChapterId(Long productId, Long chapterId) {
    // 子章节Id
    List<ProductItemModel> childChapters =
        productItemService.queryAllChildChapterItem(chapterId, productId);
    List<Long> chapterIds = new ArrayList<>();
    for (ProductItemModel chapter : childChapters) {
      chapterIds.add(chapter.getId());
    }
    chapterIds.add(chapterId);

    Map<String, Object> input = new HashMap<>();
    input.put("productId", productId);
    input.put("chapterIds", chapterIds);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("questionId", "");
    output.put("createTime", "");
    output.put("cognitionLevel", "");
    output.put("diffcultLevel", "");
    output.put("questionType", "");
    output.put("questionDesc", "");
    Page<ProductQuestionModel> productQuestionModelPage =
        productQuestionDao.queryListByCond0(input, output, null);
    return productQuestionModelPage.getResult();
  }

  /**
   * 删除记录
   * 
   * @param id
   * @return
   */
  public Boolean deleteQuestion(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return productQuestionDao.deleteEntity(cond);
  }

  /**
   * 批量删除
   * 
   * @param ids
   * @return
   */
  public Boolean deleteQuestions(List<Long> ids) {
    if (ids == null || ids.size() <= 0) {
      return true;
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", ids);
    return productQuestionDao.deleteEntity(cond);
  }


  public Boolean deleteQuestionsByChapter(Long productId, Long chapterId) {
    if (productId == null || chapterId == null) {
      return true;
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("productId", productId);
    cond.put("chapterId", chapterId);
    return productQuestionDao.deleteEntity(cond);
  }

  /**
   * 批量增加
   * 
   * @param chapterId
   * @param productId
   * @param questionList
   * @return
   */
  public boolean addQuestions(Long chapterId, Long productId,
      List<QuestionBankQuestionModel> questionList) {

    for (QuestionBankQuestionModel questionBankQuestionModel : questionList) {
      ProductQuestionModel productQuestionModel = new ProductQuestionModel();
      productQuestionModel.setProductId(productId);
      productQuestionModel.setChapterId(chapterId);
      productQuestionModel.setCognitionLevel(questionBankQuestionModel.getCognitionLevel());
      productQuestionModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      productQuestionModel.setQuestionType(questionBankQuestionModel.getQuestionType());
      productQuestionModel.setDiffcultLevel(questionBankQuestionModel.getDiffcultLevel());
      productQuestionModel.setQuestionId(questionBankQuestionModel.getId());
      productQuestionDao.addEntity(productQuestionModel);
    }
    return true;
  }

  /**
   * 增加题目
   * 
   * @param questionId
   * @param chapterId
   * @param productId
   * @return
   */
  public ProductQuestionModel addQuestion(Long questionId, Long chapterId, Long productId) {
    QuestionBankQuestionModel question =
        questionBankQuestionService.findResourceQuesById(questionId);
    ProductQuestionModel productQuestionModel = new ProductQuestionModel();
    productQuestionModel.setProductId(productId);
    productQuestionModel.setChapterId(chapterId);
    productQuestionModel.setCognitionLevel(question.getCognitionLevel());
    productQuestionModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    productQuestionModel.setQuestionType(question.getQuestionType());
    productQuestionModel.setDiffcultLevel(question.getDiffcultLevel());
    productQuestionModel.setQuestionId(question.getId());
    return productQuestionDao.addEntity(productQuestionModel);
  }

  public List<ProductQuestionModel> queryQuesListByProductId(Long id) {
    ProductQuestionModel entity = new ProductQuestionModel();
    entity.setProductId(id);
    IQueryParam param = new QueryParam();
    param.addInput("productId", id);
    Map<String, Object> allField = CommUtil.getAllField(ProductQuestionModel.class);
    allField.remove("questionDesc");
    allField.remove("chapterName");
    allField.remove("chapterIdAlias");
    param.addOutputs(allField);
    Page<ProductQuestionModel> page = productQuestionDao.findEntityListByCond(param, null);
    return page != null ? page.getResult() : null;
  }
}
