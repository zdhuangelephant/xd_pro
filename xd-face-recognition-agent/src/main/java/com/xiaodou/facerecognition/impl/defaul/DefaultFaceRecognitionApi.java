package com.xiaodou.facerecognition.impl.defaul;

import com.xiaodou.facerecognition.FaceCompareResponse;
import com.xiaodou.facerecognition.FaceIdResponse;
import com.xiaodou.facerecognition.IFaceRecognitionApi;
import com.xiaodou.facerecognition.RetCode;

/**
 * @name @see com.xiaodou.facerecognition.impl.defaul.DefaultFaceRecognitionApi.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 默认面部识别Api
 * @version 1.0
 */
public class DefaultFaceRecognitionApi implements IFaceRecognitionApi {

  @Override
  public FaceIdResponse addFace(String faceUrl) {
    FaceIdResponse response = new FaceIdResponse(RetCode.Success);
    response.setFaceId(faceUrl);
    return response;
  }

  @Override
  public FaceCompareResponse compare(String faceId1, String faceId2) {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {}
    FaceCompareResponse response = new FaceCompareResponse(RetCode.Success);
    response.setSimilarity(100d);
    return response;
  }

}
