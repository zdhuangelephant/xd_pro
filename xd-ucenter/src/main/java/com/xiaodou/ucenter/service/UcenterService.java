package com.xiaodou.ucenter.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.constant.UcenterModelConstant;
import com.xiaodou.ucenter.domain.model.BaseUserDO;
import com.xiaodou.ucenter.domain.request.FindAllUserRequest;
import com.xiaodou.ucenter.domain.request.FindPasswordRequest;
import com.xiaodou.ucenter.domain.request.FindUserRequest;
import com.xiaodou.ucenter.domain.request.LoginRequest;
import com.xiaodou.ucenter.domain.request.ModifyDefaultPwdRequest;
import com.xiaodou.ucenter.domain.request.ModifyPwdRequest;
import com.xiaodou.ucenter.domain.request.RegistRequest;
import com.xiaodou.ucenter.domain.request.UserByTelAndModRequest;
import com.xiaodou.ucenter.domain.response.BaseResultInfo;
import com.xiaodou.ucenter.domain.response.BaseUserInfo;
import com.xiaodou.ucenter.domain.response.BaseUserListResposne;
import com.xiaodou.ucenter.domain.response.FindPasswordResponse;
import com.xiaodou.ucenter.domain.response.LoginResponse;
import com.xiaodou.ucenter.domain.response.ModifyPwdResponse;
import com.xiaodou.ucenter.domain.response.RegistResponse;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;
import com.xiaodou.ucenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.ucenter.util.UcenterUtil;
import com.xiaodou.ucenter.util.VerifyUtil;

/**
 * 
 * @name UcenterService CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @description TODO
 * @version 1.0
 */
@Service("ucenterService")
public class UcenterService extends AbstractUserService {

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;

