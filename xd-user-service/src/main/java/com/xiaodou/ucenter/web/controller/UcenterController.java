package com.xiaodou.ucenter.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.ucenter.request.BaseRequest;
import com.xiaodou.ucenter.request.CheckCodeRequest;
import com.xiaodou.ucenter.request.CheckTokenRequest;
import com.xiaodou.ucenter.request.FindPwdRequest;
import com.xiaodou.ucenter.request.FuzzySeekingUserRequest;
import com.xiaodou.ucenter.request.LoginRequest;
import com.xiaodou.ucenter.request.ModifyPwdRequest;
import com.xiaodou.ucenter.request.ModifyUserRequest;
import com.xiaodou.ucenter.request.QueryUserRequest;
import com.xiaodou.ucenter.request.RegistUserRequest;
import com.xiaodou.ucenter.request.UserListRequest;
import com.xiaodou.ucenter.service.UcenterService;

@Controller("ucenterController")
@RequestMapping("/user")
public class UcenterController extends BaseController {

  @Resource
  UcenterService ucenterService;

  @RequestMapping("/check_token")
  @ResponseBody
  public String checkToken(CheckTokenRequest request) {
    return doMain(request, new IController<CheckTokenRequest>() {
      @Override
      public ResultInfo doService(CheckTokenRequest pojo) throws Exception {
        return ucenterService.checkToken(pojo);
      }
    });
  }

  /**
   * 用户注册接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/register_member")
  @ResponseBody
  public String registerMember(RegistUserRequest request) {
    return doMain(request, new IController<RegistUserRequest>() {
      @Override
      public ResultInfo doService(RegistUserRequest pojo) throws Exception {
        return ucenterService.registerAccount(pojo);
      }
    });
  }

  /**
   * 登录接口
   * 
   * @param
   */
  @RequestMapping("/login")
  @ResponseBody
  public String login(LoginRequest request) {
    return doMain(request, new IController<LoginRequest>() {
      @Override
      public ResultInfo doService(LoginRequest pojo) throws Exception {
        return ucenterService.login(pojo);
      }
    });
  }

  /**
   * 获取验证码接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/check_code")
  public @ResponseBody
  String checkCode(CheckCodeRequest request) throws Exception {
    return doMain(request, new IController<CheckCodeRequest>() {
      @Override
      public ResultInfo doService(CheckCodeRequest pojo) throws Exception {
        return ucenterService.getCheckCode(pojo);
      }
    });
  }

  /**
   * 用户找回密码接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/find_pwd")
  @ResponseBody
  public String findPwd(FindPwdRequest request) {
    return doMain(request, new IController<FindPwdRequest>() {
      @Override
      public ResultInfo doService(FindPwdRequest pojo) throws Exception {
        return ucenterService.findPassword(pojo);
      }
    });
  }

  /**
   * 用户修改密码接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/modify_pwd")
  @ResponseBody
  public String modifyPwd(ModifyPwdRequest request) {
    return doMain(request, new IController<ModifyPwdRequest>() {
      @Override
      public ResultInfo doService(ModifyPwdRequest pojo) throws Exception {
        return ucenterService.modifyPwd(pojo);
      }
    });
  }

  @RequestMapping("/user_list")
  @ResponseBody
  public String getUserList(UserListRequest request) {
    return doMain(request, new IController<UserListRequest>() {
      @Override
      public ResultInfo doService(UserListRequest pojo) throws Exception {
        return ucenterService.userList(pojo);
      }
    });
  }

  /**
   * 根据用户昵称模糊查询用户
   */
  @RequestMapping("/fuzzy_seeking_user")
  @ResponseBody
  public String fuzzySeekingUser(FuzzySeekingUserRequest request) {
    return doMain(request, new IController<FuzzySeekingUserRequest>() {
      @Override
      public ResultInfo doService(FuzzySeekingUserRequest pojo) throws Exception {
        return ucenterService.fuzzySeekingUser(pojo);
      }
    });
  }


  /**
   * 退出登录接口
   * 
   * @param token
   */
  @RequestMapping("/login_out")
  public @ResponseBody
  String loginOut(BaseRequest request) throws Exception {
    return doMain(request, new IController<BaseRequest>() {
      @Override
      public ResultInfo doService(BaseRequest pojo) throws Exception {
        return ucenterService.loginOut(pojo);
      }
    });
  }

  /**
   * 查询指定用户信息
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/query_user")
  @ResponseBody
  public String queryUser(QueryUserRequest request) throws Exception {
    return doMain(request, new IController<QueryUserRequest>() {
      @Override
      public ResultInfo doService(QueryUserRequest pojo) throws Exception {
        return ucenterService.queryUser(pojo);
      }
    });
  }

  /**
   * 个人资料修改接口
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/modify_my_info")
  @ResponseBody
  public String modifyMyInfo(ModifyUserRequest request) {
    return doMain(request, new IController<ModifyUserRequest>() {
      @Override
      public ResultInfo doService(ModifyUserRequest pojo) throws Exception {
        return ucenterService.modifyUser(pojo);
      }
    });
  }
}
