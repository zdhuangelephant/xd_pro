package com.xiaodou.facerecognition.impl.faceplus;

import org.apache.commons.httpclient.NameValuePair;

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
import com.xiaodou.facerecognition.impl.faceplus.FacePlusTransportEntity.Compare;

public class FacePlusFaceRecognitionApi implements IFaceRecognitionApi, FacePlusRetCode {

  @Override
  public FaceIdResponse addFace(String faceUrl) {
    FaceIdResponse response = new FaceIdResponse(RetCode.Success);
    response.setFaceId(faceUrl);
    return response;
  }

  @Override
  public FaceCompareResponse compare(String faceId1, String faceId2) {
    if (StringUtils.isOrBlank(faceId1, faceId2)) return new FaceCompareResponse(RetCode.SysErr);
    HttpUtil http =
        HttpUtil.getInstance(FacePlusProp.getHost(), FacePlusProp.getPort(),
            FacePlusProp.getProtocol(), FacePlusProp.getTimeout(), FacePlusProp.getRetrytime());
    String baseUrl = FacePlusProp.getCompareUrl();
    NameValuePair[] params = new NameValuePair[4];
    params[0] = new NameValuePair("api_key", FacePlusProp.getAppKey());
    params[1] = new NameValuePair("api_secret", FacePlusProp.getAppSecret());
    params[2] = new NameValuePair("image_url1", faceId1);
    params[3] = new NameValuePair("image_url2", faceId2);
    http.setMethod(HttpMethodUtil.getPostMethod(baseUrl, params));
    HttpResult result = http.getHttpResult();
    if (null == result || StringUtils.isJsonBlank(result.getContent()))
      return new FaceCompareResponse(RetCode.SysErr);
    InOutEntity inout = new InOutEntity();
    inout.setTargetUrl(baseUrl);
    inout.setResult(result);
    LoggerUtil.inOutInfo(inout);
    if (Success_Code == result.getStatusCode()) {
      Compare resultMap = FastJsonUtil.fromJson(result.getContent(), Compare.class);
      FaceCompareResponse response = new FaceCompareResponse(RetCode.Success);
      if (null != resultMap && null != resultMap.getConfidence())
        response.setSimilarity(resultMap.getConfidence().doubleValue());
      else
        response.setSimilarity(0d);
      return response;
    } else {
      return new FaceCompareResponse(RetCode.SysErr);
    }
  }
}
