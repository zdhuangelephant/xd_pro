package com.xiaodou.course.model.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @author 李德洪
 * @since 2016年7月13日11:00:47
 * 
 */
public class MajorDataModel {

  // 主键Id
  @BaseField
  @Column(isMajor = true, sortBy = true)
  private String id;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String name;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String majorInfo;

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

  public String getMajorInfo() {
    return majorInfo;
  }

  public void setMajorInfo(String majorInfo) {
    this.majorInfo = majorInfo;
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(MajorDataModel.class, "xd_major_data",
            "D:\\work\\workspace_xd\\xd-course-2b\\src\\main\\resources\\conf\\mybatis\\product")
        .buildXml();

  }

  public static class MajorInfo {
    // private String typeCode;
    // // 模块名
    // private String moduleName;
    // /* 专业层次(eg:专科) */
    // private String majorLevel;
    // /* 主考院校 */
    // private String chiefAcademy;

    private String createTime;// 创建记录时间
    private String degree;// 学位（eg:文学学士）
    private String detail;
    private String examSchool;// majorLevel;主考院校
    private String majorLevel;// 专业层次(eg:专科)

    public String getCreateTime() {
      return createTime;
    }

    public void setCreateTime(String createTime) {
      this.createTime = createTime;
    }

    public String getDegree() {
      return degree;
    }

    public void setDegree(String degree) {
      this.degree = degree;
    }

    public String getDetail() {
      return detail;
    }

    public void setDetail(String detail) {
      this.detail = detail;
    }

    public String getExamSchool() {
      return examSchool;
    }

    public void setExamSchool(String examSchool) {
      this.examSchool = examSchool;
    }

    public String getMajorLevel() {
      return majorLevel;
    }

    public void setMajorLevel(String majorLevel) {
      this.majorLevel = majorLevel;
    }
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }
}
