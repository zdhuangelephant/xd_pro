package com.xiaodou.course.model.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class FinalExamModel {
  /** id 主键ID */
  @Column(isMajor = true)
  private Long id;
  /** examName 试卷 */
  private String examName;
  /** questionNums 试题个数 */
  private Integer questionNums;
  /** sort 排序角标 */
  private Integer sort;
  /** courseId 关联课程id */
  private Long courseId;
  /** createTime 创建时间 */
  private Timestamp createTime = new Timestamp(System.currentTimeMillis());

  private String score;
  private String status = "0";

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(FinalExamModel.class, "xd_course_product_final_exam",
        "E:/work3/xd-course/src/main/resources/conf/mybatis/product").buildXml();
  }


  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = examName;
  }

  public Integer getQuestionNums() {
    return questionNums;
  }

  public void setQuestionNums(Integer questionNums) {
    this.questionNums = questionNums;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

}
