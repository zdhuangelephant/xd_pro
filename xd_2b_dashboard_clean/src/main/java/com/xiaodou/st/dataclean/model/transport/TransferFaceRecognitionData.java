package com.xiaodou.st.dataclean.model.transport;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name TransferFaceRecognitionData
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 人脸比较结果
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferFaceRecognitionData extends BaseTransferModel {
  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  /** deviceId 登录设备ID */
  @NotEmpty
  private String deviceId;
  /** userId 用户ID */
  @NotEmpty
  private String userId;
  /** paperId 试卷ID */
  @NotEmpty
  private String paperId;
  /** typeCode 专业码值 */
  @NotEmpty
  private String typeCode;
  /** productId 产品ID */
  @NotEmpty
  private String productId;
  /** chapterName 章节名称 */
  @NotEmpty
  private String chapterName;
  /** faceUrl 人脸地址 */
  @NotEmpty
  private String faceUrl;
  /** similarity 相似度 */
  @NotEmpty
  private Double similarity;
  /** isSelf 是否本人 */
  @NotEmpty
  private String isSelf;
  /** recordTime 记录时间 */
  @NotEmpty
  private Timestamp recordTime;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getFaceUrl() {
    return faceUrl;
  }

  public void setFaceUrl(String faceUrl) {
    this.faceUrl = faceUrl;
  }

  public Double getSimilarity() {
    return similarity;
  }

  public void setSimilarity(Double similarity) {
    this.similarity = similarity;
  }

  public String getIsSelf() {
    return isSelf;
  }

  public void setIsSelf(String isSelf) {
    this.isSelf = isSelf;
  }

  public Timestamp getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Timestamp recordTime) {
    this.recordTime = recordTime;
  }
}
