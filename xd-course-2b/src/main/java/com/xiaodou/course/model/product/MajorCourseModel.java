package com.xiaodou.course.model.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @author 李德洪
 * @since 2016年7月13日10:02:01
 * 
 */
public class MajorCourseModel {

  // 主键Id
  @BaseField
  @GeneralField
  @Column(isMajor = true, sortBy = true)
  private String id;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String majorCourseInfo;

  /** region 地域 */
  @Column(canUpdate = true, sortBy = false)
  private String region;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMajorCourseInfo() {
    return majorCourseInfo;
  }

  public void setMajorCourseInfo(String majorCourseInfo) {
    this.majorCourseInfo = majorCourseInfo;
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(MajorCourseModel.class, "xd_major_course",
            "D:\\work\\workspace_xd\\xd-course-2b\\src\\main\\resources\\conf\\mybatis\\product")
        .buildXml();

  }

  public static class MajorCourseInfo {
    // private String courseQuality;
    // private String courseCredit;// 课程学分
    // private String courseCheckType;// 课程考核方式
    // private String examDetail;// 近期考试安排详情(与考试院一致)
    // private String major;// 专业名称
    // private String majorCode; // 专业码值
    // private String score;// 得分
    // private String myExamDate;// 我的考试期（1610）每条数据有且最多只存在两个选择项（**04/**10）
    // private String examDateStatus;// 1近期2其它考期(用户自定义)

    private String courseType;// courseQuality; 课程性质
    private String createTime;//
    private String credit;// courseCredit;课程学分
    private String detail;// 课程介绍
    private String examDate;// 2016年10月16日星期日下午14:30-17:00;2016年04月24日星期日下午14:30-17:00;
    private String examDateType;// 课程考期类型 0 上半年 1 下半年 2 整年
    private String mode;// courseCheckType; 课程考核方式

    public String getCourseType() {
      return courseType;
    }

    public void setCourseType(String courseType) {
      this.courseType = courseType;
    }

    public String getCreateTime() {
      return createTime;
    }

    public void setCreateTime(String createTime) {
      this.createTime = createTime;
    }

    public String getCredit() {
      return credit;
    }

    public void setCredit(String credit) {
      this.credit = credit;
    }

    public String getDetail() {
      return detail;
    }

    public void setDetail(String detail) {
      this.detail = detail;
    }

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      this.examDate = examDate;
    }

    public String getExamDateType() {
      return examDateType;
    }

    public void setExamDateType(String examDateType) {
      this.examDateType = examDateType;
    }

    public String getMode() {
      return mode;
    }

    public void setMode(String mode) {
      this.mode = mode;
    }
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

}
