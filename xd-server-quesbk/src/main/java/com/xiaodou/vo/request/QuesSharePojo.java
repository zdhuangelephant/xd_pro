package com.xiaodou.vo.request;

import org.springframework.validation.Errors;

import com.xiaodou.share.request.ShareRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;

/**
 * @name @see com.xiaodou.vo.request.QuesSharePojo.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 题库分享pojo
 * @version 1.0
 */
public class QuesSharePojo extends ShareRequest {

  /** paperId 考卷ID */
  @NotEmpty
  private String paperId;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;

  /** score 得分 */
  @NotEmpty
  @LegalValueList({"S", "A", "B", "C", "D"})
  private String score;

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    if (!"3".equals(getShareType()))
      errors.rejectValue("shareType", null, "shareType has a unvalid value, plz check it.");
    return errors;
  }

}
