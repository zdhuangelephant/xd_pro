package com.xiaodou.userCenter.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.AppBindingModelDao;
import com.xiaodou.model.AppBindingModel;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.cache.CheckCodeCache;
import com.xiaodou.userCenter.cache.UserInfoByTokenCache;
import com.xiaodou.userCenter.common.enums.CheckCodeTypeEnum;
import com.xiaodou.userCenter.constant.Constant;
import com.xiaodou.userCenter.model.BaseUserInfo;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CheckCodeModel;
import com.xiaodou.userCenter.model.FeedBackModel;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserModuleInfoModel;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.model.property.HelpModelProperty;
import com.xiaodou.userCenter.model.property.UserModelProperty;
import com.xiaodou.userCenter.model.property.UserModelVoProperty;
import com.xiaodou.userCenter.model.property.UserModuleInfoProperty;
import com.xiaodou.userCenter.model.property.UserRelateNoticeProperty;
import com.xiaodou.userCenter.model.vo.UserModelVo;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.CheckCodeRequest;
import com.xiaodou.userCenter.request.FeedBackRequest;
import com.xiaodou.userCenter.request.FindPwdRequest;
import com.xiaodou.userCenter.request.LoginRequest;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.request.ModifyPwdRequest;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.RegistAccountRequest;
import com.xiaodou.userCenter.request.UserNoticeRequest;
import com.xiaodou.userCenter.request.ValidateCodeRequest;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.CheckCodeResponse;
import com.xiaodou.userCenter.response.FeedBackResponse;
import com.xiaodou.userCenter.response.FindPasswordResponse;
import com.xiaodou.userCenter.response.HelpDocResponse;
import com.xiaodou.userCenter.response.LoginResponse;
import com.xiaodou.userCenter.response.ModifyMyInfoResponse;
import com.xiaodou.userCenter.response.ModifyPwdResponse;
import com.xiaodou.userCenter.response.NewLoginResponse;
import com.xiaodou.userCenter.response.NoticeResponse;
import com.xiaodou.userCenter.response.RegistAccountResponse;
import com.xiaodou.userCenter.response.UserInfoByTokenResponse;
import com.xiaodou.userCenter.response.UserRelateNoticeResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.service.queue.QueueService;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;
import com.xiaodou.userCenter.util.RandomNumberUtil;
import com.xiaodou.userCenter.util.TimeUtil;
import com.xiaodou.userCenter.util.UcenterUtil;

