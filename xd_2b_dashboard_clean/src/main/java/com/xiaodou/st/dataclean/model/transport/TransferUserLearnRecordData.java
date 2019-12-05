package com.xiaodou.st.dataclean.model.transport;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name TransferUserLearnRecordData
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 用户学习记录模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferUserLearnRecordData extends BaseTransferModel {
  // 源记录ID
  @NotEmpty
  private Integer srcRecordId;
  // 用户Id
  @NotEmpty
  private Integer userId;
  /** typeCode 专业码值 */
  @NotEmpty
  private String typeCode;
  // 产品Id
  @NotEmpty
  private Integer productId;
  // 章节Id
  @NotEmpty
  private Integer chapterId;
  // 条目Id
  @NotEmpty
  private Integer itemId;
  // 记录时间
  @NotEmpty
  private Timestamp recordTime;
  // 学习时长
  @NotEmpty
  private Integer learnTime;
  // appId
  @NotEmpty
  private Integer moduleId;
  @NotEmpty
  private Short learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

  @NotEmpty
  private String learnContent;// 学习内容（eg:第一章第一节）

  public TransferUserLearnRecordData() {}

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(TransferUserLearnRecordData.class, "xd_dashboard_learn_record",
        "E:/work3/xd-dashboard-2b/src/main/resources/conf/mybatis/").buildXml();
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Timestamp getRecordTime() {
    return recordTime;
  }

  public String getLearnContent() {
    return learnContent;
  }

  public void setLearnContent(String learnContent) {
    this.learnContent = learnContent;
  }

  public void setRecordTime(Timestamp recordTime) {
    this.recordTime = recordTime;
  }

  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
    if (null != moduleId) {
      setRealSqlSessionKey(moduleId.toString());
    }
  }

  public Short getLearnType() {
    return learnType;
  }

  public void setLearnType(Short learnType) {
    this.learnType = learnType;
  }

  public Integer getSrcRecordId() {
    return srcRecordId;
  }

  public void setSrcRecordId(Integer srcRecordId) {
    this.srcRecordId = srcRecordId;
  }

}
