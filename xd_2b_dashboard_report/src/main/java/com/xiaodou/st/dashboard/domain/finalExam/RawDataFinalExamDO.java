package com.xiaodou.st.dashboard.domain.finalExam;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RawDataFinalExamDO {

  private Long id;
  private Long productId;
  private Long studentId;
  /**
   * 区分不同类型，可以省去排序的判断（0、章1、期末测试）
   */
  private Short type;
  //名称内容
  private String content;
  //占比
  private Double weight;
  //排序
  private Short order;
  //得分
  private Double score;
  private Timestamp createTime; 
}
