package com.xiaodou.st.dataclean.model.domain.raw;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name UserLearnRecordModel
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 用户学习记录模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RawDataLearnRecordModel extends RawTransferField {
  // id
  @Column(isMajor = true)
  @GeneralField
  private Integer id;

  // 源记录ID
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String srcRecordId;
  // appId
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String moduleId;

  // 用户Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String userId;

  // 专业码值
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String typeCode;

  // 专业Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String productCategoryId;

  // 产品Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String productId;

  // 学生Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer studentId;

  /** taughtUnitId 自考办ID */
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer taughtUnitId;

  // 主考院校Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer chiefUnitId;

  // 试点单位Id
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer pilotUnitId;

  // 记录时间
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private String recordTime;

  // 学习时长
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer learnTime;

  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Short learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

  @GeneralField
  @Column(canUpdate = false, sortBy = false)
  private String learnContent;// 学习内容（eg:第一章第一节）

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(String productCategoryId) {
    this.productCategoryId = productCategoryId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public Integer getChiefUnitId() {
    return chiefUnitId;
  }

  public void setChiefUnitId(Integer chiefUnitId) {
    this.chiefUnitId = chiefUnitId;
  }

  public Integer getPilotUnitId() {
    return pilotUnitId;
  }

  public void setPilotUnitId(Integer pilotUnitId) {
    this.pilotUnitId = pilotUnitId;
  }

  public String getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(String recordTime) {
    this.recordTime = recordTime;
  }

  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public Short getLearnType() {
    return learnType;
  }

  public void setLearnType(Short learnType) {
    this.learnType = learnType;
  }

  public Integer getTaughtUnitId() {
    return taughtUnitId;
  }

  public void setTaughtUnitId(Integer taughtUnitId) {
    this.taughtUnitId = taughtUnitId;
  }

  public String getLearnContent() {
    return learnContent;
  }

  public void setLearnContent(String learnContent) {
    this.learnContent = learnContent;
  }


public String getSrcRecordId() {
	return srcRecordId;
}

public void setSrcRecordId(String srcRecordId) {
	this.srcRecordId = srcRecordId;
}

public static void main(String[] args) {
    MybatisXmlTool.getInstance(RawDataLearnRecordModel.class, "xd_raw_data_learn_record",
        "src/main/resources/conf/mybatis/raw").buildXml();
  }
}
