package com.xiaodou.ucenter.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.cache.CheckCodeCache;
import com.xiaodou.ucenter.cache.UserInfoByTokenCache;
import com.xiaodou.ucenter.constant.UcenterConstant;
import com.xiaodou.ucenter.dao.AppBindingModelDao;
import com.xiaodou.ucenter.enums.CheckCodeTypeEnum;
import com.xiaodou.ucenter.model.AppBindingModel;
import com.xiaodou.ucenter.model.AppBindingModel.AppBindingStatus;
import com.xiaodou.ucenter.model.CheckCodeModel;
import com.xiaodou.ucenter.model.UserModel;
import com.xiaodou.ucenter.module.ModuleWrapper;
import com.xiaodou.ucenter.request.BaseRequest;
import com.xiaodou.ucenter.request.CheckCodeRequest;
import com.xiaodou.ucenter.request.FindPwdRequest;
import com.xiaodou.ucenter.request.FirstLoginTimeRequest;
import com.xiaodou.ucenter.request.FuzzySeekingUserRequest;
import com.xiaodou.ucenter.request.LoginRequest;
import com.xiaodou.ucenter.request.ModifyPwdRequest;
import com.xiaodou.ucenter.request.ModifyUserRequest;
import com.xiaodou.ucenter.request.QueryUserRequest;
import com.xiaodou.ucenter.request.RegistUserRequest;
import com.xiaodou.ucenter.request.UserListRequest;
import com.xiaodou.ucenter.request.ValidateCodeRequest;
import com.xiaodou.ucenter.response.BaseResponse;
import com.xiaodou.ucenter.response.CheckCodeResponse;
import com.xiaodou.ucenter.response.FindPasswordResponse;
import com.xiaodou.ucenter.response.FirstLoginTimeResponse;
import com.xiaodou.ucenter.response.LoginResponse;
import com.xiaodou.ucenter.response.ModifyPwdResponse;
import com.xiaodou.ucenter.response.ModifyUserResponse;
import com.xiaodou.ucenter.response.RegistUserResponse;
import com.xiaodou.ucenter.response.UserListResponse;
import com.xiaodou.ucenter.response.UserModelListResponse;
import com.xiaodou.ucenter.response.UserModelResponse;
import com.xiaodou.ucenter.response.resultype.UcenterResType;
import com.xiaodou.ucenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.ucenter.service.queue.QueueService;
import com.xiaodou.ucenter.util.RandomNumberUtil;
import com.xiaodou.ucenter.util.TimeUtil;
import com.xiaodou.ucenter.util.UcenterUtil;

