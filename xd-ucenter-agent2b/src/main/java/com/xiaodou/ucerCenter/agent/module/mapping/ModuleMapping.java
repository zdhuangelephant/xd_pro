package com.xiaodou.ucerCenter.agent.module.mapping;

import java.util.HashSet;
import java.util.Set;

import com.xiaodou.ucerCenter.agent.model.BaseUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StFriend;
import com.xiaodou.ucerCenter.agent.module.selfTaught.model.StUserInfo;
import com.xiaodou.ucerCenter.agent.module.selfTaught.request.StConfigRequest;
import com.xiaodou.ucerCenter.agent.module.selfTaught.request.StOfficialInfoRegist;
import com.xiaodou.ucerCenter.agent.module.selfTaught.request.StUserInfoModify;
import com.xiaodou.ucerCenter.agent.module.selfTaught.request.StUserInfoNewLogin;
import com.xiaodou.ucerCenter.agent.module.selfTaught.request.StUserInfoRegist;
import com.xiaodou.ucerCenter.agent.module.selfTaught.response.StConfigResponse;
import com.xiaodou.ucerCenter.agent.module.selfTaught.response.StConfigVersionResponse;
import com.xiaodou.ucerCenter.agent.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.ucerCenter.agent.request.ConfigRequest;
import com.xiaodou.ucerCenter.agent.request.ModifyMyInfoRequest;
import com.xiaodou.ucerCenter.agent.request.NewLoginRequest;
import com.xiaodou.ucerCenter.agent.request.OfficialRegistRequest;
import com.xiaodou.ucerCenter.agent.request.RegistAccountRequest;
import com.xiaodou.ucerCenter.agent.response.BaseUserModel;
import com.xiaodou.ucerCenter.agent.response.ConfigResponse;
import com.xiaodou.ucerCenter.agent.response.FriendListResponse.Friend;
import com.xiaodou.ucerCenter.agent.service.IConfigService;

public enum ModuleMapping {
  // Maping_1("教师资格考典", JzUserInfo.class, JzUserInfoRegist.class,null, JzUserInfoModify.class,
  // JzUserInfoResponse.class, JzConfigVersionResponse.class, JzConfigRequest.class,
  // JzConfigResponse.class, new JzConfigService(), JzUserInfoNewLogin.class, null),
  // Maping_12("小逗题库", TkUserInfo.class, TkUserInfoRegist.class,null, TkUserInfoModify.class,
  // TkUserInfoResponse.class, TkConfigVersionResponse.class, TkConfigRequest.class,
  // TkConfigResponse.class, new TkConfigService(), TkUserInfoLogin.class, null),
  Maping_2("小逗自考", StUserInfo.class, StUserInfoRegist.class, StOfficialInfoRegist.class,
      StUserInfoModify.class, StUserInfoResponse.class, StConfigVersionResponse.class,
      StConfigRequest.class, StConfigResponse.class, null,
      StUserInfoNewLogin.class, StFriend.class);
  private ModuleMapping(String name, Class<? extends BaseUserInfo> model,
      Class<? extends RegistAccountRequest> regist,
      Class<? extends OfficialRegistRequest> officialregist,
      Class<? extends ModifyMyInfoRequest> modify, Class<? extends BaseUserModel> response,
      Class<? extends ConfigResponse<?>> cvOutClass, Class<? extends ConfigRequest> confInClass,
      Class<? extends ConfigResponse<?>> confOutClass, IConfigService<?, ?, ?> configService,
      Class<? extends NewLoginRequest> newLogin, Class<? extends Friend> friend) {
    this.name = name;
    String signName = this.toString();
    this.code = signName.substring(signName.length() - 1);
    this.model = model;
    this.regist = regist;
    this.officialregist = officialregist;
    this.modify = modify;
    this.response = response;
    this.cvOutClass = cvOutClass;
    this.confInClass = confInClass;
    this.confOutClass = confOutClass;
    this.configService = configService;
    this.newLogin = newLogin;
    this.friend = friend;
  }

  private String code;
  private String name;
  private Class<? extends BaseUserInfo> model;
  private Class<? extends RegistAccountRequest> regist;
  private Class<? extends OfficialRegistRequest> officialregist;
  private Class<? extends ModifyMyInfoRequest> modify;
  private Class<? extends BaseUserModel> response;

  private Class<? extends ConfigResponse<?>> cvOutClass;
  private Class<? extends ConfigRequest> confInClass;
  private Class<? extends ConfigResponse<?>> confOutClass;
  private IConfigService configService;

  private Class<? extends NewLoginRequest> newLogin;

  private Class<? extends Friend> friend;

  public Class<? extends Friend> getFriend() {
    return friend;
  }

  public void setFriend(Class<? extends Friend> friend) {
    this.friend = friend;
  }

  public Class<? extends NewLoginRequest> getNewLogin() {
    return newLogin;
  }

  public void setNewLogin(Class<? extends NewLoginRequest> newLogin) {
    this.newLogin = newLogin;
  }

  public Class<? extends OfficialRegistRequest> getOfficialregist() {
    return officialregist;
  }

  public void setOfficialregist(Class<? extends OfficialRegistRequest> officialregist) {
    this.officialregist = officialregist;
  }

  public IConfigService getConfigService() {
    return configService;
  }

  public void setConfigService(IConfigService configService) {
    this.configService = configService;
  }

  public Class<? extends ConfigResponse<?>> getCvOutClass() {
    return cvOutClass;
  }

  public void setCvOutClass(Class<? extends ConfigResponse<?>> cvOutClass) {
    this.cvOutClass = cvOutClass;
  }

  public Class<? extends ConfigRequest> getConfInClass() {
    return confInClass;
  }

  public void setConfInClass(Class<? extends ConfigRequest> confInClass) {
    this.confInClass = confInClass;
  }

  public Class<? extends ConfigResponse<?>> getConfOutClass() {
    return confOutClass;
  }

  public void setConfOutClass(Class<? extends ConfigResponse<?>> confOutClass) {
    this.confOutClass = confOutClass;
  }

  public Class<? extends ModifyMyInfoRequest> getModify() {
    return modify;
  }

  public void setModify(Class<? extends ModifyMyInfoRequest> modify) {
    this.modify = modify;
  }

  public Class<? extends RegistAccountRequest> getRegist() {
    return regist;
  }

  public void setRegist(Class<? extends RegistAccountRequest> regist) {
    this.regist = regist;
  }

  public Class<? extends BaseUserModel> getResponse() {
    return response;
  }

  public void setResponse(Class<? extends BaseUserModel> response) {
    this.response = response;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<? extends BaseUserInfo> getModel() {
    return model;
  }

  public BaseUserInfo getModelEntity() throws InstantiationException, IllegalAccessException {
    return model.newInstance();
  }

  public void setModel(Class<? extends BaseUserInfo> model) {
    this.model = model;
  }

  private static Set<String> valueSet = new HashSet<String>() {
    /** serialVersionUID */
    private static final long serialVersionUID = 7481802876639940395L;

    public HashSet<String> initThis() {
      for (ModuleMapping module : ModuleMapping.values()) {
        this.add(module.toString());
      }
      return this;
    }
  }.initThis();

  private static final String _PREFIX = "Maping_%s";

  public static ModuleMapping getByCode(String code) {
    String format = String.format(_PREFIX, code);
    if (valueSet.contains(format)) {
      return Enum.valueOf(ModuleMapping.class, format);
    }
    return null;
  }

}
