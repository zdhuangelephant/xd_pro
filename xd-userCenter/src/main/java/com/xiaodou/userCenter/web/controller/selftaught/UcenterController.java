package com.xiaodou.userCenter.web.controller.selftaught;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.userCenter.request.selftaught.ModifyStMyInfoRequest;
import com.xiaodou.userCenter.request.selftaught.PersonInfoRequest;
import com.xiaodou.userCenter.service.selftaught.UcenterService;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;
import com.xiaodou.userCenter.web.controller.BaseController;

@Controller("stUcenterController")
@RequestMapping("/selftaught/user")
public class UcenterController extends BaseController {
	@Resource(name="stUcenterService")
	UcenterService stUcenterService;

	/**
	 * selftaught 个人资料修改接口
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyMyInfo")
	@ResponseBody
	public String modifySelfTaughtMyInfo(HttpServletRequest request) {
		return doMain(request, new IController<ModifyStMyInfoRequest>() {
			@Override
			public ResultInfo doService(ModifyStMyInfoRequest pojo,
					HttpServletRequest request) throws Exception {
				return stUcenterService.modifyStUser(pojo);
			}
			@Override
			public ModifyStMyInfoRequest buildRequest(HttpServletRequest request)
					throws Exception {
				ModifyStMyInfoRequest pojo = null;
				if ((pojo = getParamsValue(request, ModuleMappingWrapper
						.getWrapper().getModule().getStModify())) == null)
					pojo = ModuleMappingWrapper.getWrapper().getModule()
							.getStModify().newInstance();
				return pojo;
			}
		});
	}
	
	/**
	 * selftaught 获取个人详情页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person_info")
	@ResponseBody
	public String personInfo(HttpServletRequest request) {
		return doMain(request, new IController<PersonInfoRequest>() {
			@Override
			public ResultInfo doService(PersonInfoRequest pojo,
					HttpServletRequest request) throws Exception {
				return stUcenterService.personInfo(pojo);
			}
			@Override
			public PersonInfoRequest buildRequest(HttpServletRequest request)
					throws Exception {
				PersonInfoRequest pojo = new PersonInfoRequest();
				if ((pojo = getParamsValue(request, PersonInfoRequest.class)) == null)
					pojo = new PersonInfoRequest();
				return pojo;
			}
		});
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
	public String praise(HttpServletRequest request) {
		return doMain(request, new IController<PersonInfoRequest>() {
			@Override
			public ResultInfo doService(PersonInfoRequest pojo,
					HttpServletRequest request) throws Exception {
				return stUcenterService.personInfo(pojo);
			}
			@Override
			public PersonInfoRequest buildRequest(HttpServletRequest request)
					throws Exception {
				PersonInfoRequest pojo = new PersonInfoRequest();
				if ((pojo = getParamsValue(request, PersonInfoRequest.class)) == null)
					pojo = new PersonInfoRequest();
				return pojo;
			}
		});
	}
	
	
}
