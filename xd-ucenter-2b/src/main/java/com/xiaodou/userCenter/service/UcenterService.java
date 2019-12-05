package com.xiaodou.userCenter.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.info.model.ModuleInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.facerecognition.FaceIdResponse;
import com.xiaodou.facerecognition.IFaceRecognitionApi;
import com.xiaodou.facerecognition.RetCode;
import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.FollowEvent;
import com.xiaodou.mission.engine.event.ImproveInfoEvent;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.ShortMessageProLine;
import com.xiaodou.push.agent.model.ShortMessage;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.thrift.annotion.ThriftMethod;
import com.xiaodou.thrift.annotion.ThriftService;
import com.xiaodou.userCenter.cache.CheckCodeCache;
import com.xiaodou.userCenter.cache.UserInfoByTokenCache;
import com.xiaodou.userCenter.cache.UserLoginInfoCache;
import com.xiaodou.userCenter.common.Constant;
import com.xiaodou.userCenter.common.enums.CheckCodeTypeEnum;
import com.xiaodou.userCenter.common.enums.PretreatmentEnum;
import com.xiaodou.userCenter.common.enums.StatusEnum;
import com.xiaodou.userCenter.constant.UcenterConstant;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.dao.AppBindingDao;
import com.xiaodou.userCenter.model.AppBindingModel;
import com.xiaodou.userCenter.model.AppBindingModel.AppBindingStatus;
import com.xiaodou.userCenter.model.BaseUserInfo;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CheckCodeModel;
import com.xiaodou.userCenter.model.CreditChangeLog;
import com.xiaodou.userCenter.model.FeedBackCategoryDO;
import com.xiaodou.userCenter.model.FeedBackModel;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.RankUserInfo;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserPraiseModel;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.model.alarm.AlarmRecordModel;
import com.xiaodou.userCenter.model.alarm.LoginInfoModel;
import com.xiaodou.userCenter.model.alarm.PretreatmentDTO;
import com.xiaodou.userCenter.model.http.AdmissionCardCodeRequest.AdmissionCardCodeRequestDTO;
import com.xiaodou.userCenter.model.http.ResponseBase;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.model.property.HelpModelProperty;
import com.xiaodou.userCenter.model.property.UserModelProperty;
import com.xiaodou.userCenter.model.property.UserRelateNoticeProperty;
import com.xiaodou.userCenter.model.vo.UserPraiseVo;
import com.xiaodou.userCenter.module.selfTaught.model.StOfficialInfo;
import com.xiaodou.userCenter.module.selfTaught.request.StOfficialInfoRegist;
import com.xiaodou.userCenter.module.selfTaught.response.StUserInfoResponse;
import com.xiaodou.userCenter.module.selfTaught.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.prop.OpenCourseProp;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.CheckCodeRequest;
import com.xiaodou.userCenter.request.CreditOperationListRequest;
import com.xiaodou.userCenter.request.FeedBackCategoryRequest;
import com.xiaodou.userCenter.request.FeedBackRequest;
import com.xiaodou.userCenter.request.FindPwdRequest;
import com.xiaodou.userCenter.request.FirstLoginTimeRequest;
import com.xiaodou.userCenter.request.ModifyDefaultPassword;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.request.ModifyPwdRequest;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.request.PraiseListRequest;
import com.xiaodou.userCenter.request.QueryUserListRequest;
import com.xiaodou.userCenter.request.QueryUserRequest;
import com.xiaodou.userCenter.request.RegistAccountRequest;
import com.xiaodou.userCenter.request.UploadBenchmarkFaceRequest;
import com.xiaodou.userCenter.request.UserBaseInfo;
import com.xiaodou.userCenter.request.UserListRequest;
import com.xiaodou.userCenter.request.UserNoticeRequest;
import com.xiaodou.userCenter.request.UserPraiseRequest;
import com.xiaodou.userCenter.request.UserProductOpenRequest;
import com.xiaodou.userCenter.request.UserRankRequest;
import com.xiaodou.userCenter.request.ValidateCodeRequest;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.BaseUserModel;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.CheckCodeResponse;
import com.xiaodou.userCenter.response.CreditOperationListResponse;
import com.xiaodou.userCenter.response.CreditOperationListResponse.CreditOperation;
import com.xiaodou.userCenter.response.FeedBackResponse;
import com.xiaodou.userCenter.response.FindPasswordResponse;
import com.xiaodou.userCenter.response.FirstLoginTimeResponse;
import com.xiaodou.userCenter.response.HelpDocResponse;
import com.xiaodou.userCenter.response.ImportMemberResponse;
import com.xiaodou.userCenter.response.LoginResponse;
import com.xiaodou.userCenter.response.ModifyMyInfoResponse;
import com.xiaodou.userCenter.response.ModifyPwdResponse;
import com.xiaodou.userCenter.response.NoticeResponse;
import com.xiaodou.userCenter.response.PraiseListResponse;
import com.xiaodou.userCenter.response.PraiseListResponse.UserPraiseInfo;
import com.xiaodou.userCenter.response.QueryUserListResponse;
import com.xiaodou.userCenter.response.RegistAccountResponse;
import com.xiaodou.userCenter.response.UnreadMessageResponse;
import com.xiaodou.userCenter.response.UserInfoByTokenResponse;
import com.xiaodou.userCenter.response.UserListResponse;
import com.xiaodou.userCenter.response.UserModelResponse;
import com.xiaodou.userCenter.response.UserRankResponse;
import com.xiaodou.userCenter.response.UserRelateNoticeResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.response.ucenterHttp.BaseUcenterResponse;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.service.http.UcenterHttpService;
import com.xiaodou.userCenter.service.queue.QueueService;
import com.xiaodou.userCenter.service.queue.QueueService.PushLoginOutDTO;
import com.xiaodou.userCenter.util.CheckDuplicateUtil;
import com.xiaodou.userCenter.util.RandomNumberUtil;
import com.xiaodou.userCenter.util.TimeUtil;
import com.xiaodou.userCenter.util.VerifyUtil;
import com.xiaodou.userCenter.util.WalletUtil;
import com.xiaodou.userCenter.vo.alarm.UCenterExecuteExceptionAlarm;
import com.xiaodou.userCenter.vo.alarm.UCenterExecuteExceptionAlarm.UcenterAlarmEnum;
import com.xiaodou.userCenter.vo.mq.UpdateSourceFaceEvent;
import com.xiaodou.userCenter.vo.mq.UpdateSourceFaceEvent.TransferStudentData;
import com.xiaodou.wallet.agent.service.WalletService;

