package com.xiaodou.facerecognitiondemo.util;

import java.util.List;

import lombok.Data;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.HttpUtil.ProtocolEnum;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class FaceUtil {
  private static String APP_ID = "9b1a7964524141cb974e865621f3e436";
  private static String APP_KEY = "ed45f0282c3b4dd8bada535cdca121e1";

  public static String addFace(String faceUrl) {
    if (StringUtils.isBlank(faceUrl)) return null;
    HttpUtil http = HttpUtil.getInstance("api.eyekey.com", 80, ProtocolEnum.HTTP, 60000, 2);
    String baseUrl =
        String.format("/face/Check/checking?app_id=" + APP_ID + "&app_key=" + APP_KEY + "&url=%s",
            faceUrl);
    http.setMethod(HttpMethodUtil.getGetMethod(baseUrl));
    HttpResult result = http.getHttpResult();
    if (null == result || StringUtils.isJsonBlank(result.getContent())) return null;
    InOutEntity inout = new InOutEntity();
    inout.setTargetUrl(baseUrl);
    inout.setResult(result);
    LoggerUtil.inOutInfo(inout);
    Check resultMap = FastJsonUtil.fromJson(result.getContent(), Check.class);
    if ("0000".equals(resultMap.getRes_code())) return resultMap.getFace().get(0).getFace_id();
    return null;
  }

  public static Double compare(String faceId1, String faceId2) {
    if (StringUtils.isBlank(faceId1, faceId2)) return 0d;
    HttpUtil http = HttpUtil.getInstance("api.eyekey.com", 80, ProtocolEnum.HTTP, 60000, 2);
    String baseUrl =
        String.format("/face/Match/match_compare?app_id=" + APP_ID + "&app_key=" + APP_KEY
            + "&face_id1=%s&face_id2=%s", faceId1, faceId2);
    http.setMethod(HttpMethodUtil.getGetMethod(baseUrl));
    HttpResult result = http.getHttpResult();
    if (null == result || StringUtils.isJsonBlank(result.getContent())) return 0d;
    InOutEntity inout = new InOutEntity();
    inout.setTargetUrl(baseUrl);
    inout.setResult(result);
    LoggerUtil.inOutInfo(inout);
    Check resultMap = FastJsonUtil.fromJson(result.getContent(), Check.class);
    if ("0000".equals(resultMap.getRes_code())) return resultMap.getSimilarity();
    return 0d;
  }

  @Data
  public static class Check {
    private String message;
    // 1011 图片获取异常
    // 0000 正常
    private String res_code;
    private List<Face> face;
    private Double similarity;
  }

  @Data
  public static class Face {
    private String face_id;
  }

}
