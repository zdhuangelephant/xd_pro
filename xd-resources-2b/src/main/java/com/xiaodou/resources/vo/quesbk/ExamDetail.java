package com.xiaodou.resources.vo.quesbk;


/**
 * @name ExamDetail 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月31日
 * @description 练习出题接口参数模型
 * @version 1.0
 */
public class ExamDetail {
  /** uid 用户ID */
  private String uid;
  /** courseId 课程ID */
  private String courseId;
  /** chapterId 章ID */
  private String chapterId;
  /** itemId 节ID */
  private String itemId;
  /** examType 测验类型 */
  private String examType;
  /** recordId 挑战记录ID, 练习类型为挑战时必传 */
  private String recordId;
  /** priorQues  */
  private String priorQues;
  /** lastQuesId  */
  private String lastQuesId;
  public ExamDetail(String uid) {
    this.uid = uid;
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
  public String getRecordId() {
    return recordId;
  }
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid = uid;
  }
  public String getPriorQues() {
    return priorQues;
  }
  public void setPriorQues(String priorQues) {
    this.priorQues = priorQues;
  }
  public String getLastQuesId() {
    return lastQuesId;
  }
  public void setLastQuesId(String lastQuesId) {
    this.lastQuesId = lastQuesId;
  }

}