package com.xiaodou.facerecognition;

import lombok.Data;

/**
 * @name FaceCompareResponse
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description 人脸比较响应
 * @version 1.0
 */
@Data
public class FaceCompareResponse {
  /** retCode 响应码 */
  private String retCode;
  /** retDesc 描述 */
  private String retDesc;
  /** similarity 相似度 */
  private Double similarity;

  public FaceCompareResponse(RetCode retCode) {
    this.retCode = retCode.getCode();
    this.retDesc = retCode.getDesc();
  }
}
