package com.xiaodou.facerecognition;

import lombok.Data;

/**
 * @name FaceId
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description 人脸唯一记录
 * @version 1.0
 */
@Data
public class FaceIdResponse {
  /** retCode 响应码 */
  private String retCode;
  /** retDesc 描述 */
  private String retDesc;
  /** faceId ID */
  private String faceId;

  public FaceIdResponse(RetCode retCode) {
    this.retCode = retCode.getCode();
    this.retDesc = retCode.getDesc();
  }
}
