package com.xiaodou.server.mapi.web.controller.selfTaught;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.server.mapi.request.FriendAddRequest;
import com.xiaodou.server.mapi.request.FriendDelRequest;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.server.mapi.request.ucenter.CheckCodeRequest;
import com.xiaodou.server.mapi.request.ucenter.ConfigRequest;
import com.xiaodou.server.mapi.request.ucenter.CreditOperationListRequest;
import com.xiaodou.server.mapi.request.ucenter.FeedBackCategoryRequest;
import com.xiaodou.server.mapi.request.ucenter.FeedBackRequest;
import com.xiaodou.server.mapi.request.ucenter.FindPwdRequest;
import com.xiaodou.server.mapi.request.ucenter.ModifyDefaultPassword;
import com.xiaodou.server.mapi.request.ucenter.ModifyMyInfoRequest;
import com.xiaodou.server.mapi.request.ucenter.ModifyPwdRequest;
import com.xiaodou.server.mapi.request.ucenter.NewLoginRequest;
import com.xiaodou.server.mapi.request.ucenter.QueryListRequest;
import com.xiaodou.server.mapi.request.ucenter.QueryUserByNameRequest;
import com.xiaodou.server.mapi.request.ucenter.QueryUserRequest;
import com.xiaodou.server.mapi.request.ucenter.RechargeAmountRequest;
import com.xiaodou.server.mapi.request.ucenter.RechargeOrderRequest;
import com.xiaodou.server.mapi.request.ucenter.RegistAccountRequest;
import com.xiaodou.server.mapi.request.ucenter.ReportRequest;
import com.xiaodou.server.mapi.request.ucenter.SysMessageRequest;
import com.xiaodou.server.mapi.request.ucenter.UpTokenPojo;
import com.xiaodou.server.mapi.request.ucenter.UpdateBenchmarkFaceRequest;
import com.xiaodou.server.mapi.request.ucenter.UserNoticeRequest;
import com.xiaodou.server.mapi.request.ucenter.ValidateCodeRequest;
import com.xiaodou.server.mapi.service.ucenter.UserService;
import com.xiaodou.server.mapi.web.controller.BaseMapiController;

@Controller("ucenterContorller")
@RequestMapping("/selftaught")
public class UcenterContorller extends BaseMapiController {

  @Resource
  UserService userService;

  /**
   * 获取七牛上传token
   * 
   */
  @RequestMapping("/up_token")
  @ResponseBody
  public String getUpToken(UpTokenPojo request) {
    return userService.getUpToken(request).toString0();
  }

  /**
   * 校验token
   */
  @RequestMapping(value = "/check_token")
  @ResponseBody
  public String checkToken(MapiBaseRequest request) {
    return userService.checkToken(request).toString0();
  }

  /**
   * 校验token
   */
  @RequestMapping(value = "/register_im")
  @ResponseBody
  public String registerIM(MapiBaseRequest request) {
    return userService.registerIM(request).toString0();
  }



  /**
   * 校验验证码接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */

  @RequestMapping("/validate_code")
  @ResponseBody
  public String validateCode(ValidateCodeRequest request) {
    return userService.validateCode(request).toString0();
  }

  /**
   * 用户注册接口
   * 
   */
  @RequestMapping("/register_member")
  @ResponseBody
  public String registerMember(RegistAccountRequest request) {
    return userService.registerAccount(request).toString0();
  }

  /**
   * 用户登录
   * 
   */
  @ResponseBody
  @RequestMapping(value = "/login")
  public String login(NewLoginRequest request) {
    return userService.login(request).toString0();
  }

  /**
   * 获取验证码接口
   * 
   */
  @RequestMapping("/check_code")
  @ResponseBody
  public String checkCode(CheckCodeRequest request) throws Exception {
    return userService.getCheckCode(request).toString0();
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
    return userService.findPassword(request).toString0();
  }

  /**
   * 用户修改密码接口
   * 
   */
  @RequestMapping("/modify_pwd")
  @ResponseBody
  public String modifyPwd(ModifyPwdRequest request) {
    return userService.modifyPwd(request).toString0();
  }


  /**
   * 发起好友申请。
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("/list_friend")
  public String listFriend(MapiBaseRequest request) {
    return userService.listFriend(request).toString0();
  }

  /**
   * 发起好友申请。
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("/add_friend")
  public String addFriend(FriendAddRequest request) {
    return userService.addFriend(request).toString0();
  }

  /**
   * 删除好友。
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("/del_friend")
  public String delFriend(FriendDelRequest request) {
    return userService.delFriend(request).toString0();
  }

  /**
   * 公告接口
   * 
   */
  @RequestMapping("/notice")
  @ResponseBody
  public String notice(MapiBaseRequest request) throws Exception {
    return userService.notice(request).toString0();
  }

/*  @RequestMapping(value = "/get_show_notice")
  public ModelAndView showNotice(String noticeId) {
    // HttpServletRequestWrapper servletRequest =
    // (HttpServletRequestWrapper) ReqInfoWrapper.getWrapper().getReq();
    // // 未登录用户
    // String noticeId = servletRequest.getRequest().getParameter("noticeId");
    if (StringUtils.isBlank(noticeId)) {
      return new ModelAndView("/error");
    }
    return userService.getNoticeByNoticeId(noticeId);
  }*/
  
