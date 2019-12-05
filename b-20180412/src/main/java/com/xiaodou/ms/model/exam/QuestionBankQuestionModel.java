package com.xiaodou.ms.model.exam;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.ms.model.product.ProductQuestionModel;

import lombok.Data;

/**
 * Created by zyp on 15/6/26.
 */
@Data
public class QuestionBankQuestionModel {

  /**
   * 主键ID
   */
  private Long id;

  /**
   * 题目类型
   */
  private Integer questionType;

  /**
   * 题目来源
   */
//  private Integer resourceId;

  /**
   * 题目来源
   */
//  private String questionSrc;

  /**
   * 章节ID
   */
  private Long chapterId;

  /**
   * 课程ID
   */
  private Long courseId;

  /**
   * 题目图片
   */
  private String quesImgUrl;

  /**
   * 考点
   */
  private String keyPoint;

  /**
   * 认知程度
   */
  private Integer cognitionLevel;

  /**
   * 难易程度
   */
  private Integer diffcultLevel;

  /**
   * 答案
   */
  private String answerIds;

  /**
   * 题干
   */
  private String mdesc;

  /**
   * 解析
   */
  private String manalyze;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 杂项
   */
  private String misc;

  /**
   * 答案
   */
  private String answerList;

  /**
   * 出题时间
   */
  private Timestamp quesTime;

  /**
   * 章节名称
   */
  private String chapterName;

  /**
   * 是否为真题
   */
  private Integer zhenti;

  /**
   * 关联产品题库
   */
  private List<ProductQuestionModel> relationProductQuestions;
  
}
