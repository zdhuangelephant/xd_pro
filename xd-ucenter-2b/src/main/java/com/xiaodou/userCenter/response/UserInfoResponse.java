package com.xiaodou.userCenter.response;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.FriendShip;
import com.xiaodou.userCenter.enums.UserPraiseEnum;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo;
import com.xiaodou.userCenter.module.selfTaught.model.StUserInfo.StMedal;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.prop.InitMedalProp;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class UserInfoResponse extends BaseResultInfo {
  public UserInfoResponse(ResultType type) {
    super(type);
  }

  public UserInfoResponse(UcenterResType resType) {
    super(resType);
  }

  /**
   * 用户信息
   */
  private UserInfo userInfo = new UserInfo();

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public static class UserInfo extends StUserInfoResponse {
    private String isPraised = UserPraiseEnum.isNotPraised.getCode();// 0.没有点赞1.已经点赞 默认没有点赞
    private String isFriend = FriendShip.STRENGER.getCode().toString();// 是否是好友 默认为陌生人

    public UserInfo getUserInfo(UserModel model) {
      if (null == model) return new UserInfo();
      if (null != model.getId()) this.setUserId(model.getId().toString());
      //if (StringUtils.isNotBlank(model.getUserName())) this.setUserName(model.getUserName());
      if (StringUtils.isNotBlank(model.getNickName())) this.setNickName(model.getNickName());
      if (StringUtils.isNotBlank(model.getPortrait())) this.setPortrait(model.getPortrait());
      if (null != model.getGender()) this.setGender(model.getGender().toString());
      if (null != model.getCredit()) this.setCredit(model.getCredit().toString());
      // if (null != model.getCoin()) this.setCoin(model.getCoin().toString());
      if (StringUtils.isNotBlank(model.getModuleInfo())) this.getModuleInfo(model.getModuleInfo());
      return this;
    }

    public void getModuleInfo(String moduleInfo) {
      if (StringUtils.isBlank(moduleInfo)) return;
      StUserInfo stInfo = FastJsonUtil.fromJson(moduleInfo, StUserInfo.class);
      if (null != stInfo && StringUtils.isNotBlank(stInfo.getSign()))
        this.setSign(stInfo.getSign());
      // if (null != stInfo && StringUtils.isNotBlank(stInfo.getCoin()))
      // this.setCoin(stInfo.getCoin());
      if (null != stInfo && StringUtils.isNotBlank(stInfo.getMedal())) {
        StMedal stMedal = FastJsonUtil.fromJson(stInfo.getMedal(), StMedal.class);
        this.setMedalId(StringUtils.isNotBlank(stMedal.getMedalId())
            ? stMedal.getMedalId()
            : InitMedalProp.getParams("init.medalId"));
        this.setMedalName(StringUtils.isNotBlank(stMedal.getMedalName())
            ? stMedal.getMedalName()
            : InitMedalProp.getParams("init.medalName"));
        this.setMedalImg(StringUtils.isNotBlank(stMedal.getMedalImg())
            ? stMedal.getMedalImg()
            : InitMedalProp.getParams("init.medalImg"));
      }
      if (null != stInfo && StringUtils.isNotBlank(stInfo.getMajor()))
        this.setMajor(stInfo.getMajor());
      if (null != stInfo && StringUtils.isNotBlank(stInfo.getPicList()))
        this.setPicList(FastJsonUtil.fromJsons(stInfo.getPicList(),
            new TypeReference<List<String>>() {}));
    }

    public String getIsPraised() {
      return isPraised;
    }

    public void setIsPraised(String isPraised) {
      this.isPraised = isPraised;
    }

    public String getIsFriend() {
      return isFriend;
    }

    public void setIsFriend(String isFriend) {
      this.isFriend = isFriend;
    }
  }
}