  /**
   * 
   * 用户注册方法
   * 
   * @param pojo
   * @return
   */
  public RegistResponse registerAccount(RegistRequest pojo) {
    RegistResponse response = new RegistResponse(ResultType.SUCCESS);
    try {
      // 可能會出现，非手机账号的注册（保留方法，在入参中限制）
      switch (pojo.getPlatform()) {
        case UcenterModelConstant.PLATFORM_TELEPHONE:
          response = this.telRegist(pojo);
          break;
        case UcenterModelConstant.PLATFORM_QQ:
        case UcenterModelConstant.PLATFORM_WEIBO:
        case UcenterModelConstant.PLATFORM_WEIXIN:
        case UcenterModelConstant.PLATFORM_TOURIST:
          response = this.thirdRegist(pojo);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      LoggerUtil.error("service层：用户注册异常", e);
    }
    return response;
  }

  /**
   * 
   * @description 手机号注册
   * @author 李德洪
   * @Date 2017年4月25日
   * @param pojo
   * @return
   */
  public RegistResponse telRegist(RegistRequest pojo) {
    RegistResponse response = new RegistResponse(ResultType.SUCCESS);
    // 1、校验手机号
    if (!VerifyUtil.isMobile(pojo.getTelephone())) {
      return new RegistResponse(UcenterResType.PhoneNumError);
    }
    // 1.1、注册验证密码
    if (!VerifyUtil.isNUMSTR(pojo.getPassword()))
      return new RegistResponse(UcenterResType.PwdError);
    // 2、先去数据库查询xd_user
    List<BaseUserDO> queryModelList =
        super.getUserByTel(pojo.getTelephone());
    if (!queryModelList.isEmpty()) {
      return new RegistResponse(UcenterResType.HasRegisterd);
    }
    // 3、校验通过，注册，插入数据库记录
    BaseUserDO userModel = pojo.setTelRegistUserInfo();
    if (null != ucenterServiceFacade.saveUser(userModel))
      response.setXdUniqueId(userModel.getXdUniqueId());// 插入用户信息
    return response;
  }

  /**
   * 
   * @description 三方+游客注册
   * @author 李德洪
   * @Date 2017年4月25日
   * @param pojo
   * @return
   */

  public RegistResponse thirdRegist(RegistRequest pojo) {
    RegistResponse response = new RegistResponse(ResultType.SUCCESS);
    BaseUserDO userModel = pojo.setThirdRegistUserInfo();
    if (null != ucenterServiceFacade.saveUser(userModel))
      response.setXdUniqueId(userModel.getXdUniqueId());// 插入用户信息
    return response;
  }

  public LoginResponse login(LoginRequest pojo) {
    LoginResponse response = new LoginResponse(ResultType.SUCCESS);
    try {
      // 1、先去数据库查询
      Map<String, Object> input = pojo.getQueryModelCond();
      List<BaseUserDO> queryModelList =
          ucenterServiceFacade.listUser(input, CommUtil.getAllField(BaseUserDO.class));
      BaseUserDO queryModel = null;
      if (CollectionUtils.isEmpty(queryModelList)) {
        if (!pojo.canRegist()) return new LoginResponse(UcenterResType.NoFoundUser);
        // 2、注册，插入数据库记录
        queryModel = new BaseUserDO();
        pojo.setRegistUserInfo(queryModel);
        ucenterServiceFacade.saveUser(queryModel);
      } else {
        queryModel = queryModelList.get(0);
        if (!pojo.checkPassword(queryModel)) {
          return new LoginResponse(UcenterResType.PasswordError);
        }
      }
      response.setXdUniqueId(queryModel.getXdUniqueId());
    } catch (Exception e) {
      LoggerUtil.error("service层：" + "用户第三方登录接口异常", e);
    }
    return response;
  }

  /**
   * 
   * 找回密码接口
   * 
   * @param pojo
   * @return
   */
  public FindPasswordResponse findPassword(FindPasswordRequest pojo) {
    FindPasswordResponse response = new FindPasswordResponse(ResultType.SUCCESS);
    try {
      if (!VerifyUtil.isMobile(pojo.getTelephone()))
        return new FindPasswordResponse(UcenterResType.PhoneNumError);
      if (!VerifyUtil.isNUMSTR(pojo.getPassword()))
        return new FindPasswordResponse(UcenterResType.PwdError);
      List<BaseUserDO> queryModelList =
          super.getUserByTel(pojo.getTelephone());
      if (null == queryModelList || queryModelList.isEmpty())
        return new FindPasswordResponse(UcenterResType.NoFoundUser);
      BaseUserDO queryModel = queryModelList.get(0);
      if (null == queryModel) return new FindPasswordResponse(UcenterResType.NoFoundUser);
      String salt = queryModel.getSalt();
      String password = UcenterUtil.getPasswd(pojo.getPassword() + salt);
      if (password.equals(queryModel.getPassword()))
        return new FindPasswordResponse(UcenterResType.PwdFit);
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("telephone", queryModel.getTelephone());
      BaseUserDO updateModel = new BaseUserDO();
      updateModel.setPassword(password);
      if (!ucenterServiceFacade.updateUser(cond, updateModel)) {
        return new FindPasswordResponse(UcenterResType.UpdatePasswordFail);
      }
    } catch (Exception e) {
      LoggerUtil.error("service层：用户注册异常", e);
    }
    return response;
  }


  /**
   * 修改用户密码service
   * 
   * @param pojo
   * @return
   */
  public ModifyPwdResponse modifyPassword(ModifyPwdRequest pojo) {
    ModifyPwdResponse response = new ModifyPwdResponse(ResultType.SUCCESS);
    try {
      if (!VerifyUtil.isNUMSTR(pojo.getOldPwd()) || !VerifyUtil.isNUMSTR(pojo.getNewPwd())
          || !VerifyUtil.isNUMSTR(pojo.getConfirmNewPwd()))
        return new ModifyPwdResponse(UcenterResType.PwdError);
      if (!pojo.getConfirmNewPwd().trim().equals(pojo.getNewPwd().trim()))
        return new ModifyPwdResponse(UcenterResType.TwicePwdError);
      List<BaseUserDO> queryModelList = super.getUserByXid(pojo.getXdUniqueId());
      if (null == queryModelList || queryModelList.isEmpty())
        return new ModifyPwdResponse(UcenterResType.NoFoundUser);
      BaseUserDO queryModel = queryModelList.get(0);
      if (null == queryModel) return new ModifyPwdResponse(UcenterResType.NoFoundUser);
      String oldPwd = UcenterUtil.getPasswd(pojo.getOldPwd() + queryModel.getSalt());
      if (!oldPwd.equals(queryModel.getPassword()))
        return new ModifyPwdResponse(UcenterResType.OldPwdError);
      String newPwd = UcenterUtil.getPasswd(pojo.getConfirmNewPwd() + queryModel.getSalt());
      BaseUserDO updateModel = new BaseUserDO();
      updateModel.setPassword(newPwd);
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("xdUniqueId", pojo.getXdUniqueId());
      if (!ucenterServiceFacade.updateUser(cond, updateModel)) {
        return new ModifyPwdResponse(UcenterResType.UpdatePasswordFail);
      }
    } catch (Exception e) {
      LoggerUtil.error("service层：用户注册异常", e);
    }
    return response;
  }

  public BaseUserListResposne findUser(FindUserRequest pojo) {
    BaseUserListResposne response = new BaseUserListResposne(ResultType.SUCCESS);
    List<BaseUserDO> queryModelList = super.listUserByXids(pojo.getXdUniqueIdList());
    if (CollectionUtils.isEmpty(queryModelList)) {
        return new BaseUserListResposne(UcenterResType.NoFoundUser);
    }
    List<BaseUserInfo> userBaseInfoList = Lists.newArrayList();
    for (BaseUserDO userModel : queryModelList) {
      userBaseInfoList.add(BaseUserInfo.init(userModel));
    }
    response.setUserBaseInfoList(userBaseInfoList);
    return response;
  }



  /**
   * 合作用户，修改初始密码接口
   * 
   * @param pojo
   * @return
   */
  public ModifyPwdResponse modifyDefaultPassword(ModifyDefaultPwdRequest pojo) {
    ModifyPwdResponse response = new ModifyPwdResponse(ResultType.SUCCESS);
    try {
      if (!VerifyUtil.isNUMSTR(pojo.getPassword()))
        return new ModifyPwdResponse(UcenterResType.PwdError);
      List<BaseUserDO> queryModelList = super.getUserByXid(pojo.getXdUniqueId());
      if (null == queryModelList || queryModelList.isEmpty())
        return new ModifyPwdResponse(UcenterResType.NoFoundUser);
      BaseUserDO queryModel = queryModelList.get(0);
      if (null == queryModel) return new ModifyPwdResponse(UcenterResType.NoFoundUser);
      String newPwd = UcenterUtil.getPasswd(pojo.getPassword() + queryModel.getSalt());
      if (newPwd.equals(queryModel.getPassword()))
        return new ModifyPwdResponse(UcenterResType.EqualDefaultPassword);
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("xdUniqueId", pojo.getXdUniqueId());
      BaseUserDO updateModel = new BaseUserDO();
      updateModel.setPassword(newPwd);
      if (!ucenterServiceFacade.updateUser(cond, updateModel)) {
        return new ModifyPwdResponse(UcenterResType.UpdatePasswordFail);
      }
    } catch (Exception e) {
      LoggerUtil.error("service层：合作用户，修改初始密码接口异常", e);
      return new ModifyPwdResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public BaseUserListResposne findAllUser(FindAllUserRequest pojo) {
    BaseUserListResposne response = new BaseUserListResposne(ResultType.SUCCESS);
    List<BaseUserDO> queryModelList = super.listAllUser();
    if (null == queryModelList || queryModelList.isEmpty())
      return new BaseUserListResposne(UcenterResType.NoFoundUser);
    List<BaseUserInfo> userBaseInfoList = Lists.newArrayList();
    for (BaseUserDO userModel : queryModelList) {
      userBaseInfoList.add(BaseUserInfo.init(userModel));
    }
    response.setUserBaseInfoList(userBaseInfoList);
    return response;
  }

  public BaseResultInfo getUserByTelAndMod(UserByTelAndModRequest pojo) {
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    try {
      // 1、校验手机号
      if (!VerifyUtil.isMobile(pojo.getTelephone())) {
        return new RegistResponse(UcenterResType.PhoneNumError);
      }
      List<BaseUserDO> listUser = super.getUserByTel(pojo.getTelephone());
      if (null == listUser || listUser.isEmpty()) {
        return new BaseResultInfo(UcenterResType.NoFoundUser);
      }
    } catch (Exception e) {
      LoggerUtil.error("getUserByTelAndMod", e);
    }
    return response;
  }

}
