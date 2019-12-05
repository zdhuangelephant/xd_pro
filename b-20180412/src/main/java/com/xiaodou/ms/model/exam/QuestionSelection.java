package com.xiaodou.ms.model.exam;

/**
 * Created by zyp on 15/7/28.
 */
public class QuestionSelection {

  public static final Integer YES = 1;

  public static final Integer NO = 0;

  public static final Integer textOption = 1;

  public static final Integer imageOption = 2;

  // 主键
  private String id;

  // 选项
  private String selection;

  // 是答案
  private Integer isAnswer;

  // 图片
  private String imgUrl;

  // 类型
  private Integer optionType;

  public Integer getOptionType() {
    return optionType;
  }

  public void setOptionType(Integer optionType) {
    this.optionType = optionType;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public Integer getIsAnswer() {
    return isAnswer;
  }

  public void setIsAnswer(Integer isAnswer) {
    this.isAnswer = isAnswer;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSelection() {
    return selection;
  }

  public void setSelection(String selection) {
    this.selection = selection;
  }
}