/**
 * 
 * 用户中心service 用户是用户，module是module
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
@Service("ucenterService")
@ThriftService
public class UcenterService extends CheckLoginService {

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;
  @Resource
  AppBindingDao appBindingDao;
  @Resource
  QueueService queueService;
  @Resource
  AsyncMessageService asyncMessageService;
  @Resource
  UserLoginInfoService userLoginInfoService;
  @Resource
  IFaceRecognitionApi faceRecognitionApi;
  @Resource
  UcenterHttpService ucenterHttpService;
  @Resource(name = "stUcenterModelServiceFacade")
  IUcenterModelServiceFacade stUcenterModelServiceFacade;

  /**
   * 
   * 获取验证码
   * 
   * @param pojo
   * @return
   */
  @ThriftMethod
  public CheckCodeResponse getCheckCode(CheckCodeRequest pojo) throws Exception {
    try {
      CheckCodeResponse response = new CheckCodeResponse(ResultType.SUCCESS);
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        String code = RandomNumberUtil.randomString(4);
        // 短信验证码测试数据
        // 1、校验验证码类型和手机号
        if (!PhoneUtil.validatePhone(pojo.getPhoneNum())) {
          return new CheckCodeResponse(UcenterResType.PhoneNumError);
        }
        // 2.1、校验验证码类型
        if (CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())) {
          if (ucenterHttpService.checkPhoneExist(pojo.getPhoneNum())) {
            return new CheckCodeResponse(UcenterResType.HasRegisterd);
          }
        }
        if (CheckCodeTypeEnum.FindBackPassword.getCode().equals(pojo.getCheckCodeType())
            || CheckCodeTypeEnum.OfficialPassword.getCode().equals(pojo.getCheckCodeType())) {
          if (!ucenterHttpService.checkPhoneExist(pojo.getPhoneNum())) {
            return new CheckCodeResponse(UcenterResType.NoFoundUser);
          }
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
        sm.setProductLine(ShortMessageProLine.CHECK_CODE.name());
        sm.setAppMessageId(UUID.randomUUID().toString());
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
    if (!PhoneUtil.validatePhone(pojo.getPhoneNum()))
      return new RegistAccountResponse(UcenterResType.PhoneNumError);
    if (!CheckCodeTypeEnum.UserRegister.getCode().equals(pojo.getCheckCodeType())
        && !CheckCodeTypeEnum.FindBackPassword.getCode().equals(pojo.getCheckCodeType())) {
      return new CheckCodeResponse(UcenterResType.CheckCodeTypeError);
    }
    // 3、校验验证码
    if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
      if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4))
        return new RegistAccountResponse(UcenterResType.checkCodeError);
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
      if (!VerifyUtil.isMobile(pojo.getPhoneNum())) {
        return new RegistAccountResponse(UcenterResType.PhoneNumError);
      }
      // 1.1、注册验证密码
      if (!VerifyUtil.isNUMSTR(pojo.getPassword())
          || !VerifyUtil.isNUMSTR(pojo.getConfirmPassword()))
        return new RegistAccountResponse(UcenterResType.PwdError);
      // 2、校验pwd与confirmPassword是否相同
      if (!pojo.getPassword().equals(pojo.getConfirmPassword())) {
        return new RegistAccountResponse(UcenterResType.EnteredPasswordsDiffer);
      }
      BaseUcenterResponse baseResponse =
          ucenterHttpService.register(pojo.getPassword(), pojo.getPhoneNum(), pojo.getModule());
      if (null == baseResponse) new RegistAccountResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        result.setRetcode(baseResponse.getRetcode());
        result.setRetdesc(baseResponse.getRetdesc());
        return result;
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
      // 1、业务数据库查询
      UserModel condModel = new UserModel();
      condModel.setXdUniqueId(baseResponse.getXdUniqueId());
      Map<String, Object> cond = this.getQueryCond(null, null, condModel);
      UserModel findModel = ucenterServiceFacade.queryUser(cond, UserModelProperty.getAllInfo());
      UserModel model = new UserModel();
      String token = "";
      if (null != findModel) {// 这段逻辑，理论是永远不会进入的。
        // 流程 更新数据
        String oldToken = findModel.getToken();
        UserInfoByTokenCache.deleteUserInfoFromCache(oldToken);
        if (!updateInfo(pojo, condModel)) {
          return new RegistAccountResponse(UcenterResType.TokenUpdateFail);
        }
        token = condModel.getToken();
      } else {// 5、校验通过，注册，插入数据库记录
        model.setXdUniqueId(baseResponse.getXdUniqueId());
        pojo.setRegistUserInfo(model);
        model = operateRegistUserInfo(pojo, model);
        token = model.getToken();
      }
      // 6、删除该条code缓存
      CheckCodeCache.deleteCheckCode(CheckCodeTypeEnum.UserRegister.getCode(), pojo.getPhoneNum());
      result.setToken(token);
      return result;
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new UCenterExecuteExceptionAlarm(UcenterAlarmEnum.REGIST_USER_FAIL,
          FastJsonUtil.toJson(pojo)));
      LoggerUtil.error("service层：用户注册异常", e);
      throw e;
    }
  }

  /**
   * 事务注册用户对象至数据库 目标保证，数据库生成数据
   * 
   * @param pojo
   * @param model
   * @return
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public UserModel operateRegistUserInfo(BaseRequest pojo, UserModel model)
      throws InstantiationException, IllegalAccessException {
    model = ucenterServiceFacade.insertOrUpdateEntity(model);// 插入用户信息
    if (null == model || null == model.getId()) {
      String modelJson = StringUtils.EMPTY;
      int tryTime = 0;
      while (StringUtils.isJsonBlank(modelJson) && tryTime < 50) {
        try {
          Thread.sleep(100);
        } catch (Exception e) {}
        modelJson = JedisUtil.getStringFromJedis(Constant.CACHE_USER + model.getXdUniqueId());
        tryTime++;
      }
      if (StringUtils.isJsonNotBlank(modelJson)) {
        model = FastJsonUtil.fromJson(modelJson, UserModel.class);
      } else {
        return model;
      }
    } else {
      ResultInfo walletResponse =
          WalletService.createAccount(WalletUtil.formatProductLine(), model.getId().toString(),
              Lists.newArrayList(UcenterConstant.BUSINESS_TYPE_ZKJ));
      if (!"0".equals(walletResponse.getRetcode()) && !"40001".equals(walletResponse.getRetcode())) {
        throw new RuntimeException(walletResponse.getRetdesc());
      }
    }
    JedisUtil.addStringToJedis(Constant.CACHE_USER + model.getXdUniqueId(),
        FastJsonUtil.toJson(model), 86400);
    // queueService.registIMAccount(model);
    return model;
  }

  /**
   * 
   * 找回密码接口
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public FindPasswordResponse findPassword(FindPwdRequest pojo) throws Exception {
    FindPasswordResponse response = new FindPasswordResponse(ResultType.SUCCESS);
    try {
      if (!PhoneUtil.validatePhone(pojo.getPhoneNum())) {
        return new FindPasswordResponse(UcenterResType.PhoneNumError);
      }
      if (!VerifyUtil.isNUMSTR(pojo.getPwd())) {
        return new FindPasswordResponse(UcenterResType.PwdError);
      }
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        // 3、校验验证码
        if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4)) {
          return new FindPasswordResponse(UcenterResType.checkCodeError);
        }
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
      ResponseBase baseResponse =
          ucenterHttpService.findPassword(pojo.getPwd(), pojo.getPhoneNum());
      if (null == baseResponse) new FindPasswordResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        response.setRetcode(baseResponse.getRetcode());
        response.setRetdesc(baseResponse.getRetdesc());
        return response;
      }
      // 删除缓存信息
      CheckCodeCache.deleteCheckCode(CheckCodeTypeEnum.FindBackPassword.getCode(),
          pojo.getPhoneNum());
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 帮助(学长秘籍)serivce
   * 
   * @return
   */
  public HelpDocResponse selectHelpDoc(BaseRequest pojo) {
    try {
      HelpDocResponse response = new HelpDocResponse(ResultType.SUCCESS);
      List<HelpDocModel> helpList =
          ucenterServiceFacade.queryHelpDocList(null, HelpModelProperty.getAllInfo());
      if (null == helpList || helpList.size() <= 0) {
        return new HelpDocResponse(UcenterResType.NoFoundHelp);
      }
      for (HelpDocModel helpDocModel : helpList) {
        helpDocModel.setHelpId(helpDocModel.getId());
      }
      response.setHelpList(helpList);
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 获取学长秘籍内容
   */
  public HelpDocModel findHelpDocById(String helpId) throws Exception {
    Map<String, Object> input = Maps.newHashMap();
    input.put("helpId", helpId);
    List<HelpDocModel> helpList =
        ucenterServiceFacade.queryHelpDocList(input, CommUtil.getAllField(HelpDocModel.class));
    if (null != helpList && helpList.size() == 1) {
      return helpList.get(0);
    }
    return null;
  }

  /**
   * 公告serivce
   * 
   * @return
   * @throws Exception
   */
  public UserRelateNoticeResponse selectNotice(BaseRequest pojo) throws Exception {
    try {
      UserRelateNoticeResponse response = new UserRelateNoticeResponse(ResultType.SUCCESS);
      CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) return new UserRelateNoticeResponse(result.getResType());
      BaseUserModel model = result.getModel();
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("userId", model.getId());
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
          uNoticeModel.setUserId(Long.valueOf(model.getId()));
          uNoticeModel.setStatus(Short.valueOf(UcenterModelConstant.NOTICEINFO_UNREAD));
          uNoticeModel.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
          ucenterServiceFacade.insertUserRelateNotice(uNoticeModel);
        }
        uNoticeModel.setId(uNoticeModel.getNoticeId());
        if (StringUtils.isNotBlank(uNoticeModel.getCreateTime()))
          uNoticeModel.setCreateTime(DateUtil.relativeDateFormat(Timestamp.valueOf(createTime)));
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
   * 获取和用户相关的通知内容
   */
  public NoticeResponse findUserRelateNotice(UserNoticeRequest pojo) throws Exception {
    NoticeResponse response = new NoticeResponse(ResultType.SUCCESS);
    String userId = pojo.getUserId();
    String noticeId = pojo.getNoticeId();
    if (StringUtils.isBlank(noticeId)) return new NoticeResponse(UcenterResType.NoFoundNotice);
    if (StringUtils.isBlank(userId)) return new NoticeResponse(ResultType.SYSFAIL);
    UserRelateNoticeModel userRelateNoticeModel =
        ucenterServiceFacade.findUserRelateNoticeById(Long.valueOf(userId),
            Integer.valueOf(noticeId));
    response.setUserRelateNoticeModel(userRelateNoticeModel);
    response.setUserId(userId);
    response.setNoticeId(noticeId);
    return response;
  }


  /**
   * 更改用户的某条通知状态
   * 
   * @param userId
   * @param noticeId
   * @return
   * @throws Exception
   */
  public BaseResultInfo updateUserRelateNotice(UserNoticeRequest pojo) throws Exception {
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    String userId = pojo.getUserId();
    String noticeId = pojo.getNoticeId();
    // 通知改为已经读
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", Long.valueOf(userId));
    queryCond.put("noticeId", Integer.valueOf(noticeId));
    // queryCond.put("relateId", relateId);
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setStatus(Short.valueOf(UcenterModelConstant.NOTICEINFO_READED));
    int flag = ucenterServiceFacade.updateUserRelateNotice(queryCond, entity);
    if (flag != 1) return new BaseResultInfo(ResultType.SYSFAIL);
    return response;
  }

  /**
   * 更改用户的某条通知状态
   * 
   * @param userId
   * @param noticeId
   * @return
   * @throws Exception
   */
  public int updateUserRelateNotice(String userId, String noticeId) throws Exception {
    // 通知改为已经读
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", Long.valueOf(userId));
    queryCond.put("noticeId", Integer.valueOf(noticeId));
    // queryCond.put("relateId", relateId);
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setStatus(Short.valueOf(UcenterModelConstant.NOTICEINFO_READED));
    return ucenterServiceFacade.updateUserRelateNotice(queryCond, entity);
  }

  /**
   * 更改用户通知状态
   * 
   * @param userId
   * @return
   * @throws Exception
   */
  public int updateUserRelateNoticeByUid(String userId) throws Exception {
    // 通知改为已经读
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", Long.valueOf(userId));
    UserRelateNoticeModel entity = new UserRelateNoticeModel();
    entity.setStatus(Short.valueOf(UcenterModelConstant.NOTICEINFO_READED));
    return ucenterServiceFacade.updateUserRelateNotice(queryCond, entity);
  }

  /**
   * 一键已读
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo noticetoReadAll(BaseRequest pojo) {
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    try {
      CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) return new BaseResultInfo(result.getResType());
      // 校验信息完整性
      // if (!result.getModel().checkInfo()) {
      // response = new BaseResultInfo(UcenterResType.UnCompleteInfo);
      // return response;
      // }
      this.updateUserRelateNoticeByUid(result.getModel().getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  /**
   * 意见反馈service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo addFeedBack(FeedBackRequest pojo) throws Exception {
    FeedBackResponse response = new FeedBackResponse(ResultType.SUCCESS);
    try {
      FeedBackModel model = new FeedBackModel();
      model.setContent(pojo.getFeedContent());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model = ucenterServiceFacade.insertFeedBack(model);
      if (null == model) {
        return new FeedBackResponse(UcenterResType.FeekbackFail);
      }
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 
   * @description 1.4.0版本 意见反馈
   * @author 李德洪
   * @Date 2017年11月9日
   * @param pojo
   * @return
   * @throws Exception
   */
  public FeedBackResponse saveFeedBack(FeedBackCategoryRequest pojo) {
    if (StringUtils.isNotBlank(pojo.getFeedContent()) && pojo.getFeedContent().length() > 1000) {
      return new FeedBackResponse(UcenterResType.MORE_THAN_EXCEED);
    }
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("token", pojo.getSessionToken());
    UserModel userModel =
        ucenterServiceFacade.queryUser(cond, CommUtil.getAllField(UserModel.class));
    if (null == userModel) return new FeedBackResponse(UcenterResType.NoFoundUser);
    FeedBackResponse response = new FeedBackResponse(ResultType.SUCCESS);
    try {
      FeedBackModel model = new FeedBackModel();
      List<String> categoryDescList =
          FastJsonUtil.fromJsons(pojo.getCategoryDescList(), new TypeReference<List<String>>() {});
      if (null == categoryDescList || categoryDescList.isEmpty()) {
        return new FeedBackResponse(UcenterResType.CategorIsNull);
      }
      model.setCategoryDescs(pojo.getCategoryDescList());
      model.setContent(FastJsonUtil.toJson(pojo));
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setContent(pojo.getFeedContent());
      if (StringUtils.isNotBlank(pojo.getImageUrlList())) {
        FastJsonUtil.fromJsons(pojo.getImageUrlList(), new TypeReference<List<String>>() {});
        model.setImageUrls(pojo.getImageUrlList());
      }
      model.setNumber(pojo.getNumber());
      model.setUserId(Long.valueOf(pojo.getUserId()));
      model.setDeviceType(pojo.getDeviceType());
      model.setOsVersion(pojo.getOsVersion());
      model.setAppVersion(pojo.getVersion());
      model = ucenterServiceFacade.insertFeedBack(model);
      Map<String, Object> column = CommUtil.getAllField(FeedBackCategoryDO.class);
      column.remove("id");
      List<FeedBackCategoryDO> value = Lists.newArrayList();
      for (String cd : categoryDescList) {
        FeedBackCategoryDO fdo = new FeedBackCategoryDO();
        fdo.setFeedbackId(model.getId());
        fdo.setCategoryDesc(cd);
        fdo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        value.add(fdo);
      }
      ucenterServiceFacade.saveFeedBackCategoryList(column, value);
      ucenterHttpService.sendDingtalk(userModel, pojo);
      return response;
    } catch (JSONException e) {
      LoggerUtil.error("saveFeedBack", e);
      return new FeedBackResponse(UcenterResType.JSON_FAIL);
    } catch (Exception e) {
      LoggerUtil.error("saveFeedBack", e);
      return new FeedBackResponse(ResultType.SYSFAIL);
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
      if (!VerifyUtil.isNUMSTR(pojo.getOldPwd()) || !VerifyUtil.isNUMSTR(pojo.getNewPwd())
          || !VerifyUtil.isNUMSTR(pojo.getConfirmNewPwd()))
        return new ModifyPwdResponse(UcenterResType.PwdError);
      if (!pojo.getConfirmNewPwd().trim().equals(pojo.getNewPwd().trim()))
        return new ModifyPwdResponse(UcenterResType.TwicePwdError);
      // 检测token是否存在
      if (StringUtils.isBlank(pojo.getSessionToken())) {
        return new ModifyPwdResponse(UcenterResType.UnLoginUser);
      }
      // 判断缓存中是否有用户信息
      UserModel model = new UserModel();
      model.setToken(pojo.getSessionToken());
      Map<String, Object> cond = this.getQueryCond(null, null, model);
      List<UserModel> userList =
          ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
      if (null == model || userList.size() <= 0) {
        return new ModifyPwdResponse(UcenterResType.UnLoginUser);
      }
      model = userList.get(0);
      ResponseBase baseResponse =
          ucenterHttpService.modifyPassword(model.getXdUniqueId(), pojo.getOldPwd(),
              pojo.getNewPwd(), pojo.getConfirmNewPwd());
      if (null == baseResponse) new ModifyPwdResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        response.setRetcode(baseResponse.getRetcode());
        response.setRetdesc(baseResponse.getRetdesc());
        return response;
      }
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
  public UserInfoByTokenResponse checkToken(BaseRequest pojo, String headVersion) throws Exception {
    UserInfoByTokenResponse response = new UserInfoByTokenResponse(ResultType.SUCCESS);
    try {
      CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) {
        return new UserInfoByTokenResponse(result.getResType());
      }
      // 校验信息完整性
      if (!result.getModel().checkInfo()) {
        response = new UserInfoByTokenResponse(UcenterResType.UnCompleteInfo);
        return response;
      }
      // 获取基础信息
      String mainAccount =
          ucenterHttpService.findUserMainAccount(result.getModel().getXdUniqueId());
      if (StringUtils.isEmpty(mainAccount)) {
        return new UserInfoByTokenResponse(UcenterResType.BASE_USER_IS_NULL);
      }
      StUserInfoResponse userInfo = (StUserInfoResponse) result.getModel();
      userInfo.setMainAccount(mainAccount);

      if (!headVersion.equals("149")) {
        // 新版本，无地域信息时
        if (StringUtils.isEmpty(userInfo.getRegion())) {
          // 根据专业查region
          Map<String, Object> input = Maps.newHashMap();
          input.put("showStatus", 1);
          input.put("module", UcenterModelConstant.MODULE);
          input.put("typeCode", ((StUserInfoResponse) result.getModel()).getMajor());
          Map<String, Object> output = CommUtil.getAllField(ProductCategoryUtilModel.class);
          output.remove("majorInfo");
          List<ProductCategoryUtilModel> ls =
              stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
          if (!CollectionUtils.isEmpty(ls)) {
            ProductCategoryUtilModel temp = ls.get(0);
            userInfo.setRegion(temp.getModule());
            userInfo.setRegionName(temp.getModuleName());
            userInfo.setMajorId(temp.getId().toString());
            userInfo.setMajorName(temp.getName());
          }
        }
      }
      response.setUser(userInfo);

    } catch (Exception e) {
      LoggerUtil.alarmInfo(new UCenterExecuteExceptionAlarm(UcenterAlarmEnum.CHECK_TOKEN_FAIL,
          FastJsonUtil.toJson(pojo)));
      LoggerUtil.error("checkToken. ", e);
      response = new UserInfoByTokenResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 退出登录service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo loginOut(BaseRequest pojo) throws Exception {
    BaseResultInfo resultInfo = new BaseResultInfo(ResultType.SUCCESS);
    try {
      // 校验token
      // CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      // if (!result.isRetOk()) return resultInfo;
      // 1 删除缓存中的数据
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
      // 2 更新数据库中的token和tokenTime
      UserModel model = new UserModel();
      model.setToken(UUID.randomUUID().toString());
      model.setTokenTime(new Timestamp(System.currentTimeMillis()));
      Map<String, Object> queryCond = Maps.newHashMap();
      queryCond.put("token", pojo.getSessionToken());
      ucenterServiceFacade.updateUser(queryCond, model);
      // 3 更改登录状态
      AppBindingModel appBindingModel = new AppBindingModel();
      appBindingModel.setStatus("1");// 退出登录
      String deviceId = pojo.getDeviceId();
      Map<String, Object> qcond = new HashMap<String, Object>();
      qcond.put("deviceId", deviceId);
      appBindingDao.updateEntity(qcond, appBindingModel);
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
  private boolean checkUserStatus(String deviceId, String userId) {
    // 去数据库查询
    Map<String, Object> input = Maps.newHashMap();
    input.put("deviceId", deviceId);
    input.put("userId", userId);
    List<AppBindingModel> list =
        appBindingDao.queryList(input, CommUtil.getAllField(AppBindingModel.class));
    if (null != list && list.size() >= 1) {
      if (list.get(0).getStatus().equals(AppBindingStatus.LOGIN.getCode()))
        return true;
      else if (list.get(0).getStatus().equals(AppBindingStatus.OUTLOGIN.getCode())) return false;
    }
    return false;
  }

  public LoginResponse login(NewLoginRequest pojo, String headVersion) throws Exception {
    // 1s之内同一账号同时登录
    if (CheckDuplicateUtil.checkDuplicate(pojo.getPhoneNum(), 1)) {
      return new LoginResponse(UcenterResType.LOGIN_DUPLICATE);
    }
    LoginResponse response = new LoginResponse(ResultType.SUCCESS);
    try {
      // 登录基础账号
      BaseUcenterResponse baseResponse = ucenterHttpService.login(pojo);
      if (null == baseResponse) new LoginResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        response.setRetcode(baseResponse.getRetcode());
        response.setRetdesc(baseResponse.getRetdesc());
        return response;
      }
      // 1、业务数据库查询
      UserModel condModel = new UserModel();
      condModel.setXdUniqueId(baseResponse.getXdUniqueId());
      Map<String, Object> cond = this.getQueryCond(null, null, condModel);
      UserModel findModel = ucenterServiceFacade.queryUser(cond, UserModelProperty.getAllInfo());
      if (null == findModel) {
        // 2、注册，插入数据库记录(与基础用户同步)
        pojo.setRegistUserInfo(condModel);
        findModel = operateRegistUserInfo(pojo, condModel);
      }

      String usedDeviceId = findModel.getUsedDeviceId();
      // 3、登录流程 更新数据
      String oldToken = findModel.getToken();
      // 4、登录流程 推送顶人消息
      toPushLoginOut(pojo, findModel, oldToken, usedDeviceId);
      // 5、登录流程 更新绑定信息
      updateBinding(pojo, findModel.getId().toString());

      UserInfoByTokenCache.deleteUserInfoFromCache(oldToken);
      if (!updateInfo(pojo, findModel)) {
        return new LoginResponse(UcenterResType.TokenUpdateFail);
      }
      StUserInfoResponse userInfo =
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(findModel);
      UserInfoByTokenCache.addUserInfoToCache(userInfo, findModel.getToken());

      if (UcenterModelConstant.USER_TYPE_IMPORT == findModel.getUserType()) {
        if (StringUtils.isBlank(pojo.getLat()) || StringUtils.isBlank(pojo.getLng()))
          return new LoginResponse(UcenterResType.NeedUploadArae);
      }

      if (!headVersion.equals("149")) {
        initRegion(userInfo, pojo);
      }

      // 6、校验信息完整性
      if (!userInfo.checkInfo()) {
        response = new LoginResponse(UcenterResType.UnCompleteInfo);
        response.setToken(userInfo.getToken());
        response.setUser(userInfo);
        return response;
      }
      // 保存登录缓存信息
      if (UcenterModelConstant.USER_TYPE_IMPORT == findModel.getUserType()) {
        this.saveUserLoginInfo(pojo, findModel);
      }
      response.setToken(userInfo.getToken());
      response.setUser(userInfo);
    } catch (Exception e) {
      LoggerUtil.error("service层：" + "用户登录接口异常", e);
      LoggerUtil.alarmInfo(new UCenterExecuteExceptionAlarm(UcenterAlarmEnum.LOGIN_USER_FAIL,
          FastJsonUtil.toJson(pojo)));
      response = new LoginResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  private void initRegion(StUserInfoResponse userInfo, NewLoginRequest pojo) {
    // 新版本，无地域信息时
    if (StringUtils.isEmpty(userInfo.getRegion())) {
      if (userInfo.checkInfo()) {
        // 根据专业查region
        Map<String, Object> input = Maps.newHashMap();
        input.put("showStatus", 1);
        input.put("typeCode", userInfo.getMajor());
        input.put("module", UcenterModelConstant.MODULE);
        Map<String, Object> output = CommUtil.getAllField(ProductCategoryUtilModel.class);
        output.remove("majorInfo");
        List<ProductCategoryUtilModel> ls =
            stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
        if (!CollectionUtils.isEmpty(ls)) {
          ProductCategoryUtilModel temp = ls.get(0);
          userInfo.setRegion(temp.getModule());
          userInfo.setRegionName(temp.getModuleName());
          userInfo.setMajorId(temp.getId().toString());
          userInfo.setMajorName(temp.getName());
        }
      } else {
        ModuleInfo mi = null;
        // 先查当前地域信息
        String regionName = userLoginInfoService.getProvince(pojo);
        MODULEINFO: {
          if (StringUtils.isNotBlank(regionName)) {
            mi = CommonInfoUtil.getModuleInfoByModuleName(regionName);
          }
          if (null != mi) {
            break MODULEINFO;
          }
          // 获取默认地域
          mi = CommonInfoUtil.getDefauleModuleInfo();
        }
        if (null == mi) {
          LoggerUtil.alarmInfo(new UCenterExecuteExceptionAlarm(UcenterAlarmEnum.FIND_MODULE_FAIL,
              null));
        } else {
          userInfo.setRegion(mi.getModule());
          userInfo.setRegionName(mi.getModuleName());
        }
      }
    }
  }

  @SuppressWarnings("unused")
  private LoginResponse isNormalUser(UserModel model) {
    // 查询缓存中异常消息
    AlarmRecordModel alarmRecord = this.getAlarmRecordCache(model);
    if (null != alarmRecord) {
      switch (alarmRecord.getPretreatment()) {
        case AccountDeviceClosure:
          return new LoginResponse(UcenterResType.AccountDeviceClosure);
        case AccountClosure:
          return new LoginResponse(UcenterResType.AccountClosure);
        case DeviceClosure:
          return new LoginResponse(UcenterResType.DeviceClosure);
        default:
          break;
      }
    }
    return new LoginResponse(ResultType.SUCCESS);
  }

  public String createContent(UserProductOpenRequest obj) throws Exception {
    if (obj == null) return "";
    StringBuilder content = new StringBuilder();
    content.append("&uid=" + obj.getUid());
    content.append("&typeCode=" + obj.getTypeCode());
    content.append("&module=" + obj.getModule());
    content.append("&orderStatus=" + obj.getOrderStatus());
    content.append("&courseId=" + obj.getCourseId());
    return content.substring(1);
  }

  public <T> T doflow(UserProductOpenRequest req, Class<T> clazzRes, Map<String, String> headers) {
    try {
      String content = this.createContent(req);
      String result =
          send(OpenCourseProp.getParams("course.host"),
              Integer.parseInt(OpenCourseProp.getParams("course.port")),
              OpenCourseProp.getParams("course.open.newbie.url"),
              Integer.parseInt(OpenCourseProp.getParams("course.open.newbie.retryTime")),
              Integer.parseInt(OpenCourseProp.getParams("course.open.newbie.timeout")), content,
              headers);
      return parseResponse(result, clazzRes);
    } catch (Exception e) {
      LoggerUtil.error("游客开通新手课程异常", e);
      return null;
    }
  }

  public <T> T parseResponse(String result, Class<T> clazzRes) {
    return null == result ? null : JSON.parseObject(result, clazzRes);
  }

  public static String send(String host, int port, String path, int retryTime, int timeout,
      String content, Map<String, String> headers) throws Exception {

    HttpUtil http = HttpUtil.getInstance(host, port, "http", timeout, retryTime);
    HttpMethod httpMethod =
        HttpMethodUtil.getPostMethod(path, "application/x-www-form-urlencoded", "utf-8", content);
    if (null != headers) for (String headerName : headers.keySet()) {
      httpMethod.setRequestHeader(headerName, headers.get(headerName));
    }
    http.setMethod(httpMethod);
    HttpResult httpResult = http.getHttpResult();
    if (!httpResult.isResultOk()) {
      LoggerUtil.error("[通信异常][" + httpResult.toString() + "]", httpResult.getErr() == null
          ? new RuntimeException()
          : httpResult.getErr());
      return null;
    } else {
      return httpResult.getContent();
    }
  }

  public void updateBinding(NewLoginRequest pojo, String newUniqueId) {
    try {
      if (pojo.isNotPush()) {
        return;
      }
      // 新设备
      Map<String, Object> queryCond = new HashMap<String, Object>();
      queryCond.put("deviceId", pojo.getDeviceId());
      List<AppBindingModel> appList =
          appBindingDao.queryList(queryCond, CommUtil.getAllField(AppBindingModel.class));
      AppBindingModel appBindingModel = new AppBindingModel();
      appBindingModel.setSystemType(pojo.getClientType());
      appBindingModel.setUserId(newUniqueId);
      appBindingModel.setStatus(UcenterModelConstant.LOGIN);// 登录状态
      if (null == appList || appList.size() <= 0) {// 第一次登录之时
        appBindingModel.setDeviceId(pojo.getDeviceId());
        appBindingModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        appBindingModel.setPushId(pojo.getRegistrationId());
        appBindingDao.addEntity(appBindingModel);
      } else if (null != appList && appList.size() > 0) {
        appBindingModel.setPushId(pojo.getRegistrationId());
        appBindingDao.updateEntity(queryCond, appBindingModel);// 有每次可能会变的参数
      }
    } catch (Exception e) {
      LoggerUtil.error("登录流程 更新绑定信息 异常！", e);
    }
  }

  public boolean updateInfo(UserBaseInfo pojo, UserModel entity) {
    // 更新连续登录天数
    Long loginDays = entity.getLoginDays();
    try {
      if (null == loginDays || loginDays == 0l) loginDays = 1l;
      int addDay =
          DateUtil.getDiffDays(entity.getTokenTime().toString(),
              new Timestamp(System.currentTimeMillis()).toString());
      if (addDay == 1) loginDays++;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    entity.setLoginDays(loginDays);
    // 更新token值和token生成时间
    // 获取旧TOKEN,更新前从redis删除
    String token = UUID.randomUUID().toString();
    entity.setToken(token);
    entity.setTokenTime(new Timestamp(System.currentTimeMillis()));
    entity.setLatestDeviceIp(pojo.getClientIp());
    entity.setUsedDeviceId(pojo.getDeviceId());
    entity.setPackageTag(pojo.getPackageTag());
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", entity.getId());
    if (1 == ucenterServiceFacade.updateUser(cond, entity)) {
      return true;
    } else {
      LoggerUtil.error("更新用戶信息失败", new RuntimeException());
      return false;
    }
  }


  public void toPushLoginOut(NewLoginRequest pojo, UserModel entity, String oldToken,
      String usedDeviceId) {
    try {
      if (pojo.isNotPush()) {
        return;
      }
      // 获取旧的第三方唯一标识（旧设备登录的最新用户）
      String oldUserId = StringUtils.EMPTY;
      // 查询设备id的最新登录用户（为了找出在此设备上登录的最新用户，此步查询不是多余的查询）
      UserModel usermodel = new UserModel();
      usermodel.setUsedDeviceId(usedDeviceId);
      // 确定发送方
      String oldStatus = StringUtils.EMPTY;
      String oldRegistrationId = StringUtils.EMPTY;
      String oldSystemType = StringUtils.EMPTY;
      Map<String, Object> oldqueryCond = new HashMap<String, Object>();
      oldqueryCond.put("deviceId", usedDeviceId);
      List<AppBindingModel> oldappList =
          appBindingDao.queryList(oldqueryCond, CommUtil.getAllField(AppBindingModel.class));
      if (CollectionUtils.isEmpty(oldappList)) {
        return;
      }
      oldRegistrationId = oldappList.get(0).getPushId();
      oldStatus = oldappList.get(0).getStatus();
      oldSystemType = oldappList.get(0).getSystemType();
      oldUserId = oldappList.get(0).getUserId();
      AppBindingModel oldAppBindingModel = new AppBindingModel();
      oldAppBindingModel.setStatus(UcenterModelConstant.LOGINOUT);// 退出登录状态
      appBindingDao.updateEntity(oldqueryCond, oldAppBindingModel);
      // 发送推送
      if (StringUtils.isNotBlank(usedDeviceId) && StringUtils.isNotBlank(oldUserId)
          && StringUtils.isNotBlank(oldRegistrationId)
          && !oldRegistrationId.equals(pojo.getRegistrationId())
          && !usedDeviceId.equals(pojo.getDeviceId())
          && oldUserId.equals(entity.getId().toString())
          && (UcenterModelConstant.LOGIN).equals(oldStatus)) {
        entity.setToken(oldToken);

        PushLoginOutDTO pushLoginOutDTO = new PushLoginOutDTO();
        pushLoginOutDTO.setEntity(entity);
        pushLoginOutDTO.setRegistrationId(oldRegistrationId);
        pushLoginOutDTO.setSystemType(oldSystemType);
        queueService.pushLoginOut(pushLoginOutDTO);
      } else {
        LoggerUtil
            .debug(String
                .format(
                    "oldDeviceId:%s-deviceId:%s,oldRegistrationId:%s,oldUserId:%s-userId:%s,oldStatus:%s,oldToken:%s",
                    usedDeviceId, pojo.getDeviceId(), oldRegistrationId, oldUserId, entity.getId()
                        .toString(), oldStatus, oldToken));
      }
    } catch (

    Exception e) {
      LoggerUtil.error("登录流程 推送顶人消息 异常！", e);
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
      queryCond.put("type", UcenterModelConstant.BLANK_NOTICE_UNSHOW);// 找出要展示的
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

  /**
   * 查询指定用户信息
   * 
   * @param pojo
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public UserInfoByTokenResponse queryUser(QueryUserRequest pojo) throws InstantiationException,
      IllegalAccessException {
    UserInfoByTokenResponse response = new UserInfoByTokenResponse(ResultType.SUCCESS);
    if (StringUtils.isBlank(pojo.getSessionToken())) {
      return new UserInfoByTokenResponse(UcenterResType.UnLoginUser);
    }
    UserModel user = new UserModel();
    user.setToken(pojo.getSessionToken());
    Map<String, Object> cond = this.getQueryCond(null, null, user);
    // 因为新需求的出现所以得加上获取moudelInfo的逻辑
    List<UserModel> list = ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
    if (null == list || list.size() == 0) {
      return new UserInfoByTokenResponse(UcenterResType.UnLoginUser);
    }
    user = new UserModel();
    user.setId(pojo.getUserId());
    cond = this.getQueryCond(null, null, user);
    // 因为新需求的出现所以得加上获取moudelInfo的逻辑
    list = ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
    if (null == list || list.size() == 0) {
      return new UserInfoByTokenResponse(UcenterResType.UnUserExisted);
    }

    StUserInfoResponse initFromUserModel =
        (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(list.get(0));
    initFromUserModel.setLatestDeviceIp(null);
    initFromUserModel.setToken(null);
    initFromUserModel.setTokenTime(null);
    initFromUserModel.setUsedDeviceId(null);
    response.setUser(initFromUserModel);
    return response;
  }

  /**
   * 修改个人资料service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public ModifyMyInfoResponse modifyUser(ModifyMyInfoRequest pojo, String headVersion)
      throws Exception {
    ModifyMyInfoResponse response = new ModifyMyInfoResponse(ResultType.SUCCESS);
    if (StringUtils.isNotBlank(pojo.getNickName())
        && UcenterModelConstant.NAME_TOURIST.equals(pojo.getNickName())) {
      return new ModifyMyInfoResponse(UcenterResType.IS_KEYWORD);
    }
    CheckLoginResult<UserModel> result = checkLoginWithUserModel(pojo);
    if (!result.isRetOk()) {
      return new ModifyMyInfoResponse(result.getResType());
    }
    UserModel user = result.getModel();
    if (StringUtils.isNotBlank(pojo.getMajor())) {
      if (!checkValidModifyUserInfo(pojo))
        return new ModifyMyInfoResponse(UcenterResType.NotFindMajor);
    }

    // 1.5.0以前的版本
    if (headVersion.equals("149")) {
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("module", UcenterModelConstant.MODULE);
      input.put("showStatus", 1);
      input.put("typeCode", pojo.getMajor());
      Map<String, Object> output = CommUtil.getAllField(ProductCategoryUtilModel.class);
      output.remove("majorInfo");
      List<ProductCategoryUtilModel> ls =
          stUcenterModelServiceFacade.listProductCategoryJoinModuleInfo(input, output);
      if (!CollectionUtils.isEmpty(ls)) {
        ProductCategoryUtilModel temp = ls.get(0);
        pojo.setRegion(temp.getModule());
        pojo.setRegionName(temp.getModuleName());
        pojo.setMajorId(temp.getId().toString());
        pojo.setMajorName(temp.getName());
      }
    }
    pojo.initModifyInfo(user, headVersion);// 设置用户信息

    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", user.getId());
    // 完善资料数据存在不为空时，触发消息
    if (StringUtils.isOrNotBlank(pojo.getPortrait(), pojo.getNickName(), pojo.getSign())) {
      sendImproveEvent(pojo, user);
    }
    Integer updateFlag = ucenterServiceFacade.updateUser(cond, user);
    if (null != updateFlag && updateFlag == 1) {
      // 删除之前的缓存
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
      // 添加最新的用户信息到缓存中
      UserInfoByTokenCache.addUserInfoToCache(
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(user),
          pojo.getSessionToken());
    } else {
      return new ModifyMyInfoResponse(UcenterResType.UserUpdateFail);
    }
    return response;
  }

  private void sendImproveEvent(ModifyMyInfoRequest pojo, UserModel user) {
    ImproveInfoEvent event = EventBuilder.buildImproveInfoEvent();
    event.setUserId(user.getId().toString());
    event.setModule(UcenterConstant.COMMON_MODULE);
    event.setImproveNickname(StringUtils.isNotBlank(pojo.getNickName()));
    event.setImproveProtrait(StringUtils.isNotBlank(pojo.getPortrait()));
    event.setImproveSign(StringUtils.isNotBlank(pojo.getSign()));
    event.send();
  }


  /**
   *  获取赞列表service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public PraiseListResponse praiseList(PraiseListRequest pojo) throws Exception {
    try {
      PraiseListResponse response = new PraiseListResponse(ResultType.SUCCESS);
      List<UserPraiseInfo> praiseList = Lists.newArrayList();
      CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) return new PraiseListResponse(result.getResType());
      BaseUserModel userModel = result.getModel();
      // 获取点赞用户列表
      Map<String, Object> input = new HashMap<String, Object>();
      Map<String, Object> output = CommUtil.getAllField(UserPraiseVo.class);
      switch (pojo.getType()) {// 类型 1、谁点赞过我
        case "1":
          input.put("targetUserId", userModel.getId());
          break;
        case "2":// 2、我点赞过谁 
          input.put("userId", userModel.getId());
          break;
      }
      Page<UserPraiseVo> userPraiseListPage =
          ucenterServiceFacade.queryUserListByPraise(input, output, null);
      if (null != userPraiseListPage && null != userPraiseListPage.getResult()) {
        for (UserPraiseVo userPraiseVo : userPraiseListPage.getResult()) {
          UserPraiseInfo userPraiseInfo = new UserPraiseInfo();
          StUserInfoResponse userInfo = new StUserInfoResponse();
          if (null != userPraiseVo.getModuleInfo())
            userInfo.initModuleInfo(userPraiseVo.getModuleInfo());
          userPraiseInfo.setGender(userPraiseVo.getGender());
          userPraiseInfo.setNickName(userPraiseVo.getNickName());
          userPraiseInfo.setPortrait(userPraiseVo.getPortrait());
          if (null != userInfo.getSign()) userPraiseInfo.setSign(userInfo.getSign());
          if (null != userPraiseVo.getTimestamp())
            userPraiseInfo.setTimestamp(DateUtil.relativeDateFormat(userPraiseVo.getTimestamp()));
          userPraiseInfo.setUserId(userPraiseVo.getUserId());
          praiseList.add(userPraiseInfo);
        }
      }
      response.setPraiseList(praiseList);
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   *  点赞service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo praise(UserPraiseRequest pojo) throws Exception {
    try {
      BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
      CheckLoginResult<BaseUserModel> result = checkLoginWithBaseUserModel(pojo);
      if (!result.isRetOk()) return new BaseResultInfo(result.getResType());
      BaseUserModel user = result.getModel();
      UserModel targetUser = new UserModel();
      targetUser.setId(Long.valueOf(pojo.getTargetUserId()));
      Map<String, Object> queryCond = this.getQueryCond(null, null, targetUser);
      List<UserModel> targetList =
          ucenterServiceFacade.queryUserList(queryCond, UserModelProperty.getAllInfo());
      if (null == targetList || targetList.size() < 1) {
        return new BaseResultInfo(UcenterResType.TargetUserMissed);
      }
      UserPraiseModel userPraiseModel =
          ucenterServiceFacade.queryUserPraiseRelate(user.getId(), pojo.getTargetUserId());
      if (null != userPraiseModel) return new BaseResultInfo(UcenterResType.HasPraised);
      UserPraiseModel model = new UserPraiseModel();
      model.setModule(pojo.getRegion());
      model.setUserId(user.getId());
      model.setTargetUserId(pojo.getTargetUserId());
      sendFollowEvent(model);
      sendBeFollowedEvent(model);
      ucenterServiceFacade.insertUserPraise(model);
      // 更改被点赞数
      List<UserPraiseModel> praiseL =
          ucenterServiceFacade.queryUserPraiseList(pojo.getTargetUserId());
      Map<String, Object> input = Maps.newHashMap();
      input.put("id", pojo.getTargetUserId());
      targetUser = new UserModel();
      targetUser.setBePraiseNum(praiseL.size());
      ucenterServiceFacade.updateUser(input, targetUser);
      return response;
    } catch (Exception e) {
      throw e;
    }
  }

  private void sendBeFollowedEvent(UserPraiseModel model) {
    FollowEvent event = EventBuilder.buildFollowEvent();
    event.setModule(model.getModule());
    event.setUserId(model.getTargetUserId());
    event.setTargetUserId(model.getUserId());
    event.send();
  }

  private void sendFollowEvent(UserPraiseModel model) {
    BeFollowedEvent event = EventBuilder.buildBeFollowedEvent();
    event.setModule(model.getModule());
    event.setUserId(model.getUserId());
    event.setTargetUserId(model.getTargetUserId());
    event.send();
  }

  /**
   *  排行榜service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo rankList(UserRankRequest pojo) throws Exception {
    UserRankResponse response = new UserRankResponse(ResultType.SUCCESS);
    try {
      if (StringUtils.isBlank(pojo.getSessionToken())) {
        return new UserRankResponse(UcenterResType.UnLoginUser);
      }
      // 个人用户资料
      UserModel user = new UserModel();
      user.setToken(pojo.getSessionToken());
      List<UserModel> list =
          ucenterServiceFacade.queryUserList(this.getQueryCond(null, null, user),
              UserModelProperty.getAllInfo());
      if (null == list || list.isEmpty() || list.size() > 1) {
        return new UserRankResponse(UcenterResType.UnLoginUser);
      }
      // 个人信息逻辑
      UserModel entity = list.get(0);
      // 排行榜
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("userId", entity.getId().toString());
      Map<String, Object> output = CommUtil.getAllField(RankUserInfo.class);
      List<RankUserInfo> rankInfoList = Lists.newArrayList();
      switch (pojo.getType()) {
        case "2":// 2 学霸（按积分数）
          // 查询积分排行列表
          Page<RankUserInfo> creditRankListPage =
              ucenterServiceFacade.queryCreditListByCond(input, output, null);
          if (null == creditRankListPage || null == creditRankListPage.getResult()
              || creditRankListPage.getResult().isEmpty()) {
            return new UserRankResponse(UcenterResType.USER_CREDIT_RANK_IS_NULL);
          }
          AtomicInteger i = new AtomicInteger(0);
          for (RankUserInfo rankInfo : creditRankListPage.getResult()) {
            // 此处判断，正常情况应该不会进入
            if (rankInfo == null || StringUtils.isEmpty(rankInfo.getUserId())) continue;
            rankInfo.setRank(String.valueOf(i.incrementAndGet()));
            // 我的信息
            if (StringUtils.isNotBlank(rankInfo.getUserId())
                && rankInfo.getUserId().equals(entity.getId().toString())) {
              response.setMyRankInfo(rankInfo);
            }
            if (i.get() <= 50) {
              rankInfoList.add(rankInfo);
            } else if (StringUtils.isNotBlank(response.getMyRankInfo().getUserId())) {
              break;
            }
          }
          break;
        default:
          break;
      }
      response.setRankInfoList(rankInfoList);
      return response;
    } catch (Exception e) {
      LoggerUtil.error("rankList", e);
      return new UserRankResponse(ResultType.SYSFAIL);
    }
  }

  public UserListResponse userList(UserListRequest pojo) throws Exception {
    UserListResponse response = new UserListResponse(ResultType.SUCCESS);
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("idList", pojo.getUserIdList());
    List<UserModel> userList =
        ucenterServiceFacade.queryUserList(queryCond, UserModelProperty.getBaseInfo());
    if (null == userList || userList.size() <= 0) {
      return new UserListResponse(ResultType.SYSFAIL);
    }
    for (UserModel model : userList) {
      StUserInfoResponse baseUserModel =
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(model);
      response.getUserModelList().add(CommUtil.getShowField(baseUserModel));
    }
    return response;
  }

  public UnreadMessageResponse unReadMesCount(BaseRequest pojo) throws Exception {
    UnreadMessageResponse response = new UnreadMessageResponse(ResultType.SUCCESS);
    // 1.用关联表和公告表做关联，查询数据
    BaseUserModel model = this.decideRedisExsited(pojo);
    if (null == model || StringUtils.isBlank(model.getId()))
      return new UnreadMessageResponse(UcenterResType.UnLoginUser);
    if (!checkUserStatus(model.getUsedDeviceId(), model.getId()))
      return new UnreadMessageResponse(UcenterResType.UnLoginUser);
    String userId = model.getId();
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", userId);
    Integer relateNoticeCount = 0;
    // （mybatis的sql设计问题需要改进）
    relateNoticeCount = ucenterServiceFacade.countRelateNoticeByUid(queryCond);
    if (0 == relateNoticeCount) {
      List<UserRelateNoticeModel> uNoticeList =
          ucenterServiceFacade.queryRelateNoticeList(queryCond,
              UserRelateNoticeProperty.getAllInfo());
      if (null != uNoticeList) relateNoticeCount = uNoticeList.size();
    } else {
      queryCond.put("status", UcenterConstant.BLANK_NOTICE_UNREAD);
      relateNoticeCount = ucenterServiceFacade.countRelateNotice(queryCond);
    }
    response.setNoticeCount(relateNoticeCount);
    response.setSysMesCount(asyncMessageService.countUSysmes(model));
    return response;
  }

  /**
   * 
   * 导入用户注册方法
   * 
   * @param pojo
   * @return
   */
  public ImportMemberResponse importMember(StOfficialInfoRegist pojo) {
    ImportMemberResponse response = new ImportMemberResponse(ResultType.SUCCESS);
    try {
      // 1、校验手机号
      if (!VerifyUtil.isMobile(pojo.getPhoneNum())) {
        return new ImportMemberResponse(UcenterResType.PhoneNumError);
      }
      BaseUcenterResponse baseResponse =
          ucenterHttpService.register(pojo.getPassword(), pojo.getPhoneNum(), pojo.getRegion());
      if (null == baseResponse) new ImportMemberResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        response.setRetcode(baseResponse.getRetcode());
        response.setRetdesc(baseResponse.getRetdesc());
        return response;
      }
      // 3、校验通过，注册，插入数据库记录
      UserModel model = new UserModel();
      model.setXdUniqueId(baseResponse.getXdUniqueId());
      pojo.setRegistOfficialInfo(model);
      model = ucenterServiceFacade.insertOfficialUser(model);// 插入官方用户信息
      // queueService.registIMAccount(model);
      com.xiaodou.wallet.agent.response.ResponseBase walletResponse =
          WalletService.createAccount(WalletUtil.formatProductLine(), model.getId().toString(),
              Lists.newArrayList(UcenterConstant.BUSINESS_TYPE_ZKJ));
      if (!walletResponse.isRetOk() && !"40001".equals(walletResponse.getRetcode())) {
        throw new RuntimeException(walletResponse.getRetdesc());
      }
      response.setUserId(model.getId());
      return response;
    } catch (Exception e) {
      LoggerUtil.alarmInfo(new UCenterExecuteExceptionAlarm(UcenterAlarmEnum.IMPORT_USER_FAIL,
          FastJsonUtil.toJson(pojo)));
      LoggerUtil.error("service层：用户注册异常", e);
      return new ImportMemberResponse(ResultType.SYSFAIL);
    }
  }

  /**
   * 修改个人资料下的准考证号service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public BaseResultInfo modifyAdmissionCardCode(AdmissionCardCodeRequestDTO pojo) {
    ModifyMyInfoResponse response = new ModifyMyInfoResponse(ResultType.SUCCESS);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", pojo.getUserId());
    List<UserModel> list = ucenterServiceFacade.queryUserList(cond, UserModelProperty.getAllInfo());
    if (null == list || list.isEmpty()) {
      return new BaseResultInfo(UcenterResType.UnUserExisted);
    }
    UserModel user = list.get(0);
    // 设置OfficialInfo信息
    if (StringUtils.isJsonNotBlank(pojo.getAdmissionCardCode())) {
      Map<String, String> officialInfoMap =
          FastJsonUtil.fromJsons(user.getOfficialInfo(),
              new TypeReference<Map<String, String>>() {});
      officialInfoMap.put("admissionCardCode", pojo.getAdmissionCardCode());
      user.setOfficialInfo(FastJsonUtil.toJson(officialInfoMap));
    }

    Integer updateFlag = ucenterServiceFacade.updateUser(cond, user);
    if (null == updateFlag || updateFlag != 1) {
      return new BaseResultInfo(UcenterResType.UserUpdateFail);
    }
    return response;
  }


  public FirstLoginTimeResponse getUserFirstLoginTime(FirstLoginTimeRequest pojo) {
    FirstLoginTimeResponse response = new FirstLoginTimeResponse(ResultType.SUCCESS);
    Map<String, Object> queryCond = new HashMap<String, Object>();
    queryCond.put("userId", pojo.getUserId());
    // queryCond.put("module", pojo.getModule());
    List<AppBindingModel> appList =
        appBindingDao.queryList(queryCond, CommUtil.getAllField(AppBindingModel.class));
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
   * 查询指定用户组的信息
   * 
   * @param pojo
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public QueryUserListResponse queryUserList(QueryUserListRequest pojo) throws Exception {
    QueryUserListResponse response = new QueryUserListResponse(ResultType.SUCCESS);
    Map<String, Object> cond = Maps.newHashMap();
    // List<String> userNameList = Lists.newArrayList(pojo.getUserNameList().split(","));
    cond.put("idList", pojo.getUserIdList());
    // cond.put("module", ModuleMappingWrapper.getWrapper().getModule().getCode());
    List<UserModel> list =
        ucenterServiceFacade.queryUserList(cond, CommUtil.getAllField(UserModel.class));
    if (null == list || list.size() < 1) {
      return new QueryUserListResponse(UcenterResType.TargetUserMissed);
    }
    List<UserModelResponse> userList = Lists.newArrayList();
    for (UserModel user : list) {
      StUserInfoResponse initFromUserModel =
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(user);
      initFromUserModel.setLatestDeviceIp(null);
      initFromUserModel.setToken(null);
      initFromUserModel.setTokenTime(null);
      initFromUserModel.setUsedDeviceId(null);
      userList.add(initFromUserModel);
    }
    response.setUserList(userList);
    return response;
  }

  /**
   * 查询缓存中异常消息
   * 
   * @return
   */
  public AlarmRecordModel getAlarmRecordCache(UserModel model) {
    AlarmRecordModel loginAlarmRecordModel =
        UserLoginInfoCache.getAlarmRecordCache(model.getId().toString());
    if (null == loginAlarmRecordModel)
      loginAlarmRecordModel = UserLoginInfoCache.getAlarmRecordCache(model.getUsedDeviceId());
    return loginAlarmRecordModel;
  }

  /**
   * 保存登录信息
   * 
   * @return
   */
  public void saveUserLoginInfo(NewLoginRequest request, UserModel model) {
    // 1、保存登录日志 并发送消息
    LoginInfoModel lastLoginInfoModel =
        userLoginInfoService.saveUserLoginInfo(request, model.getId());
    // 2、查询 报警异常
    List<PretreatmentDTO> list = userLoginInfoService.checkUserLoginInfo(lastLoginInfoModel);
    // 3、保存异常记录 并发消息
    if (null == list || list.isEmpty()) return;
    for (PretreatmentDTO pdto : list) {
      if (null == pdto) continue;
      AlarmRecordModel alarmRecord = new AlarmRecordModel();
      alarmRecord.setUserId(model.getId());
      alarmRecord.setDeviceId(model.getUsedDeviceId());
      alarmRecord.setLoginInfoId(lastLoginInfoModel.getId());
      alarmRecord.setAlarmLevel(pdto.getAlarmTypeEnum().getAlarmLevelEnum());
      alarmRecord.setAlarmType(pdto.getAlarmTypeEnum());
      alarmRecord.setPretreatment(pdto.getAlarmTypeEnum().getPretreatmentEnum());
      alarmRecord.setStatus(StatusEnum.Status_1);
      alarmRecord.setAlarmTime(new Timestamp(System.currentTimeMillis()));
      alarmRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
      userLoginInfoService.saveAlarmRecord(alarmRecord);
    }

  }

  @SuppressWarnings("unused")
  private void cacheAlarmRecordCache(PretreatmentEnum pre, AlarmRecordModel alarmRecord,
      String userId, String deviceId) {
    switch (pre) {
      case AccountDeviceClosure:
        UserLoginInfoCache.saveAlarmRecordCache(userId, alarmRecord);
        UserLoginInfoCache.saveAlarmRecordCache(deviceId, alarmRecord);
        break;
      case AccountClosure:
        UserLoginInfoCache.saveAlarmRecordCache(userId, alarmRecord);
        break;
      case DeviceClosure:
        UserLoginInfoCache.saveAlarmRecordCache(deviceId, alarmRecord);
        break;
      default:
        break;
    }
  }

  public BaseResultInfo UploadBenchmarkFaceRequest(UploadBenchmarkFaceRequest pojo)
      throws Exception {
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    CheckLoginResult<UserModel> result = checkLoginWithUserModel(pojo);
    if (!result.isRetOk()) {
      return new BaseResultInfo(result.getResType());
    }
    UserModel user = result.getModel();
    if (UcenterModelConstant.USER_TYPE_IMPORT != user.getUserType()) {
      return new BaseResultInfo(UcenterResType.NotImportUser);
    }
    String uniqueId = user.getXdUniqueId();
    List<com.xiaodou.userCenter.response.ucenterHttp.BaseUserInfo> baseUserList =
        ucenterHttpService.findUser(Lists.newArrayList(uniqueId));
    if (null == baseUserList || baseUserList.isEmpty()) {
      return new BaseResultInfo(UcenterResType.NoFoundUser);
    }
    com.xiaodou.userCenter.response.ucenterHttp.BaseUserInfo baseUser = baseUserList.get(0);
    FaceIdResponse benchFaceRes = faceRecognitionApi.addFace(pojo.getBenchmarkFaceUrl());
    if (!RetCode.Success.getCode().equals(benchFaceRes.getRetCode())) {
      return new BaseResultInfo(UcenterResType.UnFindFace);
    }
    StOfficialInfo officialInfo = new StOfficialInfo();
    String sOfficialInfo = user.getOfficialInfo();
    if (StringUtils.isJsonNotBlank(sOfficialInfo))
      officialInfo = FastJsonUtil.fromJson(sOfficialInfo, StOfficialInfo.class);
    officialInfo.setBenchmarkFace(benchFaceRes.getFaceId());
    officialInfo.setOfficialStatus(UcenterModelConstant.OFFICIALINFO_BENCHMARKFACE);
    user.setOfficialInfo(FastJsonUtil.toJson(officialInfo));
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", user.getId());
    Integer updateFlag = ucenterServiceFacade.updateUserOfficialInfo(cond, user);
    if (null != updateFlag && updateFlag == 1) {
      // 删除之前的缓存
      UserInfoByTokenCache.deleteUserInfoFromCache(pojo.getSessionToken());
      // 添加最新的用户信息到缓存中
      UserInfoByTokenCache.addUserInfoToCache(
          (StUserInfoResponse) new StUserInfoResponse().initFromUserModel(user),
          pojo.getSessionToken());
      UpdateSourceFaceEvent event = new UpdateSourceFaceEvent();
      TransferStudentData model = new TransferStudentData();
      model.setModule(baseUser.getModule());
      model.setUserId(user.getId().toString());
      model.setSourcePortrait(pojo.getBenchmarkFaceUrl());
      model.setClientType(pojo.getClientType());
      event.setModule(pojo.getRegion());
      event.setDataModel(model);
      event.send();
    } else {
      return new ModifyMyInfoResponse(UcenterResType.UserUpdateFail);
    }
    return response;
  }


  /**
   * 
   * 合作用户，修改初始密码接口
   * 
   * @param pojo
   * @return
   */
  public FindPasswordResponse modifyDefaultPassword(ModifyDefaultPassword pojo) {
    FindPasswordResponse result = new FindPasswordResponse(ResultType.SUCCESS);
    try {
      if (!PhoneUtil.validatePhone(pojo.getPhoneNum()))
        return new FindPasswordResponse(UcenterResType.PhoneNumError);
      if (!VerifyUtil.isNUMSTR(pojo.getPassword()))
        return new FindPasswordResponse(UcenterResType.PwdError);
      if (!"ON".equals(ConfigProp.getParams("check.code.mock"))) {
        // 3、校验验证码
        if (!PhoneUtil.validateNumber(pojo.getCheckCode(), 4))
          return new FindPasswordResponse(UcenterResType.checkCodeError);
        CheckCodeModel codeFromCache =
            CheckCodeCache.getCheckCodeFromCache(CheckCodeTypeEnum.OfficialPassword.getCode(),
                pojo.getPhoneNum());
        if (null == codeFromCache) {
          return new FindPasswordResponse(UcenterResType.CheckCodeOutDate);
        }
        if (!pojo.getCheckCode().equals(codeFromCache.getCheckCode())) {
          return new FindPasswordResponse(UcenterResType.CheckCodeError);
        }
      }
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("token", pojo.getSessionToken());
      UserModel queryModel =
          ucenterServiceFacade.queryUser(cond, CommUtil.getAllField(UserModel.class));
      if (null == queryModel) return new FindPasswordResponse(UcenterResType.NoFoundUser);
      ResponseBase baseResponse =
          ucenterHttpService.modifyDefaultPassword(queryModel.getXdUniqueId(), pojo.getPassword());
      if (null == baseResponse) return new FindPasswordResponse(ResultType.SYSFAIL);
      if (!baseResponse.isRetOk()) {
        result.setRetcode(baseResponse.getRetcode());
        result.setRetdesc(baseResponse.getRetdesc());
        return result;
      }
      StOfficialInfo officialInfo = new StOfficialInfo();
      String sOfficialInfo = queryModel.getOfficialInfo();
      if (StringUtils.isJsonNotBlank(sOfficialInfo))
        officialInfo = FastJsonUtil.fromJson(sOfficialInfo, StOfficialInfo.class);
      officialInfo.setOfficialStatus(UcenterModelConstant.OFFICIALINFO_PASSWORD);
      queryModel.setOfficialInfo(FastJsonUtil.toJson(officialInfo));
      if (1 != ucenterServiceFacade.updateUser(cond, queryModel)) {
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

  public CreditOperationListResponse creditOperationList(CreditOperationListRequest pojo) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("token", pojo.getSessionToken());
    UserModel queryModel =
        ucenterServiceFacade.queryUser(cond, CommUtil.getAllField(UserModel.class));
    if (null == queryModel) return new CreditOperationListResponse(UcenterResType.NoFoundUser);
    CreditOperationListResponse response = new CreditOperationListResponse(ResultType.SUCCESS);
    Page<CreditChangeLog> page = new Page<>(pojo.getSize());
    IQueryParam param = new QueryParam();
    if (StringUtils.isNotBlank(pojo.getIdUpper())) {
      param.addInput("idUpper", pojo.getIdUpper());
    }
    param.addInput("userId", queryModel.getId());
    param.addOutputs(CommUtil.getAllField(CreditChangeLog.class));
    param.addSort("id", Sort.DESC);
    page = ucenterServiceFacade.queryCreditChangeLog(param, page);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
      for (CreditChangeLog creditChangeLog : page.getResult()) {
        CreditOperation operation = new CreditOperation(creditChangeLog);
        if (null != operation && StringUtils.isNotBlank(operation.getOperationId())) {
          response.getCreditOperationList().add(operation);
        }
      }
    }
    return response;
  }
}
