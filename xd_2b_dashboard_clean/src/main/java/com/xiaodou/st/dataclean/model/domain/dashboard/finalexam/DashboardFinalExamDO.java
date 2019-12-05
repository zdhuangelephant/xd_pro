package com.xiaodou.st.dataclean.model.domain.dashboard.finalexam;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DashboardFinalExamDO {
  private Long id;
  private Long productId;
  private Long studentId;
  /**
   * 类型（1章节测评，2期末测试，3阶段测评成绩，4学习行为成绩，5总成绩）
   */
  private Short type;
  //名称内容
  private String content;
  //占比
  private Double weight;
  //得分
  private Double score;
  private Timestamp createTime; 
  
}
