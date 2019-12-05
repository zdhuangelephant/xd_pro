package com.xiaodou.vo.response;

import com.xiaodou.constant.ResultType;

/**
 * @name FaceRecognitionResponse
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月30日
 * @description 面部识别响应类
 * @version 1.0
 */
public class FaceRecognitionResponse extends BaseResponse {
  public FaceRecognitionResponse(ResultType type) {
    super(type);
  }

  /** isSelf 是否本人 */
  private String isSelf;

  public String getIsSelf() {
    return isSelf;
  }

  public void setIsSelf(String isSelf) {
    this.isSelf = isSelf;
  }
}
