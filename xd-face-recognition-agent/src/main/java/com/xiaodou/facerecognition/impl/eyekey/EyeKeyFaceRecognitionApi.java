package com.xiaodou.facerecognition.impl.eyekey;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.facerecognition.FaceCompareResponse;
import com.xiaodou.facerecognition.FaceIdResponse;
import com.xiaodou.facerecognition.IFaceRecognitionApi;
import com.xiaodou.facerecognition.RetCode;
import com.xiaodou.facerecognition.impl.eyekey.EyeKeyTransportEntity.Check;

/**
 * @name EyeKeyFaceRecognitionApi
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description EyeKey面部识别API
 * @version 1.0
 */
public class EyeKeyFaceRecognitionApi implements IFaceRecognitionApi, EyeKeyRetCode {

  @Override
  public FaceIdResponse addFace(String faceUrl) {
    if (StringUtils.isBlank(faceUrl)) return new FaceIdResponse(RetCode.SysErr);
    HttpUtil http =
        HttpUtil.getInstance(EyeKeyProp.getHost(), EyeKeyProp.getPort(), EyeKeyProp.getProtocol(),
            EyeKeyProp.getTimeout(), EyeKeyProp.getRetrytime());
    String baseUrl =
        String.format(EyeKeyProp.getAddFaceUrl(), EyeKeyProp.getAppId(), EyeKeyProp.getAppKey(),
            faceUrl);
    http.setMethod(HttpMethodUtil.getGetMethod(baseUrl));
    HttpResult result = http.getHttpResult();
    if (null == result || StringUtils.isJsonBlank(result.getContent()))
      return new FaceIdResponse(RetCode.SysErr);
    InOutEntity inout = new InOutEntity();
    inout.setTargetUrl(baseUrl);
    inout.setResult(result);
    LoggerUtil.inOutInfo(inout);
    Check resultMap = FastJsonUtil.fromJson(result.getContent(), Check.class);
    if (Success_Code.equals(resultMap.getRes_code()) && resultMap.getFace() != null
        && resultMap.getFace().size() > 0) {
      FaceIdResponse response = new FaceIdResponse(RetCode.Success);
      response.setFaceId(resultMap.getFace().get(0).getFace_id());
      return response;
    } else {
      FaceIdResponse response = new FaceIdResponse(RetCode.SysErr);
      return response;
    }
  }

  @Override
  public FaceCompareResponse compare(String faceId1, String faceId2) {
    if (StringUtils.isOrBlank(faceId1, faceId2)) return new FaceCompareResponse(RetCode.SysErr);
    HttpUtil http =
        HttpUtil.getInstance(EyeKeyProp.getHost(), EyeKeyProp.getPort(), EyeKeyProp.getProtocol(),
            EyeKeyProp.getTimeout(), EyeKeyProp.getRetrytime());
    String baseUrl =
        String.format(EyeKeyProp.getCompareUrl(), EyeKeyProp.getAppId(), EyeKeyProp.getAppKey(),
            faceId1, faceId2);
    http.setMethod(HttpMethodUtil.getGetMethod(baseUrl));
    HttpResult result = http.getHttpResult();
    if (null == result || StringUtils.isJsonBlank(result.getContent()))
      return new FaceCompareResponse(RetCode.SysErr);
    InOutEntity inout = new InOutEntity();
    inout.setTargetUrl(baseUrl);
    inout.setResult(result);
    LoggerUtil.inOutInfo(inout);
    Check resultMap = FastJsonUtil.fromJson(result.getContent(), Check.class);
    if (Success_Code.equals(resultMap.getRes_code())) {
      FaceCompareResponse response = new FaceCompareResponse(RetCode.Success);
      response.setSimilarity(resultMap.getSimilarity());
      return response;
    } else {
      return new FaceCompareResponse(RetCode.SysErr);
    }
  }
}
