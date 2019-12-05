package com.xiaodou.server.mapi.service.ucenter;

import static com.xiaodou.server.mapi.constant.SelfTaughtConstant.MODULE;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.request.FriendAddRequest;
import com.xiaodou.server.mapi.request.FriendDelRequest;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.server.mapi.request.UserListRequest;
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
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.CheckCodeResponse;
import com.xiaodou.server.mapi.response.ucenter.CreditOperationListResponse;
import com.xiaodou.server.mapi.response.ucenter.FindPasswordResponse;
import com.xiaodou.server.mapi.response.ucenter.FriendListResponse;
import com.xiaodou.server.mapi.response.ucenter.HelpDocResponse;
import com.xiaodou.server.mapi.response.ucenter.LoginResponse;
import com.xiaodou.server.mapi.response.ucenter.ModifyMyInfoResponse;
import com.xiaodou.server.mapi.response.ucenter.ModifyPwdResponse;
import com.xiaodou.server.mapi.response.ucenter.NoticeResponse;
import com.xiaodou.server.mapi.response.ucenter.PersonInfoResponse;
import com.xiaodou.server.mapi.response.ucenter.QueryListResponse;
import com.xiaodou.server.mapi.response.ucenter.RegistAccountResponse;
import com.xiaodou.server.mapi.response.ucenter.StConfigResponse;
import com.xiaodou.server.mapi.response.ucenter.StConfigResponse.SysCount;
import com.xiaodou.server.mapi.response.ucenter.StConfigVersionResponse;
import com.xiaodou.server.mapi.response.ucenter.UpTokenResponse;
import com.xiaodou.server.mapi.response.ucenter.UserInfoByTokenResponse;
import com.xiaodou.server.mapi.response.ucenter.UserListResponse;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.response.ucenter.UserRelateNoticeResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.service.BaseMapiService;
import com.xiaodou.server.mapi.util.PlatformUtil;
import com.xiaodou.server.mapi.util.UserTokenWrapper;
import com.xiaodou.server.mapi.vo.ucenter.model.UserRelateNoticeModel;
import com.xiaodou.server.mapi.web.filter.CheckUserStatusFilter.LoginPar;
import com.xiaodou.summer.util.ReqInfoWrapper;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.response.GoodsListResponse;
import com.xiaodou.wallet.agent.response.RechargeAmountResponse;
import com.xiaodou.wallet.agent.response.RechargeOrderResponse;

@Service("userService")
public class UserService extends BaseMapiService {

