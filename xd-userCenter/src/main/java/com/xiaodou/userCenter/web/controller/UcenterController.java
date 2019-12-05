package com.xiaodou.userCenter.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.web.filter.DecryptParamsFilter.RequestDecrypt;
import com.xiaodou.userCenter.model.UserRelateNoticeModel;
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
import com.xiaodou.userCenter.request.selftaught.ModifyStMyInfoRequest;
import com.xiaodou.userCenter.response.NoticeResponse;
import com.xiaodou.userCenter.service.UcenterService;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

@Controller("ucenterController")
@RequestMapping("/user")
public class UcenterController extends BaseController {

	@Resource
	UcenterService ucenterService;
	
	@RequestMapping("/checkToken")
	public @ResponseBody
	String checkToken(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<BaseRequest>() {

			@Override
			public ResultInfo doService(BaseRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.checkToken(pojo);
			}

			@Override
			public BaseRequest buildRequest(HttpServletRequest request)
					throws Exception {
				return new BaseRequest();
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
	@RequestMapping("/checkCode")
	public @ResponseBody
	String checkCode(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<CheckCodeRequest>() {
			@Override
			public ResultInfo doService(CheckCodeRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.getCheckCode(pojo);
			}

			@Override
			public CheckCodeRequest buildRequest(HttpServletRequest request)
					throws Exception {
				CheckCodeRequest pojo = null;
				if ((pojo = getParamsValue(request, CheckCodeRequest.class)) == null)
					pojo = new CheckCodeRequest();
				return pojo;
			}
		});
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
	@RequestMapping("/validateCode")
	public @ResponseBody
	String validateCode(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<ValidateCodeRequest>() {
			@Override
			public ResultInfo doService(ValidateCodeRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.validateCode(pojo);
			}

			@Override
			public ValidateCodeRequest buildRequest(HttpServletRequest request)
					throws Exception {
				ValidateCodeRequest pojo = null;
				if ((pojo = getParamsValue(request, ValidateCodeRequest.class)) == null)
					pojo = new ValidateCodeRequest();
				return pojo;
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
	@RequestMapping("/registerMember")
	@ResponseBody
	public String registerMember(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<RegistAccountRequest>() {
			@Override
			public ResultInfo doService(RegistAccountRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.registerAccount(pojo);
			}

			@Override
			public RegistAccountRequest buildRequest(HttpServletRequest request)
					throws Exception {
				RegistAccountRequest pojo = null;
				if ((pojo = getParamsValue(request, ModuleMappingWrapper
						.getWrapper().getModule().getRegist())) == null)
					pojo = ModuleMappingWrapper.getWrapper().getModule()
							.getRegist().newInstance();
				return pojo;
			}
		});
	}

	/**
	 * 用户登录接口
	 * 
	 * @param pojo
	 * @param errors
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request) {
		return doMain(request, new IController<LoginRequest>() {
			@Override
			public ResultInfo doService(LoginRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.login(pojo);
			}

			@Override
			public LoginRequest buildRequest(HttpServletRequest request)
					throws Exception {
				LoginRequest pojo = null;
				if ((pojo = getParamsValue(request, LoginRequest.class)) == null)
					pojo = new LoginRequest();
				return pojo;
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
	@RequestMapping("/findPwd")
	@ResponseBody
	public String findPwd(HttpServletRequest request) {
		return doMain(request, new IController<FindPwdRequest>() {
			@Override
			public ResultInfo doService(FindPwdRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.findPassword(pojo);
			}

			@Override
			public FindPwdRequest buildRequest(HttpServletRequest request)
					throws Exception {
				FindPwdRequest pojo = null;
				if ((pojo = getParamsValue(request, FindPwdRequest.class)) == null)
					pojo = new FindPwdRequest();
				return pojo;
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
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public String modifyPwd(HttpServletRequest request) {
		return doMain(request, new IController<ModifyPwdRequest>() {
			@Override
			public ResultInfo doService(ModifyPwdRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.modifyPwd(pojo);
			}

			@Override
			public ModifyPwdRequest buildRequest(HttpServletRequest request)
					throws Exception {
				ModifyPwdRequest pojo = null;
				if ((pojo = getParamsValue(request, ModifyPwdRequest.class)) == null)
					pojo = new ModifyPwdRequest();
				return pojo;
			}
		});
	}

	/**
	 * 个人资料修改接口
	 * 
	 * @param pojo
	 * @param errors
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyMyInfo")
	@ResponseBody
	public String modifyMyInfo(HttpServletRequest request) {
		return doMain(request, new IController<ModifyMyInfoRequest>() {
			@Override
			public ResultInfo doService(ModifyMyInfoRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.modifyUser(pojo);
			}

			@Override
			public ModifyMyInfoRequest buildRequest(HttpServletRequest request)
					throws Exception {
				ModifyMyInfoRequest modifyPojo = ModuleMappingWrapper
						.getWrapper().getModule().getModify().newInstance();
				modifyPojo = getParamsValue(request, ModuleMappingWrapper
						.getWrapper().getModule().getModify());
				if (null == modifyPojo)
					return null;
				return modifyPojo.getModifyAccountInfo();
			}

		});
	}

	/**
	 * 使用帮助接口
	 * 
	 * @param pojo
	 * @param errors
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/helpDoc")
	@ResponseBody
	public String helpDoc(HttpServletRequest request) {
		return doMain(request, new IController<BaseRequest>() {

			@Override
			public ResultInfo doService(BaseRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.selectHelpDoc(pojo, request);
			}

			@Override
			public BaseRequest buildRequest(HttpServletRequest request)
					throws Exception {
				return new BaseRequest();
			}

		});
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
	public String notice(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<BaseRequest>() {

			@Override
			public ResultInfo doService(BaseRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.selectNotice(pojo);
			}

			@Override
			public BaseRequest buildRequest(HttpServletRequest request)
					throws Exception {
				return new BaseRequest();
			}

		});
	}

	/**
	 * 公告预览页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showNotice")
	public ModelAndView showNotice(HttpServletRequest request) throws Exception {
		try {
			String method = request.getMethod();
			if ("GET".equals(method)) {
				String noticeId = request.getParameter("noticeId");
				if (request instanceof RequestDecrypt) {
					noticeId = ((RequestDecrypt) request).getRequest()
							.getParameter("noticeId");
				}
				return getNoticeByNoticeId(noticeId);
			}
			String json = doMain(request, new IController<UserNoticeRequest>() {
				@Override
				public ResultInfo doService(UserNoticeRequest pojo,
						HttpServletRequest request) throws Exception {
					return ucenterService.findUserRelateNotice(pojo);
				}

				@Override
				public UserNoticeRequest buildRequest(HttpServletRequest request)
						throws Exception {
					UserNoticeRequest pojo = null;
					if ((pojo = getParamsValue(request, UserNoticeRequest.class)) == null)
						pojo = new UserNoticeRequest();
					return pojo;
				}
			});
			if (null == JSON.parseObject(json)
					|| !("0").equals((String) JSON.parseObject(json).get(
							"retcode"))) {
				String reqJson = request.getParameter("req");
				if (StringUtils.isBlank(reqJson))
					return new ModelAndView("/error");
				UserNoticeRequest noticeRequest = FastJsonUtil.fromJson(
						this.getDecodeValue(reqJson), UserNoticeRequest.class);
				String noticeId = noticeRequest.getNoticeId();
				return getNoticeByNoticeId(noticeId);
			}
			NoticeResponse noticeResponse = FastJsonUtil.fromJson(
					this.getDecodeValue(json), NoticeResponse.class);

			UserRelateNoticeModel uNoticeModel = noticeResponse
					.getUserRelateNoticeModel();
			String userId = noticeResponse.getUserId();
			String noticeId = noticeResponse.getNoticeId();
			if (null == uNoticeModel)
				return new ModelAndView("/error");
			ModelAndView modelAndView = new ModelAndView("/show");
			if (null != uNoticeModel) {
				// 公告状态改为已经读
				if (uNoticeModel.getStatus() == 1) {
					int flag = ucenterService.updateUserRelateNotice(userId,
							noticeId);
					if (flag != 1)
						return new ModelAndView("/error");// 修改状态失败
				}
				if (uNoticeModel.getType() == 2) {
					return new ModelAndView("redirect:"
							+ uNoticeModel.getContent());
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
		UserRelateNoticeModel uNoticeModel = noticeResponse
				.getUserRelateNoticeModel();
		if (null == uNoticeModel)
			return new ModelAndView("/error");
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

	/*
	 * 没有被用到的方法
	 */
	@SuppressWarnings("unused")
	private void getVersion(HttpServletRequest request) {
		// 控制版本
		String version = StringUtils.EMPTY;
		BaseRequest baseReq = new BaseRequest();
		getParamsValueFromHeader(request, baseReq);
		if (StringUtils.isBlank(baseReq.getClientType()))
			;
		if ("ios".equals(baseReq.getClientType())) {
			version = baseReq.getVersion();
			if (StringUtils.isBlank(version) || "1.0.4".equals(version))
				;
		}
		if ("android".equals(baseReq.getClientType())) {
			version = baseReq.getVersion();
			if (StringUtils.isBlank(version) || "2.0.2".equals(version)
					|| "2.1.0".equals(version))
				;
		}
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
	public String feedback(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<FeedBackRequest>() {

			@Override
			public ResultInfo doService(FeedBackRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.addFeedBack(pojo);
			}

			@Override
			public FeedBackRequest buildRequest(HttpServletRequest request)
					throws Exception {
				FeedBackRequest pojo = null;
				if ((pojo = getParamsValue(request, FeedBackRequest.class)) == null)
					pojo = new FeedBackRequest();
				return pojo;
			}

		});
	}

	/**
	 * 退出登录接口
	 * 
	 * @param token
	 */
	@RequestMapping("/loginOut")
	public @ResponseBody
	String loginOut(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<BaseRequest>() {

			@Override
			public ResultInfo doService(BaseRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.loginOut(pojo);
			}

			@Override
			public BaseRequest buildRequest(HttpServletRequest request)
					throws Exception {
				return new BaseRequest();
			}

		});
	}

	/**
	 * 第三方登录接口
	 * 
	 * @param
	 */
	@RequestMapping("/newLogin")
	public @ResponseBody
	String newLogin(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<NewLoginRequest>() {
			@Override
			public ResultInfo doService(NewLoginRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.newLogin(pojo);
			}

			@Override
			public NewLoginRequest buildRequest(HttpServletRequest request)
					throws Exception {
				NewLoginRequest pojo = null;
				if ((pojo = getParamsValue(request, ModuleMappingWrapper
						.getWrapper().getModule().getNewLogin())) == null)
					pojo = ModuleMappingWrapper.getWrapper().getModule()
							.getNewLogin().newInstance();
				return pojo;
			}

		});
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
	public String blankNotice(HttpServletRequest request) throws Exception {
		return doMain(request, new IController<BaseRequest>() {

			@Override
			public ResultInfo doService(BaseRequest pojo,
					HttpServletRequest request) throws Exception {
				return ucenterService.blankNotice();
			}

			@Override
			public BaseRequest buildRequest(HttpServletRequest request)
					throws Exception {
				return new BaseRequest();
			}

		});
	}
}
