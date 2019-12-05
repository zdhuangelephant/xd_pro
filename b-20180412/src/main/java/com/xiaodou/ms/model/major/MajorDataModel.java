package com.xiaodou.ms.model.major;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 
 * @ClassName: MajorDataModel
 * @Description: 专业Model
 * @author zhouhuan
 * @date 2016年7月1日
 */
public class MajorDataModel {

  // 专业ID
  @Column(isMajor = true)
  private String id;

  // 专业名称
  @GeneralField
  private String name;

  // 专业介绍
  private String detail;

  // 主考院校
  private String examSchool;
  // 专业层次
  private String majorLevel;
  // 学位
  private String degree;
  // 创建时间
  private Timestamp createTime;
  // 专业信息
  @GeneralField
  private String majorInfo;

  // 地域
  @GeneralField
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

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getMajorInfo() {
    return majorInfo;
  }

  public void setMajorInfo(String majorInfo) {
    this.majorInfo = majorInfo;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public void initModuleInfo(String majorInfo) {
    if (StringUtils.isJsonNotBlank(majorInfo)) {
      MajorInfo mInfo = FastJsonUtil.fromJson(majorInfo, MajorInfo.class);
      if (null != mInfo) {
        if (StringUtils.isNotBlank(mInfo.getDetail())) detail = mInfo.getDetail();
        if (StringUtils.isNotBlank(mInfo.getExamSchool())) examSchool = mInfo.getExamSchool();
        if (StringUtils.isNotBlank(mInfo.getMajorLevel())) majorLevel = mInfo.getMajorLevel();
        if (StringUtils.isNotBlank(mInfo.getDegree())) degree = mInfo.getDegree();
        if (mInfo.getCreateTime() != null) createTime = mInfo.getCreateTime();
      }
    }
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(MajorDataModel.class, "xd_major_data",
        "E:/work/xd-ms2b/src/main/resources/conf/mybatis").buildXml();
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

}