    /**
     * 获取七牛上传token
     * 
     * @param pojo
     * @return
     */
    public UpTokenResponse getUpToken(UpTokenPojo pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.putAll(pojo.getUserBaseParams());
        params.put("scope", pojo.getScope());
        HttpResult httpResult = router("ucenter", "upToken", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), UpTokenResponse.class);
        }
        return new UpTokenResponse(ResultType.SYSFAIL);
    }

    public UserInfoByTokenResponse checkToken(MapiBaseRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "checkToken", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            UserInfoByTokenResponse response =
                    FastJsonUtil.fromJson(httpResult.getContent(), UserInfoByTokenResponse.class);
            return response;
        }
        return new UserInfoByTokenResponse(ResultType.SYSFAIL);
    }

    public BaseResponse registerIM(MapiBaseRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "registerIM", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            BaseResponse registRes =
                    FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
            return registRes;
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    public LoginResponse login(NewLoginRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        CommUtil.transferFromVO2Map(params, pojo);
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "login", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            LoginResponse response =
                    FastJsonUtil.fromJson(httpResult.getContent(), LoginResponse.class);
            if (null != response && response.isRetOk()
                    && PlatformUtil.isThirdPartAccount(pojo.getPlatform())) {
                if (null == response.getUser()
                        || StringUtils.isBlank(response.getUser().getUserId()))
                    return new LoginResponse(ResultType.SYSFAIL);
            }

            // 低版本
            // 如果是低版本并且之前选好的地域不是北京
            if (pojo.getVersion().compareTo("1.5.0") < 0 && null != response
                    && null != response.getUser()) {
                String region = response.getUser().getRegion();
                if (StringUtils.isNotEmpty(region) && !MODULE.equals(region)) {
                    response.setRetcode(UcenterResType.UnCompleteInfo.getCode());
                    response.setRetdesc(UcenterResType.UnCompleteInfo.getMsg());
                }
            }

            return response;
        }
        return new LoginResponse(ResultType.SYSFAIL);
    }

    /**
     * 用户注册方法
     * 
     * @param pojo
     * @return
     */
    public RegistAccountResponse registerAccount(RegistAccountRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("confirmPassword", pojo.getConfirmPassword());
        params.put("phoneNum", pojo.getPhoneNum());
        params.put("password", pojo.getPassword());
        params.put("checkCode", pojo.getCheckCode());
        params.put("nickName", pojo.getNickName());
        params.put("gender", pojo.getGender());
        params.put("portrait", pojo.getPortrait());
        params.put("packageTag", pojo.getPackageTag());
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "registerMember", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            RegistAccountResponse response =
                    FastJsonUtil.fromJson(httpResult.getContent(), RegistAccountResponse.class);
            return response;
        }
        return new RegistAccountResponse(ResultType.SYSFAIL);
    }

    /**
     * 校验验证码接口
     * 
     * @param pojo
     * @return
     */
    public BaseResponse validateCode(ValidateCodeRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("phoneNum", pojo.getPhoneNum());
        params.put("checkCodeType", pojo.getCheckCodeType());
        params.put("checkCode", pojo.getCheckCode());
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "validateCode", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            BaseResponse response =
                    FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
            return response;
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 获取验证码接口
     * 
     * @param pojo
     * @return
     */
    public CheckCodeResponse getCheckCode(CheckCodeRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("phoneNum", pojo.getPhoneNum());
        params.put("checkCodeType", pojo.getCheckCodeType());
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "checkCode", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), CheckCodeResponse.class);
        }
        return new CheckCodeResponse(ResultType.SYSFAIL);
    }


    /**
     * 
     * 找回密码接口
     * 
     * @param pojo
     * @return
     */
    public FindPasswordResponse findPassword(FindPwdRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("phoneNum", pojo.getPhoneNum());
        params.put("checkCode", pojo.getCheckCode());
        params.put("pwd", pojo.getPwd());
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "findPwd", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), FindPasswordResponse.class);
        }
        return new FindPasswordResponse(ResultType.SYSFAIL);
    }

    /**
     * 修改用户密码service
     * 
     * @param pojo
     * @return
     */
    public ModifyPwdResponse modifyPwd(ModifyPwdRequest pojo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("OldPwd", pojo.getOldPwd());
        params.put("NewPwd", pojo.getNewPwd());
        params.put("confirmNewPwd", pojo.getConfirmNewPwd());
        params.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "modifyPwd", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), ModifyPwdResponse.class);
        }
        return new ModifyPwdResponse(ResultType.SYSFAIL);
    }

    public UserListResponse queryUserListInfo(UserListRequest userIdListReq) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("userIdList", userIdListReq.getUserIdList());
        param.putAll(userIdListReq.getUserBaseParams());
        HttpResult userList = router("ucenter", "userList", param);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), UserListResponse.class);
        }
        return new UserListResponse(ResultType.SYSFAIL);
    }


    public FriendListResponse listFriend(MapiBaseRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult userList = router("friend", "listFriend", param);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), FriendListResponse.class);
        }
        return new FriendListResponse(ResultType.SYSFAIL);
    }

    /**
     * 发起好友申请。 · url /friend/add_friend
     * 
     * @param pojo
     */
    public BaseResponse addFriend(FriendAddRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("targetUserId", pojo.getTargetUserId());
        HttpResult userList = router("friend", "addFriend", param);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 5、 删除好友。 · url /friend/del_friend
     * 
     * @param pojo
     */
    public BaseResponse delFriend(FriendDelRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("targetUserId", pojo.getTargetUserId());
        HttpResult userList = router("friend", "delFriend", param);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }


    public <T extends MapiBaseRequest> BaseResponse queryFriend(T pojo, String userId,
            String targetUserId) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("userId", userId);
        param.put("targetUserId", targetUserId);
        HttpResult httpResult = router("friend", "queryFriend", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            BaseResponse response =
                    FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
            return response;
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }



    /**
     * 公告接口
     * 
     */
    public UserRelateNoticeResponse notice(MapiBaseRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "notice", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), UserRelateNoticeResponse.class);
        }
        return new UserRelateNoticeResponse(ResultType.SYSFAIL);
    }

    /**
     * 公告详情接口
     * 
     * @throws IOException
     * 
     */
    public void showNotice1(UserNoticeRequest pojo) throws IOException {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("noticeId", pojo.getNoticeId());
        HttpResult httpResult = router("ucenter", "showNotice", "1.0.3", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isNotBlank(httpResult.getContent())) {
            SET_CONTENT_TYPE: {
                for (Header header : httpResult.getHeaders()) {
                    if (header.getName().equals("Content-Type")) {
                        ReqInfoWrapper.getWrapper().getRes().setContentType(header.getValue());
                        break SET_CONTENT_TYPE;
                    }
                }
                ReqInfoWrapper.getWrapper().getRes().setContentType("text/html; charset=utf-8");
            }
            ReqInfoWrapper.getWrapper().getRes().getWriter().print(httpResult.getContent());
            ReqInfoWrapper.getWrapper().getRes().getWriter().flush();
        }
        // return new UserRelateNoticeResponse(ResultType.SYSFAIL).toString();
    }

    /**
     * 公告预览页面
     * 
     * @return
     */
    public ModelAndView showNotice(UserNoticeRequest request) {
        // HttpServletRequestWrapper servletRequest =
        // (HttpServletRequestWrapper) ReqInfoWrapper.getWrapper().getReq();
        try {
            // 未登录用户
            // String method = servletRequest.getMethod();
            // if ("GET".equals(method)) {
            // String noticeId = servletRequest.getRequest().getParameter("noticeId");
            // if (StringUtils.isBlank(noticeId)) {
            // LoggerUtil.error("GET->noticeId为空", null);
            // return new ModelAndView("/error");
            // }
            // return getNoticeByNoticeId(request, "GET");
            // }
            // 登录用户
            NoticeResponse noticeResponse = this.findUserRelateNotice(request);
            if (null == noticeResponse || !("0").equals(noticeResponse.getRetcode())) {
                return getNoticeByNoticeId(request);
            }
            UserRelateNoticeModel uNoticeModel = noticeResponse.getUserRelateNoticeModel();
            if (null == uNoticeModel) {
                LoggerUtil.error("uNoticeModel为空", null);
                return new ModelAndView("/error");
            }
            ModelAndView modelAndView = new ModelAndView("/show");
            if (null != uNoticeModel) {
                // 公告状态改为已经读
                if (uNoticeModel.getStatus() == 1) {
                    BaseResponse res = this.updateUserRelateNotice(request);
                    if (!("0").equals(res.getRetcode())) {
                        LoggerUtil.error("公告状态改为已经读 失败", null);
                        return new ModelAndView("/error");// 修改状态失败
                    }
                }
                if (uNoticeModel.getType() == 2) {
                    return new ModelAndView("redirect:" + uNoticeModel.getContent());
                }
                modelAndView.addObject("data", uNoticeModel.getContent());
                modelAndView.addObject("type", uNoticeModel.getType());
            }
            return modelAndView;
        } catch (Exception e) {
            LoggerUtil.error("获取公告预览页面异常", e);
            return new ModelAndView("/error");// 修改状态失败
        }
    }

    /**
     * @description 获取通知内容，无需header头
     * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
     * @Date 2018年5月30日
     * @param req
     * @return
     */
    public ModelAndView getNoticeByNoticeId(UserNoticeRequest req) {
        ModelAndView modelAndView = new ModelAndView("/show");
        Map<String, Object> param = Maps.newHashMap();
        param.put("noticeId", req.getNoticeId());
        HttpResult httpResult = router("ucenter", "findNotice","1.0.0", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            NoticeResponse noticeResponse =
                    FastJsonUtil.fromJson(httpResult.getContent(), NoticeResponse.class);
            if (null == noticeResponse) {
                LoggerUtil.error("GET->noticeResponse为空", null);
                return new ModelAndView("/error");
            }
            UserRelateNoticeModel uNoticeModel = noticeResponse.getUserRelateNoticeModel();
            if (null == uNoticeModel) {

                LoggerUtil.error("GET->UserNoticeRequest的值" + req.toString(), null);
                LoggerUtil.error("GET->uNoticeModel为空 start", null);
                LoggerUtil.error(JSON.toJSONString(noticeResponse), null);
                LoggerUtil.error("GET->uNoticeModel为空 end", null);
                return new ModelAndView("/error");
            }
            if (null != uNoticeModel) {
                if (uNoticeModel.getType() == 2) {
                    return new ModelAndView("redirect:" + uNoticeModel.getContent());
                }
                modelAndView.addObject("data", uNoticeModel.getContent());
                modelAndView.addObject("type", uNoticeModel.getType());
            }
        }
        return modelAndView;
    }

    /**
     * 获取通知内容
     */

    public NoticeResponse findUserRelateNotice(UserNoticeRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("userId", pojo.getUid());
        param.put("noticeId", pojo.getNoticeId());
        HttpResult httpResult = router("ucenter", "findUserRelateNotice", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), NoticeResponse.class);
        }
        return new NoticeResponse(ResultType.SYSFAIL);
    }

    /**
     * 更改用户的某条通知状态
     */

    public BaseResponse updateUserRelateNotice(UserNoticeRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("userId", pojo.getUid());
        param.put("noticeId", pojo.getNoticeId());
        HttpResult httpResult = router("ucenter", "updateUserRelateNotice", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }


    /**
     * 一键已读
     * 
     */
    public BaseResponse noticetoReadAll(MapiBaseRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "noticetoReadAll", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 退出登录接口
     * 
     */
    public BaseResponse loginOut(MapiBaseRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "loginOut", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 个人资料修改接口
     * 
     */
    public ModifyMyInfoResponse modifyMyInfo(ModifyMyInfoRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("module", pojo.getModule());
        param.put("type", pojo.getType());
        param.put("nickName", pojo.getNickName());
        param.put("address", pojo.getAddress());
        param.put("age", pojo.getAge());
        param.put("gender", pojo.getGender());
        param.put("portrait", pojo.getPortrait());
        param.put("sign", pojo.getSign());
        param.put("major", pojo.getMajor());
        param.put("region", pojo.getRegion());
        param.put("regionName", pojo.getRegionName());
        param.put("majorId", pojo.getMajorId());
        param.put("majorName", pojo.getMajorName());
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "modifyMyInfo", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), ModifyMyInfoResponse.class);
        }
        return new ModifyMyInfoResponse(ResultType.SYSFAIL);
    }

    public StConfigVersionResponse version(MapiBaseRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "version", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), StConfigVersionResponse.class);
        }
        return new StConfigVersionResponse(ResultType.SYSFAIL);
    }

    public StConfigResponse config(ConfigRequest pojo) {
        StConfigResponse response = new StConfigResponse(ResultType.SUCCESS);
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("majorList", pojo.getMajorList());
        param.put("thirdlogin", pojo.getThirdlogin());
        param.put("shareplateform", pojo.getShareplateform());
        param.put("blankNotice", pojo.getBlankNotice());
        param.put("feedbackCategory", pojo.getFeedbackCategory());
        HttpResult httpResult = router("ucenter", "config", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            response = FastJsonUtil.fromJson(httpResult.getContent(), StConfigResponse.class);
            // 获取用户未读消息数
            if (StringUtils.isNotEmpty(pojo.getSysCount()) && pojo.getSysCount().equals("1")) {
                Map<String, Object> _param = Maps.newHashMap();
                _param.putAll(pojo.getUserBaseParams());
                HttpResult result1 = router("ucenter", "unreadMesCount", _param);
                if (null != result1 && result1.getStatusCode() == HttpResultStatus.OK.getCode()
                        && StringUtils.isJsonNotBlank(result1.getContent())) {
                    PersonInfoResponse unreadMesCount =
                            FastJsonUtil.fromJson(result1.getContent(), PersonInfoResponse.class);
                    SysCount sysCount = new SysCount();
                    sysCount.setNoticeCount(unreadMesCount.getNoticeCount());
                    sysCount.setSysMesCount(unreadMesCount.getSysMesCount());
                    response.getData().setSysCount(sysCount);
                }
            }
            return response;
        }
        return new StConfigResponse(ResultType.SYSFAIL);
    }

    /**
     * 查询指定用户信息
     * 
     * @param pojo
     * @return
     */
    public UserModelResponse queryUser(QueryUserRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("userId", pojo.getUserId());
        HttpResult httpResult = router("ucenter", "queryUser", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), UserModelResponse.class);
        }
        return new UserModelResponse(ResultType.SYSFAIL);
    }

    /**
     * 查询指定用户信息
     * 
     * @param pojo
     * @return
     */
    public UserModelResponse queryUserByUserName(QueryUserByNameRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("userName", pojo.getUserName());
        HttpResult httpResult = router("ucenter", "queryUser", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), UserModelResponse.class);
        }
        return new UserModelResponse(ResultType.SYSFAIL);
    }

    /**
     * 举报
     * 
     * @param pojo
     * @return
     */
    public BaseResponse report(ReportRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("type", pojo.getType());
        param.put("targetId", pojo.getTargetId());
        param.put("content", pojo.getContent());
        HttpResult httpResult = router("ucenter", "report", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 意见反馈
     * 
     * @param pojo
     * @return
     */
    public BaseResponse feedback(FeedBackRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("feedContent", pojo.getFeedContent());
        HttpResult httpResult = router("ucenter", "feedback", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 意见反馈
     * 
     * @param pojo
     * @return
     */
    public BaseResponse saveFeedback(FeedBackCategoryRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("feedContent", pojo.getFeedContent());
        param.put("categoryDescList", pojo.getCategoryDescList());
        param.put("number", pojo.getNumber());
        param.put("imageUrlList", pojo.getImageUrlList());
        param.put("deviceType", pojo.getDeviceType());
        param.put("osVersion", pojo.getOsVersion());
        param.put("userId", pojo.getUid());
        HttpResult httpResult = router("ucenter", "saveFeedback", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    // /**
    // * 查询指定用户列表信息
    // *
    // * @param pojo
    // * @return
    // */
    // public QueryUserListResponse queryUserByUserNameList(QueryUserListRequest pojo) {
    // Map<String, Object> param = Maps.newHashMap();
    // param.putAll(pojo.getUserBaseParams());
    // param.put("userNameList", pojo.getUserNameList());
    // HttpResult httpResult = router("ucenter", "queryUserByUserNameList", param);
    // if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
    // && StringUtils.isJsonNotBlank(httpResult.getContent())) {
    // return FastJsonUtil.fromJson(httpResult.getContent(), QueryUserListResponse.class);
    // }
    // return new QueryUserListResponse(ResultType.SYSFAIL);
    // }

    /**
     * 消息反馈
     * 
     * @param pojo
     * @return
     */
    public BaseResponse messageRes(SysMessageRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("messageId", pojo.getMessageId());
        param.put("result", pojo.getResult());
        HttpResult httpResult = router("asyncMessage", "messageRes", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 我的消息(系统消息+用户消息)
     * 
     * @param pojo
     * @return
     */
    public QueryListResponse myMessage(QueryListRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("size", pojo.getSize());
        param.put("sysIdUpper", pojo.getSysIdUpper());
        param.put("type", pojo.getType());
        param.put("userIdUpper", pojo.getUserIdUpper());
        HttpResult httpResult = router("asyncMessage", "myMessage", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), QueryListResponse.class);
        }
        return new QueryListResponse(ResultType.SYSFAIL);
    }

    /**
     * 获取充值商品列表
     * 
     * @param pojo
     * @return
     */
    public GoodsListResponse goodList(MapiBaseRequest pojo) {
        GoodsListResponse response = new GoodsListResponse();
        response.setRetcode(ResultType.SYSFAIL.getCode());
        response.setRetdesc(ResultType.SYSFAIL.getMsg());
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "goodList", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), GoodsListResponse.class);
        }
        return response;
    }

    /**
     * 生成充值订单
     * 
     * @param pojo
     * @return
     */
    public RechargeOrderResponse order(RechargeOrderRequest pojo) {
        RechargeOrderResponse response = new RechargeOrderResponse();
        response.setRetcode(ResultType.SYSFAIL.getCode());
        response.setRetdesc(ResultType.SYSFAIL.getMsg());
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("goodId", pojo.getGoodId());
        HttpResult httpResult = router("ucenter", "order", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), RechargeOrderResponse.class);
        }
        return response;
    }

    /**
     * 发起充值操作
     * 
     * @param pojo
     * @return
     */
    public RechargeAmountResponse recharge(RechargeAmountRequest pojo) {
        RechargeAmountResponse response = new RechargeAmountResponse();
        response.setRetcode(ResultType.SYSFAIL.getCode());
        response.setRetdesc(ResultType.SYSFAIL.getMsg());
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("billNo", pojo.getBillNo());
        param.put("payMethod", pojo.getPayMethod());
        HttpResult httpResult = router("ucenter", "recharge", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), RechargeAmountResponse.class);
        }
        return response;
    }

    /**
     * 使用帮助接口(学长秘籍)
     * 
     * @param pojo
     * @return
     */
    public HelpDocResponse helpDoc(MapiBaseRequest pojo) {
        HelpDocResponse response = new HelpDocResponse();
        response.setRetcode(ResultType.SYSFAIL.getCode());
        response.setRetdesc(ResultType.SYSFAIL.getMsg());
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        HttpResult httpResult = router("ucenter", "helpDoc", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), HelpDocResponse.class);
        }
        return response;
    }

    /**
     * 学长秘籍预览页面
     * 
     * @param pojo
     * @return
     */
    public String showHelpDoc(HttpServletRequest request) {
        HelpDocResponse response = new HelpDocResponse();
        response.setRetcode(ResultType.SYSFAIL.getCode());
        response.setRetdesc(ResultType.SYSFAIL.getMsg());
        Map<String, Object> params = Maps.newHashMap();
        params.put(LoginPar.module.toString(), UserTokenWrapper.getWrapper().getUser().getRegion());
        params.put(LoginPar.deviceId.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getDeviceId());
        params.put(LoginPar.sessionToken.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getSessionToken());
        params.put(LoginPar.clientIp.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getClientIp());
        params.put(LoginPar.version.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getVersion());
        params.put(LoginPar.clientType.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getClientType());
        params.put(LoginPar.traceId.toString(),
                UserTokenWrapper.getWrapper().getHeadParam().getTraceId());

        params.put("helpId", request.getParameter("helpId"));
        HttpResult httpResult = router("ucenter", "showHelpDoc", params);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return httpResult.getContent();
        }
        return response.toString0();
    }

    /**
     * 更新基准头像
     * 
     * @param request
     * @return
     */
    public BaseResponse updateBenchmarkFace(UpdateBenchmarkFaceRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("benchmarkFaceUrl", pojo.getBenchmarkFaceUrl());
        HttpResult httpResult = router("ucenter", "updateBenchmarkFace", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 导入用户，修改默认密码
     * 
     * @param pojo
     * @return
     */
    public BaseResponse modifyDefaultPassword(ModifyDefaultPassword pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("phoneNum", pojo.getPhoneNum());
        param.put("checkCode", pojo.getCheckCode());
        param.put("password", pojo.getPassword());
        HttpResult httpResult = router("ucenter", "modifyDefaultPassword", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 获取积分变更列表
     * 
     * @param uest
     * @return
     */
    public CreditOperationListResponse creditOperationList(CreditOperationListRequest pojo) {
        Map<String, Object> param = Maps.newHashMap();
        param.putAll(pojo.getUserBaseParams());
        param.put("idUpper", pojo.getIdUpper());
        param.put("size", pojo.getSize());
        HttpResult httpResult = router("ucenter", "creditOperationList", param);
        if (null != httpResult && httpResult.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(httpResult.getContent())) {
            return FastJsonUtil.fromJson(httpResult.getContent(),
                    CreditOperationListResponse.class);
        }
        return new CreditOperationListResponse(ResultType.SYSFAIL);
    }
}