/**
 * 
 * 用户中心service 用户是用户，module是module
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
@Service("ucenterService")
public class UcenterService extends BaseDaoService {

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
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        String code = RandomNumberUtil.randomString(4);
        // 短信验证码测试数据
        // 1、校验验证码类型和手机号
        if (!CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
            && !CheckCodeTypeEnum.FindBackPassword.getCode().equals(pojo.getCheckCodeType())) {
          return new CheckCodeResponse(UcenterResType.CheckCodeTypeError);
        }
        if (!PhoneUtil.validatePhone(pojo.getPhoneNum())) {
          return new CheckCodeResponse(UcenterResType.PhoneNumError);
        }
        // 2、检验手机号是否被注册过
        if (CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
            && checkPhoneRigisted(pojo.getPhoneNum())) {
          return new CheckCodeResponse(UcenterResType.HasRegisterd);
        }
        // 2、5条短信判重
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

        // 5、覆盖缓存中验证码,向5条cache List中add该条code
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
  public BaseResultInfo validateCode(ValidateCodeRequest pojo) {
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    // 1、校验验证码类型和手机号
    if (pojo.getPhoneNum().trim().length() != 11) {
      return new RegistAccountResponse(UcenterResType.PhoneNumError);
    }
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
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getBriefInfo());
      if (null != list && list.size() >= 1) {
        return new RegistAccountResponse(UcenterResType.HasRegisterd);
      }
    }
    // 3、校验验证码
    if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
      CheckCodeModel codeFromCache =
          CheckCodeCache.getCheckCodeFromCache(pojo.getCheckCodeType(), pojo.getPhoneNum());
      if (null == codeFromCache) {
        return new BaseResultInfo(UcenterResType.CheckCodeOutDate);
      }
      if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
        return new BaseResultInfo(UcenterResType.CheckCodeError);
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
  public <T extends BaseUserInfo> RegistAccountResponse registerAccount(RegistAccountRequest pojo)
      throws Exception {
    RegistAccountResponse result = new RegistAccountResponse(ResultType.SUCCESS);
    try {
      // 1、校验手机号
      if (pojo.getPhoneNum().trim().length() != 11) {
        return new RegistAccountResponse(UcenterResType.PhoneNumError);
      }
      // 2、校验pwd与confirmPassword是否相同
      if (!pojo.getPassWord().equals(pojo.getConfirmPassWord())) {
        return new RegistAccountResponse(UcenterResType.EnteredPasswordsDiffer);
      }
      // 3、先去数据库查询xd_user
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getBriefInfo());
      if (null != list && list.size() >= 1) {
        return new RegistAccountResponse(UcenterResType.HasRegisterd);
      }
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        // 4、校验验证码
        CheckCodeModel codeFromCache =
            CheckCodeCache.getCheckCodeFromCache(CheckCodeTypeEnum.UserRegister.getCode(),
                pojo.getPhoneNum());
        if (null == codeFromCache) {
          return new RegistAccountResponse(UcenterResType.CheckCodeOutDate);
        }
        if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
          return new RegistAccountResponse(UcenterResType.CheckCodeError);
        }
      }
      // 5、校验通过，注册，插入数据库记录
      UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
      userModuleInfoModel.setUserName(pojo.getPhoneNum());
      Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
      List<UserModuleInfoModel> listModuleInfo =
          ucenterServiceFacade.queryUserModuleInfoList(conModule,
              UserModuleInfoProperty.getAllInfo());
      if (null == listModuleInfo || listModuleInfo.size() <= 0) {
        pojo.setRegistUserModuleInfo(userModuleInfoModel);
        userModuleInfoModel = ucenterServiceFacade.insertUserModuleInfo(userModuleInfoModel);// 插入与模块相关信息
        queueService.registIMAccount(userModuleInfoModel);
      }
      pojo.setRegistUserInfo(model);
      model = ucenterServiceFacade.insertUser(model);// 插入用户信息
      // 6、删除该条code缓存
      CheckCodeCache.deleteCheckCode(CheckCodeTypeEnum.UserRegister.getCode(), pojo.getPhoneNum());
      result.setToken(model.getToken());
      return result;
    } catch (Exception e) {
      LoggerUtil.error("service层：用户注册异常", e);
      throw e;
    }
  }

  /**
   * 
   * 登录方法
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public LoginResponse login(LoginRequest pojo) throws Exception {
    LoginResponse response = new LoginResponse(ResultType.SUCCESS);
    try {
      String newModule = ModuleMappingWrapper.getWrapper().getModule().getCode();
      // 验证用户
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
      // .getCode());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
      // 1、校验用户名、密码
      UserModel entity = null;
      UserModuleInfoModel newUserModuleInfoModel = new UserModuleInfoModel();
      if (null == list || list.size() <= 0) {
        return new LoginResponse(UcenterResType.NoFoundUser);
      } else if (null != list && list.size() > 0) {
        entity = list.get(0);
        String passwordFromApp = UcenterUtil.getPasswd(pojo.getPwd() + entity.getSalt());
        if (!entity.getPassword().equals(passwordFromApp)) {
          return new LoginResponse(UcenterResType.PasswordError);
        }
        if (StringUtils.isNotBlank(pojo.getPhoneNum())) {
          newUserModuleInfoModel.setUserName(pojo.getPhoneNum());
          Map<String, Object> conModule = this.getQueryCond(null, null, newUserModuleInfoModel);
          List<UserModuleInfoModel> listModuleInfo =
              ucenterServiceFacade.queryUserModuleInfoList(conModule,
                  UserModuleInfoProperty.getAllInfo());
          if (null != listModuleInfo && listModuleInfo.size() > 0) {
            newUserModuleInfoModel.setModule(listModuleInfo.get(0).getModule());
            newUserModuleInfoModel.setModuleInfo(listModuleInfo.get(0).getModuleInfo());
          } else {// 防止xd_user_module_info表的信息被删除
            newUserModuleInfoModel.setModule(newModule);
            ucenterServiceFacade.insertUserModuleInfo(newUserModuleInfoModel);
            queueService.registIMAccount(newUserModuleInfoModel);
          }
        }

      }

      // 获取旧的设备号
      String usedDeviceId = entity.getUsedDeviceId();
      // 获取旧的别名（旧设备登录的最新用户）
      String usedUserName = StringUtils.EMPTY;
      // 获取旧设备登录的最新用户的module
      String module = StringUtils.EMPTY;
      // 查询设备id的最新登录用户（为了找出在此设备上登录的最新用户，此步查询不是多余的查询）
      UserModel usermodel = new UserModel();
      usermodel.setUsedDeviceId(usedDeviceId);
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
      Map<String, Object> con = this.getQueryCond(null, null, usermodel);
      List<UserModel> listUserModel =
          ucenterServiceFacade.queryUserList(con, UserModelProperty.getAllInfo());
      if (null != listUserModel && listUserModel.size() > 0) {
        UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
        usedUserName = listUserModel.get(0).getUserName();
        if (StringUtils.isNotBlank(usedUserName)) {
          userModuleInfoModel.setUserName(usedUserName);
          Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
          List<UserModuleInfoModel> listModuleInfo =
              ucenterServiceFacade.queryUserModuleInfoList(conModule,
                  UserModuleInfoProperty.getAllInfo());
          if (null != listModuleInfo && listModuleInfo.size() > 0) {
            module = listModuleInfo.get(0).getModule();
          }
        }
      }
      String status = "0";
      String registrationId = StringUtils.EMPTY;
      String systemType = StringUtils.EMPTY;
      Map<String, Object> oldqueryCond = new HashMap<String, Object>();
      Map<String, Object> oldoutput = new HashMap<String, Object>();
      oldqueryCond.put("deviceId", usedDeviceId);//
      oldoutput.put("id", "1");
      oldoutput.put("pushId", "1");
      oldoutput.put("systemType", "1");
      oldoutput.put("userId", "1");// 别名（用户手机号）
      oldoutput.put("status", "1");
      List<AppBindingModel> oldappList = appBindingModelDao.queryList(oldqueryCond, oldoutput);
      if (null != oldappList && oldappList.size() > 0) {
        registrationId = oldappList.get(0).getPushId();
        status = oldappList.get(0).getStatus();
        systemType = oldappList.get(0).getSystemType();
      }
      // 当同一用户在不同的设备登录同一module的app，并且在之前设备上此用户没有退出登录时
      if (!usedDeviceId.equals(pojo.getDeviceId()) && !StringUtils.isBlank(usedUserName)
          && usedUserName.equals(pojo.getPhoneNum()) && ("0").equals(status)
          && !StringUtils.isBlank(registrationId) && StringUtils.isNotBlank(module)
          && ModuleMappingWrapper.getWrapper().getModule().getCode().equals(module)) {
        // 向之前登录的设备发送登录失效消息
        queueService.pushLoginOut(entity, registrationId, systemType);
      }

      // 存储数据
      Map<String, Object> queryCond = new HashMap<String, Object>();
      Map<String, Object> output = new HashMap<String, Object>();
      queryCond.put("deviceId", pojo.getDeviceId());// usedDeviceId
      output.put("id", "1");
      output.put("pushId", "1");
      output.put("systemType", "1");
      output.put("userId", "1");// 别名（用户手机号）
      output.put("status", "1");
      List<AppBindingModel> appList = appBindingModelDao.queryList(queryCond, output);
      AppBindingModel appBindingModel = new AppBindingModel();
      appBindingModel.setSystemType(pojo.getClientType());
      appBindingModel.setUserId(pojo.getPhoneNum());
      appBindingModel.setStatus("0");// 登录状态
      if (null == appList || appList.size() <= 0) {// 第一次登录之时
        appBindingModel.setDeviceId(pojo.getDeviceId());
        appBindingModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        appBindingModel.setPushId(pojo.getRegistrationId());
        appBindingModelDao.addEntity(appBindingModel);
      } else if (null != appList && appList.size() > 0) {
        appBindingModel.setPushId(pojo.getRegistrationId());
        appBindingModelDao.updateEntity(queryCond, appBindingModel);// 有每次可能会变的参数
      }

      entity.setUsedDeviceId(pojo.getDeviceId());
      // 更新token值和token生成时间
      String token = UUID.randomUUID().toString();
      // 获取旧TOKEN,更新前从redis删除
      String oldToken = entity.getToken();
      /*
       * 需要改进 是将module和用户信息一起做缓存还是分开做？
       */
      UserInfoByTokenCache.deleteUserInfoFromCache(oldToken);
      entity.setToken(token);
      entity.setTokenTime(new Timestamp(System.currentTimeMillis()));
      entity.setLatestDeviceIp(pojo.getClientIp());
      int flag = 1;
      if (StringUtils.isNotBlank(module))
        flag =
            ucenterServiceFacade.updateUserModuleInfo(this.getQueryCond(null, null, model),
                new UserModuleInfoModel(newModule));
      if (1 == ucenterServiceFacade.updateUser(this.getQueryCond(null, null, model), entity)
          && 1 == flag) {
        // 查出用户信息存至redis
        response.setToken(token);
        entity.setPassword(null);
        UserInfoByTokenCache.addUserInfoToCache(
            (BaseUserModel) ModuleMappingWrapper.getWrapper().getModule().getResponse()
                .newInstance().initFromUserModelVo(entity.getUserModelVo(newUserModuleInfoModel)),
            token);
      } else {
        return new LoginResponse(UcenterResType.TokenUpdateFail);
      }

      return response;
    } catch (Exception e) {
      LoggerUtil.error("service层：" + "用户登录异常", e);
      throw e;
    }
  }

  /**
   * 
   * 校验设备号
   * 
   * @param entity
   * @param pojo
   * @return
   */
  // private UserModel checkDeviceId(UserModel entity, LoginRequest pojo) {
  // String usedDeviceId = entity.getUsedDeviceId();
  // String[] deviceIdArrays = usedDeviceId.split(",");
  //
  // if (deviceIdArrays.length == 1) {
  // if (!pojo.getDeviceId().equals(deviceIdArrays[0])) {
  // entity.setUsedDeviceId(entity.getUsedDeviceId() + "," +
  // pojo.getDeviceId());
  // }
  // } else {
  // boolean flag = false;
  // for (String deviceId : deviceIdArrays) {
  // if (deviceId.equals(pojo.getDeviceId())) {
  // flag = true;
  // break;
  // }
  // }
  // if (flag)
  // return entity;
  // else {
  // if (deviceIdArrays.length == 2)
  // entity.setUsedDeviceId(pojo.getDeviceId());
  // else
  // return null;
  // }
  // }
  // return entity;
  // }

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
      // 1、查询用户是否注册
      UserModel model = new UserModel();
      model.setUserName(pojo.getPhoneNum());
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
      // .getCode());
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(this.getQueryCond(null, null, model),
              UserModelProperty.getAllInfo());
      if (null == list || list.size() <= 0) {
        return new FindPasswordResponse(UcenterResType.UnRegistered);
      }
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        // 3、校验验证码
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
   * 帮助serivce
   * 
   * @return
   */
  public HelpDocResponse selectHelpDoc(BaseRequest pojo, HttpServletRequest request) {
    try {
      HelpDocResponse response = new HelpDocResponse(ResultType.SUCCESS);
      List<HelpDocModel> helpList =
          ucenterServiceFacade.queryHelpDocList(null, HelpModelProperty.getAllInfo());
      if (null == helpList || helpList.size() == 0) {
        return new HelpDocResponse(UcenterResType.NoFoundHelp);
      }
      response.setHelpList(helpList);
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 通知serivce
   * 
   * @return
   * @throws Exception
   */
  public UserRelateNoticeResponse selectNotice(BaseRequest pojo) throws Exception {
    try {
      UserRelateNoticeResponse response = new UserRelateNoticeResponse(ResultType.SUCCESS);
      // 1.用关联表和公告表做关联，查询数据
      BaseUserModel model = this.decideRedisExsited(pojo);
      if (null == model || StringUtils.isBlank(model.getId()))
        return new UserRelateNoticeResponse(UcenterResType.UnLoginUser);
      String userId = model.getId();
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("userId", userId);
      List<UserRelateNoticeModel> uNoticeList =
          ucenterServiceFacade.queryRelateNoticeList(queryCond,
              UserRelateNoticeProperty.getAllInfo());
      if (null == uNoticeList || uNoticeList.size() <= 0) {
        return new UserRelateNoticeResponse(UcenterResType.NoFoundNotice);
      }
      for (UserRelateNoticeModel uNoticeModel : uNoticeList) {
        // 2.有userId为空的则表示该用户没有该条公告，为中间表添加数据
        String createTime = uNoticeModel.getCreateTime();
        if (null == uNoticeModel.getUserId()) {
          uNoticeModel.setUserId(Long.valueOf(userId));
          uNoticeModel.setStatus(Short.valueOf(Constant.NOTICEINFO_UNREAD));
          ucenterServiceFacade.insertUserRelateNotice(uNoticeModel);
        }
        /**
         * 为了适应就版本，把这两个值改为relateId的值 uNoticeModel.setNoticeId(Integer.valueOf
         * (relateId.toString()));
         */
        uNoticeModel.setId(Integer.valueOf(uNoticeModel.getNoticeId().toString()));
        uNoticeModel.setCreateTime(createTime);
      }
      // 3.将数据构建为list传给前端
      response.setNoticeList(uNoticeList);
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 获取通知内容
   */
  public NoticeResponse findNotice(String noticeId) throws Exception {
    NoticeResponse response = new NoticeResponse(ResultType.SUCCESS);
    UserRelateNoticeModel userRelateNoticeModel =
        ucenterServiceFacade.findNoticeById(Integer.valueOf(noticeId));
    response.setUserRelateNoticeModel(userRelateNoticeModel);
    return response;
  }

  /**
   * 获取通知内容
   */
  public NoticeResponse findUserRelateNotice(UserNoticeRequest pojo) throws Exception {
    NoticeResponse response = new NoticeResponse(ResultType.SUCCESS);
    BaseUserModel model = this.decideRedisExsited(pojo);
    if (null == model || StringUtils.isBlank(model.getId()))
      return new NoticeResponse(UcenterResType.UnLoginUser);
    String userId = model.getId();
    String noticeId = pojo.getNoticeId();
    if (StringUtils.isBlank(noticeId)) return new NoticeResponse(UcenterResType.NoFoundNotice);
    UserRelateNoticeModel userRelateNoticeModel =
        ucenterServiceFacade.findUserRelateNoticeById(Long.valueOf(userId),
            Integer.valueOf(noticeId));
    response.setUserRelateNoticeModel(userRelateNoticeModel);
    response.setUserId(userId);
    response.setNoticeId(noticeId);
    return response;
  }

  /**
   * 更改通知状态
   * 
   * @throws
   * @throws Exception
   */
  public int updateUserRelateNotice(String userId, String noticeId) throws Exception {
    // 通知改为已经读
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", Long.valueOf(userId));
    queryCond.put("noticeId", Integer.valueOf(noticeId));
    // queryCond.put("relateId", relateId);
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setStatus(Short.valueOf(Constant.NOTICEINFO_READED));
    return ucenterServiceFacade.updateUserRelateNotice(queryCond, entity);
  }

  /**
   * 修改个人资料service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public ModifyMyInfoResponse modifyUser(ModifyMyInfoRequest pojo) throws Exception {
    try {
      ModifyMyInfoResponse response = new ModifyMyInfoResponse(ResultType.SUCCESS);
      if (StringUtils.isBlank(pojo.getSessionToken())) {
        return new ModifyMyInfoResponse(UcenterResType.UnLoginUser);
      }
      UserModelVo user = new UserModelVo();
      user.setToken(pojo.getSessionToken());
      user.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
      Map<String, Object> cond = this.getQueryCond(null, null, user);
      // 因为新需求的出现所以得加上获取moudelInfo的逻辑
      List<UserModelVo> list =
          ucenterServiceFacade.queryUserVoList(cond, UserModelVoProperty.getAllInfo());
      UserModelVo entity;
      if (null != list && list.size() > 0) {
        entity = list.get(0);
        if (StringUtils.isJsonNotBlank(entity.getModuleInfo())) {
          Map<String, Object> moudelInfoMap =
              FastJsonUtil.fromJsons(entity.getModuleInfo(),
                  new TypeReference<Map<String, Object>>() {});
          user.setModuleInfo(pojo.setMoudelInfo(moudelInfoMap));
        } else {
          user.setModuleInfo(pojo.getMoudelInfo());
        }
      } else {
        return new ModifyMyInfoResponse(UcenterResType.UnLoginUser);
      }
      // 添加新信息
      user.setNickName(pojo.getNickName());
      if (StringUtils.isNotBlank(pojo.getPortrait())) user.setPortrait(pojo.getPortrait()); // 头像
      user.setGender(pojo.getGender()); // 性别
      user.setAddress(pojo.getAddress()); // 地址

      Integer updateFlag1 = ucenterServiceFacade.updateUser(cond, user.getUserModel());
      UserModelVo user2 = new UserModelVo();
      user2.setUserName(entity.getUserName());
      user2.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
      Map<String, Object> cond2 = this.getQueryCond(null, null, user2);
      Integer updateFlag2 =
          ucenterServiceFacade.updateUserModuleInfo(cond2, user.getUserModuleInfoModel());
      if (null != updateFlag1 && updateFlag1 == 1 && null != updateFlag2 && updateFlag2 == 1) {
        // 删除之前的缓存
        UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
        // 从数据库里查出最新的数据
        Map<String, Object> queryCond = Maps.newHashMap();
        queryCond.put("token", pojo.getSessionToken());
        UserModelVo model =
            ucenterServiceFacade.queryUserVoList(queryCond, UserModelVoProperty.getBaseInfo()).get(
                0);
        // 添加最新的用户信息到缓存中
        UserInfoByTokenCache.addUserInfoToCache((BaseUserModel) ModuleMappingWrapper.getWrapper()
            .getModule().getResponse().newInstance().initFromUserModelVo(model),
            pojo.getSessionToken());
      } else {
        return new ModifyMyInfoResponse(UcenterResType.UserUpdateFail);
      }
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 意见反馈service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public ResultInfo addFeedBack(FeedBackRequest pojo) throws Exception {
    FeedBackResponse response = new FeedBackResponse(ResultType.SUCCESS);
    try {
      FeedBackModel model = new FeedBackModel();
      model.setContent(pojo.getFeedContent());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model = ucenterServiceFacade.insertFeedBack(model);
      if (null == model) {
        return new FeedBackResponse(UcenterResType.FeekBackFail);
      }
      return response;
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
      // 检测token是否存在
      if (StringUtils.isBlank(pojo.getSessionToken())) {
        return new ModifyPwdResponse(UcenterResType.UnLoginUser);
      }
      // 判断缓存中是否有用户信息
      UserModel model = new UserModel();
      model.setToken(pojo.getSessionToken());
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
      // .getCode());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> userList =
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
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
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
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
  public UserInfoByTokenResponse checkToken(BaseRequest pojo) throws Exception {
    UserInfoByTokenResponse response = new UserInfoByTokenResponse(ResultType.SUCCESS);
    // 判断token是否为空
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      return new UserInfoByTokenResponse(UcenterResType.NoTokenExisted);
    }
    BaseUserModel model = this.decideRedisExsited(pojo);
    if (null == model) {
      return new UserInfoByTokenResponse(UcenterResType.UnAbleToken);
    }
    if (StringUtils.isNotBlank(model.getUsedDeviceId())) {
      if (StringUtils.isBlank(pojo.getDeviceId())
          || !pojo.getDeviceId().equals(model.getUsedDeviceId())) {
        response = new UserInfoByTokenResponse(UcenterResType.UnAbleToken);
        response.appendRetdesc("unlogin device, plz check it.");
        return response;
      }
    }
    response.setUser(model);
    return response;
  }

  /**
   * 退出登录service
   * 
   * @param pojo
   * @return
   */
  public ResultInfo loginOut(BaseRequest pojo) {
    ResultInfo resultInfo = new ResultInfo(ResultType.SUCCESS);
    try {
      // 1 删除缓存中的数据
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
      // 2 更新数据库中的token和tokenTime
      UserModel model = new UserModel();
      model.setToken(UUID.randomUUID().toString());
      model.setTokenTime(new Timestamp(System.currentTimeMillis()));
      Map<String, Object> queryCond = Maps.newHashMap();
      queryCond.put("token", pojo.getSessionToken());
      queryCond.put("moudle", ModuleMappingWrapper.getWrapper().getModule().getCode());
      ucenterServiceFacade.updateUser(queryCond, model);
      // 3 更改登录状态
      AppBindingModel appBindingModel = new AppBindingModel();
      appBindingModel.setStatus("1");// 退出登录
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
   * 判断token是否存在（从redis再到db的判断） 1 先从缓存中获取用户信息，查看用户信息是否存在，若存在则返回用户信息， 2 若不存在再从db中查看，若存在添加至缓存，不存在给出提示
   * 
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  protected BaseUserModel decideRedisExsited(BaseRequest pojo) throws InstantiationException,
      IllegalAccessException {
    BaseUserModel initFromUserModel =
        UserInfoByTokenCache.getUserInfoFromCache(pojo.getSessionToken());
    if (initFromUserModel == null) {
      // 从db查是否存在
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("token", pojo.getSessionToken());
      // queryCond.put("moudle", ModuleMappingWrapper.getWrapper()
      // .getModule().getCode());
      List<UserModel> userList =
          ucenterServiceFacade.queryUserList(queryCond, UserModelProperty.getBaseInfo());
      if (null == userList || userList.size() <= 0) {
        return null;
      } else if (null != userList && userList.size() == 1) {
        UserModel model = userList.get(0);
        model.setPassword(null);
        UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
        userModuleInfoModel.setUserName(model.getUserName());
        Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
        List<UserModuleInfoModel> listModuleInfo =
            ucenterServiceFacade.queryUserModuleInfoList(conModule,
                UserModuleInfoProperty.getAllInfo());
        if (null == listModuleInfo || listModuleInfo.size() <= 0) return null;
        initFromUserModel =
            (BaseUserModel) ModuleMappingWrapper.getWrapper().getModule().getResponse()
                .newInstance().initFromUserModelVo(model.getUserModelVo(listModuleInfo.get(0)));
        UserInfoByTokenCache.addUserInfoToCache(initFromUserModel, pojo.getSessionToken());
        return initFromUserModel;
      } else {
        return null;
      }
    }
    return initFromUserModel;
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
    // model.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
    Map<String, Object> cond = this.getQueryCond(null, null, model);
    List<UserModel> list =
        ucenterServiceFacade.queryUserList(cond, UserModelProperty.getBriefInfo());
    if (null != list && list.size() >= 1) {
      return true;
    }
    return false;
  }

  /**
   * 第三方登录接口
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public NewLoginResponse newLogin(NewLoginRequest pojo) throws Exception {
    try {
      UcenterResType ucenterResType = UcenterResType.UnCompleteInfo;
      if ("local".equals(pojo.getPlatform())) {
        return localLogin(pojo, ucenterResType);
      }
      if ("device".equals(pojo.getPlatform())) {
        return deviceLogin(pojo);
      }
      NewLoginResponse response = new NewLoginResponse(ResultType.SUCCESS);
      // 1、先去数据库查询
      UserModel model = new UserModel();
      model.setPlatform(pojo.getPlatform());
      String newUniqueId = pojo.getPlatform() + pojo.getUniqueId();// 以保证每个平台之间没有相同的uniqueId
      model.setUniqueId(newUniqueId);
      // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
      // .getCode());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
      if (StringUtils.isBlank(pojo.getPlatform()) || StringUtils.isBlank(pojo.getUniqueId())
          || StringUtils.isBlank(pojo.getRegistrationId()))
        return new NewLoginResponse(ResultType.VALFAIL);

      UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
      userModuleInfoModel.setUserName(newUniqueId);
      Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
      List<UserModuleInfoModel> listModuleInfo =
          ucenterServiceFacade.queryUserModuleInfoList(conModule,
              UserModuleInfoProperty.getAllInfo());
      if (null == listModuleInfo || listModuleInfo.size() <= 0) {
        pojo.setRegistUserModuleInfo(userModuleInfoModel);
        userModuleInfoModel = ucenterServiceFacade.insertUserModuleInfo(userModuleInfoModel);// 插入与模块相关信息
        queueService.registIMAccount(userModuleInfoModel);
      }
      if (null == list || list.size() <= 0) {
        // 执行注册流程
        // 2、注册，插入数据库记录
        pojo.setRegistUserInfo(model);
        model = ucenterServiceFacade.insertUser(model);// 插入用户信息
        response.setToken(model.getToken());
        response.setModuleInfo((BaseUserModel) ModuleMappingWrapper.getWrapper().getModule()
            .getResponse().newInstance()
            .initFromUserModelVo(model.getUserModelVo(userModuleInfoModel)));
      }

      // 执行登录流程
      return thirdLogin(pojo, ucenterResType, response, model, newUniqueId, list);
    } catch (Exception e) {
      LoggerUtil.error("service层：" + "用户第三方登录接口异常", e);
      throw e;
    }
  }

  public void updateBinding(NewLoginRequest pojo, String newUniqueId) {
    // 插入设备记录
    Map<String, Object> queryCond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<String, Object>();
    queryCond.put("deviceId", pojo.getDeviceId());
    output.put("id", "1");
    output.put("pushId", "1");
    output.put("systemType", "1");
    output.put("userId", "1");// uniqueId 第三方唯一标识
    output.put("status", "1");
    List<AppBindingModel> appList = appBindingModelDao.queryList(queryCond, output);
    AppBindingModel appBindingModel = new AppBindingModel();
    appBindingModel.setSystemType(pojo.getClientType());
    appBindingModel.setUserId(newUniqueId);
    appBindingModel.setStatus("0");// 登录状态
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

  public NewLoginResponse localLogin(NewLoginRequest pojo, UcenterResType ucenterResType)
      throws Exception {
    Map<String, Object> modeleInfoMap;
    NewLoginResponse loginResponse = new NewLoginResponse(ResultType.SUCCESS);
    UserModel model = new UserModel();
    model.setUserName(pojo.getPhoneNum());
    // model.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
    Map<String, Object> cond = this.getQueryCond(null, null, model);
    List<UserModel> list = ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
    // 1、校验用户名、密码
    if (null == list || list.size() <= 0) {
      return new NewLoginResponse(UcenterResType.NoFoundUser);
    }
    UserModel entity = list.get(0);
    String passwordFromApp = UcenterUtil.getPasswd(pojo.getPwd() + entity.getSalt());
    if (!entity.getPassword().equals(passwordFromApp)) {
      return new NewLoginResponse(UcenterResType.PasswordError);
    }
    loginResponse.setInfo(login(pojo.getLoginRequest()));

    UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
    userModuleInfoModel.setUserName(pojo.getPhoneNum());
    Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
    List<UserModuleInfoModel> listModuleInfo =
        ucenterServiceFacade
            .queryUserModuleInfoList(conModule, UserModuleInfoProperty.getAllInfo());
    if (null == listModuleInfo || listModuleInfo.size() <= 0) {
      return loginResponse;
    }
    if ("1".equals(ModuleMappingWrapper.getWrapper().getModule().getCode())) {
      if (null != listModuleInfo.get(0)
          && StringUtils.isJsonNotBlank(listModuleInfo.get(0).getModuleInfo())) {
        modeleInfoMap =
            FastJsonUtil.fromJsons(listModuleInfo.get(0).getModuleInfo(),
                new TypeReference<Map<String, Object>>() {});
        if (null == modeleInfoMap.get("type")) {
          loginResponse.setRetcode(ucenterResType.getCode());
          loginResponse.setRetdesc(ucenterResType.getMsg());
        } else if ("0".equals(loginResponse.getRetcode())) {
          loginResponse.setModuleInfo((BaseUserModel) ModuleMappingWrapper.getWrapper().getModule()
              .getResponse().newInstance()
              .initFromUserModelVo(entity.getUserModelVo(listModuleInfo.get(0))));
        }
      } else {
        loginResponse.setRetcode(ucenterResType.getCode());
        loginResponse.setRetdesc(ucenterResType.getMsg());
      }
    }
    return loginResponse;
  }

  public NewLoginResponse deviceLogin(NewLoginRequest pojo) throws InstantiationException,
      IllegalAccessException {
    NewLoginResponse response = new NewLoginResponse(ResultType.SUCCESS);
    // 1、先去数据库查询
    UserModel model = new UserModel();
    // 通过设备id和登录类型（游客模式）就可以确定唯一值
    model.setPlatform(pojo.getPlatform());
    model.setUsedDeviceId(pojo.getDeviceId());
    // model.setModule(ModuleMappingWrapper.getWrapper().getModule()
    // .getCode());
    Map<String, Object> cond = this.getQueryCond(null, null, model);
    List<UserModel> list = ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
    if (StringUtils.isBlank(pojo.getPlatform()) || StringUtils.isBlank(pojo.getDeviceId()))
      return new NewLoginResponse(ResultType.VALFAIL);
    String token = StringUtils.EMPTY;
    UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();

    userModuleInfoModel.setUserName(pojo.getDeviceId());
    Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
    List<UserModuleInfoModel> listModuleInfo =
        ucenterServiceFacade
            .queryUserModuleInfoList(conModule, UserModuleInfoProperty.getAllInfo());
    if (null == listModuleInfo || listModuleInfo.size() <= 0) {
      pojo.setDeviceUserModuleInfo(userModuleInfoModel);
      userModuleInfoModel = ucenterServiceFacade.insertUserModuleInfo(userModuleInfoModel);// 插入与模块相关信息
      queueService.registIMAccount(userModuleInfoModel);
    }

    if (null == list || list.size() <= 0) {
      // 执行(游客模式)注册流程
      // 2、注册，插入数据库记录
      pojo.setDeviceUserInfo(model);
      token = model.getToken();
      model = ucenterServiceFacade.insertUser(model);// 插入用户信息
      response.setToken(token);
    } else {
      model = list.get(0);
      token = model.getToken();
    }
    // 执行（游客模式）登录流程
    // 查出用户信息存至redis
    response.setToken(token);
    UserInfoByTokenCache.addUserInfoToCache(
        (BaseUserModel) ModuleMappingWrapper.getWrapper().getModule().getResponse().newInstance()
            .initFromUserModelVo(model.getUserModelVo(userModuleInfoModel)), token);
    return response;
  }

  public NewLoginResponse thirdLogin(NewLoginRequest pojo, UcenterResType ucenterResType,
      NewLoginResponse response, UserModel model, String newUniqueId, List<UserModel> list)
      throws InstantiationException, IllegalAccessException {
    Map<String, Object> modeleInfoMap = Maps.newHashMap();
    UserModel entity = (null != list && list.size() > 0) ? list.get(0) : model;
    // 获取旧设备登录的最新用户的module
    String module = StringUtils.EMPTY;
    toPushLoginOut(pojo, newUniqueId, entity, module);
    updateBinding(pojo, newUniqueId);
    entity.setUsedDeviceId(pojo.getDeviceId());
    // 更新token值和token生成时间
    String token = UUID.randomUUID().toString();
    // 获取旧TOKEN,更新前从redis删除
    String oldToken = entity.getToken();
    UserInfoByTokenCache.deleteUserInfoFromCache(oldToken);
    entity.setToken(token);
    entity.setTokenTime(new Timestamp(System.currentTimeMillis()));
    entity.setLatestDeviceIp(pojo.getClientIp());
    UserModel model1 = new UserModel();
    // model1.setUniqueId(newUniqueId);
    model1.setUserName(newUniqueId);

    int flag = 1;
    if (StringUtils.isNotBlank(module))
      flag =
          ucenterServiceFacade.updateUserModuleInfo(this.getQueryCond(null, null, model1),
              new UserModuleInfoModel(ModuleMappingWrapper.getWrapper().getModule().getCode()));
    if (1 == ucenterServiceFacade.updateUser(this.getQueryCond(null, null, model1), entity)
        && 1 == flag) {
      // 3.1查出用户信息存至redis
      entity.setPassword(null);
      response.setToken(token);
      UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
      userModuleInfoModel.setUserName(newUniqueId);
      Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
      List<UserModuleInfoModel> listModuleInfo =
          ucenterServiceFacade.queryUserModuleInfoList(conModule,
              UserModuleInfoProperty.getAllInfo());
      if (null == listModuleInfo || listModuleInfo.size() <= 0) {
        return response;
      }
      if ("1".equals(ModuleMappingWrapper.getWrapper().getModule().getCode())) {
        if (null != listModuleInfo.get(0)
            && StringUtils.isJsonNotBlank(listModuleInfo.get(0).getModuleInfo())) {
          modeleInfoMap =
              FastJsonUtil.fromJsons(listModuleInfo.get(0).getModuleInfo(),
                  new TypeReference<Map<String, Object>>() {});
          if (null == modeleInfoMap.get("type")) {
            response.setRetcode(ucenterResType.getCode());
            response.setRetdesc(ucenterResType.getMsg());
          } else {
            // response.setModuleInfo(FastJsonUtil.fromJson(entity.getModuleInfo(),
            // JzUserInfo.class));
            UserModelVo modelVo = entity.getUserModelVo(listModuleInfo.get(0));
            BaseUserModel initFromUserModel =
                (BaseUserModel) ModuleMappingWrapper.getWrapper().getModule().getResponse()
                    .newInstance().initFromUserModelVo(modelVo);
            response.setModuleInfo(initFromUserModel);
            UserInfoByTokenCache.addUserInfoToCache(initFromUserModel, token);
          }
        } else {
          response.setRetcode(ucenterResType.getCode());
          response.setRetdesc(ucenterResType.getMsg());
        }
      }
      return response;
    } else {
      return new NewLoginResponse(UcenterResType.TokenUpdateFail);
    }
  }

  public void toPushLoginOut(NewLoginRequest pojo, String newUniqueId, UserModel entity,
      String module) {
    // 获取旧的设备号
    String usedDeviceId = entity.getUsedDeviceId();
    // 获取旧的第三方唯一标识（旧设备登录的最新用户）
    String usedUniqueId = StringUtils.EMPTY;
    // 查询设备id的最新登录用户（为了找出在此设备上登录的最新用户，此步查询不是多余的查询）
    UserModel usermodel = new UserModel();
    usermodel.setUsedDeviceId(usedDeviceId);
    Map<String, Object> con = this.getQueryCond(null, null, usermodel);
    List<UserModel> listUserModel =
        ucenterServiceFacade.queryUserList(con, UserModelProperty.getAllInfo());// 也可以查xd_app_binding_info表来确定
    if (null != listUserModel && listUserModel.size() > 0) {
      usedUniqueId = listUserModel.get(0).getUniqueId();
      if (StringUtils.isNotBlank(usedUniqueId)) {
        UserModuleInfoModel userModuleInfoModel = new UserModuleInfoModel();
        userModuleInfoModel.setUserName(usedUniqueId);
        Map<String, Object> conModule = this.getQueryCond(null, null, userModuleInfoModel);
        List<UserModuleInfoModel> listModuleInfo =
            ucenterServiceFacade.queryUserModuleInfoList(conModule,
                UserModuleInfoProperty.getAllInfo());
        if (null != listModuleInfo && listModuleInfo.size() > 0)
          module = listModuleInfo.get(0).getModule();
        else {
          userModuleInfoModel.setModule(ModuleMappingWrapper.getWrapper().getModule().getCode());
          ucenterServiceFacade.insertUserModuleInfo(userModuleInfoModel);
          queueService.registIMAccount(userModuleInfoModel);
        }
      }
    }
    // 确定发送方
    String status = StringUtils.EMPTY;
    String registrationId = StringUtils.EMPTY;
    String systemType = StringUtils.EMPTY;
    Map<String, Object> oldqueryCond = new HashMap<String, Object>();
    Map<String, Object> oldoutput = new HashMap<String, Object>();
    oldqueryCond.put("deviceId", usedDeviceId);//
    oldoutput.put("id", "1");
    oldoutput.put("pushId", "1");
    oldoutput.put("systemType", "1");
    oldoutput.put("userId", "1");// uniqueId 第三方唯一标识
    oldoutput.put("status", "1");
    List<AppBindingModel> oldappList = appBindingModelDao.queryList(oldqueryCond, oldoutput);
    if (null != oldappList && oldappList.size() > 0) {
      registrationId = oldappList.get(0).getPushId();
      status = oldappList.get(0).getStatus();
      systemType = oldappList.get(0).getSystemType();
    }
    // 发送推送
    if (StringUtils.isNotBlank(usedDeviceId) && StringUtils.isNotBlank(usedUniqueId)
        && StringUtils.isNotBlank(registrationId) && !usedDeviceId.equals(pojo.getDeviceId())
        && usedUniqueId.equals(newUniqueId) && ("0").equals(status)
        && StringUtils.isNotBlank(module)
        && ModuleMappingWrapper.getWrapper().getModule().getCode().equals(module)) {
      queueService.pushLoginOut(entity, registrationId, systemType);
    }
  }

  /**
   * 系统公告和活动接口serivce
   * 
   * @return
   */
  public BlankNoticeResponse blankNotice() {
    try {
      BlankNoticeResponse response = new BlankNoticeResponse(ResultType.SUCCESS);
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("type", Constant.BLANK_NOTICE_UNSHOW);// 找出要展示的
      List<BlankNoticeModel> blackNoticeList =
          ucenterServiceFacade.queryBlackNoticeList(queryCond,
              BlankNoticeModelProperty.getAllInfo());
      if (null == blackNoticeList || blackNoticeList.size() <= 0) {
        return new BlankNoticeResponse(UcenterResType.NoFoundNotice);
      }
      if (blackNoticeList.size() > 1)
        return new BlankNoticeResponse(UcenterResType.MoreThanOneData);
      return response.setBlankNoticeResponse(blackNoticeList.get(0));
    } catch (Exception e) {
      throw e;
    }
  }

}