/**
 * 
 * 用户中心service 用户是用户，module是module
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
@Service("ucenterService")
public class UcenterService extends CheckLoginService {

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;
  @Resource
  AppBindingModelDao appBindingModelDao;
  @Resource
  QueueService queueService;

  /**
   * 
   * 获取验证码
   * 
   * @param pojo
   * @return
   */
  public CheckCodeResponse getCheckCode(CheckCodeRequest pojo) throws Exception {
    try {
      CheckCodeResponse response = new CheckCodeResponse(ResultType.SUCCESS);
      if (!PhoneUtil.validatePhone(pojo.getPhoneNum())) {
        return new CheckCodeResponse(UcenterResType.PhoneNumError);
      }
      // 1.2、用户注册 检验手机号是否被注册过
      if (CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
          && checkPhoneRigisted(pojo.getPhoneNum())) {
        return new CheckCodeResponse(UcenterResType.HasRegisterd);
      }
      // 1.3 用户注册 检验昵称是否被注册过
      if (CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
          && StringUtils.isNotBlank(pojo.getNickName())
          && checkNickNameRigisted(pojo.getNickName(), null)) {
        return new CheckCodeResponse(UcenterResType.HasNickName);
      }
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        String code = RandomNumberUtil.randomString(4);
        // 短信验证码测试数据
        // 2、15条短信判重
        Map<String, String> map = CheckCodeCache.getSmsAmountCache(pojo);
        if (null != map && map.size() >= 15) {
          return new CheckCodeResponse(UcenterResType.FifteenLimit);
        }
        CheckCodeModel model =
            CheckCodeCache.getCheckCodeFromCache(pojo.getCheckCodeType(), pojo.getPhoneNum());
        // 3、1分钟内不能重发判重
        if (null != model
            && !TimeUtil.isBeforeSomeMinutes(new Date(), model.getCreateDate(), new Double(1))) {
          return new CheckCodeResponse(UcenterResType.OneMinuteNoRepeat);
        }
        // 4、调用短信发送接口
        ShortMessage sm = new ShortMessage();
        sm.setTelephone(pojo.getPhoneNum());
        sm.setVariables("checkcode", code);
        sm.setTemplateId("1");
        PushClient.push(sm);
        // 5、覆盖缓存中验证码,向15条cache List中add该条code
        CheckCodeCache.addCodeToRedis(pojo, code);
        CheckCodeCache.cacheSmsAmount(pojo.getPhoneNum(), code);
      }
      return response;
    } catch (Exception e) {
      LoggerUtil.error("service层：获取验证码异常", e);
      throw e;
    }
  }

  /**
   * 验证验证码有效性接口
   * 
   * @param pojo
   * @return
   */
  public BaseResponse validateCode(ValidateCodeRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    // 1、校验验证码类型和手机号
    if (!PhoneUtil.validatePhone(pojo.getPhoneNum()))
      return new BaseResponse(UcenterResType.PhoneNumError);
    if (!CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
        && !CheckCodeTypeEnum.FindBackPassword.getCode().equals(pojo.getCheckCodeType())) {
      return new CheckCodeResponse(UcenterResType.CheckCodeTypeError);
    }
    // 2、先去数据库查询
    if (CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())) {
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
      if (null != list && list.size() >= 1) {
        return new BaseResponse(UcenterResType.HasRegisterd);
      }
    }
    // 3、校验验证码
    if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
      if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4))
        return new BaseResponse(UcenterResType.checkCodeError);
      CheckCodeModel codeFromCache =
          CheckCodeCache.getCheckCodeFromCache(pojo.getCheckCodeType(), pojo.getPhoneNum());
      if (null == codeFromCache) {
        return new BaseResponse(UcenterResType.CheckCodeOutDate);
      }
      if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
        return new BaseResponse(UcenterResType.CheckCodeError);
      }
    }
    return response;
  }

  /**
   * 
   * 用户注册方法
   * 
   * @param pojo
   * @return
   */
  public RegistUserResponse registerAccount(RegistUserRequest pojo) throws Exception {
    RegistUserResponse response = new RegistUserResponse(ResultType.SUCCESS);
    try {

      // 1、校验手机号
      if (!PhoneUtil.validatePhone(pojo.getPhoneNum())) {
        return new RegistUserResponse(UcenterResType.PhoneNumError);
      }
      // 2、检验昵称是否已经被使用
      if (checkNickNameRigisted(pojo.getNickName(), null)) {
        return new RegistUserResponse(UcenterResType.HasNickName);
      }
      // 3、先去数据库查询xd_user
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
      if (null != list && list.size() >= 1) {
        return new RegistUserResponse(UcenterResType.HasRegisterd);
      }
      // 4、校验验证码
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4))
          return new RegistUserResponse(UcenterResType.checkCodeError);
        CheckCodeModel codeFromCache =
            CheckCodeCache.getCheckCodeFromCache(CheckCodeTypeEnum.UserRegister.getCode(),
                pojo.getPhoneNum());
        if (null == codeFromCache) {
          return new RegistUserResponse(UcenterResType.CheckCodeOutDate);
        }
        if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
          return new RegistUserResponse(UcenterResType.CheckCodeError);
        }
      }
      // 5、校验通过，注册，插入数据库记录
      model.setNickName(pojo.getNickName());
      // model.setId(Long.parseLong(IDGenerator.getSeqID(pojo.getModule())));
      pojo.setRegistUserInfo(model);
      model = ucenterServiceFacade.insertUser(model);// 插入用户信息
      // 6、删除该条code缓存
      CheckCodeCache.deleteCheckCode(CheckCodeTypeEnum.UserRegister.getCode(), pojo.getPhoneNum());
      response.setToken(model.getToken());
      return response;
    } catch (Exception e) {
      LoggerUtil.error("service层：用户注册异常", e);
      throw e;
    }
  }

  /**
   * 事务注册用户对象至数据库
   * 
   * @param pojo
   * @param model
   * @return
   */
  public UserModel operateRegistUserInfo(BaseRequest pojo, UserModel model) {
    model = ucenterServiceFacade.insertUser(model);// 插入用户信息
    // if (!UcenterConstant.PLATFORM_DEVICE.equals(model.getPlatform())) {
    // String phoneNum = null;
    // if (UcenterConstant.PLATFORM_LOCAL.equals(model.getPlatform())) {
    // phoneNum = model.getUserName();
    // }
    // queueService.registIMAccount(model);
    // ResponseBase walletResponse =
    // WalletService.createAccount(WalletUtil.formatProductLine(pojo.getModule()), model.getId()
    // .toString(), phoneNum);
    // if (!walletResponse.isRetOk() && !"40001".equals(walletResponse.getRetcode())) {
    // throw new RuntimeException(walletResponse.getRetdesc());
    // }
    // }
    return model;
  }

  /**
   * 
   * 找回密码接口
   * 
   * @param pojo
   * @return
   */
  public FindPasswordResponse findPassword(FindPwdRequest pojo) {
    FindPasswordResponse result = new FindPasswordResponse(ResultType.SUCCESS);
    try {
      if (!PhoneUtil.validatePhone(pojo.getPhoneNum()))
        return new FindPasswordResponse(UcenterResType.PhoneNumError);
      if (!PhoneUtil.validateNumber(pojo.getPwd(), 6))
        return new FindPasswordResponse(UcenterResType.PwdError);
      // 1、查询用户是否注册
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      model.setModule(pojo.getModule());
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(this.getQueryCond(null, null, model),
              CommUtil.getAllField(UserModel.class));
      if (null == list || list.size() <= 0) {
        return new FindPasswordResponse(UcenterResType.UnRegistered);
      }
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        // 3、校验验证码
        if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4))
          return new FindPasswordResponse(UcenterResType.checkCodeError);
        CheckCodeModel codeFromCache =
            CheckCodeCache.getCheckCodeFromCache(CheckCodeTypeEnum.FindBackPassword.getCode(),
                pojo.getPhoneNum());
        if (null == codeFromCache) {
          return new FindPasswordResponse(UcenterResType.CheckCodeOutDate);
        }
        if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
          return new FindPasswordResponse(UcenterResType.CheckCodeError);
        }
      }
      UserModel entity = new UserModel();
      String salt = null;
      if (null != list && list.size() == 1) salt = list.get(0).getSalt();
      entity.setPassword(UcenterUtil.getPasswd(pojo.getPwd() + salt));
      if (1 != ucenterServiceFacade.updateUser(this.getQueryCond(null, null, model), entity)) {
        return new FindPasswordResponse(UcenterResType.UpdatePasswordFail);
      }
      // 删除缓存信息
      CheckCodeCache.deleteCheckCode(CheckCodeTypeEnum.FindBackPassword.getCode(),
          pojo.getPhoneNum());
      return result;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 修改用户密码service
   * 
   * @param pojo
   * @return
   */
  public ModifyPwdResponse modifyPwd(ModifyPwdRequest pojo) {
    ModifyPwdResponse response = new ModifyPwdResponse(ResultType.SUCCESS);
    try {
      if (!PhoneUtil.validateNumber(pojo.getOldPwd(), 6)
          || !PhoneUtil.validateNumber(pojo.getNewPwd(), 6)
          || !PhoneUtil.validateNumber(pojo.getConfirmNewPwd(), 6))
        return new ModifyPwdResponse(UcenterResType.PwdError);
      // 检测token是否存在
      if (StringUtils.isBlank(pojo.getSessionToken())) {
        return new ModifyPwdResponse(UcenterResType.UnLoginUser);
      }
      UserModel model = new UserModel();
      model.setToken(pojo.getSessionToken());
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
      // .getCode());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> userList =
          ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
      if (null == model || userList.size() <= 0) {
        return new ModifyPwdResponse(UcenterResType.UnLoginUser);
      }
      model = userList.get(0);
      String oldPwd = UcenterUtil.getPasswd(pojo.getOldPwd() + model.getSalt());
      if (!oldPwd.equals(model.getPassword())) {
        return new ModifyPwdResponse(UcenterResType.OldPwdError);
      }
      if (!pojo.getConfirmNewPwd().trim().equals(pojo.getNewPwd().trim())) {
        return new ModifyPwdResponse(UcenterResType.TwicePwdError);
      }
      String newPwd = UcenterUtil.getPasswd(pojo.getConfirmNewPwd() + model.getSalt());
      model.setPassword(newPwd);
      // 更新时候需要获得token值
      model.setToken(UUID.randomUUID().toString());
      // tokenTime也要更新为当前时间
      model.setTokenTime(new Timestamp(System.currentTimeMillis()));
      Integer updateFlag = ucenterServiceFacade.updateUser(cond, model);
      if (null != updateFlag && updateFlag <= 0) {
        return new ModifyPwdResponse(UcenterResType.UpdatePasswordFail);
      }
      // 更新token值前需要删除缓存中用户信息
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken(), ModuleWrapper
          .getWrapper().getModule());
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 
   * 校验token并返回用户基本信息
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public UserModelResponse checkToken(BaseRequest pojo) throws Exception {
    UserModelResponse response = new UserModelResponse(ResultType.SUCCESS);
    CheckLoginResult<UserModel> result = checkLoginWithBaseUserModel(pojo);
    if (!result.isRetOk()) return new UserModelResponse(result.getResType());
    response.setUser(result.getModel());
    return response;
  }

  /**
   * 退出登录service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public ResultInfo loginOut(BaseRequest pojo) throws Exception {
    ResultInfo resultInfo = new ResultInfo(ResultType.SUCCESS);
    try {
      // 校验token
      CheckLoginResult<UserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) return resultInfo;
      // 1 删除缓存中的数据
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken(), ModuleWrapper
          .getWrapper().getModule());
      // 2 更新数据库中的token和tokenTime
      UserModel model = new UserModel();
      model.setToken(UUID.randomUUID().toString());
      model.setTokenTime(new Timestamp(System.currentTimeMillis()));
      Map<String, Object> queryCond = Maps.newHashMap();
      queryCond.put("token", pojo.getSessionToken());
      queryCond.put("moudle", ModuleWrapper.getWrapper().getModule());
      ucenterServiceFacade.updateUser(queryCond, model);
      // 3 更改登录状态
      AppBindingModel appBindingModel = new AppBindingModel();
      appBindingModel.setStatus(UcenterConstant.LOGIN_OUT);// 退出登录
      appBindingModel.setUserId(result.getModel().getId().toString());
      appBindingModel.setSystemType(pojo.getClientType());
      String deviceId = pojo.getDeviceId();
      Map<String, Object> qcond = new HashMap<String, Object>();
      qcond.put("deviceId", deviceId);
      appBindingModelDao.updateEntity(qcond, appBindingModel);
      return resultInfo;
    } catch (Exception e) {
      LoggerUtil.error("Service层：登录异常", e);
      throw e;
    }
  }

  /**
   * 检查用户在某设备上的登录状态
   * 
   * @param pojo
   * @return
   */
  @SuppressWarnings("unused")
  private boolean checkUserStatus(String deviceId, String userId) {
    // 去数据库查询
    Map<String, Object> input = Maps.newHashMap();
    input.put("deviceId", deviceId);
    input.put("userId", userId);
    input.put("module", ModuleWrapper.getWrapper().getModule());
    List<AppBindingModel> list =
        appBindingModelDao.queryList(input, CommUtil.getAllField(AppBindingModel.class));
    if (null != list && list.size() >= 1) {
      if (list.get(0).getStatus().equals(AppBindingStatus.LOGIN.getCode()))
        return true;
      else if (list.get(0).getStatus().equals(AppBindingStatus.OUTLOGIN.getCode())) return false;
    }
    return false;
  }

  /**
   * 检查手机号是否被注册过
   * 
   * @param pojo
   * @return
   */
  private boolean checkPhoneRigisted(String phoneNum) {
    // 去数据库查询
    UserModel model = new UserModel();
    model.setUserName(phoneNum);
    model.setModule(ModuleWrapper.getWrapper().getModule());
    Map<String, Object> cond = this.getQueryCond(null, null, model);
    List<UserModel> list =
        ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
    if (null != list && list.size() >= 1) {
      return true;
    }
    return false;
  }

  /**
   * 检查用户昵称是否被注册过
   * 
   * @param pojo
   * @return
   */
  private boolean checkNickNameRigisted(String nickName, String userId) {
    // 去数据库查询
    UserModel model = new UserModel();
    model.setNickName(nickName);
    model.setModule(ModuleWrapper.getWrapper().getModule());
    Map<String, Object> cond = this.getQueryCond(null, null, model);
    if (StringUtils.isNotBlank(userId)) {
      List<String> notIdList = Lists.newArrayList();
      notIdList.add(userId);
      cond.put("notIdList", notIdList);
    }
    List<UserModel> list =
        ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
    if (null != list && list.size() >= 1) {
      return true;
    }
    return false;
  }

  public LoginResponse login(LoginRequest pojo) throws Exception {
    try {
      LoginResponse response = new LoginResponse(ResultType.SUCCESS);
      // 1、先去数据库查询
      UserModel model = pojo.queryModel();
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      model = ucenterServiceFacade.queryUser(cond, CommUtil.getAllField(UserModel.class));
      if (null == model) {
        if (!pojo.canRegist()) return new LoginResponse(UcenterResType.NoFoundUser);
        // 2、注册，插入数据库记录
        model = new UserModel();
        pojo.setRegistUserInfo(model);
        model = operateRegistUserInfo(pojo, model);
        response.setToken(model.getToken());
      }
      if (!pojo.checkPassword(model)) {
        return new LoginResponse(UcenterResType.PasswordError);
      }
      String usedDeviceId = model.getDeviceId();
      // 3、登录流程 更新数据
      String oldToken = model.getToken();
      UserInfoByTokenCache
          .deleteUserInfoFromCache(oldToken, ModuleWrapper.getWrapper().getModule());
      if (!updateInfo(pojo, model)) {
        return new LoginResponse(UcenterResType.TokenUpdateFail);
      }
      UserInfoByTokenCache.addUserInfoToCache(model, model.getToken(), ModuleWrapper.getWrapper()
          .getModule());
      // 5、登录流程 推送顶人消息
      toPushLoginOut(pojo, model, oldToken, usedDeviceId);
      // 6、登录流程 更新绑定信息
      updateBinding(pojo, model.getId().toString());
      response.setToken(model.getToken());
      return response;
    } catch (Exception e) {
      LoggerUtil.error("service层：" + "用户第三方登录接口异常", e);
      throw e;
    }
  }

  public void updateBinding(LoginRequest pojo, String newUniqueId) {
    // 新设备
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("deviceId", pojo.getDeviceId());
    queryCond.put("module", pojo.getModule());
    List<AppBindingModel> appList =
        appBindingModelDao.queryList(queryCond, CommUtil.getAllField(AppBindingModel.class));
    AppBindingModel appBindingModel = new AppBindingModel();
    appBindingModel.setSystemType(pojo.getClientType());
    appBindingModel.setUserId(newUniqueId);
    appBindingModel.setStatus("0");// 登录状态
    appBindingModel.setModule(ModuleWrapper.getWrapper().getModule());
    if (null == appList || appList.size() <= 0) {// 第一次登录之时
      appBindingModel.setDeviceId(pojo.getDeviceId());
      appBindingModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      appBindingModel.setPushId(pojo.getRegistrationId());
      appBindingModelDao.addEntity(appBindingModel);
    } else if (null != appList && appList.size() > 0) {
      appBindingModel.setPushId(pojo.getRegistrationId());
      appBindingModelDao.updateEntity(queryCond, appBindingModel);// 有每次可能会变的参数
    }
  }

  public boolean updateInfo(BaseRequest pojo, UserModel entity) {
    String token = UUID.randomUUID().toString();
    entity.setToken(token);
    entity.setTokenTime(new Timestamp(System.currentTimeMillis()));
    entity.setLatestDeviceIp(pojo.getClientIp());
    entity.setDeviceId(pojo.getDeviceId());
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", entity.getId());
    if (1 == ucenterServiceFacade.updateUser(cond, entity)) {
      return true;
    } else {
      LoggerUtil.error("更新用戶信息失败", new RuntimeException());
      return false;
    }
  }

  public void toPushLoginOut(LoginRequest pojo, UserModel entity, String oldToken,
      String usedDeviceId) {
    // 获取旧的第三方唯一标识（旧设备登录的最新用户）
    String usedUniqueId = StringUtils.EMPTY;
    // 查询设备id的最新登录用户（为了找出在此设备上登录的最新用户，此步查询不是多余的查询）
    UserModel usermodel = new UserModel();
    usermodel.setDeviceId(usedDeviceId);
    // 确定发送方
    String status = StringUtils.EMPTY;
    String registrationId = StringUtils.EMPTY;
    String systemType = StringUtils.EMPTY;
    Map<String, Object> oldqueryCond = new HashMap<String, Object>();
    oldqueryCond.put("deviceId", usedDeviceId);
    oldqueryCond.put("module", pojo.getModule());
    List<AppBindingModel> oldappList =
        appBindingModelDao.queryList(oldqueryCond, CommUtil.getAllField(AppBindingModel.class));
    if (null != oldappList && oldappList.size() > 0) {
      registrationId = oldappList.get(0).getPushId();
      status = oldappList.get(0).getStatus();
      systemType = oldappList.get(0).getSystemType();
      usedUniqueId = oldappList.get(0).getUserId();
      AppBindingModel oldAppBindingModel = new AppBindingModel();
      oldAppBindingModel.setStatus(UcenterConstant.LOGIN_OUT);// 退出登录状态
      appBindingModelDao.updateEntity(oldqueryCond, oldAppBindingModel);
    }
    // 发送推送
    if (StringUtils.isNotBlank(usedDeviceId) && StringUtils.isNotBlank(usedUniqueId)
        && StringUtils.isNotBlank(registrationId) && !usedDeviceId.equals(pojo.getDeviceId())
        && usedUniqueId.equals(entity.getId().toString()) && ("0").equals(status)) {
      entity.setToken(oldToken);
      queueService.pushLoginOut(entity, registrationId, systemType);
    }
  }

  /**
   * 查询指定用户信息
   * 
   * @param pojo
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public UserModelResponse queryUser(QueryUserRequest pojo) throws Exception {
    UserModelResponse response = new UserModelResponse(ResultType.SUCCESS);
    CheckLoginResult<UserModel> result = checkLoginWithUserModel(pojo);
    if (!result.isRetOk()) return new UserModelResponse(result.getResType());
    UserModel user = new UserModel();
    user.setId(pojo.getUserId());
    user.setModule(ModuleWrapper.getWrapper().getModule());
    Map<String, Object> cond = this.getQueryCond(null, null, user);
    List<UserModel> list =
        ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
    if (null == list || list.size() == 0) {
      return new UserModelResponse(UcenterResType.UnUserExisted);
    }
    response.setUser(list.get(0));
    return response;
  }

  /**
   * 修改个人资料service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public ModifyUserResponse modifyUser(ModifyUserRequest pojo) throws Exception {
    try {
      ModifyUserResponse response = new ModifyUserResponse(ResultType.SUCCESS);
      CheckLoginResult<UserModel> result = checkLoginWithUserModel(pojo);
      if (!result.isRetOk()) return new ModifyUserResponse(result.getResType());
      UserModel user = result.getModel();
      // 检验昵称是否已经被使用
      if (StringUtils.isNotBlank(pojo.getNickName())
          && checkNickNameRigisted(pojo.getNickName(), user.getId().toString())) {
        return new ModifyUserResponse(UcenterResType.HasNickName);
      }
      pojo.setModifyInfo(user);// 设置用户信息
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("id", user.getId());
      Integer updateFlag = ucenterServiceFacade.updateUser(cond, user);
      if (null != updateFlag && updateFlag == 1) {
        // 删除之前的缓存
        UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken(), ModuleWrapper
            .getWrapper().getModule());
        // 添加最新的用户信息到缓存中
        UserInfoByTokenCache.addUserInfoToCache(user, pojo.getSessionToken(), ModuleWrapper
            .getWrapper().getModule());
      } else {
        return new ModifyUserResponse(UcenterResType.UserUpdateFail);
      }
      return response;
    } catch (Exception e) {
      throw e;
    }
  }


  public UserListResponse userList(UserListRequest pojo) throws Exception {
    UserListResponse response = new UserListResponse(ResultType.SUCCESS);
    CheckLoginResult<UserModel> result = checkLoginWithBaseUserModel(pojo);
    if (!result.isRetOk()) return new UserListResponse(result.getResType());
    Map<String, Object> queryCond = new HashMap<String, Object>();
    List<String> idList =
        FastJsonUtil.fromJsons(pojo.getUserIdList(), new TypeReference<List<String>>() {});
    queryCond.put("idList", idList);
    queryCond.put("module", pojo.getModule());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(queryCond, CommUtil.getAllField(UserModel.class));
    if (null == userList || userList.size() <= 0) {
      response.setRetdesc("没有查到用户数据！");
      return response;
    }
    for (UserModel model : userList) {
      response.getUserModelList().add(CommUtil.getShowField(model));
    }
    return response;
  }

  public FirstLoginTimeResponse getUserFirstLoginTime(FirstLoginTimeRequest pojo) {
    FirstLoginTimeResponse response = new FirstLoginTimeResponse(ResultType.SUCCESS);
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", pojo.getUserId());
    queryCond.put("module", pojo.getModule());
    List<AppBindingModel> appList =
        appBindingModelDao.queryList(queryCond, CommUtil.getAllField(AppBindingModel.class));
    if (null != appList && appList.size() > 0) {
      if (null != appList.get(0)) {
        String fristLoginTime =
            DateUtil.SDF_YMD.format(new Date(appList.get(0).getCreateTime().getTime()));
        response.setFristLoginTime(fristLoginTime);
      }
    } else {
      response = new FirstLoginTimeResponse(ResultType.NOTLOGIN);
    }
    return response;
  }

  /**
   * 根据用户昵称模糊查询用户
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public UserModelListResponse fuzzySeekingUser(FuzzySeekingUserRequest pojo) throws Exception {
    UserModelListResponse response = new UserModelListResponse(ResultType.SUCCESS);
    // 1、先去数据库查询
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("fuzzyNickName", pojo.getNickName());
    cond.put("module", pojo.getModule());
    List<UserModel> userModelList =
        ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
    response.setUserModelList(userModelList);
    return response;
  }
}
