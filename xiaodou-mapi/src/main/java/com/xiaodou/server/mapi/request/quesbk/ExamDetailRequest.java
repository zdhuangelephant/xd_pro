package com.xiaodou.server.mapi.request.quesbk;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.enums.ExamType;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.AllNotEmptyList;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.validator.enums.ValueScope;

public class ExamDetailRequest extends MapiBaseRequest {

  private Integer currVersion;
  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;
  /**
   * 章ID 章节练习/课后练习chapterId不为空
   */
  /** chapterId 章节ID */
  @OrNotEmptyList({@NotEmpty(field = "examType", value = "5"),
      @NotEmpty(field = "examType", value = "4")})
  private String chapterId;
  /** itemId 节ID */
  @OrNotEmptyList({@NotEmpty(field = "examType", value = "10")})
  @AllNotEmptyList({@NotEmpty(field = "examType", value = "6"),
      @NotEmpty(field = "currVersion", value = "140", scope = ValueScope.GE)})
  private String itemId;
  /**
   * 考试类型 参考枚举类型ExamType
   */
  @NotEmpty
  @LegalValueList(value = {"4", "5", "6", "7", "8", "9", "10", "11"})
  private String examType;

  /** recordId 挑战记录ID, 练习类型为挑战时必传 */
  @OrNotEmptyList({@NotEmpty(field = "examType", value = "7"),
      @NotEmpty(field = "examType", value = "8")})
  private String recordId;

  public Integer getCurrVersion() {
    return currVersion;
  }

  public void setCurrVersion(Integer currVersion) {
    this.currVersion = currVersion;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  @Override
  public Errors validate() {
    if (StringUtils.isNotBlank(getVersion())) {
      String sCurrentVersion = getVersion().replaceAll(".", "");
      if (StringUtils.isNotBlank(sCurrentVersion))
        this.currVersion = Integer.parseInt(sCurrentVersion);
    }
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    ExamType exam = ExamType.getByCode(examType);
    if (exam.isAllCourse()) {
      setChapterId("-1");
    } else if (StringUtils.isBlank(chapterId)) {
      errors.rejectValue("chapterId", null, "chapterId can't be null");
    }
    return errors;
  }
}