  @RequestMapping(value = "/get_show_notice")
  public ModelAndView getShowNotice(String noticeId) {
    if (StringUtils.isBlank(noticeId)) {
      return new ModelAndView("/error");
    }
    UserNoticeRequest request = new UserNoticeRequest();
    request.setNoticeId(noticeId);
    return userService.getNoticeByNoticeId(request);
  }
  

  @RequestMapping(value = "/show_notice")
  public ModelAndView showNotice(UserNoticeRequest pojo) {
    return userService.showNotice(pojo);
  }

  /**
   * 一键已读
   * 
   */
  @RequestMapping("/notice_to_read_all")
  @ResponseBody
  public String noticetoReadAll(MapiBaseRequest request) throws Exception {
    return userService.noticetoReadAll(request).toString0();
  }

  /**
   * 退出登录接口
   * 
   */
  @RequestMapping("/login_out")
  @ResponseBody
  public String loginOut(MapiBaseRequest request) throws Exception {
    return userService.loginOut(request).toString0();
  }

  /**
   * 个人资料修改接口
   * 
   */
  @RequestMapping("/modify_my_info")
  @ResponseBody
  public String modifyMyInfo(ModifyMyInfoRequest request) throws Exception {
    return userService.modifyMyInfo(request).toString0();
  }

  @RequestMapping("/version")
  @ResponseBody
  public String version(MapiBaseRequest request) {
    return userService.version(request).toString0();
  }


  @RequestMapping("/config")
  @ResponseBody
  public String config(ConfigRequest request) {
    return userService.config(request).toString0();
  }

  /**
   * 根据userId查找用户
   * 
   * @param request
   * @return
   */
  @RequestMapping("/query_user")
  @ResponseBody
  public String queryUser(QueryUserRequest request) {
    return userService.queryUser(request).toString0();
  }

  /**
   * 根据userName查找用户
   * 
   * @param request
   * @return
   */
  @RequestMapping("/query_user_by_name")
  @ResponseBody
  public String queryUserByUserName(QueryUserByNameRequest request) {
    return userService.queryUserByUserName(request).toString0();
  }

  /**
   * 获取用户各类未读消息数目
   * 
   * @param request
   * @return
   */
  // @RequestMapping("/my_message_count")
  // @ResponseBody
  // public String getMesCount( MapiBaseRequest request) {
  // return userService.getMesCount(request).toString0();
  // }

  /**
   * 举报
   * 
   * @param request
   * @return
   */
  @RequestMapping("/report")
  @ResponseBody
  public String report(ReportRequest request) {
    return userService.report(request).toString0();
  }

  /**
   * 意见反馈
   * 
   * @param request
   * @return
   */
  @RequestMapping("/feedback")
  @ResponseBody
  public String feedback(FeedBackRequest request) {
    return userService.feedback(request).toString0();
  }

  /**
   * 意见反馈
   * 
   * @param request
   * @return
   */
  @RequestMapping("/save_feedback")
  @ResponseBody
  public String saveFeedback(FeedBackCategoryRequest request) {
    return userService.saveFeedback(request).toString0();
  }

  /**
   * 消息反馈
   * 
   * @param request
   * @return
   */
  @RequestMapping("/message_res")
  @ResponseBody
  public String messageRes(SysMessageRequest request) {
    return userService.messageRes(request).toString0();
  }

  /**
   * 我的消息(系统消息+用户消息)
   * 
   * @param request
   * @return
   */
  @RequestMapping("/my_message")
  @ResponseBody
  public String myMessage(QueryListRequest request) {
    return userService.myMessage(request).toString0();
  }


  /**
   * 获取充值商品列表
   * 
   * @param request
   * @return
   */
  @RequestMapping("/good_list")
  @ResponseBody
  public String goodList(MapiBaseRequest request) {
    return userService.goodList(request).toString0();
  }

  /**
   * 生成充值订单
   * 
   * @param request
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public String order(RechargeOrderRequest request) {
    return userService.order(request).toString0();
  }

  /**
   * 发起充值操作
   * 
   * @param request
   * @return
   */
  @RequestMapping("/recharge")
  @ResponseBody
  public String recharge(RechargeAmountRequest request) {
    return userService.recharge(request).toString0();
  }

  /**
   * 学长秘籍列表
   * 
   * @param request
   * @return
   */
  @RequestMapping("/help_doc")
  @ResponseBody
  public String helpDoc(MapiBaseRequest request) {
    return userService.helpDoc(request).toString0();
  }


  /**
   * 学长秘籍预览页面
   * 
   * @param request
   * @return
   */
  @RequestMapping("/show_help_doc")
  @ResponseBody
  public String showHelpDoc(HttpServletRequest request) {
    return userService.showHelpDoc(request);
  }

  /**
   * 更新基准头像
   * 
   * @param request
   * @return
   */
  @RequestMapping("/benchmark_face")
  @ResponseBody
  public String updateBenchmarkFace(UpdateBenchmarkFaceRequest request) {
    return userService.updateBenchmarkFace(request).toString0();
  }

  /**
   * 更新基准头像
   * 
   * @param request
   * @return
   */
  @RequestMapping("/modify_default_password")
  @ResponseBody
  public String modifyDefaultPassword(ModifyDefaultPassword request) {
    return userService.modifyDefaultPassword(request).toString0();
  }

  /**
   * 获取积分变更列表
   * 
   * @param request
   * @return
   */
  @RequestMapping("/credit_operation_list")
  @ResponseBody
  public String creditOperationList(CreditOperationListRequest request) {
    return userService.creditOperationList(request).toString0();
  }

}
