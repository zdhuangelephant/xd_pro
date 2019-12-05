package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

/**
 * 
 * 51xiaodou.com (c) 2016 by 李德洪
 * 
 * @name LearnRecordRequest
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */
public class LearnRecordRequest extends BaseRequest {

  @NotEmpty
  private Integer learnTime;// 学习时长(秒为单位)

  @NotEmpty
  private String courseId; // 课程ID
  @OrNotEmptyList({@NotEmpty(field = "learnType", value = "41"),
      @NotEmpty(field = "learnType", value = "21"), @NotEmpty(field = "learnType", value = "22")})
  private String chapterId;// 章ID
  @OrNotEmptyList({@NotEmpty(field = "learnType", value = "21"),
      @NotEmpty(field = "learnType", value = "22")})
  private String itemId;// 节ID
  @NotEmpty
  @LegalValueList({"11", "12", "21", "22", "31", "41"})
  private String learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

  private String learnScope;// 范围（courseId,chapterId,itemId的非空组合范围）


  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getLearnType() {
    return learnType;
  }

  public void setLearnType(String learnType) {
    this.learnType = learnType;
  }

  public String getLearnScope() {
    return learnScope;
  }

  public void setLearnScope(String learnScope) {
    this.learnScope = learnScope;
  }

}
