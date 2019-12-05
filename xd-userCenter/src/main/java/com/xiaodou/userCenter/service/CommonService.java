package com.xiaodou.userCenter.service;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.cache.UpTokenCache;
import com.xiaodou.userCenter.prop.SevenCowProp;
import com.xiaodou.userCenter.request.UpTokenPojo;
import com.xiaodou.userCenter.response.UpTokenResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.util.UpTokenUtil;

@Service("commonService")
public class CommonService {

  public UpTokenResponse getUpToken(UpTokenPojo pojo) throws Exception {
    UpTokenResponse upTokenResponse = new UpTokenResponse(ResultType.SUCCESS);
    String upToken = UpTokenCache.getUpToken(pojo);
    if (StringUtils.isBlank(upToken)) {
      String accessKey = SevenCowProp.getParams("sevencow.uptoken.init.accessKey");// 获取accessKey
      String secretKey = SevenCowProp.getParams("sevencow.uptoken.init.secretKey");// 获取accessKey
      if (StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)) {
        return new UpTokenResponse(UcenterResType.MISSKEY);
      }
      upToken = UpTokenUtil.getUpToken(accessKey, secretKey, pojo);
      UpTokenCache.setUpToken(pojo, upToken);
    }
    upTokenResponse.setUpToken(upToken);
    return upTokenResponse;
  }

  public UpTokenResponse relaseUpToken(UpTokenPojo pojo) throws Exception {
    UpTokenResponse upTokenResponse = new UpTokenResponse(ResultType.SUCCESS);
    String accessKey = SevenCowProp.getParams("sevencow.uptoken.init.accessKey");// 获取accessKey
    String secretKey = SevenCowProp.getParams("sevencow.uptoken.init.secretKey");// 获取accessKey
    if (StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)) {
      return new UpTokenResponse(UcenterResType.MISSKEY);
    }
    String upToken = UpTokenUtil.getUpToken(accessKey, secretKey, pojo);
    UpTokenCache.releaseUpToken(pojo, upToken);
    upTokenResponse.setUpToken(upToken);
    return upTokenResponse;
  }
}
