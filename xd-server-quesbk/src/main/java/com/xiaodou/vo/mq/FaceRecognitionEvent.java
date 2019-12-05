package com.xiaodou.vo.mq;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.vo.mq.FaceRecognitionEvent.FaceCompareResult;

/**
 * @name FaceRecognitionEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 面部识别事件
 * @version 1.0
 */
public class FaceRecognitionEvent extends DCCoreEvent<FaceCompareResult> {
  private static final String EVENT_NAME = "faceRecognitionEvent";

  public FaceRecognitionEvent() {
    setEventName(EVENT_NAME);
  }

  /**
   * @name FaceCompareResult
   * @CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月31日
   * @description 人脸比较结果
   * @version 1.0
   */
  @Data
  public static class FaceCompareResult {
    /** module 模块ID */
    private String module;
    /** userId 用户ID */
    private String userId;
    /** deviceId 登录设备ID */
    private String deviceId;
    /** paperId 试卷ID */
    private String paperId;
    /** typeCode 专业码值 */
    private String typeCode;
    /** productId 产品ID */
    private String productId;
    /** chapterName 章节名称 */
    private String chapterName;
    /** faceUrl 人脸地址 */
    private String faceUrl;
    /** similarity 相似度 */
    private Double similarity;
    /** isSelf 是否本人 */
    private String isSelf;
    /** recordTime 记录时间 */
    private Timestamp recordTime;
    public FaceCompareResult(){}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
}
