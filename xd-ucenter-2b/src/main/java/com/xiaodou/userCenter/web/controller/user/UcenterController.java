package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.filter.DecryptParamsFilter.RequestDecrypt;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.HelpDocModel;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
import com.xiaodou.userCenter.module.selfTaught.request.StUserInfoNewLogin;
import com.xiaodou.userCenter.module.selfTaught.request.StUserInfoRegist;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.CheckCodeRequest;
import com.xiaodou.userCenter.request.CheckTokenRequest;
import com.xiaodou.userCenter.request.CreditOperationListRequest;
import com.xiaodou.userCenter.request.FeedBackCategoryRequest;
import com.xiaodou.userCenter.request.FeedBackRequest;
import com.xiaodou.userCenter.request.FindPwdRequest;
import com.xiaodou.userCenter.request.FirstLoginTimeRequest;
import com.xiaodou.userCenter.request.ModifyDefaultPassword;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.request.ModifyPwdRequest;
import com.xiaodou.userCenter.request.PraiseListRequest;
import com.xiaodou.userCenter.request.QueryUserListRequest;
import com.xiaodou.userCenter.request.QueryUserRequest;
import com.xiaodou.userCenter.request.UploadBenchmarkFaceRequest;
import com.xiaodou.userCenter.request.UserListRequest;
import com.xiaodou.userCenter.request.UserNoticeRequest;
import com.xiaodou.userCenter.request.UserNoticeRequest.UserNoticeGetRequest;
import com.xiaodou.userCenter.request.UserPraiseRequest;
import com.xiaodou.userCenter.request.UserRankRequest;
import com.xiaodou.userCenter.request.ValidateCodeRequest;
import com.xiaodou.userCenter.response.NoticeResponse;
import com.xiaodou.userCenter.service.UcenterService;

@Controller("ucenterController")
@RequestMapping("/user")
public class UcenterController extends BaseController {

    @Resource
    UcenterService ucenterService;

    @RequestMapping("/check_token_v_1_4_9")
    @ResponseBody
    public String checkTokenV149(CheckTokenRequest request) throws Exception {
        String headVersion = "149";
        return ucenterService.checkToken(request,headVersion).toJsonString();
    }
    
