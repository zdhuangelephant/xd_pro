package com.xiaodou.vo.request;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.enums.ExamType;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;

public class ExamDetailPojo extends QuesBasePojo {

  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;
  /**
   * 章ID 章节练习/课后练习chapterId不为空
   */
  @OrNotEmptyList({@NotEmpty(field = "examType", value = "4"),
      @NotEmpty(field = "examType", value = "5")})
  private String chapterId;
  /**
   * 考试类型 参考枚举类型ExamType
   */
  @NotEmpty
  @LegalValueList(value = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"})
  private String examType;

  /**
   * 试卷编号 真题练习paperId不为空 继续练习paperId不为空
   */
  @NotEmpty(field = "examType", value = "1")
  private String paperId;

  /**
   * 节ID
   */
  @NotEmpty(field = "examType", value = "6")
  private String itemId;

  /** priorQues 优先选择 */
  @LegalValueList({"1", "2"})
  private String priorQues;

  /** lastQuesId 试题ID */
  private String lastQuesId;

  /** recordId 挑战记录ID, 练习类型为挑战时必传 */
  @OrNotEmptyList({@NotEmpty(field = "examType", value = "7"),
      @NotEmpty(field = "examType", value = "8")})
  private String recordId;

  /** userType 用户类型 */
  private String userType;

  /** srcFaceId 源面容ID */
  private String srcFaceId;

  /** userCredit 用户积分 */
  private Integer userCredit = 0;

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getSrcFaceId() {
    return srcFaceId;
  }

  public void setSrcFaceId(String srcFaceId) {
    this.srcFaceId = srcFaceId;
  }

  public String getLastQuesId() {
    return lastQuesId;
  }

  public void setLastQuesId(String lastQuesId) {
    this.lastQuesId = lastQuesId;
  }

  public String getPriorQues() {
    return priorQues;
  }

  public void setPriorQues(String priorQues) {
    this.priorQues = priorQues;
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

  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    ExamType exam = ExamType.getByCode(examType);
    if (exam.equals(ExamType.RANDOM_CHALLENGE_PAPER)
        || exam.equals(ExamType.FRIEND_CHALLENGE_PAPER) || exam.equals(ExamType.DAILY_PRACTICE)) {
      setChapterId("-1");
    } else if (StringUtils.isBlank(chapterId)) {
      errors.rejectValue("chapterId", null, "chapterId can't be null");
    }
    return errors;
  }

  public Integer getUserCredit() {
    return userCredit;
  }

  public void setUserCredit(Integer userCredit) {
    this.userCredit = userCredit;
  }

}
