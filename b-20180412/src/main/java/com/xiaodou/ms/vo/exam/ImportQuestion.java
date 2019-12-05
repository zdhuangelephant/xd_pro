package com.xiaodou.ms.vo.exam;


import lombok.Data;

@Data
public class ImportQuestion {
  public String chapterName;
  public String sectionName;
//  public String questionSrc;
  public String zhenti;
  public String questionType;
  public String mdesc;
  public String manalyze;
  public String optA;
  public String optB;
  public String optC;
  public String optD;
  public String answerIds;
  public String point;
  // 存储错误信息的字段列
  private String msg;

}
