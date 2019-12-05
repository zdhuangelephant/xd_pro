package com.xiaodou.facerecognition;


/**
 * @name IFaceRecognitionApi
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description 人脸识别接口API
 * @version 1.0
 */
public interface IFaceRecognitionApi {

  public FaceIdResponse addFace(String faceUrl);

  public FaceCompareResponse compare(String faceId1, String faceId2);

}
