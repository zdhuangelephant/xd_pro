package com.xiaodou.facerecognitiondemo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.facerecognitiondemo.cache.UpTokenCache;
import com.xiaodou.facerecognitiondemo.cache.UserCache;
import com.xiaodou.facerecognitiondemo.enums.UserFaceResType;
import com.xiaodou.facerecognitiondemo.model.User;
import com.xiaodou.facerecognitiondemo.prop.SevenCowProp;
import com.xiaodou.facerecognitiondemo.util.FaceUtil;
import com.xiaodou.facerecognitiondemo.util.UpTokenUtil;
import com.xiaodou.facerecognitiondemo.vo.request.UserRequest;
import com.xiaodou.facerecognitiondemo.vo.request.UserUptokenPojo;
import com.xiaodou.facerecognitiondemo.vo.response.CompareUserResponse;
import com.xiaodou.facerecognitiondemo.vo.response.CreateUserResponse;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.UpTokenResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

@Service("userService")
public class UserService {

  public CreateUserResponse addUser(String faceUrl) {
    if (StringUtils.isBlank(faceUrl)) return new CreateUserResponse(ResultType.SYSFAIL);
    CreateUserResponse res = new CreateUserResponse(ResultType.SUCCESS);
    User user = new User();
    String uid = UUID.randomUUID().toString();
    user.setUid(uid);
    String faceId = FaceUtil.addFace(faceUrl);
    if (StringUtils.isBlank(faceId)) return new CreateUserResponse(UserFaceResType.CreateUserFail);
    user.setFaceId(faceId);
    UserCache.addUser(user);
    res.setUid(uid);
    return res;
  }

  public ResultInfo getUptoken(UserUptokenPojo pojo) throws Exception {
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

  public CompareUserResponse compare(UserRequest pojo) {
    if (StringUtils.isBlank(pojo.getUid(), pojo.getFaceUrl()))
      return new CompareUserResponse(ResultType.SYSFAIL);
    CompareUserResponse res = new CompareUserResponse(ResultType.SUCCESS);
    User user = UserCache.getUser(pojo.getUid());
    String faceId = FaceUtil.addFace(pojo.getFaceUrl());
    if (StringUtils.isBlank(faceId))
      return new CompareUserResponse(UserFaceResType.CompareUserFail);
    Double similar = FaceUtil.compare(user.getFaceId(), faceId);
    if (similar != null) res.setSimilar(similar.toString());
    if (similar != null && similar > 75d)
      res.setIsSelf("1");
    else
      res.setIsSelf("2");
    return res;
  }

}