    @RequestMapping("/check_token")
    @ResponseBody
    public String checkToken(CheckTokenRequest request) throws Exception {
        String headVersion = StringUtils.EMPTY;
        return ucenterService.checkToken(request,headVersion).toJsonString();
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
    public @ResponseBody String checkCode(CheckCodeRequest request) throws Exception {
        return ucenterService.getCheckCode(request).toJsonString();
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
    public @ResponseBody String validateCode(ValidateCodeRequest request) throws Exception {
        return ucenterService.validateCode(request).toJsonString();
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
    public String registerMember(StUserInfoRegist request) throws Exception {
        return ucenterService.registerAccount(request).toJsonString();
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
    public String findPwd(FindPwdRequest request) throws Exception {
        return ucenterService.findPassword(request).toJsonString();
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
    public String modifyPwd(ModifyPwdRequest request) throws Exception {
        return ucenterService.modifyPwd(request).toJsonString();
    }

    /**
     * 使用帮助接口(学长秘籍)
     * 
     * @param pojo
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/help_doc")
    @ResponseBody
    public String helpDoc(BaseRequest request) throws Exception {
        return ucenterService.selectHelpDoc(request).toJsonString();
    }

    /**
     * 学长秘籍预览页面
     * 
     * @return
     */
    @RequestMapping(value = "/show_help_doc")
    public ModelAndView showHelpDoc(HttpServletRequest request) throws Exception {
        try {
            String helpId = request.getParameter("helpId");
            if (request instanceof RequestDecrypt) {
                helpId = ((RequestDecrypt) request).getRequest().getParameter("helpId");
            }
            HelpDocModel helpDocModel = ucenterService.findHelpDocById(helpId);
            if (null == helpDocModel) return new ModelAndView("/error");
            ModelAndView modelAndView = new ModelAndView("/show");
            if (null != helpDocModel) {
                if (helpDocModel.getType() == 2) {
                    return new ModelAndView("redirect:" + helpDocModel.getContent());
                }
                modelAndView.addObject("data", helpDocModel.getContent());
                modelAndView.addObject("type", helpDocModel.getType());
            }
            return modelAndView;
        } catch (Exception e) {
            LoggerUtil.error("获取学长秘籍预览页面异常", e);
            throw e;
        }
    }


    /**
     * 公告接口
     * 
     * @param pojo
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/notice")
    @ResponseBody
    public String notice(BaseRequest request) throws Exception {
        return ucenterService.selectNotice(request).toJsonString();
    }

    /**
     * 公告预览页面
     * 
     * @deprecated
     * @return
     */
    @RequestMapping(value = "/show_notice")
    public ModelAndView showNotice(HttpServletRequest servletRequest, UserNoticeRequest request)
            throws Exception {
        try {
            String method = servletRequest.getMethod();
            if ("GET".equals(method)) {
                String noticeId = servletRequest.getParameter("noticeId");
                if (servletRequest instanceof RequestDecrypt) {
                    noticeId =
                            ((RequestDecrypt) servletRequest).getRequest().getParameter("noticeId");
                }
                return getNoticeByNoticeId(noticeId);
            }
            String json = ucenterService.findUserRelateNotice(request).toJsonString();
            NoticeResponse noticeResponse =
                    FastJsonUtil.fromJson(super.getDecodeValue(json), NoticeResponse.class);
            if (null == noticeResponse || !("0").equals(noticeResponse.getRetcode())) {
                String reqJson = servletRequest.getParameter("req");
                if (servletRequest instanceof RequestDecrypt) {
                    reqJson = ((RequestDecrypt) servletRequest).getRequest().getParameter("req");
                }
                if (StringUtils.isBlank(reqJson)) return new ModelAndView("/error");
                UserNoticeRequest noticeRequest = FastJsonUtil
                        .fromJson(super.getDecodeValue(reqJson), UserNoticeRequest.class);
                String noticeId = noticeRequest.getNoticeId();
                return getNoticeByNoticeId(noticeId);
            }

            UserRelateNoticeModel uNoticeModel = noticeResponse.getUserRelateNoticeModel();
            String userId = noticeResponse.getUserId();
            String noticeId = noticeResponse.getNoticeId();
            if (null == uNoticeModel) {
                LoggerUtil.error("uNoticeModel为空", null);
                return new ModelAndView("/error");
            }
            ModelAndView modelAndView = new ModelAndView("/show");
            if (null != uNoticeModel) {
                // 公告状态改为已经读
                if (uNoticeModel.getStatus() == 1) {
                    int flag = ucenterService.updateUserRelateNotice(userId, noticeId);
                    if (flag != 1) {
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
            throw e;
        }
    }

    private ModelAndView getNoticeByNoticeId(String noticeId) throws Exception {
        NoticeResponse noticeResponse = ucenterService.findNotice(noticeId);
        if (!noticeResponse.isRetOk()) return new ModelAndView("/error");
        UserRelateNoticeModel uNoticeModel = noticeResponse.getUserRelateNoticeModel();
        if (null == uNoticeModel) return new ModelAndView("/error");
        ModelAndView modelAndView = new ModelAndView("/show");
        if (null != uNoticeModel) {
            if (uNoticeModel.getType() == 2) {
                return new ModelAndView("redirect:" + uNoticeModel.getContent());
            }
            modelAndView.addObject("data", uNoticeModel.getContent());
            modelAndView.addObject("type", uNoticeModel.getType());
        }
        return modelAndView;
    }


    /**
     * 获取通知内容
     */
    @RequestMapping("/find_user_relate_notice")
    @ResponseBody
    public String findUserRelateNotice(UserNoticeRequest request) throws Exception {
        return ucenterService.findUserRelateNotice(request).toJsonString();
    }

    /**
     * 获取通知内容
     */
    @RequestMapping("/find_notice")
    @ResponseBody
    public String findNotice(UserNoticeGetRequest request) throws Exception {
        return ucenterService.findNotice(request.getNoticeId()).toString0();
    }

    /**
     * 更改用户的某条通知状态
     */
    @RequestMapping("/update_user_relate_notice")
    @ResponseBody
    public String updateUserRelateNotice(UserNoticeRequest request) throws Exception {
        return ucenterService.updateUserRelateNotice(request).toJsonString();
    }

    /**
     * 一键已读
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @RequestMapping("/notice_to_read_all")
    @ResponseBody
    public String noticetoReadAll(BaseRequest request) throws Exception {
        return ucenterService.noticetoReadAll(request).toJsonString();
    }

    /**
     * 意见反馈接口
     * 
     * @param pojo
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/feedback")
    @ResponseBody
    public String feedback(FeedBackRequest request) throws Exception {
        return ucenterService.addFeedBack(request).toJsonString();
    }

    @RequestMapping("/save_feedback")
    @ResponseBody
    public String saveFeedback(FeedBackCategoryRequest request) throws Exception {
        return ucenterService.saveFeedBack(request).toJsonString();
    }

    @RequestMapping("/login_out")
    public @ResponseBody String loginOut(BaseRequest request) throws Exception {
        return ucenterService.loginOut(request).toJsonString();
    }

    // @RequestMapping("/login")
    // @ResponseBody
    // public String login(StUserInfoNewLogin request) throws Exception {
    // return doMain(request, new IController<NewLoginRequest>() {
    // @Override
    // public ResultInfo doService(NewLoginRequest pojo) throws Exception {
    // return ucenterService.login(pojo);
    // }
    // });
    // }

    @RequestMapping("/login_v_1_4_9")
    @ResponseBody
    public String login_v_1_4_9(StUserInfoNewLogin request) throws Exception {
        String headVersion = "149";
        return ucenterService.login(request, headVersion).toJsonString();
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(StUserInfoNewLogin request) throws Exception {
        Errors error = request.validate();
        if (StringUtils.isEmpty(request.getPackageTag())) {
            error.rejectValue("packageTag", null, null, "packageTag is empty.");
        }
        if (null != error && error.hasErrors()) {
            ResultInfo response = new ResultInfo(ResultType.VALFAIL);
            response.appendRetdesc("#" + getErrMsg(error));
            return FastJsonUtil.toJson(response);
        }

        String headVersion = StringUtils.EMPTY;
        return ucenterService.login(request, headVersion).toJsonString();
    }

    /**
     * 系统公告和活动接口(没有用到)
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/blank_notice")
    @ResponseBody
    public String blankNotice(BaseRequest request) throws Exception {
        return ucenterService.blankNotice().toJsonString();
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
        return ucenterService.queryUser(request).toJsonString();
    }


    /**
     * 个人资料修改接口
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/modify_my_info_v_1_4_9")
    @ResponseBody
    public String modifyMyInfo_v_1_4_9(ModifyMyInfoRequest request) throws Exception {
        String headVersion = "149";
        return ucenterService.modifyUser(request, headVersion).toJsonString();
    }

    @RequestMapping("/modify_my_info")
    @ResponseBody
    public String modifyMyInfo(ModifyMyInfoRequest request) throws Exception {
        Errors error = request.validate();
        if (UcenterModelConstant.IMPROVE_INFO.equals(request.getType())) {
            if (StringUtils.isEmpty(request.getRegion())) {
                error.rejectValue("region", null, null, "region is empty.");
            }
            if (StringUtils.isEmpty(request.getRegionName())) {
                error.rejectValue("regionName", null, null, "regionName is empty.");
            }
            if (StringUtils.isEmpty(request.getMajor())) {
                error.rejectValue("major", null, null, "major is empty.");
            }
            if (StringUtils.isEmpty(request.getMajorId())) {
                error.rejectValue("majorId", null, null, "majorId is empty.");
            }
            if (StringUtils.isEmpty(request.getMajorName())) {
                error.rejectValue("majorName", null, null, "majorName is empty.");
            }
        }
        if (null != error && error.hasErrors()) {
            ResultInfo response = new ResultInfo(ResultType.VALFAIL);
            response.appendRetdesc("#" + getErrMsg(error));
            return FastJsonUtil.toJson(response);
        }
        String headVersion = StringUtils.EMPTY;
        return ucenterService.modifyUser(request, headVersion).toJsonString();
    }

    /**
     * selftaught 获取赞列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/praise_list")
    @ResponseBody
    public String praiseList(PraiseListRequest request) throws Exception {
        return ucenterService.praiseList(request).toJsonString();
    }

    /**
     * selftaught 点赞
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/praise")
    @ResponseBody
    public String praise(UserPraiseRequest request) throws Exception {
        return ucenterService.praise(request).toJsonString();
    }

    /**
     * selftaught 排位赛列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/rank_list")
    @ResponseBody
    public String rankList(UserRankRequest request) throws Exception {
        return ucenterService.rankList(request).toJsonString();
    }

    /**
     * 查询用户信息列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/user_list")
    @ResponseBody
    public String userList(UserListRequest request) throws Exception {
        return ucenterService.userList(request).toJsonString();
    }

    /**
     * selftaught 获取个人详情页
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/unread_mes_count")
    @ResponseBody
    public String unReadMesCount(BaseRequest request) throws Exception {
        return ucenterService.unReadMesCount(request).toJsonString();
    }

    /**
     * 查询用户首次登陆app时间
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/user_first_login_time")
    @ResponseBody
    public String getUserFirstLoginTime(FirstLoginTimeRequest request) throws Exception {
        return ucenterService.getUserFirstLoginTime(request).toJsonString();
    }

    /**
     * 查询指定用户组的信息
     * 
     * @param pojo
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @RequestMapping("/query_user_by_id_list")
    @ResponseBody
    public String queryUserList(QueryUserListRequest request) throws Exception {
        return ucenterService.queryUserList(request).toJsonString();
    }

    /**
     * 更新用户基准面容
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/benchmark_face")
    @ResponseBody
    public String uploadBenchmarkFace(UploadBenchmarkFaceRequest request) throws Exception {
        return ucenterService.UploadBenchmarkFaceRequest(request).toJsonString();
    }

    /**
     * 
     * @description 导入用户，修改默认密码
     * @author 李德洪
     * @Date 2017年5月12日
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/modify_default_password")
    @ResponseBody
    public String modifyDefaultPassword(ModifyDefaultPassword request) throws Exception {
        return ucenterService.modifyDefaultPassword(request).toJsonString();
    }

    /**
     * 获取积分变更列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/credit_operation_list")
    @ResponseBody
    public String creditOperationList(CreditOperationListRequest request) throws Exception {
        return ucenterService.creditOperationList(request).toJsonString();
    }
}
